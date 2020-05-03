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

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.beans.GenericEnumValue;

/**
 * Unit tests for {@code GenericEnumConverter}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-04
 * @since 0.1
 * @see GenericEnumConverter
 */
@Tag("ut")
public class GenericEnumConverterTest {

    @Test
    public void testNullConversionWithDefaultNull() {
        final GenericEnumConverter converter = new GenericEnumConverter(null);
        final Object result = converter.convert(
                GenericEnumValue.class,
                (Object) null);
        assertNotNull(result);
        assertEquals(GenericEnumValue.class, result.getClass());
        final TestEnum enumValue = ((GenericEnumValue) result).getValue(TestEnum.class);
        assertNull(enumValue);
    }

    @Test
    public void testValueConversionWithDefaultNull() {
        final GenericEnumConverter converter = new GenericEnumConverter(null);
        final Object result = converter.convert(
                GenericEnumValue.class,
                "VALUE_A");
        assertNotNull(result);
        assertEquals(GenericEnumValue.class, result.getClass());
        final TestEnum enumValue = ((GenericEnumValue) result).getValue(TestEnum.class);
        assertNotNull(enumValue);
        assertEquals(TestEnum.VALUE_A, enumValue);
    }

    @Test
    public void testMissingValueConversionWithDefaultNull() {
        final GenericEnumConverter converter = new GenericEnumConverter(null);
        final Object result = converter.convert(
                GenericEnumValue.class,
                "VALUE_D");
        assertNotNull(result);
        assertEquals(GenericEnumValue.class, result.getClass());
        assertThrows(IllegalArgumentException.class, () ->{
            ((GenericEnumValue) result).getValue(TestEnum.class);
        });
    }

    public static enum TestEnum {
        VALUE_A,
        VALUE_B,
        VALUE_C;
    }
}
