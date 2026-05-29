# Apache Commons BeanUtils converters

Library provides additional converters for [`Apache Commons BeanUtils`][apache beanutils]:

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

[apache beanutils]: https://commons.apache.org/proper/commons-beanutils/