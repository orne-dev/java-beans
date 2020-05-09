package dev.orne.beans;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


/**
 * Interface representing the identity of a bean. Allows hiding the actual
 * identity implementation from referencing users.
 * 
 * The implementations of this interface must be inmutable.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 */
@XmlJavaTypeAdapter(TokenIdentity.IdentityXmlAdapter.class)
@JsonDeserialize(as=TokenIdentity.class)
public interface Identity
extends Serializable {

    /**
     * Returns the identity token. This token must be unique among non equal
     * instances of the same type of tokens.
     * 
     * @return The identity token
     */
    @Nonnull
    @NotBlank
    @JsonValue
    String getIdentityToken();
}
