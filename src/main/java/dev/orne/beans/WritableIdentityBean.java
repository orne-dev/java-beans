package dev.orne.beans;

import javax.annotation.Nullable;

/**
 * Interface representing a bean with identity that allows assigning its
 * identity.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 */
public interface WritableIdentityBean
extends IdentityBean {

    /**
     * Sets the instance's identity.
     * 
     * @param identity The instance's identity
     */
    void setIdentity(
            @Nullable Identity identity);
}
