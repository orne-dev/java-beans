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

import org.apache.commons.beanutils.ConvertUtilsBean;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.beans.GenericEnumValue;

/**
 * Integration tests for {@code GenericEnumConverter}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-04
 * @since 0.1
 * @see GenericEnumConverter
 */
@Tag("it")
public class GenericEnumConverterIT {

    private static ConvertUtilsBean converter;

    @BeforeAll
    public static void createConverter() {
        converter = new ConvertUtilsBean();
        converter.register(new GenericEnumConverter(null), GenericEnumValue.class);
    }

    @Test
    public void testNullConversion() {
        final Object result = converter.convert(
                (Object) null,
                GenericEnumValue.class);
        assertNotNull(result);
        assertEquals(GenericEnumValue.class, result.getClass());
        final TestEnum enumValue = ((GenericEnumValue) result).getValue(TestEnum.class);
        assertNull(enumValue);
    }

    @Test
    public void testValueConversion() {
        final Object result = converter.convert(
                "VALUE_A",
                GenericEnumValue.class);
        assertNotNull(result);
        assertEquals(GenericEnumValue.class, result.getClass());
        final TestEnum enumValue = ((GenericEnumValue) result).getValue(TestEnum.class);
        assertNotNull(enumValue);
        assertEquals(TestEnum.VALUE_A, enumValue);
    }

    @Test
    public void testMissingValueConversion() {
        final Object result = converter.convert(
                "VALUE_D",
                GenericEnumValue.class);
        assertNotNull(result);
        assertEquals(GenericEnumValue.class, result.getClass());
        assertThrows(IllegalArgumentException.class, () -> {
            ((GenericEnumValue) result).getValue(TestEnum.class);
        });
    }

    public static enum TestEnum {
        VALUE_A,
        VALUE_B,
        VALUE_C;
    }
}
