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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;

import javax.validation.constraints.NotBlank;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import dev.orne.beans.IdentityResolver.WeakHashMapCache;
import dev.orne.beans.IdentityResolver.Cache;
import dev.orne.beans.IdentityResolver.UnresolvableIdentityException;

/**
 * Unit tests for {@code IdentityResolver}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see IdentityResolver
 */
@Tag("ut")
public class IdentityResolverTest {

    /**
     * Test {@link IdentityResolver#getInstance()}.
     */
    @Test
    public void testGetInstance() {
        final IdentityResolver instance1 = IdentityResolver.getInstance();
        assertNotNull(instance1);
        final IdentityResolver instance2 = IdentityResolver.getInstance();
        assertNotNull(instance2);
        assertSame(instance1, instance2);
    }

    /**
     * Creates a new {@code IdentityResolver} for testing.
     * 
     * @return The created instance
     */
    protected IdentityResolver createInstance() {
        return new IdentityResolver();
    }

    /**
     * Test {@link IdentityResolver#setCache(BeanAnnotationFinder.Cache)}.
     */
    @Test
    public void testSetCache() {
        final IdentityResolver instance = createInstance();
        final Cache sharedCache = instance.getCache();
        final Cache mockCache = mock(Cache.class);
        instance.setCache(mockCache);
        assertNotNull(instance.getCache());
        assertSame(mockCache, instance.getCache());
        instance.setCache(null);
        assertNotNull(instance.getCache());
        assertSame(sharedCache, instance.getCache());
    }

    /**
     * Test {@link IdentityResolver#resolve(Identity, Class)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testResolveIdentityNullType()
    throws Throwable {
        final IdentityResolver instance = spy(createInstance());
        final Cache mockCache = mock(Cache.class);
        instance.setCache(mockCache);
        final Identity mockIdentity = mock(Identity.class);
        assertThrows(NullPointerException.class, () -> {
            instance.resolve(mockIdentity, null);
        });
        then(mockCache).shouldHaveNoInteractions();
    }

    /**
     * Test {@link IdentityResolver#resolve(Identity, Class)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testResolveNullIdentity()
    throws Throwable {
        final IdentityResolver instance = spy(createInstance());
        final Cache mockCache = mock(Cache.class);
        instance.setCache(mockCache);
        final Class<? extends Identity> mockTargetType = mock(Identity.class).getClass();
        final Identity mockIdentity = null;
        assertNull(instance.resolve(mockIdentity, mockTargetType));
        then(mockCache).shouldHaveNoInteractions();
        then(instance).should(never()).getResolver(mockTargetType);
        then(instance).should(never()).findTokenResolverMethod(mockTargetType);
        then(instance).should(never()).findTokenConstructor(mockTargetType);
    }

    /**
     * Test {@link IdentityResolver#resolve(Identity, Class)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testResolveSameTypeIdentity()
    throws Throwable {
        final IdentityResolver instance = spy(createInstance());
        final Cache mockCache = mock(Cache.class);
        instance.setCache(mockCache);
        final Identity mockIdentity = mock(Identity.class);
        final Class<? extends Identity> mockTargetType = mockIdentity.getClass();
        final Identity result = instance.resolve(mockIdentity, mockTargetType);
        then(mockCache).shouldHaveNoInteractions();
        then(instance).should(never()).getResolver(mockTargetType);
        then(instance).should(never()).findTokenResolverMethod(mockTargetType);
        then(instance).should(never()).findTokenConstructor(mockTargetType);
        assertNotNull(result);
        assertSame(mockIdentity, result);
    }

    /**
     * Test {@link IdentityResolver#resolve(Identity, Class)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testResolveIdentity()
    throws Throwable {
        final IdentityResolver instance = spy(createInstance());
        final Cache mockCache = mock(Cache.class);
        instance.setCache(mockCache);
        final Identity mockIdentity = mock(Identity.class);
        final Class<? extends Identity> mockTargetType = TestEmptyIdentity.class;
        final String mockToken = "mock token";
        final Identity mockResult = mock(Identity.class);
        willReturn(mockToken).given(mockIdentity).getIdentityToken();
        willReturn(mockResult).given(instance).resolve(mockToken, mockTargetType);
        final Identity result = instance.resolve(mockIdentity, mockTargetType);
        then(mockCache).shouldHaveNoInteractions();
        then(instance).should().resolve(mockToken, mockTargetType);
        then(instance).should(never()).getResolver(mockTargetType);
        then(instance).should(never()).findTokenResolverMethod(mockTargetType);
        then(instance).should(never()).findTokenConstructor(mockTargetType);
        assertNotNull(result);
        assertSame(mockResult, result);
    }

    /**
     * Test {@link IdentityResolver#resolve(String, Class)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testResolveTokenNullType()
    throws Throwable {
        final IdentityResolver instance = spy(createInstance());
        final Cache mockCache = mock(Cache.class);
        instance.setCache(mockCache);
        final String mockToken = "mock token";
        assertThrows(NullPointerException.class, () -> {
            instance.resolve(mockToken, null);
        });
        then(mockCache).shouldHaveNoInteractions();
    }

    /**
     * Test {@link IdentityResolver#resolve(Identity, Class)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testResolveNullToken()
    throws Throwable {
        final IdentityResolver instance = spy(createInstance());
        final Cache mockCache = mock(Cache.class);
        instance.setCache(mockCache);
        final Class<? extends Identity> mockTargetType = mock(Identity.class).getClass();
        final String mockToken = null;
        assertNull(instance.resolve(mockToken, mockTargetType));
        then(mockCache).shouldHaveNoInteractions();
        then(instance).should(never()).getResolver(mockTargetType);
        then(instance).should(never()).findTokenResolverMethod(mockTargetType);
        then(instance).should(never()).findTokenConstructor(mockTargetType);
    }

    /**
     * Test {@link IdentityResolver#resolve(String, Class)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testResolveTokenUnresolvableIdentity()
    throws Throwable {
        final IdentityResolver instance = spy(createInstance());
        final Cache mockCache = mock(Cache.class);
        instance.setCache(mockCache);
        final String mockToken = "mock token";
        final Class<? extends Identity> mockTargetType = mock(Identity.class).getClass();
        final UnresolvableIdentityException mockGetResolverResult =
                new UnresolvableIdentityException();
        willThrow(mockGetResolverResult).given(instance).getResolver(mockTargetType);
        final UnrecognizedIdentityTokenException result =
                assertThrows(UnrecognizedIdentityTokenException.class, () -> {
            instance.resolve(mockToken, mockTargetType);
        });
        then(mockCache).shouldHaveNoInteractions();
        then(instance).should().getResolver(mockTargetType);
        then(instance).should(never()).findTokenResolverMethod(mockTargetType);
        then(instance).should(never()).findTokenConstructor(mockTargetType);
        assertNotNull(result);
        assertNotNull(result.getCause());
        assertSame(mockGetResolverResult, result.getCause());
    }

    /**
     * Test {@link IdentityResolver#resolve(String, Class)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testResolveTokenUnknownExecutableType()
    throws Throwable {
        final IdentityResolver instance = spy(createInstance());
        final Cache mockCache = mock(Cache.class);
        instance.setCache(mockCache);
        final String mockToken = "mock token";
        final Class<? extends Identity> mockTargetType = mock(Identity.class).getClass();
        final Executable mockResolver = mock(Executable.class);
        willReturn(mockResolver).given(instance).getResolver(mockTargetType);
        final UnrecognizedIdentityTokenException result =
                assertThrows(UnrecognizedIdentityTokenException.class, () -> {
            instance.resolve(mockToken, mockTargetType);
        });
        then(mockCache).shouldHaveNoInteractions();
        then(instance).should().getResolver(mockTargetType);
        then(instance).should(never()).findTokenResolverMethod(mockTargetType);
        then(instance).should(never()).findTokenConstructor(mockTargetType);
        assertNotNull(result);
        assertNotNull(result.getCause());
        assertTrue(result.getCause() instanceof UnresolvableIdentityException);
    }

    /**
     * Test {@link IdentityResolver#resolve(String, Class)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testResolveTokenConstructor()
    throws Throwable {
        final IdentityResolver instance = spy(createInstance());
        final Cache mockCache = mock(Cache.class);
        instance.setCache(mockCache);
        final String mockToken = "mock token";
        final Class<TestTokenConstructorIdentity> targetType =TestTokenConstructorIdentity.class;
        final Executable mockResolver = TestTokenConstructorIdentity.class
                .getConstructor(String.class);
        willReturn(mockResolver).given(instance).getResolver(targetType);
        final TestTokenConstructorIdentity result =
                instance.resolve(mockToken, targetType);
        then(mockCache).shouldHaveNoInteractions();
        then(instance).should().getResolver(targetType);
        then(instance).should(never()).findTokenResolverMethod(targetType);
        then(instance).should(never()).findTokenConstructor(targetType);
        assertNotNull(result);
        assertNotNull(result.constructorParam);
        assertSame(mockToken, result.constructorParam);
    }

    /**
     * Test {@link IdentityResolver#resolve(String, Class)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testResolveTokenConstructorUnrecognized()
    throws Throwable {
        final IdentityResolver instance = spy(createInstance());
        final Cache mockCache = mock(Cache.class);
        instance.setCache(mockCache);
        final String mockToken = "mock token";
        final Class<TestTokenConstructorIdentityUnrecognized> targetType =
                TestTokenConstructorIdentityUnrecognized.class;
        final Executable mockResolver = TestTokenConstructorIdentityUnrecognized.class
                .getConstructor(String.class);
        willReturn(mockResolver).given(instance).getResolver(targetType);
        final UnrecognizedIdentityTokenException result =
                assertThrows(UnrecognizedIdentityTokenException.class, () -> {
                    instance.resolve(mockToken, targetType);
                });
        then(mockCache).shouldHaveNoInteractions();
        then(instance).should().getResolver(targetType);
        then(instance).should(never()).findTokenResolverMethod(targetType);
        then(instance).should(never()).findTokenConstructor(targetType);
        assertNotNull(result);
        assertNotNull(result.getMessage());
        assertSame(mockToken, result.getMessage());
    }

    /**
     * Test {@link IdentityResolver#resolve(String, Class)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testResolveTokenConstructorException()
    throws Throwable {
        final IdentityResolver instance = spy(createInstance());
        final Cache mockCache = mock(Cache.class);
        instance.setCache(mockCache);
        final String mockToken = "mock token";
        final Class<TestTokenConstructorIdentityIllegal> targetType =
                TestTokenConstructorIdentityIllegal.class;
        final Executable mockResolver = TestTokenConstructorIdentityIllegal.class
                .getConstructor(String.class);
        willReturn(mockResolver).given(instance).getResolver(targetType);
        final UnrecognizedIdentityTokenException result =
                assertThrows(UnrecognizedIdentityTokenException.class, () -> {
                    instance.resolve(mockToken, targetType);
                });
        then(mockCache).shouldHaveNoInteractions();
        then(instance).should().getResolver(targetType);
        then(instance).should(never()).findTokenResolverMethod(targetType);
        then(instance).should(never()).findTokenConstructor(targetType);
        assertNotNull(result);
        assertNotNull(result.getCause());
        assertTrue(result.getCause() instanceof IllegalArgumentException);
        assertSame(mockToken, result.getCause().getMessage());
    }

    /**
     * Test {@link IdentityResolver#resolve(String, Class)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testResolveTokenConstructorNoInstanciable()
    throws Throwable {
        final IdentityResolver instance = spy(createInstance());
        final Cache mockCache = mock(Cache.class);
        instance.setCache(mockCache);
        final String mockToken = "mock token";
        final Class<TestTokenConstructorIdentityAbstract> targetType =
                TestTokenConstructorIdentityAbstract.class;
        final Executable mockResolver = TestTokenConstructorIdentityAbstract.class
                .getConstructor(String.class);
        willReturn(mockResolver).given(instance).getResolver(targetType);
        final UnrecognizedIdentityTokenException result =
                assertThrows(UnrecognizedIdentityTokenException.class, () -> {
                    instance.resolve(mockToken, targetType);
                });
        then(mockCache).shouldHaveNoInteractions();
        then(instance).should().getResolver(targetType);
        then(instance).should(never()).findTokenResolverMethod(targetType);
        then(instance).should(never()).findTokenConstructor(targetType);
        assertNotNull(result);
        assertNotNull(result.getCause());
        assertTrue(result.getCause() instanceof InstantiationException);
    }

    /**
     * Test {@link IdentityResolver#resolve(String, Class)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testResolveTokenMethod()
    throws Throwable {
        final IdentityResolver instance = spy(createInstance());
        final Cache mockCache = mock(Cache.class);
        instance.setCache(mockCache);
        final String mockToken = "mock token";
        final Class<TestTokenResolverIdentity> targetType =TestTokenResolverIdentity.class;
        final Executable mockResolver = TestTokenResolverIdentity.class
                .getMethod("resolve", String.class);
        willReturn(mockResolver).given(instance).getResolver(targetType);
        final TestTokenResolverIdentity result =
                instance.resolve(mockToken, targetType);
        then(mockCache).shouldHaveNoInteractions();
        then(instance).should().getResolver(targetType);
        then(instance).should(never()).findTokenResolverMethod(targetType);
        then(instance).should(never()).findTokenConstructor(targetType);
        assertNotNull(result);
        assertNotNull(result.resolverParam);
        assertSame(mockToken, result.resolverParam);
    }

    /**
     * Test {@link IdentityResolver#resolve(String, Class)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testResolveTokenMethodUnrecognized()
    throws Throwable {
        final IdentityResolver instance = spy(createInstance());
        final Cache mockCache = mock(Cache.class);
        instance.setCache(mockCache);
        final String mockToken = "mock token";
        final Class<TestTokenResolverIdentityUnrecognized> targetType =
                TestTokenResolverIdentityUnrecognized.class;
        final Executable mockResolver = TestTokenResolverIdentityUnrecognized.class
                .getMethod("resolve", String.class);
        willReturn(mockResolver).given(instance).getResolver(targetType);
        final UnrecognizedIdentityTokenException result =
                assertThrows(UnrecognizedIdentityTokenException.class, () -> {
                    instance.resolve(mockToken, targetType);
                });
        then(mockCache).shouldHaveNoInteractions();
        then(instance).should().getResolver(targetType);
        then(instance).should(never()).findTokenResolverMethod(targetType);
        then(instance).should(never()).findTokenConstructor(targetType);
        assertNotNull(result);
        assertNotNull(result.getMessage());
        assertSame(mockToken, result.getMessage());
    }

    /**
     * Test {@link IdentityResolver#resolve(String, Class)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testResolveTokenMethodException()
    throws Throwable {
        final IdentityResolver instance = spy(createInstance());
        final Cache mockCache = mock(Cache.class);
        instance.setCache(mockCache);
        final String mockToken = "mock token";
        final Class<TestTokenResolverIdentityIllegal> targetType =
                TestTokenResolverIdentityIllegal.class;
        final Executable mockResolver = TestTokenResolverIdentityIllegal.class
                .getMethod("resolve", String.class);
        willReturn(mockResolver).given(instance).getResolver(targetType);
        final UnrecognizedIdentityTokenException result =
                assertThrows(UnrecognizedIdentityTokenException.class, () -> {
                    instance.resolve(mockToken, targetType);
                });
        then(mockCache).shouldHaveNoInteractions();
        then(instance).should().getResolver(targetType);
        then(instance).should(never()).findTokenResolverMethod(targetType);
        then(instance).should(never()).findTokenConstructor(targetType);
        assertNotNull(result);
        assertNotNull(result.getCause());
        assertTrue(result.getCause() instanceof IllegalArgumentException);
        assertSame(mockToken, result.getCause().getMessage());
    }

    /**
     * Test {@link IdentityResolver#getResolver(Class)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testGetResolverNullType()
    throws Throwable {
        final IdentityResolver instance = spy(createInstance());
        final Cache mockCache = mock(Cache.class);
        instance.setCache(mockCache);
        assertThrows(NullPointerException.class, () -> {
            instance.getResolver(null);
        });
        then(mockCache).shouldHaveNoInteractions();
    }

    /**
     * Test {@link IdentityResolver#getResolver(Class)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testGetResolverCache()
    throws Throwable {
        final IdentityResolver instance = spy(createInstance());
        final Cache mockCache = mock(Cache.class);
        instance.setCache(mockCache);
        final Class<? extends Identity> mockTargetType =
                mock(Identity.class).getClass();
        final Executable expectedResult = mock(Executable.class);
        willReturn(true).given(mockCache).contains(mockTargetType);
        willReturn(expectedResult).given(mockCache).get(mockTargetType);
        final Executable result = instance.getResolver(mockTargetType);
        then(instance).should(never()).findTokenResolverMethod(mockTargetType);
        then(instance).should(never()).findTokenConstructor(mockTargetType);
        then(mockCache).should().get(mockTargetType);
        assertNotNull(result);
        assertSame(expectedResult, result);
    }

    /**
     * Test {@link IdentityResolver#getResolver(Class)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testGetResolverMethodFail()
    throws Throwable {
        final IdentityResolver instance = spy(createInstance());
        final Cache mockCache = mock(Cache.class);
        instance.setCache(mockCache);
        final Class<? extends Identity> targetType =
                TestTokenResolverIdentity.class;
        final UnresolvableIdentityException expectedResult =
                new UnresolvableIdentityException();
        willReturn(false).given(mockCache).contains(targetType);
        willReturn(null).given(mockCache).get(targetType);
        willThrow(expectedResult).given(instance).findTokenResolverMethod(targetType);
        final UnresolvableIdentityException result =
                assertThrows(UnresolvableIdentityException.class, () -> {
            instance.getResolver(targetType);
        });
        final InOrder inOrder = inOrder(instance, mockCache);
        inOrder.verify(instance).findTokenResolverMethod(targetType);
        inOrder.verify(mockCache).put(targetType, null);
        inOrder.verifyNoMoreInteractions();
        then(instance).should(never()).findTokenConstructor(targetType);
        assertNotNull(result);
        assertSame(expectedResult, result);
    }

    /**
     * Test {@link IdentityResolver#getResolver(Class)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testGetResolverMethodFailSecurity()
    throws Throwable {
        final IdentityResolver instance = spy(createInstance());
        final Cache mockCache = mock(Cache.class);
        instance.setCache(mockCache);
        final Class<? extends Identity> targetType =
                TestTokenResolverIdentity.class;
        final SecurityException expectedResult = new SecurityException();
        willReturn(false).given(mockCache).contains(targetType);
        willReturn(null).given(mockCache).get(targetType);
        willThrow(expectedResult).given(instance).findTokenResolverMethod(targetType);
        final UnresolvableIdentityException result =
                assertThrows(UnresolvableIdentityException.class, () -> {
            instance.getResolver(targetType);
        });
        final InOrder inOrder = inOrder(instance, mockCache);
        inOrder.verify(instance).findTokenResolverMethod(targetType);
        inOrder.verify(mockCache).put(targetType, null);
        inOrder.verifyNoMoreInteractions();
        then(instance).should(never()).findTokenConstructor(targetType);
        assertNotNull(result);
        assertNotNull(result.getCause());
        assertSame(expectedResult, result.getCause());
    }

    /**
     * Test {@link IdentityResolver#getResolver(Class)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testGetResolverMethod()
    throws Throwable {
        final IdentityResolver instance = spy(createInstance());
        final Cache mockCache = mock(Cache.class);
        instance.setCache(mockCache);
        final Class<? extends Identity> targetType =
                TestTokenResolverIdentity.class;
        final Method expectedResult = TestTokenResolverIdentity.class
                .getMethod("resolve", String.class);
        willReturn(false).given(mockCache).contains(targetType);
        willReturn(null).given(mockCache).get(targetType);
        willReturn(expectedResult).given(instance).findTokenResolverMethod(targetType);
        final Executable result = instance.getResolver(targetType);
        final InOrder inOrder = inOrder(instance, mockCache);
        inOrder.verify(instance).findTokenResolverMethod(targetType);
        inOrder.verify(mockCache).put(targetType, result);
        inOrder.verifyNoMoreInteractions();
        then(instance).should(never()).findTokenConstructor(targetType);
        assertNotNull(result);
        assertSame(expectedResult, result);
    }

    /**
     * Test {@link IdentityResolver#getResolver(Class)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testGetResolverConstructorFail()
    throws Throwable {
        final IdentityResolver instance = spy(createInstance());
        final Cache mockCache = mock(Cache.class);
        instance.setCache(mockCache);
        final Class<? extends Identity> targetType =
                TestTokenConstructorIdentity.class;
        final UnresolvableIdentityException expectedResult =
                new UnresolvableIdentityException();
        willReturn(false).given(mockCache).contains(targetType);
        willReturn(null).given(mockCache).get(targetType);
        willReturn(null).given(instance).findTokenResolverMethod(targetType);
        willThrow(expectedResult).given(instance).findTokenConstructor(targetType);
        final UnresolvableIdentityException result =
                assertThrows(UnresolvableIdentityException.class, () -> {
            instance.getResolver(targetType);
        });
        final InOrder inOrder = inOrder(instance, mockCache);
        inOrder.verify(instance).findTokenResolverMethod(targetType);
        inOrder.verify(instance).findTokenConstructor(targetType);
        inOrder.verify(mockCache).put(targetType, null);
        inOrder.verifyNoMoreInteractions();
        assertNotNull(result);
        assertSame(expectedResult, result);
    }

    /**
     * Test {@link IdentityResolver#getResolver(Class)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testGetResolverConstructorFailSecurity()
    throws Throwable {
        final IdentityResolver instance = spy(createInstance());
        final Cache mockCache = mock(Cache.class);
        instance.setCache(mockCache);
        final Class<? extends Identity> targetType =
                TestTokenConstructorIdentity.class;
        final SecurityException expectedResult =
                new SecurityException();
        willReturn(false).given(mockCache).contains(targetType);
        willReturn(null).given(mockCache).get(targetType);
        willReturn(null).given(instance).findTokenResolverMethod(targetType);
        willThrow(expectedResult).given(instance).findTokenConstructor(targetType);
        final UnresolvableIdentityException result =
                assertThrows(UnresolvableIdentityException.class, () -> {
            instance.getResolver(targetType);
        });
        final InOrder inOrder = inOrder(instance, mockCache);
        inOrder.verify(instance).findTokenResolverMethod(targetType);
        inOrder.verify(instance).findTokenConstructor(targetType);
        inOrder.verify(mockCache).put(targetType, null);
        inOrder.verifyNoMoreInteractions();
        assertNotNull(result);
        assertNotNull(result.getCause());
        assertSame(expectedResult, result.getCause());
    }

    /**
     * Test {@link IdentityResolver#getResolver(Class)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testGetResolverConstructor()
    throws Throwable {
        final IdentityResolver instance = spy(createInstance());
        final Cache mockCache = mock(Cache.class);
        instance.setCache(mockCache);
        final Class<? extends Identity> targetType =
                TestTokenConstructorIdentity.class;
        final Constructor<?> expectedResult = TestTokenConstructorIdentity.class
                .getConstructor(String.class);
        willReturn(false).given(mockCache).contains(targetType);
        willReturn(null).given(mockCache).get(targetType);
        willReturn(null).given(instance).findTokenResolverMethod(targetType);
        willReturn(expectedResult).given(instance).findTokenConstructor(targetType);
        final Executable result = instance.getResolver(targetType);
        final InOrder inOrder = inOrder(instance, mockCache);
        inOrder.verify(instance).findTokenResolverMethod(targetType);
        inOrder.verify(instance).findTokenConstructor(targetType);
        inOrder.verify(mockCache).put(targetType, result);
        inOrder.verifyNoMoreInteractions();
        assertNotNull(result);
        assertSame(expectedResult, result);
    }

    /**
     * Test {@link IdentityResolver#getResolver(Class)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testGetResolverFail()
    throws Throwable {
        final IdentityResolver instance = spy(createInstance());
        final Cache mockCache = mock(Cache.class);
        instance.setCache(mockCache);
        final Class<? extends Identity> targetType =
                TestTokenConstructorIdentity.class;
        willReturn(false).given(mockCache).contains(targetType);
        willReturn(null).given(mockCache).get(targetType);
        willReturn(null).given(instance).findTokenResolverMethod(targetType);
        willReturn(null).given(instance).findTokenConstructor(targetType);
        assertThrows(UnresolvableIdentityException.class, () -> {
            instance.getResolver(targetType);
        });
        final InOrder inOrder = inOrder(instance, mockCache);
        inOrder.verify(instance).findTokenResolverMethod(targetType);
        inOrder.verify(instance).findTokenConstructor(targetType);
        inOrder.verify(mockCache).put(targetType, null);
        inOrder.verifyNoMoreInteractions();
    }

    /**
     * Test {@link IdentityResolver#findTokenResolverMethod(Class)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testFindTokenResolverMethodNullType()
    throws Throwable {
        final IdentityResolver instance = createInstance();
        assertThrows(NullPointerException.class, () -> {
            instance.findTokenResolverMethod(null);
        });
    }

    /**
     * Test {@link IdentityResolver#findTokenResolverMethod(Class)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testFindTokenResolverNotAnnotatedMethod()
    throws Throwable {
        final IdentityResolver instance = createInstance();
        assertNull(instance.findTokenResolverMethod(
                TestEmptyIdentity.class));
        assertNull(instance.findTokenResolverMethod(
                TestPrivateTokenConstructorIdentity.class));
        assertNull(instance.findTokenResolverMethod(
                TestTokenConstructorIdentity.class));
        assertNull(instance.findTokenResolverMethod(
                TestNoAnnotationTokenResolverIdentity.class));
    }

    /**
     * Test {@link IdentityResolver#findTokenResolverMethod(Class)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testFindTokenResolverNoStaticMethod()
    throws Throwable {
        final IdentityResolver instance = createInstance();
        assertThrows(UnresolvableIdentityException.class, () -> {
            instance.findTokenResolverMethod(
                    TestNoStaticTokenResolverIdentity.class);
        });
    }

    /**
     * Test {@link IdentityResolver#findTokenResolverMethod(Class)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testFindTokenResolverNoPublicMethod()
    throws Throwable {
        final IdentityResolver instance = createInstance();
        assertThrows(UnresolvableIdentityException.class, () -> {
            instance.findTokenResolverMethod(
                    TestNoPublicTokenResolverIdentity.class);
        });
    }

    /**
     * Test {@link IdentityResolver#findTokenResolverMethod(Class)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testFindTokenResolverWrongParamsMethod()
    throws Throwable {
        final IdentityResolver instance = createInstance();
        assertThrows(UnresolvableIdentityException.class, () -> {
            instance.findTokenResolverMethod(
                    TestWrongParamsTokenResolverIdentity.class);
        });
    }

    /**
     * Test {@link IdentityResolver#findTokenResolverMethod(Class)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testFindTokenResolverWrongReturnTypeMethod()
    throws Throwable {
        final IdentityResolver instance = createInstance();
        assertThrows(UnresolvableIdentityException.class, () -> {
            instance.findTokenResolverMethod(
                    TestWrongReturnTypeTokenResolverIdentity.class);
        });
    }

    /**
     * Test {@link IdentityResolver#findTokenResolverMethod(Class)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testFindTokenResolverMethod()
    throws Throwable {
        final IdentityResolver instance = createInstance();
        final Method expectedResult = TestTokenResolverIdentity.class
                .getMethod("resolve", String.class);
        final Method result = instance.findTokenResolverMethod(
                TestTokenResolverIdentity.class);
        assertNotNull(result);
        assertEquals(expectedResult, result);
    }

    /**
     * Test {@link IdentityResolver#findTokenConstructor(Class)}.
     */
    @Test
    public void testFindTokenConstructorNullType() {
        final IdentityResolver instance = createInstance();
        assertThrows(NullPointerException.class, () -> {
            instance.findTokenConstructor(null);
        });
    }

    /**
     * Test {@link IdentityResolver#findTokenConstructor(Class)}.
     */
    @Test
    public void testFindTokenConstructorNoConstructor() {
        final IdentityResolver instance = createInstance();
        assertThrows(UnresolvableIdentityException.class, () -> {
            instance.findTokenConstructor(TestEmptyIdentity.class);
        });
    }

    /**
     * Test {@link IdentityResolver#findTokenConstructor(Class)}.
     */
    @Test
    public void testFindTokenConstructorNoPublicConstructor() {
        final IdentityResolver instance = createInstance();
        assertThrows(UnresolvableIdentityException.class, () -> {
            instance.findTokenConstructor(TestPrivateTokenConstructorIdentity.class);
        });
    }

    /**
     * Test {@link IdentityResolver#findTokenConstructor(Class)}.
     * @throws Throwable Should not happen
     */
    @Test
    public void testFindTokenConstructor()
    throws Throwable {
        final IdentityResolver instance = createInstance();
        final Constructor<?> expectedResult = TestTokenConstructorIdentity.class
                .getConstructor(String.class);
        final Constructor<?> result = instance.findTokenConstructor(
                TestTokenConstructorIdentity.class);
        assertNotNull(result);
        assertEquals(expectedResult, result);
    }

    /**
     * Tests for {@link IdentityResolver.WeakHashMapCache}.
     */
    @Test
    public void testWeakHashMapCache() {
        final WeakHashMapCache cache = new WeakHashMapCache();
        final Class<? extends Identity> key = mock(Identity.class).getClass();
        assertFalse(cache.contains(key));
        assertNull(cache.get(key));
        final Executable annotations = mock(Executable.class);
        cache.put(key, annotations);
        assertTrue(cache.contains(key));
        assertSame(annotations, cache.get(key));
    }

    protected static class TestEmptyIdentity
    implements Identity {
        private static final long serialVersionUID = 1L;
        @Override
        public @NotBlank String getIdentityToken() {
            return null;
        }
    }

    protected static class TestPrivateTokenConstructorIdentity
    implements Identity {
        private static final long serialVersionUID = 1L;
        private TestPrivateTokenConstructorIdentity(
                final String token) {
            // NOP
        }
        @Override
        public @NotBlank String getIdentityToken() {
            return null;
        }
    }

    protected static class TestTokenConstructorIdentity
    implements Identity {
        private static final long serialVersionUID = 1L;
        private String constructorParam;
        public TestTokenConstructorIdentity(
                final String token) {
            this.constructorParam = token;
        }
        @Override
        public @NotBlank String getIdentityToken() {
            return null;
        }
    }

    protected static class TestTokenConstructorIdentityUnrecognized
    implements Identity {
        private static final long serialVersionUID = 1L;
        public TestTokenConstructorIdentityUnrecognized(
                final String token)
        throws UnrecognizedIdentityTokenException {
            throw new UnrecognizedIdentityTokenException(token);
        }
        @Override
        public @NotBlank String getIdentityToken() {
            return null;
        }
    }

    protected static class TestTokenConstructorIdentityIllegal
    implements Identity {
        private static final long serialVersionUID = 1L;
        public TestTokenConstructorIdentityIllegal(
                final String token) {
            throw new IllegalArgumentException(token);
        }
        @Override
        public @NotBlank String getIdentityToken() {
            return null;
        }
    }

    protected static abstract class TestTokenConstructorIdentityAbstract
    implements Identity {
        private static final long serialVersionUID = 1L;
        public TestTokenConstructorIdentityAbstract(
                final String token) {
            throw new IllegalArgumentException(token);
        }
        @Override
        public @NotBlank String getIdentityToken() {
            return null;
        }
    }

    protected static class TestNoAnnotationTokenResolverIdentity
    implements Identity {
        private static final long serialVersionUID = 1L;
        @Override
        public @NotBlank String getIdentityToken() {
            return null;
        }
        public static TestTokenResolverIdentity resolve(
                final String token) {
            return null;
        }
    }

    protected static class TestNoStaticTokenResolverIdentity
    implements Identity {
        private static final long serialVersionUID = 1L;
        @Override
        public @NotBlank String getIdentityToken() {
            return null;
        }
        @IdentityTokenResolver
        public TestTokenResolverIdentity resolve(
                final String token) {
            return null;
        }
    }

    protected static class TestNoPublicTokenResolverIdentity
    implements Identity {
        private static final long serialVersionUID = 1L;
        @Override
        public @NotBlank String getIdentityToken() {
            return null;
        }
        @IdentityTokenResolver
       private static TestTokenResolverIdentity resolve(
                final String token) {
            return null;
        }
    }

    protected static class TestWrongParamsTokenResolverIdentity
    implements Identity {
        private static final long serialVersionUID = 1L;
        @Override
        public @NotBlank String getIdentityToken() {
            return null;
        }
        @IdentityTokenResolver
        public static TestTokenResolverIdentity resolve(
                final String token,
                final String extra) {
            return null;
        }
    }

    protected static class TestWrongReturnTypeTokenResolverIdentity
    implements Identity {
        private static final long serialVersionUID = 1L;
        @Override
        public @NotBlank String getIdentityToken() {
            return null;
        }
        @IdentityTokenResolver
        public static Identity resolve(
                final String token) {
            return null;
        }
    }

    protected static class TestTokenResolverIdentity
    implements Identity {
        private static final long serialVersionUID = 1L;
        private String resolverParam;
        @Override
        public @NotBlank String getIdentityToken() {
            return null;
        }
        @IdentityTokenResolver
        public static TestTokenResolverIdentity resolve(
                final String token) {
            final TestTokenResolverIdentity result = new TestTokenResolverIdentity();
            result.resolverParam = token;
            return result;
        }
    }

    protected static class TestTokenResolverIdentityUnrecognized
    implements Identity {
        private static final long serialVersionUID = 1L;
        @Override
        public @NotBlank String getIdentityToken() {
            return null;
        }
        @IdentityTokenResolver
        public static TestTokenResolverIdentityUnrecognized resolve(
                final String token)
        throws UnrecognizedIdentityTokenException {
            throw new UnrecognizedIdentityTokenException(token);
        }
    }

    protected static class TestTokenResolverIdentityIllegal
    implements Identity {
        private static final long serialVersionUID = 1L;
        @Override
        public @NotBlank String getIdentityToken() {
            return null;
        }
        @IdentityTokenResolver
        public static TestTokenResolverIdentityUnrecognized resolve(
                final String token) {
            throw new IllegalArgumentException(token);
        }
    }
}
