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
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.beanutils.converters.AbstractConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@code Converter} that converts {@code LocalDate} instances
 * to and from {@code String} using ISO-8601 as {@code String}
 * representation by default.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 */
public abstract class AbstractDateTimeConverter
extends AbstractConverter {

    /** The temporal value formatter. */
    @Nonnull
    private final DateTimeFormatter formatter;
    /** The temporal value parsers. */
    @Nonnull
    private final Set<DateTimeFormatter> parsers = new LinkedHashSet<>();
    /** The logger for this instance. */
    private Logger logger;

    /**
     * Creates a new instance that throws a {@code ConversionException} if an
     * error occurs.
     * 
     * @param formatter The temporal value formatter and default parser
     */
    public AbstractDateTimeConverter(
            @Nonnull
            final DateTimeFormatter formatter) {
        super();
        this.formatter = formatter;
        this.parsers.add(formatter);
    }

    /**
     * Creates a new instance that returns a default value if an error occurs.
     * 
     * @param formatter The temporal value formatter and default parser
     * @param defaultValue The default value to be returned if the value to be
     * converted is missing or an error occurs converting the value
     */
    public AbstractDateTimeConverter(
            @Nonnull
            final DateTimeFormatter formatter,
            @Nullable
            final TemporalAccessor defaultValue) {
        super(defaultValue);
        this.formatter = formatter;
        this.parsers.add(formatter);
    }

    /**
     * Returns the temporal value formatter and default parser.
     * 
     * @return The temporal value formatter and default parser
     */
    @Nonnull
    public DateTimeFormatter getFormatter() {
        return this.formatter;
    }

    /**
     * Returns the extra temporal value parsers.
     * 
     * @return The extra temporal value parsers
     */
    @Nonnull
    public List<DateTimeFormatter> getParsers() {
        return Collections.unmodifiableList(new ArrayList<>(this.parsers));
    }

    /**
     * Sets the temporal value parsers.
     * 
     * @param parsers The temporal value parsers
     */
    public void setParsers(
            @Nonnull
            final DateTimeFormatter... parsers) {
        setParsers(Arrays.asList(parsers));
    }

    /**
     * Sets the temporal value parsers.
     * 
     * @param parsers The temporal value parsers
     */
    public void setParsers(
            @Nonnull
            final Collection<DateTimeFormatter> parsers) {
        this.parsers.clear();
        this.parsers.addAll(parsers);
    }

    /**
     * Adds specified parsers to the temporal value parsers.
     * 
     * @param parsers The temporal value parsers to add
     */
    public void addParsers(
            @Nonnull
            final DateTimeFormatter... parsers) {
        addParsers(Arrays.asList(parsers));
    }

    /**
     * Adds specified parsers to the temporal value parsers.
     * 
     * @param parsers The temporal value parsers to add
     */
    public void addParsers(
            @Nonnull
            final Collection<DateTimeFormatter> parsers) {
        this.parsers.addAll(parsers);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected abstract Class<?> getDefaultType();

    /**
     * {@inheritDoc}
     */
    @Override
    protected <T> T convertToType(
            @Nonnull
            final Class<T> type,
            @Nonnull
            final Object value) {
        if (TemporalAccessor.class.isAssignableFrom(type)) {
            @SuppressWarnings("unchecked")
            final Class<? extends TemporalAccessor> temporalType =
                    (Class<? extends TemporalAccessor>) type;
            if (value instanceof TemporalAccessor) {
                return type.cast(fromTemporalAccessor(
                        temporalType,
                        (TemporalAccessor) value));
            } else if (value instanceof Long) {
                return type.cast(fromTemporalAccessor(
                        temporalType,
                        Instant.ofEpochMilli((long) value)));
            } else if (value instanceof GregorianCalendar) {
                return type.cast(fromTemporalAccessor(
                        temporalType,
                        ((GregorianCalendar) value).toZonedDateTime()));
            } else if (value instanceof Calendar) {
                return type.cast(fromTemporalAccessor(
                        temporalType,
                        ((Calendar) value).toInstant()));
            } else if (value instanceof Date) {
                return type.cast(fromTemporalAccessor(
                        temporalType,
                        ((Date) value).toInstant()));
            } else {
                return type.cast(parseString(temporalType, value.toString()));
            }
        }
        throw conversionException(type, value);
    }

    /**
     * Tries to parse a {@code TemporalAccessor} of the specified type
     * from the specified {@code String} value. Tries the parses in order
     * calling {@link #parse(Class, DateTimeFormatter, String)}. If all
     * parsers fail throws exception thrown by first parser.
     * 
     * @param <T> The expected type of {@code TemporalAccessor}
     * @param type The expected type of {@code TemporalAccessor}
     * @param value The value to parse
     * @return The parsed {@code TemporalAccessor} of the expected type
     * @throws DateTimeException If the value cannot be parsed
     */
    protected <T extends TemporalAccessor> T parseString(
            @Nonnull
            final Class<T> type,
            @Nonnull
            final String value) {
        DateTimeException firstException = null;
        for (final DateTimeFormatter parser : this.parsers) {
            try {
                return type.cast(parse(type, parser, value));
            } catch (final DateTimeException dte) {
                if (firstException == null) {
                    firstException = dte;
                }
                getLogger().debug("Failed to parse temporal value", dte);
            }
        }
        if (firstException == null) {
            throw conversionException(type, value);
        } else {
            throw firstException;
        }
    }

    /**
     * Tries to parse a {@code TemporalAccessor} of the specified type
     * from the specified value.
     * 
     * @param <T> The expected type of {@code TemporalAccessor}
     * @param type The expected type of {@code TemporalAccessor}
     * @param parser The parser to use for parsing the value
     * @param value The value to parse
     * @return The parsed {@code TemporalAccessor} of the expected type
     * @throws DateTimeException If the value cannot be parsed
     */
    @Nonnull
    protected <T extends TemporalAccessor> T parse(
            final Class<T> type,
            final DateTimeFormatter parser,
            final String value) {
        return fromTemporalAccessor(type, parser.parse(value));
    }

    /**
     * Converts the {@code TemporalAccessor} passed as argument to the
     * specified type.
     * 
     * @param <T> The expected type of {@code TemporalAccessor}
     * @param type The expected type of {@code TemporalAccessor}
     * @param value The {@code TemporalAccessor} to convert to the
     * expected type
     * @return The converted {@code TemporalAccessor} of the expected type
     * @throws DateTimeException If the value cannot be converted
     */
    @Nonnull
    protected abstract <T extends TemporalAccessor> T fromTemporalAccessor(
            @Nonnull
            Class<T> type,
            @Nonnull
            TemporalAccessor value);

    /**
     * {@inheritDoc}
     */
    @Override
    protected String convertToString(
            @Nonnull
            final Object value) {
        if (value instanceof TemporalAccessor) {
            return this.formatter.format((TemporalAccessor) value);
        } else if (value instanceof String) {
            return value.toString();
        } else {
            throw conversionException(String.class, value);
        }
    }

    /**
     * Returns the logger for this instance
     * 
     * @return The logger for this instance
     */
    @Nonnull
    protected Logger getLogger() {
        synchronized (this) {
            if (this.logger == null) {
                this.logger = LoggerFactory.getLogger(getClass());
            }
            return this.logger;
        }
    }
}
