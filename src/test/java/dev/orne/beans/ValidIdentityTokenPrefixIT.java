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

import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Integration tests for {@code ValidIdentityTokenPrefix}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see ValidIdentityTokenPrefix
 */
@Tag("it")
class ValidIdentityTokenPrefixIT {

    /**
     * Test {@link ValidIdentityTokenPrefix) validations.
     */
    @Test
    void testNullPrefixValidation() {
        final String prefix = null;
        final TestContainer nullableContainer = new TestContainer();
        nullableContainer.prefix = prefix;
        assertTrue(BeanValidationUtils.isValid(nullableContainer));
        final TestNonNullContainer nonnullContainer = new TestNonNullContainer();
        nonnullContainer.prefix = prefix;
        assertFalse(BeanValidationUtils.isValid(nonnullContainer));
    }

    /**
     * Test {@link ValidIdentityTokenPrefix) validations.
     */
    @Test
    void testEmptyPrefixValidation() {
        final String prefix = "";
        final TestContainer nullableContainer = new TestContainer();
        nullableContainer.prefix = prefix;
        assertFalse(BeanValidationUtils.isValid(nullableContainer));
        final TestNonNullContainer nonnullContainer = new TestNonNullContainer();
        nonnullContainer.prefix = prefix;
        assertFalse(BeanValidationUtils.isValid(nonnullContainer));
    }

    /**
     * Test {@link ValidIdentityTokenPrefix) validations.
     */
    @Test
    void testInvalidPrefixValidation() {
        final String prefix = "invalid prefix";
        final TestContainer nullableContainer = new TestContainer();
        nullableContainer.prefix = prefix;
        assertFalse(BeanValidationUtils.isValid(nullableContainer));
        final TestNonNullContainer nonnullContainer = new TestNonNullContainer();
        nonnullContainer.prefix = prefix;
        assertFalse(BeanValidationUtils.isValid(nonnullContainer));
    }

    /**
     * Test {@link ValidIdentityTokenPrefix) validations.
     */
    @Test
    void testValidPrefixValidationDefault() {
        final String prefix = IdentityTokenFormatter.DEFAULT_PREFIX;
        final TestContainer nullableContainer = new TestContainer();
        nullableContainer.prefix = prefix;
        assertTrue(BeanValidationUtils.isValid(nullableContainer));
        final TestNonNullContainer nonnullContainer = new TestNonNullContainer();
        nonnullContainer.prefix = prefix;
        assertTrue(BeanValidationUtils.isValid(nonnullContainer));
    }

    /**
     * Test {@link ValidIdentityTokenPrefix) validations.
     */
    @Test
    void testValidPrefixValidationCustom() {
        final String prefix = "CustomPrefix";
        final TestContainer nullableContainer = new TestContainer();
        nullableContainer.prefix = prefix;
        assertTrue(BeanValidationUtils.isValid(nullableContainer));
        final TestNonNullContainer nonnullContainer = new TestNonNullContainer();
        nonnullContainer.prefix = prefix;
        assertTrue(BeanValidationUtils.isValid(nonnullContainer));
    }

    /**
     * Bean with {@code @ValidIdentityTokenPrefix} for testing.
     */
    protected static class TestContainer {
        @ValidIdentityTokenPrefix
        public String prefix;
    }

    /**
     * Bean with {@code @ValidIdentityTokenPrefix} for testing.
     */
    protected static class TestNonNullContainer {
        @NotNull
        @ValidIdentityTokenPrefix
        public String prefix;
    }
}
