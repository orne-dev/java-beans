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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;

/**
 * Validation for beans that require at least a valid bean reference.
 * Validates the the bean, if non null, validates against on of the validation
 * group sets defined through {@code BeanReference} annotations.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 */
@Target({ 
    ElementType.CONSTRUCTOR,
    ElementType.METHOD,
    ElementType.FIELD,
    ElementType.PARAMETER,
    ElementType.ANNOTATION_TYPE
})
@Retention(
    RetentionPolicy.RUNTIME
)
@Documented
@Constraint(validatedBy = {
    ValidBeanReference.ValidBeanReferenceValidator.class
})
@ReportAsSingleViolation
public @interface ValidBeanReference {

    /** The default message key. */
    public static final String DEFAULT_MESSAGE =
            "dev.orne.beans.ValidBeanReference.message";
    /** The default message template. */
    public static final String DEFAULT_ERROR_TEMPLATE =
            "{" + DEFAULT_MESSAGE + "}";

    /** @return The error message template. */
    String message() default DEFAULT_ERROR_TEMPLATE;

    /** @return The validation groups. */
    Class<?>[] groups() default { };

    /** @return The validation client payload. */
    Class<? extends Payload>[] payload() default { };

    /**
     * Constraint validator for {@code ValidBeanReference}.
     * 
     * @see ValidBeanReference
     */
    public static class ValidBeanReferenceValidator
    implements ConstraintValidator<ValidBeanReference, Object> {

        /** The {@code BeanReference} annotation finder. */
        public static final BeanAnnotationFinder<BeanReference, BeanReference.List> FINDER =
                new BeanAnnotationFinder<>(
                    BeanReference.class,
                    BeanReference.List.class,
                    BeanReference.List::value);

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValid(
                final Object value,
                final ConstraintValidatorContext context) {
            boolean valid = true;
            if (value != null) {
                if (value instanceof Iterable || value.getClass().isArray()) {
                    final Iterable<?> iterable;
                    if (value instanceof Iterable) {
                        iterable = (Iterable<?>) value;
                    } else {
                        iterable = Arrays.asList((Object[]) value);
                    }
                    for (final Object obj : iterable) {
                        valid = isValid(obj);
                        if (!valid) {
                            break;
                        }
                    }
                } else {
                    valid = isValid(value, FINDER);
                }
            }
            return valid;
        }

        /**
         * Returns {@code true} if specified bean is valid reference to it's
         * bean type.
         * 
         * @param value The bean to validate
         * @return If the bean is a valid bean reference
         */
        public static boolean isValid(
                @NotNull
                final Object value) {
            return isValid(value, FINDER);
        }

        /**
         * Returns {@code true} if specified bean is valid reference to it's
         * bean type.
         * 
         * @param value The bean to validate
         * @param annotationFinder The {@code BeanAnnotationFinder} to use
         * @return If the bean is a valid bean reference
         */
        public static boolean isValid(
                @NotNull
                final Object value,
                @NotNull
                final BeanAnnotationFinder<? extends BeanReference, ?> annotationFinder) {
            boolean valid = false;
            for (final BeanReference reference : annotationFinder.find(value.getClass())) {
                valid = BeanValidationUtils.isValid(value, reference.value());
                if (valid) {
                    break;
                }
            }
            return valid;
        }
    }
}
