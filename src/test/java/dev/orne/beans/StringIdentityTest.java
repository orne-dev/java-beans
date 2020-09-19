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

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code StringIdentity}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see StringIdentity
 */
@Tag("ut")
class StringIdentityTest
extends AbstractSimpleIdentityTest {

    /** The random value generator. */
    private static final Random RND = new Random();

    /**
     * Test for {@link StringIdentity#StringIdentity(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testConstructorNullValue()
    throws Throwable {
        final StringIdentity identity = new StringIdentity((String) null);
        assertNull(identity.getValue());
    }

    /**
     * Test for {@link StringIdentity#StringIdentity(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testConstructor()
    throws Throwable {
        final String value = "mock value";
        final StringIdentity identity = new StringIdentity(value);
        assertNotNull(value);
        assertSame(value, identity.getValue());
    }

    /**
     * Generates a random {@code String}.
     * 
     * @return a random {@code String}.
     */
    protected @NotNull String randomValue() {
        final byte[] bodyBytes = new byte[RND.nextInt(100) + 1];
        RND.nextBytes(bodyBytes);
        return new String(bodyBytes);
    }

    /**
     * {@inheritDoc}
     */
    protected @NotNull StringIdentity createInstance() {
        return createInstanceWithNonNullValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected @NotNull AbstractSimpleIdentity<?> createCopy(
            final @NotNull AbstractIdentity copy) {
        return new StringIdentity((StringIdentity) copy);
    }

    /**
     * {@inheritDoc}
     */
    protected @NotNull StringIdentity createInstanceWithNullValue() {
        return new StringIdentity((String) null);
    }

    /**
     * {@inheritDoc}
     */
    protected @NotNull StringIdentity createInstanceWithNonNullValue() {
        return new StringIdentity("Some value");
    }

    /**
     * Creates a identity token for the specified value.
     * 
     * @param value The value to create the identity token for
     * @return The identity token created
     */
    protected String createIdentityToken(
            final String value) {
        return IdentityTokenFormatter.format(
                IdentityTokenFormatter.DEFAULT_PREFIX,
                value == null ? null : value);
    }

    /**
     * Creates a identity token for the specified value.
     * 
     * @param value The value to create the identity token for
     * @return The identity token created
     */
    protected StringIdentity resolveIdentityToken(
            final @NotNull String token)
    throws UnrecognizedIdentityTokenException {
        return StringIdentity.fromIdentityToken(token);
    }

    /**
     * Test for {@link StringIdentity#fromIdentityToken(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testFromIdentityTokenNullValue()
    throws Throwable {
        final String token = createIdentityToken(null);
        final StringIdentity result = resolveIdentityToken(token);
        assertNotNull(result);
        assertNull(result.getValue());
    }

    /**
     * Test for {@link StringIdentity#fromIdentityToken(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testFromIdentityToken()
    throws Throwable {
        final String expectedValue = "mock value";
        final String token = createIdentityToken(expectedValue);
        final StringIdentity result = resolveIdentityToken(token);
        assertNotNull(result);
        assertNotNull(result.getValue());
        assertEquals(expectedValue, result.getValue());
    }
}
