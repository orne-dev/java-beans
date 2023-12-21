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

import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

/**
 * <p>Marks a method as an identity token to identity resolver. Used for
 * {@code Identity} implementations which {@code String} constructor
 * cannot be used to parse a identity token.</p>
 * 
 * <p>The annotated method must be static and return an instance of the
 * specific subclass of {@code Identity}.</p>
 * 
 * <p>Example:</p>
 * 
 * <pre>
 * class MyIdentity
 * implements Identity {
 *   ...
 *   public MyIdentity(String notAToken) {
 *     ...
 *   }
 *   ...
 *   {@code @}IdentityTokenResolver
 *   public static MyIdentity resolve(String identityToken)
 *   throws UnrecognizedIdentityTokenException {
 *     // Resolve identity token
 *   }
 *   ...
 * }
 * </pre>
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 */
@API(status=Status.STABLE, since="0.1")
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface IdentityTokenResolver {
    // No extra properties
}
