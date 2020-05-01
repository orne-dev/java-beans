/**
 * 
 */
package dev.orne.beans.converters;

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
public class YearConverterTest
extends AbstractTimeConverterTest {

    public YearConverterTest() {
        super(Year.class, new YearConverter());
    }

    /**
     * Test {@link YearConverter#YearConverter()}.
     */
    @Test
    public void testConstructor() {
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
    public void testConstructorDefaultValue() {
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
    public void testConstructorFormatter() {
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
    public void testConstructorFormatterDefaultValue() {
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
    public void testFromValueInvalidConversions() {
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
    public void testFromValueInvalidConversionsWithDefaultValue() {
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
    public void testFromValueValidConversions() {
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
    public void testFromStringInvalidConversions() {
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
    public void testFromStringInvalidConversionsWithDefaultValue() {
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
    public void testFromStringValidConversions() {
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
    public void testValidToStringConversions() {
        final YearConverter converter = new YearConverter();
        assertNull(converter.convert(String.class, null));
        assertSuccess(converter, String.class, "", "");
        assertSuccess(converter, String.class, STR_NON_DATE, STR_NON_DATE);
        assertSuccess(converter, String.class, YEAR, YEAR.toString());
    }
}
