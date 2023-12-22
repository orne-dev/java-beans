package dev.orne.beans.rnd;

/*-
 * #%L
 * Orne Beans
 * %%
 * Copyright (C) 2023 Orne Developments
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

import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import dev.orne.beans.BaseIdentityBean;
import dev.orne.beans.Identity;
import dev.orne.beans.IdentityBean;
import dev.orne.beans.WritableIdentityBean;
import dev.orne.test.rnd.AbstractTypedGenerator;
import dev.orne.test.rnd.Generators;

/**
 * Generator of {@code IdentityBean}, {@code WritableIdentityBean} and
 * {@code BaseIdentityBean} values.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2023-12
 * @since 0.6
 */
@API(status=Status.INTERNAL, since="0.6")
public class BaseIdentityBeanGenerator
extends AbstractTypedGenerator<BaseIdentityBean> {

    /**
     * Creates a new instance.
     */
    public BaseIdentityBeanGenerator() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(@NotNull Class<?> type) {
        return super.supports(type)
                || IdentityBean.class.equals(type)
                || WritableIdentityBean.class.equals(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull BaseIdentityBean defaultValue() {
        return new BaseIdentityBean();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull BaseIdentityBean randomValue() {
        final BaseIdentityBean result = new BaseIdentityBean();
        result.setIdentity(Generators.nullableRandomValue(Identity.class));
        return result;
    }
}
