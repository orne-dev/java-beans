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
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import javax.annotation.Nullable;

import org.apache.commons.beanutils.converters.AbstractConverter;

import dev.orne.beans.GenericEnumValue;

/**
 * <p>Implementation of {@code Converter} that converts {@code EnumRequest}
 * instances to {@code EnumResult} and {@code Enum} instances to
 * {@code String} using value name as {@code String} representation.</p>
 * 
 * <p>Allows enumeration conversions in runtime without additional
 * converter registration:</p>
 * 
 * <pre>
 * ConvertUtilsBean converter = new ConvertUtilsBean();
 * converter.register(new GenericEnumConverter(), GenericEnumValue.class);
 * 
 * GenericEnumValue result = (GenericEnumValue) converter.convert(
 *      "ENUM_VALUE",
 *      GenericEnumValue.class);
 * EnumType value = result.getValue(EnumType.class);
 * </pre>
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-04
 * @since 0.1
 */
public class GenericEnumConverter
extends AbstractConverter {

    /**
     * Creates a new instance that throws a {@code ConversionException} if an
     * error occurs.
     */
    public GenericEnumConverter() {
        super();
    }

    /**
     * Creates a new instance that returns a default value if an error occurs.
     * 
     * @param defaultValue The default inner value of the {@code GenericEnumValue}
     * to be returned if the value to be converted is missing or an error occurs
     * converting the value
     */
    public GenericEnumConverter(
            @Nullable
            final Object defaultValue) {
        super(new GenericEnumValue(defaultValue == null ? null : defaultValue.toString()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<?> getDefaultType() {
        return GenericEnumValue.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <T> T convertToType(
            final Class<T> type,
            final Object value)
    throws Throwable {
        if (GenericEnumValue.class.isAssignableFrom(type)) {
            return type.cast(new GenericEnumValue(value.toString()));
        }
        throw conversionException(type, value);
    }
}
