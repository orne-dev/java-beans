package dev.orne.beans.jsonb;

/*-
 * #%L
 * Orne Beans
 * %%
 * Copyright (C) 2023 Orne Developments
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

import jakarta.json.bind.adapter.JsonbAdapter;

import org.apache.commons.lang3.StringUtils;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import dev.orne.beans.Identity;
import dev.orne.beans.TokenIdentity;

/**
 * JSON-B adapter for {@code Identity} that converts {@code Identity} instances
 * to and from {@code String} using the identity token as {@code String}
 * representation.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2024-03
 * @since 0.7
 */
@API(status=Status.EXPERIMENTAL, since="0.7")
public class JsonbIdentityAdapter
implements JsonbAdapter<Identity, String> {

    /**
     * Creates a new instance.
     */
    public JsonbIdentityAdapter() {
        super();
    }

    /**
     * Serializes specified {@code Identity} as a {@code String} value.
     * 
     * @param identity The value to be converted. Can be null
     * @return The resulting {@code String} instance
     */
    @Override
    public String adaptToJson(
            final Identity identity)
    throws Exception {
        final String result;
        if (identity == null) {
            result = null;
        } else {
            final String token = identity.getIdentityToken();
            if (StringUtils.isEmpty(token)) {
                result = null;
            } else {
                result = identity.getIdentityToken();
            }
        }
        return result;
    }

    /**
     * Parses specified {@code String} as a {@code TokenIdentity} instance.
     * 
     * @param value The value to be converted. Can be null
     * @return The resulting {@code TokenIdentity} instance
     */
    @Override
    public Identity adaptFromJson(
            final String value)
    throws Exception {
        final TokenIdentity result;
        if (value == null || value.isEmpty()) {
            result = null;
        } else {
            result = new TokenIdentity(value);
        }
        return result;
    }
}
