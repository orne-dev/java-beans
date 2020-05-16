package dev.orne.beans;

import static org.junit.jupiter.api.Assertions.*;

import javax.annotation.Nonnull;

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
public class ExceptionsTest {

    /** Message for exception testing. */
    private static final String TEST_MESSAGE = "Test message";
    /** Cause for exception testing. */
    private static final Throwable TEST_CAUSE = new Exception();

    /**
     * Test for {@link UnrecognizedIdentityTokenException}.
     */
    @Test
    public void testUnrecognizedIdentityTokenException() {
        assertEmptyException(new UnrecognizedIdentityTokenException());
        assertMessageException(new UnrecognizedIdentityTokenException(TEST_MESSAGE));
        assertCauseException(new UnrecognizedIdentityTokenException(TEST_CAUSE));
        assertFullException(new UnrecognizedIdentityTokenException(TEST_MESSAGE, TEST_CAUSE));
        assertFullException(new UnrecognizedIdentityTokenException(TEST_MESSAGE, TEST_CAUSE, false, false));
    }

    /**
     * Test for {@link UnresolvableIdentityException}.
     */
    @Test
    public void testUnresolvableIdentityException() {
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
            @Nonnull
            final Exception exception) {
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
            @Nonnull
            final Exception exception) {
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
            @Nonnull
            final Exception exception) {
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
            @Nonnull
            final Exception exception) {
        assertNotNull(exception);
        assertNotNull(exception.getMessage());
        assertEquals(TEST_MESSAGE, exception.getMessage());
        assertNotNull(exception.getCause());
        assertSame(TEST_CAUSE, exception.getCause());
    }
}
