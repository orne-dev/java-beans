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
import static org.mockito.BDDMockito.*;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code EnumTypedConverter}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-10
 * @since 0.4
 * @see EnumTypedConverter
 */
@Tag("ut")
class EnumTypedConverterTest
extends AbstractConverterTest {

    public EnumTypedConverterTest() {
        super(TestEnum.class, EnumTypedConverter.of(TestEnum.class));
    }

    /**
     * Test {@link EnumTypedConverter#convert(Class, Object)} when
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
     * Test {@link EnumTypedConverter#convert(Class, Object)} when
     * {@code type} is {@code null} or {@code Locale}, {@code value}
     * is invalid and a default value is set.
     */
    @Test
    void testFromValueInvalidConversionsWithDefaultValue() {
        final TestEnum defaultValue = null;
        final EnumTypedConverter<TestEnum> converter = EnumTypedConverter.of(
                TestEnum.class,
                defaultValue);
        assertSuccess(converter, null, defaultValue, defaultValue);
        assertSuccess(converter, "", defaultValue, defaultValue);
        assertSuccess(converter, "VALUE_D", defaultValue, defaultValue);
        assertSuccess(converter, 123456, defaultValue, defaultValue);
    }

    /**
     * Test {@link EnumTypedConverter#convert(Class, Object)} when
     * {@code type} is {@code null} or {@code Locale} and
     * {@code value} is valid.
     */
    @Test
    void testFromValueValidConversions() {
        assertSuccess(TestEnum.VALUE_B.name(), TestEnum.VALUE_B);
        assertSuccess(TestEnum.VALUE_B, TestEnum.VALUE_B);
    }

    /**
     * Test {@link EnumTypedConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is invalid.
     */
    @Test
    void testInvalidToStringConversions() {
        assertFail(converter, String.class, 123456);
    }

    /**
     * Test {@link EnumTypedConverter#convert(Class, Object)} when
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
     * Test {@link EnumTypedConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is invalid.
     */
    @Test
    void testInvalidToStringConversionsWithDefaultValue() {
        final TestEnum defaultValue = null;
        final EnumTypedConverter<TestEnum> converter = EnumTypedConverter.of(
                TestEnum.class,
                defaultValue);
        assertSuccess(converter, String.class, 123456, defaultValue);
    }

    /**
     * Test edge cases of {@link EnumTypedConverter#convertToType(Class, Object)}.
     */
    @Test
    void testGenericConvertToType()
    throws Throwable {
        final EnumTypedConverter<TestEnum> converter = EnumTypedConverter.of(
                TestEnum.class);
        assertNull(converter.convertToType(TestEnum.class, null));
    }

    /**
     * Test for {@link EnumTypedConverter#registerFor(ConvertUtilsBean, Class)}.
     */
    @Test
    void testRegisterFor() {
        final ConvertUtilsBean converter = mock(ConvertUtilsBean.class);
        EnumTypedConverter.registerFor(converter, TestEnum.class);
        then(converter).should().register(any(EnumTypedConverter.class), eq(TestEnum.class));
    }

    /**
     * Test for {@link EnumTypedConverter#registerFor(Class)}.
     */
    @Test
    void testRegisterForShared() {
        final ConvertUtilsBean converter = mock(ConvertUtilsBean.class);
        final BeanUtilsBean backup = BeanUtilsBean.getInstance();
        BeanUtilsBean.setInstance(new BeanUtilsBean(converter));
        try {
            EnumTypedConverter.registerFor(TestEnum.class);
            then(converter).should().register(any(EnumTypedConverter.class), eq(TestEnum.class));
        } finally {
            BeanUtilsBean.setInstance(backup);
        }
    }

    /**
     * Test for {@link EnumTypedConverter#registerFor(ConvertUtilsBean, Class)}.
     */
    @Test
    void testRegisterForDefault() {
        final ConvertUtilsBean converter = mock(ConvertUtilsBean.class);
        final TestEnum defaultValue = null;
        EnumTypedConverter.registerFor(converter, TestEnum.class, defaultValue);
        then(converter).should().register(any(EnumTypedConverter.class), eq(TestEnum.class));
    }

    /**
     * Test for {@link EnumTypedConverter#registerFor(Class)}.
     */
    @Test
    void testRegisterForDefaultShared() {
        final ConvertUtilsBean converter = mock(ConvertUtilsBean.class);
        final TestEnum defaultValue = null;
        final BeanUtilsBean backup = BeanUtilsBean.getInstance();
        BeanUtilsBean.setInstance(new BeanUtilsBean(converter));
        try {
            EnumTypedConverter.registerFor(TestEnum.class, defaultValue);
            then(converter).should().register(any(EnumTypedConverter.class), eq(TestEnum.class));
        } finally {
            BeanUtilsBean.setInstance(backup);
        }
    }

    /**
     * Enumeration for {@code EnumTypedConverter} tests.
     */
    public static enum TestEnum {
        VALUE_A,
        VALUE_B,
        VALUE_C;
    }
}
