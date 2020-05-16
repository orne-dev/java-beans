/**
 * 
 */
package dev.orne.beans;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface IdentityTokenResolver {
    // No extra properties
}
