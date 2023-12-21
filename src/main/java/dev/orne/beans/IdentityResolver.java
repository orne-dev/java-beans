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

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.WeakHashMap;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.Validate;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Identity resolver that converts an instance of {@code Identity}
 * of unknown type to an instance of a concrete type.</p>
 * 
 * <p>The target identity type must have a public constructor that accepts a
 * single {@code String} argument. If such constructor is not an identity token
 * resolution constructor a public, static method that accepts a single
 * {@code String} argument and returns instances of identity type can be
 * annotated with {@code IdentityTokenResolver} to provide an alternative
 * identity token resolution method.</p>
 * 
 * <p>If no constructor and no static method is valid a warning is logged and
 * every future resolution attempt for that identity type will fail.</p>
 * 
 * <p>Valid examples:</p>
 * 
 * <pre>
 * class MyIdentity
 * implements Identity {
 *   ...
 *   public MyIdentity(String token)
 *   throws UnrecognizedIdentityTokenException {
 *     ...
 *   }
 *   ...
 * }
 * </pre>
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
 * @see IdentityTokenResolver
 */
@API(status=Status.STABLE, since="0.1")
public class IdentityResolver {

    /** The class logger. */
    private static final Logger LOG = LoggerFactory.getLogger(IdentityResolver.class);
    /** The type level annotations shared cache. */
    private static final Cache SHARED_CACHE = new WeakHashMapCache();
    /** Error message for unresolvable identity token. */
    private static final String ERR_UNRESOLVABLE_TOKEN =
            "Identity token cannot be resolved";
    /** Error message for misconfigured identity types. */
    private static final String ERR_MISCONFIGURED =
            "Identity token cannot be resolved for misconfigured identity type";
    /** Informational message for misconfigured identity types. */
    private static final String HELP_MISCONFIGURED =
            "Identity type '{}' resolution is misconfigured."
            + " See IdentityResolver javadoc for correct Identity resolution configuration."
            + " No further resolution attempts will succed.";
    /** Error message for misconfigured identity types with no valid resolver. */
    private static final String ERR_NO_RESOLVER =
            "Identity type '%s' does not have any valid identity token resolution method or constructor";
    /** Error message for unexpected resolver type. */
    private static final String ERR_UNEXPECTED_RESOLVER_TYPE =
            "Unexpected Executable subtype for '%s' identity type: %s";
    /** Error message for unexpected resolver type. */
    private static final String ERR_GET_RESOLVER_ERROR =
            "Unexpected error analyzing '%s' identity type";
    /** Required modifiers for identity token resolution methods. */
    private static final int RESOLVER_METHOD_MODIFIERS =
            Modifier.STATIC | Modifier.PUBLIC;
    /** Error message for wrong resolution method modifiers. */
    private static final String ERR_RESOLVER_METHOD_MODIFIERS =
            "Invalid modifiers in annotated token resolution method '%s' for identity type '%s'."
            + " Method must be public and static.";
    /** Error message for wrong resolution method parameter types. */
    private static final String ERR_RESOLVER_METHOD_PARAMS =
            "Invalid parameter types in annotated token resolution method '%s' for identity type '%s'."
            + " Method must have one only argument of String type.";
    /** Error message for wrong resolution method return type. */
    private static final String ERR_RESOLVER_METHOD_RETURN_TYPE =
            "Invalid return type in annotated token resolution method '%s' for identity type '%s'."
            + " Method must return identity type.";
    /** Error message for no identity token constructor. */
    private static final String ERR_TOKEN_CONSTRUCTOR_NOT_FOUND =
            "No identity token contructor for identity type '%s'.";
    /**
     * The identity type resolve methods cache for this instance. By default
     * shared between all instances.
     */
    private @NotNull Cache cache = SHARED_CACHE;

    /**
     * Creates a new instance.
     */
    protected IdentityResolver() {
        super();
    }

    /**
     * Returns the shared, singleton instance.
     * 
     * @return The singleton instance.
     */
    public static @NotNull IdentityResolver getInstance() {
        return InstanceHolder.INSTANCE;
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
     * <p>Sets the identity type resolve methods cache for this instance.
     * If {@code cache} is {@code null} shared cache will be used.</p>
     * 
     * @param cache The cache to be used by this instance
     * @return This instance for method chaining
     */
    protected @NotNull IdentityResolver setCache(
            final Cache cache) {
        if (cache == null) {
            this.cache = SHARED_CACHE;
        } else {
            this.cache = cache;
        }
        return this;
    }

    /**
     * Resolves the specified source identity to an identity of the specified
     * target type.
     * 
     * @param <T> The target identity type
     * @param identity The source identity
     * @param targetType The target identity type
     * @return An instance of target identity type or {@code null}
     * if source identity is null or invalid
     * @throws UnrecognizedIdentityTokenException If the source identity's
     * token cannot be resolved to target identity type
     */
    public <T extends Identity> T resolve(
            final Identity identity,
            final @NotNull Class<T> targetType)
    throws UnrecognizedIdentityTokenException {
        Validate.notNull(targetType);
        if (identity == null || targetType.isInstance(identity)) {
            return targetType.cast(identity);
        }
        return resolve(identity.getIdentityToken(), targetType);
    }

    /**
     * Resolves the specified source identity token to an identity of the
     * specified target type.
     * 
     * @param <T> The target identity type
     * @param identityToken The source identity token
     * @param targetType The target identity type
     * @return An instance of target identity type or {@code null}
     * if source identity token is null
     * @throws UnrecognizedIdentityTokenException If the source identity
     * token cannot be resolved to target identity type
     */
    public <T extends Identity> T resolve(
            final String identityToken,
            final @NotNull Class<T> targetType)
    throws UnrecognizedIdentityTokenException {
        Validate.notNull(targetType);
        if (identityToken == null) {
            return null;
        }
        try {
            final Executable resolver = getResolver(targetType);
            if (resolver instanceof Method) {
                return targetType.cast(((Method) resolver).invoke(null, identityToken));
            } else if (resolver instanceof Constructor) {
                return targetType.cast(((Constructor<?>) resolver).newInstance(identityToken));
            } else {
                throw new UnresolvableIdentityException(String.format(
                        ERR_UNEXPECTED_RESOLVER_TYPE,
                        targetType,
                        resolver.getClass()));
            }
        } catch (final UnresolvableIdentityException uie) {
            throw new UnrecognizedIdentityTokenException(ERR_MISCONFIGURED, uie);
        } catch (final InvocationTargetException ite) {
            if (!(ite.getTargetException() instanceof UnrecognizedIdentityTokenException)) {
                throw new UnrecognizedIdentityTokenException(
                        ERR_UNRESOLVABLE_TOKEN,
                        ite.getTargetException());
            } else {
                throw ((UnrecognizedIdentityTokenException) ite.getTargetException());
            }
        } catch (final IllegalAccessException
                | IllegalArgumentException
                | InstantiationException e) {
            throw new UnrecognizedIdentityTokenException(ERR_UNRESOLVABLE_TOKEN, e);
        }
    }

    /**
     * Returns the method or constructor to be used to resolve identity tokens
     * for the specified target identity type.
     * 
     * @param targetType The target identity type
     * @return The identity type resolution method or constructor
     * @throws UnresolvableIdentityException If the identity type is
     * misconfigured
     */
    protected @NotNull Executable getResolver(
            final @NotNull Class<? extends Identity> targetType)
    throws UnresolvableIdentityException {
        Validate.notNull(targetType);
        Executable resolver;
        synchronized (this.cache) {
            if (this.cache.contains(targetType)) {
                resolver = this.cache.get(targetType);
            } else {
                try {
                    resolver = findTokenResolverMethod(targetType);
                    if (resolver == null) {
                        resolver = findTokenConstructor(targetType);
                    }
                    this.cache.put(targetType, resolver);
                } catch (final UnresolvableIdentityException uie) {
                    LOG.warn(HELP_MISCONFIGURED, targetType);
                    this.cache.put(targetType, null);
                    throw uie;
                } catch (final RuntimeException re) {
                    LOG.warn(HELP_MISCONFIGURED, targetType);
                    this.cache.put(targetType, null);
                    throw new UnresolvableIdentityException(
                            ERR_GET_RESOLVER_ERROR, re);
                }
            }
        }
        if (resolver == null) {
            throw new UnresolvableIdentityException(String.format(
                    ERR_NO_RESOLVER,
                    targetType));
        }
        return resolver;
    }

    /**
     * Finds a method annotated with {@code IdentityTokenResolver} in the
     * specified target identity type. The method must be public and static,
     * have a single {@code String} argument and return a
     * 
     * @param targetType The target identity type
     * @return The found identity token resolution method
     * @throws UnresolvableIdentityException If the annotated method doesn't
     * fulfill the requirements
     * @throws SecurityException If a security exception occurs accessing the
     * class methods
     * @see IdentityTokenResolver
     */
    protected Method findTokenResolverMethod(
            final @NotNull Class<?> targetType)
    throws UnresolvableIdentityException {
        Validate.notNull(targetType);
        Method resolver = null;
        for (final Method method : targetType.getDeclaredMethods()) {
            if (method.isAnnotationPresent(IdentityTokenResolver.class)) {
                if ((method.getModifiers() & RESOLVER_METHOD_MODIFIERS) != RESOLVER_METHOD_MODIFIERS) {
                    throw new UnresolvableIdentityException(String.format(
                            ERR_RESOLVER_METHOD_MODIFIERS,
                            method,
                            targetType));
                }
                if (!Arrays.equals(new Class<?>[] { String.class }, method.getParameterTypes())) {
                    throw new UnresolvableIdentityException(String.format(
                            ERR_RESOLVER_METHOD_PARAMS,
                            method,
                            targetType));
                }
                if (!(targetType.isAssignableFrom(method.getReturnType()))) {
                    throw new UnresolvableIdentityException(String.format(
                            ERR_RESOLVER_METHOD_RETURN_TYPE,
                            method,
                            targetType));
                }
                resolver = method;
                break;
            }
        }
        return resolver;
    }

    /**
     * Finds an identity token constructor in the specified target identity
     * type. The constructor must be public and have a single {@code String}
     * argument.
     * 
     * @param <T> The target identity type
     * @param targetType The target identity type
     * @return The found identity token constructor
     * @throws UnresolvableIdentityException If no constructor is found or it
     * doesn't fulfill the requirements
     * @throws SecurityException If a security exception occurs accessing the
     * constructor
     */
    protected <T> Constructor<T> findTokenConstructor(
            final @NotNull Class<T> targetType)
    throws UnresolvableIdentityException {
        Validate.notNull(targetType);
        try {
            return targetType.getConstructor(String.class);
        } catch (final NoSuchMethodException nsme) {
            throw new UnresolvableIdentityException(String.format(
                        ERR_TOKEN_CONSTRUCTOR_NOT_FOUND,
                        targetType),
                    nsme);
        }
    }

    /**
     * Holder of shared {@code IdentityResolver} instance.
     */
    @API(status=Status.INTERNAL, since="0.1")
    private static class InstanceHolder{

        /** The shared instance. */
        private static final IdentityResolver INSTANCE = new IdentityResolver();
    }

    /**
     * Interface for identity resolve methods cache.
     */
    @API(status=Status.INTERNAL, since="0.1")
    protected static interface Cache {

        /**
         * Returns {@code true} if this instance contains an entry for
         * the specified identity type.
         * 
         * @param key The identity type
         * @return If this instance contains an entry for the identity type
         */
        boolean contains(
                @NotNull Class<? extends Identity> key);

        /**
         * Returns the cached identity token resolution executable for the
         * specified identity type, if any.
         * 
         * @param key The identity type
         * @return The identity token resolution executable, or
         * {@code null} if not cached o cache expired
         */
        Executable get(
                @NotNull Class<? extends Identity> key);

        /**
         * Puts the specified identity token resolution executable for the
         * specified identity type.
         * 
         * @param key The identity type
         * @param value The identity token resolution executable
         */
        void put(
                @NotNull Class<? extends Identity> key,
                Executable value);
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
        private final WeakHashMap<Class<? extends Identity>, Executable> entries =
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
                final @NotNull Class<? extends Identity> key) {
            return this.entries.containsKey(key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public synchronized Executable get(
                final @NotNull Class<? extends Identity> key) {
            return this.entries.get(key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public synchronized void put(
                final @NotNull Class<? extends Identity> key,
                final Executable value) {
            this.entries.put(key, value);
        }
    }

    /**
     * Exception for misconfigured identity types. Caused when an {@code Identity}
     * doesn't have a {@code String} constructor and no valid static method has
     * been annotated with {@code IdentityTokenResolver}.
     * 
     * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
     * @version 1.0, 2020-05
     * @since 0.1
     * @see IdentityResolver
     * @see IdentityTokenResolver
     */
    @API(status=Status.STABLE, since="0.1")
    public static class UnresolvableIdentityException
    extends Exception {

        /** The Serial Version UID. */
        private static final long serialVersionUID = 1L;

        /**
         * Constructs a new exception with {@code null} as its detail message.
         * The cause is not initialized, and may subsequently be initialized by a
         * call to {@link #initCause}.
         */
        public UnresolvableIdentityException() {
            super();
        }

        /**
         * Constructs a new exception with the specified detail message.  The
         * cause is not initialized, and may subsequently be initialized by
         * a call to {@link #initCause}.
         *
         * @param   message   the detail message. The detail message is saved for
         *          later retrieval by the {@link #getMessage()} method.
         */
        public UnresolvableIdentityException(
                final String message) {
            super(message);
        }

        /**
         * Constructs a new exception with the specified cause and a detail
         * message of {@code (cause==null ? null : cause.toString())} (which
         * typically contains the class and detail message of {@code cause}).
         * This constructor is useful for exceptions that are little more than
         * wrappers for other throwables (for example, {@link
         * java.security.PrivilegedActionException}).
         *
         * @param  cause the cause (which is saved for later retrieval by the
         *         {@link #getCause()} method).  (A {@code null} value is
         *         permitted, and indicates that the cause is nonexistent or
         *         unknown.)
         */
        public UnresolvableIdentityException(
                final Throwable cause) {
            super(cause);
        }

        /**
         * Constructs a new exception with the specified detail message and
         * cause.  <p>Note that the detail message associated with
         * {@code cause} is <i>not</i> automatically incorporated in
         * this exception's detail message.
         *
         * @param  message the detail message (which is saved for later retrieval
         *         by the {@link #getMessage()} method).
         * @param  cause the cause (which is saved for later retrieval by the
         *         {@link #getCause()} method).  (A {@code null} value is
         *         permitted, and indicates that the cause is nonexistent or
         *         unknown.)
         */
        public UnresolvableIdentityException(
                final String message,
                final Throwable cause) {
            super(message, cause);
        }

        /**
         * Constructs a new exception with the specified detail message,
         * cause, suppression enabled or disabled, and writable stack
         * trace enabled or disabled.
         *
         * @param  message the detail message.
         * @param cause the cause.  (A {@code null} value is permitted,
         * and indicates that the cause is nonexistent or unknown.)
         * @param enableSuppression whether or not suppression is enabled
         *                          or disabled
         * @param writableStackTrace whether or not the stack trace should
         *                           be writable
         */
        public UnresolvableIdentityException(
                final String message,
                final Throwable cause,
                final boolean enableSuppression,
                final boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }
}
