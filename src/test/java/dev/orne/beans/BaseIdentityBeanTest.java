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
 * Unit tests for {@code BaseIdentityBean}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see BaseIdentityBean
 */
@Tag("ut")
class BaseIdentityBeanTest {

    /**
     * Creates the bean to be tested.
     * 
     * @return The bean created
     */
    protected @NotNull BaseIdentityBean createInstance() {
        return new BaseIdentityBean();
    }

    /**
     * Creates a copy bean to be tested.
     * 
     * @param copy The bean to copy, created with {@link #createInstance()}
     * @return The bean created
     */
    protected @NotNull BaseIdentityBean createCopy(
            final @NotNull BaseIdentityBean copy) {
        return new BaseIdentityBean(copy);
    }

    /**
     * Test for {@link BaseIdentityBean#setIdentity(Identity)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testSetIdentity()
    throws Throwable {
        final BaseIdentityBean bean = createInstance();
        final Identity identity = mock(Identity.class);
        bean.setIdentity(identity);
        assertNotNull(bean.getIdentity());
        assertSame(identity, bean.getIdentity());
        bean.setIdentity(null);
        assertNull(bean.getIdentity());
    }

    /**
     * Test for {@link BaseIdentityBean#hashCode()} and
     * {@link BaseIdentityBean#equals(Object)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testEqualsHashCodeNull()
    throws Throwable {
        final BaseIdentityBean bean = createInstance();
        assertFalse(bean.equals(null));
    }

    /**
     * Test for {@link BaseIdentityBean#hashCode()} and
     * {@link BaseIdentityBean#equals(Object)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testEqualsHashCodeSame()
    throws Throwable {
        final BaseIdentityBean bean = createInstance();
        assertTrue(bean.equals(bean));
        assertEquals(bean.hashCode(), bean.hashCode());
    }

    /**
     * Test for {@link BaseIdentityBean#hashCode()} and
     * {@link BaseIdentityBean#equals(Object)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testEqualsHashCodeDiferentClass()
    throws Throwable {
        final BaseIdentityBean bean = createInstance();
        final BaseIdentityBean other = mock(BaseIdentityBean.class); 
        assertFalse(bean.equals(other));
    }

    /**
     * Test for {@link BaseIdentityBean#hashCode()} and
     * {@link BaseIdentityBean#equals(Object)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testEqualsHashCodeCopy()
    throws Throwable {
        final BaseIdentityBean identity = createInstance();
        final BaseIdentityBean other = createCopy(identity);
        assertTrue(identity.equals(other));
        assertEquals(identity.hashCode(), other.hashCode());
    }

    /**
     * Test for {@link BaseIdentityBean#toString()}.
     * @throws Throwable Should not happen
     */
    @Test
    void testToString()
    throws Throwable {
        final BaseIdentityBean bean = createInstance();
        final TokenIdentity identity = new TokenIdentity("mockIdentity");
        bean.setIdentity(identity);
        final String result = bean.toString();
        assertNotNull(result);
    }
}
