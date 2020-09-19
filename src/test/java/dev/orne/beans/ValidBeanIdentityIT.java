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

import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.beans.ValidBeanIdentity.ValidBeanIdentityValidator;

/**
 * Integration tests for {@code ValidBeanIdentity}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see ValidBeanIdentity
 */
@Tag("it")
class ValidBeanIdentityIT {

    private static final String ERR_MSG = "Failed expectation for bean #%d";
    private static final String ERR_MANY_MSG = "Failed expectation for beans #%d and #%d";

    private static TestBean[] testBeans;

    @BeforeAll
    public static void createTestBeans() {
        testBeans = new TestBean[2];
        for (int i = 0; i < testBeans.length; i++) {
            final TestBean bean = new TestBean();
            if (i % 3 == 1) {
                final Identity identity = mock(Identity.class);
                bean.setIdentity(identity);
            } else if (i % 3 == 2) {
                bean.setIdentity(new TokenIdentity("validMockIdentityToken"));
            }
            testBeans[i] = bean;
        }
    }

    /**
     * Creates an instance {@code ValidBeanIdentityValidator} to test.
     * 
     * @return The created {@code ValidBeanIdentityValidator}
     */
    protected ValidBeanIdentityValidator createValidator() {
        return new ValidBeanIdentityValidator();
    }

    /**
     * Test {@link ValidBeanReference) validations.
     */
    @Test
    void testValidateBean() {
        for (int i = 0; i < testBeans.length; i++) {
            final TestBean bean = testBeans[i];
            if (i % 3 == 2) {
                assertTrue(BeanValidationUtils.isValid(bean, IdentityBean.RequireIdentity.class),
                        String.format(ERR_MSG, i));
            } else {
                assertFalse(BeanValidationUtils.isValid(bean, IdentityBean.RequireIdentity.class),
                        String.format(ERR_MSG, i));
            }
        }
    }

    private boolean isValidIdentity(int index) {
        return (index % 3) == 2;
    }

    /**
     * Test {@link ValidBeanReference) validations.
     */
    @Test
    void testValidateSimpleContainer() {
        final SimpleContainer container = new SimpleContainer();
        for (int i = 0; i < testBeans.length; i++) {
            container.bean = testBeans[i];
            if (isValidIdentity(i)) {
                assertTrue(BeanValidationUtils.isValid(container),
                        String.format(ERR_MSG, i));
            } else {
                assertFalse(BeanValidationUtils.isValid(container),
                        String.format(ERR_MSG, i));
            }
        }
    }

    /**
     * Test {@link ValidBeanReference) validations.
     */
    @Test
    void testValidateIterableContainer() {
        final IterableContainer container = new IterableContainer();
        for (int i = 0; i < testBeans.length; i++) {
            for (int j = 0; j < testBeans.length; j++) {
                container.beans = Arrays.asList(testBeans[i], testBeans[j]);
                if (isValidIdentity(i) && isValidIdentity(j)) {
                    assertTrue(BeanValidationUtils.isValid(container),
                            String.format(ERR_MANY_MSG, i, j));
                } else {
                    assertFalse(BeanValidationUtils.isValid(container),
                            String.format(ERR_MANY_MSG, i, j));
                }
            }
        }
    }

    /**
     * Test {@link ValidBeanReference) validations.
     */
    @Test
    void testValidateArrayContainer() {
        final ArrayContainer container = new ArrayContainer();
        for (int i = 0; i < testBeans.length; i++) {
            for (int j = 0; j < testBeans.length; j++) {
                container.beans = new TestBean[] { testBeans[i], testBeans[j] };
                if (isValidIdentity(i) && isValidIdentity(j)) {
                    assertTrue(BeanValidationUtils.isValid(container),
                            String.format(ERR_MANY_MSG, i, j));
                } else {
                    assertFalse(BeanValidationUtils.isValid(container),
                            String.format(ERR_MANY_MSG, i, j));
                }
            }
        }
    }

    /**
     * Bean with {@code @ValidBeanIdentity} for testing.
     */
    protected static class SimpleContainer {
        @ValidBeanIdentity
        public TestBean bean;
    }
    /**
     * Bean with {@code @ValidBeanIdentity} for testing.
     */
    protected static class IterableContainer {
        @ValidBeanIdentity
        public Iterable<TestBean> beans;
    }
    /**
     * Bean with {@code @ValidBeanIdentity} for testing.
     */
    protected static class ArrayContainer {
        @ValidBeanIdentity
        public TestBean[] beans;
    }

    /**
     * Bean implementing {@code @IdentityBean} for testing.
     */
    protected static class TestBean
    implements IdentityBean {
        private Identity identity;
        /**
         * {@inheritDoc}
         */
        @Override
        public Identity getIdentity() {
            return this.identity;
        }
        public void setIdentity(final Identity identity) {
            this.identity = identity;
        }
    }
}
