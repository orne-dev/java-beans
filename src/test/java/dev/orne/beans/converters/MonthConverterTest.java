/**
 * 
 */
package dev.orne.beans.converters;

/*-
 * #%L
 * Orne Beans
 * %%
 * Copyright (C) 2020 Orne Developments
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import static org.junit.jupiter.api.Assertions.*;

import java.time.Month;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code MonthConverter}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see MonthConverter
 */
@Tag("ut")
public class MonthConverterTest
extends AbstractTimeConverterTest {

    public MonthConverterTest() {
        super(Month.class, new MonthConverter());
    }

    /**
     * Test {@link MonthConverter#MonthConverter()}.
     */
    @Test
    public void testConstructor() {
        final MonthConverter converter = new MonthConverter();
        assertConstructor(converter,
                MonthConverter.BY_VALUE_PARSER,
                MonthConverter.BY_VALUE_PARSER,
                MonthConverter.BY_FULL_TEXT_PARSER,
                MonthConverter.BY_SHORT_TEXT_PARSER,
                MonthConverter.BY_NARROW_TEXT_PARSER,
                YearMonthConverter.ISO_8601_PARSER,
                YearMonthConverter.BY_FULL_TEXT_PARSER,
                YearMonthConverter.BY_SHORT_TEXT_PARSER,
                YearMonthConverter.BY_NARROW_TEXT_PARSER,
                MonthDayConverter.ISO_8601_PARSER,
                MonthDayConverter.BY_FULL_TEXT_PARSER,
                MonthDayConverter.BY_SHORT_TEXT_PARSER,
                MonthDayConverter.BY_NARROW_TEXT_PARSER,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_DATE_TIME,
                DateTimeFormatter.ISO_DATE,
                DateTimeFormatter.ISO_ORDINAL_DATE,
                DateTimeFormatter.ISO_WEEK_DATE,
                DateTimeFormatter.BASIC_ISO_DATE,
                InstantConverter.EPOCH_MILLIS_PARSER);
    }

    /**
     * Test {@link MonthConverter#MonthConverter(Month)}.
     */
    @Test
    public void testConstructorDefaultValue() {
        final MonthConverter converter = new MonthConverter(
                (Month) null);
        assertConstructor(converter,
                MonthConverter.BY_VALUE_PARSER,
                MonthConverter.BY_VALUE_PARSER,
                MonthConverter.BY_FULL_TEXT_PARSER,
                MonthConverter.BY_SHORT_TEXT_PARSER,
                MonthConverter.BY_NARROW_TEXT_PARSER,
                YearMonthConverter.ISO_8601_PARSER,
                YearMonthConverter.BY_FULL_TEXT_PARSER,
                YearMonthConverter.BY_SHORT_TEXT_PARSER,
                YearMonthConverter.BY_NARROW_TEXT_PARSER,
                MonthDayConverter.ISO_8601_PARSER,
                MonthDayConverter.BY_FULL_TEXT_PARSER,
                MonthDayConverter.BY_SHORT_TEXT_PARSER,
                MonthDayConverter.BY_NARROW_TEXT_PARSER,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_DATE_TIME,
                DateTimeFormatter.ISO_DATE,
                DateTimeFormatter.ISO_ORDINAL_DATE,
                DateTimeFormatter.ISO_WEEK_DATE,
                DateTimeFormatter.BASIC_ISO_DATE,
                InstantConverter.EPOCH_MILLIS_PARSER);
    }

    /**
     * Test {@link MonthConverter#MonthConverter(DateTimeFormatter)}.
     */
    @Test
    public void testConstructorFormatter() {
        final MonthConverter converter = new MonthConverter(
                DateTimeFormatter.RFC_1123_DATE_TIME);
        assertConstructor(converter,
                DateTimeFormatter.RFC_1123_DATE_TIME,
                MonthConverter.BY_VALUE_PARSER,
                MonthConverter.BY_FULL_TEXT_PARSER,
                MonthConverter.BY_SHORT_TEXT_PARSER,
                MonthConverter.BY_NARROW_TEXT_PARSER,
                YearMonthConverter.ISO_8601_PARSER,
                YearMonthConverter.BY_FULL_TEXT_PARSER,
                YearMonthConverter.BY_SHORT_TEXT_PARSER,
                YearMonthConverter.BY_NARROW_TEXT_PARSER,
                MonthDayConverter.ISO_8601_PARSER,
                MonthDayConverter.BY_FULL_TEXT_PARSER,
                MonthDayConverter.BY_SHORT_TEXT_PARSER,
                MonthDayConverter.BY_NARROW_TEXT_PARSER,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_DATE_TIME,
                DateTimeFormatter.ISO_DATE,
                DateTimeFormatter.ISO_ORDINAL_DATE,
                DateTimeFormatter.ISO_WEEK_DATE,
                DateTimeFormatter.BASIC_ISO_DATE,
                InstantConverter.EPOCH_MILLIS_PARSER);
    }

    /**
     * Test {@link MonthConverter#MonthConverter(DateTimeFormatter, Month)}.
     */
    @Test
    public void testConstructorFormatterDefaultValue() {
        final MonthConverter converter = new MonthConverter(
                DateTimeFormatter.RFC_1123_DATE_TIME,
                (Month) null);
        assertConstructor(converter,
                DateTimeFormatter.RFC_1123_DATE_TIME,
                MonthConverter.BY_VALUE_PARSER,
                MonthConverter.BY_FULL_TEXT_PARSER,
                MonthConverter.BY_SHORT_TEXT_PARSER,
                MonthConverter.BY_NARROW_TEXT_PARSER,
                YearMonthConverter.ISO_8601_PARSER,
                YearMonthConverter.BY_FULL_TEXT_PARSER,
                YearMonthConverter.BY_SHORT_TEXT_PARSER,
                YearMonthConverter.BY_NARROW_TEXT_PARSER,
                MonthDayConverter.ISO_8601_PARSER,
                MonthDayConverter.BY_FULL_TEXT_PARSER,
                MonthDayConverter.BY_SHORT_TEXT_PARSER,
                MonthDayConverter.BY_NARROW_TEXT_PARSER,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_DATE_TIME,
                DateTimeFormatter.ISO_DATE,
                DateTimeFormatter.ISO_ORDINAL_DATE,
                DateTimeFormatter.ISO_WEEK_DATE,
                DateTimeFormatter.BASIC_ISO_DATE,
                InstantConverter.EPOCH_MILLIS_PARSER);
    }

    /**
     * Test {@link MonthConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    public void testFromValueInvalidConversions() {
        assertFail(null);
        assertFail(YEAR);
        assertFail(DAY_OF_WEEK);
        assertFail(OFFSET_TIME);
        assertFail(LOCAL_TIME);
        assertFail(ZONE_ID);
        assertFail(ZONE_OFFSET);
        assertFail(WRONG_TYPE_VALUE);
    }

    /**
     * Test {@link MonthConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    public void testFromValueInvalidConversionsWithDefaultValue() {
        final Month defaultValue = null;
        final MonthConverter converter = new MonthConverter(defaultValue);
        assertSuccess(converter, (Object) null, defaultValue, defaultValue);
        assertSuccess(converter, YEAR, defaultValue, defaultValue);
        assertSuccess(converter, DAY_OF_WEEK, defaultValue, defaultValue);
        assertSuccess(converter, OFFSET_TIME, defaultValue, defaultValue);
        assertSuccess(converter, LOCAL_TIME, defaultValue, defaultValue);
        assertSuccess(converter, ZONE_ID, defaultValue, defaultValue);
        assertSuccess(converter, ZONE_OFFSET, defaultValue, defaultValue);
        assertSuccess(converter, WRONG_TYPE_VALUE, defaultValue, defaultValue);
    }

    /**
     * Test {@link MonthConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is a {@code LocalTime}
     * ISO-8601 representation.
     */
    @Test
    public void testFromValueValidConversions() {
        assertSuccess(ZONED_DATE_TIME, MONTH);
        assertSuccess(OFFSET_DATE_TIME, MONTH);
        assertSuccess(LOCAL_DATE_TIME, MONTH);
        assertSuccess(LOCAL_DATE, MONTH);
        assertSuccess(YEAR_MONTH, MONTH);
        assertSuccess(MONTH_DAY, MONTH);
        assertSuccess(MONTH, MONTH);
        assertSuccess(INSTANT, UTC_MONTH);
        assertSuccess(EPOCH_MILLIS, UTC_MONTH);
        assertSuccess(MONTH.getValue(), MONTH);
    }

    /**
     * Test {@link MonthConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    public void testFromStringInvalidConversions() {
        assertFail(STR_EMPTY);
        assertFail(STR_NON_DATE);
        assertFail(STR_ISO_OFFSET_TIME);
        assertFail(STR_ISO_LOCAL_TIME);
        assertFail(STR_RFC_1123_DATE_TIME);
    }

    /**
     * Test {@link MonthConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    public void testFromStringInvalidConversionsWithDefaultValue() {
        final Month defaultValue = null;
        final MonthConverter converter = new MonthConverter(defaultValue);
        assertSuccess(converter, STR_EMPTY, defaultValue, defaultValue);
        assertSuccess(converter, STR_NON_DATE, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_OFFSET_TIME, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_LOCAL_TIME, defaultValue, defaultValue);
        assertSuccess(converter, STR_RFC_1123_DATE_TIME, defaultValue, defaultValue);
    }

    /**
     * Test {@link MonthConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is a {@code LocalTime}
     * ISO-8601 representation.
     */
    @Test
    public void testFromStringValidConversions() {
        assertSuccess(STR_ISO_ZONED_DATE_TIME, MONTH);
        assertSuccess(STR_ISO_OFFSET_DATE_TIME, MONTH);
        assertSuccess(STR_ISO_LOCAL_DATE_TIME, MONTH);
        assertSuccess(STR_ISO_OFFSET_DATE, MONTH);
        assertSuccess(STR_ISO_LOCAL_DATE, MONTH);
        assertSuccess(STR_ISO_BASIC_DATE, MONTH);
        assertSuccess(STR_ISO_ORDINAL_DATE, MONTH);
        assertSuccess(STR_ISO_WEEK_DATE, MONTH);
        assertSuccess(STR_ISO_YEAR_MONTH, MONTH);
        assertSuccess(STR_ISO_MONTH_DAY, MONTH);
        assertSuccess(STR_ISO_INSTANT, UTC_MONTH);
        assertSuccess(STR_EPOCH_MILLIS, UTC_MONTH);
        assertSuccess(MONTH.name(), MONTH);
    }

    /**
     * Test {@link MonthConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is a {@code LocalTime}
     * ISO-8601 representation.
     */
    @Test
    public void testValidToStringConversions() {
        final MonthConverter converter = new MonthConverter();
        assertNull(converter.convert(String.class, null));
        assertSuccess(converter, String.class, "", "");
        assertSuccess(converter, String.class, STR_NON_DATE, STR_NON_DATE);
        assertSuccess(converter, String.class, MONTH, MONTH.name());
    }
}
