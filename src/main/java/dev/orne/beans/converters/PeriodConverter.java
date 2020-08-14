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

import java.time.Period;

import org.apache.commons.beanutils.converters.AbstractConverter;

/**
 * Implementation of {@code Converter} that converts {@code Period} instances
 * to and from {@code String} using ISO-8601 as {@code String}
 * representation.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 */
public class PeriodConverter
extends AbstractConverter {

    /**
     * Creates a new instance that throws a {@code ConversionException} if an
     * error occurs.
     */
    public PeriodConverter() {
        super();
    }

    /**
     * Creates a new instance that returns a default value if an error occurs.
     * 
     * @param defaultValue The default value to be returned if the value to be
     * converted is missing or an error occurs converting the value
     */
    public PeriodConverter(final Period defaultValue) {
        super(defaultValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<?> getDefaultType() {
        return Period.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <T> T convertToType(
            final Class<T> type,
            final Object value)
    throws Throwable {
        if (type.isAssignableFrom(Period.class)) {
            return type.cast(Period.parse(value.toString()));
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
        if (value instanceof Period) {
            return ((Period) value).toString();
        } else if (value instanceof String) {
            return value.toString();
        } else {
            throw conversionException(String.class, value);
        }
    }
}
