/**
 * 
 */
package dev.orne.beans.converters;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Implementation of {@code Converter} that converts {@code ZonedDateTime} instances
 * to and from {@code String} using ISO-8601 as {@code String}
 * representation by default.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 */
public class ZonedDateTimeConverter
extends AbstractDateTimeConverter {

    /**
     * Creates a new instance that throws a {@code ConversionException} if an
     * error occurs, using ISO-8601 as default conversion format.
     */
    public ZonedDateTimeConverter() {
        super(DateTimeFormatter.ISO_ZONED_DATE_TIME);
        setDefaultParsers();
    }

    /**
     * Creates a new instance that returns a default value if an error occurs,
     * using ISO-8601 as default conversion format.
     * 
     * @param defaultValue The default value to be returned if the value to be
     * converted is missing or an error occurs converting the value
     */
    public ZonedDateTimeConverter(
            @Nullable
            final ZonedDateTime defaultValue) {
        super(DateTimeFormatter.ISO_ZONED_DATE_TIME, defaultValue);
        setDefaultParsers();
    }

    /**
     * Creates a new instance that throws a {@code ConversionException} if an
     * error occurs.
     * 
     * @param formatter The temporal value formatter and default parser
     */
    public ZonedDateTimeConverter(
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
    public ZonedDateTimeConverter(
            @Nonnull
            final DateTimeFormatter formatter,
            @Nullable
            final ZonedDateTime defaultValue) {
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
                InstantConverter.EPOCH_MILLIS_PARSER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<?> getDefaultType() {
        return ZonedDateTime.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <T extends TemporalAccessor> T fromTemporalAccessor(
            final Class<T> type,
            final TemporalAccessor value) {
        try {
            return type.cast(ZonedDateTime.from(value));
        } catch (final DateTimeException dte) {
            getLogger().debug("Failed to convert temporal accessor to ZonedDateTime directly", dte);
        }
        try {
            return type.cast(LocalDateTime.from(value).atZone(ZoneOffset.UTC));
        } catch (final DateTimeException dte) {
            getLogger().debug("Failed to convert temporal accessor to LocalDateTime directly", dte);
        }
        return type.cast(Instant.from(value).atZone(ZoneOffset.UTC));
    }
}
