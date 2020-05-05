/**
 * 
 */
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
        if (type.isAssignableFrom(Locale.class)) {
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
