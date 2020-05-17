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

import java.util.Arrays;

import javax.validation.constraints.AssertTrue;
import javax.validation.groups.Default;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.beans.ValidBeanReference.ValidBeanReferenceValidator;

/**
 * Integration tests for {@code ValidBeanReference}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see ValidBeanReference
 */
@Tag("it")
public class ValidBeanReferenceIT {

    private static final String ERR_MSG = "Failed expectation for bean #%d";
    private static final String ERR_MANY_MSG = "Failed expectation for beans #%d and #%d";

    private static TestBean[] testBeans;

    @BeforeAll
    public static void createTestBeans() {
        testBeans = new TestBean[16];
        for (int i = 0; i < testBeans.length; i++) {
            final TestBean bean = new TestBean();
            bean.value1 = (i & 1) != 0;
            bean.value2 = (i & 2) != 0;
            bean.value3 = (i & 4) != 0;
            bean.value4 = (i & 8) != 0;
            testBeans[i] = bean;
        }
    }

    /**
     * Creates an instance {@code ValidBeanReferenceValidator} to test.
     * 
     * @return The created {@code ValidBeanReferenceValidator}
     */
    protected ValidBeanReferenceValidator createValidator() {
        return new ValidBeanReferenceValidator();
    }

    /**
     * Test {@link ValidBeanReference) validations.
     */
    @Test
    public void testValidateBean() {
        for (int i = 0; i < testBeans.length; i++) {
            final TestBean bean = testBeans[i];
            if ((i & 1) == 0 || (i & 2) == 0) {
                assertFalse(BeanValidationUtils.isValid(bean),
                        String.format(ERR_MSG, i));
            } else {
                assertTrue(BeanValidationUtils.isValid(bean),
                        String.format(ERR_MSG, i));
            }
            if ((i & 1) == 0) {
                assertFalse(BeanValidationUtils.isValid(bean, ValidationGroup1.class),
                        String.format(ERR_MSG, i));
            } else {
                assertTrue(BeanValidationUtils.isValid(bean, ValidationGroup1.class),
                        String.format(ERR_MSG, i));
            }
            if ((i & 2) == 0) {
                assertFalse(BeanValidationUtils.isValid(bean, ValidationGroup2.class),
                        String.format(ERR_MSG, i));
            } else {
                assertTrue(BeanValidationUtils.isValid(bean, ValidationGroup2.class),
                        String.format(ERR_MSG, i));
            }
            if ((i & 4) == 0) {
                assertFalse(BeanValidationUtils.isValid(bean, ValidationGroup3.class),
                        String.format(ERR_MSG, i));
            } else {
                assertTrue(BeanValidationUtils.isValid(bean, ValidationGroup3.class),
                        String.format(ERR_MSG, i));
            }
            if ((i & 8) == 0) {
                assertFalse(BeanValidationUtils.isValid(bean, ValidationGroup4.class),
                        String.format(ERR_MSG, i));
            } else {
                assertTrue(BeanValidationUtils.isValid(bean, ValidationGroup4.class),
                        String.format(ERR_MSG, i));
            }
        }
    }

    private boolean isValidReference(int index) {
        return (index & 1) == 1 || (index & 6) == 6 || (index & 8) == 8;
    }

    /**
     * Test {@link ValidBeanReference) validations.
     */
    @Test
    public void testValidateSimpleContainer() {
        final SimpleContainer container = new SimpleContainer();
        for (int i = 0; i < testBeans.length; i++) {
            container.bean = testBeans[i];
            if (isValidReference(i)) {
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
    public void testValidateIterableContainer() {
        final IterableContainer container = new IterableContainer();
        for (int i = 0; i < testBeans.length; i++) {
            for (int j = 0; j < testBeans.length; j++) {
                container.beans = Arrays.asList(testBeans[i], testBeans[j]);
                if (isValidReference(i) && isValidReference(j)) {
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
    public void testValidateArrayContainer() {
        final ArrayContainer container = new ArrayContainer();
        for (int i = 0; i < testBeans.length; i++) {
            for (int j = 0; j < testBeans.length; j++) {
                container.beans = new TestBean[] { testBeans[i], testBeans[j] };
                if (isValidReference(i) && isValidReference(j)) {
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
     * Bean with {@code @BeanReference} for testing.
     */
    protected static class SimpleContainer {
        @ValidBeanReference
        public TestBean bean;
    }
    /**
     * Bean with {@code @BeanReference} for testing.
     */
    protected static class IterableContainer {
        @ValidBeanReference
        public Iterable<TestBean> beans;
    }
    /**
     * Bean with {@code @BeanReference} for testing.
     */
    protected static class ArrayContainer {
        @ValidBeanReference
        public TestBean[] beans;
    }
    /**
     * Bean with {@code @BeanReference} for testing.
     */
    @BeanReference(ValidationGroup1.class)
    @BeanReference.List({
        @BeanReference({ ValidationGroup2.class, ValidationGroup3.class }),
        @BeanReference(ValidationGroup4.class)
    })
    protected static class TestBean {
        @AssertTrue(groups = { ValidationGroup1.class, Default.class })
        public boolean value1;
        @AssertTrue(groups = { ValidationGroup2.class, Default.class })
        public boolean value2;
        @AssertTrue(groups = ValidationGroup3.class)
        public boolean value3;
        @AssertTrue(groups = ValidationGroup4.class)
        public boolean value4;
    }
    /**
     * Validation group for testing.
     */
    protected static interface ValidationGroup1 {
        // No extra methods
    }
    /**
     * Validation group for testing.
     */
    protected static interface ValidationGroup2 {
        // No extra methods
    }
    /**
     * Validation group for testing.
     */
    protected static interface ValidationGroup3 {
        // No extra methods
    }
    /**
     * Validation group for testing.
     */
    protected static interface ValidationGroup4 {
        // No extra methods
    }
}
