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
import java.util.Set;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.beans.IdentityBean.RequireIdentity;
import dev.orne.beans.ValidBeanIdentity.ValidBeanIdentityValidator;

/**
 * Unit tests for {@code ValidBeanIdentity}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see ValidBeanIdentity
 */
@Tag("ut")
public class ValidBeanIdentityTest {

    /**
     * Resets original shared validator.
     */
    @AfterAll
    public static void resetSharedValidator() {
        BeanValidationUtils.setValidator(
                Validation.buildDefaultValidatorFactory().getValidator());
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
     * Test {@link ValidBeanIdentityValidator#isValid(Object, ConstraintValidatorContext).
     */
    @Test
    public void testIsValidInstanceNull() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        final ValidBeanIdentityValidator refValidator = createValidator();
        refValidator.initialize(mock(ValidBeanIdentity.class));
        assertTrue(refValidator.isValid(null, context));
        then(validator).shouldHaveNoInteractions();
    }

    /**
     * Test {@link ValidBeanIdentityValidator#isValid(Object, ConstraintValidatorContext).
     */
    @Test
    public void testIsValidInstanceFail() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        final ValidBeanIdentityValidator refValidator = createValidator();
        final TestBean value = new TestBean();
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockFailedValidationResult =
                mock(Set.class);
        willReturn(false).given(mockFailedValidationResult).isEmpty();
        willReturn(mockFailedValidationResult).given(validator).validate(
                value, RequireIdentity.class);
        refValidator.initialize(mock(ValidBeanIdentity.class));
        final boolean result = refValidator.isValid(value, context);
        then(validator).should().validate(
                value, RequireIdentity.class);
        then(validator).shouldHaveNoMoreInteractions();
        assertFalse(result);
    }

    /**
     * Test {@link ValidBeanIdentityValidator#isValid(Object, ConstraintValidatorContext).
     */
    @Test
    public void testIsValidInstanceSuccess() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        final ValidBeanIdentityValidator refValidator = createValidator();
        final TestBean value = new TestBean();
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockSuccessValidationResult =
                mock(Set.class);
        willReturn(true).given(mockSuccessValidationResult).isEmpty();
        willReturn(mockSuccessValidationResult).given(validator).validate(
                value, RequireIdentity.class);
        refValidator.initialize(mock(ValidBeanIdentity.class));
        final boolean result = refValidator.isValid(value, context);
        then(validator).should().validate(
                value, RequireIdentity.class);
        assertTrue(result);
    }

    /**
     * Test {@link ValidBeanIdentityValidator#isValid(Object, ConstraintValidatorContext).
     */
    @Test
    public void testIsValidInstanceIterableFail1() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        final ValidBeanIdentityValidator refValidator = createValidator();
        final TestBean value1 = new TestBean();
        final TestBean value2 = new TestBean();
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockSuccessValidationResult =
                mock(Set.class);
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockFailedValidationResult =
                mock(Set.class);
        willReturn(true).given(mockSuccessValidationResult).isEmpty();
        willReturn(false).given(mockFailedValidationResult).isEmpty();
        willReturn(mockFailedValidationResult).given(validator).validate(
                value1, RequireIdentity.class);
        willReturn(mockSuccessValidationResult).given(validator).validate(
                value2, RequireIdentity.class);
        refValidator.initialize(mock(ValidBeanIdentity.class));
        final boolean result = refValidator.isValid(Arrays.asList(value1, value2), context);
        then(validator).should().validate(
                value1, RequireIdentity.class);
        assertFalse(result);
    }

    /**
     * Test {@link ValidBeanIdentityValidator#isValid(Object, ConstraintValidatorContext).
     */
    @Test
    public void testIsValidInstanceIterableFail2() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        final ValidBeanIdentityValidator refValidator = createValidator();
        final TestBean value1 = new TestBean();
        final TestBean value2 = new TestBean();
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockSuccessValidationResult =
                mock(Set.class);
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockFailedValidationResult =
                mock(Set.class);
        willReturn(true).given(mockSuccessValidationResult).isEmpty();
        willReturn(false).given(mockFailedValidationResult).isEmpty();
        willReturn(mockSuccessValidationResult).given(validator).validate(
                value1, RequireIdentity.class);
        willReturn(mockFailedValidationResult).given(validator).validate(
                value2, RequireIdentity.class);
        refValidator.initialize(mock(ValidBeanIdentity.class));
        final boolean result = refValidator.isValid(Arrays.asList(value1, value2), context);
        then(validator).should().validate(
                value2, RequireIdentity.class);
        assertFalse(result);
    }

    /**
     * Test {@link ValidBeanIdentityValidator#isValid(Object, ConstraintValidatorContext).
     */
    @Test
    public void testIsValidInstanceIterableSuccess() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        final ValidBeanIdentityValidator refValidator = createValidator();
        final TestBean value1 = new TestBean();
        final TestBean value2 = new TestBean();
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockSuccessValidationResult =
                mock(Set.class);
        willReturn(true).given(mockSuccessValidationResult).isEmpty();
        willReturn(mockSuccessValidationResult).given(validator).validate(
                value1, RequireIdentity.class);
        willReturn(mockSuccessValidationResult).given(validator).validate(
                value2, RequireIdentity.class);
        refValidator.initialize(mock(ValidBeanIdentity.class));
        final boolean result = refValidator.isValid(Arrays.asList(value1, value2), context);
        then(validator).should().validate(
                value1, RequireIdentity.class);
        then(validator).should().validate(
                value2, RequireIdentity.class);
        assertTrue(result);
    }

    /**
     * Test {@link ValidBeanIdentityValidator#isValid(Object, ConstraintValidatorContext).
     */
    @Test
    public void testIsValidInstanceArrayFail1() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        final ValidBeanIdentityValidator refValidator = createValidator();
        final TestBean value1 = new TestBean();
        final TestBean value2 = new TestBean();
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockSuccessValidationResult =
                mock(Set.class);
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockFailedValidationResult =
                mock(Set.class);
        willReturn(true).given(mockSuccessValidationResult).isEmpty();
        willReturn(false).given(mockFailedValidationResult).isEmpty();
        willReturn(mockFailedValidationResult).given(validator).validate(
                value1, RequireIdentity.class);
        willReturn(mockSuccessValidationResult).given(validator).validate(
                value2, RequireIdentity.class);
        refValidator.initialize(mock(ValidBeanIdentity.class));
        final boolean result = refValidator.isValid(new TestBean[] { value1, value2 }, context);
        then(validator).should().validate(
                value1, RequireIdentity.class);
        assertFalse(result);
    }

    /**
     * Test {@link ValidBeanIdentityValidator#isValid(Object, ConstraintValidatorContext).
     */
    @Test
    public void testIsValidInstanceArrayFail2() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        final ValidBeanIdentityValidator refValidator = createValidator();
        final TestBean value1 = new TestBean();
        final TestBean value2 = new TestBean();
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockSuccessValidationResult =
                mock(Set.class);
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockFailedValidationResult =
                mock(Set.class);
        willReturn(true).given(mockSuccessValidationResult).isEmpty();
        willReturn(false).given(mockFailedValidationResult).isEmpty();
        willReturn(mockSuccessValidationResult).given(validator).validate(
                value1, RequireIdentity.class);
        willReturn(mockFailedValidationResult).given(validator).validate(
                value2, RequireIdentity.class);
        refValidator.initialize(mock(ValidBeanIdentity.class));
        final boolean result = refValidator.isValid(new TestBean[] { value1, value2 }, context);
        then(validator).should().validate(
                value2, RequireIdentity.class);
        assertFalse(result);
    }

    /**
     * Test {@link ValidBeanIdentityValidator#isValid(Object, ConstraintValidatorContext).
     */
    @Test
    public void testIsValidInstanceArraySuccess() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        final ValidBeanIdentityValidator refValidator = createValidator();
        final TestBean value1 = new TestBean();
        final TestBean value2 = new TestBean();
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockSuccessValidationResult =
                mock(Set.class);
        willReturn(true).given(mockSuccessValidationResult).isEmpty();
        willReturn(mockSuccessValidationResult).given(validator).validate(
                value1, RequireIdentity.class);
        willReturn(mockSuccessValidationResult).given(validator).validate(
                value2, RequireIdentity.class);
        refValidator.initialize(mock(ValidBeanIdentity.class));
        final boolean result = refValidator.isValid(new TestBean[] { value1, value2 }, context);
        then(validator).should().validate(
                value1, RequireIdentity.class);
        then(validator).should().validate(
                value2, RequireIdentity.class);
        assertTrue(result);
    }

    /**
     * Test {@link ValidBeanIdentityValidator#isValid(Object).
     */
    @Test
    public void testIsValidStaticFail() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final ValidBeanIdentityValidator refValidator = createValidator();
        final TestBean value = new TestBean();
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockFailedValidationResult = mock(Set.class);
        willReturn(false).given(mockFailedValidationResult).isEmpty();
        willReturn(mockFailedValidationResult).given(validator).validate(
                value, RequireIdentity.class);
        refValidator.initialize(mock(ValidBeanIdentity.class));
        final boolean result = ValidBeanIdentityValidator.isValid(value);
        then(validator).should().validate(
                value, RequireIdentity.class);
        then(validator).shouldHaveNoMoreInteractions();
        assertFalse(result);
    }

    /**
     * Test {@link ValidBeanIdentityValidator#isValid(Object).
     */
    @Test
    public void testIsValidStaticSuccess() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final ValidBeanIdentityValidator refValidator = createValidator();
        final TestBean value = new TestBean();
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockSuccessValidationResult =
                mock(Set.class);
        willReturn(true).given(mockSuccessValidationResult).isEmpty();
        willReturn(mockSuccessValidationResult).given(validator).validate(
                value, RequireIdentity.class);
        refValidator.initialize(mock(ValidBeanIdentity.class));
        final boolean result = ValidBeanIdentityValidator.isValid(value);
        then(validator).should().validate(
                value, RequireIdentity.class);
        assertTrue(result);
    }

    /**
     * Bean implementing {@code @BeanIdentity} for testing.
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
