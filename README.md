# Orne Bean utilities

Provides utilities for implementation and management of POJO Java beans.

## Status

[![License][status.license.badge]][status.license]
[![Latest version][status.maven.badge]][status.maven]
[![Javadoc][status.javadoc.badge]][javadoc]
[![Maven site][status.site.badge]][site]

| Latest Release | Develop |
| :------------: | :-------------: |
| [![Build Status][status.latest.ci.badge]][status.latest.ci] | [![Build Status][status.dev.ci.badge]][status.dev.ci] |
| [![Coverage][status.latest.cov.badge]][status.latest.cov] | [![Coverage][status.dev.cov.badge]][status.dev.cov] |

## Usage

The binaries can be obtained from [Maven Central][status.maven] with the
`dev.orne:beans` coordinates:

```xml
<dependency>
  <groupId>dev.orne</groupId>
  <artifactId>beans</artifactId>
  <version>0.5.0</version>
</dependency>
```

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
- `UriConverter`: Converts `java.net.URI` instances.
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

## Further information

For further information refer to the [Javadoc][javadoc]
and [Maven Site][site].

[site]: https://orne-dev.github.io/java-beans/
[javadoc]: https://javadoc.io/doc/dev.orne/beans
[status.license]: http://www.gnu.org/licenses/gpl-3.0.txt
[status.license.badge]: https://img.shields.io/github/license/orne-dev/java-beans
[status.maven]: https://search.maven.org/artifact/dev.orne/beans
[status.maven.badge]: https://img.shields.io/maven-central/v/dev.orne/beans.svg?label=Maven%20Central
[status.javadoc.badge]: https://javadoc.io/badge2/dev.orne/beans/javadoc.svg
[status.site.badge]: https://img.shields.io/website?url=https%3A%2F%2Forne-dev.github.io%2Fjava-beans%2F
[status.latest.ci]: https://github.com/orne-dev/java-beans/actions/workflows/release.yml
[status.latest.ci.badge]: https://github.com/orne-dev/java-beans/actions/workflows/release.yml/badge.svg?branch=master
[status.latest.cov]: https://sonarcloud.io/dashboard?id=orne-dev_java-beans
[status.latest.cov.badge]: https://sonarcloud.io/api/project_badges/measure?project=orne-dev_java-beans&metric=coverage
[status.dev.ci]: https://github.com/orne-dev/java-beans/actions/workflows/build.yml
[status.dev.ci.badge]: https://github.com/orne-dev/java-beans/actions/workflows/build.yml/badge.svg?branch=develop
[status.dev.cov]: https://sonarcloud.io/dashboard?id=orne-dev_java-beans&branch=develop
[status.dev.cov.badge]: https://sonarcloud.io/api/project_badges/measure?project=orne-dev_java-beans&metric=coverage&branch=develop
