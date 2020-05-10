package dev.orne.beans;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code BeanValidationUtils}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see BeanValidationUtils
 */
@Tag("ut")
public class BeanValidationUtilsTest {

    /**
     * Resets original shared validator.
     */
    @AfterAll
    public static void resetSharedValidator() {
        BeanValidationUtils.setValidator(
                Validation.buildDefaultValidatorFactory().getValidator());
    }

    /**
     * Test {@link BeanValidationUtils#setValidator(Validator)}.
     */
    @Test
    public void testSetValidator() {
        final Validator mockValidator = mock(Validator.class);
        final Validator defaultValidator = BeanValidationUtils.getValidator();
        BeanValidationUtils.setValidator(mockValidator);
        assertSame(mockValidator, BeanValidationUtils.getValidator());
        BeanValidationUtils.setValidator(defaultValidator);
        assertSame(defaultValidator, BeanValidationUtils.getValidator());
    }

    /**
     * Test {@link BeanValidationUtils#validate(Object, Class...)}.
     */
    @Test
    public void testValidate() {
        final Validator mockValidator = mock(Validator.class);
        BeanValidationUtils.setValidator(mockValidator);
        final Object mockObject = mock(Object.class);
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockResult = mock(Set.class);
        willReturn(mockResult).given(mockValidator).validate(mockObject);
        final Set<ConstraintViolation<Object>> result = BeanValidationUtils.validate(mockObject);
        assertNotNull(result);
        assertSame(mockResult, result);
        then(mockValidator).should().validate(mockObject);
    }

    /**
     * Test {@link BeanValidationUtils#validate(Object, Class...)}.
     */
    @Test
    public void testValidateGroups() {
        final Validator mockValidator = mock(Validator.class);
        BeanValidationUtils.setValidator(mockValidator);
        final Object mockObject = mock(Object.class);
        final Class<?> mockGroup1 = Default.class;
        final Class<?> mockGroup2 = Object.class;
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockResult = mock(Set.class);
        willReturn(mockResult).given(mockValidator).validate(mockObject, mockGroup1, mockGroup2);
        final Set<ConstraintViolation<Object>> result =
                BeanValidationUtils.validate(mockObject, mockGroup1, mockGroup2);
        assertNotNull(result);
        assertSame(mockResult, result);
        then(mockValidator).should().validate(mockObject, mockGroup1, mockGroup2);
    }

    /**
     * Test {@link BeanValidationUtils#isValid(Object, Class...)}.
     */
    @Test
    public void testIsValid() {
        final Validator mockValidator = mock(Validator.class);
        BeanValidationUtils.setValidator(mockValidator);
        final Object mockObject = mock(Object.class);
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockResult = mock(Set.class);
        willReturn(mockResult).given(mockValidator).validate(mockObject);
        willReturn(true).given(mockResult).isEmpty();
        final boolean result = BeanValidationUtils.isValid(mockObject);
        assertTrue(result);
        then(mockValidator).should().validate(mockObject);
        then(mockResult).should().isEmpty();
    }

    /**
     * Test {@link BeanValidationUtils#isValid(Object, Class...)}.
     */
    @Test
    public void testIsValidFalse() {
        final Validator mockValidator = mock(Validator.class);
        BeanValidationUtils.setValidator(mockValidator);
        final Object mockObject = mock(Object.class);
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockResult = mock(Set.class);
        willReturn(mockResult).given(mockValidator).validate(mockObject);
        willReturn(false).given(mockResult).isEmpty();
        final boolean result = BeanValidationUtils.isValid(mockObject);
        assertFalse(result);
        then(mockValidator).should().validate(mockObject);
        then(mockResult).should().isEmpty();
    }

    /**
     * Test {@link BeanValidationUtils#isValid(Object, Class...)}.
     */
    @Test
    public void testIsValidGroups() {
        final Validator mockValidator = mock(Validator.class);
        BeanValidationUtils.setValidator(mockValidator);
        final Object mockObject = mock(Object.class);
        final Class<?> mockGroup1 = Default.class;
        final Class<?> mockGroup2 = Object.class;
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockResult = mock(Set.class);
        willReturn(mockResult).given(mockValidator).validate(mockObject, mockGroup1, mockGroup2);
        willReturn(true).given(mockResult).isEmpty();
        final boolean result = BeanValidationUtils.isValid(mockObject, mockGroup1, mockGroup2);
        assertTrue(result);
        then(mockValidator).should().validate(mockObject, mockGroup1, mockGroup2);
        then(mockResult).should().isEmpty();
    }

    /**
     * Test {@link BeanValidationUtils#isValid(Object, Class...)}.
     */
    @Test
    public void testIsValidGroupsFalse() {
        final Validator mockValidator = mock(Validator.class);
        BeanValidationUtils.setValidator(mockValidator);
        final Object mockObject = mock(Object.class);
        final Class<?> mockGroup1 = Default.class;
        final Class<?> mockGroup2 = Object.class;
        @SuppressWarnings("unchecked")
        final Set<ConstraintViolation<Object>> mockResult = mock(Set.class);
        willReturn(mockResult).given(mockValidator).validate(mockObject, mockGroup1, mockGroup2);
        willReturn(false).given(mockResult).isEmpty();
        final boolean result = BeanValidationUtils.isValid(mockObject, mockGroup1, mockGroup2);
        assertFalse(result);
        then(mockValidator).should().validate(mockObject, mockGroup1, mockGroup2);
        then(mockResult).should().isEmpty();
    }
}
