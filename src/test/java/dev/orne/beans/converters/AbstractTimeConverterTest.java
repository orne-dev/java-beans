package dev.orne.beans.converters;

/*-
 * #%L
 * Orne Beans
 * %%
 * Copyright (C) 2020 Orne Developments
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;

import javax.validation.constraints.NotNull;

import org.apache.commons.beanutils.Converter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Abstract class for `java.time` converters unit tests.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see AbstractDateTimeConverter
 */
abstract class AbstractTimeConverterTest
extends AbstractConverterTest {

    // Zoned values (13)
    protected static ZonedDateTime ZONED_DATE_TIME;
    protected static OffsetDateTime OFFSET_DATE_TIME;
    protected static LocalDateTime LOCAL_DATE_TIME;
    protected static LocalDate LOCAL_DATE;
    protected static Year YEAR;
    protected static YearMonth YEAR_MONTH;
    protected static MonthDay MONTH_DAY;
    protected static Month MONTH;
    protected static DayOfWeek DAY_OF_WEEK;
    protected static OffsetTime OFFSET_TIME;
    protected static LocalTime LOCAL_TIME;
    protected static ZoneId ZONE_ID;
    protected static ZoneOffset ZONE_OFFSET;
    // UTC values (13)
    protected static ZonedDateTime UTC_ZONED_DATE_TIME;
    protected static ZonedDateTime UTC_ZONED_OFFSET_DATE_TIME;
    protected static OffsetDateTime UTC_OFFSET_DATE_TIME;
    protected static LocalDateTime UTC_LOCAL_DATE_TIME;
    protected static LocalDate UTC_LOCAL_DATE;
    protected static Year UTC_YEAR;
    protected static YearMonth UTC_YEAR_MONTH;
    protected static MonthDay UTC_MONTH_DAY;
    protected static Month UTC_MONTH;
    protected static DayOfWeek UTC_DAY_OF_WEEK;
    protected static OffsetTime UTC_OFFSET_TIME;
    protected static LocalTime UTC_LOCAL_TIME;
    protected static ZoneId UTC_ZONE_ID;
    protected static ZoneOffset UTC_ZONE_OFFSET;
    // String values (17)
    protected static String STR_EMPTY = "";
    protected static String STR_NON_DATE = "not date";
    protected static String STR_ISO_INSTANT;
    protected static String STR_ISO_INSTANT_MILLIS;
    protected static String STR_ISO_ZONED_DATE_TIME;
    protected static String STR_ISO_ZONED_DATE_TIME_MILLIS;
    protected static String STR_ISO_OFFSET_DATE_TIME;
    protected static String STR_ISO_LOCAL_DATE_TIME;
    protected static String STR_UTC_ISO_LOCAL_DATE_TIME;
    protected static String STR_ISO_OFFSET_DATE;
    protected static String STR_ISO_LOCAL_DATE;
    protected static String STR_ISO_BASIC_DATE;
    protected static String STR_ISO_ORDINAL_DATE;
    protected static String STR_ISO_WEEK_DATE;
    protected static String STR_ISO_YEAR_MONTH;
    protected static String STR_ISO_MONTH_DAY;
    protected static String STR_ISO_OFFSET_TIME;
    protected static String STR_ISO_LOCAL_TIME;
    protected static String STR_UTC_ISO_LOCAL_TIME;
    protected static String STR_RFC_1123_DATE_TIME;
    protected static String STR_EPOCH_MILLIS;
    // Other values (4)
    protected static Instant INSTANT;
    protected static Instant UTC_INSTANT;
    protected static Long EPOCH_MILLIS;
    protected static Object WRONG_TYPE_VALUE = new Object();

    protected AbstractTimeConverterTest(
            final @NotNull Class<? extends TemporalAccessor> targetType,
            final @NotNull AbstractDateTimeConverter converter) {
        super(targetType, converter);
    }

    @BeforeAll
    public static void createTestValues() {
        // Zoned values
        ZONED_DATE_TIME = ZonedDateTime.now();
        OFFSET_DATE_TIME = OffsetDateTime.from(ZONED_DATE_TIME);
        LOCAL_DATE_TIME = LocalDateTime.from(ZONED_DATE_TIME);
        LOCAL_DATE = LocalDate.from(ZONED_DATE_TIME);
        YEAR = Year.from(ZONED_DATE_TIME);
        YEAR_MONTH = YearMonth.from(ZONED_DATE_TIME);
        MONTH_DAY = MonthDay.from(ZONED_DATE_TIME);
        MONTH = Month.from(ZONED_DATE_TIME);
        DAY_OF_WEEK = DayOfWeek.from(ZONED_DATE_TIME);
        OFFSET_TIME = OffsetTime.from(ZONED_DATE_TIME);
        LOCAL_TIME = LocalTime.from(ZONED_DATE_TIME);
        ZONE_ID = ZoneId.from(ZONED_DATE_TIME);
        ZONE_OFFSET = ZoneOffset.from(ZONED_DATE_TIME);
        // Absolute values
        INSTANT = Instant.from(ZONED_DATE_TIME);
        EPOCH_MILLIS = INSTANT.toEpochMilli();
        STR_EPOCH_MILLIS = String.valueOf(EPOCH_MILLIS);
        UTC_INSTANT = LOCAL_DATE_TIME.toInstant(ZoneOffset.UTC);
        // UTC values
        UTC_ZONED_DATE_TIME = INSTANT.atZone(ZoneOffset.UTC);
        UTC_ZONED_OFFSET_DATE_TIME = OFFSET_DATE_TIME.toZonedDateTime();
        UTC_OFFSET_DATE_TIME = OffsetDateTime.from(UTC_ZONED_DATE_TIME);
        UTC_LOCAL_DATE_TIME = LocalDateTime.from(UTC_ZONED_DATE_TIME);
        UTC_LOCAL_DATE = LocalDate.from(UTC_ZONED_DATE_TIME);
        UTC_YEAR = Year.from(UTC_ZONED_DATE_TIME);
        UTC_YEAR_MONTH = YearMonth.from(UTC_ZONED_DATE_TIME);
        UTC_MONTH_DAY = MonthDay.from(UTC_ZONED_DATE_TIME);
        UTC_MONTH = Month.from(UTC_ZONED_DATE_TIME);
        UTC_DAY_OF_WEEK = DayOfWeek.from(UTC_ZONED_DATE_TIME);
        UTC_OFFSET_TIME = OffsetTime.from(UTC_ZONED_DATE_TIME);
        UTC_LOCAL_TIME = LocalTime.from(UTC_ZONED_DATE_TIME);
        UTC_ZONE_ID = ZoneId.from(UTC_ZONED_DATE_TIME);
        UTC_ZONE_OFFSET = ZoneOffset.from(UTC_ZONED_DATE_TIME);
        // Zoned values as string
        STR_ISO_INSTANT = DateTimeFormatter.ISO_INSTANT.format(ZONED_DATE_TIME);
        STR_ISO_INSTANT_MILLIS = DateTimeFormatter.ISO_INSTANT.format(
                ZONED_DATE_TIME.truncatedTo(ChronoUnit.MILLIS));
        STR_ISO_ZONED_DATE_TIME = DateTimeFormatter.ISO_ZONED_DATE_TIME.format(ZONED_DATE_TIME);
        STR_ISO_ZONED_DATE_TIME_MILLIS = DateTimeFormatter.ISO_ZONED_DATE_TIME.format(
                ZONED_DATE_TIME.truncatedTo(ChronoUnit.MILLIS));
        STR_ISO_OFFSET_DATE_TIME = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(ZONED_DATE_TIME);
        STR_ISO_LOCAL_DATE_TIME = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(ZONED_DATE_TIME);
        STR_UTC_ISO_LOCAL_DATE_TIME = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(UTC_ZONED_DATE_TIME);
        STR_ISO_OFFSET_DATE = DateTimeFormatter.ISO_OFFSET_DATE.format(ZONED_DATE_TIME);
        STR_ISO_LOCAL_DATE = DateTimeFormatter.ISO_LOCAL_DATE.format(ZONED_DATE_TIME);
        STR_ISO_BASIC_DATE = DateTimeFormatter.BASIC_ISO_DATE.format(ZONED_DATE_TIME);
        STR_ISO_ORDINAL_DATE = DateTimeFormatter.ISO_ORDINAL_DATE.format(ZONED_DATE_TIME);
        STR_ISO_WEEK_DATE = DateTimeFormatter.ISO_WEEK_DATE.format(ZONED_DATE_TIME);
        STR_ISO_YEAR_MONTH = YEAR_MONTH.toString();
        STR_ISO_MONTH_DAY = MONTH_DAY.toString();
        STR_ISO_OFFSET_TIME = DateTimeFormatter.ISO_OFFSET_TIME.format(ZONED_DATE_TIME);
        STR_ISO_LOCAL_TIME = DateTimeFormatter.ISO_LOCAL_TIME.format(ZONED_DATE_TIME);
        STR_UTC_ISO_LOCAL_TIME = DateTimeFormatter.ISO_LOCAL_TIME.format(UTC_ZONED_DATE_TIME);
        STR_RFC_1123_DATE_TIME = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZONED_DATE_TIME);
    }

    protected void assertConstructor(
            final @NotNull AbstractDateTimeConverter converter,
            final @NotNull DateTimeFormatter formatter,
            final @NotNull DateTimeFormatter... parsers) {
        assertNotNull(converter.getFormatter());
        assertEquals(formatter, converter.getFormatter());
        assertNotNull(converter.getParsers());
        for (final DateTimeFormatter parser : parsers) {
            assertTrue(converter.getParsers().contains(parser));
        }
    }

    /**
     * Test {@link LocalTimeConverter#getDefaultType()}.
     */
    @Test
    void testDefaultType() {
        final Class<?> defaultType = ((AbstractDateTimeConverter) this.converter).getDefaultType();
        assertNotNull(defaultType);
        assertEquals(this.targetType, defaultType);
    }

    protected void assertFail(
            final Object value) {
        super.assertFail(value);
        assertFail(this.converter, UnimplementedTemporal.class, value);
    }

    protected void assertSuccess(
            final Object value,
            final Object expectedResult) {
        super.assertSuccess(value, expectedResult);
        assertFail(this.converter, UnimplementedTemporal.class, value);
    }

    protected void assertSuccess(
            final @NotNull Converter converter,
            final Object value,
            final Object expectedResult,
            final Object defaultValue) {
        super.assertSuccess(converter, value, expectedResult, defaultValue);
        assertSuccess(converter, UnimplementedTemporal.class, value, defaultValue);
    }

    /**
     * Mock extension of {@code TemporalAccessor} for testing.
     */
    private static interface UnimplementedTemporal
    extends TemporalAccessor {
        // No extra methods
    }
}
