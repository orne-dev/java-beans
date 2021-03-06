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
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.beans.ValidBeanReference.ValidBeanReferenceValidator;

/**
 * Unit tests for {@code ValidBeanReference}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see ValidBeanReference
 */
@Tag("ut")
class ValidBeanReferenceTest {

    /**
     * Resets original shared validator.
     */
    @AfterAll
    public static void resetSharedValidator() {
        BeanValidationUtils.setValidator(
                Validation.buildDefaultValidatorFactory().getValidator());
    }

    /**
     * Creates an instance {@code ValidBeanReferenceValidator} to test.
     * 
     * @return The created {@code ValidBeanReferenceValidator}
     */
    protected @NotNull ValidBeanReferenceValidator createValidator() {
        return new ValidBeanReferenceValidator();
    }

    /**
     * Test {@link ValidBeanReferenceValidator#isValid(Object, ConstraintValidatorContext)}.
     */
    @Test
    void testIsValidInstanceNull() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        final ValidBeanReferenceValidator refValidator = createValidator();
        refValidator.initialize(mock(ValidBeanReference.class));
        assertTrue(refValidator.isValid(null, context));
        then(validator).shouldHaveNoInteractions();
    }

    /**
     * Test {@link ValidBeanReferenceValidator#isValid(Object, ConstraintValidatorContext)}.
     */
    @Test
    void testIsValidInstanceFailAll() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        final ValidBeanReferenceValidator refValidator = createValidator();
        final TestBean value = new TestBean();
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockFailedValidationResult =
                mock(Set.class);
        willReturn(false).given(mockFailedValidationResult).isEmpty();
        willReturn(mockFailedValidationResult).given(validator).validate(
                value, ValidationGroup1.class);
        willReturn(mockFailedValidationResult).given(validator).validate(
                value, ValidationGroup2.class, ValidationGroup3.class);
        willReturn(mockFailedValidationResult).given(validator).validate(
                value, ValidationGroup4.class);
        refValidator.initialize(mock(ValidBeanReference.class));
        final boolean result = refValidator.isValid(value, context);
        then(validator).should().validate(
                value, ValidationGroup1.class);
        then(validator).should().validate(
                value, ValidationGroup2.class, ValidationGroup3.class);
        then(validator).should().validate(
                value, ValidationGroup4.class);
        then(validator).shouldHaveNoMoreInteractions();
        assertFalse(result);
    }

    /**
     * Test {@link ValidBeanReferenceValidator#isValid(Object, ConstraintValidatorContext)}.
     */
    @Test
    void testIsValidInstanceSuccess1() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        final ValidBeanReferenceValidator refValidator = createValidator();
        final TestBean value = new TestBean();
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockSuccessValidationResult =
                mock(Set.class);
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockFailedValidationResult =
                mock(Set.class);
        willReturn(true).given(mockSuccessValidationResult).isEmpty();
        willReturn(false).given(mockFailedValidationResult).isEmpty();
        willReturn(mockSuccessValidationResult).given(validator).validate(
                value, ValidationGroup1.class);
        willReturn(mockFailedValidationResult).given(validator).validate(
                value, ValidationGroup2.class, ValidationGroup3.class);
        willReturn(mockFailedValidationResult).given(validator).validate(
                value, ValidationGroup4.class);
        refValidator.initialize(mock(ValidBeanReference.class));
        final boolean result = refValidator.isValid(value, context);
        then(validator).should().validate(
                value, ValidationGroup1.class);
        assertTrue(result);
    }

    /**
     * Test {@link ValidBeanReferenceValidator#isValid(Object, ConstraintValidatorContext)}.
     */
    @Test
    void testIsValidInstanceSuccess2() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        final ValidBeanReferenceValidator refValidator = createValidator();
        final TestBean value = new TestBean();
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockSuccessValidationResult =
                mock(Set.class);
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockFailedValidationResult =
                mock(Set.class);
        willReturn(true).given(mockSuccessValidationResult).isEmpty();
        willReturn(false).given(mockFailedValidationResult).isEmpty();
        willReturn(mockFailedValidationResult).given(validator).validate(
                value, ValidationGroup1.class);
        willReturn(mockSuccessValidationResult).given(validator).validate(
                value, ValidationGroup2.class, ValidationGroup3.class);
        willReturn(mockFailedValidationResult).given(validator).validate(
                value, ValidationGroup4.class);
        refValidator.initialize(mock(ValidBeanReference.class));
        final boolean result = refValidator.isValid(value, context);
        then(validator).should().validate(
                value, ValidationGroup2.class, ValidationGroup3.class);
        assertTrue(result);
    }

    /**
     * Test {@link ValidBeanReferenceValidator#isValid(Object, ConstraintValidatorContext)}.
     */
    @Test
    void testIsValidInstanceSuccess3() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        final ValidBeanReferenceValidator refValidator = createValidator();
        final TestBean value = new TestBean();
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockSuccessValidationResult =
                mock(Set.class);
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockFailedValidationResult =
                mock(Set.class);
        willReturn(true).given(mockSuccessValidationResult).isEmpty();
        willReturn(false).given(mockFailedValidationResult).isEmpty();
        willReturn(mockFailedValidationResult).given(validator).validate(
                value, ValidationGroup1.class);
        willReturn(mockFailedValidationResult).given(validator).validate(
                value, ValidationGroup2.class, ValidationGroup3.class);
        willReturn(mockSuccessValidationResult).given(validator).validate(
                value, ValidationGroup4.class);
        refValidator.initialize(mock(ValidBeanReference.class));
        final boolean result = refValidator.isValid(value, context);
        then(validator).should().validate(
                value, ValidationGroup4.class);
        assertTrue(result);
    }

    /**
     * Test {@link ValidBeanReferenceValidator#isValid(Object, ConstraintValidatorContext)}.
     */
    @Test
    void testIsValidInstanceIterableFail1() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        final ValidBeanReferenceValidator refValidator = createValidator();
        final TestSimpleBean value1 = new TestSimpleBean();
        final TestSimpleBean value2 = new TestSimpleBean();
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockSuccessValidationResult =
                mock(Set.class);
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockFailedValidationResult =
                mock(Set.class);
        willReturn(true).given(mockSuccessValidationResult).isEmpty();
        willReturn(false).given(mockFailedValidationResult).isEmpty();
        willReturn(mockFailedValidationResult).given(validator).validate(
                value1, ValidationGroup1.class);
        willReturn(mockSuccessValidationResult).given(validator).validate(
                value2, ValidationGroup1.class);
        refValidator.initialize(mock(ValidBeanReference.class));
        final boolean result = refValidator.isValid(Arrays.asList(value1, value2), context);
        then(validator).should().validate(
                value1, ValidationGroup1.class);
        assertFalse(result);
    }

    /**
     * Test {@link ValidBeanReferenceValidator#isValid(Object, ConstraintValidatorContext)}.
     */
    @Test
    void testIsValidInstanceIterableFail2() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        final ValidBeanReferenceValidator refValidator = createValidator();
        final TestSimpleBean value1 = new TestSimpleBean();
        final TestSimpleBean value2 = new TestSimpleBean();
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockSuccessValidationResult =
                mock(Set.class);
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockFailedValidationResult =
                mock(Set.class);
        willReturn(true).given(mockSuccessValidationResult).isEmpty();
        willReturn(false).given(mockFailedValidationResult).isEmpty();
        willReturn(mockSuccessValidationResult).given(validator).validate(
                value1, ValidationGroup1.class);
        willReturn(mockFailedValidationResult).given(validator).validate(
                value2, ValidationGroup1.class);
        refValidator.initialize(mock(ValidBeanReference.class));
        final boolean result = refValidator.isValid(Arrays.asList(value1, value2), context);
        then(validator).should().validate(
                value2, ValidationGroup1.class);
        assertFalse(result);
    }

    /**
     * Test {@link ValidBeanReferenceValidator#isValid(Object, ConstraintValidatorContext)}.
     */
    @Test
    void testIsValidInstanceIterableSuccess() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        final ValidBeanReferenceValidator refValidator = createValidator();
        final TestSimpleBean value1 = new TestSimpleBean();
        final TestSimpleBean value2 = new TestSimpleBean();
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockSuccessValidationResult =
                mock(Set.class);
        willReturn(true).given(mockSuccessValidationResult).isEmpty();
        willReturn(mockSuccessValidationResult).given(validator).validate(
                value1, ValidationGroup1.class);
        willReturn(mockSuccessValidationResult).given(validator).validate(
                value2, ValidationGroup1.class);
        refValidator.initialize(mock(ValidBeanReference.class));
        final boolean result = refValidator.isValid(Arrays.asList(value1, value2), context);
        then(validator).should().validate(
                value1, ValidationGroup1.class);
        then(validator).should().validate(
                value2, ValidationGroup1.class);
        assertTrue(result);
    }

    /**
     * Test {@link ValidBeanReferenceValidator#isValid(Object, ConstraintValidatorContext)}.
     */
    @Test
    void testIsValidInstanceArrayFail1() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        final ValidBeanReferenceValidator refValidator = createValidator();
        final TestSimpleBean value1 = new TestSimpleBean();
        final TestSimpleBean value2 = new TestSimpleBean();
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockSuccessValidationResult =
                mock(Set.class);
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockFailedValidationResult =
                mock(Set.class);
        willReturn(true).given(mockSuccessValidationResult).isEmpty();
        willReturn(false).given(mockFailedValidationResult).isEmpty();
        willReturn(mockFailedValidationResult).given(validator).validate(
                value1, ValidationGroup1.class);
        willReturn(mockSuccessValidationResult).given(validator).validate(
                value2, ValidationGroup1.class);
        refValidator.initialize(mock(ValidBeanReference.class));
        final boolean result = refValidator.isValid(new TestSimpleBean[] { value1, value2 }, context);
        then(validator).should().validate(
                value1, ValidationGroup1.class);
        assertFalse(result);
    }

    /**
     * Test {@link ValidBeanReferenceValidator#isValid(Object, ConstraintValidatorContext)}.
     */
    @Test
    void testIsValidInstanceArrayFail2() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        final ValidBeanReferenceValidator refValidator = createValidator();
        final TestSimpleBean value1 = new TestSimpleBean();
        final TestSimpleBean value2 = new TestSimpleBean();
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockSuccessValidationResult =
                mock(Set.class);
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockFailedValidationResult =
                mock(Set.class);
        willReturn(true).given(mockSuccessValidationResult).isEmpty();
        willReturn(false).given(mockFailedValidationResult).isEmpty();
        willReturn(mockSuccessValidationResult).given(validator).validate(
                value1, ValidationGroup1.class);
        willReturn(mockFailedValidationResult).given(validator).validate(
                value2, ValidationGroup1.class);
        refValidator.initialize(mock(ValidBeanReference.class));
        final boolean result = refValidator.isValid(new TestSimpleBean[] { value1, value2 }, context);
        then(validator).should().validate(
                value2, ValidationGroup1.class);
        assertFalse(result);
    }

    /**
     * Test {@link ValidBeanReferenceValidator#isValid(Object, ConstraintValidatorContext)}.
     */
    @Test
    void testIsValidInstanceArraySuccess() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        final ValidBeanReferenceValidator refValidator = createValidator();
        final TestSimpleBean value1 = new TestSimpleBean();
        final TestSimpleBean value2 = new TestSimpleBean();
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockSuccessValidationResult =
                mock(Set.class);
        willReturn(true).given(mockSuccessValidationResult).isEmpty();
        willReturn(mockSuccessValidationResult).given(validator).validate(
                value1, ValidationGroup1.class);
        willReturn(mockSuccessValidationResult).given(validator).validate(
                value2, ValidationGroup1.class);
        refValidator.initialize(mock(ValidBeanReference.class));
        final boolean result = refValidator.isValid(new TestSimpleBean[] { value1, value2 }, context);
        then(validator).should().validate(
                value1, ValidationGroup1.class);
        then(validator).should().validate(
                value2, ValidationGroup1.class);
        assertTrue(result);
    }

    /**
     * Test {@link ValidBeanReferenceValidator#isValid(Object)}.
     */
    @Test
    void testIsValidStaticFailAll() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final TestBean value = new TestBean();
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockFailedValidationResult = mock(Set.class);
        willReturn(false).given(mockFailedValidationResult).isEmpty();
        willReturn(mockFailedValidationResult).given(validator).validate(
                value, ValidationGroup1.class);
        willReturn(mockFailedValidationResult).given(validator).validate(
                value, ValidationGroup2.class, ValidationGroup3.class);
        willReturn(mockFailedValidationResult).given(validator).validate(
                value, ValidationGroup4.class);
        final boolean result = ValidBeanReferenceValidator.isValid(value);
        then(validator).should().validate(
                value, ValidationGroup1.class);
        then(validator).should().validate(
                value, ValidationGroup2.class, ValidationGroup3.class);
        then(validator).should().validate(
                value, ValidationGroup4.class);
        then(validator).shouldHaveNoMoreInteractions();
        assertFalse(result);
    }

    /**
     * Test {@link ValidBeanReferenceValidator#isValid(Object)}.
     */
    @Test
    void testIsValidStaticSucess1() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final TestBean value = new TestBean();
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockSuccessValidationResult =
                mock(Set.class);
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockFailedValidationResult =
                mock(Set.class);
        willReturn(true).given(mockSuccessValidationResult).isEmpty();
        willReturn(false).given(mockFailedValidationResult).isEmpty();
        willReturn(mockSuccessValidationResult).given(validator).validate(
                value, ValidationGroup1.class);
        willReturn(mockFailedValidationResult).given(validator).validate(
                value, ValidationGroup2.class, ValidationGroup3.class);
        willReturn(mockFailedValidationResult).given(validator).validate(
                value, ValidationGroup4.class);
        final boolean result = ValidBeanReferenceValidator.isValid(value);
        then(validator).should().validate(
                value, ValidationGroup1.class);
        assertTrue(result);
    }

    /**
     * Test {@link ValidBeanReferenceValidator#isValid(Object)}.
     */
    @Test
    void testIsValidStaticSucess2() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final TestBean value = new TestBean();
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockSuccessValidationResult =
                mock(Set.class);
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockFailedValidationResult =
                mock(Set.class);
        willReturn(true).given(mockSuccessValidationResult).isEmpty();
        willReturn(false).given(mockFailedValidationResult).isEmpty();
        willReturn(mockFailedValidationResult).given(validator).validate(
                value, ValidationGroup1.class);
        willReturn(mockSuccessValidationResult).given(validator).validate(
                value, ValidationGroup2.class, ValidationGroup3.class);
        willReturn(mockFailedValidationResult).given(validator).validate(
                value, ValidationGroup4.class);
        final boolean result = ValidBeanReferenceValidator.isValid(value);
        then(validator).should().validate(
                value, ValidationGroup2.class, ValidationGroup3.class);
        assertTrue(result);
    }

    /**
     * Test {@link ValidBeanReferenceValidator#isValid(Object)}.
     */
    @Test
    void testIsValidStaticSucess3() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final TestBean value = new TestBean();
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockSuccessValidationResult =
                mock(Set.class);
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockFailedValidationResult =
                mock(Set.class);
        willReturn(true).given(mockSuccessValidationResult).isEmpty();
        willReturn(false).given(mockFailedValidationResult).isEmpty();
        willReturn(mockFailedValidationResult).given(validator).validate(
                value, ValidationGroup1.class);
        willReturn(mockFailedValidationResult).given(validator).validate(
                value, ValidationGroup2.class, ValidationGroup3.class);
        willReturn(mockSuccessValidationResult).given(validator).validate(
                value, ValidationGroup4.class);
        final boolean result = ValidBeanReferenceValidator.isValid(value);
        then(validator).should().validate(
                value, ValidationGroup4.class);
        assertTrue(result);
    }

    /**
     * Test {@link ValidBeanReferenceValidator#isValid(Object, BeanAnnotationFinder)}.
     */
    @Test
    void testIsValidStaticFinderFailAll() {
        @SuppressWarnings("unchecked")
        final BeanAnnotationFinder<BeanReference, ?> finder =
                mock(BeanAnnotationFinder.class);
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final TestBean value = new TestBean();
        final BeanReference reference1 = mock(BeanReference.class);
        final BeanReference reference2 = mock(BeanReference.class);
        final Set<BeanReference> mockAnnotations = new HashSet<>();
        mockAnnotations.add(reference1);
        mockAnnotations.add(reference2);
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockFailedValidationResult = mock(Set.class);
        willReturn(new Class<?>[] { ValidationGroup1.class }).given(reference1).value();
        willReturn(new Class<?>[] { ValidationGroup2.class }).given(reference2).value();
        willReturn(mockAnnotations).given(finder).find(TestBean.class);
        willReturn(false).given(mockFailedValidationResult).isEmpty();
        willReturn(mockFailedValidationResult).given(validator).validate(
                value, ValidationGroup1.class);
        willReturn(mockFailedValidationResult).given(validator).validate(
                value, ValidationGroup2.class);
        final boolean result = ValidBeanReferenceValidator.isValid(value, finder);
        then(finder).should().find(TestBean.class);
        then(validator).should().validate(
                value, ValidationGroup1.class);
        then(validator).should().validate(
                value, ValidationGroup2.class);
        then(validator).shouldHaveNoMoreInteractions();
        assertFalse(result);
    }

    /**
     * Test {@link ValidBeanReferenceValidator#isValid(Object, BeanAnnotationFinder)}.
     */
    @Test
    void testIsValidStaticFinderSucess1() {
        @SuppressWarnings("unchecked")
        final BeanAnnotationFinder<BeanReference, ?> finder =
                mock(BeanAnnotationFinder.class);
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final TestBean value = new TestBean();
        final BeanReference reference1 = mock(BeanReference.class);
        final BeanReference reference2 = mock(BeanReference.class);
        final Set<BeanReference> mockAnnotations = new HashSet<>();
        mockAnnotations.add(reference1);
        mockAnnotations.add(reference2);
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockSuccessValidationResult =
                mock(Set.class);
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockFailedValidationResult =
                mock(Set.class);
        willReturn(new Class<?>[] { ValidationGroup1.class }).given(reference1).value();
        willReturn(new Class<?>[] { ValidationGroup2.class }).given(reference2).value();
        willReturn(mockAnnotations).given(finder).find(TestBean.class);
        willReturn(true).given(mockSuccessValidationResult).isEmpty();
        willReturn(false).given(mockFailedValidationResult).isEmpty();
        willReturn(mockSuccessValidationResult).given(validator).validate(
                value, ValidationGroup1.class);
        willReturn(mockFailedValidationResult).given(validator).validate(
                value, ValidationGroup2.class);
        final boolean result = ValidBeanReferenceValidator.isValid(value, finder);
        then(finder).should().find(TestBean.class);
        then(validator).should().validate(
                value, ValidationGroup1.class);
        assertTrue(result);
    }

    /**
     * Test {@link ValidBeanReferenceValidator#isValid(Object, BeanAnnotationFinder)}.
     */
    @Test
    void testIsValidStaticFinderSucess2() {
        @SuppressWarnings("unchecked")
        final BeanAnnotationFinder<BeanReference, ?> finder =
                mock(BeanAnnotationFinder.class);
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final TestBean value = new TestBean();
        final BeanReference reference1 = mock(BeanReference.class);
        final BeanReference reference2 = mock(BeanReference.class);
        final Set<BeanReference> mockAnnotations = new HashSet<>();
        mockAnnotations.add(reference1);
        mockAnnotations.add(reference2);
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockSuccessValidationResult =
                mock(Set.class);
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockFailedValidationResult =
                mock(Set.class);
        willReturn(new Class<?>[] { ValidationGroup1.class }).given(reference1).value();
        willReturn(new Class<?>[] { ValidationGroup2.class }).given(reference2).value();
        willReturn(mockAnnotations).given(finder).find(TestBean.class);
        willReturn(true).given(mockSuccessValidationResult).isEmpty();
        willReturn(false).given(mockFailedValidationResult).isEmpty();
        willReturn(mockFailedValidationResult).given(validator).validate(
                value, ValidationGroup1.class);
        willReturn(mockSuccessValidationResult).given(validator).validate(
                value, ValidationGroup2.class);
        final boolean result = ValidBeanReferenceValidator.isValid(value, finder);
        then(finder).should().find(TestBean.class);
        then(validator).should().validate(
                value, ValidationGroup2.class);
        assertTrue(result);
    }

    /**
     * Test {@link BeanValidationUtils#isVaidBeanReference(Object)}.
     */
    @Test
    void testUtilsIsValidBeanReferenceFail() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final TestSimpleBean value = new TestSimpleBean();
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockFailedValidationResult = mock(Set.class);
        willReturn(false).given(mockFailedValidationResult).isEmpty();
        willReturn(mockFailedValidationResult).given(validator).validate(
                value, ValidationGroup1.class);
        final boolean result = BeanValidationUtils.isValidBeanReference(value);
        then(validator).should().validate(
                value, ValidationGroup1.class);
        then(validator).shouldHaveNoMoreInteractions();
        assertFalse(result);
    }

    /**
     * Test {@link BeanValidationUtils#isVaidBeanReference(Object)}.
     */
    @Test
    void testUtilsIsValidBeanReferenceSuccess() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final TestSimpleBean value = new TestSimpleBean();
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockSuccessValidationResult =
                mock(Set.class);
        willReturn(true).given(mockSuccessValidationResult).isEmpty();
        willReturn(mockSuccessValidationResult).given(validator).validate(
                value, ValidationGroup1.class);
        final boolean result = BeanValidationUtils.isValidBeanReference(value);
        then(validator).should().validate(
                value, ValidationGroup1.class);
        then(validator).shouldHaveNoMoreInteractions();
        assertTrue(result);
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
        // No extra methods
    }

    /**
     * Bean with {@code @BeanReference} for testing.
     */
    @BeanReference(ValidationGroup1.class)
    protected static class TestSimpleBean {
        // No extra methods
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
