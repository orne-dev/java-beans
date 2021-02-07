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

    protected final AbstractConverter converter;
    protected final Class<?> targetType;

    public AbstractConverterTest(
            final @NotNull Class<?> targetType,
            final @NotNull AbstractConverter converter) {
        super();
        this.targetType = targetType;
        this.converter = converter;
    }

    protected void assertFail(
            final Object value) {
        assertFail(this.converter, this.targetType, value);
        assertFail(this.converter, null, value);
        assertFail(this.converter, Exception.class, value);
    }

    protected void assertFail(
            final @NotNull Converter converter,
            final @NotNull Class<?> type,
            final Object value) {
        assertThrows(ConversionException.class, () -> {
            converter.convert(type, value);
        });
    }

    protected void assertSuccess(
            final Object value,
            final Object expectedResult) {
        assertSuccess(this.converter, this.targetType, value, expectedResult);
        assertSuccess(this.converter, null, value, expectedResult);
        assertFail(this.converter, Exception.class, value);
    }

    protected void assertSuccess(
            final @NotNull Converter converter,
            final Object value,
            final Object expectedResult,
            final Object defaultValue) {
        assertSuccess(converter, this.targetType, value, expectedResult);
        assertSuccess(converter, null, value, expectedResult);
        assertSuccess(converter, Exception.class, value, defaultValue);
    }

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
