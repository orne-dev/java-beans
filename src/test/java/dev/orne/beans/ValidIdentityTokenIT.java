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
 * Integration tests for {@code ValidIdentityToken}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see ValidIdentityToken
 */
@Tag("it")
public class ValidIdentityTokenIT {

    /** The random values generator. */
    private static final Random RND = new Random();

    /**
     * Test {@link ValidIdentityToken) validations.
     */
    @Test
    public void testNullTokenValidation() {
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
    public void testEmptyTokenValidation() {
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
    public void testInvalidTokenValidation() {
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
    public void testValidTokenValidationNullBody() {
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
    public void testValidTokenValidationEmptyBody() {
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
    public void testValidTokenValidationSimpleBody() {
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
    public void testValidTokenValidation() {
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

    /**
     * Bean with {@code @ValidIdentityToken} for testing.
     */
    protected static class TestContainer {
        @ValidIdentityToken
        public String token;
    }

    /**
     * Bean with {@code @ValidIdentityToken} for testing.
     */
    protected static class TestNonNullContainer {
        @NotNull
        @ValidIdentityToken
        public String token;
    }
}
