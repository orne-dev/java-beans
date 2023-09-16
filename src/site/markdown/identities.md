# Bean identities

Bean identities are properties of the beans that unmistakably identifies the
entity represented by the bean. It can be considered an abstraction of the
primary key in the ER paradigm.

Interface `Identity` provides an abstract mechanism to retrieve and manipulate
entity identities with zero knowledge of the composition of the identity.
Useful for referencing entities in front-ends and REST services without
exposing or depending of the implementation of the entities in the back-end
layer.

A default set of single value identity implementations is provided, including
implementations for identities of the next types:

- `Long` (`LongIdentity`)
- `String` (`StringIdentity`)
- `BigInteger` (`BigIntegerIdentity`)

The interface provides method `getIdentityToken()`, which returns a
`String` representation of the identity. This token must be used when exposed
through APIs, and should be a valid identifier in most of the contexts.

## Token identity

When a identity is received through APIs the token should be converted to an
implementation of `TokenIdentity`, which represents an identity of unknown
type.

Only services directly responsible of identity composition (like DAOs) should
try to resolve the generic `TokenIdentity` to the required implementation.
Other components should use the passed identity "as is".

To resolve `TokenIdentity` instances to specific identity implementations
the class `IdentityResolver` is provided. This class tries to create an
instance of the requested identity type based on the target class configuration
(see [Custom identities](#custom-identities)):

```java
TokenIdentity unresolvedIdentity;
IdentityResolver resolver = IdentityResolver.getInstance();
MyIdentity resolvedIdentity = resolver.resolve(
        unresolvedIdentity,
        MyIdentity.class);
assertEquals(
        unresolvedIdentity.getIdentityToken(),
        resolvedIdentity.getIdentityToken());
```

## Custom identities

To implement custom identities abstract classes `AbstractIdentity` and
`AbstractSimpleIdentity` are provided. A custom identity must provide a
identity body through method `getIdentityTokenBody()` that represents the
identity components as `String` and a method to parse a identity token back to
the identity type. Class `AbstractSimpleIdentity` provides base methods to
identities composed by a single value.

To implement the token parsing method a constructor with a single `String`
argument is allowed. For implementations that cannot provide such a
constructor or prefer another method a public static method that accepts a
single `String` parameter annotated with `IdentityTokenResolver`
can be provided:

```java
class MyIdentity
extends AbstractSimpleIdentity<CustomType> {

  /**
   * Creates a new instance.
   * 
   * @param value The identity value
   */
  public MyIdentity(
      final CustomType value) {
    super(value);
  }

  /**
   * Copy constructor.
   * 
   * @param copy The instance to copy
   */
  public MyIdentity(
      final MyIdentity copy) {
    super(copy);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getIdentityTokenBody() {
    // Implemented by AbstractSimpleIdentity. Can be overwritten
    // to customize to String conversion.
      return this.getValue() == null ? null : this.getValue().toString();
  }

  /**
   * Resolves the specified identity token to a valid {@code MyIdentity}
   * 
   * @param token The identity token
   * @return The resolved identity token
   * @throws NullPointerException If the identity token is {@code null}
   * @throws UnrecognizedIdentityTokenException If the identity token is not
   * a valid identity token or it doesn't start with the expected prefix
   */
  @NotNull
  @IdentityTokenResolver
  public static MyIdentity fromIdentityToken(
      @NotNull
      final String token)
  throws UnrecognizedIdentityTokenException {
    final String body = IdentityTokenFormatter.parse(
            IdentityTokenFormatter.DEFAULT_PREFIX,
            token);
    if (body == null) {
        return new MyIdentity((CustomType) null);
    } else {
      try {
        // Extract value from token body
        final CustomType value = CustomType.fromString(body);
        return new MyIdentity(value);
      } catch (final SomeException se) {
        throw new UnrecognizedIdentityTokenException(
            "Unrecognized identity token", se);
      }
    }
  }
}
```

Identities composed by multiple values can choice any bidirectional method to
format and parse the identity token body. The resulting `String` will be
converted to Base32 to avoid illegal characters, so any suitable format is
allowed.

By default the default identity prefix provided by
`IdentityTokenFormatter.DEFAULT_PREFIX` is used. If a custom identity prefers
to generate token with a custom prefix method `getIdentityTokenPrefix` can
be overwritten, remembering to use same prefix during token parsing:

```java
class MyIdentity ... {

  public static final String CUSTOM_PREFIX = "MYPREFIX";

  /**
   * {@inheritDoc}
   */
  @Nonnull
  @ValidIdentityTokenPrefix
  @Override
  protected String getIdentityTokenPrefix() {
      return CUSTOM_PREFIX
  }

  /**
   * Resolves the specified identity token to a valid {@code MyIdentity}
   * 
   * @param token The identity token
   * @return The resolved identity token
   * @throws NullPointerException If the identity token is {@code null}
   * @throws UnrecognizedIdentityTokenException If the identity token is not
   * a valid identity token or it doesn't start with the expected prefix
   */
  @NotNull
  @IdentityTokenResolver
  public static MyIdentity fromIdentityToken(
      @NotNull
      final String token)
  throws UnrecognizedIdentityTokenException {
    final String body = IdentityTokenFormatter.parse(
            CUSTOM_PREFIX,
            token);
    // ...
  }
}
```

## Identity beans

For implementing bean classes with identities the interface `IdentityBean` and
class `BaseIdentityBean` are provided:

```
class MyBean extends BaseIdentityBean {

  /**
   * Empty constructor.
   */
  public MyBean() {
      super();
  }

  /**
   * Copy constructor.
   * 
   * @param copy The instance to copy
   */
  public MyBean(
          @Nonnull
          final MyBean copy) {
      super(copy);
      // Copy properties
  }
}

MyBean bean = new MyBean();
bean.setIdentity(beanIdentity);
assertEquals(
        beanIdentity.getIdentityToken(),
        bean.getIdentity().getIdentityToken());
```

## Identity beans validation

For validating that a passed bean contains a valid identity annotation
`ValidBeanIdentity` is provided. This annotation validates that a valid
identity is provided. If the identity is an instance of `TokenIdentity` this
validation does not check that the token is in the expected format.

```java
void myMethod(
    @ValidBeanIdentity
    MyBean bean) {
  // ...
}
```

Utility class `BeanValidationUtils` provides method `isValidBeanIdentity()`
to check programmatically if a `IdentityBean` has a valid identity. Under the
hoods validates the bean against the `IdentityBean.RequireIdentity` group.

## Identity serialization and conversion

The library provides "out of the box" configuration for Java Serialization,
Jackson based JSON serialization, JAXB based XML serialization and Java Beans
editor based conversion.
