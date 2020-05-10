package dev.orne.beans;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;

import org.apache.commons.lang3.Validate;

/**
 * Utility class for bean validations.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 */
public final class BeanValidationUtils {

    /** The shared {@code Validator} used to validate beans. */
    private static Validator validator =
            Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * Private constructor: Utility class.
     */
    private BeanValidationUtils() {
        // No instances allowed
    }

    /**
     * Returns the shared {@code Validator} used to validate beans.
     * 
     * @return The shared {@code Validator} used to validate beans
     */
    public static Validator getValidator() {
        return BeanValidationUtils.validator;
    }

    /**
     * Sets the shared {@code Validator} used to validate beans.
     * 
     * @param validator The shared {@code Validator} used to validate beans
     */
    public static void setValidator(
            @Nonnull
            final Validator validator) {
        Validate.notNull(validator, "Validator is required.");
        BeanValidationUtils.validator = validator;
    }

    /**
     * Validates if the specified bean is valid for the specified validation
     * groups.
     * 
     * @param obj The bean to validate
     * @param groups The group or list of groups targeted for validation
     * (defaults to Default)
     * @return If the object is valid for the specified groups
     * @throws IllegalArgumentException if object is {@code null}
     *         or if {@code null} is passed to the varargs groups
     * @throws ValidationException if a non recoverable error happens
     *         during the validation process
     */
    public static boolean isValid(
            @Nonnull
            final Object obj,
            @Nonnull
            final Class<?>... validGroups) {
        return validate(obj, validGroups).isEmpty();
    }

    /**
     * Validates the specified bean for the specified validation groups.
     * 
     * @param <T> The type of bean to validate
     * @param obj The bean to validate
     * @param groups The group or list of groups targeted for validation
     * (defaults to Default)
     * @return The constraint violations or an empty set if none
     * @throws IllegalArgumentException if object is {@code null}
     *         or if {@code null} is passed to the varargs groups
     * @throws ValidationException if a non recoverable error happens
     *         during the validation process
     */
    @Nonnull
    public static <T> Set<ConstraintViolation<T>> validate(
            @Nonnull
            final T obj,
            @Nonnull
            final Class<?>... groups) {
        return BeanValidationUtils.validator.validate(obj, groups);
    }
}
