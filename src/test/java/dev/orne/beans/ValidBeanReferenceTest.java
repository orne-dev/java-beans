package dev.orne.beans;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

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
public class ValidBeanReferenceTest {

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
    protected ValidBeanReferenceValidator createValidator() {
        return new ValidBeanReferenceValidator();
    }

    /**
     * Test {@link ValidBeanReferenceValidator#isValid(Object, ConstraintValidatorContext).
     */
    @Test
    public void testIsValidInstanceNull() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        final ValidBeanReferenceValidator refValidator = createValidator();
        refValidator.initialize(mock(ValidBeanReference.class));
        assertTrue(refValidator.isValid(null, context));
        then(validator).shouldHaveNoInteractions();
    }

    /**
     * Test {@link ValidBeanReferenceValidator#isValid(Object, ConstraintValidatorContext).
     */
    @Test
    public void testIsValidInstanceFailAll() {
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
     * Test {@link ValidBeanReferenceValidator#isValid(Object, ConstraintValidatorContext).
     */
    @Test
    public void testIsValidInstanceSuccess1() {
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
     * Test {@link ValidBeanReferenceValidator#isValid(Object, ConstraintValidatorContext).
     */
    @Test
    public void testIsValidInstanceSuccess2() {
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
     * Test {@link ValidBeanReferenceValidator#isValid(Object, ConstraintValidatorContext).
     */
    @Test
    public void testIsValidInstanceSuccess3() {
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
     * Test {@link ValidBeanReferenceValidator#isValid(Object, ConstraintValidatorContext).
     */
    @Test
    public void testIsValidInstanceIterableFail1() {
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
     * Test {@link ValidBeanReferenceValidator#isValid(Object, ConstraintValidatorContext).
     */
    @Test
    public void testIsValidInstanceIterableFail2() {
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
     * Test {@link ValidBeanReferenceValidator#isValid(Object, ConstraintValidatorContext).
     */
    @Test
    public void testIsValidInstanceIterableSuccess() {
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
     * Test {@link ValidBeanReferenceValidator#isValid(Object, ConstraintValidatorContext).
     */
    @Test
    public void testIsValidInstanceArrayFail1() {
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
     * Test {@link ValidBeanReferenceValidator#isValid(Object, ConstraintValidatorContext).
     */
    @Test
    public void testIsValidInstanceArrayFail2() {
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
     * Test {@link ValidBeanReferenceValidator#isValid(Object, ConstraintValidatorContext).
     */
    @Test
    public void testIsValidInstanceArraySuccess() {
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
     * Test {@link ValidBeanReferenceValidator#isValid(Object).
     */
    @Test
    public void testIsValidStaticFailAll() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final ValidBeanReferenceValidator refValidator = createValidator();
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
        refValidator.initialize(mock(ValidBeanReference.class));
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
     * Test {@link ValidBeanReferenceValidator#isValid(Object).
     */
    @Test
    public void testIsValidStaticFailSucess1() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
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
        final boolean result = ValidBeanReferenceValidator.isValid(value);
        then(validator).should().validate(
                value, ValidationGroup1.class);
        assertTrue(result);
    }

    /**
     * Test {@link ValidBeanReferenceValidator#isValid(Object).
     */
    @Test
    public void testIsValidStaticFailSucess2() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
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
        final boolean result = ValidBeanReferenceValidator.isValid(value);
        then(validator).should().validate(
                value, ValidationGroup2.class, ValidationGroup3.class);
        assertTrue(result);
    }

    /**
     * Test {@link ValidBeanReferenceValidator#isValid(Object).
     */
    @Test
    public void testIsValidStaticFailSucess3() {
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
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
        final boolean result = ValidBeanReferenceValidator.isValid(value);
        then(validator).should().validate(
                value, ValidationGroup4.class);
        assertTrue(result);
    }

    /**
     * Test {@link ValidBeanReferenceValidator#isValid(Object, BeanAnnotationFinder).
     */
    @Test
    public void testIsValidStaticFinderFailAll() {
        @SuppressWarnings("unchecked")
        final BeanAnnotationFinder<BeanReference, ?> finder =
                mock(BeanAnnotationFinder.class);
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final ValidBeanReferenceValidator refValidator = createValidator();
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
        refValidator.initialize(mock(ValidBeanReference.class));
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
     * Test {@link ValidBeanReferenceValidator#isValid(Object, BeanAnnotationFinder).
     */
    @Test
    public void testIsValidStaticFinderSucess1() {
        @SuppressWarnings("unchecked")
        final BeanAnnotationFinder<BeanReference, ?> finder =
                mock(BeanAnnotationFinder.class);
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final ValidBeanReferenceValidator refValidator = createValidator();
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
        refValidator.initialize(mock(ValidBeanReference.class));
        final boolean result = ValidBeanReferenceValidator.isValid(value, finder);
        then(finder).should().find(TestBean.class);
        then(validator).should().validate(
                value, ValidationGroup1.class);
        assertTrue(result);
    }

    /**
     * Test {@link ValidBeanReferenceValidator#isValid(Object, BeanAnnotationFinder).
     */
    @Test
    public void testIsValidStaticFinderSucess2() {
        @SuppressWarnings("unchecked")
        final BeanAnnotationFinder<BeanReference, ?> finder =
                mock(BeanAnnotationFinder.class);
        final Validator validator = mock(Validator.class);
        BeanValidationUtils.setValidator(validator);
        final ValidBeanReferenceValidator refValidator = createValidator();
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
        refValidator.initialize(mock(ValidBeanReference.class));
        final boolean result = ValidBeanReferenceValidator.isValid(value, finder);
        then(finder).should().find(TestBean.class);
        then(validator).should().validate(
                value, ValidationGroup2.class);
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
