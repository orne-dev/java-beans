package dev.orne.beans.converters;

/*-
 * #%L
 * Orne Beans
 * %%
 * Copyright (C) 2020 - 2021 Orne Developments
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

import java.util.function.BiFunction;
import java.util.function.Function;

import javax.validation.constraints.NotNull;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.converters.AbstractConverter;
import org.apache.commons.lang3.Validate;

/**
 * Implementation of {@code Converter} that converts {@code Enum} instances
 * to and from {@code String} using value name as {@code String}
 * representation.
 * 
 * @param <E> The type of enumeration this instance converts
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.1, 2022-10
 * @since 0.1
 */
public class EnumTypedConverter<E extends Enum<E>>
extends AbstractConverter {

    /** The type of enumeration this instance converts. */
    private final Class<E> enumType;
    /** The {@code String} to {@code Enum} conversion function. */
    private final BiFunction<Class<E>, String, E> stringToEnum;
    /** The {@code Enum} to {@code String} conversion function. */
    private final Function<E, String> enumToString;

    /**
     * Creates a new instance that throws a {@code ConversionException} if an
     * error occurs.
     * 
     * @param enumType The type of enumeration this instance converts
     */
    public EnumTypedConverter(
            final @NotNull Class<E> enumType) {
        this(enumType, Enum::valueOf, Enum::name);
    }

    /**
     * Creates a new instance that returns a default value if an error occurs.
     * 
     * @param enumType The type of enumeration this instance converts
     * @param defaultValue The default value to be returned if the value to be
     * converted is missing or an error occurs converting the value
     */
    public EnumTypedConverter(
            final @NotNull Class<E> enumType,
            final E defaultValue) {
        this(enumType, Enum::valueOf, Enum::name, defaultValue);
    }

    /**
     * Creates a new instance that throws a {@code ConversionException} if an
     * error occurs.
     * 
     * @param enumType The type of enumeration this instance converts
     * @param stringToEnum The {@code String} to {@code Enum} conversion function
     * @param enumToString The {@code Enum} to {@code String} conversion function
     */
    public EnumTypedConverter(
            final @NotNull Class<E> enumType,
            final @NotNull BiFunction<Class<E>, String, E> stringToEnum,
            final @NotNull Function<E, String> enumToString) {
        super();
        this.enumType = Validate.notNull(enumType);
        this.stringToEnum = Validate.notNull(stringToEnum);
        this.enumToString = Validate.notNull(enumToString);
    }

    /**
     * Creates a new instance that returns a default value if an error occurs.
     * 
     * @param enumType The type of enumeration this instance converts
     * @param stringToEnum The {@code String} to {@code Enum} conversion function
     * @param enumToString The {@code Enum} to {@code String} conversion function
     * @param defaultValue The default value to be returned if the value to be
     * converted is missing or an error occurs converting the value
     */
    public EnumTypedConverter(
            final @NotNull Class<E> enumType,
            final @NotNull BiFunction<Class<E>, String, E> stringToEnum,
            final @NotNull Function<E, String> enumToString,
            final E defaultValue) {
        super(defaultValue);
        this.enumType = Validate.notNull(enumType);
        this.stringToEnum = Validate.notNull(stringToEnum);
        this.enumToString = Validate.notNull(enumToString);
    }

    /**
     * Creates a new instance that throws a {@code ConversionException} if an
     * error occurs.
     * 
     * @param <T> The type of enumeration this instance converts
     * @param enumType The type of enumeration this instance converts
     * @return The created converter
     */
    public static <T extends Enum<T>> EnumTypedConverter<T> of(
            final Class<T> enumType) {
        return new EnumTypedConverter<>(enumType);
    }

    /**
     * Creates a new instance that returns a default value if an error occurs.
     * 
     * @param <T> The type of enumeration this instance converts
     * @param enumType The type of enumeration this instance converts
     * @param defaultValue The default value to be returned if the value to be
     * converted is missing or an error occurs converting the value
     * @return The created converter
     */
    public static <T extends Enum<T>> EnumTypedConverter<T> of(
            final Class<T> enumType,
            final T defaultValue) {
        return new EnumTypedConverter<>(enumType, defaultValue);
    }

    /**
     * Registers a new {@code EnumTypedConverter} for the specified enumeration
     * type that throws a {@code ConversionException} if an error occurs to the
     * singleton Apache commons bean utils converter.
     * 
     * @param <T> The type of enumeration this instance converts
     * @param enumType The type of enumeration this instance converts
     */
    public static <T extends Enum<T>> void registerFor(
            final Class<T> enumType) {
        BeanUtilsBean.getInstance().getConvertUtils().register(
                new EnumTypedConverter<>(enumType),
                enumType);
    }

    /**
     * Registers a new {@code EnumTypedConverter} for the specified enumeration
     * type that throws a {@code ConversionException} if an error occurs to the
     * specified Apache commons bean utils converter.
     * 
     * @param <T> The type of enumeration this instance converts
     * @param converter The Apache commons bean utils converter to register to
     * @param enumType The type of enumeration this instance converts
     */
    public static <T extends Enum<T>> void registerFor(
            final ConvertUtilsBean converter,
            final Class<T> enumType) {
        Validate.notNull(converter).register(
                new EnumTypedConverter<>(enumType),
                enumType);
    }

    /**
     * Registers a new {@code EnumTypedConverter} for the specified enumeration
     * type that returns a default value if an error occurs to the singleton
     * Apache commons bean utils converter.
     * 
     * @param <T> The type of enumeration this instance converts
     * @param enumType The type of enumeration this instance converts
     * @param defaultValue The default value to be returned if the value to be
     * converted is missing or an error occurs converting the value
     */
    public static <T extends Enum<T>> void registerFor(
            final Class<T> enumType,
            final T defaultValue) {
        BeanUtilsBean.getInstance().getConvertUtils().register(
                new EnumTypedConverter<>(enumType, defaultValue),
                enumType);
    }

    /**
     * Registers a new {@code EnumTypedConverter} for the specified enumeration
     * type that returns a default value if an error occurs to the specified
     * Apache commons beanutils2 converter.
     * 
     * @param <T> The type of enumeration this instance converts
     * @param converter The Apache commons beanutils2 converter to register to
     * @param enumType The type of enumeration this instance converts
     * @param defaultValue The default value to be returned if the value to be
     * converted is missing or an error occurs converting the value
     */
    public static <T extends Enum<T>> void registerFor(
            final ConvertUtilsBean converter,
            final Class<T> enumType,
            final T defaultValue) {
        Validate.notNull(converter).register(
                new EnumTypedConverter<>(enumType, defaultValue),
                enumType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected @NotNull Class<?> getDefaultType() {
        return this.enumType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <T> T convertToType(
            final @NotNull Class<T> type,
            final Object value)
    throws Throwable {
        if (type.isAssignableFrom(this.enumType)) {
            if (value == null) {
                return null;
            } else {
                return type.cast(stringToEnum.apply(this.enumType, value.toString()));
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
        if (this.enumType.isInstance(value)) {
            return this.enumToString.apply(this.enumType.cast(value));
        } else if (value instanceof String) {
            return value.toString();
        } else {
            throw conversionException(String.class, value);
        }
    }
}
