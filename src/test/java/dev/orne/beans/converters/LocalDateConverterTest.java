/**
 * 
 */
package dev.orne.beans.converters;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code LocalDateConverter}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see LocalDateConverter
 */
@Tag("ut")
public class LocalDateConverterTest
extends AbstractTimeConverterTest {

    public LocalDateConverterTest() {
        super(LocalDate.class, new LocalDateConverter());
    }

    /**
     * Test {@link LocalDateConverter#LocalDateConverter()}.
     */
    @Test
    public void testConstructor() {
        final LocalDateConverter converter = new LocalDateConverter();
        assertConstructor(converter,
                DateTimeFormatter.ISO_LOCAL_DATE,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_DATE_TIME,
                DateTimeFormatter.ISO_DATE,
                DateTimeFormatter.ISO_ORDINAL_DATE,
                DateTimeFormatter.ISO_WEEK_DATE,
                DateTimeFormatter.BASIC_ISO_DATE,
                InstantConverter.EPOCH_MILLIS_PARSER);
    }

    /**
     * Test {@link LocalDateConverter#LocalDateConverter(LocalDate)}.
     */
    @Test
    public void testConstructorDefaultValue() {
        final LocalDateConverter converter = new LocalDateConverter(
                (LocalDate) null);
        assertConstructor(converter,
                DateTimeFormatter.ISO_LOCAL_DATE,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_DATE_TIME,
                DateTimeFormatter.ISO_DATE,
                DateTimeFormatter.ISO_ORDINAL_DATE,
                DateTimeFormatter.ISO_WEEK_DATE,
                DateTimeFormatter.BASIC_ISO_DATE,
                InstantConverter.EPOCH_MILLIS_PARSER);
    }

    /**
     * Test {@link LocalDateConverter#LocalDateConverter(DateTimeFormatter)}.
     */
    @Test
    public void testConstructorFormatter() {
        final LocalDateConverter converter = new LocalDateConverter(
                DateTimeFormatter.RFC_1123_DATE_TIME);
        assertConstructor(converter,
                DateTimeFormatter.RFC_1123_DATE_TIME,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_DATE_TIME,
                DateTimeFormatter.ISO_DATE,
                DateTimeFormatter.ISO_ORDINAL_DATE,
                DateTimeFormatter.ISO_WEEK_DATE,
                DateTimeFormatter.BASIC_ISO_DATE,
                InstantConverter.EPOCH_MILLIS_PARSER);
    }

    /**
     * Test {@link LocalDateConverter#LocalDateConverter(DateTimeFormatter, LocalDateTime)}.
     */
    @Test
    public void testConstructorFormatterDefaultValue() {
        final LocalDateConverter converter = new LocalDateConverter(
                DateTimeFormatter.RFC_1123_DATE_TIME,
                (LocalDate) null);
        assertConstructor(converter,
                DateTimeFormatter.RFC_1123_DATE_TIME,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_DATE_TIME,
                DateTimeFormatter.ISO_DATE,
                DateTimeFormatter.ISO_ORDINAL_DATE,
                DateTimeFormatter.ISO_WEEK_DATE,
                DateTimeFormatter.BASIC_ISO_DATE,
                InstantConverter.EPOCH_MILLIS_PARSER);
    }

    /**
     * Test {@link LocalDateConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    public void testFromValueInvalidConversions() {
        assertFail(null);
        assertFail(YEAR);
        assertFail(YEAR_MONTH);
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
     * Test {@link LocalDateConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    public void testFromValueInvalidConversionsWithDefaultValue() {
        final LocalDate defaultValue = null;
        final LocalDateConverter converter = new LocalDateConverter(defaultValue);
        assertSuccess(converter, (Object) null, defaultValue, defaultValue);
        assertSuccess(converter, YEAR, defaultValue, defaultValue);
        assertSuccess(converter, YEAR_MONTH, defaultValue, defaultValue);
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
     * Test {@link LocalDateConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is a {@code LocalTime}
     * ISO-8601 representation.
     */
    @Test
    public void testFromValueValidConversions() {
        assertSuccess(ZONED_DATE_TIME, LOCAL_DATE);
        assertSuccess(OFFSET_DATE_TIME, LOCAL_DATE);
        assertSuccess(LOCAL_DATE_TIME, LOCAL_DATE);
        assertSuccess(LOCAL_DATE, LOCAL_DATE);
        assertSuccess(INSTANT, UTC_LOCAL_DATE);
        assertSuccess(EPOCH_MILLIS, UTC_LOCAL_DATE);
    }

    /**
     * Test {@link LocalDateConverter#convert(Class, Object)} when
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
     * Test {@link LocalDateConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    public void testFromStringInvalidConversionsWithDefaultValue() {
        final LocalDate defaultValue = null;
        final LocalDateConverter converter = new LocalDateConverter(defaultValue);
        assertSuccess(converter, STR_EMPTY, defaultValue, defaultValue);
        assertSuccess(converter, STR_NON_DATE, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_YEAR_MONTH, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_MONTH_DAY, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_OFFSET_TIME, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_LOCAL_TIME, defaultValue, defaultValue);
        assertSuccess(converter, STR_RFC_1123_DATE_TIME, defaultValue, defaultValue);
    }

    /**
     * Test {@link LocalDateConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is a {@code LocalTime}
     * ISO-8601 representation.
     */
    @Test
    public void testFromStringValidConversions() {
        assertSuccess(STR_ISO_ZONED_DATE_TIME, LOCAL_DATE);
        assertSuccess(STR_ISO_OFFSET_DATE_TIME, LOCAL_DATE);
        assertSuccess(STR_ISO_LOCAL_DATE_TIME, LOCAL_DATE);
        assertSuccess(STR_ISO_OFFSET_DATE, LOCAL_DATE);
        assertSuccess(STR_ISO_LOCAL_DATE, LOCAL_DATE);
        assertSuccess(STR_ISO_BASIC_DATE, LOCAL_DATE);
        assertSuccess(STR_ISO_ORDINAL_DATE, LOCAL_DATE);
        assertSuccess(STR_ISO_WEEK_DATE, LOCAL_DATE);
        assertSuccess(STR_ISO_INSTANT, UTC_LOCAL_DATE);
        assertSuccess(STR_EPOCH_MILLIS, UTC_LOCAL_DATE);
    }

    /**
     * Test {@link LocalDateConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is a {@code LocalTime}
     * ISO-8601 representation.
     */
    @Test
    public void testValidToStringConversions() {
        final LocalDateConverter converter = new LocalDateConverter();
        assertNull(converter.convert(String.class, null));
        assertSuccess(converter, String.class, "", "");
        assertSuccess(converter, String.class, STR_NON_DATE, STR_NON_DATE);
        assertSuccess(converter, String.class, LOCAL_DATE, STR_ISO_LOCAL_DATE);
    }
}
