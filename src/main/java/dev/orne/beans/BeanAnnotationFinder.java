package dev.orne.beans;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.WeakHashMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
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
 * Set<?> annotations = new BeanAnnotationFinder<>(annotationType)
 *     .find(beanType);
 * // Real case
 * Set&lt;NotNull&gt; annotations = new BeanAnnotationFinder<>(NotNull.class)
 *     .find(beanType);
 * </pre>
 *
 * <p>To search for type level annotations with annotation list support:</p>
 * <pre>
 * new BeanAnnotationFinder<>(
 *         annotationType,
 *         annotationListType,
 *         annotationListExtractor)
 *     .find(beanType);
 * // Real case
 * Set&lt;NotNull&gt; annotations = new BeanAnnotationFinder<>(
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
public class BeanAnnotationFinder<
        T extends Annotation,
        L extends Annotation> {

    /** The class logger. */
    private static final Logger LOG = LoggerFactory.getLogger(BeanAnnotationFinder.class);
    /** The type level annotations shared cache. */
    private static final Cache SHARED_CACHE = new WeakHashMapCache();

    /** The searched annotation type. */
    @Nonnull
    private final Class<T> annotationType;
    /** The searched annotation list type. */
    @Nullable
    private final Class<L> annotationListType;
    /** The searched annotation list extractor. */
    @Nullable
    private final AnnotationListExtractor<L, T> extractor;
    /**
     * The type level annotations cache for this instance. By default shared
     * between all instances.
     */
    @Nonnull
    private Cache cache = SHARED_CACHE;

    /**
     * Creates a new instance.
     * 
     * @param annotationType The searched annotation type
     */
    public BeanAnnotationFinder(
            @Nonnull
            final Class<T> annotationType) {
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
            @Nonnull
            final Class<T> annotationType,
            @Nullable
            final Class<L> annotationListType,
            @Nullable
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
    @Nonnull
    public Class<T> getAnnotationType() {
        return this.annotationType;
    }

    /**
     * Returns the searched annotation list type.
     * 
     * @return The searched annotation list type
     */
    @Nullable
    public Class<L> getAnnotationListType() {
        return this.annotationListType;
    }

    /**
     * Returns the searched annotation list extractor.
     * 
     * @return The searched annotation list extractor
     */
    @Nullable
    public AnnotationListExtractor<L, T> getExtractor() {
        return this.extractor;
    }

    /**
     * Returns the cache to be used by this instance.
     * 
     * @return The cache to be used by this instance
     */
    protected Cache getCache() {
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
            @Nullable
            Cache cache) {
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
    @Nonnull
    public Set<T> find(
            @Nonnull
            final Class<?> type) {
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
    @Nonnull
    protected Set<T> findAnnotations(
            @Nonnull
            final Class<?> type,
            @Nonnull
            final Set<Class<?>> visitedTypes) {
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
    @Nonnull
    protected Set<T> findAllAnnotations(
            @Nonnull
            final Class<?> type) {
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
            @Nonnull
            final Class<?> type,
            @Nonnull
            final Set<T> annotations) {
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
            @Nonnull
            final Class<?> type,
            @Nonnull
            final Set<T> annotations) {
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
            @Nonnull
            final Class<?> type,
            @Nonnull
            final Set<T> annotations,
            @Nonnull
            final Set<Class<?>> visitedTypes) {
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
            @Nonnull
            final Class<?> type,
            @Nonnull
            final Set<T> annotations,
            @Nonnull
            final Set<Class<?>> visitedTypes) {
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
        T[] extract(L list);
    }

    /**
     * Class level annotations cache entry. This class is immutable.
     * 
     * @param <T> The annotation type
     */
    protected static final class CacheEntryKey<T extends Annotation> {

        /** The analyzed class. */
        private final Class<?> type;
        /** The annotation type searched. */
        private final Class<T> annotationType;

        /**
         * Creates a new instance.
         * 
         * @param type The analyzed class
         * @param annotationType The annotation type searched
         */
        public CacheEntryKey(
                @Nonnull
                final Class<?> type,
                @Nonnull
                Class<T> annotationType) {
            super();
            this.type = type;
            this.annotationType = annotationType;
        }

        /**
         * Returns the analyzed class.
         * 
         * @return The analyzed class
         */
        public Class<?> getType() {
            return this.type;
        }

        /**
         * Returns the annotation type searched.
         * 
         * @return The annotation type searched
         */
        public Class<T> getAnnotationType() {
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
    protected static interface Cache {
        /**
         * Returns {@code true} if this instance contains an entry for
         * the specified key.
         * 
         * @param key The cache entry key
         * @return If this instance contains an entry for the key
         */
        boolean contains(
                @Nonnull
                CacheEntryKey<?> key);
        /**
         * Puts the 
         * @param <T>
         * @param key
         * @param value
         */
        <T extends Annotation> void put(
                @Nonnull
                CacheEntryKey<T> key,
                @Nonnull
                Set<T> value);
        /**
         * 
         * @param <T>
         * @param key
         * @return
         */
        @Nullable
        <T extends Annotation> Set<T> get(
                @Nonnull
                CacheEntryKey<T> key);
    }

    /**
     * Implementation of {@code Cache} based on {@code WeakHashMap}.
     * 
     * @see Cache
     * @see WeakHashMap
     */
    protected static class WeakHashMapCache
    implements Cache {

        /** The cache entries. */
        private final WeakHashMap<CacheEntryKey<?>, Set<? extends Annotation>> entries =
                new WeakHashMap<>();

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public synchronized boolean contains(
                @Nonnull
                final CacheEntryKey<?> key) {
            return this.entries.containsKey(key);
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public synchronized <T extends Annotation> void put(
                @Nonnull
                final CacheEntryKey<T> key,
                @Nonnull
                final Set<T> value) {
            this.entries.put(key, value);
        }

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        @SuppressWarnings("unchecked")
        public synchronized <T extends Annotation> Set<T> get(
                @Nonnull
                final CacheEntryKey<T> key) {
            return (Set<T>) this.entries.get(key);
        }
    }
}
