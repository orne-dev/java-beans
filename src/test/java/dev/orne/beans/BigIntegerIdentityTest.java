package dev.orne.beans;

/*-
 * #%L
 * Orne Beans
 * %%
 * Copyright (C) 2020 - 2023 Orne Developments
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
import java.time.Duration;
import java.util.HashSet;
import java.util.stream.Stream;

import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import dev.orne.test.rnd.Generators;

/**
 * Unit tests for {@code BigIntegerIdentity}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see BigIntegerIdentity
 */
@Tag("ut")
class BigIntegerIdentityTest
extends AbstractSimpleIdentityTest {

    /** Custom prefix. */
    private static final String CUSTOM_PREFIX = "CustomPrefix";

    /**
     * Test for {@link BigIntegerIdentity#BigIntegerIdentity(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testConstructorNullValue()
    throws Throwable {
        final BigIntegerIdentity identity = new BigIntegerIdentity((BigInteger) null);
        assertNull(identity.getValue());
    }

    /**
     * Test for {@link BigIntegerIdentity#BigIntegerIdentity(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testConstructor()
    throws Throwable {
        final BigInteger value = Generators.randomValue(BigInteger.class);
        final BigIntegerIdentity identity = new BigIntegerIdentity(value);
        assertNotNull(value);
        assertSame(value, identity.getValue());
    }

    /**
     * {@inheritDoc}
     */
    protected @NotNull BigIntegerIdentity createInstance() {
        return createInstanceWithNonNullValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected @NotNull BigIntegerIdentity createCopy(
            final @NotNull AbstractIdentity copy) {
        return new BigIntegerIdentity((BigIntegerIdentity) copy);
    }

    /**
     * {@inheritDoc}
     */
    protected @NotNull BigIntegerIdentity createInstanceWithNullValue() {
        return new BigIntegerIdentity((BigInteger) null);
    }

    /**
     * {@inheritDoc}
     */
    protected @NotNull BigIntegerIdentity createInstanceWithNonNullValue() {
        return new BigIntegerIdentity(Generators.randomValue(BigInteger.class));
    }

    /**
     * Creates a identity token for the specified value.
     * 
     * @param value The value to create the identity token for
     * @return The identity token created
     */
    protected @NotNull String createIdentityToken(
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
    protected @NotNull String createNoNumberIdentityToken() {
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
    protected @NotNull BigIntegerIdentity resolveIdentityToken(
            final @NotNull String token)
    throws UnrecognizedIdentityTokenException {
        return BigIntegerIdentity.fromIdentityToken(token);
    }

    /**
     * Test for {@link BigIntegerIdentity#fromIdentityToken(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testFromIdentityTokenNullValue()
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
    void testFromIdentityTokenInvalidValue()
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
    void testFromIdentityToken()
    throws Throwable {
        final BigInteger expectedValue = Generators.randomValue(BigInteger.class);
        final String token = createIdentityToken(expectedValue);
        final BigIntegerIdentity result = resolveIdentityToken(token);
        assertNotNull(result);
        assertNotNull(result.getValue());
        assertEquals(expectedValue, result.getValue());
    }

    /**
     * Test for {@link BigIntegerIdentity#extractTokenValue(String)}.
     * @throws Throwable Should not happen
     */
    @ParameterizedTest
    @MethodSource("testExtractTokenValue")
    void testExtractTokenValue(
            final String prefix,
            final BigInteger value)
    throws Throwable {
        final String token = IdentityTokenFormatter.format(prefix,
                value == null ? null : value.toString());
        final BigInteger result = BigIntegerIdentity.extractTokenValue(prefix, token);
        assertEquals(value, result);
    }

    /**
     * Test for {@link BigIntegerIdentity#extractRequiredTokenValue(String)}.
     * @throws Throwable Should not happen
     */
    @ParameterizedTest
    @MethodSource("testExtractTokenValue")
    void testExtractRequiredTokenValue(
            final String prefix,
            final BigInteger value)
    throws Throwable {
        final String token = IdentityTokenFormatter.format(prefix,
                value == null ? null : value.toString());
        if (value == null) {
            assertThrows(UnrecognizedIdentityTokenException.class, () -> {
                BigIntegerIdentity.extractRequiredTokenValue(prefix, token);
            });
        } else {
            final BigInteger result = BigIntegerIdentity.extractRequiredTokenValue(prefix, token);
            assertEquals(value, result);
        }
    }

    private static Stream<Arguments> testExtractTokenValue() {
        return Stream.of(
                Arguments.of(IdentityTokenFormatter.DEFAULT_PREFIX, (BigInteger) null),
                Arguments.of(IdentityTokenFormatter.DEFAULT_PREFIX, BigInteger.ZERO),
                Arguments.of(IdentityTokenFormatter.DEFAULT_PREFIX, BigInteger.valueOf(1234567890L)),
                Arguments.of(IdentityTokenFormatter.DEFAULT_PREFIX, BigInteger.valueOf(-1234567890L)),
                Arguments.of(CUSTOM_PREFIX, (Long) null),
                Arguments.of(CUSTOM_PREFIX, BigInteger.ZERO),
                Arguments.of(CUSTOM_PREFIX, BigInteger.valueOf(1234567890L)),
                Arguments.of(CUSTOM_PREFIX, BigInteger.valueOf(-1234567890L))
        );
    }

    /**
     * Test for {@link BigIntegerIdentity#resolve()}.
     * @throws Throwable Should not happen
     */
    @Test
    void testResolve()
    throws Throwable {
        final BigInteger value = Generators.randomValue(BigInteger.class);
        final BigIntegerIdentity expected = new BigIntegerIdentity(value);
        final String token = expected.getIdentityToken();
        assertSame(expected, expected.resolve(BigIntegerIdentity.class));
        assertEquals(expected, TokenIdentity.fromToken(token).resolve(BigIntegerIdentity.class));
        final Long longValue = Generators.randomValue(Long.class);
        assertEquals(
                new BigIntegerIdentity(BigInteger.valueOf(longValue)),
                new LongIdentity(longValue).resolve(BigIntegerIdentity.class));
        assertEquals(expected, new StringIdentity(String.valueOf(value)).resolve(BigIntegerIdentity.class));
        final Identity notNumber = new StringIdentity("NotALong");
        assertThrows(UnrecognizedIdentityTokenException.class, () -> {
            notNumber.resolve(BigIntegerIdentity.class);
        });
    }

    /**
     * Test for default {@link BigIntegerIdentity} generation.
     * @throws Throwable Should not happen
     */
    @Test
    void testGenerable()
    throws Throwable {
        assertTrue(Generators.supports(BigIntegerIdentity.class));
        assertNull(Generators.nullableDefaultValue(BigIntegerIdentity.class));
        BigIntegerIdentity result = Generators.defaultValue(BigIntegerIdentity.class);
        assertNotNull(result);
        assertNull(result.getValue());
    }

    /**
     * Test for random {@link BigIntegerIdentity} generation.
     * @throws Throwable Should not happen
     */
    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void testRandomGeneration(
            final boolean nullable)
    throws Throwable {
        final HashSet<BigIntegerIdentity> results = new HashSet<>();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            boolean nullValues = false;
            while (results.size() < 100 || (nullable && !nullValues)) {
                final BigIntegerIdentity result;
                if (nullable) {
                    result = Generators.nullableRandomValue(BigIntegerIdentity.class);
                } else {
                    result = Generators.randomValue(BigIntegerIdentity.class);
                }
                if (result == null) {
                    nullValues = true;
                } else {
                    results.add(result);
                }
            }
            assertTrue(!nullable || nullValues);
        });
        assertTrue(results.size() >= 100);
    }
}
