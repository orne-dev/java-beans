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

import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code OffsetTimeConverter}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see OffsetTimeConverter
 */
@Tag("ut")
class OffsetTimeConverterTest
extends AbstractTimeConverterTest {

    public OffsetTimeConverterTest() {
        super(OffsetTime.class, new OffsetTimeConverter());
    }

    /**
     * Test {@link OffsetTimeConverter#OffsetTimeConverter()}.
     */
    @Test
    void testConstructor() {
        final OffsetTimeConverter converter = new OffsetTimeConverter();
        assertConstructor(converter,
                DateTimeFormatter.ISO_OFFSET_TIME,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_DATE_TIME,
                DateTimeFormatter.ISO_TIME,
                InstantConverter.EPOCH_MILLIS_PARSER);
    }

    /**
     * Test {@link OffsetTimeConverter#OffsetTimeConverter(LocalDate)}.
     */
    @Test
    void testConstructorDefaultValue() {
        final OffsetTimeConverter converter = new OffsetTimeConverter(
                (OffsetTime) null);
        assertConstructor(converter,
                DateTimeFormatter.ISO_OFFSET_TIME,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_DATE_TIME,
                DateTimeFormatter.ISO_TIME,
                InstantConverter.EPOCH_MILLIS_PARSER);
    }

    /**
     * Test {@link OffsetTimeConverter#OffsetTimeConverter(DateTimeFormatter)}.
     */
    @Test
    void testConstructorFormatter() {
        final OffsetTimeConverter converter = new OffsetTimeConverter(
                DateTimeFormatter.RFC_1123_DATE_TIME);
        assertConstructor(converter,
                DateTimeFormatter.RFC_1123_DATE_TIME,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_DATE_TIME,
                DateTimeFormatter.ISO_TIME,
                InstantConverter.EPOCH_MILLIS_PARSER);
    }

    /**
     * Test {@link OffsetTimeConverter#OffsetTimeConverter(DateTimeFormatter, LocalDateTime)}.
     */
    @Test
    void testConstructorFormatterDefaultValue() {
        final OffsetTimeConverter converter = new OffsetTimeConverter(
                DateTimeFormatter.RFC_1123_DATE_TIME,
                (OffsetTime) null);
        assertConstructor(converter,
                DateTimeFormatter.RFC_1123_DATE_TIME,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_DATE_TIME,
                DateTimeFormatter.ISO_TIME,
                InstantConverter.EPOCH_MILLIS_PARSER);
    }

    /**
     * Test {@link OffsetTimeConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    void testFromValueInvalidConversions() {
        assertFail(null);
        assertFail(LOCAL_DATE);
        assertFail(YEAR);
        assertFail(YEAR_MONTH);
        assertFail(MONTH_DAY);
        assertFail(MONTH);
        assertFail(DAY_OF_WEEK);
        assertFail(ZONE_ID);
        assertFail(ZONE_OFFSET);
        assertFail(WRONG_TYPE_VALUE);
    }

    /**
     * Test {@link OffsetTimeConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    void testFromValueInvalidConversionsWithDefaultValue() {
        final OffsetTime defaultValue = null;
        final OffsetTimeConverter converter = new OffsetTimeConverter(defaultValue);
        assertSuccess(converter, (Object) null, defaultValue, defaultValue);
        assertSuccess(converter, LOCAL_DATE, defaultValue, defaultValue);
        assertSuccess(converter, YEAR, defaultValue, defaultValue);
        assertSuccess(converter, YEAR_MONTH, defaultValue, defaultValue);
        assertSuccess(converter, MONTH_DAY, defaultValue, defaultValue);
        assertSuccess(converter, MONTH, defaultValue, defaultValue);
        assertSuccess(converter, DAY_OF_WEEK, defaultValue, defaultValue);
        assertSuccess(converter, ZONE_ID, defaultValue, defaultValue);
        assertSuccess(converter, ZONE_OFFSET, defaultValue, defaultValue);
        assertSuccess(converter, WRONG_TYPE_VALUE, defaultValue, defaultValue);
    }

    /**
     * Test {@link OffsetTimeConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is a {@code OffsetTime}
     * ISO-8601 representation.
     */
    @Test
    void testFromValueValidConversions() {
        assertSuccess(ZONED_DATE_TIME, OFFSET_TIME);
        assertSuccess(OFFSET_DATE_TIME, OFFSET_TIME);
        assertSuccess(UTC_LOCAL_DATE_TIME, UTC_OFFSET_TIME);
        assertSuccess(OFFSET_TIME, OFFSET_TIME);
        assertSuccess(UTC_LOCAL_TIME, UTC_OFFSET_TIME);
        assertSuccess(INSTANT, UTC_OFFSET_TIME);
        assertSuccess(EPOCH_MILLIS, UTC_OFFSET_TIME.truncatedTo(ChronoUnit.MILLIS));
    }

    /**
     * Test {@link OffsetTimeConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    void testFromStringInvalidConversions() {
        assertFail(STR_EMPTY);
        assertFail(STR_NON_DATE);
        assertFail(STR_ISO_OFFSET_DATE);
        assertFail(STR_ISO_LOCAL_DATE);
        assertFail(STR_ISO_BASIC_DATE);
        assertFail(STR_ISO_ORDINAL_DATE);
        assertFail(STR_ISO_WEEK_DATE);
        assertFail(STR_ISO_YEAR_MONTH);
        assertFail(STR_ISO_MONTH_DAY);
        assertFail(STR_RFC_1123_DATE_TIME);
    }

    /**
     * Test {@link OffsetTimeConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    void testFromStringInvalidConversionsWithDefaultValue() {
        final OffsetTime defaultValue = null;
        final OffsetTimeConverter converter = new OffsetTimeConverter(defaultValue);
        assertSuccess(converter, STR_EMPTY, defaultValue, defaultValue);
        assertSuccess(converter, STR_NON_DATE, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_OFFSET_DATE, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_LOCAL_DATE, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_BASIC_DATE, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_ORDINAL_DATE, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_WEEK_DATE, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_YEAR_MONTH, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_MONTH_DAY, defaultValue, defaultValue);
        assertSuccess(converter, STR_RFC_1123_DATE_TIME, defaultValue, defaultValue);
    }

    /**
     * Test {@link OffsetTimeConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is a {@code OffsetTime}
     * ISO-8601 representation.
     */
    @Test
    void testFromStringValidConversions() {
        assertSuccess(STR_ISO_ZONED_DATE_TIME, OFFSET_TIME);
        assertSuccess(STR_ISO_OFFSET_DATE_TIME, OFFSET_TIME);
        assertSuccess(STR_UTC_ISO_LOCAL_DATE_TIME, UTC_OFFSET_TIME);
        assertSuccess(STR_ISO_OFFSET_TIME, OFFSET_TIME);
        assertSuccess(STR_UTC_ISO_LOCAL_TIME, UTC_OFFSET_TIME);
        assertSuccess(STR_ISO_INSTANT, UTC_OFFSET_TIME);
        assertSuccess(STR_EPOCH_MILLIS, UTC_OFFSET_TIME.truncatedTo(ChronoUnit.MILLIS));
    }

    /**
     * Test {@link OffsetTimeConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is a {@code OffsetTime}
     * ISO-8601 representation.
     */
    @Test
    void testValidToStringConversions() {
        final OffsetTimeConverter converter = new OffsetTimeConverter();
        assertNull(converter.convert(String.class, null));
        assertSuccess(converter, String.class, "", "");
        assertSuccess(converter, String.class, STR_NON_DATE, STR_NON_DATE);
        assertSuccess(converter, String.class, OFFSET_TIME, STR_ISO_OFFSET_TIME);
    }
}
