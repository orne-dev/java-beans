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
 * Unit tests for {@code LongIdentity}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.1, 2023-11
 * @since 0.1
 * @see LongIdentity
 */
@Tag("ut")
class LongIdentityTest
extends AbstractSimpleIdentityTest {

    /** Custom prefix. */
    private static final String CUSTOM_PREFIX = "CustomPrefix";

    /**
     * Test for {@link LongIdentity#LongIdentity(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testConstructorNullValue()
    throws Throwable {
        final LongIdentity identity = new LongIdentity((Long) null);
        assertNull(identity.getValue());
    }

    /**
     * Test for {@link LongIdentity#LongIdentity(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testConstructor()
    throws Throwable {
        final Long value = Generators.randomValue(Long.class);
        final LongIdentity identity = new LongIdentity(value);
        assertNotNull(value);
        assertSame(value, identity.getValue());
    }

    /**
     * {@inheritDoc}
     */
    protected @NotNull LongIdentity createInstance() {
        return createInstanceWithNonNullValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected @NotNull LongIdentity createCopy(
            final @NotNull AbstractIdentity copy) {
        return new LongIdentity((LongIdentity) copy);
    }

    /**
     * {@inheritDoc}
     */
    protected @NotNull LongIdentity createInstanceWithNullValue() {
        return new LongIdentity((Long) null);
    }

    /**
     * {@inheritDoc}
     */
    protected @NotNull LongIdentity createInstanceWithNonNullValue() {
        return new LongIdentity(Generators.randomValue(Long.class));
    }

    /**
     * Creates a identity token for the specified value.
     * 
     * @param value The value to create the identity token for
     * @return The identity token created
     */
    protected String createIdentityToken(
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
            final @NotNull String token)
    throws UnrecognizedIdentityTokenException {
        return LongIdentity.fromIdentityToken(token);
    }

    /**
     * Test for {@link LongIdentity#fromIdentityToken(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testFromIdentityTokenNullValue()
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
    void testFromIdentityTokenInvalidValue()
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
    void testFromIdentityToken()
    throws Throwable {
        final Long expectedValue = Generators.randomValue(Long.class);
        final String token = createIdentityToken(expectedValue);
        final LongIdentity result = resolveIdentityToken(token);
        assertNotNull(result);
        assertNotNull(result.getValue());
        assertEquals(expectedValue, result.getValue());
    }

    /**
     * Test for {@link LongIdentity#extractTokenValue(String)}.
     * @throws Throwable Should not happen
     */
    @ParameterizedTest
    @MethodSource("testExtractTokenValue")
    void testExtractTokenValue(
            final String prefix,
            final Long value)
    throws Throwable {
        final String token = IdentityTokenFormatter.format(prefix,
                value == null ? null : value.toString());
        final Long result = LongIdentity.extractTokenValue(prefix, token);
        assertEquals(value, result);
    }

    /**
     * Test for {@link LongIdentity#extractRequiredTokenValue(String)}.
     * @throws Throwable Should not happen
     */
    @ParameterizedTest
    @MethodSource("testExtractTokenValue")
    void testExtractRequiredTokenValue(
            final String prefix,
            final Long value)
    throws Throwable {
        final String token = IdentityTokenFormatter.format(prefix,
                value == null ? null : value.toString());
        if (value == null) {
            assertThrows(UnrecognizedIdentityTokenException.class, () -> {
                LongIdentity.extractRequiredTokenValue(prefix, token);
            });
        } else {
            final Long result = LongIdentity.extractRequiredTokenValue(prefix, token);
            assertEquals(value, result);
        }
    }

    private static Stream<Arguments> testExtractTokenValue() {
        return Stream.of(
                Arguments.of(IdentityTokenFormatter.DEFAULT_PREFIX, (Long) null),
                Arguments.of(IdentityTokenFormatter.DEFAULT_PREFIX, 0L),
                Arguments.of(IdentityTokenFormatter.DEFAULT_PREFIX, 1234567890L),
                Arguments.of(IdentityTokenFormatter.DEFAULT_PREFIX, -1234567890L),
                Arguments.of(CUSTOM_PREFIX, (Long) null),
                Arguments.of(CUSTOM_PREFIX, 0L),
                Arguments.of(CUSTOM_PREFIX, 1234567890L),
                Arguments.of(CUSTOM_PREFIX, -1234567890L)
        );
    }

    /**
     * Test for default {@link LongIdentity} generation.
     * @throws Throwable Should not happen
     */
    @Test
    void testGenerable()
    throws Throwable {
        assertTrue(Generators.supports(LongIdentity.class));
        assertNull(Generators.nullableDefaultValue(LongIdentity.class));
        LongIdentity result = Generators.defaultValue(LongIdentity.class);
        assertNotNull(result);
        assertNull(result.getValue());
    }

    /**
     * Test for random {@link LongIdentity} generation.
     * @throws Throwable Should not happen
     */
    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void testRandomGeneration(
            final boolean nullable)
    throws Throwable {
        final HashSet<LongIdentity> results = new HashSet<>();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            boolean nullValues = false;
            while (results.size() < 100 || (nullable && !nullValues)) {
                final LongIdentity result;
                if (nullable) {
                    result = Generators.nullableRandomValue(LongIdentity.class);
                } else {
                    result = Generators.randomValue(LongIdentity.class);
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
