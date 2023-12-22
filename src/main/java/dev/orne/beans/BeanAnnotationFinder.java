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

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.WeakHashMap;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Type level annotation finder. Detects annotations in classes,
 * directly implemented interfaces, super classes and inherited
 * interfaces. Supports detection of annotation list annotations.</p>
 *
 * <p>Examples:</p>
 * 
 * <p>To search for type level annotations:</p>
 * <pre>
 * Set&lt;?&gt; annotations = new BeanAnnotationFinder&lt;&gt;(annotationType)
 *     .find(beanType);
 * // Real case
 * Set&lt;NotNull&gt; annotations = new BeanAnnotationFinder&lt;&gt;(NotNull.class)
 *     .find(beanType);
 * </pre>
 *
 * <p>To search for type level annotations with annotation list support:</p>
 * <pre>
 * Set&lt;?&gt; annotations = new BeanAnnotationFinder&lt;&gt;(
 *         annotationType,
 *         annotationListType,
 *         annotationListExtractor)
 *     .find(beanType);
 * // Real case
 * Set&lt;NotNull&gt; annotations = new BeanAnnotationFinder&lt;&gt;(
 *         NotNull.class,
 *         NotNull.List.class,
 *         NotNull.List::values)
 *     .find(beanType);
 * </pre>
 *
 * <p>Instances are reusable and thread-safe.</p>
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @param <T> The supported annotation type
 * @param <L> The supported annotation list type
 * @since 0.1
 */
@API(status=Status.INTERNAL, since="0.1")
public class BeanAnnotationFinder<
        T extends Annotation,
        L extends Annotation> {

    /** The class logger. */
    private static final Logger LOG = LoggerFactory.getLogger(BeanAnnotationFinder.class);
    /** The type level annotations shared cache. */
    private static final Cache SHARED_CACHE = new WeakHashMapCache();

    /** The searched annotation type. */
    private final @NotNull Class<T> annotationType;
    /** The searched annotation list type. */
    private final Class<L> annotationListType;
    /** The searched annotation list extractor. */
    private final AnnotationListExtractor<L, T> extractor;
    /**
     * The type level annotations cache for this instance. By default shared
     * between all instances.
     */
    private @NotNull Cache cache = SHARED_CACHE;

    /**
     * Creates a new instance.
     * 
     * @param annotationType The searched annotation type
     */
    public BeanAnnotationFinder(
            final @NotNull Class<T> annotationType) {
        this(annotationType, null, null);
    }

    /**
     * Creates a new instance. To support annotation list both
     * {@code annotationListType} and {@code extractor} must be supplied.
     * 
     * @param annotationType The searched annotation type
     * @param annotationListType The searched annotation list type
     * @param extractor The searched annotation list extractor
     */
    public BeanAnnotationFinder(
            final @NotNull Class<T> annotationType,
            final Class<L> annotationListType,
            final AnnotationListExtractor<L, T> extractor) {
        super();
        Validate.notNull(annotationType, "Annotation type is required.");
        this.annotationType = annotationType;
        if ((annotationListType == null) != (extractor == null)) {
            LOG.warn(
                    "To support annotation lists both 'annotationListType'" +
                        " and 'extractor' are required." +
                        " Annotation list support is disabled." +
                        " Passed values are: '{}' and '{}'",
                    annotationListType,
                    extractor);
        }
        this.annotationListType = annotationListType;
        this.extractor = extractor;
    }

    /**
     * Returns the searched annotation type.
     * 
     * @return The searched annotation type
     */
    public @NotNull Class<T> getAnnotationType() {
        return this.annotationType;
    }

    /**
     * Returns the searched annotation list type.
     * 
     * @return The searched annotation list type
     */
    public Class<L> getAnnotationListType() {
        return this.annotationListType;
    }

    /**
     * Returns the searched annotation list extractor.
     * 
     * @return The searched annotation list extractor
     */
    public AnnotationListExtractor<L, T> getExtractor() {
        return this.extractor;
    }

    /**
     * Returns the cache to be used by this instance.
     * 
     * @return The cache to be used by this instance
     */
    protected @NotNull Cache getCache() {
        return this.cache;
    }

    /**
     * <p>Sets the type level annotations cache for this instance.
     * If {@code cache} is {@code null} shared cache will be used.</p>
     * 
     * @param cache The cache to be used by this instance
     * @return This instance for method chaining
     */
    protected BeanAnnotationFinder<T, L> setCache(
            final Cache cache) {
        if (cache == null) {
            this.cache = SHARED_CACHE;
        } else {
            this.cache = cache;
        }
        return this;
    }

    /**
     * Finds the type level annotations this instance supports in the specified
     * type.
     * 
     * @param type The type to search for supported annotations
     * @return The type level annotations of the type
     */
    public @NotNull Set<T> find(
            final @NotNull Class<?> type) {
        Validate.notNull(type, "Type is required.");
        return findAnnotations(type, new HashSet<>());
    }

    /**
     * Finds type level annotations of the supported annotation type in the
     * specified type. If the type has been already processed returns an
     * empty set. Found annotations are cached for future uses.
     * 
     * @param type The type to found annotations on
     * @param visitedTypes The visited types record to prevent loops
     * and repeated searches
     * @return The found annotations
     */
    protected @NotNull Set<T> findAnnotations(
            final @NotNull Class<?> type,
            final @NotNull Set<Class<?>> visitedTypes) {
        final Set<T> annotations = new HashSet<>(0);
        if (!visitedTypes.contains(type)) {
            synchronized (this.cache) {
                final CacheEntryKey<T> cacheKey = new CacheEntryKey<>(
                        type,
                        this.annotationType);
                final Set<T> cachedDefinitions = this.cache.get(cacheKey);
                if (cachedDefinitions == null) {
                    final Set<T> foundAnnotatios = findAllAnnotations(type);
                    annotations.addAll(foundAnnotatios);
                    LOG.debug(
                            "Caching annotations for type {}: {}",
                            type,
                            annotations);
                    this.cache.put(cacheKey, foundAnnotatios);
                } else {
                    annotations.addAll(cachedDefinitions);
                }
            }
            visitedTypes.add(type);
        }
        return annotations;
    }

    /**
     * Finds type level annotations of the supported annotation type in the
     * specified type. Finds direct annotations (detecting annotation lists if
     * configured) and annotations in super class and implemented interfaces.
     * 
     * @param type The type to found annotations on
     * @return The found annotations
     */
    protected @NotNull Set<T> findAllAnnotations(
            final @NotNull Class<?> type) {
        final Set<T> annotations = new HashSet<>(0);
        final Set<Class<?>> visitedTypes = new HashSet<>();
        visitedTypes.add(type);
        addDirectAnnotation(type, annotations);
        addDirectAnnotationsList(type, annotations);
        addInterfacesAnnotations(type, annotations, visitedTypes);
        addSuperclassAnnotations(type, annotations, visitedTypes);
        return annotations;
    }

    /**
     * Finds direct type level annotations of the supported annotation type in
     * the specified type.
     * 
     * @param type The type to found annotations on
     * @param annotations The set to add the found annotations on
     */
    protected void addDirectAnnotation(
            final @NotNull Class<?> type,
            final @NotNull Set<T> annotations) {
        final T annotation = type.getAnnotation(this.annotationType);
        if (annotation != null) {
            annotations.add(annotation);
        }
    }

    /**
     * Finds direct type level annotations of the supported annotation list
     * type in the specified type, if configured. If {@code annotationListType}
     * or {@code extractor} are {@code null} no searching is done.
     * 
     * @param type The type to found annotations on
     * @param annotations The set to add the found annotations on
     */
    protected void addDirectAnnotationsList(
            final @NotNull Class<?> type,
            final @NotNull Set<T> annotations) {
        if (this.annotationListType != null && this.extractor != null) {
            final L annotationsList = type.getAnnotation(
                    this.annotationListType);
            if (annotationsList != null) {
                annotations.addAll(Arrays.asList(
                        this.extractor.extract(annotationsList)));
            }
        }
    }
 
    /**
     * Finds type level annotations of the supported annotation type in
     * the super class of the specified type, if any.
     * 
     * @param type The type to found annotations on
     * @param annotations The set to add the found annotations on
     * @param visitedTypes The visited types record to prevent loops
     * and repeated searches
     */
    protected void addSuperclassAnnotations(
            final @NotNull Class<?> type,
            final @NotNull Set<T> annotations,
            final @NotNull Set<Class<?>> visitedTypes) {
        if (type.getSuperclass() != null) {
            annotations.addAll(findAnnotations(
                    type.getSuperclass(),
                    visitedTypes));
        }
    }

    /**
     * Finds type level annotations of the supported annotation type in
     * the interfaces implemented by the specified type, if any.
     * 
     * @param type The type to found annotations on
     * @param annotations The set to add the found annotations on
     * @param visitedTypes The visited types record to prevent loops
     * and repeated searches
     */
    protected void addInterfacesAnnotations(
            final @NotNull Class<?> type,
            final @NotNull Set<T> annotations,
            final @NotNull Set<Class<?>> visitedTypes) {
        for (final Class<?> iface : type.getInterfaces()) {
            annotations.addAll(findAnnotations(iface, visitedTypes));
        }
    }

    /**
     * <p>Functional interface for extractor of individual annotations from
     * annotation list annotations.</p>
     * 
     * @param <T> The annotation type
     * @param <L> The annotation list type
     */
    @API(status=Status.INTERNAL, since="0.1")
    @FunctionalInterface
    public static interface AnnotationListExtractor<
            L extends Annotation,
            T extends Annotation> {

        /**
         * Extracts the nested annotations from the list annotation
         * passed as argument.
         * 
         * @param list The annotation list annotation
         * @return The nested annotations
         */
        @NotNull T[] extract(L list);
    }

    /**
     * Class level annotations cache entry. This class is immutable.
     * 
     * @param <T> The annotation type
     */
    @API(status=Status.INTERNAL, since="0.1")
    protected static final class CacheEntryKey<T extends Annotation> {

        /** The analyzed class. */
        private final @NotNull Class<?> type;
        /** The annotation type searched. */
        private final @NotNull Class<T> annotationType;

        /**
         * Creates a new instance.
         * 
         * @param type The analyzed class
         * @param annotationType The annotation type searched
         */
        public CacheEntryKey(
                final @NotNull Class<?> type,
                final @NotNull Class<T> annotationType) {
            super();
            this.type = type;
            this.annotationType = annotationType;
        }

        /**
         * Returns the analyzed class.
         * 
         * @return The analyzed class
         */
        public @NotNull Class<?> getType() {
            return this.type;
        }

        /**
         * Returns the annotation type searched.
         * 
         * @return The annotation type searched
         */
        public @NotNull Class<T> getAnnotationType() {
            return this.annotationType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                    .append(getClass())
                    .append(this.type)
                    .append(this.annotationType)
                    .toHashCode();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(final Object obj) {
            if (obj == null) { return false; }
            if (obj == this) { return true; }
            if (obj.getClass() != getClass()) {
                return false;
            }
            final CacheEntryKey<?> other = (CacheEntryKey<?>) obj;
            return new EqualsBuilder()
                    .append(this.type, other.type)
                    .append(this.annotationType, other.annotationType)
                    .isEquals();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("type", this.type)
                    .append("annotationType", this.annotationType)
                    .toString();
        }
    }

    /**
     * Interface for type level annotations cache.
     */
    @API(status=Status.INTERNAL, since="0.1")
    protected static interface Cache {

        /**
         * Returns {@code true} if this instance contains an entry for
         * the specified key.
         * 
         * @param key The cache entry key
         * @return If this instance contains an entry for the key
         */
        boolean contains(
                @NotNull CacheEntryKey<?> key);

        /**
         * Returns the cached found annotations for the specified key, if any.
         * 
         * @param <T> The type of annotations
         * @param key The cache entry key
         * @return The annotations found, or {@code null} if not cached o cache expired
         */
        <T extends Annotation> Set<T> get(
                @NotNull CacheEntryKey<T> key);

        /**
         * Puts the specified found annotations for the specified key.
         * 
         * @param <T> The type of annotations
         * @param key The cache entry key
         * @param value The annotations found
         */
        <T extends Annotation> void put(
                @NotNull CacheEntryKey<T> key,
                @NotNull Set<T> value);
    }

    /**
     * Implementation of {@code Cache} based on {@code WeakHashMap}.
     * 
     * @see Cache
     * @see WeakHashMap
     */
    @API(status=Status.INTERNAL, since="0.1")
    protected static class WeakHashMapCache
    implements Cache {

        /** The cache entries. */
        private final WeakHashMap<CacheEntryKey<?>, Set<? extends Annotation>> entries =
                new WeakHashMap<>();

        /**
         * Creates a new instance.
         */
        public WeakHashMapCache() {
            super();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public synchronized boolean contains(
                final @NotNull CacheEntryKey<?> key) {
            return this.entries.containsKey(key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public synchronized @NotNull <T extends Annotation> void put(
                final @NotNull CacheEntryKey<T> key,
                final @NotNull Set<T> value) {
            this.entries.put(key, value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @SuppressWarnings("unchecked")
        public synchronized <T extends Annotation> Set<T> get(
                final @NotNull CacheEntryKey<T> key) {
            return (Set<T>) this.entries.get(key);
        }
    }
}
