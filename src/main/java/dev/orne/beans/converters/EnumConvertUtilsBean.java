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

import javax.validation.constraints.NotNull;

import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;

/**
 * Extension of {@code ConvertUtilsBean} that falls back to converter registered
 * for {@code Enum} class when converting enumeration types.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2021-09
 * @since 0.4
 */
public class EnumConvertUtilsBean
extends ConvertUtilsBean {

    /**
     * Creates a new instance.
     */
    public EnumConvertUtilsBean() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Converter lookup(
            final @NotNull Class<?> clazz) {
        Converter converter = super.lookup(clazz);
        if (converter == null && clazz.isEnum()) {
            converter = super.lookup(Enum.class);
        }
        return converter;
    }
}
