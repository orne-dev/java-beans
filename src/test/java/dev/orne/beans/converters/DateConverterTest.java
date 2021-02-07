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
import static org.mockito.BDDMockito.*;

import java.time.Instant;
import java.util.Date;

import org.apache.commons.beanutils.Converter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code DateConverter}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-08
 * @since 0.3
 * @see DateConverter
 */
@Tag("ut")
class DateConverterTest
extends AbstractConverterTest {

    protected static Date DATE;
    protected static Date UTC_DATE;

    public DateConverterTest() {
        super(Date.class, new DateConverter());
    }

    @BeforeAll
    public static void createTestValues() {
        AbstractTimeConverterTest.createTestValues();
        DATE = Date.from(AbstractTimeConverterTest.INSTANT);
        UTC_DATE = Date.from(AbstractTimeConverterTest.UTC_INSTANT);
    }

    /**
     * Test {@link DateConverter#DateConverter()}.
     */
    @Test
    void testConstructor() {
        final DateConverter converter = new DateConverter();
        assertTrue(converter.getInstantConverter() instanceof InstantConverter);
    }

    /**
     * Test {@link DateConverter#DateConverter(Date)}.
     */
    @Test
    void testConstructorDefaultValue() {
        final DateConverter converter = new DateConverter(
                (Date) null);
        assertTrue(converter.getInstantConverter() instanceof InstantConverter);
    }

    /**
     * Test {@link DateConverter#DateConverter(Date)}.
     */
    @Test
    void testConstructorDefaultValueNonNull() {
        final Date defaultValue = new Date();
        final DateConverter converter = new DateConverter(defaultValue);
        assertTrue(converter.getInstantConverter() instanceof InstantConverter);
        assertEquals(
                defaultValue.toInstant(),
                converter.getInstantConverter().convert(Instant.class, null));
    }

    /**
     * Test {@link DateConverter#DateConverter(Converter)}.
     */
    @Test
    void testConstructorFormatter() {
        final Converter instantConverter = mock(Converter.class);
        final DateConverter converter = new DateConverter(
                instantConverter);
        assertSame(instantConverter, converter.getInstantConverter());
    }

    /**
     * Test {@link DateConverter#DateConverter(Converter, Date)}.
     */
    @Test
    void testConstructorFormatterDefaultValue() {
        final Converter instantConverter = mock(Converter.class);
        final DateConverter converter = new DateConverter(
                instantConverter,
                (Date) null);
        assertSame(instantConverter, converter.getInstantConverter());
    }

    /**
     * Test that {@link DateConverter#convert(Class, Object)} delegates
     * in nested converter when converting to {@code Date}.
     */
    @Test
    void testDelegatedInstantConversionsToDate() {
        final Converter instantConverter = mock(Converter.class);
        final DateConverter converter = new DateConverter(instantConverter);
        final Instant now = Instant.now();
        final Object value = new Object();
        willReturn(now).given(instantConverter).convert(Instant.class, value);
        final Date result = converter.convert(Date.class, value);
        assertEquals(Date.from(now), result);
        then(instantConverter).should().convert(Instant.class, value);
    }

    /**
     * Test that {@link DateConverter#convert(Class, Object)} delegates
     * in nested converter when converting to {@code String}.
     */
    @Test
    void testDelegatedInstantConversionsToString() {
        final Converter instantConverter = mock(Converter.class);
        final DateConverter converter = new DateConverter(instantConverter);
        final Instant now = Instant.now();
        final Date value = Date.from(now);
        final String mockResult = "mockResult";
        willReturn(mockResult).given(instantConverter).convert(same(String.class), eq(now));
        final String result = converter.convert(String.class, value);
        assertEquals(mockResult, result);
        then(instantConverter).should().convert(same(String.class), eq(now));
    }

    /**
     * Test {@link DateConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is invalid.
     */
    @Test
    void testFromValueInvalidConversions() {
        assertFail(null);
        assertFail(AbstractTimeConverterTest.LOCAL_DATE);
        assertFail(AbstractTimeConverterTest.YEAR);
        assertFail(AbstractTimeConverterTest.YEAR_MONTH);
        assertFail(AbstractTimeConverterTest.MONTH_DAY);
        assertFail(AbstractTimeConverterTest.MONTH);
        assertFail(AbstractTimeConverterTest.DAY_OF_WEEK);
        assertFail(AbstractTimeConverterTest.OFFSET_TIME);
        assertFail(AbstractTimeConverterTest.LOCAL_TIME);
        assertFail(AbstractTimeConverterTest.ZONE_ID);
        assertFail(AbstractTimeConverterTest.ZONE_OFFSET);
        assertFail(AbstractTimeConverterTest.WRONG_TYPE_VALUE);
    }

    /**
     * Test {@link DateConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is invalid.
     */
    @Test
    void testFromValueInvalidConversionsWithDefaultValue() {
        final Date defaultValue = null;
        final DateConverter converter = new DateConverter(defaultValue);
        assertSuccess(converter, (Object) null, defaultValue, defaultValue);
        assertSuccess(converter, AbstractTimeConverterTest.LOCAL_DATE, defaultValue, defaultValue);
        assertSuccess(converter, AbstractTimeConverterTest.YEAR, defaultValue, defaultValue);
        assertSuccess(converter, AbstractTimeConverterTest.YEAR_MONTH, defaultValue, defaultValue);
        assertSuccess(converter, AbstractTimeConverterTest.MONTH_DAY, defaultValue, defaultValue);
        assertSuccess(converter, AbstractTimeConverterTest.MONTH, defaultValue, defaultValue);
        assertSuccess(converter, AbstractTimeConverterTest.DAY_OF_WEEK, defaultValue, defaultValue);
        assertSuccess(converter, AbstractTimeConverterTest.OFFSET_TIME, defaultValue, defaultValue);
        assertSuccess(converter, AbstractTimeConverterTest.LOCAL_TIME, defaultValue, defaultValue);
        assertSuccess(converter, AbstractTimeConverterTest.ZONE_ID, defaultValue, defaultValue);
        assertSuccess(converter, AbstractTimeConverterTest.ZONE_OFFSET, defaultValue, defaultValue);
        assertSuccess(converter, AbstractTimeConverterTest.WRONG_TYPE_VALUE, defaultValue, defaultValue);
    }

    /**
     * Test {@link DateConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is valid.
     */
    @Test
    void testFromValueValidConversions() {
        assertSuccess(AbstractTimeConverterTest.ZONED_DATE_TIME, DATE);
        assertSuccess(AbstractTimeConverterTest.OFFSET_DATE_TIME, DATE);
        assertSuccess(AbstractTimeConverterTest.LOCAL_DATE_TIME, UTC_DATE);
        assertSuccess(AbstractTimeConverterTest.INSTANT, DATE);
        assertSuccess(AbstractTimeConverterTest.EPOCH_MILLIS, DATE);
    }

    /**
     * Test {@link DateConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    void testFromStringInvalidConversions() {
        assertFail(AbstractTimeConverterTest.STR_EMPTY);
        assertFail(AbstractTimeConverterTest.STR_NON_DATE);
        assertFail(AbstractTimeConverterTest.STR_ISO_OFFSET_DATE);
        assertFail(AbstractTimeConverterTest.STR_ISO_LOCAL_DATE);
        assertFail(AbstractTimeConverterTest.STR_ISO_BASIC_DATE);
        assertFail(AbstractTimeConverterTest.STR_ISO_ORDINAL_DATE);
        assertFail(AbstractTimeConverterTest.STR_ISO_WEEK_DATE);
        assertFail(AbstractTimeConverterTest.STR_ISO_YEAR_MONTH);
        assertFail(AbstractTimeConverterTest.STR_ISO_MONTH_DAY);
        assertFail(AbstractTimeConverterTest.STR_ISO_OFFSET_TIME);
        assertFail(AbstractTimeConverterTest.STR_ISO_LOCAL_TIME);
        assertFail(AbstractTimeConverterTest.STR_RFC_1123_DATE_TIME);
    }

    /**
     * Test {@link DateConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    void testFromStringInvalidConversionsWithDefaultValue() {
        final Date defaultValue = null;
        final DateConverter converter = new DateConverter(defaultValue);
        assertSuccess(converter, AbstractTimeConverterTest.STR_EMPTY, defaultValue, defaultValue);
        assertSuccess(converter, AbstractTimeConverterTest.STR_NON_DATE, defaultValue, defaultValue);
        assertSuccess(converter, AbstractTimeConverterTest.STR_ISO_OFFSET_DATE, defaultValue, defaultValue);
        assertSuccess(converter, AbstractTimeConverterTest.STR_ISO_LOCAL_DATE, defaultValue, defaultValue);
        assertSuccess(converter, AbstractTimeConverterTest.STR_ISO_BASIC_DATE, defaultValue, defaultValue);
        assertSuccess(converter, AbstractTimeConverterTest.STR_ISO_ORDINAL_DATE, defaultValue, defaultValue);
        assertSuccess(converter, AbstractTimeConverterTest.STR_ISO_WEEK_DATE, defaultValue, defaultValue);
        assertSuccess(converter, AbstractTimeConverterTest.STR_ISO_YEAR_MONTH, defaultValue, defaultValue);
        assertSuccess(converter, AbstractTimeConverterTest.STR_ISO_MONTH_DAY, defaultValue, defaultValue);
        assertSuccess(converter, AbstractTimeConverterTest.STR_ISO_OFFSET_TIME, defaultValue, defaultValue);
        assertSuccess(converter, AbstractTimeConverterTest.STR_ISO_LOCAL_TIME, defaultValue, defaultValue);
        assertSuccess(converter, AbstractTimeConverterTest.STR_RFC_1123_DATE_TIME, defaultValue, defaultValue);
    }

    /**
     * Test {@link DateConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is a {@code LocalTime}
     * ISO-8601 representation.
     */
    @Test
    void testFromStringValidConversions() {
        assertSuccess(AbstractTimeConverterTest.STR_ISO_ZONED_DATE_TIME, DATE);
        assertSuccess(AbstractTimeConverterTest.STR_ISO_OFFSET_DATE_TIME, DATE);
        assertSuccess(AbstractTimeConverterTest.STR_ISO_LOCAL_DATE_TIME, UTC_DATE);
        assertSuccess(AbstractTimeConverterTest.STR_ISO_INSTANT, DATE);
        assertSuccess(AbstractTimeConverterTest.STR_EPOCH_MILLIS, DATE);
    }

    /**
     * Test {@link DateConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is {@code null},
     * a {@code String} or a {@code Date}.
     */
    @Test
    void testValidToStringConversions() {
        final DateConverter converter = new DateConverter();
        assertNull(converter.convert(String.class, null));
        assertSuccess(converter, String.class, "", "");
        assertSuccess(converter, String.class,
                AbstractTimeConverterTest.STR_NON_DATE,
                AbstractTimeConverterTest.STR_NON_DATE);
        assertSuccess(converter, String.class,
                DATE,
                AbstractTimeConverterTest.STR_ISO_INSTANT);
    }

    /**
     * Test {@link DateConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is an invalid object.
     */
    @Test
    void testInvalidToStringConversions() {
        final DateConverter converter = new DateConverter();
        assertFail(converter, String.class, new Object());
    }
}
