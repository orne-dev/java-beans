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
 * Implementation for {@code Identity} for identities composed
 * of a single inner {@code String} value.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 */
public class StringIdentity
extends AbstractSimpleIdentity<String> {

    /** The serial version UID. */
    private static final long serialVersionUID = 5660267975323191055L;

    /**
     * Creates a new instance.
     * 
     * @param value The identity value
     */
    public StringIdentity(
            @Nullable
            final String value) {
        super(value);
    }

    /**
     * Resolves the specified identity token to a valid {@code StringIdentity}
     * 
     * @param token The identity token
     * @return The resolved identity token
     * @throws NullPointerException If the identity token is {@code null}
     * @throws UnrecognizedIdentityTokenException If the identity token is not
     * a valid identity token or it doesn't start with the expected prefix
     */
    @Nonnull
    @IdentityTokenResolver
    public static StringIdentity fromIdentityToken(
            @Nonnull
            final String token)
    throws UnrecognizedIdentityTokenException {
        return new StringIdentity(IdentityTokenFormatter.parse(
                IdentityTokenFormatter.DEFAULT_PREFIX,
                token));
    }
}
