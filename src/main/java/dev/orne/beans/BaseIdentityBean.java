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

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Base implementation of {@code IdentityBean}.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 */
public class BaseIdentityBean
implements WritableIdentityBean {

    /** The instance's identity. */
    private Identity identity;

    /**
     * Empty constructor.
     */
    public BaseIdentityBean() {
        super();
    }

    /**
     * Copy constructor.
     * 
     * @param copy The instance to copy
     */
    public BaseIdentityBean(
            final @NotNull BaseIdentityBean copy) {
        super();
        Validate.notNull(copy);
        this.identity = copy.identity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity getIdentity() {
        return this.identity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIdentity(
            final Identity identity) {
        this.identity = identity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getClass())
                .append(this.identity)
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) { return false; }
        final BaseIdentityBean other = (BaseIdentityBean) obj;
        return new EqualsBuilder()
                .append(this.identity, other.identity)
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(this.identity)
                .build();
    }
}
