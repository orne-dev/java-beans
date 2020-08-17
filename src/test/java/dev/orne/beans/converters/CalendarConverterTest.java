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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.beanutils.Converter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code CalendarConverter}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-08
 * @since 0.3
 * @see CalendarConverter
 */
@Tag("ut")
public class CalendarConverterTest
extends AbstractConverterTest {

    protected static Calendar DATE;
    protected static Calendar UTC_DATE;

    public CalendarConverterTest() {
        super(Calendar.class, new CalendarConverter());
    }

    @BeforeAll
    public static void createTestValues() {
        AbstractTimeConverterTest.createTestValues();
        DATE = Calendar.getInstance();
        DATE.setTime(Date.from(AbstractTimeConverterTest.INSTANT));
        DATE.setLenient(false);
        UTC_DATE = Calendar.getInstance();
        UTC_DATE.setTime(Date.from(AbstractTimeConverterTest.UTC_INSTANT));
        UTC_DATE.setLenient(false);
    }

    /**
     * Test {@link CalendarConverter#CalendarConverter()}.
     */
    @Test
    public void testConstructor() {
        final CalendarConverter converter = new CalendarConverter();
        assertTrue(converter.getInstantConverter() instanceof InstantConverter);
        assertNull(converter.getLocale());
        assertNull(converter.getTimeZone());
    }

    /**
     * Test {@link CalendarConverter#CalendarConverter(Date)}.
     */
    @Test
    public void testConstructorDefaultValue() {
        final CalendarConverter converter = new CalendarConverter(
                (Calendar) null);
        assertTrue(converter.getInstantConverter() instanceof InstantConverter);
        assertNull(converter.getLocale());
        assertNull(converter.getTimeZone());
    }

    /**
     * Test {@link CalendarConverter#CalendarConverter(Date)}.
     */
    @Test
    public void testConstructorDefaultValueNonNull() {
        final Calendar defaultValue = Calendar.getInstance();
        final CalendarConverter converter = new CalendarConverter(defaultValue);
        assertTrue(converter.getInstantConverter() instanceof InstantConverter);
        assertEquals(
                defaultValue.toInstant(),
                converter.getInstantConverter().convert(Instant.class, null));
        assertNull(converter.getLocale());
        assertNull(converter.getTimeZone());
    }

    /**
     * Test {@link CalendarConverter#CalendarConverter(Converter)}.
     */
    @Test
    public void testConstructorFormatter() {
        final Converter instantConverter = mock(Converter.class);
        final CalendarConverter converter = new CalendarConverter(
                instantConverter);
        assertSame(instantConverter, converter.getInstantConverter());
        assertNull(converter.getLocale());
        assertNull(converter.getTimeZone());
    }

    /**
     * Test {@link CalendarConverter#CalendarConverter(Converter, Date)}.
     */
    @Test
    public void testConstructorFormatterDefaultValue() {
        final Converter instantConverter = mock(Converter.class);
        final CalendarConverter converter = new CalendarConverter(
                instantConverter,
                (Calendar) null);
        assertSame(instantConverter, converter.getInstantConverter());
        assertNull(converter.getLocale());
        assertNull(converter.getTimeZone());
    }

    /**
     * Test {@link CalendarConverter#setLocale(Locale)}.
     */
    @Test
    public void testSetLocale() {
        final Locale locale = Locale.getDefault();
        final CalendarConverter converter = new CalendarConverter();
        converter.setLocale(locale);
        assertEquals(locale, converter.getLocale());
    }

    /**
     * Test {@link CalendarConverter#setTimeZone(TimeZone)}.
     */
    @Test
    public void testSetTimeZone() {
        final TimeZone timeZone = TimeZone.getDefault();
        final CalendarConverter converter = new CalendarConverter();
        converter.setTimeZone(timeZone);
        assertEquals(timeZone, converter.getTimeZone());
    }

    /**
     * Test that {@link CalendarConverter#convert(Class, Object)} delegates
     * in nested converter when converting to {@code Calendar}.
     */
    @Test
    public void testDelegatedInstantConversionsToDate() {
        final Converter instantConverter = mock(Converter.class);
        final CalendarConverter converter = new CalendarConverter(instantConverter);
        final Instant now = Instant.now();
        final Calendar expected = Calendar.getInstance();
        expected.setTime(Date.from(now));
        expected.setLenient(false);
        final Object value = new Object();
        willReturn(now).given(instantConverter).convert(Instant.class, value);
        final Calendar result = converter.convert(Calendar.class, value);
        assertEquals(expected, result);
        then(instantConverter).should().convert(Instant.class, value);
    }

    /**
     * Test that {@link CalendarConverter#convert(Class, Object)} delegates
     * in nested converter when converting to {@code String}.
     */
    @Test
    public void testDelegatedInstantConversionsToString() {
        final Converter instantConverter = mock(Converter.class);
        final CalendarConverter converter = new CalendarConverter(instantConverter);
        final Instant now = Instant.now();
        final Calendar value = Calendar.getInstance();
        value.setTime(Date.from(now));
        final String mockResult = "mockResult";
        willReturn(mockResult).given(instantConverter).convert(same(String.class), eq(now));
        final String result = converter.convert(String.class, value);
        assertEquals(mockResult, result);
        then(instantConverter).should().convert(same(String.class), eq(now));
    }

    /**
     * Test {@link CalendarConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is invalid.
     */
    @Test
    public void testFromValueInvalidConversions() {
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
     * Test {@link CalendarConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is invalid.
     */
    @Test
    public void testFromValueInvalidConversionsWithDefaultValue() {
        final Calendar defaultValue = null;
        final CalendarConverter converter = new CalendarConverter(defaultValue);
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
     * Test {@link CalendarConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is valid.
     */
    @Test
    public void testFromValueValidConversions() {
        assertSuccess(AbstractTimeConverterTest.ZONED_DATE_TIME, DATE);
        assertSuccess(AbstractTimeConverterTest.OFFSET_DATE_TIME, DATE);
        assertSuccess(AbstractTimeConverterTest.LOCAL_DATE_TIME, UTC_DATE);
        assertSuccess(AbstractTimeConverterTest.INSTANT, DATE);
        assertSuccess(AbstractTimeConverterTest.EPOCH_MILLIS, DATE);
    }

    /**
     * Test that {@link CalendarConverter#convert(Class, Object)} converts
     * result of delegated converter honoring set {@code Locale}.
     */
    @Test
    public void testInstantToCalendarConversionWithLocale() {
        final Locale locale = Locale.JAPAN;
        final Converter instantConverter = mock(Converter.class);
        final CalendarConverter converter = new CalendarConverter(instantConverter);
        converter.setLocale(locale);
        final Instant now = Instant.now();
        final Calendar expected = Calendar.getInstance(locale);
        expected.setTime(Date.from(now));
        expected.setLenient(false);
        final Object value = new Object();
        willReturn(now).given(instantConverter).convert(Instant.class, value);
        final Calendar result = converter.convert(Calendar.class, value);
        assertEquals(expected, result);
        then(instantConverter).should().convert(Instant.class, value);
    }

    /**
     * Test that {@link CalendarConverter#convert(Class, Object)} converts
     * result of delegated converter honoring set {@code TimeZone}.
     */
    @Test
    public void testInstantToCalendarConversionWithTimeZone() {
        final TimeZone timeZone = TimeZone.getTimeZone("JST");
        final Converter instantConverter = mock(Converter.class);
        final CalendarConverter converter = new CalendarConverter(instantConverter);
        converter.setTimeZone(timeZone);
        final Instant now = Instant.now();
        final Calendar expected = Calendar.getInstance(timeZone);
        expected.setTime(Date.from(now));
        expected.setLenient(false);
        final Object value = new Object();
        willReturn(now).given(instantConverter).convert(Instant.class, value);
        final Calendar result = converter.convert(Calendar.class, value);
        assertEquals(expected, result);
        then(instantConverter).should().convert(Instant.class, value);
    }

    /**
     * Test that {@link CalendarConverter#convert(Class, Object)} converts
     * result of delegated converter honoring set {@code Locale} and
     * {@code TimeZone}.
     */
    @Test
    public void testInstantToCalendarConversionWithLocaleAndTimeZone() {
        final Locale locale = Locale.JAPAN;
        final TimeZone timeZone = TimeZone.getTimeZone("JST");
        final Converter instantConverter = mock(Converter.class);
        final CalendarConverter converter = new CalendarConverter(instantConverter);
        converter.setLocale(locale);
        converter.setTimeZone(timeZone);
        final Instant now = Instant.now();
        final Calendar expected = Calendar.getInstance(timeZone, locale);
        expected.setTime(Date.from(now));
        expected.setLenient(false);
        final Object value = new Object();
        willReturn(now).given(instantConverter).convert(Instant.class, value);
        final Calendar result = converter.convert(Calendar.class, value);
        assertEquals(expected, result);
        then(instantConverter).should().convert(Instant.class, value);
    }

    /**
     * Test {@link CalendarConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    public void testFromStringInvalidConversions() {
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
     * Test {@link CalendarConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    public void testFromStringInvalidConversionsWithDefaultValue() {
        final Calendar defaultValue = null;
        final CalendarConverter converter = new CalendarConverter(defaultValue);
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
     * Test {@link CalendarConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is a {@code LocalTime}
     * ISO-8601 representation.
     */
    @Test
    public void testFromStringValidConversions() {
        assertSuccess(AbstractTimeConverterTest.STR_ISO_ZONED_DATE_TIME, DATE);
        assertSuccess(AbstractTimeConverterTest.STR_ISO_OFFSET_DATE_TIME, DATE);
        assertSuccess(AbstractTimeConverterTest.STR_ISO_LOCAL_DATE_TIME, UTC_DATE);
        assertSuccess(AbstractTimeConverterTest.STR_ISO_INSTANT, DATE);
        assertSuccess(AbstractTimeConverterTest.STR_EPOCH_MILLIS, DATE);
    }

    /**
     * Test {@link CalendarConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is {@code null},
     * a {@code String} or a {@code Date}.
     */
    @Test
    public void testValidToStringConversions() {
        final CalendarConverter converter = new CalendarConverter();
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
     * Test {@link CalendarConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is an invalid object.
     */
    @Test
    public void testInvalidToStringConversions() {
        final CalendarConverter converter = new CalendarConverter();
        assertFail(converter, String.class, new Object());
    }
}
