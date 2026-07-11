# Orne Java bean utilities - Changelog

## 1.0.0 - *Unrealeased*

- **Requires Java 11 or newer.**

### Changed

- Change `javax.validation:validation-api:2.0.1.Final` optional dependency to `jakarta.validation:jakarta.validation-api:2.0.1`
- Change `javax.xml.bind:jaxb-api:2.3.1` optional dependency to `jakarta.xml.bind:jakarta.xml.bind-api:2.3.3`
- Change `BaseIdentityBean` copy constructor parameter type from `BaseIdentityBean` to `IdentityBean`.

### Added

- Add `dev.orne:orne-beans-jakarta` module with support for JakartaEE 9 and newer.

## 0.7.0 - 2026-05-10

- **Maven coordinates change.**

### Changed

- Change Maven coordinates from `dev.orne:beans` to `dev.orne:orne-beans`
- Bump `org.apache.commons:commons-lang3` to 3.20.0
- Bump `commons-beanutils:commons-beanutils` to 1.11.0
- Replace `dev.orne.test:generators` by `dev.orne.test:orne-test-generators`

### Added

- Add support for JSON-B 2.x based JSON serialization/deserialization of identities.
    - `dev.orne.beans.jsonb.JsonbIdentityAdapter` class.
    - `dev.orne.beans.jsonb.OrneBeansJsonbConfig` class.

## 0.6.0 - 2023-12-22

### Changed

- Bump `com.fasterxml.jackson.core:jackson-databind` 2.15.2.
- **Breaking:** Change `IdentityTokenFormatter` behavior.
    - Now accepts empty prefixes.
    - Default prefix is now empty.
    - Uses Base32 encoding to encode invalid token bodies.

### Added

- Added Identity token validation utility methods.
    - `IdentityTokenFormatter.isValidPrefix()` method.
    - `IdentityTokenFormatter.isValidBody()` method.
    - `IdentityTokenFormatter.isValidUncodedBody()` method.
    - `IdentityTokenFormatter.isValidEncodedBody()` method.
    - `IdentityTokenFormatter.isValidToken()` method.
- Support for identity token to identity type resolution.
    - `Identity.resolve(Class)` method.
- Add default validation messages for 'en', 'es', 'fr' and 'eu' languages.
- Support for `orne-generators` random identities and identity beans.
    - `dev.orne.beans.rnd.TokenIdentityGenerator` class.
    - `dev.orne.beans.rnd.BaseIdentityBeanGenerator` class.
- Add SPI based Jackson 2.x polymorphism.
    - `dev.orne.beans.JacksonSpiTypeIdResolver` class.

### Removed

- Remove `commons-codec:commons-codec` dependency.

## 0.5.2 - 2023-09-16

### Fixed

- Fix issue in identity token validation regular expression.

## 0.5.1 - 2023-09-10

### Fixed

- Fix Apache `beanutils` date-time and time converters.

    Unexpected `DateTimeFormatter.ISO_INSTANT` behavior change detected when
    executing test in Java 17.

    - `LocalDateTimeConverter`.
    - `LocalTimeConverter`.
    - `OffsetDateTimeConverter`.
    - `OffsetTimeConverter`.
    - `ZonedDateTimeConverter`.

## 0.5.0 - 2023-09-09

### Changed

- Bump `com.fasterxml.jackson.core:jackson-databind` 2.13.4.

### Added

- Add utility methods to default `Identity` implementations.
    - `LongIdentity.extractTokenValue(String, String)` method.
    - `LongIdentity.extractRequiredTokenValue(String, String)` method.
    - `BigIntegerIdentity.extractTokenValue(String, String)` method.
    - `BigIntegerIdentity.extractRequiredTokenValue(String, String)` method.
    - `StringIdentity.extractTokenValue(String, String)` method.
    - `StringIdentity.extractRequiredTokenValue(String, String)` method.
- Add support for composed identities.
    - `dev.orne.beans.AbstractComposedIdentity` class.

## 0.4.0 - 2022-10-12

### Changed

- Bump `com.fasterxml.jackson.core:jackson-databind` 2.12.6.1.
- **Breaking:** `UnrecognizedIdentityTokenException` now extends `IllegalArgumentException`.
- **Breaking:** `EnumConverter` is now singleton.

### Added

- Improved Apache `beanutils` enumeration converters.
    - `dev.orne.beans.converters.EnumConvertUtilsBean` class.

  Extends `org.apache.commons.beanutils.ConvertUtilsBean` but lookups for
  converter for `Enum` if no converter is found for concrete enumeration
  type.

    - `dev.orne.beans.converters.EnumConvertUtilsBean2` class.

  Extends `org.apache.commons.beanutils.ConvertUtilsBean2` but lookups for
  converter for `Enum` if no converter is found for concrete enumeration
  type.

- Improved identity beans validation annotations.
    - `dev.orne.beans.ValidIdentity` annotation.

## 0.3.0 - 2021-02-07

### Added

- Add writable identity bean abstraction.
    - `dev.orne.beans.WritableIdentityBean` interface.
    - `BaseIdentityBean` now implements `WritableIdentityBean`.
- Add additional Apache `beanutils` converters.
    - `dev.orne.beans.converters.DateConverter` class.
    - `dev.orne.beans.converters.GregorianCalendarConverter` class.
    - `dev.orne.beans.converters.UriConverter` class.
    - `dev.orne.beans.converters.IdentityBeanConverter` class.
    - `dev.orne.beans.converters.OrneBeansConverters.registerNetConversors(...)` methods.

## 0.2.0 - 2020-08-14

### Changed

- Bump `org.apache.commons:commons-lang3` to 3.10.
- Bump `com.fasterxml.jackson.core:jackson-databind` 2.11.0.
- Improve `Identity` implementations.
    - Now all leaf implementations hace copy constructors.
    - Implement `hashCode` and `equals`.
    - Method `toString()` now returns identity token.

### Added

- Add `dev.orne.beans.BaseIdentityBean` class.
- Extend identity bean validation support.
    - `BeanValidationUtils.isValidBeanIdentity()` method.
    - `BeanValidationUtils.isValidBeanReference()` method.
- Overload `OrneBeansConverters` methods with parameter `defaultToNull`.

## 0.1.0 - 2020-05-17

_First experimental release._

### Added

- Add dependency with `org.apache.commons:commons-lang3` 3.9.
- Add dependency with `commons-beanutils:commons-beanutils` 1.9.4.
- Add dependency with `commons-codec:commons-codec` 1.14.
- Add optional dependency with `javax.validation:validation-api` 2.0.1.Final.
- Add optional dependency with `com.fasterxml.jackson.core:jackson-databind` 2.9.9.
- Add abstraction mechanism for entity identities.
    - `dev.orne.beans.UnrecognizedIdentityTokenException` exception.
    - `dev.orne.beans.Identity` interface.
    - `dev.orne.beans.TokenIdentity` class.
    - `dev.orne.beans.AbstractIdentity` class.
    - `dev.orne.beans.AbstractSimpleIdentity` class.
    - `dev.orne.beans.StringIdentity` class.
    - `dev.orne.beans.LongIdentity` class.
    - `dev.orne.beans.BigIntegerIdentity` class.
    - `dev.orne.beans.IdentityTokenResolver` annotation.
    - `dev.orne.beans.IdentityResolver` class.
    - `dev.orne.beans.IdentityBean` interface.
- Add support for Jackson 2.x based JSON serialization/deserialization of identities.
- Add support for JAX-B 2.x based XML serialization/deserialization of identities.
- Add abstraction mechanism for entity references.
    - `dev.orne.beans.BeanReference` annotation.
- Add support for Java Validation 2.x based identity and identity beans validation.
    - `dev.orne.beans.ValidIdentityToken` annotation.
    - `dev.orne.beans.ValidIdentityTokenPrefix` annotation.
    - `dev.orne.beans.ValidBeanIdentity` annotation.
    - `dev.orne.beans.ValidBeanReference` annotation.
    - `dev.orne.beans.BeanValidationUtils` class.
- Add Apache `beanutils` converters.
    - `dev.orne.beans.converters.EnumConverter` class.
    - `dev.orne.beans.converters.LocaleConverter` class.
    - `dev.orne.beans.converters.IdentityConverter` class.
    - `dev.orne.beans.converters.AbstractDateTimeConverter` class.
    - `dev.orne.beans.converters.InstantConverter` class.
    - `dev.orne.beans.converters.DayOfWeekConverter` class.
    - `dev.orne.beans.converters.LocalDateConverter` class.
    - `dev.orne.beans.converters.LocalDateTimeConverter` class.
    - `dev.orne.beans.converters.LocalTimeConverter` class.
    - `dev.orne.beans.converters.MonthConverter` class.
    - `dev.orne.beans.converters.MonthDayConverter` class.
    - `dev.orne.beans.converters.OffsetDateTimeConverter` class.
    - `dev.orne.beans.converters.OffsetTimeConverter` class.
    - `dev.orne.beans.converters.YearConverter` class.
    - `dev.orne.beans.converters.YearMonthConverter` class.
    - `dev.orne.beans.converters.ZonedDateTimeConverter` class.
    - `dev.orne.beans.converters.ZoneOffsetConverter` class.
    - `dev.orne.beans.converters.DurationConverter` class.
    - `dev.orne.beans.converters.PeriodConverter` class.
    - `dev.orne.beans.converters.OrneBeansConverters` class.
