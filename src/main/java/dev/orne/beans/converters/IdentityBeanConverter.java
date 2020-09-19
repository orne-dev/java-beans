package dev.orne.beans.converters;

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

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.AbstractConverter;

import dev.orne.beans.BaseIdentityBean;
import dev.orne.beans.Identity;
import dev.orne.beans.IdentityBean;
import dev.orne.beans.WritableIdentityBean;

/**
 * Implementation of {@code Converter} that converts {@code IdentityBean}
 * instances to {@code String} and {@code WritableIdentityBean} from
 * {@code String} using the identity token of the bean as {@code String}
 * representation.
 * <p>
 * Uses a nested {@code Converter}, of type {@code IdentityConverter} by
 * default, to convert bean identities.
 * <p>
 * When converting from {@code String} instances of {@code TokenIdentity}
 * are produced an populated on empty instances of the bean, producing valid
 * {@code ValidBeanIdentity} instances.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-09
 * @since 0.3
 * @see IdentityBean
 * @see IdentityConverter
 */
public class IdentityBeanConverter
extends AbstractConverter {

    /** Message error when target type is not WritableIdentityBean. */
    private static final String NOT_WRITABLE_IBEAN_ERROR =
            "Can't convert value '%s' to type %s. " +
            "Target type does not implement %s";
    /** Message error when cannot instantiate target type. */
    private static final String NEW_INSTANCE_ERROR =
            "Can't convert value '%s' to type %s. " +
            "Cannot create new instances of target type";

    /** The default target type. */
    private final Class<? extends WritableIdentityBean> defaultType;
    /** The converter for identities. */
    private final Converter identityConverter;

    /**
     * Creates a new instance that throws a {@code ConversionException} if an
     * error occurs and uses default instance of {@code IdentityConverter} for
     * identity conversions.
     */
    public IdentityBeanConverter() {
        super();
        this.defaultType = BaseIdentityBean.class;
        this.identityConverter = new IdentityConverter();
    }

    /**
     * Creates a new instance that returns a default value if an error occurs 
     * and uses default instance of {@code IdentityConverter} for identity
     * conversions.
     * 
     * @param defaultValue The default value to be returned if the value to be
     * converted is missing or an error occurs converting the value
     */
    public IdentityBeanConverter(
            final IdentityBean defaultValue) {
        super(defaultValue);
        this.defaultType = BaseIdentityBean.class;
        this.identityConverter = new IdentityConverter();
    }

    /**
     * Creates a new instance that throws a {@code ConversionException} if an
     * error occurs.
     * 
     * @param identityConverter The converter to use for identity conversions
     */
    public IdentityBeanConverter(
            final @NotNull Converter identityConverter) {
        super();
        this.defaultType = BaseIdentityBean.class;
        this.identityConverter = identityConverter;
    }

    /**
     * Creates a new instance that returns a default value if an error occurs.
     * 
     * @param identityConverter The converter to use for identity conversions
     * @param defaultValue The default value to be returned if the value to be
     * converted is missing or an error occurs converting the value
     */
    public IdentityBeanConverter(
            final @NotNull Converter identityConverter,
            final WritableIdentityBean defaultValue) {
        super(defaultValue);
        this.defaultType = BaseIdentityBean.class;
        this.identityConverter = identityConverter;
    }

    /**
     * Creates a new instance that throws a {@code ConversionException} if an
     * error occurs and uses default instance of {@code IdentityConverter} for
     * identity conversions.
     * 
     * @param defaultType The default type this <code>Converter</code> handles.
     */
    public IdentityBeanConverter(
            final @NotNull Class<? extends WritableIdentityBean> defaultType) {
        super();
        this.defaultType = defaultType;
        this.identityConverter = new IdentityConverter();
    }

    /**
     * Creates a new instance that returns a default value if an error occurs 
     * and uses default instance of {@code IdentityConverter} for identity
     * conversions.
     * 
     * @param <T> The default type this <code>Converter</code> handles.
     * @param defaultType The default type this <code>Converter</code> handles.
     * @param defaultValue The default value to be returned if the value to be
     * converted is missing or an error occurs converting the value
     */
    public <T extends WritableIdentityBean> IdentityBeanConverter(
            final @NotNull Class<T> defaultType,
            final T defaultValue) {
        super(defaultValue);
        this.defaultType = defaultType;
        this.identityConverter = new IdentityConverter();
    }

    /**
     * Creates a new instance that throws a {@code ConversionException} if an
     * error occurs.
     * 
     * @param defaultType The default type this <code>Converter</code> handles.
     * @param identityConverter The converter to use for identity conversions
     */
    public IdentityBeanConverter(
            final @NotNull Class<? extends WritableIdentityBean> defaultType,
            final @NotNull Converter identityConverter) {
        super();
        this.defaultType = defaultType;
        this.identityConverter = identityConverter;
    }

    /**
     * Creates a new instance that returns a default value if an error occurs.
     * 
     * @param <T> The default type this <code>Converter</code> handles.
     * @param defaultType The default type this <code>Converter</code> handles.
     * @param identityConverter The converter to use for identity conversions
     * @param defaultValue The default value to be returned if the value to be
     * converted is missing or an error occurs converting the value
     */
    public <T extends WritableIdentityBean> IdentityBeanConverter(
            final @NotNull Class<T> defaultType,
            final @NotNull Converter identityConverter,
            final T defaultValue) {
        super(defaultValue);
        this.defaultType = defaultType;
        this.identityConverter = identityConverter;
    }

    /**
     * Returns the converter for identities.
     * 
     * @return The converter for identities
     */
    protected Converter getIdentityConverter() {
        return this.identityConverter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected @NotNull Class<?> getDefaultType() {
        return this.defaultType == null ? WritableIdentityBean.class : this.defaultType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <T> T convertToType(
            final @NotNull Class<T> type,
            final Object value) {
        if (IdentityBean.class.isAssignableFrom(type)) {
            if (type.isInstance(value)) {
                return type.cast(value);
            } else if (WritableIdentityBean.class.isAssignableFrom(type)) {
                final Identity identity = convertIdentity(value);
                if (identity == null) {
                    return null;
                }
                final T bean = createInstance(type, value);
                ((WritableIdentityBean) bean).setIdentity(identity);
                return bean;
            } else {
                throw new ConversionException(
                        String.format(
                            NOT_WRITABLE_IBEAN_ERROR,
                            value,
                            type,
                            WritableIdentityBean.class));
            }
        } else {
            throw conversionException(type, value);
        }
    }

    /**
     * Tries to create a new instance of the target type.
     * 
     * @param <T> The type of bean to create
     * @param type The type of bean to create
     * @param value The input value to be converted. Used only when an error
     * occurs
     * @return The newly created instance
     * @throws ConversionException If the new instance cannot be created
     */
    protected <T> T createInstance(
            final @NotNull Class<T> type,
            final Object value) {
        try {
            return type.newInstance();
        } catch (final InstantiationException | IllegalAccessException e) {
            throw new ConversionException(
                    String.format(
                        NEW_INSTANCE_ERROR,
                        value,
                        type),
                    e);
        }
    }

    /**
     * Converts the value to a {@code Identity} instance.
     * 
     * @param value The input value to be converted
     * @return The converted value
     * @throws ConversionException If an error occurs converting the value
     */
    protected Identity convertIdentity(
            final Object value) {
        final Identity identity;
        if (value instanceof Identity) {
            identity = (Identity) value;
        } else {
            identity = this.identityConverter.convert(
                    Identity.class,
                    value);
        }
        return identity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String convertToString(
            final Object value)
    throws Throwable {
        if (value instanceof IdentityBean) {
            return this.identityConverter.convert(
                    String.class,
                    ((IdentityBean) value).getIdentity());
        } else if (value instanceof String) {
            return value.toString();
        } else {
            throw conversionException(String.class, value);
        }
    }

}
