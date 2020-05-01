/**
 * 
 */
package dev.orne.beans.converters;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Implementation of {@code Converter} that converts {@code Instant} instances
 * to and from {@code String} using the ISO-8601 as {@code String}
 * representation.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 */
public class InstantConverter
extends AbstractDateTimeConverter {

    /**
     * The epoch milliseconds parser. 
     */
    public static final DateTimeFormatter EPOCH_MILLIS_PARSER =
            new DateTimeFormatterBuilder()
                .appendValue(ChronoField.INSTANT_SECONDS, 1, 19, SignStyle.NEVER)
                .appendValue(ChronoField.MILLI_OF_SECOND, 3)
                .toFormatter();

    /**
     * Creates a new instance that throws a {@code ConversionException} if an
     * error occurs.
     */
    public InstantConverter() {
        super(DateTimeFormatter.ISO_INSTANT);
        setDefaultParsers();
    }

    /**
     * Creates a new instance that returns a default value if an error occurs.
     * 
     * @param defaultValue The default value to be returned if the value to be
     * converted is missing or an error occurs converting the value
     */
    public InstantConverter(final Instant defaultValue) {
        super(DateTimeFormatter.ISO_INSTANT, defaultValue);
        setDefaultParsers();
    }

    /**
     * Creates a new instance that throws a {@code ConversionException} if an
     * error occurs.
     * 
     * @param formatter The temporal value formatter and default parser
     */
    public InstantConverter(
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
    public InstantConverter(
            @Nonnull
            final DateTimeFormatter formatter,
            @Nullable
            final Instant defaultValue) {
        super(formatter, defaultValue);
        setDefaultParsers();
    }

    /**
     * Sets the default parsers for this converter.
     */
    private final void setDefaultParsers() {
        setParsers(
                DateTimeFormatter.ISO_INSTANT,
                DateTimeFormatter.ISO_DATE_TIME,
                EPOCH_MILLIS_PARSER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<Instant> getDefaultType() {
        return Instant.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <T extends TemporalAccessor> T fromTemporalAccessor(
            @Nonnull
            final Class<T> type,
            @Nonnull
            final TemporalAccessor value) {
        try {
            return type.cast(Instant.from(value));
        } catch (final DateTimeException dte) {
            getLogger().debug("Failed to convert temporal accessor to Instant directly", dte);
        }
        return type.cast(LocalDateTime.from(value).toInstant(ZoneOffset.UTC));
    }
}
