# :package: 0.6.0

01. :gift: Added `orne-generators` support for identities and identity beans.

# :package: 0.5.2 (2023-09-16)

01. :beetle: Fixed issue in identity token validation regular expression.

# :package: 0.5.1 (2023-09-10)

01. :beetle: Fixed Apache `beanutils` date-time and time converters.
    Unexpected `DateTimeFormatter.ISO_INSTANT` behavior change detected when executing test in Java 17.
    01. Fixed `dev.orne.beans.converters.LocalDateTimeConverter`.
    01. Fixed `dev.orne.beans.converters.LocalTimeConverter`.
    01. Fixed `dev.orne.beans.converters.OffsetDateTimeConverter`.
    01. Fixed `dev.orne.beans.converters.OffsetTimeConverter`.
    01. Fixed `dev.orne.beans.converters.ZonedDateTimeConverter`.

# :package: 0.5.0 (2023-09-09)

01. :gift: Added utility methods to default `Identity` implementations.
    01. Added `dev.orne.beans.LongIdentity.extractTokenValue(String, String)` method.
    01. Added `dev.orne.beans.LongIdentity.extractRequiredTokenValue(String, String)` method.
    01. Added `dev.orne.beans.BigIntegerIdentity.extractTokenValue(String, String)` method.
    01. Added `dev.orne.beans.BigIntegerIdentity.extractRequiredTokenValue(String, String)` method.
    01. Added `dev.orne.beans.StringIdentity.extractTokenValue(String, String)` method.
    01. Added `dev.orne.beans.StringIdentity.extractRequiredTokenValue(String, String)` method.
01. :gift: Added class `dev.orne.beans.AbstractComposedIdentity`.

# :package: 0.4.0 (2022-10-12)

01. :boom: Changed `dev.orne.beans.UnrecognizedIdentityTokenException` hierarchy.
    Extends `IllegalArgumentException` now.
01. :gift: Improved Apache `beanutils` enumerations converter
    01. :boom: Removed generic nature of `EnumConverter` and exposed as singleton instance
    01. Added class `dev.orne.beans.converters.EnumTypedConverter`
        01. Added to method `OrneBeansConverters.registerBeansConversors(ConvertUtilsBean, ...)`
    01. Added class `dev.orne.beans.converters.EnumConvertUtilsBean`
        Extends `org.apache.commons.beanutils.ConvertUtilsBean` but lookups for
        converter for `Enum` if no converter is found for concrete enumeration
        type.
    01. Added class `dev.orne.beans.converters.EnumConvertUtilsBean2`
        Extends `org.apache.commons.beanutils.ConvertUtilsBean2` but lookups for
        converter for `Enum` if no converter is found for concrete enumeration
        type.
01. :gift: Added annotation `dev.orne.beans.ValidIdentity`
    01. Added class `dev.orne.beans.ValidIdentity.ValidIdentityValidator`
    01. Added class `dev.orne.beans.ValidIdentity.ValidIdentityValidatorForString`

# :package: 0.3.0 (2021-02-07)

01. :gift: Added interface `dev.orne.beans.WritableIdentityBean`
01. :gift: Improved class `BaseIdentityBean`
    01. Implements `WritableIdentityBean`
01. :gift: Added Apache `beanutils` converters
    01. Added class `dev.orne.beans.converters.DateConverter`
        01. Added to methods `OrneBeansConverters.registerUtilConversors(...)`
    01. Added class `dev.orne.beans.converters.GregorianCalendarConverter`
        01. Added to methods `OrneBeansConverters.registerUtilConversors(...)`
    01. Added class `dev.orne.beans.converters.UriConverter`
        01. Added to methods `OrneBeansConverters.registerNetConversors(...)`
    01. Added class `dev.orne.beans.converters.IdentityBeanConverter`
01. :gift: Added methods `registerNetConversors` to `dev.orne.beans.converters.OrneBeansConverters`

# :package: 0.2.0 (2020-08-14)

01. :gift: Improved class `AbstractIdentity`
    01. Method `toString` returns identity token
    01. Generated identity token is cached
01. :gift: Improved class `AbstractSimpleIdentity`
    01. Added copy constructor
    01. Implemented `hashCode` and `equals`
01. :gift: Improved class `StringIdentity`
    01. Added copy constructor
01. :gift: Improved class `LongIdentity`
    01. Added copy constructor
01. :gift: Improved class `BigIntegerIdentity`
    01. Added copy constructor
01. :gift: Improved class `BeanValidationUtils`
    01. Added method `isValidBeanIdentity`
    01. Added method `isValidBeanReference`
01. :gift: Added class `dev.orne.beans.BaseIdentityBean`
01. :gift: Overloaded `OrneBeansConverters` methods with parameter `defaultToNull`

# :package: 0.1.0 (2020-05-17)

01. :gift: Added exception `dev.orne.beans.UnrecognizedIdentityTokenException`
01. :gift: Added class `dev.orne.beans.IdentityTokenFormatter`
01. :gift: Added annotation `dev.orne.beans.ValidIdentityToken`
01. :gift: Added annotation `dev.orne.beans.ValidIdentityTokenPrefix`
01. :gift: Added interface `dev.orne.beans.Identity`
01. :gift: Added class `dev.orne.beans.TokenIdentity`
01. :gift: Added class `dev.orne.beans.AbstractIdentity`
01. :gift: Added class `dev.orne.beans.AbstractSimpleIdentity`
01. :gift: Added class `dev.orne.beans.StringIdentity`
01. :gift: Added class `dev.orne.beans.LongIdentity`
01. :gift: Added class `dev.orne.beans.BigIntegerIdentity`
01. :gift: Added annotation `dev.orne.beans.IdentityTokenResolver`
01. :gift: Added class `dev.orne.beans.IdentityResolver`
01. :gift: Added interface `dev.orne.beans.IdentityBean`
01. :gift: Added annotation `dev.orne.beans.BeanReference`
01. :gift: Added annotation `dev.orne.beans.ValidBeanIdentity`
    01. Added class `dev.orne.beans.ValidBeanIdentity.ValidBeanIdentityValidator`
01. :gift: Added annotation `dev.orne.beans.ValidBeanReference`
    01. Added class `dev.orne.beans.ValidBeanReference.ValidBeanReferenceValidator`
01. :gift: Added class `dev.orne.beans.BeanValidationUtils`
01. :gift: Added class `dev.orne.beans.BeanAnnotationFinder`
01. :gift: Added Apache `beanutils` converters
    01. Added class `dev.orne.beans.converters.OrneBeansConverters`
    01. Added class `dev.orne.beans.converters.EnumConverter`
    01. Added class `dev.orne.beans.converters.LocaleConverter`
    01. Added class `dev.orne.beans.converters.IdentityConverter`
    01. Added class `dev.orne.beans.converters.AbstractDateTimeConverter`
    01. Added class `dev.orne.beans.converters.InstantConverter`
    01. Added class `dev.orne.beans.converters.DayOfWeekConverter`
    01. Added class `dev.orne.beans.converters.LocalDateConverter`
    01. Added class `dev.orne.beans.converters.LocalDateTimeConverter`
    01. Added class `dev.orne.beans.converters.LocalTimeConverter`
    01. Added class `dev.orne.beans.converters.MonthConverter`
    01. Added class `dev.orne.beans.converters.MonthDayConverter`
    01. Added class `dev.orne.beans.converters.OffsetDateTimeConverter`
    01. Added class `dev.orne.beans.converters.OffsetTimeConverter`
    01. Added class `dev.orne.beans.converters.YearConverter`
    01. Added class `dev.orne.beans.converters.YearMonthConverter`
    01. Added class `dev.orne.beans.converters.ZonedDateTimeConverter`
    01. Added class `dev.orne.beans.converters.ZoneOffsetConverter`
    01. Added class `dev.orne.beans.converters.DurationConverter`
    01. Added class `dev.orne.beans.converters.PeriodConverter`
