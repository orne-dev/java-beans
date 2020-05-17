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

import java.io.Serializable;

import javax.annotation.Nullable;

/**
 * Abstract implementation for {@code Identity} for identities composed
 * of a single inner value.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @param <T> The type of the identity value
 */
public abstract class AbstractSimpleIdentity<T extends Serializable>
extends AbstractIdentity {

    /** The serial version UID. */
    private static final long serialVersionUID = -4740745453560262909L;

    /** The identity inner value. */
    @Nullable
    private final T value;

    /**
     * Creates a new instance.
     * 
     * @param value The identity value
     */
    public AbstractSimpleIdentity(
            @Nullable
            final T value) {
        this.value = value;
    }

    /**
     * Returns the identity inner value.
     * 
     * @return The identity inner value
     */
    public T getValue() {
        return this.value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Nullable
    protected String getIdentityTokenBody() {
        return this.value == null ? null : this.value.toString();
    }
}
