/**
 * 
 */
package dev.orne.beans.converters;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.beanutils.ConversionException;
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
public class EnumConverterTest {

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    public void testToDefaultTypeNullConversion() {
        final EnumConverter<TestEnum> converter = new EnumConverter<>(
                TestEnum.class);
        assertThrows(ConversionException.class, () -> {
            converter.convert(null, null);
        });
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}
     * and a default value is set.
     */
    @Test
    public void testToDefaultTypeNullConversionWithNullDefault() {
        final EnumConverter<TestEnum> converter = new EnumConverter<>(
                TestEnum.class, null);
        final TestEnum result = converter.convert(null, null);
        assertNull(result);
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code ""}.
     */
    @Test
    public void testToDefaultTypeEmptyConversion() {
        final EnumConverter<TestEnum> converter = new EnumConverter<>(
                TestEnum.class);
        assertThrows(ConversionException.class, () -> {
            converter.convert(null, "");
        });
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code ""}
     * and a default value is set.
     */
    @Test
    public void testToDefaultTypeEmptyConversionWithNullDefault() {
        final EnumConverter<TestEnum> converter = new EnumConverter<>(
                TestEnum.class, null);
        final TestEnum result = converter.convert(null, "");
        assertNull(result);
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is a valid
     * enumeration constant name.
     */
    @Test
    public void testToDefaultTypeConstantNameConversion() {
        final EnumConverter<TestEnum> converter = new EnumConverter<>(
                TestEnum.class);
        final TestEnum result = converter.convert(
                null,
                TestEnum.VALUE_B.name());
        assertNotNull(result);
        assertEquals(TestEnum.VALUE_B, result);
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is a
     * enumeration constant.
     */
    @Test
    public void testToDefaultTypeConstantConversion() {
        final EnumConverter<TestEnum> converter = new EnumConverter<>(
                TestEnum.class);
        final TestEnum result = converter.convert(
                null,
                TestEnum.VALUE_B);
        assertNotNull(result);
        assertEquals(TestEnum.VALUE_B, result);
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is an
     * instance of type different to {@code String} or
     * supported enumeration.
     */
    @Test
    public void testToDefaultTypeWrongTypeConversion() {
        final EnumConverter<TestEnum> converter = new EnumConverter<>(
                TestEnum.class);
        final int data = 123456;
        assertThrows(ConversionException.class, () -> {
            converter.convert(null, data);
        });
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is an
     * instance of type different to {@code String} or supported enumeration
     * and a default value is set.
     */
    @Test
    public void testToDefaultTypeWrongTypeConversionWithNullDefault() {
        final EnumConverter<TestEnum> converter = new EnumConverter<>(
                TestEnum.class, null);
        final int data = 123456;
        final Object result = converter.convert(
                null,
                data);
        assertNull(result);
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is supported enumeration and {@code value} is {@code null}.
     */
    @Test
    public void testToEnumNullConversion() {
        final EnumConverter<TestEnum> converter = new EnumConverter<>(
                TestEnum.class);
        assertThrows(ConversionException.class, () -> {
            converter.convert(TestEnum.class, null);
        });
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is supported enumeration and {@code value} is {@code null}
     * and a default value is set.
     */
    @Test
    public void testToEnumNullConversionWithNullDefault() {
        final EnumConverter<TestEnum> converter = new EnumConverter<>(
                TestEnum.class, null);
        final TestEnum result = converter.convert(TestEnum.class, null);
        assertNull(result);
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is supported enumeration and {@code value} is {@code ""}.
     */
    @Test
    public void testToEnumEmptyConversion() {
        final EnumConverter<TestEnum> converter = new EnumConverter<>(
                TestEnum.class);
        assertThrows(ConversionException.class, () -> {
            converter.convert(TestEnum.class, "");
        });
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code ""}
     * and a default value is set.
     */
    @Test
    public void testToEnumEmptyConversionWithNullDefault() {
        final EnumConverter<TestEnum> converter = new EnumConverter<>(
                TestEnum.class, null);
        final TestEnum result = converter.convert(TestEnum.class, "");
        assertNull(result);
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is supported enumeration and {@code value} is a valid
     * enumeration constant.
     */
    @Test
    public void testToEnumConstantNameConversion() {
        final EnumConverter<TestEnum> converter = new EnumConverter<>(
                TestEnum.class);
        final TestEnum result = converter.convert(
                TestEnum.class,
                TestEnum.VALUE_B.name());
        assertNotNull(result);
        assertEquals(TestEnum.VALUE_B, result);
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is supported enumeration and {@code value} is a
     * enumeration constant.
     */
    @Test
    public void testToEnumConstantConversion() {
        final EnumConverter<TestEnum> converter = new EnumConverter<>(
                TestEnum.class);
        final TestEnum result = converter.convert(
                TestEnum.class,
                TestEnum.VALUE_B);
        assertNotNull(result);
        assertEquals(TestEnum.VALUE_B, result);
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is supported enumeration and {@code value} is an
     * instance of type different to {@code String} or supported enumeration.
     */
    @Test
    public void testToEnumWrongTypeConversion() {
        final EnumConverter<TestEnum> converter = new EnumConverter<>(
                TestEnum.class);
        final int data = 123456;
        assertThrows(ConversionException.class, () -> {
            converter.convert(TestEnum.class, data);
        });
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is supported enumeration and {@code value} is an
     * instance of type different to {@code String} or supported enumeration
     * and a default value is set.
     */
    @Test
    public void testToEnumWrongTypeConversionWithNullDefault() {
        final EnumConverter<TestEnum> converter = new EnumConverter<>(
                TestEnum.class, null);
        final int data = 123456;
        final TestEnum result = converter.convert(TestEnum.class, data);
        assertNull(result);
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is {@code null}.
     */
    @Test
    public void testToStringNullConversion() {
        final EnumConverter<TestEnum> converter = new EnumConverter<>(
                TestEnum.class);
        final String result = converter.convert(String.class, null);
        assertNull(result);
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is {@code null}
     * and a default value is set.
     */
    @Test
    public void testToStringNullConversionWithNullDefault() {
        final EnumConverter<TestEnum> converter = new EnumConverter<>(
                TestEnum.class, null);
        final String result = converter.convert(String.class, null);
        assertNull(result);
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is {@code String}.
     */
    @Test
    public void testToStringStringConversion() {
        final EnumConverter<TestEnum> converter = new EnumConverter<>(
                TestEnum.class);
        final String result = converter.convert(
                String.class,
                "test string");
        assertNotNull(result);
        assertEquals("test string", result);
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is enumeration
     * constant}.
     */
    @Test
    public void testToStringConstantConversion() {
        final EnumConverter<TestEnum> converter = new EnumConverter<>(
                TestEnum.class);
        final String result = converter.convert(
                String.class,
                TestEnum.VALUE_B);
        assertNotNull(result);
        assertEquals(TestEnum.VALUE_B.name(), result);
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is an
     * instance of type different to {@code String} or supported enumeration.
     */
    @Test
    public void testToStringWrongTypeConversion() {
        final EnumConverter<TestEnum> converter = new EnumConverter<>(
                TestEnum.class);
        final int data = 123456;
        assertThrows(ConversionException.class, () -> {
            converter.convert(String.class, data);
        });
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is an
     * instance of type different to {@code String} or supported enumeration
     * and a default value is set.
     */
    @Test
    public void testToStringWrongTypeConversionWithNullDefault() {
        final EnumConverter<TestEnum> converter = new EnumConverter<>(
                TestEnum.class, null);
        final int data = 123456;
        final String result = converter.convert(String.class, data);
        assertNull(result);
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is different to {@code String} or supported enumeration
     * and {@code value} is {@code null}.
     */
    @Test
    public void testToWrongTypeNullConversion() {
        final EnumConverter<TestEnum> converter = new EnumConverter<>(
                TestEnum.class);
        assertThrows(ConversionException.class, () -> {
            converter.convert(Integer.class, null);
        });
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is different to {@code String} or supported enumeration
     * and {@code value} is {@code null} and a default value is set.
     */
    @Test
    public void testToWrongTypeNullConversionWithNullDefault() {
        final EnumConverter<TestEnum> converter = new EnumConverter<>(
                TestEnum.class, null);
        final Object result = converter.convert(
                Integer.class,
                null);
        assertNull(result);
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is different to {@code String} or supported enumeration
     * and {@code value} is {@code ""}.
     */
    @Test
    public void testToWrongTypeEmptyConversion() {
        final EnumConverter<TestEnum> converter = new EnumConverter<>(
                TestEnum.class);
        assertThrows(ConversionException.class, () -> {
            converter.convert(Integer.class, "");
        });
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is different to {@code String} or supported enumeration
     * and {@code value} is {@code ""} and a default value is set.
     */
    @Test
    public void testToWrongEmptyConversionTypeWithNullDefault() {
        final EnumConverter<TestEnum> converter = new EnumConverter<>(
                TestEnum.class, null);
        final Object result = converter.convert(
                Integer.class,
                "");
        assertNull(result);
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is different to {@code String} or supported enumeration
     * and {@code value} is a valid enumeration constant name.
     */
    @Test
    public void testToWrongTypeConstantNameConversion() {
        final EnumConverter<TestEnum> converter = new EnumConverter<>(
                TestEnum.class);
        assertThrows(ConversionException.class, () -> {
            converter.convert(
                    Integer.class,
                    TestEnum.VALUE_B.name());
        });
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is different to {@code String} or supported enumeration
     * and {@code value} is a enumeration constant.
     */
    @Test
    public void testToWrongTypeConstantConversion() {
        final EnumConverter<TestEnum> converter = new EnumConverter<>(
                TestEnum.class);
        assertThrows(ConversionException.class, () -> {
            converter.convert(
                    Integer.class,
                    TestEnum.VALUE_B);
        });
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is different to {@code String} or supported enumeration
     * and {@code value} is an instance of type different to
     * {@code String} or supported enumeration.
     */
    @Test
    public void testToWrongTypeWrongTypeConversion() {
        final EnumConverter<TestEnum> converter = new EnumConverter<>(
                TestEnum.class);
        final long data = 123456;
        assertThrows(ConversionException.class, () -> {
            converter.convert(
                    Boolean.class,
                    data);
        });
    }

    /**
     * Test {@link EnumConverter#convert(Class, Object)} when
     * {@code type} is different to {@code String} or supported enumeration
     * and {@code value} is an instance of type different to
     * {@code String} or supported enumeration and a default value is set.
     */
    @Test
    public void testToWrongTypeWrongTypeConversionWithNullDefault() {
        final EnumConverter<TestEnum> converter = new EnumConverter<>(
                TestEnum.class, null);
        final long data = 123456;
        final Object result = converter.convert(
                Boolean.class,
                data);
        assertNull(result);
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
