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
 * Unit tests for {@code StringIdentity}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.1, 2023-12
 * @since 0.1
 * @see StringIdentity
 */
@Tag("ut")
class StringIdentityTest
extends AbstractSimpleIdentityTest {

    /** Custom prefix. */
    private static final String CUSTOM_PREFIX = "CustomPrefix";

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
        final String value = Generators.randomValue(String.class);
        final StringIdentity identity = new StringIdentity(value);
        assertNotNull(value);
        assertSame(value, identity.getValue());
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
        return new StringIdentity(Generators.randomValue(String.class));
    }

    /**
     * Creates a identity token for the specified value.
     * 
     * @param value The value to create the identity token for
     * @return The identity token created
     */
    protected String createIdentityToken(
            final String value) {
        return IdentityTokenFormatter.format(IdentityTokenFormatter.DEFAULT_PREFIX, value);
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

    /**
     * Test for {@link StringIdentity#extractTokenValue(String)}.
     * @throws Throwable Should not happen
     */
    @ParameterizedTest
    @MethodSource("testExtractTokenValue")
    void testExtractTokenValue(
            final String prefix,
            final String value)
    throws Throwable {
        final String token = IdentityTokenFormatter.format(prefix, value);
        final String result = StringIdentity.extractTokenValue(prefix, token);
        assertEquals(value, result);
    }

    /**
     * Test for {@link StringIdentity#extractRequiredTokenValue(String)}.
     * @throws Throwable Should not happen
     */
    @ParameterizedTest
    @MethodSource("testExtractTokenValue")
    void testExtractRequiredTokenValue(
            final String prefix,
            final String value)
    throws Throwable {
        final String token = IdentityTokenFormatter.format(prefix, value);
        if (value == null) {
            assertThrows(UnrecognizedIdentityTokenException.class, () -> {
                StringIdentity.extractRequiredTokenValue(prefix, token);
            });
        } else {
            final String result = StringIdentity.extractRequiredTokenValue(prefix, token);
            assertEquals(value, result);
        }
    }

    private static Stream<Arguments> testExtractTokenValue() {
        return Stream.of(
                Arguments.of(IdentityTokenFormatter.DEFAULT_PREFIX, (String) null),
                Arguments.of(IdentityTokenFormatter.DEFAULT_PREFIX, ""),
                Arguments.of(IdentityTokenFormatter.DEFAULT_PREFIX, "SimpleValue"),
                Arguments.of(IdentityTokenFormatter.DEFAULT_PREFIX, "mock value"),
                Arguments.of(CUSTOM_PREFIX, (String) null),
                Arguments.of(CUSTOM_PREFIX, ""),
                Arguments.of(CUSTOM_PREFIX, "SimpleValue"),
                Arguments.of(CUSTOM_PREFIX, "mock value")
        );
    }

    /**
     * Test for {@link StringIdentity#resolve()}.
     * @throws Throwable Should not happen
     */
    @Test
    void testResolve()
    throws Throwable {
        final String value = Generators.randomValue(String.class);
        final StringIdentity expected = new StringIdentity(value);
        final String token = expected.getIdentityToken();
        assertSame(expected, expected.resolve(StringIdentity.class));
        assertEquals(expected, TokenIdentity.fromToken(token).resolve(StringIdentity.class));
        final Long longValue = Generators.randomValue(Long.class);
        assertEquals(
                new StringIdentity(String.valueOf(longValue)),
                new LongIdentity(longValue).resolve(StringIdentity.class));
        final BigInteger bigIntValue = Generators.randomValue(BigInteger.class);
        assertEquals(
                new StringIdentity(String.valueOf(bigIntValue)),
                new BigIntegerIdentity(bigIntValue).resolve(StringIdentity.class));
    }

    /**
     * Test for default {@link StringIdentity} generation.
     * @throws Throwable Should not happen
     */
    @Test
    void testGenerable()
    throws Throwable {
        assertTrue(Generators.supports(StringIdentity.class));
        assertNull(Generators.nullableDefaultValue(StringIdentity.class));
        StringIdentity result = Generators.defaultValue(StringIdentity.class);
        assertNotNull(result);
        assertNull(result.getValue());
    }

    /**
     * Test for random {@link StringIdentity} generation.
     * @throws Throwable Should not happen
     */
    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void testRandomGeneration(
            final boolean nullable)
    throws Throwable {
        final HashSet<StringIdentity> results = new HashSet<>();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            boolean nullValues = false;
            while (results.size() < 100 || (nullable && !nullValues)) {
                final StringIdentity result;
                if (nullable) {
                    result = Generators.nullableRandomValue(StringIdentity.class);
                } else {
                    result = Generators.randomValue(StringIdentity.class);
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
