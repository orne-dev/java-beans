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

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Locale;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;

import dev.orne.beans.Identity;

/**
 * <p>Utility class for registration of default implementations
 * of Orne custom converters</p>.
 * 
 * <p>Registers the following converters:</p>
 * 
 * <dl>
 *   <dt>Beans converters</dt>
 *   <dd>
 *      <ul>
 *       <li>{@link IdentityConverter}</li>
 *      </ul>
 *   </dd>
 *   <dt>Util converters</dt>
 *   <dd>
 *      <ul>
 *       <li>{@link LocaleConverter}</li>
 *      </ul>
 *   </dd>
 *   <dt>Time converters</dt>
 *   <dd>
 *     <ul>
 *       <li>{@link InstantConverter}</li>
 *       <li>{@link YearConverter}</li>
 *       <li>{@link YearMonthConverter}</li>
 *       <li>{@link MonthConverter}</li>
 *       <li>{@link MonthDayConverter}</li>
 *       <li>{@link DayOfWeekConverter}</li>
 *       <li>{@link LocalDateConverter}</li>
 *       <li>{@link LocalDateTimeConverter}</li>
 *       <li>{@link LocalTimeConverter}</li>
 *       <li>{@link OffsetDateTimeConverter}</li>
 *       <li>{@link OffsetTimeConverter}</li>
 *       <li>{@link ZonedDateTimeConverter}</li>
 *       <li>{@link ZoneOffsetConverter}</li>
 *       <li>{@link DurationConverter}</li>
 *       <li>{@link PeriodConverter}</li>
 *     </ul>
 *   </dd>
 * </dl>
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 */
public final class OrneBeansConverters {

    /**
     * Utility class.
     */
    private OrneBeansConverters() {
        // No instances allowed
    }

    /**
     * Registers all the Orne custom converters in {@code ConvertUtils}.
     *
     * @see ConvertUtils#register(Converter, Class)
     */
    public static void register() {
        registerBeansConversors();
        registerUtilConversors();
        registerTimeConversors();
    }

    /**
     * Registers all the Orne custom converters in {@code ConvertUtils}.
     *
     * @param defaultToNull If {@code null} must be returned on missing
     * ({@code null}) values and on conversion exceptions
     * @see ConvertUtils#register(Converter, Class)
     */
    public static void register(
            final boolean defaultToNull) {
        registerBeansConversors(defaultToNull);
        registerUtilConversors(defaultToNull);
        registerTimeConversors(defaultToNull);
    }

    /**
     * Registers all the Orne custom converters in the specified
     * {@code ConvertUtilsBean}.
     *
     * @param converter Converter to register the Orne converters to
     * @see ConvertUtilsBean#register(Converter, Class)
     */
    public static void register(
            final ConvertUtilsBean converter) {
        registerBeansConversors(converter);
        registerUtilConversors(converter);
        registerTimeConversors(converter);
    }

    /**
     * Registers all the Orne custom converters in the specified
     * {@code ConvertUtilsBean}.
     *
     * @param converter Converter to register the Orne converters to
     * @param defaultToNull If {@code null} must be returned on missing
     * ({@code null}) values and on conversion exceptions
     * @see ConvertUtilsBean#register(Converter, Class)
     */
    public static void register(
            final ConvertUtilsBean converter,
            final boolean defaultToNull) {
        registerBeansConversors(converter, defaultToNull);
        registerUtilConversors(converter, defaultToNull);
        registerTimeConversors(converter, defaultToNull);
    }

    /**
     * Registers all the Orne custom converters for {@code dev.orne.beans} in
     * {@code ConvertUtils}.
     *
     * @see ConvertUtils#register(Converter, Class)
     */
    public static void registerBeansConversors() {
        registerBeansConversors(false);
    }

    /**
     * Registers all the Orne custom converters for {@code dev.orne.beans} in
     * {@code ConvertUtils}.
     *
     * @param defaultToNull If {@code null} must be returned on missing
     * ({@code null}) values and on conversion exceptions
     * @see ConvertUtils#register(Converter, Class)
     */
    public static void registerBeansConversors(
            final boolean defaultToNull) {
        if (defaultToNull) {
            ConvertUtils.register(new IdentityConverter((Identity) null), Identity.class);
        } else {
            ConvertUtils.register(new IdentityConverter(), Identity.class);
        }
    }

    /**
     * Registers all the Orne custom converters for {@code dev.orne.beans} in
     * the specified {@code ConvertUtilsBean}.
     *
     * @param converter Converter to register the Orne converters to
     * @see ConvertUtilsBean#register(Converter, Class)
     */
    public static void registerBeansConversors(
            final ConvertUtilsBean converter) {
        registerBeansConversors(converter, false);
    }

    /**
     * Registers all the Orne custom converters for {@code dev.orne.beans} in
     * the specified {@code ConvertUtilsBean}.
     *
     * @param converter Converter to register the Orne converters to
     * @param defaultToNull If {@code null} must be returned on missing
     * ({@code null}) values and on conversion exceptions
     * @see ConvertUtilsBean#register(Converter, Class)
     */
    public static void registerBeansConversors(
            final ConvertUtilsBean converter,
            final boolean defaultToNull) {
        if (defaultToNull) {
            converter.register(new IdentityConverter((Identity) null), Identity.class);
        } else {
            converter.register(new IdentityConverter(), Identity.class);
        }
    }

    /**
     * Registers all the Orne custom converters for {@code java.util} in
     * {@code ConvertUtils}.
     *
     * @see ConvertUtils#register(Converter, Class)
     */
    public static void registerUtilConversors() {
        registerUtilConversors(false);
    }

    /**
     * Registers all the Orne custom converters for {@code java.util} in
     * {@code ConvertUtils}.
     *
     * @param defaultToNull If {@code null} must be returned on missing
     * ({@code null}) values and on conversion exceptions
     * @see ConvertUtils#register(Converter, Class)
     */
    public static void registerUtilConversors(
            final boolean defaultToNull) {
        if (defaultToNull) {
            ConvertUtils.register(new LocaleConverter((Locale) null), Locale.class);
        } else {
            ConvertUtils.register(new LocaleConverter(), Locale.class);
        }
    }

    /**
     * Registers all the Orne custom converters for {@code java.util} in
     * the specified {@code ConvertUtilsBean}.
     *
     * @param converter Converter to register the Orne converters to
     * @see ConvertUtilsBean#register(Converter, Class)
     */
    public static void registerUtilConversors(
            final ConvertUtilsBean converter) {
        registerUtilConversors(converter, false);
    }

    /**
     * Registers all the Orne custom converters for {@code java.util} in
     * the specified {@code ConvertUtilsBean}.
     *
     * @param converter Converter to register the Orne converters to
     * @param defaultToNull If {@code null} must be returned on missing
     * ({@code null}) values and on conversion exceptions
     * @see ConvertUtilsBean#register(Converter, Class)
     */
    public static void registerUtilConversors(
            final ConvertUtilsBean converter,
            final boolean defaultToNull) {
        if (defaultToNull) {
            converter.register(new LocaleConverter((Locale) null), Locale.class);
        } else {
            converter.register(new LocaleConverter(), Locale.class);
        }
    }

    /**
     * Registers all the Orne custom converters for {@code java.time} in
     * {@code ConvertUtils}.
     *
     * @see ConvertUtils#register(Converter, Class)
     */
    public static void registerTimeConversors() {
        registerTimeConversors(false);
    }

    /**
     * Registers all the Orne custom converters for {@code java.time} in
     * {@code ConvertUtils}.
     *
     * @param defaultToNull If {@code null} must be returned on missing
     * ({@code null}) values and on conversion exceptions
     * @see ConvertUtils#register(Converter, Class)
     */
    public static void registerTimeConversors(
            final boolean defaultToNull) {
        if (defaultToNull) {
            ConvertUtils.register(new InstantConverter((Instant) null), Instant.class);
            ConvertUtils.register(new YearConverter((Year) null), Year.class);
            ConvertUtils.register(new YearMonthConverter((YearMonth) null), YearMonth.class);
            ConvertUtils.register(new MonthConverter((Month) null), Month.class);
            ConvertUtils.register(new MonthDayConverter((MonthDay) null), MonthDay.class);
            ConvertUtils.register(new DayOfWeekConverter((DayOfWeek) null), DayOfWeek.class);
            ConvertUtils.register(new LocalDateConverter((LocalDate) null), LocalDate.class);
            ConvertUtils.register(new LocalTimeConverter((LocalTime) null), LocalTime.class);
            ConvertUtils.register(new LocalDateTimeConverter((LocalDateTime) null), LocalDateTime.class);
            ConvertUtils.register(new OffsetTimeConverter((OffsetTime) null), OffsetTime.class);
            ConvertUtils.register(new OffsetDateTimeConverter((OffsetDateTime) null), OffsetDateTime.class);
            ConvertUtils.register(new ZonedDateTimeConverter((ZonedDateTime) null), ZonedDateTime.class);
            ConvertUtils.register(new ZoneOffsetConverter((ZoneOffset) null), ZoneId.class);
            ConvertUtils.register(new ZoneOffsetConverter((ZoneOffset) null), ZoneOffset.class);
            ConvertUtils.register(new DurationConverter((Duration) null), Duration.class);
            ConvertUtils.register(new PeriodConverter((Period) null), Period.class);
        } else {
            ConvertUtils.register(new InstantConverter(), Instant.class);
            ConvertUtils.register(new YearConverter(), Year.class);
            ConvertUtils.register(new YearMonthConverter(), YearMonth.class);
            ConvertUtils.register(new MonthConverter(), Month.class);
            ConvertUtils.register(new MonthDayConverter(), MonthDay.class);
            ConvertUtils.register(new DayOfWeekConverter(), DayOfWeek.class);
            ConvertUtils.register(new LocalDateConverter(), LocalDate.class);
            ConvertUtils.register(new LocalTimeConverter(), LocalTime.class);
            ConvertUtils.register(new LocalDateTimeConverter(), LocalDateTime.class);
            ConvertUtils.register(new OffsetTimeConverter(), OffsetTime.class);
            ConvertUtils.register(new OffsetDateTimeConverter(), OffsetDateTime.class);
            ConvertUtils.register(new ZonedDateTimeConverter(), ZonedDateTime.class);
            ConvertUtils.register(new ZoneOffsetConverter(), ZoneId.class);
            ConvertUtils.register(new ZoneOffsetConverter(), ZoneOffset.class);
            ConvertUtils.register(new DurationConverter(), Duration.class);
            ConvertUtils.register(new PeriodConverter(), Period.class);
        }
    }

    /**
     * Registers all the Orne custom converters for {@code java.time} in
     * the specified {@code ConvertUtilsBean}.
     *
     * @param converter Converter to register the Orne converters to
     * @see ConvertUtilsBean#register(Converter, Class)
     */
    public static void registerTimeConversors(
            final ConvertUtilsBean converter) {
        registerTimeConversors(converter, false);
    }

    /**
     * Registers all the Orne custom converters for {@code java.time} in
     * the specified {@code ConvertUtilsBean}.
     *
     * @param converter Converter to register the Orne converters to
     * @param defaultToNull If {@code null} must be returned on missing
     * ({@code null}) values and on conversion exceptions
     * @see ConvertUtilsBean#register(Converter, Class)
     */
    public static void registerTimeConversors(
            final ConvertUtilsBean converter,
            final boolean defaultToNull) {
        if (defaultToNull) {
            converter.register(new InstantConverter((Instant) null), Instant.class);
            converter.register(new YearConverter((Year) null), Year.class);
            converter.register(new YearMonthConverter((YearMonth) null), YearMonth.class);
            converter.register(new MonthConverter((Month) null), Month.class);
            converter.register(new MonthDayConverter((MonthDay) null), MonthDay.class);
            converter.register(new DayOfWeekConverter((DayOfWeek) null), DayOfWeek.class);
            converter.register(new LocalDateConverter((LocalDate) null), LocalDate.class);
            converter.register(new LocalTimeConverter((LocalTime) null), LocalTime.class);
            converter.register(new LocalDateTimeConverter((LocalDateTime) null), LocalDateTime.class);
            converter.register(new OffsetTimeConverter((OffsetTime) null), OffsetTime.class);
            converter.register(new OffsetDateTimeConverter((OffsetDateTime) null), OffsetDateTime.class);
            converter.register(new ZonedDateTimeConverter((ZonedDateTime) null), ZonedDateTime.class);
            converter.register(new ZoneOffsetConverter((ZoneOffset) null), ZoneId.class);
            converter.register(new ZoneOffsetConverter((ZoneOffset) null), ZoneOffset.class);
            converter.register(new DurationConverter((Duration) null), Duration.class);
            converter.register(new PeriodConverter((Period) null), Period.class);
        } else {
            converter.register(new InstantConverter(), Instant.class);
            converter.register(new YearConverter(), Year.class);
            converter.register(new YearMonthConverter(), YearMonth.class);
            converter.register(new MonthConverter(), Month.class);
            converter.register(new MonthDayConverter(), MonthDay.class);
            converter.register(new DayOfWeekConverter(), DayOfWeek.class);
            converter.register(new LocalDateConverter(), LocalDate.class);
            converter.register(new LocalTimeConverter(), LocalTime.class);
            converter.register(new LocalDateTimeConverter(), LocalDateTime.class);
            converter.register(new OffsetTimeConverter(), OffsetTime.class);
            converter.register(new OffsetDateTimeConverter(), OffsetDateTime.class);
            converter.register(new ZonedDateTimeConverter(), ZonedDateTime.class);
            converter.register(new ZoneOffsetConverter(), ZoneId.class);
            converter.register(new ZoneOffsetConverter(), ZoneOffset.class);
            converter.register(new DurationConverter(), Duration.class);
            converter.register(new PeriodConverter(), Period.class);
        }
    }
}
