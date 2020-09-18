# :package: 0.3.0

01. :gift: Added interface `dev.orne.beans.WritableIdentityBean`
01. Improved class `BaseIdentityBean`
    01. :gift: Implements `WritableIdentityBean`
01. :gift: Added Apache `beanutils` converters
    01. :gift: Added class `dev.orne.beans.converters.DateConverter`
        01. :gift: Added to methods `OrneBeansConverters.registerUtilConversors(...)`
    01. :gift: Added class `dev.orne.beans.converters.GregorianCalendarConverter`
        01. :gift: Added to methods `OrneBeansConverters.registerUtilConversors(...)`
    01. :gift: Added class `dev.orne.beans.converters.UriConverter`
        01. :gift: Added to methods `OrneBeansConverters.registerNetConversors(...)`
    01. :gift: Added class `dev.orne.beans.converters.IdentityBeanConverter`
01. :gift: Added methods `registerNetConversors` to `dev.orne.beans.converters.OrneBeansConverters`

# :package: 0.2.0

01. Improved class `AbstractIdentity`
    01. :gift: Method `toString` returns identity token
    01. :gift: Generated identity token is cached
01. Improved class `AbstractSimpleIdentity`
    01. :gift: Added copy constructor
    01. :gift: Implemented `hashCode` and `equals`
01. Improved class `StringIdentity`
    01. :gift: Added copy constructor
01. Improved class `LongIdentity`
    01. :gift: Added copy constructor
01. Improved class `BigIntegerIdentity`
    01. :gift: Added copy constructor
01. Improved class `BeanValidationUtils`
    01. Added method `isValidBeanIdentity`
    01. Added method `isValidBeanReference`
01. :gift: Added class `dev.orne.beans.BaseIdentityBean`
01. :gift: Overloaded `OrneBeansConverters` methods with parameter `defaultToNull`

# :package: 0.1.0

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
    01. :gift: Added class `dev.orne.beans.converters.OrneBeansConverters`
    01. :gift: Added class `dev.orne.beans.converters.EnumConverter`
    01. :gift: Added class `dev.orne.beans.converters.LocaleConverter`
    01. :gift: Added class `dev.orne.beans.converters.IdentityConverter`
    01. :gift: Added class `dev.orne.beans.converters.AbstractDateTimeConverter`
    01. :gift: Added class `dev.orne.beans.converters.InstantConverter`
    01. :gift: Added class `dev.orne.beans.converters.DayOfWeekConverter`
    01. :gift: Added class `dev.orne.beans.converters.LocalDateConverter`
    01. :gift: Added class `dev.orne.beans.converters.LocalDateTimeConverter`
    01. :gift: Added class `dev.orne.beans.converters.LocalTimeConverter`
    01. :gift: Added class `dev.orne.beans.converters.MonthConverter`
    01. :gift: Added class `dev.orne.beans.converters.MonthDayConverter`
    01. :gift: Added class `dev.orne.beans.converters.OffsetDateTimeConverter`
    01. :gift: Added class `dev.orne.beans.converters.OffsetTimeConverter`
    01. :gift: Added class `dev.orne.beans.converters.YearConverter`
    01. :gift: Added class `dev.orne.beans.converters.YearMonthConverter`
    01. :gift: Added class `dev.orne.beans.converters.ZonedDateTimeConverter`
    01. :gift: Added class `dev.orne.beans.converters.ZoneOffsetConverter`
    01. :gift: Added class `dev.orne.beans.converters.DurationConverter`
    01. :gift: Added class `dev.orne.beans.converters.PeriodConverter`
