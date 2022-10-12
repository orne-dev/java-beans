package dev.orne.beans;

/*-
 * #%L
 * Orne Beans
 * %%
 * Copyright (C) 2022 Orne Developments
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

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.Validate;

/**
 * Validation for valid identities.
 * Validates that the identity, if non null, is of the expected type or can be
 * resolved to an identity of the expected type. If no identity type is
 * specified 
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-10
 * @since 0.4
 */
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
@Valid
@Constraint(validatedBy = {
        ValidIdentity.ValidIdentityValidator.class,
        ValidIdentity.ValidIdentityValidatorForString.class,
    })
@ReportAsSingleViolation
public @interface ValidIdentity {

    /** The default message key. */
    public static final String DEFAULT_MESSAGE =
            "dev.orne.beans.ValidIdentity.message";
    /** The default message template. */
    public static final String DEFAULT_ERROR_TEMPLATE =
            "{" + DEFAULT_MESSAGE + "}";

    /** @return The expected type of {@code Identity} to resolve to. */
    Class<? extends Identity> value() default Identity.class;

    /** @return The error message template. */
    String message() default DEFAULT_ERROR_TEMPLATE;

    /** @return  The validation groups. */
    Class<?>[] groups() default { };

    /** @return  The validation client payload. */
    Class<? extends Payload>[] payload() default { };

    /**
     * Constraint validator for {@code ValidBeanIdentity} on {@code Identity}
     * instances.
     * 
     * @see ValidBeanIdentity
     */
    public static class ValidIdentityValidator
    implements ConstraintValidator<ValidIdentity, Identity> {

        /** The expected type of identity. */
        private @NotNull Class<? extends Identity> expectedType;

        /**
         * {@inheritDoc}
         */
        @Override
        public void initialize(
                final @NotNull ValidIdentity annotation) {
            this.expectedType = annotation.value();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValid(
                final Identity value,
                final ConstraintValidatorContext context) {
            boolean valid = true;
            if (value != null) {
                valid = isValid(value, this.expectedType);
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
                final @NotNull Identity value) {
            return isValid(value, Identity.class);
        }

        /**
         * Returns {@code true} if specified bean is valid reference to it's
         * bean type.
         * 
         * @param value The bean to validate
         * @param expectedType The expected type of identity
         * @return If the bean is a valid bean reference
         */
        public static boolean isValid(
                final @NotNull Identity value,
                final @NotNull Class<? extends Identity> expectedType) {
            Validate.notNull(value);
            Validate.notNull(expectedType);
            if (expectedType.isInstance(value)) {
                return true;
            } else {
                try {
                    IdentityResolver.getInstance().resolve(value, expectedType);
                    return true;
                } catch (final UnrecognizedIdentityTokenException ignore) {
                    return false;
                }
            }
        }
    }

    /**
     * Constraint validator for {@code ValidBeanIdentity} on {@code String}
     * identity tokens.
     * 
     * @see ValidBeanIdentity
     */
    public static class ValidIdentityValidatorForString
    implements ConstraintValidator<ValidIdentity, String> {

        /** The expected type of identity. */
        private @NotNull Class<? extends Identity> expectedType;

        /**
         * {@inheritDoc}
         */
        @Override
        public void initialize(
                final @NotNull ValidIdentity annotation) {
            this.expectedType = annotation.value();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValid(
                final String value,
                final ConstraintValidatorContext context) {
            boolean valid = true;
            if (value != null) {
                valid = isValid(value, this.expectedType);
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
                final @NotNull String value) {
            return isValid(value, Identity.class);
        }

        /**
         * Returns {@code true} if specified bean is valid reference to it's
         * bean type.
         * 
         * @param value The bean to validate
         * @param expectedType The expected type of identity
         * @return If the bean is a valid bean reference
         */
        public static boolean isValid(
                final @NotNull String value,
                final @NotNull Class<? extends Identity> expectedType) {
            Validate.notNull(value);
            Validate.notNull(expectedType);
            if (Identity.class.equals(expectedType)) {
                return true;
            } else {
                try {
                    IdentityResolver.getInstance().resolve(value, expectedType);
                    return true;
                } catch (final UnrecognizedIdentityTokenException ignore) {
                    return false;
                }
            }
        }
    }
}
