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
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import dev.orne.beans.Identity;
import dev.orne.beans.TokenIdentity;

/**
 * Implementation of {@code Converter} that converts {@code Identity} instances
 * to and from {@code String} using the identity token as {@code String}
 * representation. When converting from {@code String} instances of
 * {@code TokenIdentity} are produced.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 */
@API(status=Status.STABLE, since="0.1")
public class IdentityConverter
extends AbstractConverter {

    /**
     * Creates a new instance that throws a {@code ConversionException} if an
     * error occurs.
     */
    public IdentityConverter() {
        super();
    }

    /**
     * Creates a new instance that returns a default value if an error occurs.
     * 
     * @param defaultValue The default value to be returned if the value to be
     * converted is missing or an error occurs converting the value
     */
    public IdentityConverter(
            final Identity defaultValue) {
        super(defaultValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected @NotNull Class<?> getDefaultType() {
        return Identity.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <T> T convertToType(
            final @NotNull Class<T> type,
            final Object value) {
        if (type.isAssignableFrom(TokenIdentity.class)) {
            if (type.isInstance(value)) {
                return type.cast(value);
            } else {
                return type.cast(TokenIdentity.fromToken(value.toString()));
            }
        } else {
            throw conversionException(type, value);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String convertToString(
            final Object value)
    throws Throwable {
        if (value instanceof Identity) {
            return ((Identity) value).getIdentityToken();
        } else if (value instanceof String) {
            return value.toString();
        } else {
            throw conversionException(String.class, value);
        }
    }
}
