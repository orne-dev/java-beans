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

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code ZoneOffsetConverter}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see ZoneOffsetConverter
 */
@Tag("ut")
public class ZoneOffsetConverterTest
extends AbstractTimeConverterTest {

    public ZoneOffsetConverterTest() {
        super(ZoneOffset.class, new ZoneOffsetConverter());
    }

    /**
     * Test {@link ZoneOffsetConverter#ZoneOffsetConverter()}.
     */
    @Test
    public void testConstructor() {
        final ZoneOffsetConverter converter = new ZoneOffsetConverter();
        assertConstructor(converter,
                ZoneOffsetConverter.BY_ID_PARSER,
                ZoneOffsetConverter.BY_ID_PARSER,
                DateTimeFormatter.ISO_ZONED_DATE_TIME,
                DateTimeFormatter.ISO_OFFSET_DATE_TIME,
                DateTimeFormatter.ISO_OFFSET_DATE,
                DateTimeFormatter.ISO_OFFSET_TIME);
    }

    /**
     * Test {@link ZoneOffsetConverter#ZoneOffsetConverter(ZoneOffset)}.
     */
    @Test
    public void testConstructorDefaultValue() {
        final ZoneOffsetConverter converter = new ZoneOffsetConverter(
                (ZoneOffset) null);
        assertConstructor(converter,
                ZoneOffsetConverter.BY_ID_PARSER,
                ZoneOffsetConverter.BY_ID_PARSER,
                DateTimeFormatter.ISO_ZONED_DATE_TIME,
                DateTimeFormatter.ISO_OFFSET_DATE_TIME,
                DateTimeFormatter.ISO_OFFSET_DATE,
                DateTimeFormatter.ISO_OFFSET_TIME);
    }

    /**
     * Test {@link ZoneOffsetConverter#ZoneOffsetConverter(DateTimeFormatter)}.
     */
    @Test
    public void testConstructorFormatter() {
        final ZoneOffsetConverter converter = new ZoneOffsetConverter(
                DateTimeFormatter.RFC_1123_DATE_TIME);
        assertConstructor(converter,
                DateTimeFormatter.RFC_1123_DATE_TIME,
                ZoneOffsetConverter.BY_ID_PARSER,
                DateTimeFormatter.ISO_ZONED_DATE_TIME,
                DateTimeFormatter.ISO_OFFSET_DATE_TIME,
                DateTimeFormatter.ISO_OFFSET_DATE,
                DateTimeFormatter.ISO_OFFSET_TIME);
    }

    /**
     * Test {@link ZoneOffsetConverter#ZoneOffsetConverter(DateTimeFormatter, ZoneOffset)}.
     */
    @Test
    public void testConstructorFormatterDefaultValue() {
        final ZoneOffsetConverter converter = new ZoneOffsetConverter(
                DateTimeFormatter.RFC_1123_DATE_TIME,
                (ZoneOffset) null);
        assertConstructor(converter,
                DateTimeFormatter.RFC_1123_DATE_TIME,
                ZoneOffsetConverter.BY_ID_PARSER,
                DateTimeFormatter.ISO_ZONED_DATE_TIME,
                DateTimeFormatter.ISO_OFFSET_DATE_TIME,
                DateTimeFormatter.ISO_OFFSET_DATE,
                DateTimeFormatter.ISO_OFFSET_TIME);
    }

    /**
     * Test {@link ZoneOffsetConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    public void testFromValueInvalidConversions() {
        assertFail(null);
        assertFail(LOCAL_DATE);
        assertFail(YEAR);
        assertFail(YEAR_MONTH);
        assertFail(MONTH_DAY);
        assertFail(MONTH);
        assertFail(DAY_OF_WEEK);
        assertFail(LOCAL_TIME);
        assertFail(ZONE_ID);
        assertFail(INSTANT);
        assertFail(EPOCH_MILLIS);
        assertFail(WRONG_TYPE_VALUE);
    }

    /**
     * Test {@link ZoneOffsetConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    public void testFromValueInvalidConversionsWithDefaultValue() {
        final ZoneOffset defaultValue = null;
        final ZoneOffsetConverter converter = new ZoneOffsetConverter(defaultValue);
        assertSuccess(converter, (Object) null, defaultValue, defaultValue);
        assertSuccess(converter, LOCAL_DATE, defaultValue, defaultValue);
        assertSuccess(converter, YEAR, defaultValue, defaultValue);
        assertSuccess(converter, YEAR_MONTH, defaultValue, defaultValue);
        assertSuccess(converter, MONTH_DAY, defaultValue, defaultValue);
        assertSuccess(converter, MONTH, defaultValue, defaultValue);
        assertSuccess(converter, DAY_OF_WEEK, defaultValue, defaultValue);
        assertSuccess(converter, LOCAL_TIME, defaultValue, defaultValue);
        assertSuccess(converter, ZONE_ID, defaultValue, defaultValue);
        assertSuccess(converter, INSTANT, defaultValue, defaultValue);
        assertSuccess(converter, EPOCH_MILLIS, defaultValue, defaultValue);
        assertSuccess(converter, WRONG_TYPE_VALUE, defaultValue, defaultValue);
    }

    /**
     * Test {@link ZoneOffsetConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is a {@code LocalTime}
     * ISO-8601 representation.
     */
    @Test
    public void testFromValueValidConversions() {
        assertSuccess(ZONED_DATE_TIME, ZONE_OFFSET);
        assertSuccess(OFFSET_DATE_TIME, ZONE_OFFSET);
        assertSuccess(OFFSET_TIME, ZONE_OFFSET);
        assertSuccess(ZONE_OFFSET, ZONE_OFFSET);
    }

    /**
     * Test {@link ZoneOffsetConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    public void testFromStringInvalidConversions() {
        assertFail(STR_EMPTY);
        assertFail(STR_NON_DATE);
        assertFail(STR_UTC_ISO_LOCAL_DATE_TIME);
        assertFail(STR_ISO_LOCAL_DATE);
        assertFail(STR_ISO_BASIC_DATE);
        assertFail(STR_ISO_ORDINAL_DATE);
        assertFail(STR_ISO_WEEK_DATE);
        assertFail(STR_ISO_YEAR_MONTH);
        assertFail(STR_ISO_MONTH_DAY);
        assertFail(STR_ISO_LOCAL_TIME);
        assertFail(STR_EPOCH_MILLIS);
        assertFail(STR_RFC_1123_DATE_TIME);
    }

    /**
     * Test {@link ZoneOffsetConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    public void testFromStringInvalidConversionsWithDefaultValue() {
        final ZoneOffset defaultValue = null;
        final ZoneOffsetConverter converter = new ZoneOffsetConverter(defaultValue);
        assertSuccess(converter, STR_EMPTY, defaultValue, defaultValue);
        assertSuccess(converter, STR_NON_DATE, defaultValue, defaultValue);
        assertSuccess(converter, STR_UTC_ISO_LOCAL_DATE_TIME, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_LOCAL_DATE, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_BASIC_DATE, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_ORDINAL_DATE, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_WEEK_DATE, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_YEAR_MONTH, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_MONTH_DAY, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_LOCAL_TIME, defaultValue, defaultValue);
        assertSuccess(converter, STR_EPOCH_MILLIS, defaultValue, defaultValue);
        assertSuccess(converter, STR_RFC_1123_DATE_TIME, defaultValue, defaultValue);
    }

    /**
     * Test {@link ZoneOffsetConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is a {@code LocalTime}
     * ISO-8601 representation.
     */
    @Test
    public void testFromStringValidConversions() {
        assertSuccess(STR_ISO_ZONED_DATE_TIME, ZONE_OFFSET);
        assertSuccess(STR_ISO_OFFSET_DATE_TIME, ZONE_OFFSET);
        assertSuccess(STR_ISO_OFFSET_DATE, ZONE_OFFSET);
        assertSuccess(STR_ISO_OFFSET_TIME, ZONE_OFFSET);
        assertSuccess(ZONE_OFFSET.toString(), ZONE_OFFSET);
        assertSuccess(STR_ISO_INSTANT, ZoneOffset.UTC);
    }

    /**
     * Test {@link ZoneOffsetConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is a {@code LocalTime}
     * ISO-8601 representation.
     */
    @Test
    public void testValidToStringConversions() {
        final ZoneOffsetConverter converter = new ZoneOffsetConverter();
        assertNull(converter.convert(String.class, null));
        assertSuccess(converter, String.class, "", "");
        assertSuccess(converter, String.class, STR_NON_DATE, STR_NON_DATE);
        assertSuccess(converter, String.class, ZONE_OFFSET, ZONE_OFFSET.toString());
    }
}
