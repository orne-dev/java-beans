package dev.orne.beans;

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
 * Validation for beans that require a valid, non null identity.
 * Validates that the bean, if non null, has a non null valid identity.
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
    ValidBeanIdentity.ValidBeanIdentityValidator.class
})
@ReportAsSingleViolation
public @interface ValidBeanIdentity {

    /** The default message key. */
    public static final String DEFAULT_MESSAGE =
            "dev.orne.beans.ValidBeanIdentity.message";
    /** The default message template. */
    public static final String DEFAULT_ERROR_TEMPLATE =
            "{" + DEFAULT_MESSAGE + "}";

    /** The error message template. */
    String message() default DEFAULT_ERROR_TEMPLATE;

    /** The validation groups. */
    Class<?>[] groups() default { };

    /** The validation client payload. */
    Class<? extends Payload>[] payload() default { };

    /**
     * Constraint validator for {@code ValidBeanIdentity}.
     * 
     * @see ValidBeanIdentity
     */
    public static class ValidBeanIdentityValidator
    implements ConstraintValidator<ValidBeanIdentity, Object> {

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
                    valid = isValid(value);
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
            return BeanValidationUtils.isValid(value, IdentityBean.RequireIdentity.class);
        }
    }
}
