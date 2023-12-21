package dev.orne.beans.rnd;

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

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import dev.orne.beans.Identity;
import dev.orne.beans.IdentityTokenFormatter;
import dev.orne.beans.TokenIdentity;
import dev.orne.test.rnd.AbstractTypedGenerator;

/**
 * Generator of {@code Identity} and {@code TokenIdentity} values.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2023-12
 * @since 0.6
 */
public class TokenIdentityGenerator
extends AbstractTypedGenerator<TokenIdentity> {

    /** Valid characters for starting character of a valid token body. */
    private static final String VALID_BODY_START_CHARS =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    /** Valid characters for non starting character of a valid token body. */
    private static final String VALID_BODY_CHARS =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_";

    /**
     * Creates a new instance.
     */
    public TokenIdentityGenerator() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(@NotNull Class<?> type) {
        return super.supports(type) || Identity.class.equals(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull TokenIdentity defaultValue() {
        return new TokenIdentity(IdentityTokenFormatter.format(null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull TokenIdentity randomValue() {
        final String prefix = RandomStringUtils.randomAlphabetic(1, 10);
        final String body;
        if (RandomUtils.nextFloat(0f, 1f) < 0.02f) {
            body = null;
        } else if (RandomUtils.nextBoolean()) {
            body = RandomStringUtils.random(1, VALID_BODY_START_CHARS)
                    + RandomStringUtils.random(
                            RandomUtils.nextInt(0, 20),
                            VALID_BODY_CHARS);
        } else {
            body = RandomStringUtils.random(
                    RandomUtils.nextInt(1, 20));
        }
        return new TokenIdentity(IdentityTokenFormatter.format(prefix, body));
    }
}
