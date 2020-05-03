/**
 * 
 */
package dev.orne.beans;

/*-
 * #%L
 * Orne Beans
 * %%
 * Copyright (C) 2020 Orne Developments
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Generic enumeration container. Stores enumeration value as
 * {@code String} using enumeration's {@code name}. Allows conversion between
 * enumerations and between String and enumerations.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-04
 * @since 0.1
 */
public class GenericEnumValue {

    /** The name of the enumeration constant. */
    private final String name;

    /**
     * Creates a new generic value with the specified enumeration constant
     * name.
     * 
     * @param name The name of the enumeration constant
     */
    public GenericEnumValue(
            @Nullable
            final String name) {
        super();
        this.name = name;
    }

    /**
     * Creates a new generic value with the name of the specified enumeration
     * constant.
     * 
     * @param value The enumeration constant which name store
     */
    public GenericEnumValue(
            @Nullable
            final Enum<?> value) {
        super();
        this.name = value == null ? null : value.name();
    }

    /**
     * Returns the stored name of the enumeration constant.
     * 
     * @return The stored name of the enumeration constant
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the enumeration value of the specified type with name
     * equal to the stored name.
     * 
     * @param <T> The type of enumeration to return
     * @param enumType The type of enumeration to return
     * @return The enumeration value for the given name, or {@code null}
     * if {@code name} is {@code null}
     * @throws IllegalArgumentException If the stored value does not exist in
     * specified enumeration type
     */
    public <T extends Enum<T>> T getValue(
            @Nonnull
            final Class<T> enumType) {
        return getValue(this.name, enumType);
    }

    /**
     * Returns the enumeration value of the specified type with name
     * equal to specified name.
     * 
     * @param <T> The type of enumeration to return
     * @param name The name of the enumeration value to return
     * @param enumType The type of enumeration to return
     * @return The enumeration value for the given name, or {@code null}
     * if {@code name} is {@code null}
     * @throws IllegalArgumentException If the specified value does not exist
     * in specified enumeration type
     */
    public static <T extends Enum<T>> T getValue(
            @Nullable
            final String name,
            @Nonnull
            final Class<T> enumType) {
        return name == null ? null : Enum.valueOf(enumType, name);
    }
}
