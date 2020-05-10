package dev.orne.beans;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

/**
 * Interface representing a bean with identity. Allows hiding the actual
 * identity implementation from referencing users.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 */
@BeanReference(IdentityBean.RequireIdentity.class)
public interface IdentityBean {

    /**
     * Returns the instance's identity.
     * 
     * @return The instance's identity
     */
    @NotNull(groups = RequireIdentity.class)
    @Valid
    @ConvertGroup(from = RequireIdentity.class, to = Default.class)
    Identity getIdentity();

    /**
     * Validation group to require a valid identity.
     */
    public static interface RequireIdentity {
        // No extra methods
    }
}
