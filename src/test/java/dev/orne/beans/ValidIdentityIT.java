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

import java.util.Arrays;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Integration tests for {@code ValidIdentity}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-10
 * @since 0.4
 * @see ValidIdentity
 */
@Tag("it")
class ValidIdentityIT {

    private static final String ERR_MANY_MSG = "Failed expectation for tokens #%d and #%d";

    private static String[] testTokens;

    @BeforeAll
    public static void createTestBeans() {
        testTokens = new String[2];
        for (int i = 0; i < testTokens.length; i++) {
            String token = null;
            if (i % 3 == 1) {
                token = "IDsomeBody";
            } else if (i % 3 == 2) {
                token = "invalid token";
            }
            testTokens[i] = token;
        }
    }

    /**
     * Test {@link ValidIdentity) validations.
     */
    @Test
    void testNullTokenValidation() {
        final String token = null;
        final TestContainer nullableContainer = new TestContainer();
        nullableContainer.token = token;
        assertTrue(BeanValidationUtils.isValid(nullableContainer));
        final TestNonNullContainer nonnullContainer = new TestNonNullContainer();
        nonnullContainer.token = token;
        assertFalse(BeanValidationUtils.isValid(nonnullContainer));
        final TestIdentityContainer nullableIdContainer = new TestIdentityContainer();
        nullableIdContainer.identity = null;
        assertTrue(BeanValidationUtils.isValid(nullableIdContainer));
        final TestNonNullIdentityContainer nonnullIdContainer = new TestNonNullIdentityContainer();
        nonnullIdContainer.identity = null;
        assertFalse(BeanValidationUtils.isValid(nonnullIdContainer));
    }

    /**
     * Test {@link ValidIdentity) validations.
     */
    @Test
    void testInvalidTokenValidation() {
        final String token = "invalid token";
        final TestContainer nullableContainer = new TestContainer();
        nullableContainer.token = token;
        assertFalse(BeanValidationUtils.isValid(nullableContainer));
        final TestNonNullContainer nonnullContainer = new TestNonNullContainer();
        nonnullContainer.token = token;
        assertFalse(BeanValidationUtils.isValid(nonnullContainer));
        final TestIdentityContainer nullableIdContainer = new TestIdentityContainer();
        nullableIdContainer.identity = new TokenIdentity(token);
        assertFalse(BeanValidationUtils.isValid(nullableIdContainer));
        final TestNonNullIdentityContainer nonnullIdContainer = new TestNonNullIdentityContainer();
        nonnullIdContainer.identity = new TokenIdentity(token);
        assertFalse(BeanValidationUtils.isValid(nonnullIdContainer));
    }

    /**
     * Test {@link ValidIdentity) validations.
     */
    @Test
    void testValidTokenValidationNullBody() {
        final String token = "ID" + IdentityTokenFormatter.NULL_TOKEN;
        final TestContainer nullableContainer = new TestContainer();
        nullableContainer.token = token;
        assertTrue(BeanValidationUtils.isValid(nullableContainer));
        final TestNonNullContainer nonnullContainer = new TestNonNullContainer();
        nonnullContainer.token = token;
        assertTrue(BeanValidationUtils.isValid(nonnullContainer));
        final TestIdentityContainer nullableIdContainer = new TestIdentityContainer();
        nullableIdContainer.identity = new TokenIdentity(token);
        assertTrue(BeanValidationUtils.isValid(nullableIdContainer));
        final TestNonNullIdentityContainer nonnullIdContainer = new TestNonNullIdentityContainer();
        nonnullIdContainer.identity = new TokenIdentity(token);
        assertTrue(BeanValidationUtils.isValid(nonnullIdContainer));
    }

    /**
     * Test {@link ValidIdentity) validations.
     */
    @Test
    void testValidTokenValidationSimpleBody() {
        final String token = "IDsomeBody";
        final TestContainer nullableContainer = new TestContainer();
        nullableContainer.token = token;
        assertTrue(BeanValidationUtils.isValid(nullableContainer));
        final TestNonNullContainer nonnullContainer = new TestNonNullContainer();
        nonnullContainer.token = token;
        assertTrue(BeanValidationUtils.isValid(nonnullContainer));
        final TestIdentityContainer nullableIdContainer = new TestIdentityContainer();
        nullableIdContainer.identity = new TokenIdentity(token);
        assertTrue(BeanValidationUtils.isValid(nullableIdContainer));
        final TestNonNullIdentityContainer nonnullIdContainer = new TestNonNullIdentityContainer();
        nonnullIdContainer.identity = new TokenIdentity(token);
        assertTrue(BeanValidationUtils.isValid(nonnullIdContainer));
    }

    private boolean isValidToken(int index) {
        return (index % 3) != 2;
    }

    /**
     * Test {@link ValidIdentity) validations.
     */
    @Test
    void testValidateIterableContainer() {
        final IterableElementContainer container = new IterableElementContainer();
        for (int i = 0; i < testTokens.length; i++) {
            for (int j = 0; j < testTokens.length; j++) {
                container.tokens = Arrays.asList(testTokens[i], testTokens[j]);
                if (isValidToken(i) && isValidToken(j)) {
                    assertTrue(BeanValidationUtils.isValid(container),
                            String.format(ERR_MANY_MSG, i, j));
                } else {
                    assertFalse(BeanValidationUtils.isValid(container),
                            String.format(ERR_MANY_MSG, i, j));
                }
            }
        }
    }

    /**
     * Test {@link ValidIdentity) validations.
     */
    @Test
    void testValidateIterableIdentityContainer() {
        final IterableElementIdentityContainer container = new IterableElementIdentityContainer();
        for (int i = 0; i < testTokens.length; i++) {
            for (int j = 0; j < testTokens.length; j++) {
                container.identities = Arrays.asList(
                        asIdentity(testTokens[i]),
                        asIdentity(testTokens[j]));
                if (isValidToken(i) && isValidToken(j)) {
                    assertTrue(BeanValidationUtils.isValid(container),
                            String.format(ERR_MANY_MSG, i, j));
                } else {
                    assertFalse(BeanValidationUtils.isValid(container),
                            String.format(ERR_MANY_MSG, i, j));
                }
            }
        }
    }

    /**
     * Test {@link ValidIdentity) validations.
     */
    @Test
    void testValidateArrayContainer() {
        final ArrayElementContainer container = new ArrayElementContainer();
        for (int i = 0; i < testTokens.length; i++) {
            for (int j = 0; j < testTokens.length; j++) {
                container.tokens = new String[] { testTokens[i], testTokens[j] };
                if (isValidToken(i) && isValidToken(j)) {
                    assertTrue(BeanValidationUtils.isValid(container),
                            String.format(ERR_MANY_MSG, i, j));
                } else {
                    assertFalse(BeanValidationUtils.isValid(container),
                            String.format(ERR_MANY_MSG, i, j));
                }
            }
        }
    }

    /**
     * Test {@link ValidIdentity) validations.
     */
    @Test
    void testValidateArrayIdentityContainer() {
        final ArrayElementIdentityContainer container = new ArrayElementIdentityContainer();
        for (int i = 0; i < testTokens.length; i++) {
            for (int j = 0; j < testTokens.length; j++) {
                container.identities = new Identity[] {
                        asIdentity(testTokens[i]),
                        asIdentity(testTokens[j])
                };
                if (isValidToken(i) && isValidToken(j)) {
                    assertTrue(BeanValidationUtils.isValid(container),
                            String.format(ERR_MANY_MSG, i, j));
                } else {
                    assertFalse(BeanValidationUtils.isValid(container),
                            String.format(ERR_MANY_MSG, i, j));
                }
            }
        }
    }

    private Identity asIdentity(String token) {
        if (token == null) {
            return null;
        } else {
            return new TokenIdentity(token);
        }
    }

    protected static class TestIdentity
    implements Identity {
        private static final long serialVersionUID = 1L;
        private String token;
        public TestIdentity(
                final String token)
        throws UnrecognizedIdentityTokenException {
            if (token == null || token.contains("invalid")) {
                throw new UnrecognizedIdentityTokenException(token);
            }
            this.token = token;
        }
        @Override
        public @NotBlank String getIdentityToken() {
            return token;
        }
    }

    /**
     * Bean with {@code ValidIdentity} for testing.
     */
    protected static class TestContainer {
        @ValidIdentity(TestIdentity.class)
        public String token;
    }
    /**
     * Bean with {@code ValidIdentity} for testing.
     */
    protected static class TestNonNullContainer {
        @NotNull
        @ValidIdentity(TestIdentity.class)
        public String token;
    }
    /**
     * Bean with {@code ValidIdentity} for testing.
     */
    protected static class IterableElementContainer {
        public Iterable<@ValidIdentity(TestIdentity.class) String> tokens;
    }
    /**
     * Bean with {@code ValidIdentity} for testing.
     */
    protected static class ArrayElementContainer {
        public String @ValidIdentity(TestIdentity.class)[] tokens;
    }
    /**
     * Bean with {@code ValidIdentity} for testing.
     */
    protected static class TestIdentityContainer {
        @ValidIdentity(TestIdentity.class)
        public Identity identity;
    }
    /**
     * Bean with {@code ValidIdentity} for testing.
     */
    protected static class TestNonNullIdentityContainer {
        @NotNull
        @ValidIdentity(TestIdentity.class)
        public Identity identity;
    }
    /**
     * Bean with {@code ValidIdentity} for testing.
     */
    protected static class IterableElementIdentityContainer {
        public Iterable<@ValidIdentity(TestIdentity.class) Identity> identities;
    }
    /**
     * Bean with {@code ValidIdentity} for testing.
     */
    protected static class ArrayElementIdentityContainer {
        public Identity @ValidIdentity(TestIdentity.class)[] identities;
    }
}
