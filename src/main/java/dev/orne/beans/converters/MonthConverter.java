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
import java.time.Month;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Implementation of {@code Converter} that converts {@code Month} instances
 * to and from {@code String} using "YYYY-MM" as {@code String}
 * representation.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 */
public class MonthConverter
extends AbstractDateTimeConverter {

    /**
     * The month by value parser. 
     */
    public static final DateTimeFormatter BY_VALUE_PARSER =
            new DateTimeFormatterBuilder()
                .appendValue(ChronoField.MONTH_OF_YEAR)
                .toFormatter();
    /**
     * The month by full text parser, for default locale. 
     */
    public static final DateTimeFormatter BY_FULL_TEXT_PARSER =
            new DateTimeFormatterBuilder()
                .appendText(ChronoField.MONTH_OF_YEAR, TextStyle.FULL_STANDALONE)
                .toFormatter();
    /**
     * The month by short text parser, for default locale. 
     */
    public static final DateTimeFormatter BY_SHORT_TEXT_PARSER =
            new DateTimeFormatterBuilder()
                .appendText(ChronoField.MONTH_OF_YEAR, TextStyle.SHORT_STANDALONE)
                .toFormatter();
    /**
     * The month by short text parser, for default locale. 
     */
    public static final DateTimeFormatter BY_NARROW_TEXT_PARSER =
            new DateTimeFormatterBuilder()
                .appendText(ChronoField.MONTH_OF_YEAR, TextStyle.NARROW_STANDALONE)
                .toFormatter();

    /**
     * Creates a new instance that throws a {@code ConversionException} if an
     * error occurs.
     */
    public MonthConverter() {
        super(BY_VALUE_PARSER);
        setDefaultParsers();
    }

    /**
     * Creates a new instance that returns a default value if an error occurs.
     * 
     * @param defaultValue The default value to be returned if the value to be
     * converted is missing or an error occurs converting the value
     */
    public MonthConverter(
            @Nullable
            final Month defaultValue) {
        super(BY_VALUE_PARSER, defaultValue);
        setDefaultParsers();
    }

    /**
     * Creates a new instance that throws a {@code ConversionException} if an
     * error occurs.
     * 
     * @param formatter The temporal value formatter and default parser
     */
    public MonthConverter(
            @Nonnull
            final DateTimeFormatter formatter) {
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
    public MonthConverter(
            @Nonnull
            final DateTimeFormatter formatter,
            @Nullable
            final Month defaultValue) {
        super(formatter, defaultValue);
        setDefaultParsers();
    }

    /**
     * Sets the default parsers for this converter.
     */
    private final void setDefaultParsers() {
        setParsers(
                BY_VALUE_PARSER,
                BY_FULL_TEXT_PARSER,
                BY_SHORT_TEXT_PARSER,
                BY_NARROW_TEXT_PARSER,
                YearMonthConverter.ISO_8601_PARSER,
                YearMonthConverter.BY_FULL_TEXT_PARSER,
                YearMonthConverter.BY_SHORT_TEXT_PARSER,
                YearMonthConverter.BY_NARROW_TEXT_PARSER,
                MonthDayConverter.ISO_8601_PARSER,
                MonthDayConverter.BY_FULL_TEXT_PARSER,
                MonthDayConverter.BY_SHORT_TEXT_PARSER,
                MonthDayConverter.BY_NARROW_TEXT_PARSER,
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_DATE_TIME,
                DateTimeFormatter.ISO_DATE,
                DateTimeFormatter.ISO_ORDINAL_DATE,
                DateTimeFormatter.ISO_WEEK_DATE,
                DateTimeFormatter.BASIC_ISO_DATE,
                InstantConverter.EPOCH_MILLIS_PARSER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<?> getDefaultType() {
        return Month.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <T> T convertToType(
            final Class<T> type,
            final Object value) {
        if (type.isAssignableFrom(Month.class)) {
            if (value instanceof String) {
                try {
                    return type.cast(Month.valueOf((String) value));
                } catch (final IllegalArgumentException iae) {
                    getLogger().debug("Failed to parse value as constant name", iae);
                    return super.convertToType(type, value);
                }
            } else if (value instanceof Number) {
                try {
                    return type.cast(Month.of(
                            ((Number) value).intValue()));
                } catch (final DateTimeException dte) {
                    getLogger().debug("Failed to parse value as constant value", dte);
                    return super.convertToType(type, value);
                }
            } else {
                return super.convertToType(type, value);
            }
        } else {
            throw conversionException(type, value);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <T extends TemporalAccessor> T fromTemporalAccessor(
            final Class<T> type,
            final TemporalAccessor value) {
        try {
            return type.cast(Month.from(value));
        } catch (final DateTimeException dte) {
            getLogger().debug("Failed to convert temporal accessor to DayOfWeek directly", dte);
        }
        return type.cast(Instant.from(value).atOffset(ZoneOffset.UTC).getMonth());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String convertToString(
            final Object value) {
        if (value instanceof Month) {
            return ((Month) value).name();
        } else {
            return super.convertToString(value);
        }
    }
}
