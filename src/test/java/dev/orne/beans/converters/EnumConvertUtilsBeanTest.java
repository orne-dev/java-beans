package dev.orne.beans.converters;

/*-
 * #%L
 * Orne Beans
 * %%
 * Copyright (C) 2022 Orne Developments
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

import org.apache.commons.beanutils.ConvertUtilsBean;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code EnumConvertUtilsBean}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-10
 * @since 0.4
 * @see EnumConvertUtilsBean
 */
@Tag("ut")
class EnumConvertUtilsBeanTest {

    /**
     * Test for {@link ConvertUtilsBean#lookup(Class)}.
     */
    @Test
    void testDefaultLookup() {
        final ConvertUtilsBean bean = new ConvertUtilsBean();
        assertNull(bean.lookup(TestEnum.class));
        assertNull(bean.lookup(NotEnum.class));
        assertNotNull(bean.lookup(Long.class));
        bean.register(EnumConverter.GENERIC, Enum.class);
        assertNull(bean.lookup(TestEnum.class));
        assertNull(bean.lookup(NotEnum.class));
        assertNotNull(bean.lookup(Long.class));
    }

    /**
     * Test for {@link EnumConvertUtilsBean#lookup(Class)}.
     */
    @Test
    void testFixedLookup() {
        final EnumConvertUtilsBean bean = new EnumConvertUtilsBean();
        assertNull(bean.lookup(TestEnum.class));
        assertNull(bean.lookup(NotEnum.class));
        assertNotNull(bean.lookup(Long.class));
        bean.register(EnumConverter.GENERIC, Enum.class);
        assertSame(EnumConverter.GENERIC, bean.lookup(TestEnum.class));
        assertNull(bean.lookup(NotEnum.class));
        assertNotNull(bean.lookup(Long.class));
    }

    /**
     * Enumeration for {@code EnumConvertUtilsBean} tests.
     */
    public static enum TestEnum {
        VALUE_A,
        VALUE_B,
        VALUE_C;
    }

    /**
     * Non enumeration type for {@code EnumConvertUtilsBean} tests.
     */
    public static interface NotEnum {}
}
