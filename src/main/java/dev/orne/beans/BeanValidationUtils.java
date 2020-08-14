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

import java.util.Set;

import javax.annotation.Nonnull;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;

import org.apache.commons.lang3.Validate;

import dev.orne.beans.ValidBeanIdentity.ValidBeanIdentityValidator;
import dev.orne.beans.ValidBeanReference.ValidBeanReferenceValidator;

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
            final Class<?>... groups) {
        return validate(obj, groups).isEmpty();
    }

    /**
     * Validates if the specified bean has a valid, non null, identity.
     * 
     * @param obj The bean to validate
     * @return If the bean has a valid identity
     * @throws IllegalArgumentException if bean is {@code null}
     * @throws ValidationException if a non recoverable error happens
     *         during the validation process
     */
    public static boolean isValidBeanIdentity(
            @Nonnull
            final Object obj) {
        return ValidBeanIdentityValidator.isValid(obj);
    }

    /**
     * Validates if the specified bean is a valid bean reference.
     * 
     * @param obj The bean to validate
     * @return If the bean is a valid bean reference
     * @throws IllegalArgumentException if bean is {@code null}
     * @throws ValidationException if a non recoverable error happens
     *         during the validation process
     */
    public static boolean isValidBeanReference(
            @Nonnull
            final Object obj) {
        return ValidBeanReferenceValidator.isValid(obj);
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
