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

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code YearMonthConverter}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see YearMonthConverter
 */
@Tag("ut")
public class YearMonthConverterTest
extends AbstractTimeConverterTest {

    public YearMonthConverterTest() {
        super(YearMonth.class, new YearMonthConverter());
    }

    /**
     * Test {@link YearMonthConverter#YearMonthConverter()}.
     */
    @Test
    public void testConstructor() {
        final YearMonthConverter converter = new YearMonthConverter();
        assertConstructor(converter,
                YearMonthConverter.ISO_8601_PARSER,
                YearMonthConverter.ISO_8601_PARSER,
                YearMonthConverter.BY_FULL_TEXT_PARSER,
                YearMonthConverter.BY_SHORT_TEXT_PARSER,
                YearMonthConverter.BY_NARROW_TEXT_PARSER,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_DATE_TIME,
                DateTimeFormatter.ISO_DATE,
                DateTimeFormatter.ISO_ORDINAL_DATE,
                DateTimeFormatter.ISO_WEEK_DATE,
                DateTimeFormatter.BASIC_ISO_DATE,
                InstantConverter.EPOCH_MILLIS_PARSER);
    }

    /**
     * Test {@link YearMonthConverter#YearMonthConverter(YearMonth)}.
     */
    @Test
    public void testConstructorDefaultValue() {
        final YearMonthConverter converter = new YearMonthConverter(
                (YearMonth) null);
        assertConstructor(converter,
                YearMonthConverter.ISO_8601_PARSER,
                YearMonthConverter.ISO_8601_PARSER,
                YearMonthConverter.BY_FULL_TEXT_PARSER,
                YearMonthConverter.BY_SHORT_TEXT_PARSER,
                YearMonthConverter.BY_NARROW_TEXT_PARSER,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_DATE_TIME,
                DateTimeFormatter.ISO_DATE,
                DateTimeFormatter.ISO_ORDINAL_DATE,
                DateTimeFormatter.ISO_WEEK_DATE,
                DateTimeFormatter.BASIC_ISO_DATE,
                InstantConverter.EPOCH_MILLIS_PARSER);
    }

    /**
     * Test {@link YearMonthConverter#YearMonthConverter(DateTimeFormatter)}.
     */
    @Test
    public void testConstructorFormatter() {
        final YearMonthConverter converter = new YearMonthConverter(
                DateTimeFormatter.RFC_1123_DATE_TIME);
        assertConstructor(converter,
                DateTimeFormatter.RFC_1123_DATE_TIME,
                YearMonthConverter.ISO_8601_PARSER,
                YearMonthConverter.BY_FULL_TEXT_PARSER,
                YearMonthConverter.BY_SHORT_TEXT_PARSER,
                YearMonthConverter.BY_NARROW_TEXT_PARSER,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_DATE_TIME,
                DateTimeFormatter.ISO_DATE,
                DateTimeFormatter.ISO_ORDINAL_DATE,
                DateTimeFormatter.ISO_WEEK_DATE,
                DateTimeFormatter.BASIC_ISO_DATE,
                InstantConverter.EPOCH_MILLIS_PARSER);
    }

    /**
     * Test {@link YearMonthConverter#YearMonthConverter(DateTimeFormatter, YearMonth)}.
     */
    @Test
    public void testConstructorFormatterDefaultValue() {
        final YearMonthConverter converter = new YearMonthConverter(
                DateTimeFormatter.RFC_1123_DATE_TIME,
                (YearMonth) null);
        assertConstructor(converter,
                DateTimeFormatter.RFC_1123_DATE_TIME,
                YearMonthConverter.ISO_8601_PARSER,
                YearMonthConverter.BY_FULL_TEXT_PARSER,
                YearMonthConverter.BY_SHORT_TEXT_PARSER,
                YearMonthConverter.BY_NARROW_TEXT_PARSER,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_DATE_TIME,
                DateTimeFormatter.ISO_DATE,
                DateTimeFormatter.ISO_ORDINAL_DATE,
                DateTimeFormatter.ISO_WEEK_DATE,
                DateTimeFormatter.BASIC_ISO_DATE,
                InstantConverter.EPOCH_MILLIS_PARSER);
    }

    /**
     * Test {@link YearMonthConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    public void testFromValueInvalidConversions() {
        assertFail(null);
        assertFail(YEAR);
        assertFail(MONTH_DAY);
        assertFail(MONTH);
        assertFail(DAY_OF_WEEK);
        assertFail(OFFSET_TIME);
        assertFail(LOCAL_TIME);
        assertFail(ZONE_ID);
        assertFail(ZONE_OFFSET);
        assertFail(WRONG_TYPE_VALUE);
    }

    /**
     * Test {@link YearMonthConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    public void testFromValueInvalidConversionsWithDefaultValue() {
        final YearMonth defaultValue = null;
        final YearMonthConverter converter = new YearMonthConverter(defaultValue);
        assertSuccess(converter, (Object) null, defaultValue, defaultValue);
        assertSuccess(converter, YEAR, defaultValue, defaultValue);
        assertSuccess(converter, MONTH_DAY, defaultValue, defaultValue);
        assertSuccess(converter, MONTH, defaultValue, defaultValue);
        assertSuccess(converter, DAY_OF_WEEK, defaultValue, defaultValue);
        assertSuccess(converter, OFFSET_TIME, defaultValue, defaultValue);
        assertSuccess(converter, LOCAL_TIME, defaultValue, defaultValue);
        assertSuccess(converter, ZONE_ID, defaultValue, defaultValue);
        assertSuccess(converter, ZONE_OFFSET, defaultValue, defaultValue);
        assertSuccess(converter, WRONG_TYPE_VALUE, defaultValue, defaultValue);
    }

    /**
     * Test {@link YearMonthConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is a {@code LocalTime}
     * ISO-8601 representation.
     */
    @Test
    public void testFromValueValidConversions() {
        assertSuccess(ZONED_DATE_TIME, YEAR_MONTH);
        assertSuccess(OFFSET_DATE_TIME, YEAR_MONTH);
        assertSuccess(LOCAL_DATE_TIME, YEAR_MONTH);
        assertSuccess(LOCAL_DATE, YEAR_MONTH);
        assertSuccess(YEAR_MONTH, YEAR_MONTH);
        assertSuccess(INSTANT, UTC_YEAR_MONTH);
        assertSuccess(EPOCH_MILLIS, UTC_YEAR_MONTH);
    }

    /**
     * Test {@link YearMonthConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    public void testFromStringInvalidConversions() {
        assertFail(STR_EMPTY);
        assertFail(STR_NON_DATE);
        assertFail(STR_ISO_MONTH_DAY);
        assertFail(STR_ISO_OFFSET_TIME);
        assertFail(STR_ISO_LOCAL_TIME);
        assertFail(STR_RFC_1123_DATE_TIME);
    }

    /**
     * Test {@link YearMonthConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    public void testFromStringInvalidConversionsWithDefaultValue() {
        final YearMonth defaultValue = null;
        final YearMonthConverter converter = new YearMonthConverter(defaultValue);
        assertSuccess(converter, STR_EMPTY, defaultValue, defaultValue);
        assertSuccess(converter, STR_NON_DATE, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_MONTH_DAY, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_OFFSET_TIME, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_LOCAL_TIME, defaultValue, defaultValue);
        assertSuccess(converter, STR_RFC_1123_DATE_TIME, defaultValue, defaultValue);
    }

    /**
     * Test {@link YearMonthConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is a {@code LocalTime}
     * ISO-8601 representation.
     */
    @Test
    public void testFromStringValidConversions() {
        assertSuccess(STR_ISO_ZONED_DATE_TIME, YEAR_MONTH);
        assertSuccess(STR_ISO_OFFSET_DATE_TIME, YEAR_MONTH);
        assertSuccess(STR_ISO_LOCAL_DATE_TIME, UTC_YEAR_MONTH);
        assertSuccess(STR_ISO_OFFSET_DATE, YEAR_MONTH);
        assertSuccess(STR_ISO_LOCAL_DATE, UTC_YEAR_MONTH);
        assertSuccess(STR_ISO_BASIC_DATE, UTC_YEAR_MONTH);
        assertSuccess(STR_ISO_ORDINAL_DATE, UTC_YEAR_MONTH);
        assertSuccess(STR_ISO_WEEK_DATE, UTC_YEAR_MONTH);
        assertSuccess(STR_ISO_YEAR_MONTH, UTC_YEAR_MONTH);
        assertSuccess(STR_ISO_INSTANT, UTC_YEAR_MONTH);
        assertSuccess(STR_EPOCH_MILLIS, UTC_YEAR_MONTH);
    }

    /**
     * Test {@link YearMonthConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is a {@code LocalTime}
     * ISO-8601 representation.
     */
    @Test
    public void testValidToStringConversions() {
        final YearMonthConverter converter = new YearMonthConverter();
        assertNull(converter.convert(String.class, null));
        assertSuccess(converter, String.class, "", "");
        assertSuccess(converter, String.class, STR_NON_DATE, STR_NON_DATE);
        assertSuccess(converter, String.class, YEAR_MONTH, STR_ISO_YEAR_MONTH);
    }
}
