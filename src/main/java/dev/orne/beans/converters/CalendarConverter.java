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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.AbstractConverter;

/**
 * Implementation of {@code Converter} that converts {@code Calendar} instances
 * to and from {@code String} using delegating in a instance of
 * {@code InstantConverter}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-08
 * @since 0.3
 */
public class CalendarConverter
extends AbstractConverter {

    /** The converter to use when converting {@code Instant} instances. */
    private final Converter instantConverter;

    /** The locale of the calendars to create. */
    private Locale locale;
    /** The time zone of the calendars to create. */
    private TimeZone timeZone;

    /**
     * Creates a new instance that throws a {@code ConversionException} if an
     * error occurs.
     */
    public CalendarConverter() {
        super();
        this.instantConverter = new InstantConverter();
    }

    /**
     * Creates a new instance that returns a default value if an error occurs.
     * 
     * @param defaultValue The default value to be returned if the value to be
     * converted is missing or an error occurs converting the value
     */
    public CalendarConverter(final Calendar defaultValue) {
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
    public CalendarConverter(
            @Nonnull
            final Converter instantConverter) {
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
    public CalendarConverter(
            @Nonnull
            final Converter instantConverter,
            @Nullable
            final Calendar defaultValue) {
        super(defaultValue);
        this.instantConverter = instantConverter;
    }

    /**
     * Returns the converter to use when converting {@code Instant} instances.
     * 
     * @return The converter to use when converting {@code Instant} instances
     */
    protected Converter getInstantConverter() {
        return this.instantConverter;
    }

    /**
     * Returns the locale of the calendars to create.
     * 
     * @return The locale of the calendars to create
     */
    public Locale getLocale() {
        return this.locale;
    }

    /**
     * Sets the locale of the calendars to create.
     * 
     * @param locale The locale of the calendars to create
     */
    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

    /**
     * Returns the time zone of the calendars to create.
     * 
     * @return The time zone of the calendars to create
     */
    public TimeZone getTimeZone() {
        return this.timeZone;
    }

    /**
     * Sets the time zone of the calendars to create.
     * 
     * @param timeZone The time zone of the calendars to create
     */
    public void setTimeZone(final TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<Calendar> getDefaultType() {
        return Calendar.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <T> T convertToType(
            final Class<T> type,
            final Object value)
    throws Throwable {
        if (type.isAssignableFrom(Calendar.class)) {
            if (type.isInstance(value)) {
                return type.cast(value);
            }
            final Instant instant = this.instantConverter.convert(Instant.class, value);
            if (instant == null) {
                return null;
            } else {
                Calendar calendar = null;
                if (this.locale == null && this.timeZone == null) {
                    calendar = Calendar.getInstance();
                } else if (this.locale == null) {
                    calendar = Calendar.getInstance(this.timeZone);
                } else if (this.timeZone == null) {
                    calendar = Calendar.getInstance(this.locale);
                } else {
                    calendar = Calendar.getInstance(this.timeZone, this.locale);
                }
                calendar.setTime(Date.from(instant));
                calendar.setLenient(false);
                return type.cast(calendar);
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
            return this.instantConverter.convert(String.class, ((Calendar) value).toInstant());
        } else if (value instanceof String) {
            return value.toString();
        } else {
            throw conversionException(String.class, value);
        }
    }
}
