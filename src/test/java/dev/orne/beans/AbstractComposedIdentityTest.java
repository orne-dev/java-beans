package dev.orne.beans;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.math.BigInteger;

import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.test.rnd.Generators;

/**
 * Unit tests for {@code AbstractComposedIdentity}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2023-12
 * @since 0.6
 * @see AbstractComposedIdentity
 */
@Tag("ut")
class AbstractComposedIdentityTest
extends AbstractIdentityTest {

    /**
     * {@inheritDoc}
     */
    @Override
    protected @NotNull AbstractIdentity createInstance() {
        return new TestIdentity(
                Generators.nullableRandomValue(String.class),
                Generators.nullableRandomValue(Long.class),
                Generators.nullableRandomValue(BigInteger.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected @NotNull AbstractIdentity createCopy(
            final @NotNull AbstractIdentity copy) {
        return new TestIdentity((TestIdentity) copy);
    }

    /**
     * Test for {@link AbstractComposedIdentity#getIdentityTokenBodyPartsSeparator()}.
     */
    @Test
    void testGetIdentityTokenBodyPartsSeparator() {
        final AbstractComposedIdentity identity = spy(AbstractComposedIdentity.class);
        given(identity.getIdentityTokenBodyPartsSeparator()).willCallRealMethod();
        assertEquals(
                AbstractComposedIdentity.DEFAULT_SEPARATOR,
                identity.getIdentityTokenBodyPartsSeparator());
    }

    /**
     * Test for {@link AbstractComposedIdentity#getIdentityTokenBodyPartsNullPlaceholder()}.
     */
    @Test
    void testGetIdentityTokenBodyPartsNullPlaceholder() {
        final AbstractComposedIdentity identity = spy(AbstractComposedIdentity.class);
        given(identity.getIdentityTokenBodyPartsNullPlaceholder()).willCallRealMethod();
        assertEquals(
                AbstractComposedIdentity.DEFAULT_NULL_PLACEHOLDER,
                identity.getIdentityTokenBodyPartsNullPlaceholder());
    }

    /**
     * Test for {@link AbstractComposedIdentity#getIdentityTokenBody()}.
     */
    @Test
    void testGetIdentityTokenBody() {
        final String separator = "<sep>";
        final String nullPlaceholder = "<null>";
        final AbstractComposedIdentity identity = spy(AbstractComposedIdentity.class);
        given(identity.getIdentityTokenBodyPartsSeparator()).willReturn(separator);
        given(identity.getIdentityTokenBodyPartsNullPlaceholder()).willReturn(nullPlaceholder);
        given(identity.getIdentityTokenBodyParts()).willReturn(new String[] { "first", "second" });
        assertEquals("first<sep>second", identity.getIdentityTokenBody());
        given(identity.getIdentityTokenBodyParts()).willReturn(new String[] { "first", null, "third" });
        assertEquals("first<sep><null><sep>third", identity.getIdentityTokenBody());
        given(identity.getIdentityTokenBodyParts()).willReturn(null);
        assertThrows(NullPointerException.class, () -> {
            identity.getIdentityTokenBody();
        });
        given(identity.getIdentityTokenBodyParts()).willReturn(new String[] {});
        assertNull(identity.getIdentityTokenBody());
        given(identity.getIdentityTokenBodyParts()).willReturn(new String[] { "" });
        assertEquals("", identity.getIdentityTokenBody());
    }

    /**
     * Test for {@link AbstractComposedIdentity#extractTokenBodyParts(String, String)}.
     */
    @Test
    void testExtractTokenBodyParts_Simple() {
        final String prefix = "PREFIX";
        final String value0 = Generators.randomValue(String.class);
        final String value1 = Generators.randomValue(String.class);
        final String token = IdentityTokenFormatter.format(prefix,
                value0
                + AbstractComposedIdentity.DEFAULT_SEPARATOR + value1);
        assertThrows(NullPointerException.class, () -> AbstractComposedIdentity.extractTokenBodyParts(
                null,
                token));
        assertThrows(NullPointerException.class, () -> AbstractComposedIdentity.extractTokenBodyParts(
                prefix,
                null));
        assertArrayEquals(
                new String[] {},
                AbstractComposedIdentity.extractTokenBodyParts(
                    prefix,
                    prefix + IdentityTokenFormatter.NULL_BODY));
        assertArrayEquals(
                new String[] { "" },
                AbstractComposedIdentity.extractTokenBodyParts(
                    prefix,
                    prefix));
        assertArrayEquals(
                new String[] { value0, value1 },
                AbstractComposedIdentity.extractTokenBodyParts(
                    prefix,
                    IdentityTokenFormatter.format(prefix,
                            value0
                            + AbstractComposedIdentity.DEFAULT_SEPARATOR + value1)));
        assertArrayEquals(
                new String[] { null, value0, value1 },
                AbstractComposedIdentity.extractTokenBodyParts(
                    prefix,
                    IdentityTokenFormatter.format(prefix,
                            AbstractComposedIdentity.DEFAULT_NULL_PLACEHOLDER
                            + AbstractComposedIdentity.DEFAULT_SEPARATOR + value0
                            + AbstractComposedIdentity.DEFAULT_SEPARATOR + value1)));
        assertArrayEquals(
                new String[] { value0, null, value1 },
                AbstractComposedIdentity.extractTokenBodyParts(
                    prefix,
                    IdentityTokenFormatter.format(prefix,
                            value0
                            + AbstractComposedIdentity.DEFAULT_SEPARATOR + AbstractComposedIdentity.DEFAULT_NULL_PLACEHOLDER +
                            AbstractComposedIdentity.DEFAULT_SEPARATOR + value1)));
        assertArrayEquals(
                new String[] { value0, value1, null },
                AbstractComposedIdentity.extractTokenBodyParts(
                    prefix,
                    IdentityTokenFormatter.format(prefix,
                            value0
                            + AbstractComposedIdentity.DEFAULT_SEPARATOR + value1
                            + AbstractComposedIdentity.DEFAULT_SEPARATOR + AbstractComposedIdentity.DEFAULT_NULL_PLACEHOLDER)));
    }

    /**
     * Test for {@link AbstractComposedIdentity#extractTokenBodyParts(String, String, String)}.
     */
    @Test
    void testExtractTokenBodyParts_Separator() {
        final String prefix = "PREFIX";
        final String separator = "<sep>";
        final String value0 = Generators.randomValue(String.class);
        final String value1 = Generators.randomValue(String.class);
        final String token = IdentityTokenFormatter.format(prefix,
                value0
                + AbstractComposedIdentity.DEFAULT_SEPARATOR + value1);
        assertThrows(NullPointerException.class, () -> AbstractComposedIdentity.extractTokenBodyParts(
                null,
                token,
                separator));
        assertThrows(NullPointerException.class, () -> AbstractComposedIdentity.extractTokenBodyParts(
                prefix,
                null,
                separator));
        assertThrows(NullPointerException.class, () -> AbstractComposedIdentity.extractTokenBodyParts(
                prefix,
                token,
                null));
        assertArrayEquals(
                new String[] {},
                AbstractComposedIdentity.extractTokenBodyParts(
                    prefix,
                    prefix + IdentityTokenFormatter.NULL_BODY,
                    separator));
        assertArrayEquals(
                new String[] { "" },
                AbstractComposedIdentity.extractTokenBodyParts(
                    prefix,
                    prefix,
                    separator));
        assertArrayEquals(
                new String[] { value0, value1 },
                AbstractComposedIdentity.extractTokenBodyParts(
                    prefix,
                    IdentityTokenFormatter.format(prefix,
                            value0
                            + separator + value1),
                    separator));
        assertArrayEquals(
                new String[] { null, value0, value1 },
                AbstractComposedIdentity.extractTokenBodyParts(
                    prefix,
                    IdentityTokenFormatter.format(prefix,
                            AbstractComposedIdentity.DEFAULT_NULL_PLACEHOLDER
                            + separator + value0
                            + separator + value1),
                    separator));
        assertArrayEquals(
                new String[] { value0, null, value1 },
                AbstractComposedIdentity.extractTokenBodyParts(
                    prefix,
                    IdentityTokenFormatter.format(prefix,
                            value0
                            + separator + AbstractComposedIdentity.DEFAULT_NULL_PLACEHOLDER +
                            separator + value1),
                    separator));
        assertArrayEquals(
                new String[] { value0, value1, null },
                AbstractComposedIdentity.extractTokenBodyParts(
                    prefix,
                    IdentityTokenFormatter.format(prefix,
                            value0
                            + separator + value1
                            + separator + AbstractComposedIdentity.DEFAULT_NULL_PLACEHOLDER),
                    separator));
    }

    /**
     * Test for {@link AbstractComposedIdentity#extractTokenBodyParts(String, String, String, String)}.
     */
    @Test
    void testExtractTokenBodyParts_Full() {
        final String prefix = "PREFIX";
        final String separator = "<sep>";
        final String nullPlaceholder = "<null>";
        final String value0 = Generators.randomValue(String.class);
        final String value1 = Generators.randomValue(String.class);
        final String token = IdentityTokenFormatter.format(prefix,
                value0
                + AbstractComposedIdentity.DEFAULT_SEPARATOR + value1);
        assertThrows(NullPointerException.class, () -> AbstractComposedIdentity.extractTokenBodyParts(
                null,
                token,
                separator,
                nullPlaceholder));
        assertThrows(NullPointerException.class, () -> AbstractComposedIdentity.extractTokenBodyParts(
                prefix,
                null,
                separator,
                nullPlaceholder));
        assertThrows(NullPointerException.class, () -> AbstractComposedIdentity.extractTokenBodyParts(
                prefix,
                token,
                null,
                nullPlaceholder));
        assertThrows(NullPointerException.class, () -> AbstractComposedIdentity.extractTokenBodyParts(
                prefix,
                token,
                separator,
                null));
        assertArrayEquals(
                new String[] {},
                AbstractComposedIdentity.extractTokenBodyParts(
                    prefix,
                    prefix + IdentityTokenFormatter.NULL_BODY,
                    separator,
                    nullPlaceholder));
        assertArrayEquals(
                new String[] { "" },
                AbstractComposedIdentity.extractTokenBodyParts(
                    prefix,
                    prefix,
                    separator,
                    nullPlaceholder));
        assertArrayEquals(
                new String[] { value0, value1 },
                AbstractComposedIdentity.extractTokenBodyParts(
                    prefix,
                    IdentityTokenFormatter.format(prefix,
                            value0
                            + separator + value1),
                    separator,
                    nullPlaceholder));
        assertArrayEquals(
                new String[] { null, value0, value1 },
                AbstractComposedIdentity.extractTokenBodyParts(
                    prefix,
                    IdentityTokenFormatter.format(prefix,
                            nullPlaceholder
                            + separator + value0
                            + separator + value1),
                    separator,
                    nullPlaceholder));
        assertArrayEquals(
                new String[] { value0, null, value1 },
                AbstractComposedIdentity.extractTokenBodyParts(
                    prefix,
                    IdentityTokenFormatter.format(prefix,
                            value0
                            + separator + nullPlaceholder
                            + separator + value1),
                    separator,
                    nullPlaceholder));
        assertArrayEquals(
                new String[] { value0, value1, null },
                AbstractComposedIdentity.extractTokenBodyParts(
                    prefix,
                    IdentityTokenFormatter.format(prefix,
                            value0
                            + separator + value1
                            + separator + nullPlaceholder),
                    separator,
                    nullPlaceholder));
    }

    /**
     * Test for {@link AbstractComposedIdentity#extractRequiredTokenBodyParts(String, String, int)}.
     */
    @Test
    void testExtractRequiredTokenBodyParts_Simple() {
        final String prefix = "PREFIX";
        final String value0 = Generators.randomValue(String.class);
        final String value1 = Generators.randomValue(String.class);
        final String token = IdentityTokenFormatter.format(prefix,
                value0
                + AbstractComposedIdentity.DEFAULT_SEPARATOR + value1);
        assertThrows(NullPointerException.class, () -> AbstractComposedIdentity.extractRequiredTokenBodyParts(
                null,
                token,
                0));
        assertThrows(NullPointerException.class, () -> AbstractComposedIdentity.extractRequiredTokenBodyParts(
                prefix,
                null,
                0));
        assertThrows(UnrecognizedIdentityTokenException.class, () -> AbstractComposedIdentity.extractRequiredTokenBodyParts(
                prefix,
                token,
                0));
        assertThrows(UnrecognizedIdentityTokenException.class, () -> AbstractComposedIdentity.extractRequiredTokenBodyParts(
                prefix,
                token,
                1));
        assertThrows(UnrecognizedIdentityTokenException.class, () -> AbstractComposedIdentity.extractRequiredTokenBodyParts(
                prefix,
                token,
                3));
        assertArrayEquals(
                new String[] {},
                AbstractComposedIdentity.extractRequiredTokenBodyParts(
                    prefix,
                    prefix + IdentityTokenFormatter.NULL_BODY,
                    0));
        assertArrayEquals(
                new String[] { "" },
                AbstractComposedIdentity.extractRequiredTokenBodyParts(
                    prefix,
                    prefix,
                    1));
        assertArrayEquals(
                new String[] { value0, value1 },
                AbstractComposedIdentity.extractRequiredTokenBodyParts(
                    prefix,
                    token,
                    2));
        assertArrayEquals(
                new String[] { null, value0, value1 },
                AbstractComposedIdentity.extractRequiredTokenBodyParts(
                    prefix,
                    IdentityTokenFormatter.format(prefix,
                            AbstractComposedIdentity.DEFAULT_NULL_PLACEHOLDER
                            + AbstractComposedIdentity.DEFAULT_SEPARATOR + value0
                            + AbstractComposedIdentity.DEFAULT_SEPARATOR + value1),
                    3));
        assertArrayEquals(
                new String[] { value0, null, value1 },
                AbstractComposedIdentity.extractRequiredTokenBodyParts(
                    prefix,
                    IdentityTokenFormatter.format(prefix,
                            value0
                            + AbstractComposedIdentity.DEFAULT_SEPARATOR + AbstractComposedIdentity.DEFAULT_NULL_PLACEHOLDER
                            + AbstractComposedIdentity.DEFAULT_SEPARATOR + value1),
                    3));
        assertArrayEquals(
                new String[] { value0, value1, null },
                AbstractComposedIdentity.extractRequiredTokenBodyParts(
                    prefix,
                    IdentityTokenFormatter.format(prefix,
                            value0
                            + AbstractComposedIdentity.DEFAULT_SEPARATOR + value1
                            + AbstractComposedIdentity.DEFAULT_SEPARATOR + AbstractComposedIdentity.DEFAULT_NULL_PLACEHOLDER),
                    3));
    }

    /**
     * Test for {@link AbstractComposedIdentity#extractRequiredTokenBodyParts(String, String, String, int)}.
     */
    @Test
    void testExtractRequiredTokenBodyParts_Separator() {
        final String prefix = "PREFIX";
        final String separator = "<sep>";
        final String value0 = Generators.randomValue(String.class);
        final String value1 = Generators.randomValue(String.class);
        final String token = IdentityTokenFormatter.format(prefix,
                value0
                + separator + value1);
        assertThrows(NullPointerException.class, () -> AbstractComposedIdentity.extractRequiredTokenBodyParts(
                null,
                token,
                separator,
                0));
        assertThrows(NullPointerException.class, () -> AbstractComposedIdentity.extractRequiredTokenBodyParts(
                prefix,
                null,
                separator,
                0));
        assertThrows(NullPointerException.class, () -> AbstractComposedIdentity.extractRequiredTokenBodyParts(
                prefix,
                token,
                null,
                0));
        assertThrows(UnrecognizedIdentityTokenException.class, () -> AbstractComposedIdentity.extractRequiredTokenBodyParts(
                prefix,
                token,
                separator,
                0));
        assertThrows(UnrecognizedIdentityTokenException.class, () -> AbstractComposedIdentity.extractRequiredTokenBodyParts(
                prefix,
                token,
                separator,
                1));
        assertThrows(UnrecognizedIdentityTokenException.class, () -> AbstractComposedIdentity.extractRequiredTokenBodyParts(
                prefix,
                token,
                separator,
                3));
        assertArrayEquals(
                new String[] {},
                AbstractComposedIdentity.extractRequiredTokenBodyParts(
                    prefix,
                    prefix + IdentityTokenFormatter.NULL_BODY,
                    separator,
                    0));
        assertArrayEquals(
                new String[] { "" },
                AbstractComposedIdentity.extractRequiredTokenBodyParts(
                    prefix,
                    prefix,
                    separator,
                    1));
        assertArrayEquals(
                new String[] { value0, value1 },
                AbstractComposedIdentity.extractRequiredTokenBodyParts(
                    prefix,
                    token,
                    separator,
                    2));
        assertArrayEquals(
                new String[] { null, value0, value1 },
                AbstractComposedIdentity.extractRequiredTokenBodyParts(
                    prefix,
                    IdentityTokenFormatter.format(prefix,
                            AbstractComposedIdentity.DEFAULT_NULL_PLACEHOLDER
                            + separator + value0
                            + separator + value1),
                    separator,
                    3));
        assertArrayEquals(
                new String[] { value0, null, value1 },
                AbstractComposedIdentity.extractRequiredTokenBodyParts(
                    prefix,
                    IdentityTokenFormatter.format(prefix,
                            value0
                            + separator + AbstractComposedIdentity.DEFAULT_NULL_PLACEHOLDER
                            + separator + value1),
                    separator,
                    3));
        assertArrayEquals(
                new String[] { value0, value1, null },
                AbstractComposedIdentity.extractRequiredTokenBodyParts(
                    prefix,
                    IdentityTokenFormatter.format(prefix,
                            value0
                            + separator + value1
                            + separator + AbstractComposedIdentity.DEFAULT_NULL_PLACEHOLDER),
                    separator,
                    3));
    }

    /**
     * Test for {@link AbstractComposedIdentity#extractRequiredTokenBodyParts(String, String, String, String, int)}.
     */
    @Test
    void testExtractRequiredTokenBodyParts_Full() {
        final String prefix = "PREFIX";
        final String separator = "<sep>";
        final String nullPlaceholder = "<null>";
        final String value0 = Generators.randomValue(String.class);
        final String value1 = Generators.randomValue(String.class);
        final String token = IdentityTokenFormatter.format(prefix,
                value0
                + separator + value1);
        assertThrows(NullPointerException.class, () -> AbstractComposedIdentity.extractRequiredTokenBodyParts(
                null,
                token,
                separator,
                nullPlaceholder,
                0));
        assertThrows(NullPointerException.class, () -> AbstractComposedIdentity.extractRequiredTokenBodyParts(
                prefix,
                null,
                separator,
                nullPlaceholder,
                0));
        assertThrows(NullPointerException.class, () -> AbstractComposedIdentity.extractRequiredTokenBodyParts(
                prefix,
                token,
                null,
                nullPlaceholder,
                0));
        assertThrows(NullPointerException.class, () -> AbstractComposedIdentity.extractRequiredTokenBodyParts(
                prefix,
                token,
                separator,
                null,
                0));
        assertThrows(UnrecognizedIdentityTokenException.class, () -> AbstractComposedIdentity.extractRequiredTokenBodyParts(
                prefix,
                token,
                separator,
                nullPlaceholder,
                0));
        assertThrows(UnrecognizedIdentityTokenException.class, () -> AbstractComposedIdentity.extractRequiredTokenBodyParts(
                prefix,
                token,
                separator,
                nullPlaceholder,
                1));
        assertThrows(UnrecognizedIdentityTokenException.class, () -> AbstractComposedIdentity.extractRequiredTokenBodyParts(
                prefix,
                token,
                separator,
                nullPlaceholder,
                3));
        assertArrayEquals(
                new String[] {},
                AbstractComposedIdentity.extractRequiredTokenBodyParts(
                    prefix,
                    prefix + IdentityTokenFormatter.NULL_BODY,
                    separator,
                    nullPlaceholder,
                    0));
        assertArrayEquals(
                new String[] { "" },
                AbstractComposedIdentity.extractRequiredTokenBodyParts(
                    prefix,
                    prefix,
                    separator,
                    nullPlaceholder,
                    1));
        assertArrayEquals(
                new String[] { value0, value1 },
                AbstractComposedIdentity.extractRequiredTokenBodyParts(
                    prefix,
                    token,
                    separator,
                    nullPlaceholder,
                    2));
        assertArrayEquals(
                new String[] { null, value0, value1 },
                AbstractComposedIdentity.extractRequiredTokenBodyParts(
                    prefix,
                    IdentityTokenFormatter.format(prefix,
                            nullPlaceholder
                            + separator + value0
                            + separator + value1),
                    separator,
                    nullPlaceholder,
                    3));
        assertArrayEquals(
                new String[] { value0, null, value1 },
                AbstractComposedIdentity.extractRequiredTokenBodyParts(
                    prefix,
                    IdentityTokenFormatter.format(prefix,
                            value0
                            + separator + nullPlaceholder
                            + separator + value1),
                    separator,
                    nullPlaceholder,
                    3));
        assertArrayEquals(
                new String[] { value0, value1, null },
                AbstractComposedIdentity.extractRequiredTokenBodyParts(
                    prefix,
                    IdentityTokenFormatter.format(prefix,
                            value0
                            + separator + value1
                            + separator + nullPlaceholder),
                    separator,
                    nullPlaceholder,
                    3));
    }

    /**
     * Mock implementation of {@code AbstractComposedIdentity}
     * for testing.
     */
    private static class TestIdentity
    extends AbstractComposedIdentity {

        /** The serial version UID. */
        private static final long serialVersionUID = 1L;

        /** The first identity value. */
        private final String value0;
        /** The second identity value. */
        private final Long value1;
        /** The third identity value. */
        private final BigInteger value2;

        /**
         * Creates a new instance.
         * 
         * @param values The identity values
         */
        public TestIdentity(
                final String value0,
                final Long value1,
                final BigInteger value2) {
            super();
            this.value0 = value0;
            this.value1 = value1;
            this.value2 = value2;
        }

        /**
         * Copy constructor.
         * 
         * @param copy The instance to copy
         */
        public TestIdentity(
                final @NotNull TestIdentity copy) {
            super();
            this.value0 = copy.value0;
            this.value1 = copy.value1;
            this.value2 = copy.value2;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected @NotNull String[] getIdentityTokenBodyParts() {
            return new String[] {
                this.value0,
                this.value1 == null ? null : this.value1.toString(),
                this.value2 == null ? null : this.value2.toString(),
            };
        }

        /**
         * Resolves the specified identity token to a valid {@code TestIdentity}.
         * 
         * @param token The identity token
         * @return The resolved identity
         * @throws NullPointerException If the identity token is {@code null}
         * @throws UnrecognizedIdentityTokenException If the identity token is not
         * a valid identity token, it doesn't start with the expected prefix or
         * the identity token body parts are no valid
         */
        @IdentityTokenResolver
        public static @NotNull TestIdentity fromIdentityToken(
                final @NotNull String token) {
            final String[] parts =  AbstractComposedIdentity.extractRequiredTokenBodyParts(
                    IdentityTokenFormatter.DEFAULT_PREFIX,
                    token,
                    3);
            try {
                return new TestIdentity(
                        parts[0],
                        parts[1] == null ? null : Long.valueOf(parts[1]),
                        parts[2] == null ? null : new BigInteger(parts[2]));
            } catch (final NumberFormatException e) {
                throw new UnrecognizedIdentityTokenException(
                        "Unrecognized identity token: " + token);
            }
        }
    }
}
