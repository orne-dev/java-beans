/**
 * 
 */
package dev.orne.beans.converters;

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
