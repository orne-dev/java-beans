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

import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

/**
 * Validation for beans that require a valid, non null identity.
 * Validates that the bean, if non null, has a non null valid identity.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 */
@API(status=Status.STABLE, since="0.1")
@Target({ 
    ElementType.CONSTRUCTOR,
    ElementType.METHOD,
    ElementType.FIELD,
    ElementType.PARAMETER,
    ElementType.LOCAL_VARIABLE,
    ElementType.TYPE_USE,
    ElementType.ANNOTATION_TYPE
})
@Retention(
    RetentionPolicy.RUNTIME
)
@Documented
@Constraint(validatedBy = {
    ValidBeanIdentity.ValidBeanIdentityValidator.class
})
@ReportAsSingleViolation
public @interface ValidBeanIdentity {

    /**
     * Returns the error message.
     * 
     * @return The error message.
     */
    String message() default "{dev.orne.beans.ValidBeanIdentity.message}";

    /**
     * Returns the validation groups.
     * 
     * @return The validation groups.
     */
    Class<?>[] groups() default { };

    /**
     * Returns the validation client payload.
     * 
     * @return The validation client payload.
     */
    Class<? extends Payload>[] payload() default { };

    /**
     * Constraint validator for {@code ValidBeanIdentity}.
     * 
     * @see ValidBeanIdentity
     */
    @API(status=Status.INTERNAL, since="0.1")
    public static class ValidBeanIdentityValidator
    implements ConstraintValidator<ValidBeanIdentity, Object> {

        /**
         * Creates a new instance.
         */
        public ValidBeanIdentityValidator() {
            super();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValid(
                final Object value,
                final ConstraintValidatorContext context) {
            if (value == null) {
                return true;
            }
            boolean valid = true;
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
                valid = isValid(value);
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
            return value instanceof IdentityBean &&
                    BeanValidationUtils.isValid(value, IdentityBean.RequireIdentity.class);
        }
    }
}
