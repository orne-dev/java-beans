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
import static org.mockito.BDDMockito.*;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.beanutils.ConversionException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

/**
 * Unit tests for {@code AbstractDateTimeConverter}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see AbstractDateTimeConverter
 */
@Tag("ut")
public class AbstractDateTimeConverterTest {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ISO_INSTANT;
    private static final DateTimeFormatter PARSER1 =
            DateTimeFormatter.ofLocalizedDateTime(
                FormatStyle.MEDIUM,
                FormatStyle.LONG);
    private static final DateTimeFormatter PARSER2 =
            new DateTimeFormatterBuilder()
                .appendLocalized(
                    FormatStyle.LONG, 
                    FormatStyle.LONG)
                .toFormatter(Locale.FRENCH);
    private static final DateTimeFormatter PARSER3 =
            new DateTimeFormatterBuilder()
            .appendLocalized(
                FormatStyle.LONG, 
                FormatStyle.LONG)
            .toFormatter(Locale.GERMAN);

    /**
     * Tests {@link AbstractDateTimeConverter#AbstractDateTimeConverter(DateTimeFormatter)} method.
     */
    @Test
    public void testConstructorFormatter() {
        final AbstractDateTimeConverter converter = new TestDataTimeConverter(
                FORMATTER);
        assertNotNull(converter.getFormatter());
        assertSame(FORMATTER, converter.getFormatter());
        assertFalse(converter.isUseDefault());
        assertNotNull(converter.getParsers());
        assertEquals(1, converter.getParsers().size());
        assertTrue(converter.getParsers().contains(FORMATTER));
    }

    /**
     * Tests {@link AbstractDateTimeConverter#AbstractDateTimeConverter(DateTimeFormatter, TemporalAccessor)} method.
     */
    @Test
    public void testConstructorFormatterDefault() {
        final Instant defaultValue = Instant.now();
        final TestDataTimeConverter converter = new TestDataTimeConverter(
                FORMATTER,
                defaultValue);
        assertNotNull(converter.getFormatter());
        assertSame(FORMATTER, converter.getFormatter());
        assertTrue(converter.isUseDefault());
        assertNotNull(converter.getParsers());
        assertEquals(1, converter.getParsers().size());
        assertTrue(converter.getParsers().contains(FORMATTER));
    }

    /**
     * Tests {@link AbstractDateTimeConverter#getLogger()} method.
     */
    @Test
    public void testGetLogger() {
        final AbstractDateTimeConverter converter = new TestDataTimeConverter(
                FORMATTER);
        final Logger logger = converter.getLogger();
        assertNotNull(logger);
        final Logger repeatResult = converter.getLogger();
        assertNotNull(repeatResult);
        assertSame(logger, repeatResult);
    }

    /**
     * Tests {@link AbstractDateTimeConverter#addParsers(DateTimeFormatter...)} method.
     */
    @Test
    public void testAddParsersVarargs() {
        final AbstractDateTimeConverter converter = new TestDataTimeConverter(
                FORMATTER);
        converter.addParsers(PARSER1);
        assertEquals(2, converter.getParsers().size());
        assertTrue(converter.getParsers().contains(FORMATTER));
        assertTrue(converter.getParsers().contains(PARSER1));
        converter.addParsers(PARSER2, PARSER3);
        assertEquals(4, converter.getParsers().size());
        assertTrue(converter.getParsers().contains(FORMATTER));
        assertTrue(converter.getParsers().contains(PARSER1));
        assertTrue(converter.getParsers().contains(PARSER2));
        assertTrue(converter.getParsers().contains(PARSER3));
        assertSame(FORMATTER, converter.getParsers().get(0));
        assertSame(PARSER1, converter.getParsers().get(1));
        assertSame(PARSER2, converter.getParsers().get(2));
        assertSame(PARSER3, converter.getParsers().get(3));
    }

    /**
     * Tests {@link AbstractDateTimeConverter#addParsers(java.util.Collection)} method.
     */
    @Test
    public void testAddParsersCollection() {
        final AbstractDateTimeConverter converter = new TestDataTimeConverter(
                FORMATTER);
        converter.addParsers(Arrays.asList(PARSER1));
        assertEquals(2, converter.getParsers().size());
        assertTrue(converter.getParsers().contains(FORMATTER));
        assertTrue(converter.getParsers().contains(PARSER1));
        assertTrue(converter.getParsers().contains(PARSER1));
        converter.addParsers(Arrays.asList(PARSER2, PARSER3));
        assertEquals(4, converter.getParsers().size());
        assertTrue(converter.getParsers().contains(FORMATTER));
        assertTrue(converter.getParsers().contains(PARSER1));
        assertTrue(converter.getParsers().contains(PARSER2));
        assertTrue(converter.getParsers().contains(PARSER3));
        assertSame(FORMATTER, converter.getParsers().get(0));
        assertSame(PARSER1, converter.getParsers().get(1));
        assertSame(PARSER2, converter.getParsers().get(2));
        assertSame(PARSER3, converter.getParsers().get(3));
    }

    /**
     * Tests {@link AbstractDateTimeConverter#addParsers(DateTimeFormatter...)} method.
     */
    @Test
    public void testSetParsersVarargs() {
        final AbstractDateTimeConverter converter = new TestDataTimeConverter(
                FORMATTER);
        converter.setParsers(PARSER1);
        assertEquals(1, converter.getParsers().size());
        assertTrue(converter.getParsers().contains(PARSER1));
        converter.setParsers(PARSER2, PARSER3);
        assertEquals(2, converter.getParsers().size());
        assertFalse(converter.getParsers().contains(PARSER1));
        assertTrue(converter.getParsers().contains(PARSER2));
        assertTrue(converter.getParsers().contains(PARSER3));
        assertSame(PARSER2, converter.getParsers().get(0));
        assertSame(PARSER3, converter.getParsers().get(1));
    }

    /**
     * Tests {@link AbstractDateTimeConverter#addParsers(java.util.Collection)} method.
     */
    @Test
    public void testSetParsersCollection() {
        final AbstractDateTimeConverter converter = new TestDataTimeConverter(
                FORMATTER);
        converter.setParsers(Arrays.asList(PARSER1));
        assertEquals(1, converter.getParsers().size());
        assertTrue(converter.getParsers().contains(PARSER1));
        converter.setParsers(Arrays.asList(PARSER2, PARSER3));
        assertEquals(2, converter.getParsers().size());
        assertFalse(converter.getParsers().contains(PARSER1));
        assertTrue(converter.getParsers().contains(PARSER2));
        assertTrue(converter.getParsers().contains(PARSER3));
        assertSame(PARSER2, converter.getParsers().get(0));
        assertSame(PARSER3, converter.getParsers().get(1));
    }

    /**
     * Tests {@link AbstractDateTimeConverter#convertToType(Class, Object)} method.
     * @throws Throwable Should not happen
     */
    @Test
    public void testConvertToTemporalAccessorFromTemporalAccessor()
    throws Throwable {
        final TestDataTimeConverter converter = new TestDataTimeConverter(FORMATTER);
        converter.setParsers(Collections.emptyList());
        final TestDataTimeConverter converterSpy = spy(converter);
        final Class<TemporalAccessor> type = TemporalAccessor.class;
        final Instant value = Instant.now();
        final LocalDate expectedResult = LocalDate.now();
        doReturn(expectedResult)
                .when(converterSpy)
                .fromTemporalAccessor(same(type), eq(value));
        final TemporalAccessor result = converterSpy.convertToType(type, value);
        assertSame(expectedResult, result);
        then(converterSpy).should(times(1)).convertToType(TemporalAccessor.class, value);
        then(converterSpy).should(times(1)).fromTemporalAccessor(same(type), eq(value));
    }

    /**
     * Tests {@link AbstractDateTimeConverter#convertToType(Class, Object)} method.
     * @throws Throwable Should not happen
     */
    @Test
    public void testConvertToTemporalAccessorFromLong()
    throws Throwable {
        final TestDataTimeConverter converter = new TestDataTimeConverter(FORMATTER);
        converter.setParsers(Collections.emptyList());
        final TestDataTimeConverter converterSpy = spy(converter);
        final Class<TemporalAccessor> type = TemporalAccessor.class;
        final Long value = System.currentTimeMillis();
        final Instant valueInstant = Instant.ofEpochMilli(value);
        final LocalDate expectedResult = LocalDate.now();
        doReturn(expectedResult)
                .when(converterSpy)
                .fromTemporalAccessor(same(type), eq(valueInstant));
        final TemporalAccessor result = converterSpy.convertToType(type, value);
        assertSame(expectedResult, result);
        then(converterSpy).should(times(1)).convertToType(TemporalAccessor.class, value);
        then(converterSpy).should(times(1)).fromTemporalAccessor(same(type), eq(valueInstant));
    }

    /**
     * Tests {@link AbstractDateTimeConverter#convertToType(Class, Object)} method.
     * @throws Throwable Should not happen
     */
    @Test
    public void testConvertToTemporalAccessorFromDate()
    throws Throwable {
        final TestDataTimeConverter converter = new TestDataTimeConverter(FORMATTER);
        converter.setParsers(Collections.emptyList());
        final TestDataTimeConverter converterSpy = spy(converter);
        final Class<TemporalAccessor> type = TemporalAccessor.class;
        final Date value = new Date();
        final Instant valueInstant = value.toInstant();
        final LocalDate expectedResult = LocalDate.now();
        doReturn(expectedResult)
                .when(converterSpy)
                .fromTemporalAccessor(same(type), eq(valueInstant));
        final TemporalAccessor result = converterSpy.convertToType(type, value);
        assertSame(expectedResult, result);
        then(converterSpy).should(times(1)).convertToType(TemporalAccessor.class, value);
        then(converterSpy).should(times(1)).fromTemporalAccessor(same(type), eq(valueInstant));
    }

    /**
     * Tests {@link AbstractDateTimeConverter#convertToType(Class, Object)} method.
     * @throws Throwable Should not happen
     */
    @Test
    public void testConvertToTemporalAccessorFromGregorianCalendar()
    throws Throwable {
        final TestDataTimeConverter converter = new TestDataTimeConverter(FORMATTER);
        converter.setParsers(Collections.emptyList());
        final TestDataTimeConverter converterSpy = spy(converter);
        final Class<TemporalAccessor> type = TemporalAccessor.class;
        final GregorianCalendar value = (GregorianCalendar) GregorianCalendar.getInstance();
        final ZonedDateTime valueDateTime = value.toZonedDateTime();
        final LocalDate expectedResult = LocalDate.now();
        doReturn(expectedResult)
                .when(converterSpy)
                .fromTemporalAccessor(same(type), eq(valueDateTime));
        final TemporalAccessor result = converterSpy.convertToType(type, value);
        assertSame(expectedResult, result);
        then(converterSpy).should(times(1)).convertToType(TemporalAccessor.class, value);
        then(converterSpy).should(times(1)).fromTemporalAccessor(same(type), eq(valueDateTime));
    }

    /**
     * Tests {@link AbstractDateTimeConverter#convertToType(Class, Object)} method.
     * @throws Throwable Should not happen
     */
    @Test
    public void testConvertToTemporalAccessorFromCalendar()
    throws Throwable {
        final TestDataTimeConverter converter = new TestDataTimeConverter(FORMATTER);
        converter.setParsers(Collections.emptyList());
        final TestDataTimeConverter converterSpy = spy(converter);
        final Class<TemporalAccessor> type = TemporalAccessor.class;
        final Calendar value = mock(Calendar.class);
        final Instant valueInstant = Instant.now();
        final LocalDate expectedResult = LocalDate.now();
        doReturn(valueInstant.toEpochMilli())
                .when(value)
                .getTimeInMillis();
        doReturn(expectedResult)
                .when(converterSpy)
                .fromTemporalAccessor(same(type), eq(valueInstant));
        final TemporalAccessor result = converterSpy.convertToType(type, value);
        assertSame(expectedResult, result);
        then(converterSpy).should(times(1)).convertToType(TemporalAccessor.class, value);
        then(converterSpy).should(times(1)).fromTemporalAccessor(same(type), eq(valueInstant));
    }

    /**
     * Tests {@link AbstractDateTimeConverter#convertToType(Class, Object)} method.
     * @throws Throwable Should not happen
     */
    @Test
    public void testConvertToTemporalAccessorFromString()
    throws Throwable {
        final TestDataTimeConverter converter = new TestDataTimeConverter(FORMATTER);
        converter.setParsers(Collections.emptyList());
        final TestDataTimeConverter converterSpy = spy(converter);
        final Class<TemporalAccessor> type = TemporalAccessor.class;
        final String value = "mock value";
        final LocalDate expectedResult = LocalDate.now();
        doReturn(expectedResult)
                .when(converterSpy)
                .parseString(same(type), same(value));
        final TemporalAccessor result = converterSpy.convertToType(type, value);
        assertSame(expectedResult, result);
        then(converterSpy).should(times(1)).convertToType(TemporalAccessor.class, value);
        then(converterSpy).should(times(1)).parseString(same(type), same(value));
    }

    /**
     * Tests {@link AbstractDateTimeConverter#convertToType(Class, Object)} method.
     * @throws Throwable Should not happen
     */
    @Test
    public void testConvertToTemporalAccessorFromOther()
    throws Throwable {
        final TestDataTimeConverter converter = new TestDataTimeConverter(FORMATTER);
        converter.setParsers(Collections.emptyList());
        final TestDataTimeConverter converterSpy = spy(converter);
        final Class<TemporalAccessor> type = TemporalAccessor.class;
        final Object value = mock(Object.class);
        final String mockValueToString = "mock to string";
        final LocalDate expectedResult = LocalDate.now();
        given(value.toString()).willReturn(mockValueToString);
        doReturn(expectedResult)
                .when(converterSpy)
                .parseString(same(type), same(mockValueToString));
        final TemporalAccessor result = converterSpy.convertToType(type, value);
        assertSame(expectedResult, result);
        then(converterSpy).should(times(1)).convertToType(TemporalAccessor.class, value);
        then(converterSpy).should(times(1)).parseString(same(type), same(mockValueToString));
    }

    /**
     * Tests {@link AbstractDateTimeConverter#parseString(Class, String)} method.
     * @throws Throwable Should not happen
     */
    @Test
    public void testParseStringNoParsers()
    throws Throwable {
        final TestDataTimeConverter converter = new TestDataTimeConverter(FORMATTER);
        converter.setParsers(Collections.emptyList());
        final TestDataTimeConverter converterSpy = spy(converter);
        final Class<TemporalAccessor> type = TemporalAccessor.class;
        final String value = "mock value";
        assertThrows(ConversionException.class, () -> {
            converterSpy.parseString(type, value);
        });
        then(converterSpy).should(times(1)).parseString(TemporalAccessor.class, value);
        then(converterSpy).should(times(0)).parse(any(), any(), any());
    }

    /**
     * Tests {@link AbstractDateTimeConverter#parseString(Class, String)} method.
     * @throws Throwable Should not happen
     */
    @Test
    public void testParseStringDefaultParserSuccess()
    throws Throwable {
        final TestDataTimeConverter converter = new TestDataTimeConverter(FORMATTER);
        final TestDataTimeConverter converterSpy = spy(converter);
        final Class<TemporalAccessor> type = TemporalAccessor.class;
        final String value = "mock value";
        final TemporalAccessor expectedResult = Instant.now();
        doReturn(expectedResult)
                .when(converterSpy)
                .parse(type, FORMATTER, value);
        final TemporalAccessor result = converterSpy.parseString(type, value);
        assertSame(expectedResult, result);
        then(converterSpy).should(times(1)).parseString(TemporalAccessor.class, value);
        then(converterSpy).should(times(1)).parse(type, FORMATTER, value);
    }

    /**
     * Tests {@link AbstractDateTimeConverter#parseString(Class, String)} method.
     * @throws Throwable Should not happen
     */
    @Test
    public void testParseStringDefaultParserFail()
    throws Throwable {
        final TestDataTimeConverter converter = new TestDataTimeConverter(FORMATTER);
        final TestDataTimeConverter converterSpy = spy(converter);
        final Class<TemporalAccessor> type = TemporalAccessor.class;
        final Logger logger = mock(Logger.class);
        final String value = "mock value";
        final DateTimeException mockException = new DateTimeException("mock error");
        willThrow(mockException)
                .given(converterSpy)
                .parse(type, FORMATTER, value);
        doReturn(logger)
                .when(converterSpy)
                .getLogger();
        final DateTimeException thrown = assertThrows(DateTimeException.class, () -> {
            converterSpy.parseString(type, value);
        });
        assertSame(thrown, mockException);
        then(converterSpy).should(times(1)).parseString(TemporalAccessor.class, value);
        then(converterSpy).should(times(1)).parse(type, FORMATTER, value);
        then(logger).should(times(1)).debug(any(String.class), same(mockException));
    }

    /**
     * Tests {@link AbstractDateTimeConverter#parseString(Class, String)} method.
     * @throws Throwable Should not happen
     */
    @Test
    public void testParseStringExtraParserSuccess()
    throws Throwable {
        final TestDataTimeConverter converter = new TestDataTimeConverter(FORMATTER);
        converter.addParsers(PARSER1, PARSER2, PARSER3);
        final TestDataTimeConverter converterSpy = spy(converter);
        final Class<TemporalAccessor> type = TemporalAccessor.class;
        final Logger logger = mock(Logger.class);
        final String value = "mock value";
        final TemporalAccessor expectedResult = Instant.now();
        final DateTimeException mockExceptionDefault =
                new DateTimeException("mock default parser error");
        final DateTimeException mockExceptionExtra1 =
                new DateTimeException("mock extra parser 1 error");
        willThrow(mockExceptionDefault)
                .given(converterSpy)
                .parse(type, FORMATTER, value);
        willThrow(mockExceptionExtra1)
                .given(converterSpy)
                .parse(type, PARSER1, value);
        doReturn(expectedResult)
                .when(converterSpy)
                .parse(type, PARSER2, value);
        doReturn(logger)
                .when(converterSpy)
                .getLogger();
        final TemporalAccessor result = converterSpy.parseString(type, value);
        assertSame(expectedResult, result);
        then(converterSpy).should(times(1)).parseString(TemporalAccessor.class, value);
        then(converterSpy).should(times(1)).parse(type, FORMATTER, value);
        then(converterSpy).should(times(1)).parse(type, PARSER1, value);
        then(converterSpy).should(times(1)).parse(type, PARSER2, value);
        then(converterSpy).should(times(0)).parse(type, PARSER3, value);
        then(logger).should(times(1)).debug(any(String.class), same(mockExceptionDefault));
        then(logger).should(times(1)).debug(any(String.class), same(mockExceptionExtra1));
    }

    /**
     * Tests {@link AbstractDateTimeConverter#parseString(Class, String)} method.
     * @throws Throwable Should not happen
     */
    @Test
    public void testParseStringMultipleParsersFail()
    throws Throwable {
        final TestDataTimeConverter converter = new TestDataTimeConverter(FORMATTER);
        converter.addParsers(PARSER1, PARSER2, PARSER3);
        final TestDataTimeConverter converterSpy = spy(converter);
        final Class<TemporalAccessor> type = TemporalAccessor.class;
        final Logger logger = mock(Logger.class);
        final String value = "mock value";
        final DateTimeException mockExceptionDefault =
                new DateTimeException("mock default parser error");
        final DateTimeException mockExceptionExtra1 =
                new DateTimeException("mock extra parser 1 error");
        final DateTimeException mockExceptionExtra2 =
                new DateTimeException("mock extra parser 2 error");
        final DateTimeException mockExceptionExtra3 =
                new DateTimeException("mock extra parser 3 error");
        willThrow(mockExceptionDefault)
                .given(converterSpy)
                .parse(type, FORMATTER, value);
        willThrow(mockExceptionExtra1)
                .given(converterSpy)
                .parse(type, PARSER1, value);
        willThrow(mockExceptionExtra2)
                .given(converterSpy)
                .parse(type, PARSER2, value);
        willThrow(mockExceptionExtra3)
                .given(converterSpy)
                .parse(type, PARSER3, value);
        doReturn(logger)
                .when(converterSpy)
                .getLogger();
        final DateTimeException thrown = assertThrows(DateTimeException.class, () -> {
            converterSpy.parseString(type, value);
        });
        assertSame(thrown, mockExceptionDefault);
        then(converterSpy).should(times(1)).parseString(TemporalAccessor.class, value);
        then(converterSpy).should(times(1)).parse(type, FORMATTER, value);
        then(converterSpy).should(times(1)).parse(type, PARSER1, value);
        then(converterSpy).should(times(1)).parse(type, PARSER2, value);
        then(converterSpy).should(times(1)).parse(type, PARSER3, value);
        then(logger).should(times(1)).debug(any(String.class), same(mockExceptionDefault));
        then(logger).should(times(1)).debug(any(String.class), same(mockExceptionExtra1));
        then(logger).should(times(1)).debug(any(String.class), same(mockExceptionExtra2));
        then(logger).should(times(1)).debug(any(String.class), same(mockExceptionExtra3));
    }

    /**
     * Tests {@link AbstractDateTimeConverter#convertToType(Class, Object)} method.
     * @throws Throwable Should not happen
     */
    @Test
    public void testConvertToOtherType()
    throws Throwable {
        final TestDataTimeConverter converter = spy(
                new TestDataTimeConverter(FORMATTER));
        final Object value = new Object();
        assertThrows(ConversionException.class, () -> {
            converter.convertToType(Object.class, value);
        });
        then(converter).should(times(1)).convertToType(Object.class, value);
        then(converter).should(times(1)).conversionException(
                Object.class, value);
        then(converter).shouldHaveNoMoreInteractions();
    }

    /**
     * Tests {@link AbstractDateTimeConverter#convertToString(Object)} method.
     * @throws Throwable Should not happen
     */
    @Test
    public void testConvertToStringFromTemporal() throws Throwable {
        final AbstractDateTimeConverter converter = spy(
                new TestDataTimeConverter(FORMATTER));
        final TemporalAccessor value = Instant.now();
        final String result = converter.convertToString(value);
        assertEquals(FORMATTER.format(value), result);
        then(converter).should(times(1)).convertToString(value);
        then(converter).shouldHaveNoMoreInteractions();
    }

    /**
     * Tests {@link AbstractDateTimeConverter#convertToString(Object)} method.
     * @throws Throwable Should not happen
     */
    @Test
    public void testConvertToStringFromTemporalFail()
    throws Throwable {
        final TestDataTimeConverter converter = spy(
                new TestDataTimeConverter(FORMATTER));
        final TemporalAccessor value = Month.APRIL;
        assertThrows(DateTimeException.class, () -> {
            converter.convertToString(value);
        });
        then(converter).should(times(1)).convertToString(value);
        then(converter).shouldHaveNoMoreInteractions();
    }

    /**
     * Tests {@link AbstractDateTimeConverter#convertToString(Object)} method.
     * @throws Throwable Should not happen
     */
    @Test
    public void testConvertToStringFromString()
    throws Throwable {
        final AbstractDateTimeConverter converter = spy(
                new TestDataTimeConverter(FORMATTER));
        final String value = "some string";
        final String result = converter.convertToString(value);
        assertSame(value, result);
        then(converter).should(times(1)).convertToString(value);
        then(converter).shouldHaveNoMoreInteractions();
    }

    /**
     * Tests {@link AbstractDateTimeConverter#convertToString(Object)} method.
     * @throws Throwable Should not happen
     */
    @Test
    public void testConvertToStringFromOther()
    throws Throwable {
        final TestDataTimeConverter converter = spy(
                new TestDataTimeConverter(FORMATTER));
        final Object value = new Object();
        assertThrows(ConversionException.class, () -> {
            converter.convertToString(value);
        });
        then(converter).should(times(1)).convertToString(value);
        then(converter).should(times(1)).conversionException(
                String.class, value);
        then(converter).shouldHaveNoMoreInteractions();
    }

    private class TestDataTimeConverter
    extends AbstractDateTimeConverter {
        /**
         * No default value constructor.
         * @param formatter Formatter and default parser.
         */
        public TestDataTimeConverter(
                final DateTimeFormatter formatter) {
            super(formatter);
        }
        /**
         * Default value constructor.
         * @param formatter Formatter and default parser.
         * @param defaultValue Default value
         */
        public TestDataTimeConverter(
                final DateTimeFormatter formatter,
                final TemporalAccessor defaultValue) {
            super(formatter, defaultValue);
        }
        @Override
        protected Class<?> getDefaultType() {
            return Instant.class;
        }
        @Override
        protected <T extends TemporalAccessor> T parse(
                Class<T> type,
                DateTimeFormatter parser,
                String value)
        throws IllegalArgumentException {
            return null;
        }
        @Override
        protected <T extends TemporalAccessor> T fromTemporalAccessor(
                Class<T> type,
                TemporalAccessor value) {
            return null;
        }
        @Override
        public ConversionException conversionException(
                Class<?> type,
                Object value) {
            return super.conversionException(type, value);
        }
    }
}
