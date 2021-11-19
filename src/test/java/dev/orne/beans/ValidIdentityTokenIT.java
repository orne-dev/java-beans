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
import java.util.Random;

import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Integration tests for {@code ValidIdentityToken}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see ValidIdentityToken
 */
@Tag("it")
class ValidIdentityTokenIT {

    /** The random values generator. */
    private static final Random RND = new Random();

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
     * Test {@link ValidIdentityToken) validations.
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
    }

    /**
     * Test {@link ValidIdentityToken) validations.
     */
    @Test
    void testEmptyTokenValidation() {
        final String token = "";
        final TestContainer nullableContainer = new TestContainer();
        nullableContainer.token = token;
        assertFalse(BeanValidationUtils.isValid(nullableContainer));
        final TestNonNullContainer nonnullContainer = new TestNonNullContainer();
        nonnullContainer.token = token;
        assertFalse(BeanValidationUtils.isValid(nonnullContainer));
    }

    /**
     * Test {@link ValidIdentityToken) validations.
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
    }

    /**
     * Test {@link ValidIdentityToken) validations.
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
    }

    /**
     * Test {@link ValidIdentityToken) validations.
     */
    @Test
    void testValidTokenValidationEmptyBody() {
        final String token = "ID";
        final TestContainer nullableContainer = new TestContainer();
        nullableContainer.token = token;
        assertTrue(BeanValidationUtils.isValid(nullableContainer));
        final TestNonNullContainer nonnullContainer = new TestNonNullContainer();
        nonnullContainer.token = token;
        assertTrue(BeanValidationUtils.isValid(nonnullContainer));
    }

    /**
     * Test {@link ValidIdentityToken) validations.
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
    }

    /**
     * Test {@link ValidIdentityToken) validations.
     */
    @Test
    void testValidTokenValidation() {
        final String prefix = "CustomPrefix";
        final byte[] bodyBytes = new byte[RND.nextInt(100) + 1];
        RND.nextBytes(bodyBytes);
        final String body = new String(bodyBytes);
        final String token =
                IdentityTokenFormatter.format(prefix, body);
        final TestContainer nullableContainer = new TestContainer();
        nullableContainer.token = token;
        assertTrue(BeanValidationUtils.isValid(nullableContainer));
        final TestNonNullContainer nonnullContainer = new TestNonNullContainer();
        nonnullContainer.token = token;
        assertTrue(BeanValidationUtils.isValid(nonnullContainer));
    }

    private boolean isValidToken(int index) {
        return (index % 3) != 2;
    }

    /**
     * Test {@link ValidIdentityToken) validations.
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
     * Test {@link ValidIdentityToken) validations.
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
     * Bean with {@code ValidIdentityToken} for testing.
     */
    protected static class TestContainer {
        @ValidIdentityToken
        public String token;
    }

    /**
     * Bean with {@code ValidIdentityToken} for testing.
     */
    protected static class TestNonNullContainer {
        @NotNull
        @ValidIdentityToken
        public String token;
    }
    /**
     * Bean with {@code ValidIdentityToken} for testing.
     */
    protected static class IterableElementContainer {
        public Iterable<@ValidIdentityToken String> tokens;
    }
    /**
     * Bean with {@code ValidIdentityToken} for testing.
     */
    protected static class ArrayElementContainer {
        public String @ValidIdentityToken[] tokens;
    }
}
