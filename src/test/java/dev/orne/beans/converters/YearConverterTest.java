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

import java.time.Year;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code YearConverter}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see YearConverter
 */
@Tag("ut")
class YearConverterTest
extends AbstractTimeConverterTest {

    public YearConverterTest() {
        super(Year.class, new YearConverter());
    }

    /**
     * Test {@link YearConverter#YearConverter()}.
     */
    @Test
    void testConstructor() {
        final YearConverter converter = new YearConverter();
        assertConstructor(converter,
                YearConverter.BY_VALUE_PARSER,
                YearConverter.BY_VALUE_PARSER,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_DATE_TIME,
                DateTimeFormatter.ISO_DATE,
                YearMonthConverter.ISO_8601_PARSER,
                YearMonthConverter.BY_FULL_TEXT_PARSER,
                YearMonthConverter.BY_SHORT_TEXT_PARSER,
                YearMonthConverter.BY_NARROW_TEXT_PARSER,
                DateTimeFormatter.ISO_ORDINAL_DATE,
                DateTimeFormatter.ISO_WEEK_DATE,
                DateTimeFormatter.BASIC_ISO_DATE,
                InstantConverter.EPOCH_MILLIS_PARSER);
    }

    /**
     * Test {@link YearConverter#YearConverter(Year)}.
     */
    @Test
    void testConstructorDefaultValue() {
        final YearConverter converter = new YearConverter(
                (Year) null);
        assertConstructor(converter,
                YearConverter.BY_VALUE_PARSER,
                YearConverter.BY_VALUE_PARSER,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_DATE_TIME,
                DateTimeFormatter.ISO_DATE,
                YearMonthConverter.ISO_8601_PARSER,
                YearMonthConverter.BY_FULL_TEXT_PARSER,
                YearMonthConverter.BY_SHORT_TEXT_PARSER,
                YearMonthConverter.BY_NARROW_TEXT_PARSER,
                DateTimeFormatter.ISO_ORDINAL_DATE,
                DateTimeFormatter.ISO_WEEK_DATE,
                DateTimeFormatter.BASIC_ISO_DATE,
                InstantConverter.EPOCH_MILLIS_PARSER);
    }

    /**
     * Test {@link YearConverter#YearConverter(DateTimeFormatter)}.
     */
    @Test
    void testConstructorFormatter() {
        final YearConverter converter = new YearConverter(
                DateTimeFormatter.RFC_1123_DATE_TIME);
        assertConstructor(converter,
                DateTimeFormatter.RFC_1123_DATE_TIME,
                YearConverter.BY_VALUE_PARSER,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_DATE_TIME,
                DateTimeFormatter.ISO_DATE,
                YearMonthConverter.ISO_8601_PARSER,
                YearMonthConverter.BY_FULL_TEXT_PARSER,
                YearMonthConverter.BY_SHORT_TEXT_PARSER,
                YearMonthConverter.BY_NARROW_TEXT_PARSER,
                DateTimeFormatter.ISO_ORDINAL_DATE,
                DateTimeFormatter.ISO_WEEK_DATE,
                DateTimeFormatter.BASIC_ISO_DATE,
                InstantConverter.EPOCH_MILLIS_PARSER);
    }

    /**
     * Test {@link YearConverter#YearConverter(DateTimeFormatter, Year)}.
     */
    @Test
    void testConstructorFormatterDefaultValue() {
        final YearConverter converter = new YearConverter(
                DateTimeFormatter.RFC_1123_DATE_TIME,
                (Year) null);
        assertConstructor(converter,
                DateTimeFormatter.RFC_1123_DATE_TIME,
                YearConverter.BY_VALUE_PARSER,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_DATE_TIME,
                DateTimeFormatter.ISO_DATE,
                YearMonthConverter.ISO_8601_PARSER,
                YearMonthConverter.BY_FULL_TEXT_PARSER,
                YearMonthConverter.BY_SHORT_TEXT_PARSER,
                YearMonthConverter.BY_NARROW_TEXT_PARSER,
                DateTimeFormatter.ISO_ORDINAL_DATE,
                DateTimeFormatter.ISO_WEEK_DATE,
                DateTimeFormatter.BASIC_ISO_DATE,
                InstantConverter.EPOCH_MILLIS_PARSER);
    }

    /**
     * Test {@link YearConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    void testFromValueInvalidConversions() {
        assertFail(null);
        assertFail(MONTH);
        assertFail(MONTH_DAY);
        assertFail(DAY_OF_WEEK);
        assertFail(OFFSET_TIME);
        assertFail(LOCAL_TIME);
        assertFail(ZONE_ID);
        assertFail(ZONE_OFFSET);
        assertFail(WRONG_TYPE_VALUE);
    }

    /**
     * Test {@link YearConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    void testFromValueInvalidConversionsWithDefaultValue() {
        final Year defaultValue = null;
        final YearConverter converter = new YearConverter(defaultValue);
        assertSuccess(converter, (Object) null, defaultValue, defaultValue);
        assertSuccess(converter, MONTH, defaultValue, defaultValue);
        assertSuccess(converter, MONTH_DAY, defaultValue, defaultValue);
        assertSuccess(converter, DAY_OF_WEEK, defaultValue, defaultValue);
        assertSuccess(converter, OFFSET_TIME, defaultValue, defaultValue);
        assertSuccess(converter, LOCAL_TIME, defaultValue, defaultValue);
        assertSuccess(converter, ZONE_ID, defaultValue, defaultValue);
        assertSuccess(converter, ZONE_OFFSET, defaultValue, defaultValue);
        assertSuccess(converter, WRONG_TYPE_VALUE, defaultValue, defaultValue);
    }

    /**
     * Test {@link YearConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is a {@code LocalTime}
     * ISO-8601 representation.
     */
    @Test
    void testFromValueValidConversions() {
        assertSuccess(ZONED_DATE_TIME, YEAR);
        assertSuccess(OFFSET_DATE_TIME, YEAR);
        assertSuccess(LOCAL_DATE_TIME, YEAR);
        assertSuccess(LOCAL_DATE, YEAR);
        assertSuccess(YEAR_MONTH, YEAR);
        assertSuccess(YEAR, YEAR);
        assertSuccess(INSTANT, UTC_YEAR);
        assertSuccess(EPOCH_MILLIS, UTC_YEAR);
        assertSuccess(YEAR.getValue(), YEAR);
    }

    /**
     * Test {@link YearConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    void testFromStringInvalidConversions() {
        assertFail(STR_EMPTY);
        assertFail(STR_NON_DATE);
        assertFail(STR_ISO_MONTH_DAY);
        assertFail(STR_ISO_OFFSET_TIME);
        assertFail(STR_ISO_LOCAL_TIME);
        assertFail(STR_RFC_1123_DATE_TIME);
    }

    /**
     * Test {@link YearConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    void testFromStringInvalidConversionsWithDefaultValue() {
        final Year defaultValue = null;
        final YearConverter converter = new YearConverter(defaultValue);
        assertSuccess(converter, STR_EMPTY, defaultValue, defaultValue);
        assertSuccess(converter, STR_NON_DATE, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_MONTH_DAY, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_OFFSET_TIME, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_LOCAL_TIME, defaultValue, defaultValue);
        assertSuccess(converter, STR_RFC_1123_DATE_TIME, defaultValue, defaultValue);
    }

    /**
     * Test {@link YearConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is a {@code LocalTime}
     * ISO-8601 representation.
     */
    @Test
    void testFromStringValidConversions() {
        assertSuccess(STR_ISO_ZONED_DATE_TIME, YEAR);
        assertSuccess(STR_ISO_OFFSET_DATE_TIME, YEAR);
        assertSuccess(STR_ISO_LOCAL_DATE_TIME, YEAR);
        assertSuccess(STR_ISO_OFFSET_DATE, YEAR);
        assertSuccess(STR_ISO_LOCAL_DATE, YEAR);
        assertSuccess(STR_ISO_BASIC_DATE, YEAR);
        assertSuccess(STR_ISO_ORDINAL_DATE, YEAR);
        assertSuccess(STR_ISO_WEEK_DATE, YEAR);
        assertSuccess(STR_ISO_YEAR_MONTH, YEAR);
        assertSuccess(STR_ISO_INSTANT, UTC_YEAR);
        assertSuccess(STR_EPOCH_MILLIS, UTC_YEAR);
        assertSuccess(YEAR.toString(), YEAR);
    }

    /**
     * Test {@link YearConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is a {@code LocalTime}
     * ISO-8601 representation.
     */
    @Test
    void testValidToStringConversions() {
        final YearConverter converter = new YearConverter();
        assertNull(converter.convert(String.class, null));
        assertSuccess(converter, String.class, "", "");
        assertSuccess(converter, String.class, STR_NON_DATE, STR_NON_DATE);
        assertSuccess(converter, String.class, YEAR, YEAR.toString());
    }
}
