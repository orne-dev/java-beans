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

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code IdentityToken}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see IdentityToken
 */
@Tag("ut")
class IdentityTokenTest {

    /**
     * Test {@link TokenIdentity#TokenIdentity(String)}.
     */
    @Test
    void testConstructor() {
        assertThrows(NullPointerException.class, () -> {
            new TokenIdentity(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new TokenIdentity("");
        });
        final String identityToken = "mock identity token";
        final TokenIdentity identity = new TokenIdentity(identityToken);
        assertEquals(identityToken, identity.getIdentityToken());
    }

    /**
     * Test {@link TokenIdentity#equals(Object)} and {@link TokenIdentity#hashCode()}.
     */
    @Test
    void testEqualsHash() {
        final TokenIdentity identity = new TokenIdentity("mock identity token");
        final Object nullValue = null;
        final TokenIdentity sameInstance = identity;
        final Object wrongType = "mock";
        final TokenIdentity equal = new TokenIdentity("mock identity token");
        final TokenIdentity notEqual = new TokenIdentity("different identity token");
        assertNotEquals(identity, nullValue);
        assertEquals(identity, sameInstance);
        assertEquals(identity.hashCode(), sameInstance.hashCode());
        assertNotEquals(identity, wrongType);
        assertEquals(identity, equal);
        assertEquals(identity.hashCode(), equal.hashCode());
        assertNotEquals(identity, notEqual);
    }

    /**
     * Test {@link TokenIdentity#toString()}.
     */
    @Test
    void testToString() {
        final String identityToken = "mock identity token";
        final TokenIdentity identity = new TokenIdentity(identityToken);
        assertEquals(identityToken, identity.toString());
    }

    /**
     * Test {@link TokenIdentity#fromToken(String)}.
     */
    @Test
    void testFromToken() {
        assertNull(TokenIdentity.fromToken(null));
        assertNull(TokenIdentity.fromToken(""));
        final String identityToken = "mock identity token";
        final TokenIdentity identity = TokenIdentity.fromToken(identityToken);
        assertEquals(identityToken, identity.getIdentityToken());
    }

    /**
     * Test {@link TokenIdentity.IdentityXmlAdapter#unmarshal(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testTokenIdentityXmlAdapterUnmarshall()
    throws Throwable {
        final TokenIdentity.IdentityXmlAdapter adapter = new TokenIdentity.IdentityXmlAdapter();
        assertNull(adapter.unmarshal(null));
        assertNull(adapter.unmarshal(""));
        final TokenIdentity result = adapter.unmarshal("mock identity");
        assertNotNull(result);
        assertEquals("mock identity", result.getIdentityToken());
    }

    /**
     * Test {@link TokenIdentity.IdentityXmlAdapter#marshal(Identity)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testTokenIdentityXmlAdapterMarshall()
    throws Throwable {
        final String identityToken = "mock identity token";
        final Identity identity = mock(Identity.class);
        given(identity.getIdentityToken()).willReturn(identityToken);
        final Identity nullTokenIdentity = mock(Identity.class);
        given(nullTokenIdentity.getIdentityToken()).willReturn(null);
        final Identity emptyIdentity = mock(Identity.class);
        given(emptyIdentity.getIdentityToken()).willReturn("");
        final TokenIdentity tokenIdentity = new TokenIdentity(identityToken);
        final TokenIdentity.IdentityXmlAdapter adapter = new TokenIdentity.IdentityXmlAdapter();
        assertNull(adapter.marshal(null));
        assertNull(adapter.marshal(nullTokenIdentity));
        assertNull(adapter.marshal(emptyIdentity));
        assertEquals(identityToken, adapter.marshal(identity));
        assertEquals(identityToken, adapter.marshal(tokenIdentity));
    }
}
