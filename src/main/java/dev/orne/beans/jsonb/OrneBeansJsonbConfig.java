package dev.orne.beans.jsonb;

/*-
 * #%L
 * Orne Beans
 * %%
 * Copyright (C) 2020 - 2025 Orne Developments
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

import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.adapter.JsonbAdapter;
import javax.validation.constraints.NotNull;

import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

/**
 * Utility class for JSON-B configuration.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2024-03
 * @since 0.7
 */
@API(status=Status.MAINTAINED, since="0.7")
public final class OrneBeansJsonbConfig {

    /**
     * Private constructor.
     */
    private OrneBeansJsonbConfig() {
        // Utility class
    }

    /**
     * Configures specified JSON-B configuration with all
     * JSON-B features provided by the library.
     * 
     * @param config The JSON-B configuration.
     * @return The JSON-B configuration.
     */
    public static @NotNull JsonbConfig configure(
            final @NotNull JsonbConfig config) {
        return config
                .withAdapters(adapters());
    }

    /**
     * Returns the JSON-B adapters provided by the library.
     * 
     * @return The JSON-B adapters provided by the library.
     */
    @SuppressWarnings("rawtypes")
    @API(status=Status.EXPERIMENTAL, since="0.7")
    public static @NotNull JsonbAdapter[] adapters() {
        return new JsonbAdapter[] { new JsonbIdentityAdapter() };
    }
}
