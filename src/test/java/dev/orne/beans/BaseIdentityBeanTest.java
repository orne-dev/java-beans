package dev.orne.beans;

/*-
 * #%L
 * Orne Beans
 * %%
 * Copyright (C) 2020 - 2023 Orne Developments
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

import java.time.Duration;
import java.util.HashSet;
import java.util.stream.Stream;

import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import dev.orne.test.rnd.Generators;

/**
 * Unit tests for {@code BaseIdentityBean}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.2, 2023-12
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
        final Object other = null;
        assertNotEquals(bean, other);
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
        assertEquals(bean, bean);
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
        assertNotEquals(bean, other);
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
        assertEquals(identity, other);
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

    /**
     * Test for default {@link IdentityBean} generation.
     * @throws Throwable Should not happen
     */
    @Test
    void testIdentityBeanGenerable()
    throws Throwable {
        assertTrue(Generators.supports(IdentityBean.class));
        assertNull(Generators.nullableDefaultValue(IdentityBean.class));
        IdentityBean result = Generators.defaultValue(IdentityBean.class);
        assertNotNull(result);
        assertEquals(BaseIdentityBean.class, result.getClass());
        assertNull(result.getIdentity());
    }

    /**
     * Test for default {@link WritableIdentityBean} generation.
     * @throws Throwable Should not happen
     */
    @Test
    void testWritableIdentityBeanGenerable()
    throws Throwable {
        assertTrue(Generators.supports(WritableIdentityBean.class));
        assertNull(Generators.nullableDefaultValue(WritableIdentityBean.class));
        WritableIdentityBean result = Generators.defaultValue(WritableIdentityBean.class);
        assertNotNull(result);
        assertEquals(BaseIdentityBean.class, result.getClass());
        assertNull(result.getIdentity());
    }

    /**
     * Test for default {@link BaseIdentityBean} generation.
     * @throws Throwable Should not happen
     */
    @Test
    void testTokenIdentityGenerable()
    throws Throwable {
        assertTrue(Generators.supports(BaseIdentityBean.class));
        assertNull(Generators.nullableDefaultValue(BaseIdentityBean.class));
        BaseIdentityBean result = Generators.defaultValue(BaseIdentityBean.class);
        assertNotNull(result);
        assertEquals(BaseIdentityBean.class, result.getClass());
        assertNull(result.getIdentity());
    }

    /**
     * Test for random {Identity BaseIdentityBean} generation.
     * @throws Throwable Should not happen
     */
    @ParameterizedTest
    @MethodSource("testIdentityRandomGeneration")
    void testIdentityRandomGeneration(
            final Class<? extends IdentityBean> type, 
            final boolean nullable)
    throws Throwable {
        final HashSet<BaseIdentityBean> results = new HashSet<>();
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            boolean nullValues = false;
            boolean nullIndentities = false;
            boolean nonNullIndentities = false;
            while (results.size() < 100 || (nullable && !nullValues) || !nullIndentities || !nonNullIndentities) {
                final IdentityBean result;
                if (nullable) {
                    result = Generators.nullableRandomValue(type);
                } else {
                    result = Generators.randomValue(type);
                }
                if (result == null) {
                    nullValues = true;
                } else {
                    assertEquals(BaseIdentityBean.class, result.getClass());
                    if (result.getIdentity() == null) {
                        nullIndentities = true;
                    } else {
                        nonNullIndentities = true;
                    }
                    results.add((BaseIdentityBean) result);
                }
            }
            assertTrue(!nullable || nullValues);
        });
        assertTrue(results.size() >= 100);
    }

    private static Stream<Arguments> testIdentityRandomGeneration() {
        return Stream.of(
                Arguments.of(IdentityBean.class, false),
                Arguments.of(WritableIdentityBean.class, false),
                Arguments.of(BaseIdentityBean.class, false),
                Arguments.of(IdentityBean.class, true),
                Arguments.of(WritableIdentityBean.class, true),
                Arguments.of(BaseIdentityBean.class, true)
        );
    }
}
