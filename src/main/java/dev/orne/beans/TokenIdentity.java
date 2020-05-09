package dev.orne.beans;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;
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
            @Nonnull
            final String token) {
        super();
        Validate.notBlank(token);
        this.identityToken = token;
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @NotBlank
    @Override
    public String getIdentityToken() {
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
            @Nullable
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
         * @param The value to be converted. Can be null
         * @return The resulting {@code TokenIdentity} instance
         */
        @Nullable
        @Override
        public TokenIdentity unmarshal(
                @Nullable
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
        @Nullable
        @Override
        public String marshal(
                @Nullable
                final Identity identity) {
            final String result;
            if (identity == null
                    || identity.getIdentityToken() == null
                    || identity.getIdentityToken().isEmpty()) {
                result = null;
            } else {
                result = identity.getIdentityToken();
            }
            return result;
        }
    }
}
