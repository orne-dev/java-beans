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

import javax.validation.constraints.NotNull;

import org.apache.commons.beanutils.converters.AbstractConverter;
import org.apache.commons.lang3.Validate;

/**
 * Implementation of {@code Converter} that converts {@code Enum} instances
 * to and from {@code String} using value name as {@code String}
 * representation.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 2.0, 2021-09
 * @since 0.1
 */
public class EnumConverter
extends AbstractConverter {

    /**
     * Shared instance that throws a {@code ConversionException} if an
     * error occurs.
     */
    public static final EnumConverter GENERIC = new EnumConverter();
    /**
     * Shared instance that returns {@code null} if an error occurs.
     */
    public static final EnumConverter GENERIC_DEFAULT = new EnumConverter(null);

    /**
     * Creates a new generic instance that throws a {@code ConversionException} if an
     * error occurs.
     */
    private EnumConverter() {
        super();
    }

    /**
     * Creates a new generic instance that returns a default value if an error
     * occurs.
     * 
     * @param defaultValue The default value to be returned if the value to be
     * converted is missing or an error occurs converting the value
     */
    private EnumConverter(
            final Enum<?> defaultValue) {
        super(defaultValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected @NotNull Class<?> getDefaultType() {
        return Enum.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <T> T convertToType(
            final @NotNull Class<T> type,
            final Object value)
    throws Throwable {
        if (type.isEnum()) {
            if (value == null) {
                return null;
            } else {
                @SuppressWarnings("unchecked")
                final Class<? extends Enum<?>> eType = (Class<? extends Enum<?>>) type;
                return type.cast(enumFromName(eType, value.toString()));
            }
        }
        throw conversionException(type, value);
    }

    /**
     * Returns the enumeration constant of the specified type that matches the
     * specified enumeration name.
     * 
     * @param <T> The enumeration type
     * @param type The enumeration type
     * @param name The name of the constant
     * @return The enumeration constant for the name
     * @throws ConversionException If the type is not an enumeration or no
     * constant matches the specified name
     */
    protected <T> T enumFromName(
            final @NotNull Class<T> type,
            final @NotNull String name) {
        Validate.notNull(type);
        Validate.notNull(name);
        final T[] constants = type.getEnumConstants();
        if (constants == null) {
            throw conversionException(type, name);
        }
        for (final T constant : constants) {
            if (((Enum<?>) constant).name().equals(name)) {
                return constant;
            }
        }
        throw conversionException(type, name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String convertToString(
            final Object value)
    throws Throwable {
        if (value instanceof Enum) {
            return ((Enum<?>) value).name();
        } else if (value instanceof String) {
            return value.toString();
        } else {
            throw conversionException(String.class, value);
        }
    }
}
