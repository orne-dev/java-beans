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
