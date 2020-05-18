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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code LongIdentity}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see LongIdentity
 */
@Tag("ut")
public class LongIdentityTest
extends AbstractSimpleIdentityTest {

    /** The random value generator. */
    private static final Random RND = new Random();

    /**
     * Test for {@link LongIdentity#LongIdentity(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testConstructorNullValue()
    throws Throwable {
        final LongIdentity identity = new LongIdentity((Long) null);
        assertNull(identity.getValue());
    }

    /**
     * Test for {@link LongIdentity#LongIdentity(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testConstructor()
    throws Throwable {
        final Long value = randomValue();
        final LongIdentity identity = new LongIdentity(value);
        assertNotNull(value);
        assertSame(value, identity.getValue());
    }

    /**
     * Generates a random {@code long}.
     * 
     * @return a random {@code long}.
     */
    protected long randomValue() {
        return RND.nextLong();
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    protected LongIdentity createInstance() {
        return createInstanceWithNonNullValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Nonnull
    protected LongIdentity createCopy(
            @Nonnull
            final AbstractIdentity copy) {
        return new LongIdentity((LongIdentity) copy);
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    protected LongIdentity createInstanceWithNullValue() {
        return new LongIdentity((Long) null);
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    protected LongIdentity createInstanceWithNonNullValue() {
        return new LongIdentity(randomValue());
    }

    /**
     * Creates a identity token for the specified value.
     * 
     * @param value The value to create the identity token for
     * @return The identity token created
     */
    protected String createIdentityToken(
            @Nullable
            final Long value) {
        return IdentityTokenFormatter.format(
                IdentityTokenFormatter.DEFAULT_PREFIX,
                value == null ? null : value.toString());
    }

    /**
     * Creates a identity token with an invalid number body.
     * 
     * @return The identity token created
     */
    protected String createNoNumberIdentityToken() {
        return IdentityTokenFormatter.format(
                IdentityTokenFormatter.DEFAULT_PREFIX,
                "not a number");
    }

    /**
     * Creates a identity token for the specified value.
     * 
     * @param value The value to create the identity token for
     * @return The identity token created
     */
    protected LongIdentity resolveIdentityToken(
            @Nonnull
            final String token)
    throws UnrecognizedIdentityTokenException {
        return LongIdentity.fromIdentityToken(token);
    }

    /**
     * Test for {@link LongIdentity#fromIdentityToken(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testFromIdentityTokenNullValue()
    throws Throwable {
        final String token = createIdentityToken(null);
        final LongIdentity result = resolveIdentityToken(token);
        assertNotNull(result);
        assertNull(result.getValue());
    }

    /**
     * Test for {@link LongIdentity#fromIdentityToken(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testFromIdentityTokenInvalidValue()
    throws Throwable {
        final String token = createNoNumberIdentityToken();
        assertThrows(UnrecognizedIdentityTokenException.class, () -> {
            resolveIdentityToken(token);
        });
    }

    /**
     * Test for {@link LongIdentity#fromIdentityToken(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testFromIdentityToken()
    throws Throwable {
        final Long expectedValue = randomValue();
        final String token = createIdentityToken(expectedValue);
        final LongIdentity result = resolveIdentityToken(token);
        assertNotNull(result);
        assertNotNull(result.getValue());
        assertEquals(expectedValue, result.getValue());
    }
}
