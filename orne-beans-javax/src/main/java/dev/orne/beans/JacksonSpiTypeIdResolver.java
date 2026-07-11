package dev.orne.beans;

/*-
 * #%L
 * Orne Beans
 * %%
 * Copyright (C) 2020 - 2025 Orne Developments
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * Implementation of {@code TypeIdResolver} that discovers interface/bean
 * subtypes using SPI configuration files.
 * <p>
 * Uses {@code JsonTypeName} annotations to map ID to subtype.
 * <p>
 * Example:
 * <pre>
 * package a.b.c;
 * {@literal @}JsonTypeInfo(
 *         use=JsonTypeInfo.Id.NAME,
 *         defaultImpl=DefaultImpl.class)
 * {@literal @}JsonTypeIdResolver(JacksonSpiTypeIdResolver.class)
 * interface IBase { ... }
 * 
 * package a.b.c;
 * {@literal @}JsonTypeName("Default")
 * class DefaultImpl implements IBase { ... }
 * 
 * package a.b.c.extra;
 * 
 * {@literal @}JsonTypeName("Extra")
 * class ExtraImpl implements IBase { ... }
 * </pre>
 * In (potentially multiple) {@code META-INF/services/a.b.c.IBase} file:
 * <pre>
 * a.b.c.DefaultImpl
 * a.b.c.extra.ExtraImpl
 * </pre>
 * 
 * @author <a href="https://github.com/ihernaez">(w) Iker Hernaez</a>
 * @version 1.0, 2023-11
 * @since 0.6
 * @see TypeIdResolver
 */
@API(status=Status.EXPERIMENTAL, since="0.6")
public class JacksonSpiTypeIdResolver
extends TypeIdResolverBase {

    /** The class logger. */
    private static final Logger LOG = LoggerFactory.getLogger(JacksonSpiTypeIdResolver.class);

    /** The registered types. */
    private final Map<String, Class<?>> subtypes = new HashMap<>();
    /** Error message for repeated subtype names. */
    private static final String REPEATED_ERR = "Annotated type [%s] got repeated subtype name [%s]";

    /**
     * Creates a new instance.
     */
    public JacksonSpiTypeIdResolver() {
        super();
    }

    /**
     * Returns the registered types.
     * 
     * @return The registered types
     */
    public Map<String, Class<?>> getSubtypes() {
        return Map.copyOf(this.subtypes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(
            final JavaType bt) {
        super.init(Objects.requireNonNull(bt));
        final Class<?> bc = bt.getRawClass();
        if (bc.isAnnotationPresent(JsonTypeIdResolver.class)) {
            this.subtypes.putAll(getRegisteredSubTypes(bc));
        } else {
            this.subtypes.putAll(findInheritedSubTypes(bc)
                    .entrySet().stream()
                    .filter(entry -> bc.isAssignableFrom(entry.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable String idFromValue(
            final Object value) {
        return getIdFromBean(Objects.requireNonNull(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable String idFromValueAndType(
            final @Nullable Object value,
            final Class<?> suggestedType) {
        return value == null ? getIdFromAnnotation(suggestedType) : idFromValue(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable JavaType typeFromId(
            final @Nullable DatabindContext context,
            final @Nullable String id) {
        final Class<?> type;
        if (id != null && this.subtypes.containsKey(id)) {
            type = this.subtypes.get(id);
        } else {
            type = null;
        }
        final JavaType result;
        if (type == null) {
            LOG.debug("Unresolved ID {}. Using default implementation (if any)...", id);
            result = null;
        } else if (context == null) {
            LOG.debug("Resolved ID {} to type {}", id, type);
            result = TypeFactory.defaultInstance().constructType(type);
        } else {
            LOG.debug("Resolved ID {} to type {}", id, type);
            result = context.constructType(type);
        }
        return result;
    }

    /**
     * Discovers the registered sub-types scanning the classpath for
     * service providers of the base type.
     * 
     * @param baseClass The base type.
     * @return The map of IDs to subtypes
     */
    protected Map<String, Class<?>> getRegisteredSubTypes(
            final Class<?> baseClass) {
        final ServiceLoader<?> loader = ServiceLoader.load(baseClass);
        final Map<String, Class<?>> result = new HashMap<>();
        for (final Object bean : loader) {
            final Class<?> type = bean.getClass();
            final String id = getIdFromBean(bean);
            checkForRepeatedName(result, id, type);
            result.put(id, type);
        }
        return result;
    }

    /**
     * Discovers the registered sub-types scanning the classpath for
     * service providers of the base type. Searchs for 
     * 
     * @param type The base type.
     * @return The map of IDs to subtypes
     */
    protected Map<String, Class<?>> findInheritedSubTypes(
            final Class<?> type) {
        Map<String, Class<?>> result;
        JsonTypeIdResolver annot = type.getAnnotation(JsonTypeIdResolver.class);
        if (annot != null) {
            result = getRegisteredSubTypes(type);
        } else {
            result = new HashMap<>();
        }
        for (final Class<?> interf : type.getInterfaces()) {
            final Map<String, Class<?>> inherited = findInheritedSubTypes(interf);
            for (final Map.Entry<String, Class<?>> entry : inherited.entrySet()) {
                checkForRepeatedName(result, entry.getKey(), entry.getValue());
                result.put(entry.getKey(), entry.getValue());
            }
        }
        if (!type.isInterface() && type.getSuperclass() != Object.class) {
            final Map<String, Class<?>> inherited = findInheritedSubTypes(type.getSuperclass());
            for (final Map.Entry<String, Class<?>> entry : inherited.entrySet()) {
                checkForRepeatedName(result, entry.getKey(), entry.getValue());
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    /**
     * Checks the known sub-types for repeated IDs for different sub-type.
     * 
     * @param known The known sub-types.
     * @param id The type ID to check.
     * @param type The type to check.
     * @throws IllegalArgumentException If the ID has been used for another
     * sub-type.
     */
    protected void checkForRepeatedName(
            final Map<String, Class<?>> known,
            final String id,
            final Class<?> type) {
        if (known.containsKey(id) && !known.get(id).equals(type)) {
            throw new IllegalArgumentException(
                    String.format(REPEATED_ERR, type, id));
        }
    }

    /**
     * Retrieves the type ID from the bean instance.
     * <p>
     * Default implementation calls to {@link #getIdFromAnnotation(Class)} when
     * bean class.
     * 
     * @param bean The bean to retrieve the type ID from
     * @return The type ID, or {@code null} if not resolved
     */
    protected String getIdFromBean(
            final Object bean) {
        String id = getIdFromAnnotation(bean.getClass());
        if (id == null) {
            id = defaultTypeId(bean.getClass());
        }
        return id;
    }

    /**
     * Extracts the type ID from the {@code JsonTypeName} annotation
     * of the specified type.
     * 
     * @param type The actual bean type
     * @return The type ID, or {@code null} if not resolved
     */
    protected @Nullable String getIdFromAnnotation(
            final Class<?> type) {
        final JsonTypeName annot = type.getAnnotation(JsonTypeName.class);
        final String name;
        if (annot == null) {
            name = null;
        } else {
            name = annot.value();
        }
        return name;
    }

    /**
     * If no name was explicitly given for a class, we will just
     * use non-qualified class name
     * 
     * @param cls The type class
     * @return The non-qualified class name
     * 
     * @see com.fasterxml.jackson.databind.jsontype.impl.TypeNameIdResolver
     */
    public static String defaultTypeId(
            final Class<?> cls) {
        return defaultTypeId(cls.getName());
    }

    /**
     * If no name was explicitly given for a class, we will just
     * use non-qualified class name
     * 
     * @param clsName The class name
     * @return The non-qualified class name
     * 
     * @see com.fasterxml.jackson.databind.jsontype.impl.TypeNameIdResolver
     */
    public static String defaultTypeId(
            final String clsName) {
        final int index = clsName.lastIndexOf('.');
        final String id;
        if (index < 0) {
            id = clsName;
        } else {
            id = clsName.substring(index+1);
        }
        return id;
    }
}
