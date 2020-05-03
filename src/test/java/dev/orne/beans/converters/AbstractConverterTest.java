/**
 * 
 */
package dev.orne.beans.converters;

import static org.junit.jupiter.api.Assertions.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
            @Nonnull
            final Class<?> targetType,
            @Nonnull
            final AbstractConverter converter) {
        super();
        this.targetType = targetType;
        this.converter = converter;
    }

    protected void assertFail(
            @Nullable
            final Object value) {
        assertFail(this.converter, this.targetType, value);
        assertFail(this.converter, null, value);
        assertFail(this.converter, Exception.class, value);
    }

    protected void assertFail(
            @Nonnull
            final Converter converter,
            @Nonnull
            final Class<?> type,
            @Nullable
            final Object value) {
        assertThrows(ConversionException.class, () -> {
            converter.convert(type, value);
        });
    }

    protected void assertSuccess(
            @Nullable
            final Object value,
            @Nullable
            final Object expectedResult) {
        assertSuccess(this.converter, this.targetType, value, expectedResult);
        assertSuccess(this.converter, null, value, expectedResult);
        assertFail(this.converter, Exception.class, value);
    }

    protected void assertSuccess(
            @Nonnull
            final Converter converter,
            @Nullable
            final Object value,
            @Nullable
            final Object expectedResult,
            @Nullable
            final Object defaultValue) {
        assertSuccess(converter, this.targetType, value, expectedResult);
        assertSuccess(converter, null, value, expectedResult);
        assertSuccess(converter, Exception.class, value, defaultValue);
    }

    protected void assertSuccess(
            @Nonnull
            final Converter converter,
            @Nonnull
            final Class<?> type,
            @Nullable
            final Object value,
            @Nullable
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
