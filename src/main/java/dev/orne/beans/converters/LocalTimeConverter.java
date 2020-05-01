/**
 * 
 */
package dev.orne.beans.converters;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Implementation of {@code Converter} that converts {@code LocalTime} instances
 * to and from {@code String} using ISO-8601 as {@code String}
 * representation by default.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 */
public class LocalTimeConverter
extends AbstractDateTimeConverter {

    /**
     * Creates a new instance that throws a {@code ConversionException} if an
     * error occurs, using ISO-8601 as default conversion format.
     */
    public LocalTimeConverter() {
        super(DateTimeFormatter.ISO_LOCAL_TIME);
        setDefaultParsers();
    }

    /**
     * Creates a new instance that returns a default value if an error occurs,
     * using ISO-8601 as default conversion format.
     * 
     * @param defaultValue The default value to be returned if the value to be
     * converted is missing or an error occurs converting the value
     */
    public LocalTimeConverter(
            @Nullable
            final LocalTime defaultValue) {
        super(DateTimeFormatter.ISO_LOCAL_TIME, defaultValue);
        setDefaultParsers();
    }

    /**
     * Creates a new instance that throws a {@code ConversionException} if an
     * error occurs.
     * 
     * @param formatter The temporal value formatter and default parser
     */
    public LocalTimeConverter(
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
    public LocalTimeConverter(
            @Nonnull
            final DateTimeFormatter formatter,
            @Nullable
            final LocalTime defaultValue) {
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
                DateTimeFormatter.ISO_TIME,
                InstantConverter.EPOCH_MILLIS_PARSER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<?> getDefaultType() {
        return LocalTime.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <T extends TemporalAccessor> T fromTemporalAccessor(
            final Class<T> type,
            final TemporalAccessor value) {
        try {
            return type.cast(LocalTime.from(value));
        } catch (final DateTimeException dte) {
            getLogger().debug("Failed to convert temporal accessor to LocalTime directly", dte);
        }
        return type.cast(Instant.from(value).atOffset(ZoneOffset.UTC).toLocalTime());
    }
}
