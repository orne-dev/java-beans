/**
 * 
 */
package dev.orne.beans.converters;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Implementation of {@code Converter} that converts {@code ZoneOffset} instances
 * to and from {@code String} using ID as {@code String} representation by default.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 */
public class ZoneOffsetConverter
extends AbstractDateTimeConverter {

    /**
     * The zone offset by id parser. 
     */
    public static final DateTimeFormatter BY_ID_PARSER =
            new DateTimeFormatterBuilder()
                .appendOffsetId()
                .optionalStart()
                .appendLiteral('[')
                .parseCaseSensitive()
                .appendZoneRegionId()
                .appendLiteral(']')
                .toFormatter();

    /**
     * Creates a new instance that throws a {@code ConversionException} if an
     * error occurs, using ISO-8601 as default conversion format.
     */
    public ZoneOffsetConverter() {
        super(BY_ID_PARSER);
        setDefaultParsers();
    }

    /**
     * Creates a new instance that returns a default value if an error occurs,
     * using ISO-8601 as default conversion format.
     * 
     * @param defaultValue The default value to be returned if the value to be
     * converted is missing or an error occurs converting the value
     */
    public ZoneOffsetConverter(
            @Nullable
            final ZoneOffset defaultValue) {
        super(BY_ID_PARSER, defaultValue);
        setDefaultParsers();
    }

    /**
     * Creates a new instance that throws a {@code ConversionException} if an
     * error occurs.
     * 
     * @param formatter The temporal value formatter and default parser
     */
    public ZoneOffsetConverter(
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
    public ZoneOffsetConverter(
            @Nonnull
            final DateTimeFormatter formatter,
            @Nullable
            final ZoneOffset defaultValue) {
        super(formatter, defaultValue);
        setDefaultParsers();
    }

    /**
     * Sets the default parsers for this converter.
     */
    private final void setDefaultParsers() {
        setParsers(
                BY_ID_PARSER,
                DateTimeFormatter.ISO_ZONED_DATE_TIME,
                DateTimeFormatter.ISO_OFFSET_DATE_TIME,
                DateTimeFormatter.ISO_OFFSET_DATE,
                DateTimeFormatter.ISO_OFFSET_TIME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<?> getDefaultType() {
        return ZoneOffset.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <T extends TemporalAccessor> T fromTemporalAccessor(
            final Class<T> type,
            final TemporalAccessor value) {
        return type.cast(ZoneOffset.from(value));
    }
}
