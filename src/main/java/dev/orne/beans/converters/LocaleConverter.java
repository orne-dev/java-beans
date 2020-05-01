/**
 * 
 */
package dev.orne.beans.converters;

import java.util.Locale;

import org.apache.commons.beanutils.converters.AbstractConverter;

/**
 * Implementation of {@code Converter} that converts {@code Locale} instances
 * to and from {@code String} using the language tag as {@code String}
 * representation.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 */
public class LocaleConverter
extends AbstractConverter {

    /**
     * Creates a new instance that throws a {@code ConversionException} if an
     * error occurs.
     */
    public LocaleConverter() {
        super();
    }

    /**
     * Creates a new instance that returns a default value if an error occurs.
     * 
     * @param defaultValue The default value to be returned if the value to be
     * converted is missing or an error occurs converting the value
     */
    public LocaleConverter(final Locale defaultValue) {
        super(defaultValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<?> getDefaultType() {
        return Locale.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <T> T convertToType(
            final Class<T> type,
            final Object value)
    throws Throwable {
        if (Locale.class.equals(type)) {
            final Locale result = Locale.forLanguageTag(value.toString());
            if (result.getLanguage().isEmpty()) {
                // When parsing fails returns an empty locale (WTF!?)
                throw conversionException(type, value);
            } else {
                return type.cast(result);
            }
        }
        throw conversionException(type, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String convertToString(
            final Object value)
    throws Throwable {
        if (value instanceof Locale) {
            return ((Locale) value).toLanguageTag();
        } else if (value instanceof String) {
            return value.toString();
        } else {
            throw conversionException(String.class, value);
        }
    }
}
