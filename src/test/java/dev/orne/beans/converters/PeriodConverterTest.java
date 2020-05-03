/**
 * 
 */
package dev.orne.beans.converters;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Period;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code PeriodConverter}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see PeriodConverter
 */
@Tag("ut")
public class PeriodConverterTest
extends AbstractConverterTest {

    private static final Period DAYS_PERIOD = Period.ofDays(1);
    private static final Period WEEKS_PERIOD = Period.ofWeeks(2);
    private static final Period MONTHS_PERIOD = Period.ofMonths(3);
    private static final Period YEARS_PERIOD = Period.ofYears(4);
    private static final Period MIXED_PERIOD = Period.ofDays(7)
            .plus(DAYS_PERIOD)
            .plus(WEEKS_PERIOD)
            .plus(MONTHS_PERIOD)
            .plus(YEARS_PERIOD);
    private static final Period NEGATED_PERIOD = Period.from(MIXED_PERIOD)
            .negated();

    public PeriodConverterTest() {
        super(Period.class, new PeriodConverter());
    }

    /**
     * Test {@link PeriodConverter#convert(Class, Object)} when
     * {@code type} is {@code null} or {@code Locale} and
     * {@code value} is invalid.
     */
    @Test
    public void testFromValueInvalidConversions() {
        assertFail(null);
        assertFail("");
        assertFail(true);
    }

    /**
     * Test {@link PeriodConverter#convert(Class, Object)} when
     * {@code type} is {@code null} or {@code Locale}, {@code value}
     * is invalid and a default value is set.
     */
    @Test
    public void testFromValueInvalidConversionsWithDefaultValue() {
        final Period defaultValue = null;
        final PeriodConverter converter = new PeriodConverter(defaultValue);
        assertSuccess(converter, null, defaultValue, defaultValue);
        assertSuccess(converter, "", defaultValue, defaultValue);
        assertSuccess(converter, true, defaultValue, defaultValue);
    }

    /**
     * Test {@link PeriodConverter#convert(Class, Object)} when
     * {@code type} is {@code null} or {@code Locale} and
     * {@code value} is valid.
     */
    @Test
    public void testFromValueValidConversions() {
        assertSuccess(DAYS_PERIOD.toString(), DAYS_PERIOD);
        assertSuccess(DAYS_PERIOD, DAYS_PERIOD);
        assertSuccess(WEEKS_PERIOD.toString(), WEEKS_PERIOD);
        assertSuccess(WEEKS_PERIOD, WEEKS_PERIOD);
        assertSuccess(MONTHS_PERIOD.toString(), MONTHS_PERIOD);
        assertSuccess(MONTHS_PERIOD, MONTHS_PERIOD);
        assertSuccess(YEARS_PERIOD.toString(), YEARS_PERIOD);
        assertSuccess(YEARS_PERIOD, YEARS_PERIOD);
        assertSuccess(MIXED_PERIOD.toString(), MIXED_PERIOD);
        assertSuccess(MIXED_PERIOD, MIXED_PERIOD);
        assertSuccess(NEGATED_PERIOD.toString(), NEGATED_PERIOD);
        assertSuccess(NEGATED_PERIOD, NEGATED_PERIOD);
    }

    /**
     * Test {@link PeriodConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is invalid.
     */
    @Test
    public void testInvalidToStringConversions() {
        assertFail(converter, String.class, 123456);
        
    }

    /**
     * Test {@link PeriodConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is invalid.
     */
    @Test
    public void testInvalidToStringConversionsWithDefaultValue() {
        final Period defaultValue = null;
        final PeriodConverter converter = new PeriodConverter(defaultValue);
        assertSuccess(converter, String.class, 123456, defaultValue);
    }

    /**
     * Test {@link PeriodConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is valid.
     */
    @Test
    public void testValidToStringConversions() {
        assertNull(converter.convert(String.class, null));
        assertSuccess(converter, String.class, "", "");
        assertSuccess(converter, String.class, "test string", "test string");
        assertSuccess(converter, String.class, DAYS_PERIOD, DAYS_PERIOD.toString());
        assertSuccess(converter, String.class, WEEKS_PERIOD, WEEKS_PERIOD.toString());
        assertSuccess(converter, String.class, MONTHS_PERIOD, MONTHS_PERIOD.toString());
        assertSuccess(converter, String.class, YEARS_PERIOD, YEARS_PERIOD.toString());
        assertSuccess(converter, String.class, MIXED_PERIOD, MIXED_PERIOD.toString());
        assertSuccess(converter, String.class, NEGATED_PERIOD, NEGATED_PERIOD.toString());
    }
}
