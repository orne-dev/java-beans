package dev.orne.beans;

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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

/**
 * Interface representing a bean with identity. Allows hiding the actual
 * identity implementation from referencing users.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 */
@BeanReference(IdentityBean.RequireIdentity.class)
public interface IdentityBean {

    /**
     * Returns the instance's identity.
     * 
     * @return The instance's identity
     */
    @NotNull(groups = RequireIdentity.class)
    @Valid
    @ConvertGroup(from = RequireIdentity.class, to = Default.class)
    Identity getIdentity();

    /**
     * Validation group to require a valid identity.
     */
    public static interface RequireIdentity {
        // No extra methods
    }
}
