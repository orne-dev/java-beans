package dev.orne.beans.converters;

/*-
 * #%L
 * Orne Beans
 * %%
 * Copyright (C) 2020 - 2025 Orne Developments
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
import static org.mockito.BDDMockito.*;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code EnumConverter}.
 *
 * @author <a href="https://github.com/ihernaez">(w) Iker Hernaez</a>
 * @version 1.1, 2022-10
 * @since 0.1
 * @see EnumConverter
 */
@Tag("ut")
class EnumConverterTest
extends AbstractConverterTest {

    public EnumConverterTest() {
        super(TestEnum.class, EnumConverter.GENERIC);
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is {@code null} or {@code Locale} and
     * {@code value} is invalid.
     */
    @Test
    void testFromValueInvalidConversions() {
        assertFail(null);
        assertFail("");
        assertFail("VALUE_D");
        assertFail(123456);
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is {@code null} or {@code Locale} and
     * {@code value} is valid.
     */
    @Test
    void testFromValueValidConversions() {
        assertSuccessTyped(TestEnum.VALUE_B.name(), TestEnum.VALUE_B);
        assertSuccessTyped(TestEnum.VALUE_B, TestEnum.VALUE_B);
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is invalid.
     */
    @Test
    void testInvalidToStringConversions() {
        assertFail(converter, String.class, 123456);
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is valid.
     */
    @Test
    void testValidToStringConversions() {
        assertNull(converter.convert(String.class, null));
        assertSuccess(converter, String.class, "", "");
        assertSuccess(converter, String.class, "test string", "test string");
        assertSuccess(converter, String.class, TestEnum.VALUE_B, TestEnum.VALUE_B.name());
    }

    /**
     * Test edge cases of {@link EnumConverter#convertToType(Class, Object)}.
     */
    @Test
    void testGenericConvertToType()
    throws Throwable {
        assertNull(EnumConverter.GENERIC.convertToType(TestEnum.class, null));
    }

    /**
     * Test edge cases of {@link EnumConverter#enumFromName(Class, String)}.
     */
    @Test
    void testGenericEnumFromName() {
        assertThrows(ConversionException.class, () -> {
            EnumConverter.GENERIC.enumFromName(Object.class, "Some name");
        });
    }

    /**
     * Test for {@link EnumConverter#register(ConvertUtilsBean)}.
     */
    @Test
    void testRegisterFor() {
        final ConvertUtilsBean converter = mock(ConvertUtilsBean.class);
        EnumConverter.register(converter);
        then(converter).should().register(EnumConverter.GENERIC, Enum.class);
    }

    /**
     * Test for {@link EnumConverter#register()}.
     */
    @Test
    void testRegisterForShared() {
        final ConvertUtilsBean converter = mock(ConvertUtilsBean.class);
        final BeanUtilsBean backup = BeanUtilsBean.getInstance();
        BeanUtilsBean.setInstance(new BeanUtilsBean(converter));
        try {
            EnumConverter.register();
            then(converter).should().register(EnumConverter.GENERIC, Enum.class);
        } finally {
            BeanUtilsBean.setInstance(backup);
        }
    }

    /**
     * Test for {@link EnumConverter#registerWithDefault(ConvertUtilsBean)}.
     */
    @Test
    void testRegisterWithDefault() {
        final ConvertUtilsBean converter = mock(ConvertUtilsBean.class);
        EnumConverter.registerWithDefault(converter);
        then(converter).should().register(EnumConverter.GENERIC_DEFAULT, Enum.class);
    }

    /**
     * Test for {@link EnumConverter#registerWithDefault()}.
     */
    @Test
    void testRegisterWithDefaultForShared() {
        final ConvertUtilsBean converter = mock(ConvertUtilsBean.class);
        final BeanUtilsBean backup = BeanUtilsBean.getInstance();
        BeanUtilsBean.setInstance(new BeanUtilsBean(converter));
        try {
            EnumConverter.registerWithDefault();
            then(converter).should().register(EnumConverter.GENERIC_DEFAULT, Enum.class);
        } finally {
            BeanUtilsBean.setInstance(backup);
        }
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
