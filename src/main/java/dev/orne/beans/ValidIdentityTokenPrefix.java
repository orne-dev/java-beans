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

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;

/**
 * Validation for valid identity token prefixes.
 * Validates that the token, if non null, is not empty and contains only
 * US-ASCII alpha characters.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 */
@Target({ 
    ElementType.METHOD,
    ElementType.FIELD,
    ElementType.PARAMETER,
    ElementType.ANNOTATION_TYPE
})
@Retention(
    RetentionPolicy.RUNTIME
)
@Documented
@Constraint(validatedBy = {})
@Pattern(regexp = IdentityTokenFormatter.PREFIX)
@ReportAsSingleViolation
public @interface ValidIdentityTokenPrefix {

    /**
     * Returns the error message.
     * 
     * @return The error message.
     */
    String message() default "{dev.orne.beans.ValidIdentityTokenPrefix.message}";

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
}
