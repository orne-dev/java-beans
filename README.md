# Orne Bean utilities

Provides utilities for implementation and management of POJO Java beans.

## Identities

Interface `Identity` provides an abstract mechanism to retrieve and manipulate
entity identities with zero knowledge of the composition of the identity.
Useful for referencing abstract entities in front-ends and REST services
without exposing or depending of the implementation of the entities in the
back-end layer.

A default set of single value identity implementations is provided, including
implementations for identities of the next types:

- `Long` (`LongIdentity`)
- `String` (`StringIdentity`)
- `BigInteger` (`BigIntegerIdentity`)

For serialization the identity's token should be used, which provides a
`String` representation of the identity valid for usage in URIs and most of
identifiers. While deserializing the token should be converted to an
implementation of `TokenIdentity`, which represents an identity of unknown
type.

Only services directly responsible of identity composition (like DAOs) should
try to convert the generic `TokenIdentity` to the required implementation.
Other services should use the passed identity "as is".

### Custom identities

To implement custom identities abstract classes `AbstractIdentity` and
`AbstractSimpleIdentity` are provided. A custom identity must provide a
identity body through method `getIdentityTokenBody()` that represents the
identity components as `String` and a method to parse a identity token back to
the identity type. Class `AbstractSimpleIdentity` provides base methods to
identities composed by a single value.

To implement the token parsing method a constructor with a single `String`
parameter is allowed. For implementations that cannot provide such a
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
converted to Base64 to avoid illegal characters, so any suitable format is
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

### Identity beans

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
bean.setIdentity(myIdentity);
Identity beanIdentity = bean.getIdentity();
```

### Identity beans validation

For validating that a passed bean contains a valid identity annotation
`ValidBeanIdentity` is provided. This annotation validates that a valid
identity is provided. If the identity is derived from a token does not check
that the token is in the expected format.

```java
void myMethod(
    @ValidBeanIdentity
    MyBean bean) {
  // ...
}
```

Utility class `BeanValidationUtils` provides method `isValidBeanIdentity()`
to check programmatically if a `IdentityBean` has a valid identity.

## References

Bean references allow defining sets of validation groups that can be applied
to detect a valid reference to an unique entity.

Usually a bean with a valid identity is a valid bean reference, in addition of
any number of extra references.

To define a valid bean reference add the annotation `BeanReference` to the
bean class with an array of validation groups to apply to the bean to validate
the valid reference.

```java
@BeanReference(RequireCode.class)
protected static class MyBean {
  // ...
  @NotNull(groups=RequireCode.class)
  @ValidCode(groups=Default.class,RequireCode.class)
  private String code;
  // ...
  public static interface RequireCode {
    // Validation group
  }
}
```

Multiple `BeanReference` annotations are allowed to define multiple bean
reference types:

```java
@BeanReference(RequireCode.class)
@BeanReference({ RequireYear.class, RequireMonth.class })
protected static class MyBean {
  // ...
}
```

### Bean references validation

For validating that a passed bean contains a valid reference annotation
`ValidBeanReference` is provided. This annotation validates that a valid
reference is provided. If the is an instance of `IdentityBean` and has a
identity same limitations of identity validations apply.

```java
void myMethod(
    @ValidBeanReference
    MyBean bean) {
  // ...
}
```

Utility class `BeanValidationUtils` provides method `isValidBeanReference()`
to check programmatically if a bean has a valid reference.

## Conversors

Library provides additional converters for `Apache Commons BeanUtils`:

- `IdentityConverter`: Converts `Identity` instances to `String` using identity token and back using `TokenIdentity`.
- `LocaleConverter`: Converts `java.util.Locale` instances.
- `InstantConverter`: Converts `java.time.Instant` instances.
- `YearConverter`: Converts `java.time.Year` instances.
- `YearMonthConverter`: Converts `java.time.YearMonth` instances.
- `MonthConverter`: Converts `java.time.Month` instances.
- `MonthDayConverter`: Converts `java.time.MonthDay` instances.
- `DayOfWeekConverter`: Converts `java.time.DayOfWeek` instances.
- `LocalDateConverter`: Converts `java.time.LocalDate` instances.
- `LocalDateTimeConverter`: Converts `java.time.LocaleDateTime` instances.
- `LocalTimeConverter`: Converts `java.time.LocalTime` instances.
- `OffsetDateTimeConverter`: Converts `java.time.OffsetDateTime` instances.
- `OffsetTimeConverter`: Converts `java.time.OffsetTime` instances.
- `ZonedDateTimeConverter`: Converts `java.time.ZonedDateTime` instances.
- `ZoneOffsetConverter`: Converts `java.time.ZoneOffset` instances.
- `DurationConverter`: Converts `java.time.Duration` instances.
- `PeriodConverter`: Converts `java.time.Period` instances.

Utility class `OrneBeansConverters` allows the bulk registration of those
additional converters through utility methods:

```java
OrneBeansConverters.register();
```

Bulk registration in custom instances of `ConvertUtilsBean` is also supported:

```java
ConvertUtilsBean converter = new ConvertUtilsBean();
// ...
OrneBeansConverters.register(converter);
```
