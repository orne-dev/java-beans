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
public class AbstractIdentityTest {

    /**
     * Creates the identity to be tested.
     * 
     * @return The identity created
     */
    protected AbstractIdentity createInstance() {
        return new TestHttpServiceOperation();
    }

    /**
     * Test for {@link AbstractIdentity#getIdentityTokenPrefix()}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testGetIdentityTokenPrefix()
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
    public void testGetIdentityToken()
    throws Throwable {
        final AbstractIdentity identity = createInstance();
        final String result = identity.getIdentityToken();
        assertNotNull(result);
        assertTrue(IdentityTokenFormatter.VALID_TOKEN_PREDICATE.test(result));
    }

    /**
     * Test for {@link AbstractIdentity#parseIdentityTokenBody(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testParseIdentityTokenBody()
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
     * Mock implementation of {@code AbstractIdentity}
     * for testing.
     */
    private static class TestHttpServiceOperation
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
