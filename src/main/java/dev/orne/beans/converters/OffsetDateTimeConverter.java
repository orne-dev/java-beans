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

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import javax.validation.constraints.NotNull;

import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

/**
 * Implementation of {@code Converter} that converts {@code OffsetDateTime} instances
 * to and from {@code String} using ISO-8601 as {@code String}
 * representation by default.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 */
@API(status=Status.STABLE, since="0.1")
public class OffsetDateTimeConverter
extends AbstractDateTimeConverter {

    /**
     * Creates a new instance that throws a {@code ConversionException} if an
     * error occurs, using ISO-8601 as default conversion format.
     */
    public OffsetDateTimeConverter() {
        super(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        setDefaultParsers();
    }

    /**
     * Creates a new instance that returns a default value if an error occurs,
     * using ISO-8601 as default conversion format.
     * 
     * @param defaultValue The default value to be returned if the value to be
     * converted is missing or an error occurs converting the value
     */
    public OffsetDateTimeConverter(
            final OffsetDateTime defaultValue) {
        super(DateTimeFormatter.ISO_OFFSET_DATE_TIME, defaultValue);
        setDefaultParsers();
    }

    /**
     * Creates a new instance that throws a {@code ConversionException} if an
     * error occurs.
     * 
     * @param formatter The temporal value formatter and default parser
     */
    public OffsetDateTimeConverter(
            final @NotNull DateTimeFormatter formatter) {
        super(formatter);
        setDefaultParsers();
    }

    /**
     * Creates a new instance that returns a default value if an error occurs.
     * 
     * @param formatter The temporal value formatter and default parser
     * @param defaultValue The default value to be returned if the value to be
     * converted is missing or an error occurs converting the value
     */
    public OffsetDateTimeConverter(
            final @NotNull DateTimeFormatter formatter,
            final OffsetDateTime defaultValue) {
        super(formatter, defaultValue);
        setDefaultParsers();
    }

    /**
     * Sets the default parsers for this converter.
     */
    private final void setDefaultParsers() {
        setParsers(
                DateTimeFormatter.ISO_DATE_TIME,
                DateTimeFormatter.ISO_INSTANT,
                InstantConverter.EPOCH_MILLIS_PARSER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected @NotNull Class<?> getDefaultType() {
        return OffsetDateTime.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <T extends TemporalAccessor> T fromTemporalAccessor(
            final @NotNull Class<T> type,
            final @NotNull TemporalAccessor value) {
        try {
            return type.cast(OffsetDateTime.from(value));
        } catch (final DateTimeException dte) {
            getLogger().debug("Failed to convert temporal accessor to OffsetDateTime directly", dte);
        }
        try {
            return type.cast(LocalDateTime.from(value).atOffset(ZoneOffset.UTC));
        } catch (final DateTimeException dte) {
            getLogger().debug("Failed to convert temporal accessor to LocalDateTime directly", dte);
        }
        return type.cast(Instant.from(value).atOffset(ZoneOffset.UTC));
    }
}
