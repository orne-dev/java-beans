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
import static org.mockito.BDDMockito.*;

import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code AbstractIdentity}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see AbstractIdentity
 */
@Tag("ut")
class AbstractIdentityTest {

    /**
     * Creates the identity to be tested.
     * 
     * @return The identity created
     */
    protected @NotNull AbstractIdentity createInstance() {
        return new TestIdentity();
    }

    /**
     * Creates a copy identity to be tested.
     * 
     * @param copy The identity to copy, created with {@link #createInstance()}
     * @return The identity created
     */
    protected @NotNull AbstractIdentity createCopy(
            final @NotNull AbstractIdentity copy) {
        return new TestIdentity();
    }

    /**
     * Test for {@link AbstractIdentity#getIdentityTokenPrefix()}.
     * @throws Throwable Should not happen
     */
    @Test
    void testGetIdentityTokenPrefix()
    throws Throwable {
        final AbstractIdentity identity = createInstance();
        final String result = identity.getIdentityTokenPrefix();
        assertNotNull(result);
        assertTrue(IdentityTokenFormatter.VALID_PREFIX_PREDICATE.test(result));
    }

    /**
     * Test for {@link AbstractIdentity#getIdentityToken()}.
     * @throws Throwable Should not happen
     */
    @Test
    void testGetIdentityToken()
    throws Throwable {
        final AbstractIdentity identity = createInstance();
        final String result = identity.getIdentityToken();
        assertNotNull(result);
        assertTrue(IdentityTokenFormatter.VALID_TOKEN_PREDICATE.test(result));
    }

    /**
     * Test for {@link AbstractIdentity#getIdentityToken()}.
     * @throws Throwable Should not happen
     */
    @Test
    void testGetIdentityTokenCache()
    throws Throwable {
        final AbstractIdentity identity = createInstance();
        final String first = identity.getIdentityToken();
        final String result = identity.getIdentityToken();
        assertNotNull(result);
        assertSame(first, result);
    }

    /**
     * Test for {@link AbstractIdentity#parseIdentityTokenBody(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testParseIdentityTokenBody()
    throws Throwable {
        final AbstractIdentity identity = createInstance();
        final String body = "mock identity token body";
        final String token = IdentityTokenFormatter.format(
                identity.getIdentityTokenPrefix(),
                body);
        final String result = identity.parseIdentityTokenBody(token);
        assertNotNull(result);
        assertEquals(body, result);
    }

    /**
     * Test for {@link AbstractIdentity#hashCode()} and
     * {@link AbstractIdentity#equals(Object)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testEqualsHashCodeNull()
    throws Throwable {
        final AbstractIdentity identity = createInstance();
        assertFalse(identity.equals(null));
    }

    /**
     * Test for {@link AbstractIdentity#hashCode()} and
     * {@link AbstractIdentity#equals(Object)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testEqualsHashCodeSame()
    throws Throwable {
        final AbstractIdentity identity = createInstance();
        assertTrue(identity.equals(identity));
        assertEquals(identity.hashCode(), identity.hashCode());
    }

    /**
     * Test for {@link AbstractIdentity#hashCode()} and
     * {@link AbstractIdentity#equals(Object)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testEqualsHashCodeDiferentClass()
    throws Throwable {
        final AbstractIdentity identity = createInstance();
        final AbstractIdentity other = mock(AbstractIdentity.class); 
        assertFalse(identity.equals(other));
    }

    /**
     * Test for {@link AbstractIdentity#hashCode()} and
     * {@link AbstractIdentity#equals(Object)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testEqualsHashCodeCopy()
    throws Throwable {
        final AbstractIdentity identity = createInstance();
        final AbstractIdentity other = createCopy(identity);
        assertTrue(identity.equals(other));
        assertEquals(identity.hashCode(), other.hashCode());
    }

    /**
     * Test for {@link AbstractIdentity#toString()}.
     * @throws Throwable Should not happen
     */
    @Test
    void testToString()
    throws Throwable {
        final AbstractIdentity identity = createInstance();
        final String identityToken = identity.getIdentityToken();
        final String result = identity.toString();
        assertNotNull(result);
        assertEquals(identityToken, result);
    }

    /**
     * Mock implementation of {@code AbstractIdentity}
     * for testing.
     */
    private static class TestIdentity
    extends AbstractIdentity {

        /** The serial version UID. */
        private static final long serialVersionUID = 1L;

        /**
         * Mock implementation.
         * {@inheritDoc}
         */
        @Override
        protected String getIdentityTokenBody() {
            return null;
        }
    }
}
