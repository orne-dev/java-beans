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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Base abstract implementation for {@code Identity}. Manages the formatting
 * and parsing of identity tokens.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 */
public abstract class AbstractIdentity
implements Identity {

    /** The serial version UID. */
    private static final long serialVersionUID = 1L;

    /**
     * Return the identity token prefix used for instances of this identity
     * type. Must return same value for instances of the same class.
     * 
     * @return The identity token prefix for this class
     */
    @Nonnull
    @ValidIdentityTokenPrefix
    protected String getIdentityTokenPrefix() {
        return IdentityTokenFormatter.DEFAULT_PREFIX;
    }

    /**
     * Returns the identity token body composed from the values of this
     * identity. Equal instances must return equal identity token body.
     * 
     * @return The identity token body for this instance
     */
    @Nullable
    protected abstract String getIdentityTokenBody();

    /**
     * Parses the specified identity token and returns the original identity
     * token body used during formatting.
     * 
     * @param token The token identity
     * @return The original token identity body
     * @throws UnrecognizedIdentityTokenException If the identity token
     * is not recognized
     */
    @Nullable
    protected final String parseIdentityTokenBody(
            @Nonnull
            final String token)
    throws UnrecognizedIdentityTokenException {
        return IdentityTokenFormatter.parse(
                getIdentityTokenPrefix(),
                token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Nonnull
    @ValidIdentityToken
    public String getIdentityToken() {
        return IdentityTokenFormatter.format(
                getIdentityTokenPrefix(),
                getIdentityTokenBody());
    }
}