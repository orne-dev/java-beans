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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Implementation of {@code Identity} for identities of unknown format restored
 * from identity tokens.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 */
public class TokenIdentity
implements Identity {

    /** The serial version UID. */
    private static final long serialVersionUID = -1;
    /** The identity token. */
    private final String identityToken;

    /**
     * Creates a new instance.
     * 
     * @param token The identity token
     */
    public TokenIdentity(
            final @NotNull String token) {
        super();
        Validate.notBlank(token);
        this.identityToken = token;
    }

    /**
     * {@inheritDoc}
     */
    
    @Override
    public @NotBlank String getIdentityToken() {
        return this.identityToken;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getClass())
                .append(this.identityToken)
                .toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        final TokenIdentity other = (TokenIdentity) obj;
        return new EqualsBuilder()
                .append(this.identityToken, other.identityToken)
                .isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.identityToken;
    }

    /**
     * Creates the {@code TokenIdentity} from the identity token in {@code String}
     * form. If the token is {@code null} or empty returns {@code null}.
     * 
     * @param token The identity token
     * @return The created {@code TokenIdentity} instance, or {@code null} if
     * invalid token
     */
    @JsonCreator
    public static TokenIdentity fromToken(
            final String token) {
        final TokenIdentity result;
        if (token == null || token.isEmpty()) {
            result = null;
        } else {
            result = new TokenIdentity(token);
        }
        return result;
    }

    /**
     * JAXB adapter for {@code Identity} that converts {@code Identity} instances
     * to and from {@code String} using the identity token as {@code String}
     * representation.
     */
    public static class IdentityXmlAdapter
    extends XmlAdapter<String, Identity> {

        /**
         * Parses specified {@code String} as a {@code TokenIdentity} instance.
         * 
         * @param value The value to be converted. Can be null
         * @return The resulting {@code TokenIdentity} instance
         */
        @Override
        public TokenIdentity unmarshal(
                final String value) {
            final TokenIdentity result;
            if (value == null || value.isEmpty()) {
                result = null;
            } else {
                result = new TokenIdentity(value);
            }
            return result;
        }

        /**
         * Serializes specified {@code Identity} as a {@code String} value.
         * 
         * @param identity The value to be converted. Can be null
         * @return The resulting {@code String} instance
         */
        @Override
        public String marshal(
                final Identity identity) {
            final String result;
            if (identity == null || identity.getIdentityToken().isEmpty()) {
                result = null;
            } else {
                result = identity.getIdentityToken();
            }
            return result;
        }
    }
}
