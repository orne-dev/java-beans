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

import java.time.Instant;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.AbstractConverter;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

/**
 * Implementation of {@code Converter} that converts {@code Date} instances
 * to and from {@code String} using delegating in a instance of
 * {@code InstantConverter}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-08
 * @since 0.3
 */
@API(status=Status.STABLE, since="0.3")
public class DateConverter
extends AbstractConverter {

    /** The converter to use when converting {@code Instant} instances. */
    private final @NotNull Converter instantConverter;

    /**
     * Creates a new instance that throws a {@code ConversionException} if an
     * error occurs.
     */
    public DateConverter() {
        super();
        this.instantConverter = new InstantConverter();
    }

    /**
     * Creates a new instance that returns a default value if an error occurs.
     * 
     * @param defaultValue The default value to be returned if the value to be
     * converted is missing or an error occurs converting the value
     */
    public DateConverter(final Date defaultValue) {
        super(defaultValue);
        this.instantConverter = new InstantConverter(
                defaultValue == null ? null : defaultValue.toInstant());
    }

    /**
     * Creates a new instance that throws a {@code ConversionException} if an
     * error occurs.
     * 
     * @param instantConverter The converter to use when converting
     * {@code Instant} instances
     */
    public DateConverter(
            final @NotNull Converter instantConverter) {
        super();
        this.instantConverter = instantConverter;
    }

    /**
     * Creates a new instance that returns a default value if an error occurs.
     * 
     * @param instantConverter The converter to use when converting
     * {@code Instant} instances
     * @param defaultValue The default value to be returned if the value to be
     * converted is missing or an error occurs converting the value
     */
    public DateConverter(
            final @NotNull Converter instantConverter,
            final Date defaultValue) {
        super(defaultValue);
        this.instantConverter = instantConverter;
    }

    /**
     * Returns the converter to use when converting {@code Instant} instances.
     * 
     * @return The converter to use when converting {@code Instant} instances
     */
    protected @NotNull Converter getInstantConverter() {
        return this.instantConverter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected @NotNull Class<Date> getDefaultType() {
        return Date.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <T> T convertToType(
            final @NotNull Class<T> type,
            final Object value)
    throws Throwable {
        if (type.isAssignableFrom(Date.class)) {
            final Instant instant = this.instantConverter.convert(Instant.class, value);
            if (instant == null) {
                return null;
            } else {
                return type.cast(Date.from(instant));
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
        if (value instanceof Date) {
            return this.instantConverter.convert(String.class, ((Date) value).toInstant());
        } else if (value instanceof String) {
            return value.toString();
        } else {
            throw conversionException(String.class, value);
        }
    }
}
