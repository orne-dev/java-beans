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

import dev.orne.beans.IdentityResolver.UnresolvableIdentityException;

/**
 * Unit tests for library exceptions.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see UnrecognizedIdentityTokenException
 * @see UnresolvableIdentityException
 */
@Tag("ut")
class ExceptionsTest {

    /** Message for exception testing. */
    private static final String TEST_MESSAGE = "Test message";
    /** Cause for exception testing. */
    private static final Throwable TEST_CAUSE = new Exception();

    /**
     * Test for {@link UnrecognizedIdentityTokenException}.
     */
    @Test
    void testUnrecognizedIdentityTokenException() {
        assertEmptyException(new UnrecognizedIdentityTokenException());
        assertMessageException(new UnrecognizedIdentityTokenException(TEST_MESSAGE));
        assertCauseException(new UnrecognizedIdentityTokenException(TEST_CAUSE));
        assertFullException(new UnrecognizedIdentityTokenException(TEST_MESSAGE, TEST_CAUSE));
    }

    /**
     * Test for {@link UnresolvableIdentityException}.
     */
    @Test
    void testUnresolvableIdentityException() {
        assertEmptyException(new UnresolvableIdentityException());
        assertMessageException(new UnresolvableIdentityException(TEST_MESSAGE));
        assertCauseException(new UnresolvableIdentityException(TEST_CAUSE));
        assertFullException(new UnresolvableIdentityException(TEST_MESSAGE, TEST_CAUSE));
        assertFullException(new UnresolvableIdentityException(TEST_MESSAGE, TEST_CAUSE, false, false));
    }

    /**
     * Asserts that exception has no message and no cause.
     * 
     * @param exception The exception to test
     */
    private void assertEmptyException(
            final @NotNull Exception exception) {
        assertNotNull(exception);
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    /**
     * Asserts that exception has message but no cause.
     * 
     * @param exception The exception to test
     */
    private void assertMessageException(
            final @NotNull Exception exception) {
        assertNotNull(exception);
        assertNotNull(exception.getMessage());
        assertEquals(TEST_MESSAGE, exception.getMessage());
        assertNull(exception.getCause());
    }

    /**
     * Asserts that exception has cause but no message.
     * 
     * @param exception The exception to test
     */
    private void assertCauseException(
            final @NotNull Exception exception) {
        assertNotNull(exception);
        assertNotNull(exception.getMessage());
        assertEquals(TEST_CAUSE.toString(), exception.getMessage());
        assertNotNull(exception.getCause());
        assertSame(TEST_CAUSE, exception.getCause());
    }

    /**
     * Asserts that exception has message and cause.
     * 
     * @param exception The exception to test
     */
    private void assertFullException(
            final @NotNull Exception exception) {
        assertNotNull(exception);
        assertNotNull(exception.getMessage());
        assertEquals(TEST_MESSAGE, exception.getMessage());
        assertNotNull(exception.getCause());
        assertSame(TEST_CAUSE, exception.getCause());
    }
}
