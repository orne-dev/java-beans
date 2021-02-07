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

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.commons.beanutils.Converter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code GregorianCalendarConverter}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-08
 * @since 0.3
 * @see GregorianCalendarConverter
 */
@Tag("ut")
class GregorianCalendarConverterTest
extends AbstractConverterTest {

    protected static GregorianCalendar UTC_CALENDAR;
    protected static GregorianCalendar ZONED_CALENDAR;
    protected static GregorianCalendar OFFSET_CALENDAR;
    protected static GregorianCalendar LOCAL_CALENDAR;

    public GregorianCalendarConverterTest() {
        super(Calendar.class, new GregorianCalendarConverter());
    }

    @BeforeAll
    public static void createTestValues() {
        AbstractTimeConverterTest.createTestValues();
        ZONED_CALENDAR = GregorianCalendar.from(AbstractTimeConverterTest.ZONED_DATE_TIME);
        OFFSET_CALENDAR = GregorianCalendar.from(AbstractTimeConverterTest.OFFSET_DATE_TIME.toZonedDateTime());
        LOCAL_CALENDAR = GregorianCalendar.from(AbstractTimeConverterTest.UTC_INSTANT.atZone(ZoneOffset.UTC));
        UTC_CALENDAR = GregorianCalendar.from(AbstractTimeConverterTest.UTC_ZONED_DATE_TIME);
    }

    /**
     * Test {@link GregorianCalendarConverter#GregorianCalendarConverter()}.
     */
    @Test
    void testConstructor() {
        final GregorianCalendarConverter converter = new GregorianCalendarConverter();
        assertTrue(converter.getZonedDateTimeConverter() instanceof ZonedDateTimeConverter);
    }

    /**
     * Test {@link GregorianCalendarConverter#GregorianCalendarConverter(GregorianCalendar)}.
     */
    @Test
    void testConstructorDefaultValue() {
        final GregorianCalendarConverter converter = new GregorianCalendarConverter(
                (GregorianCalendar) null);
        assertTrue(converter.getZonedDateTimeConverter() instanceof ZonedDateTimeConverter);
    }

    /**
     * Test {@link GregorianCalendarConverter#GregorianCalendarConverter(GregorianCalendar)}.
     */
    @Test
    void testConstructorDefaultValueNonNull() {
        final GregorianCalendar defaultValue = new GregorianCalendar();
        final GregorianCalendarConverter converter = new GregorianCalendarConverter(defaultValue);
        assertTrue(converter.getZonedDateTimeConverter() instanceof ZonedDateTimeConverter);
        assertEquals(
                defaultValue.toZonedDateTime(),
                converter.getZonedDateTimeConverter().convert(ZonedDateTime.class, null));
    }

    /**
     * Test {@link GregorianCalendarConverter#GregorianCalendarConverter(Converter)}.
     */
    @Test
    void testConstructorFormatter() {
        final Converter instantConverter = mock(Converter.class);
        final GregorianCalendarConverter converter = new GregorianCalendarConverter(
                instantConverter);
        assertSame(instantConverter, converter.getZonedDateTimeConverter());
    }

    /**
     * Test {@link GregorianCalendarConverter#GregorianCalendarConverter(Converter, GregorianCalendar)}.
     */
    @Test
    void testConstructorFormatterDefaultValue() {
        final Converter instantConverter = mock(Converter.class);
        final GregorianCalendarConverter converter = new GregorianCalendarConverter(
                instantConverter,
                (GregorianCalendar) null);
        assertSame(instantConverter, converter.getZonedDateTimeConverter());
    }

    /**
     * Test that {@link GregorianCalendarConverter#convert(Class, Object)} delegates
     * in nested converter when converting to {@code Calendar}.
     */
    @Test
    void testDelegatedZonedDateTimeConversionsToCalendar() {
        final Converter instantConverter = mock(Converter.class);
        final GregorianCalendarConverter converter = new GregorianCalendarConverter(instantConverter);
        final ZonedDateTime now = ZonedDateTime.now();
        final GregorianCalendar expected = GregorianCalendar.from(now);
        final Object value = new Object();
        willReturn(now).given(instantConverter).convert(ZonedDateTime.class, value);
        final Calendar result = converter.convert(Calendar.class, value);
        assertEquals(expected, result);
        then(instantConverter).should().convert(ZonedDateTime.class, value);
    }

    /**
     * Test that {@link GregorianCalendarConverter#convert(Class, Object)} delegates
     * in nested converter when converting to {@code GregorianCalendar}.
     */
    @Test
    void testDelegatedZonedDateTimeConversionsToGregorianCalendar() {
        final Converter instantConverter = mock(Converter.class);
        final GregorianCalendarConverter converter = new GregorianCalendarConverter(instantConverter);
        final ZonedDateTime now = ZonedDateTime.now();
        final GregorianCalendar expected = GregorianCalendar.from(now);
        final Object value = new Object();
        willReturn(now).given(instantConverter).convert(ZonedDateTime.class, value);
        final GregorianCalendar result = converter.convert(GregorianCalendar.class, value);
        assertEquals(expected, result);
        then(instantConverter).should().convert(ZonedDateTime.class, value);
    }

    /**
     * Test that {@link GregorianCalendarConverter#convert(Class, Object)} delegates
     * in nested converter when converting to {@code String}.
     */
    @Test
    void testDelegatedZonedDateTimeConversionsToString() {
        final Converter instantConverter = mock(Converter.class);
        final GregorianCalendarConverter converter = new GregorianCalendarConverter(instantConverter);
        final ZonedDateTime now = ZonedDateTime.now();
        final GregorianCalendar value = GregorianCalendar.from(now);
        final String mockResult = "mockResult";
        willReturn(mockResult).given(instantConverter).convert(same(String.class), eq(now));
        final String result = converter.convert(String.class, value);
        assertEquals(mockResult, result);
        then(instantConverter).should().convert(same(String.class), eq(now));
    }

    /**
     * Test {@link GregorianCalendarConverter#convert(Class, Object)} when
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
     * Test {@link GregorianCalendarConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is invalid.
     */
    @Test
    void testFromValueInvalidConversionsWithDefaultValue() {
        final GregorianCalendar defaultValue = null;
        final GregorianCalendarConverter converter = new GregorianCalendarConverter(defaultValue);
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
     * Test {@link GregorianCalendarConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is valid.
     */
    @Test
    void testFromValueValidConversions() {
        assertSuccess(ZONED_CALENDAR, ZONED_CALENDAR);
        assertSuccess(AbstractTimeConverterTest.ZONED_DATE_TIME, ZONED_CALENDAR);
        assertSuccess(AbstractTimeConverterTest.OFFSET_DATE_TIME, OFFSET_CALENDAR);
        assertSuccess(AbstractTimeConverterTest.LOCAL_DATE_TIME, LOCAL_CALENDAR);
        assertSuccess(AbstractTimeConverterTest.INSTANT, UTC_CALENDAR);
        assertSuccess(AbstractTimeConverterTest.EPOCH_MILLIS, UTC_CALENDAR);
    }

    /**
     * Test {@link GregorianCalendarConverter#convert(Class, Object)} when
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
     * Test {@link GregorianCalendarConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    void testFromStringInvalidConversionsWithDefaultValue() {
        final GregorianCalendar defaultValue = null;
        final GregorianCalendarConverter converter = new GregorianCalendarConverter(defaultValue);
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
     * Test {@link GregorianCalendarConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is a {@code LocalTime}
     * ISO-8601 representation.
     */
    @Test
    void testFromStringValidConversions() {
        assertSuccess(AbstractTimeConverterTest.STR_ISO_ZONED_DATE_TIME, ZONED_CALENDAR);
        assertSuccess(AbstractTimeConverterTest.STR_ISO_OFFSET_DATE_TIME, OFFSET_CALENDAR);
        assertSuccess(AbstractTimeConverterTest.STR_ISO_LOCAL_DATE_TIME, LOCAL_CALENDAR);
        assertSuccess(AbstractTimeConverterTest.STR_ISO_INSTANT, UTC_CALENDAR);
        assertSuccess(AbstractTimeConverterTest.STR_EPOCH_MILLIS, UTC_CALENDAR);
    }

    /**
     * Test {@link GregorianCalendarConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is {@code null},
     * a {@code String} or a {@code Date}.
     */
    @Test
    void testValidToStringConversions() {
        final GregorianCalendarConverter converter = new GregorianCalendarConverter();
        assertNull(converter.convert(String.class, null));
        assertSuccess(converter, String.class, "", "");
        assertSuccess(converter, String.class,
                AbstractTimeConverterTest.STR_NON_DATE,
                AbstractTimeConverterTest.STR_NON_DATE);
        assertSuccess(converter, String.class,
                ZONED_CALENDAR,
                AbstractTimeConverterTest.STR_ISO_ZONED_DATE_TIME);
        final Calendar japValue = new Calendar.Builder()
                .setCalendarType("japanese")
                .setInstant(AbstractTimeConverterTest.EPOCH_MILLIS)
                .build();
        assertSuccess(converter, String.class,
                japValue,
                AbstractTimeConverterTest.STR_ISO_ZONED_DATE_TIME);
    }

    /**
     * Test {@link GregorianCalendarConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is an invalid object.
     */
    @Test
    void testInvalidToStringConversions() {
        final GregorianCalendarConverter converter = new GregorianCalendarConverter();
        assertFail(converter, String.class, new Object());
    }
}
