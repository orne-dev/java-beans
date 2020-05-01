/**
 * 
 */
package dev.orne.beans.converters;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Locale;

import org.apache.commons.beanutils.ConversionException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code LocaleConverter}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see LocaleConverter
 */
@Tag("ut")
public class LocaleConverterTest {

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}.
     */
    @Test
    public void testToDefaultTypeNullConversion() {
        final LocaleConverter converter = new LocaleConverter();
        assertThrows(ConversionException.class, () -> {
            converter.convert(null, null);
        });
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code null}
     * and a default value is set.
     */
    @Test
    public void testToDefaultTypeNullConversionWithNullDefault() {
        final LocaleConverter converter = new LocaleConverter(null);
        final Locale result = converter.convert(null, null);
        assertNull(result);
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code ""}.
     */
    @Test
    public void testToDefaultTypeEmptyConversion() {
        final LocaleConverter converter = new LocaleConverter();
        assertThrows(ConversionException.class, () -> {
            converter.convert(null, "");
        });
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code ""}
     * and a default value is set.
     */
    @Test
    public void testToDefaultTypeEmptyConversionWithNullDefault() {
        final LocaleConverter converter = new LocaleConverter(null);
        final Locale result = converter.convert(null, "");
        assertNull(result);
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is a language
     * level {@code Locale} BCP-47 tag.
     */
    @Test
    public void testToDefaultTypeLanguageConversion() {
        final LocaleConverter converter = new LocaleConverter();
        final Locale result = converter.convert(
                null,
                Locale.ENGLISH.toLanguageTag());
        assertNotNull(result);
        assertEquals(Locale.ENGLISH, result);
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is a country
     * level {@code Locale} BCP-47 tag.
     */
    @Test
    public void testToDefaultTypeCountryConversion() {
        final LocaleConverter converter = new LocaleConverter();
        final Locale result = converter.convert(
                null,
                Locale.UK.toLanguageTag());
        assertNotNull(result);
        assertEquals(Locale.UK, result);
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is a
     * {@code Locale} instance.
     */
    @Test
    public void testToDefaultTypeLocaleConversion() {
        final LocaleConverter converter = new LocaleConverter();
        final Locale data = new Locale("xx", "YY", "ZZZ");
        final Locale result = converter.convert(
                null,
                data);
        assertNotNull(result);
        assertSame(data, result);
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is an
     * instance of type different to {@code String} or {@code Locale}.
     */
    @Test
    public void testToDefaultTypeWrongTypeConversion() {
        final LocaleConverter converter = new LocaleConverter();
        final int data = 123456;
        assertThrows(ConversionException.class, () -> {
            converter.convert(null, data);
        });
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is an
     * instance of type different to {@code String} or {@code Locale}
     * and a default value is set.
     */
    @Test
    public void testToDefaultTypeWrongTypeConversionWithNullDefault() {
        final LocaleConverter converter = new LocaleConverter(null);
        final int data = 123456;
        final Object result = converter.convert(
                null,
                data);
        assertNull(result);
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is {@code Locale} and {@code value} is {@code null}.
     */
    @Test
    public void testToLocaleNullConversion() {
        final LocaleConverter converter = new LocaleConverter();
        assertThrows(ConversionException.class, () -> {
            converter.convert(Locale.class, null);
        });
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is {@code Locale} and {@code value} is {@code null}
     * and a default value is set.
     */
    @Test
    public void testToLocaleNullConversionWithNullDefault() {
        final LocaleConverter converter = new LocaleConverter(null);
        final Locale result = converter.convert(Locale.class, null);
        assertNull(result);
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is {@code Locale} and {@code value} is {@code ""}.
     */
    @Test
    public void testToLocaleEmptyConversion() {
        final LocaleConverter converter = new LocaleConverter();
        assertThrows(ConversionException.class, () -> {
            converter.convert(Locale.class, "");
        });
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is {@code null} and {@code value} is {@code ""}
     * and a default value is set.
     */
    @Test
    public void testToLocaleEmptyConversionWithNullDefault() {
        final LocaleConverter converter = new LocaleConverter(null);
        final Locale result = converter.convert(Locale.class, "");
        assertNull(result);
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is {@code Locale} and {@code value} is a language
     * level {@code Locale} BCP-47 tag.
     */
    @Test
    public void testToLocaleLanguageConversion() {
        final LocaleConverter converter = new LocaleConverter();
        final Locale result = converter.convert(
                Locale.class,
                Locale.ENGLISH.toLanguageTag());
        assertNotNull(result);
        assertEquals(Locale.ENGLISH, result);
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is {@code Locale} and {@code value} is a country
     * level {@code Locale} BCP-47 tag.
     */
    @Test
    public void testToLocaleCountryConversion() {
        final LocaleConverter converter = new LocaleConverter();
        final Locale result = converter.convert(
                Locale.class,
                Locale.UK.toLanguageTag());
        assertNotNull(result);
        assertEquals(Locale.UK, result);
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is {@code Locale} and {@code value} is a
     * {@code Locale} instance.
     */
    @Test
    public void testToLocaleLocaleConversion() {
        final LocaleConverter converter = new LocaleConverter();
        final Locale data = new Locale("xx", "YY", "ZZZ");
        final Locale result = converter.convert(
                Locale.class,
                data);
        assertNotNull(result);
        assertSame(data, result);
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is {@code Locale} and {@code value} is an
     * instance of type different to {@code String} or {@code Locale}.
     */
    @Test
    public void testToLocaleWrongTypeConversion() {
        final LocaleConverter converter = new LocaleConverter();
        final int data = 123456;
        assertThrows(ConversionException.class, () -> {
            converter.convert(Locale.class, data);
        });
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is {@code Locale} and {@code value} is an
     * instance of type different to {@code String} or {@code Locale}
     * and a default value is set.
     */
    @Test
    public void testToLocaleWrongTypeConversionWithNullDefault() {
        final LocaleConverter converter = new LocaleConverter(null);
        final int data = 123456;
        final Locale result = converter.convert(Locale.class, data);
        assertNull(result);
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is {@code null}.
     */
    @Test
    public void testToStringNullConversion() {
        final LocaleConverter converter = new LocaleConverter();
        final String result = converter.convert(String.class, null);
        assertNull(result);
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is {@code null}
     * and a default value is set.
     */
    @Test
    public void testToStringNullConversionWithNullDefault() {
        final LocaleConverter converter = new LocaleConverter(null);
        final String result = converter.convert(String.class, null);
        assertNull(result);
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is {@code String}.
     */
    @Test
    public void testToStringStringConversion() {
        final LocaleConverter converter = new LocaleConverter();
        final String result = converter.convert(
                String.class,
                "test string");
        assertNotNull(result);
        assertEquals("test string", result);
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is a language
     * level {@code Locale}.
     */
    @Test
    public void testToStringLanguageConversion() {
        final LocaleConverter converter = new LocaleConverter();
        final String result = converter.convert(
                String.class,
                Locale.ENGLISH);
        assertNotNull(result);
        assertEquals(Locale.ENGLISH.toLanguageTag(), result);
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is a country
     * level {@code Locale}.
     */
    @Test
    public void testToStringCountryConversion() {
        final LocaleConverter converter = new LocaleConverter();
        final String result = converter.convert(
                String.class,
                Locale.UK);
        assertNotNull(result);
        assertEquals(Locale.UK.toLanguageTag(), result);
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is an
     * instance of type different to {@code String} or {@code Locale}.
     */
    @Test
    public void testToStringWrongTypeConversion() {
        final LocaleConverter converter = new LocaleConverter();
        final int data = 123456;
        assertThrows(ConversionException.class, () -> {
            converter.convert(String.class, data);
        });
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is an
     * instance of type different to {@code String} or {@code Locale}
     * and a default value is set.
     */
    @Test
    public void testToStringWrongTypeConversionWithNullDefault() {
        final LocaleConverter converter = new LocaleConverter(null);
        final int data = 123456;
        final String result = converter.convert(String.class, data);
        assertNull(result);
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is different to {@code String} or {@code Locale}
     * and {@code value} is {@code null}.
     */
    @Test
    public void testToWrongTypeNullConversion() {
        final LocaleConverter converter = new LocaleConverter();
        assertThrows(ConversionException.class, () -> {
            converter.convert(Integer.class, null);
        });
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is different to {@code String} or {@code Locale}
     * and {@code value} is {@code null} and a default value is set.
     */
    @Test
    public void testToWrongTypeNullConversionWithNullDefault() {
        final LocaleConverter converter = new LocaleConverter(null);
        final Object result = converter.convert(
                Integer.class,
                null);
        assertNull(result);
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is different to {@code String} or {@code Locale}
     * and {@code value} is {@code ""}.
     */
    @Test
    public void testToWrongTypeEmptyConversion() {
        final LocaleConverter converter = new LocaleConverter();
        assertThrows(ConversionException.class, () -> {
            converter.convert(Integer.class, "");
        });
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is different to {@code String} or {@code Locale}
     * and {@code value} is {@code ""} and a default value is set.
     */
    @Test
    public void testToWrongEmptyConversionTypeWithNullDefault() {
        final LocaleConverter converter = new LocaleConverter(null);
        final Object result = converter.convert(
                Integer.class,
                "");
        assertNull(result);
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is different to {@code String} or {@code Locale}
     * and {@code value} is a language level {@code Locale} BCP-47 tag.
     */
    @Test
    public void testToWrongTypeLanguageConversion() {
        final LocaleConverter converter = new LocaleConverter();
        assertThrows(ConversionException.class, () -> {
            converter.convert(
                    Integer.class,
                    Locale.ENGLISH.toLanguageTag());
        });
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is different to {@code String} or {@code Locale}
     * and {@code value} is a country level {@code Locale} BCP-47 tag.
     */
    @Test
    public void testToWrongTypeCountryConversion() {
        final LocaleConverter converter = new LocaleConverter();
        assertThrows(ConversionException.class, () -> {
            converter.convert(
                    Integer.class,
                    Locale.UK.toLanguageTag());
        });
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is different to {@code String} or {@code Locale}
     * and {@code value} is a {@code Locale} instance.
     */
    @Test
    public void testToWrongTypeLocaleConversion() {
        final LocaleConverter converter = new LocaleConverter();
        final Locale data = new Locale("xx", "YY", "ZZZ");
        assertThrows(ConversionException.class, () -> {
            converter.convert(
                    Integer.class,
                    data);
        });
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is different to {@code String} or {@code Locale}
     * and {@code value} is an instance of type different to
     * {@code String} or {@code Locale}.
     */
    @Test
    public void testToWrongTypeWrongTypeConversion() {
        final LocaleConverter converter = new LocaleConverter();
        final long data = 123456;
        assertThrows(ConversionException.class, () -> {
            converter.convert(
                    Boolean.class,
                    data);
        });
    }

    /**
     * Test {@link LocaleConverter#convert(Class, Object)} when
     * {@code type} is different to {@code String} or {@code Locale}
     * and {@code value} is an instance of type different to
     * {@code String} or {@code Locale} and a default value is set.
     */
    @Test
    public void testToWrongTypeWrongTypeConversionWithNullDefault() {
        final LocaleConverter converter = new LocaleConverter(null);
        final long data = 123456;
        final Object result = converter.convert(
                Boolean.class,
                data);
        assertNull(result);
    }
}
