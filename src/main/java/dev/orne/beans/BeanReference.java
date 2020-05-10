package dev.orne.beans;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a valid bean reference.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(BeanReference.List.class)
@Documented
public @interface BeanReference {

    /** The validation groups that compose a bean reference. */
    Class<?>[] value();

    /**
     * Defines multiple valid bean references.
     * 
     * @See {@link BeanReference}
     */
    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {

        /** The valid bean references. */
        BeanReference[] value();
    }
}
