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

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code EnumConverter}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see EnumConverter
 */
@Tag("ut")
public class EnumConverterTest
extends AbstractConverterTest {

    public EnumConverterTest() {
        super(TestEnum.class, new EnumConverter<>(TestEnum.class));
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is {@code null} or {@code Locale} and
     * {@code value} is invalid.
     */
    @Test
    public void testFromValueInvalidConversions() {
        assertFail(null);
        assertFail("");
        assertFail("VALUE_D");
        assertFail(123456);
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is {@code null} or {@code Locale}, {@code value}
     * is invalid and a default value is set.
     */
    @Test
    public void testFromValueInvalidConversionsWithDefaultValue() {
        final TestEnum defaultValue = null;
        final EnumConverter<TestEnum> converter = new EnumConverter<>(
                TestEnum.class,
                defaultValue);
        assertSuccess(converter, null, defaultValue, defaultValue);
        assertSuccess(converter, "", defaultValue, defaultValue);
        assertSuccess(converter, "VALUE_D", defaultValue, defaultValue);
        assertSuccess(converter, 123456, defaultValue, defaultValue);
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is {@code null} or {@code Locale} and
     * {@code value} is valid.
     */
    @Test
    public void testFromValueValidConversions() {
        assertSuccess(TestEnum.VALUE_B.name(), TestEnum.VALUE_B);
        assertSuccess(TestEnum.VALUE_B, TestEnum.VALUE_B);
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is invalid.
     */
    @Test
    public void testInvalidToStringConversions() {
        assertFail(converter, String.class, 123456);
        
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is invalid.
     */
    @Test
    public void testInvalidToStringConversionsWithDefaultValue() {
        final TestEnum defaultValue = null;
        final EnumConverter<TestEnum> converter = new EnumConverter<>(
                TestEnum.class,
                defaultValue);
        assertSuccess(converter, String.class, 123456, defaultValue);
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is valid.
     */
    @Test
    public void testValidToStringConversions() {
        assertNull(converter.convert(String.class, null));
        assertSuccess(converter, String.class, "", "");
        assertSuccess(converter, String.class, "test string", "test string");
        assertSuccess(converter, String.class, TestEnum.VALUE_B, TestEnum.VALUE_B.name());
    }

    /**
     * Enumeration for {@code EnumConverter} tests.
     */
    public static enum TestEnum {
        VALUE_A,
        VALUE_B,
        VALUE_C;
    }
}
