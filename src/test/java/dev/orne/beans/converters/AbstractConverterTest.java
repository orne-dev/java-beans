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

import javax.validation.constraints.NotNull;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.AbstractConverter;

/**
 * Abstract class for converters unit tests.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see AbstractConverter
 */
public abstract class AbstractConverterTest {

    /** The converter to test. */
    protected final AbstractConverter converter;
    /** The conversion default target type. */
    protected final Class<?> targetType;

    /**
     * Creates a new instance.
     * 
     * @param targetType The conversion default target type.
     * @param converter The converter to test.
     */
    public AbstractConverterTest(
            final @NotNull Class<?> targetType,
            final @NotNull AbstractConverter converter) {
        super();
        this.targetType = targetType;
        this.converter = converter;
    }

    /**
     * Asserts that the conversion of the specified value fails both
     * to the default target type, the tested converter default type and an
     * unsupported type ({@code Exception}).
     * 
     * @param value The value to convert.
     */
    protected void assertFail(
            final Object value) {
        assertFail(this.converter, this.targetType, value);
        assertFail(this.converter, null, value);
        assertFail(this.converter, Exception.class, value);
    }

    /**
     * Asserts that the conversion of the specified value to the specified type
     * fails.
     * 
     * @param converter The converter to test.
     * @param type The conversion target type.
     * @param value The value to convert.
     */
    protected void assertFail(
            final @NotNull Converter converter,
            final @NotNull Class<?> type,
            final Object value) {
        assertThrows(ConversionException.class, () -> {
            converter.convert(type, value);
        });
    }

    /**
     * Asserts that the conversion of the specified value results in
     * the specified expected result both for the default target type
     * and the tested converter default type.
     * Also asserts that the conversion of the specified value to an
     * unsupported type ({@code Exception}) fails.
     * 
     * @param value The value to convert.
     * @param expectedResult The expected result.
     */
    protected void assertSuccess(
            final Object value,
            final Object expectedResult) {
        assertSuccess(this.converter, this.targetType, value, expectedResult);
        assertSuccess(this.converter, null, value, expectedResult);
        assertFail(this.converter, Exception.class, value);
    }

    /**
     * Asserts that the conversion to the default target type results in
     * the specified expected result.
     * Also asserts that the conversion of the specified value to both
     * the tested converter default type and an unsupported type
     * ({@code Exception}) fails.
     * 
     * @param value The value to convert.
     * @param expectedResult The expected result.
     */
    protected void assertSuccessTyped(
            final Object value,
            final Object expectedResult) {
        assertSuccess(this.converter, this.targetType, value, expectedResult);
        assertFail(this.converter, null, value);
        assertFail(this.converter, Exception.class, value);
    }

    /**
     * Asserts that the conversion of the specified value results in
     * the specified expected result both for the default target type
     * and the tested converter default type.
     * Also asserts that the conversion of the specified value to an
     * unsupported type ({@code Exception}) results in the specified default
     * value.
     * 
     * @param converter The converter to test.
     * @param value The value to convert.
     * @param expectedResult The expected result.
     * @param defaultValue The expected result when target type is not
     * supported.
     */
    protected void assertSuccess(
            final @NotNull Converter converter,
            final Object value,
            final Object expectedResult,
            final Object defaultValue) {
        assertSuccess(converter, this.targetType, value, expectedResult);
        assertSuccess(converter, null, value, expectedResult);
        assertSuccess(converter, Exception.class, value, defaultValue);
    }

    /**
     * Asserts that the conversion of the specified value to the specified type
     * result in the specified result.
     * 
     * @param converter The converter to test.
     * @param type The conversion target type.
     * @param value The value to convert.
     * @param expectedResult The expected result.
     */
    protected void assertSuccess(
            final @NotNull Converter converter,
            final @NotNull Class<?> type,
            final Object value,
            final Object expectedResult) {
        final Object result = converter.convert(
                type,
                value);
        if (expectedResult == null) {
            assertNull(result);
        } else {
            assertNotNull(result);
            assertEquals(expectedResult, result);
        }
    }
}
