/**
 * 
 */
package dev.orne.beans.converters;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code InstantConverter}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see InstantConverter
 */
@Tag("ut")
public class InstantConverterTest
extends AbstractTimeConverterTest {

    public InstantConverterTest() {
        super(Instant.class, new InstantConverter());
    }

    /**
     * Test {@link InstantConverter#InstantConverter()}.
     */
    @Test
    public void testConstructor() {
        final InstantConverter converter = new InstantConverter();
        assertConstructor(converter,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_DATE_TIME,
                InstantConverter.EPOCH_MILLIS_PARSER);
    }

    /**
     * Test {@link InstantConverter#InstantConverter(LocalDate)}.
     */
    @Test
    public void testConstructorDefaultValue() {
        final InstantConverter converter = new InstantConverter(
                (Instant) null);
        assertConstructor(converter,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_DATE_TIME,
                InstantConverter.EPOCH_MILLIS_PARSER);
    }

    /**
     * Test {@link InstantConverter#InstantConverter(DateTimeFormatter)}.
     */
    @Test
    public void testConstructorFormatter() {
        final InstantConverter converter = new InstantConverter(
                DateTimeFormatter.RFC_1123_DATE_TIME);
        assertConstructor(converter,
                DateTimeFormatter.RFC_1123_DATE_TIME,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_DATE_TIME,
                InstantConverter.EPOCH_MILLIS_PARSER);
    }

    /**
     * Test {@link InstantConverter#InstantConverter(DateTimeFormatter, Instant)}.
     */
    @Test
    public void testConstructorFormatterDefaultValue() {
        final InstantConverter converter = new InstantConverter(
                DateTimeFormatter.RFC_1123_DATE_TIME,
                (Instant) null);
        assertConstructor(converter,
                DateTimeFormatter.RFC_1123_DATE_TIME,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_DATE_TIME,
                InstantConverter.EPOCH_MILLIS_PARSER);
    }

    /**
     * Test {@link InstantConverter#convert(Class, Object)} when
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
        assertFail(OFFSET_TIME);
        assertFail(LOCAL_TIME);
        assertFail(ZONE_ID);
        assertFail(ZONE_OFFSET);
        assertFail(WRONG_TYPE_VALUE);
    }

    /**
     * Test {@link InstantConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    public void testFromValueInvalidConversionsWithDefaultValue() {
        final Instant defaultValue = null;
        final InstantConverter converter = new InstantConverter(defaultValue);
        assertSuccess(converter, (Object) null, defaultValue, defaultValue);
        assertSuccess(converter, LOCAL_DATE, defaultValue, defaultValue);
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
     * Test {@link InstantConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is a {@code LocalTime}
     * ISO-8601 representation.
     */
    @Test
    public void testFromValueValidConversions() {
        assertSuccess(ZONED_DATE_TIME, INSTANT);
        assertSuccess(OFFSET_DATE_TIME, INSTANT);
        assertSuccess(LOCAL_DATE_TIME, UTC_INSTANT);
        assertSuccess(INSTANT, INSTANT);
        assertSuccess(EPOCH_MILLIS, INSTANT);
    }

    /**
     * Test {@link InstantConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    public void testFromStringInvalidConversions() {
        assertFail(STR_EMPTY);
        assertFail(STR_NON_DATE);
        assertFail(STR_ISO_OFFSET_DATE);
        assertFail(STR_ISO_LOCAL_DATE);
        assertFail(STR_ISO_BASIC_DATE);
        assertFail(STR_ISO_ORDINAL_DATE);
        assertFail(STR_ISO_WEEK_DATE);
        assertFail(STR_ISO_YEAR_MONTH);
        assertFail(STR_ISO_MONTH_DAY);
        assertFail(STR_ISO_OFFSET_TIME);
        assertFail(STR_ISO_LOCAL_TIME);
        assertFail(STR_RFC_1123_DATE_TIME);
    }

    /**
     * Test {@link InstantConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    public void testFromStringInvalidConversionsWithDefaultValue() {
        final Instant defaultValue = null;
        final InstantConverter converter = new InstantConverter(defaultValue);
        assertSuccess(converter, STR_EMPTY, defaultValue, defaultValue);
        assertSuccess(converter, STR_NON_DATE, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_OFFSET_DATE, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_LOCAL_DATE, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_BASIC_DATE, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_ORDINAL_DATE, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_WEEK_DATE, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_YEAR_MONTH, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_MONTH_DAY, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_OFFSET_TIME, defaultValue, defaultValue);
        assertSuccess(converter, STR_ISO_LOCAL_TIME, defaultValue, defaultValue);
        assertSuccess(converter, STR_RFC_1123_DATE_TIME, defaultValue, defaultValue);
    }

    /**
     * Test {@link InstantConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is a {@code LocalTime}
     * ISO-8601 representation.
     */
    @Test
    public void testFromStringValidConversions() {
        assertSuccess(STR_ISO_ZONED_DATE_TIME, INSTANT);
        assertSuccess(STR_ISO_OFFSET_DATE_TIME, INSTANT);
        assertSuccess(STR_ISO_LOCAL_DATE_TIME, UTC_INSTANT);
        assertSuccess(STR_ISO_INSTANT, INSTANT);
        assertSuccess(STR_EPOCH_MILLIS, INSTANT);
    }

    /**
     * Test {@link InstantConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is a {@code LocalTime}
     * ISO-8601 representation.
     */
    @Test
    public void testValidToStringConversions() {
        final InstantConverter converter = new InstantConverter();
        assertNull(converter.convert(String.class, null));
        assertSuccess(converter, String.class, "", "");
        assertSuccess(converter, String.class, STR_NON_DATE, STR_NON_DATE);
        assertSuccess(converter, String.class, INSTANT, STR_ISO_INSTANT);
    }
}
