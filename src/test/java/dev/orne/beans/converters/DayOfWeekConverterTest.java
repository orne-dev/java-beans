/**
 * 
 */
package dev.orne.beans.converters;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code DayOfWeekConverter}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see DayOfWeekConverter
 */
@Tag("ut")
public class DayOfWeekConverterTest
extends AbstractTimeConverterTest {

    public DayOfWeekConverterTest() {
        super(DayOfWeek.class, new DayOfWeekConverter());
    }

    /**
     * Test {@link DayOfWeekConverter#DayOfWeekConverter()}.
     */
    @Test
    public void testConstructor() {
        final DayOfWeekConverter converter = new DayOfWeekConverter();
        assertConstructor(converter,
                DayOfWeekConverter.BY_VALUE_PARSER,
                DayOfWeekConverter.BY_VALUE_PARSER,
                DayOfWeekConverter.BY_FULL_TEXT_PARSER,
                DayOfWeekConverter.BY_SHORT_TEXT_PARSER,
                DayOfWeekConverter.BY_NARROW_TEXT_PARSER,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_DATE_TIME,
                DateTimeFormatter.ISO_DATE,
                DateTimeFormatter.ISO_ORDINAL_DATE,
                DateTimeFormatter.ISO_WEEK_DATE,
                DateTimeFormatter.BASIC_ISO_DATE,
                InstantConverter.EPOCH_MILLIS_PARSER);
    }

    /**
     * Test {@link DayOfWeekConverter#DayOfWeekConverter(DayOfWeek)}.
     */
    @Test
    public void testConstructorDefaultValue() {
        final DayOfWeekConverter converter = new DayOfWeekConverter(
                (DayOfWeek) null);
        assertConstructor(converter,
                DayOfWeekConverter.BY_VALUE_PARSER,
                DayOfWeekConverter.BY_VALUE_PARSER,
                DayOfWeekConverter.BY_FULL_TEXT_PARSER,
                DayOfWeekConverter.BY_SHORT_TEXT_PARSER,
                DayOfWeekConverter.BY_NARROW_TEXT_PARSER,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_DATE_TIME,
                DateTimeFormatter.ISO_DATE,
                DateTimeFormatter.ISO_ORDINAL_DATE,
                DateTimeFormatter.ISO_WEEK_DATE,
                DateTimeFormatter.BASIC_ISO_DATE,
                InstantConverter.EPOCH_MILLIS_PARSER);
    }

    /**
     * Test {@link DayOfWeekConverter#DayOfWeekConverter(DateTimeFormatter)}.
     */
    @Test
    public void testConstructorFormatter() {
        final DayOfWeekConverter converter = new DayOfWeekConverter(
                DateTimeFormatter.RFC_1123_DATE_TIME);
        assertConstructor(converter,
                DateTimeFormatter.RFC_1123_DATE_TIME,
                DayOfWeekConverter.BY_VALUE_PARSER,
                DayOfWeekConverter.BY_FULL_TEXT_PARSER,
                DayOfWeekConverter.BY_SHORT_TEXT_PARSER,
                DayOfWeekConverter.BY_NARROW_TEXT_PARSER,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_DATE_TIME,
                DateTimeFormatter.ISO_DATE,
                DateTimeFormatter.ISO_ORDINAL_DATE,
                DateTimeFormatter.ISO_WEEK_DATE,
                DateTimeFormatter.BASIC_ISO_DATE,
                InstantConverter.EPOCH_MILLIS_PARSER);
    }

    /**
     * Test {@link DayOfWeekConverter#DayOfWeekConverter(DateTimeFormatter, DayOfWeek)}.
     */
    @Test
    public void testConstructorFormatterDefaultValue() {
        final DayOfWeekConverter converter = new DayOfWeekConverter(
                DateTimeFormatter.RFC_1123_DATE_TIME,
                (DayOfWeek) null);
        assertConstructor(converter,
                DateTimeFormatter.RFC_1123_DATE_TIME,
                DayOfWeekConverter.BY_VALUE_PARSER,
                DayOfWeekConverter.BY_FULL_TEXT_PARSER,
                DayOfWeekConverter.BY_SHORT_TEXT_PARSER,
                DayOfWeekConverter.BY_NARROW_TEXT_PARSER,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_DATE_TIME,
                DateTimeFormatter.ISO_DATE,
                DateTimeFormatter.ISO_ORDINAL_DATE,
                DateTimeFormatter.ISO_WEEK_DATE,
                DateTimeFormatter.BASIC_ISO_DATE,
                InstantConverter.EPOCH_MILLIS_PARSER);
    }

    /**
     * Test {@link DayOfWeekConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    public void testFromValueInvalidConversions() {
        assertFail(null);
        assertFail(YEAR);
        assertFail(YEAR_MONTH);
        assertFail(MONTH_DAY);
        assertFail(MONTH);
        assertFail(OFFSET_TIME);
        assertFail(LOCAL_TIME);
        assertFail(ZONE_ID);
        assertFail(ZONE_OFFSET);
        assertFail(WRONG_TYPE_VALUE);
    }

    /**
     * Test {@link DayOfWeekConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    public void testFromValueInvalidConversionsWithDefaultValue() {
        final DayOfWeek defaultValue = null;
        final DayOfWeekConverter converter = new DayOfWeekConverter(defaultValue);
        assertSuccess(converter, null, defaultValue, defaultValue);
        assertSuccess(converter, YEAR, defaultValue, defaultValue);
        assertSuccess(converter, YEAR_MONTH, defaultValue, defaultValue);
        assertSuccess(converter, MONTH_DAY, defaultValue, defaultValue);
        assertSuccess(converter, MONTH, defaultValue, defaultValue);
        assertSuccess(converter, OFFSET_TIME, defaultValue, defaultValue);
        assertSuccess(converter, LOCAL_TIME, defaultValue, defaultValue);
        assertSuccess(converter, ZONE_ID, defaultValue, defaultValue);
        assertSuccess(converter, ZONE_OFFSET, defaultValue, defaultValue);
        assertSuccess(converter, WRONG_TYPE_VALUE, defaultValue, defaultValue);
    }

    /**
     * Test {@link DayOfWeekConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is a {@code LocalTime}
     * ISO-8601 representation.
     */
    @Test
    public void testFromValueValidConversions() {
        assertSuccess(ZONED_DATE_TIME, DAY_OF_WEEK);
        assertSuccess(OFFSET_DATE_TIME, DAY_OF_WEEK);
        assertSuccess(LOCAL_DATE_TIME, DAY_OF_WEEK);
        assertSuccess(LOCAL_DATE, DAY_OF_WEEK);
        assertSuccess(DAY_OF_WEEK, DAY_OF_WEEK);
        assertSuccess(INSTANT, UTC_DAY_OF_WEEK);
        assertSuccess(EPOCH_MILLIS, UTC_DAY_OF_WEEK);
        assertSuccess(DAY_OF_WEEK.getValue(), DAY_OF_WEEK);
    }

    /**
     * Test {@link DayOfWeekConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    public void testFromStringInvalidConversions() {
        assertFail(STR_EMPTY);
        assertFail(STR_NON_DATE);
        assertFail(STR_ISO_YEAR_MONTH);
        assertFail(STR_ISO_MONTH_DAY);
        assertFail(STR_ISO_OFFSET_TIME);
        assertFail(STR_ISO_LOCAL_TIME);
        assertFail(STR_RFC_1123_DATE_TIME);
    }

    /**
     * Test {@link DayOfWeekConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    public void testFromStringInvalidConversionsWithDefaultValue() {
        final DayOfWeek defaultValue = null;
        final DayOfWeekConverter converter = new DayOfWeekConverter(defaultValue);
        assertSuccess(converter, STR_EMPTY, defaultValue, defaultValue);
        assertSuccess(converter, STR_NON_DATE, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_YEAR_MONTH, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_MONTH_DAY, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_OFFSET_TIME, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_LOCAL_TIME, defaultValue, defaultValue);
        assertSuccess(converter, STR_RFC_1123_DATE_TIME, defaultValue, defaultValue);
    }

    /**
     * Test {@link DayOfWeekConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is a {@code LocalTime}
     * ISO-8601 representation.
     */
    @Test
    public void testFromStringValidConversions() {
        assertSuccess(STR_ISO_ZONED_DATE_TIME, DAY_OF_WEEK);
        assertSuccess(STR_ISO_OFFSET_DATE_TIME, DAY_OF_WEEK);
        assertSuccess(STR_ISO_LOCAL_DATE_TIME, DAY_OF_WEEK);
        assertSuccess(STR_ISO_OFFSET_DATE, DAY_OF_WEEK);
        assertSuccess(STR_ISO_LOCAL_DATE, DAY_OF_WEEK);
        assertSuccess(STR_ISO_BASIC_DATE, DAY_OF_WEEK);
        assertSuccess(STR_ISO_ORDINAL_DATE, DAY_OF_WEEK);
        assertSuccess(STR_ISO_WEEK_DATE, DAY_OF_WEEK);
        assertSuccess(STR_ISO_INSTANT, UTC_DAY_OF_WEEK);
        assertSuccess(STR_EPOCH_MILLIS, UTC_DAY_OF_WEEK);
        assertSuccess(DAY_OF_WEEK.name(), DAY_OF_WEEK);
    }

    /**
     * Test {@link DayOfWeekConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is a {@code LocalTime}
     * ISO-8601 representation.
     */
    @Test
    public void testValidToStringConversions() {
        final DayOfWeekConverter converter = new DayOfWeekConverter();
        assertNull(converter.convert(String.class, null));
        assertSuccess(converter, String.class, "", "");
        assertSuccess(converter, String.class, STR_NON_DATE, STR_NON_DATE);
        assertSuccess(converter, String.class, DAY_OF_WEEK, DAY_OF_WEEK.name());
    }
}
