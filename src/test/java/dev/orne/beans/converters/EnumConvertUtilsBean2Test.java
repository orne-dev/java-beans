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

import org.apache.commons.beanutils.ConvertUtilsBean2;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code EnumConvertUtilsBean2}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-10
 * @since 0.4
 * @see EnumConvertUtilsBean2
 */
@Tag("ut")
class EnumConvertUtilsBean2Test {

    /**
     * Test for {@link ConvertUtilsBean2#lookup(Class)}.
     */
    @Test
    void testDefaultLookup() {
        final ConvertUtilsBean2 bean = new ConvertUtilsBean2();
        assertNull(bean.lookup(TestEnum.class));
        assertNull(bean.lookup(NotEnum.class));
        assertNotNull(bean.lookup(Long.class));
        bean.register(EnumConverter.GENERIC, Enum.class);
        assertNull(bean.lookup(TestEnum.class));
        assertNull(bean.lookup(NotEnum.class));
        assertNotNull(bean.lookup(Long.class));
    }

    /**
     * Test for {@link EnumConvertUtilsBean2#lookup(Class)}.
     */
    @Test
    void testFixedLookup() {
        final EnumConvertUtilsBean2 bean = new EnumConvertUtilsBean2();
        assertNull(bean.lookup(TestEnum.class));
        assertNull(bean.lookup(NotEnum.class));
        assertNotNull(bean.lookup(Long.class));
        bean.register(EnumConverter.GENERIC, Enum.class);
        assertSame(EnumConverter.GENERIC, bean.lookup(TestEnum.class));
        assertNull(bean.lookup(NotEnum.class));
        assertNotNull(bean.lookup(Long.class));
    }

    /**
     * Enumeration for {@code EnumConvertUtilsBean2} tests.
     */
    public static enum TestEnum {
        VALUE_A,
        VALUE_B,
        VALUE_C;
    }

    /**
     * Non enumeration type for {@code EnumConvertUtilsBean2} tests.
     */
    public static interface NotEnum {}
}
