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

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    /** The generated identity token cache. */
    private transient String identityToken;

    /**
     * Return the identity token prefix used for instances of this identity
     * type. Must return same value for instances of the same class.
     * 
     * @return The identity token prefix for this class
     */
    @NotNull
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
    protected final String parseIdentityTokenBody(
            final @NotNull String token)
    throws UnrecognizedIdentityTokenException {
        return IdentityTokenFormatter.parse(
                getIdentityTokenPrefix(),
                token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    @ValidIdentityToken
    public String getIdentityToken() {
        synchronized (this) {
            if (this.identityToken == null) {
                this.identityToken = IdentityTokenFormatter.format(
                        getIdentityTokenPrefix(),
                        getIdentityTokenBody());
            }
            return this.identityToken;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getClass())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        return obj.getClass() == getClass();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getIdentityToken();
    }
}
