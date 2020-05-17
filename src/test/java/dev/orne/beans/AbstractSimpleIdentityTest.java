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

import java.io.Serializable;

import javax.annotation.Nullable;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code AbstractSimpleIdentity}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see AbstractSimpleIdentity
 */
@Tag("ut")
public class AbstractSimpleIdentityTest
extends AbstractIdentityTest {

    /**
     * Test for {@link AbstractSimpleIdentity#AbstractSimpleIdentity(Serializable)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testConstructorNullValue()
    throws Throwable {
        final AbstractSimpleIdentity<?> identity = new TestHttpServiceOperation(null);
        assertNull(identity.getValue());
    }

    /**
     * Test for {@link AbstractSimpleIdentity#AbstractSimpleIdentity(Serializable)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testConstructor()
    throws Throwable {
        final Serializable value = mock(Serializable.class);
        final AbstractSimpleIdentity<?> identity = new TestHttpServiceOperation(value);
        assertNotNull(value);
        assertSame(value, identity.getValue());
    }

    /**
     * Creates the identity to be tested.
     * 
     * @return The identity created
     */
    protected AbstractSimpleIdentity<?> createInstance() {
        return createInstanceWithNonNullValue();
    }

    /**
     * Creates the identity to be tested with null value.
     * 
     * @return The identity created
     */
    protected AbstractSimpleIdentity<?> createInstanceWithNullValue() {
        return new TestHttpServiceOperation(null);
    }

    /**
     * Creates the identity to be tested with null value.
     * 
     * @return The identity created
     */
    protected AbstractSimpleIdentity<?> createInstanceWithNonNullValue() {
        return new TestHttpServiceOperation("Some value");
    }

    /**
     * Test for {@link AbstractSimpleIdentity#getIdentityTokenBody()}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testGetIdentityTokenValueNull()
    throws Throwable {
        final AbstractSimpleIdentity<?> identity = createInstanceWithNullValue();
        assertNull(identity.getIdentityTokenBody());
    }

    /**
     * Test for {@link AbstractSimpleIdentity#getIdentityTokenBody()}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testGetIdentityTokenValueNonNull()
    throws Throwable {
        final AbstractSimpleIdentity<?> identity = createInstanceWithNonNullValue();
        assertNotNull(identity.getIdentityTokenBody());
    }

    /**
     * Mock implementation of {@code AbstractSimpleIdentity}
     * for testing.
     */
    private static class TestHttpServiceOperation
    extends AbstractSimpleIdentity<Serializable> {

        /** The serial version UID. */
        private static final long serialVersionUID = 1L;

        /**
         * Creates a new instance.
         * 
         * @param value The identity value
         */
        public TestHttpServiceOperation(
                @Nullable
                final Serializable value) {
            super(value);
        }
    }
}
