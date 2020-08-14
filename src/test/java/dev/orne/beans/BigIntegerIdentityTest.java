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

import java.math.BigInteger;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code BigIntegerIdentity}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see BigIntegerIdentity
 */
@Tag("ut")
public class BigIntegerIdentityTest
extends AbstractSimpleIdentityTest {

    /** The random value generator. */
    private static final Random RND = new Random();

    /**
     * Test for {@link BigIntegerIdentity#BigIntegerIdentity(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testConstructorNullValue()
    throws Throwable {
        final BigIntegerIdentity identity = new BigIntegerIdentity((BigInteger) null);
        assertNull(identity.getValue());
    }

    /**
     * Test for {@link BigIntegerIdentity#BigIntegerIdentity(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testConstructor()
    throws Throwable {
        final BigInteger value = randomValue();
        final BigIntegerIdentity identity = new BigIntegerIdentity(value);
        assertNotNull(value);
        assertSame(value, identity.getValue());
    }

    /**
     * Generates a random {@code BigInteger}.
     * 
     * @return a random {@code BigInteger}.
     */
    @Nonnull
    protected BigInteger randomValue() {
        final byte[] buffer = new byte[RND.nextInt(20) + 1];
        RND.nextBytes(buffer);
        return new BigInteger(buffer);
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    protected BigIntegerIdentity createInstance() {
        return createInstanceWithNonNullValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Nonnull
    protected BigIntegerIdentity createCopy(
            @Nonnull
            final AbstractIdentity copy) {
        return new BigIntegerIdentity((BigIntegerIdentity) copy);
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    protected BigIntegerIdentity createInstanceWithNullValue() {
        return new BigIntegerIdentity((BigInteger) null);
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    protected BigIntegerIdentity createInstanceWithNonNullValue() {
        return new BigIntegerIdentity(randomValue());
    }

    /**
     * Creates a identity token for the specified value.
     * 
     * @param value The value to create the identity token for
     * @return The identity token created
     */
    protected String createIdentityToken(
            @Nullable
            final BigInteger value) {
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
    protected BigIntegerIdentity resolveIdentityToken(
            @Nonnull
            final String token)
    throws UnrecognizedIdentityTokenException {
        return BigIntegerIdentity.fromIdentityToken(token);
    }

    /**
     * Test for {@link BigIntegerIdentity#fromIdentityToken(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testFromIdentityTokenNullValue()
    throws Throwable {
        final String token = createIdentityToken(null);
        final BigIntegerIdentity result = resolveIdentityToken(token);
        assertNotNull(result);
        assertNull(result.getValue());
    }

    /**
     * Test for {@link BigIntegerIdentity#fromIdentityToken(String)}.
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
     * Test for {@link BigIntegerIdentity#fromIdentityToken(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testFromIdentityToken()
    throws Throwable {
        final BigInteger expectedValue = randomValue();
        final String token = createIdentityToken(expectedValue);
        final BigIntegerIdentity result = resolveIdentityToken(token);
        assertNotNull(result);
        assertNotNull(result.getValue());
        assertEquals(expectedValue, result.getValue());
    }
}
