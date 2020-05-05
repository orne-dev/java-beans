/**
 * 
 */
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

import java.time.Duration;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code DurationConverter}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see DurationConverter
 */
@Tag("ut")
public class DurationConverterTest
extends AbstractConverterTest {

    private static final Duration MILLIS_DURATION = Duration.ofMillis(123);
    private static final Duration SECONDS_DURATION = Duration.ofSeconds(4);
    private static final Duration MINUTES_DURATION = Duration.ofMinutes(5);
    private static final Duration HOURS_DURATION = Duration.ofHours(6);
    private static final Duration DAYS_DURATION = Duration.ofDays(7);
    private static final Duration MIXED_DURATION = Duration.ofDays(7)
            .plus(DAYS_DURATION)
            .plus(HOURS_DURATION)
            .plus(MINUTES_DURATION)
            .plus(SECONDS_DURATION)
            .plus(MILLIS_DURATION);
    private static final Duration NEGATED_DURATION = Duration.from(MIXED_DURATION)
            .negated();

    public DurationConverterTest() {
        super(Duration.class, new DurationConverter());
    }

    /**
     * Test {@link DurationConverter#convert(Class, Object)} when
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
     * Test {@link DurationConverter#convert(Class, Object)} when
     * {@code type} is {@code null} or {@code Locale}, {@code value}
     * is invalid and a default value is set.
     */
    @Test
    public void testFromValueInvalidConversionsWithDefaultValue() {
        final Duration defaultValue = null;
        final DurationConverter converter = new DurationConverter(defaultValue);
        assertSuccess(converter, null, defaultValue, defaultValue);
        assertSuccess(converter, "", defaultValue, defaultValue);
        assertSuccess(converter, true, defaultValue, defaultValue);
    }

    /**
     * Test {@link DurationConverter#convert(Class, Object)} when
     * {@code type} is {@code null} or {@code Locale} and
     * {@code value} is valid.
     */
    @Test
    public void testFromValueValidConversions() {
        assertSuccess(MILLIS_DURATION.toString(), MILLIS_DURATION);
        assertSuccess(MILLIS_DURATION, MILLIS_DURATION);
        assertSuccess(SECONDS_DURATION.toString(), SECONDS_DURATION);
        assertSuccess(SECONDS_DURATION, SECONDS_DURATION);
        assertSuccess(MINUTES_DURATION.toString(), MINUTES_DURATION);
        assertSuccess(MINUTES_DURATION, MINUTES_DURATION);
        assertSuccess(HOURS_DURATION.toString(), HOURS_DURATION);
        assertSuccess(HOURS_DURATION, HOURS_DURATION);
        assertSuccess(DAYS_DURATION.toString(), DAYS_DURATION);
        assertSuccess(DAYS_DURATION, DAYS_DURATION);
        assertSuccess(MIXED_DURATION.toString(), MIXED_DURATION);
        assertSuccess(MIXED_DURATION, MIXED_DURATION);
        assertSuccess(NEGATED_DURATION.toString(), NEGATED_DURATION);
        assertSuccess(NEGATED_DURATION, NEGATED_DURATION);
        assertSuccess(String.valueOf(MIXED_DURATION.toMillis()), MIXED_DURATION);
        assertSuccess(MIXED_DURATION.toMillis(), MIXED_DURATION);
        assertSuccess(String.valueOf(NEGATED_DURATION.toMillis()), NEGATED_DURATION);
        assertSuccess(NEGATED_DURATION.toMillis(), NEGATED_DURATION);
    }

    /**
     * Test {@link DurationConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is invalid.
     */
    @Test
    public void testInvalidToStringConversions() {
        assertFail(converter, String.class, 123456);
        
    }

    /**
     * Test {@link DurationConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is invalid.
     */
    @Test
    public void testInvalidToStringConversionsWithDefaultValue() {
        final Duration defaultValue = null;
        final DurationConverter converter = new DurationConverter(defaultValue);
        assertSuccess(converter, String.class, 123456, defaultValue);
    }

    /**
     * Test {@link DurationConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is valid.
     */
    @Test
    public void testValidToStringConversions() {
        assertNull(converter.convert(String.class, null));
        assertSuccess(converter, String.class, "", "");
        assertSuccess(converter, String.class, "test string", "test string");
        assertSuccess(converter, String.class, MILLIS_DURATION, MILLIS_DURATION.toString());
        assertSuccess(converter, String.class, SECONDS_DURATION, SECONDS_DURATION.toString());
        assertSuccess(converter, String.class, MINUTES_DURATION, MINUTES_DURATION.toString());
        assertSuccess(converter, String.class, HOURS_DURATION, HOURS_DURATION.toString());
        assertSuccess(converter, String.class, DAYS_DURATION, DAYS_DURATION.toString());
        assertSuccess(converter, String.class, MIXED_DURATION, MIXED_DURATION.toString());
        assertSuccess(converter, String.class, NEGATED_DURATION, NEGATED_DURATION.toString());
    }
}
