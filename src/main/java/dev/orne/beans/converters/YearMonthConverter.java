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

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.YEAR;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAccessor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Implementation of {@code Converter} that converts {@code YearMonth} instances
 * to and from {@code String} using "YYYY-MM" as {@code String}
 * representation.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 */
public class YearMonthConverter
extends AbstractDateTimeConverter {

    /**
     * The month-day by value parser. 
     */
    public static final DateTimeFormatter ISO_8601_PARSER =
            new DateTimeFormatterBuilder()
                .appendValue(YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
                .appendLiteral('-')
                .appendValue(MONTH_OF_YEAR, 2)
                .toFormatter();
    /**
     * The month-day by full text parser, for default locale. 
     */
    public static final DateTimeFormatter BY_FULL_TEXT_PARSER =
            new DateTimeFormatterBuilder()
                .appendValue(YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
                .appendLiteral('-')
                .appendText(MONTH_OF_YEAR, TextStyle.FULL)
                .toFormatter();
    /**
     * The month-day by short text parser, for default locale. 
     */
    public static final DateTimeFormatter BY_SHORT_TEXT_PARSER =
            new DateTimeFormatterBuilder()
                .appendValue(YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
                .appendLiteral('-')
                .appendText(MONTH_OF_YEAR, TextStyle.SHORT)
                .toFormatter();
    /**
     * The month-day by short text parser, for default locale. 
     */
    public static final DateTimeFormatter BY_NARROW_TEXT_PARSER =
            new DateTimeFormatterBuilder()
                .appendValue(YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
                .appendLiteral('-')
                .appendText(MONTH_OF_YEAR, TextStyle.NARROW)
                .toFormatter();

    /**
     * Creates a new instance that throws a {@code ConversionException} if an
     * error occurs, using ISO-8601 as default conversion format.
     */
    public YearMonthConverter() {
        super(ISO_8601_PARSER);
        setDefaultParsers();
    }

    /**
     * Creates a new instance that returns a default value if an error occurs,
     * using ISO-8601 as default conversion format.
     * 
     * @param defaultValue The default value to be returned if the value to be
     * converted is missing or an error occurs converting the value
     */
    public YearMonthConverter(
            @Nullable
            final YearMonth defaultValue) {
        super(ISO_8601_PARSER, defaultValue);
        setDefaultParsers();
    }

    /**
     * Creates a new instance that throws a {@code ConversionException} if an
     * error occurs.
     * 
     * @param formatter The temporal value formatter and default parser
     */
    public YearMonthConverter(
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
    public YearMonthConverter(
            @Nonnull
            final DateTimeFormatter formatter,
            @Nullable
            final YearMonth defaultValue) {
        super(formatter, defaultValue);
        setDefaultParsers();
    }

    /**
     * Sets the default parsers for this converter.
     */
    private final void setDefaultParsers() {
        setParsers(
                ISO_8601_PARSER,
                BY_FULL_TEXT_PARSER,
                BY_SHORT_TEXT_PARSER,
                BY_NARROW_TEXT_PARSER,
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
        return YearMonth.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <T extends TemporalAccessor> T fromTemporalAccessor(
            final Class<T> type,
            final TemporalAccessor value) {
        try {
            return type.cast(YearMonth.from(value));
        } catch (final DateTimeException dte) {
            getLogger().debug("Failed to convert temporal accessor to YearMonth directly", dte);
        }
        return type.cast(YearMonth.from(Instant.from(value).atOffset(ZoneOffset.UTC)));
    }
}
