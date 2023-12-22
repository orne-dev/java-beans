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

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.validation.constraints.NotNull;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.AbstractConverter;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

/**
 * Implementation of {@code Converter} that converts {@code Calendar} instances
 * to and from {@code String} using delegating in a instance of
 * {@code InstantConverter}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-08
 * @since 0.3
 */
@API(status=Status.STABLE, since="0.3")
public class GregorianCalendarConverter
extends AbstractConverter {

    /** The converter to use when converting {@code ZonedDateTime} instances. */
    private final @NotNull Converter zonedDateTimeConverter;

    /**
     * Creates a new instance that throws a {@code ConversionException} if an
     * error occurs.
     */
    public GregorianCalendarConverter() {
        super();
        this.zonedDateTimeConverter = new ZonedDateTimeConverter();
    }

    /**
     * Creates a new instance that returns a default value if an error occurs.
     * 
     * @param defaultValue The default value to be returned if the value to be
     * converted is missing or an error occurs converting the value
     */
    public GregorianCalendarConverter(final GregorianCalendar defaultValue) {
        super(defaultValue);
        this.zonedDateTimeConverter = new ZonedDateTimeConverter(
                defaultValue == null ? null : defaultValue.toZonedDateTime());
    }

    /**
     * Creates a new instance that throws a {@code ConversionException} if an
     * error occurs.
     * 
     * @param instantConverter The converter to use when converting
     * {@code ZonedDateTime} instances
     */
    public GregorianCalendarConverter(
            final @NotNull Converter instantConverter) {
        super();
        this.zonedDateTimeConverter = instantConverter;
    }

    /**
     * Creates a new instance that returns a default value if an error occurs.
     * 
     * @param instantConverter The converter to use when converting
     * {@code ZonedDateTime} instances
     * @param defaultValue The default value to be returned if the value to be
     * converted is missing or an error occurs converting the value
     */
    public GregorianCalendarConverter(
            final @NotNull Converter instantConverter,
            final GregorianCalendar defaultValue) {
        super(defaultValue);
        this.zonedDateTimeConverter = instantConverter;
    }

    /**
     * Returns the converter to use when converting {@code ZonedDateTime}
     * instances.
     * 
     * @return The converter to use when converting {@code ZonedDateTime}
     * instances
     */
    protected @NotNull Converter getZonedDateTimeConverter() {
        return this.zonedDateTimeConverter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected @NotNull Class<GregorianCalendar> getDefaultType() {
        return GregorianCalendar.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <T> T convertToType(
            final @NotNull Class<T> type,
            final Object value)
    throws Throwable {
        if (type.isAssignableFrom(GregorianCalendar.class)) {
            if (type.isInstance(value)) {
                return type.cast(value);
            }
            final ZonedDateTime instant = this.zonedDateTimeConverter.convert(ZonedDateTime.class, value);
            if (instant == null) {
                return null;
            } else {
                return type.cast(GregorianCalendar.from(instant));
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
        if (value instanceof Calendar) {
            final ZonedDateTime zdt;
            if (value instanceof GregorianCalendar) {
                zdt = ((GregorianCalendar) value).toZonedDateTime();
            } else {
                zdt = ZonedDateTime.ofInstant(((Calendar) value).toInstant(), ZoneId.systemDefault());
            }
            return this.zonedDateTimeConverter.convert(String.class, zdt);
        } else if (value instanceof String) {
            return value.toString();
        } else {
            throw conversionException(String.class, value);
        }
    }
}
