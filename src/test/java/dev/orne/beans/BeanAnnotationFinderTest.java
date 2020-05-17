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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;

import dev.orne.beans.BeanAnnotationFinder.Cache;
import dev.orne.beans.BeanAnnotationFinder.CacheEntryKey;
import dev.orne.beans.BeanAnnotationFinder.WeakHashMapCache;

/**
 * Unit tests for {@code BeanAnnotationFinder}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see BeanAnnotationFinder
 */
@Tag("ut")
public class BeanAnnotationFinderTest {

    /**
     * Test {@link BeanAnnotationFinder#BeanAnnotationFinder(Class)}.
     */
    @Test
    public void testConstructorNullType() {
        assertThrows(NullPointerException.class, () -> {
            new BeanAnnotationFinder<>(null);
        });
    }

    /**
     * Test {@link BeanAnnotationFinder#BeanAnnotationFinder(Class)}.
     */
    @Test
    public void testConstructor() {
        final BeanAnnotationFinder<?, ?> instance =
                new BeanAnnotationFinder<>(TestAnnotation.class);
        assertSame(TestAnnotation.class, instance.getAnnotationType());
        assertNull(instance.getAnnotationListType());
        assertNull(instance.getExtractor());
        assertNotNull(instance.getCache());
        
        final BeanAnnotationFinder<?, ?> otherInstance =
                new BeanAnnotationFinder<>(TestAnnotation.class);
        assertNotNull(otherInstance.getCache());
        assertSame(instance.getCache(), otherInstance.getCache());
    }

    /**
     * Test {@link BeanAnnotationFinder#BeanAnnotationFinder(Class, Class, BeanAnnotationFinder.AnnotationListExtractor)}.
     */
    @Test
    public void testListConstructorNullType() {
        assertThrows(NullPointerException.class, () -> {
            new BeanAnnotationFinder<>(null,
                    TestAnnotation.List.class,
                    TestAnnotation.List::value);
        });
    }

    /**
     * Test {@link BeanAnnotationFinder#BeanAnnotationFinder(Class, Class, BeanAnnotationFinder.AnnotationListExtractor)}.
     */
    @Test
    public void testListConstructorNullListType() {
        final BeanAnnotationFinder<?, ?> instance =
                new BeanAnnotationFinder<>(
                    TestAnnotation.class,
                    null,
                    TestAnnotation.List::value);
        assertSame(TestAnnotation.class, instance.getAnnotationType());
        assertNull(instance.getAnnotationListType());
        assertNotNull(instance.getExtractor());
        assertNotNull(instance.getCache());
        
        final BeanAnnotationFinder<?, ?> otherInstance =
                new BeanAnnotationFinder<>(TestAnnotation.class);
        assertNotNull(otherInstance.getCache());
        assertSame(instance.getCache(), otherInstance.getCache());
    }

    /**
     * Test {@link BeanAnnotationFinder#BeanAnnotationFinder(Class, Class, BeanAnnotationFinder.AnnotationListExtractor)}.
     */
    @Test
    public void testListConstructorNullExtractor() {
        final BeanAnnotationFinder<?, ?> instance =
                new BeanAnnotationFinder<>(
                    TestAnnotation.class,
                    TestAnnotation.List.class,
                    null);
        assertSame(TestAnnotation.class, instance.getAnnotationType());
        assertSame(TestAnnotation.List.class, instance.getAnnotationListType());
        assertNull(instance.getExtractor());
        assertNotNull(instance.getCache());
        
        final BeanAnnotationFinder<?, ?> otherInstance =
                new BeanAnnotationFinder<>(TestAnnotation.class);
        assertNotNull(otherInstance.getCache());
        assertSame(instance.getCache(), otherInstance.getCache());
    }

    /**
     * Test {@link BeanAnnotationFinder#BeanAnnotationFinder(Class, Class, BeanAnnotationFinder.AnnotationListExtractor)}.
     */
    @Test
    public void testListConstructor() {
        final BeanAnnotationFinder<?, ?> instance =
                new BeanAnnotationFinder<>(
                    TestAnnotation.class,
                    TestAnnotation.List.class,
                    TestAnnotation.List::value);
        assertSame(TestAnnotation.class, instance.getAnnotationType());
        assertSame(TestAnnotation.List.class, instance.getAnnotationListType());
        assertNotNull(instance.getExtractor());
        assertNotNull(instance.getCache());
        
        final BeanAnnotationFinder<?, ?> otherInstance =
                new BeanAnnotationFinder<>(TestAnnotation.class);
        assertNotNull(otherInstance.getCache());
        assertSame(instance.getCache(), otherInstance.getCache());
    }

    /**
     * Creates a new {@code BeanAnnotationFinder} instance without list
     * annotation support.
     * 
     * @return The created instance
     */
    protected BeanAnnotationFinder<TestAnnotation, ?> createInstance() {
        return new BeanAnnotationFinder<>(TestAnnotation.class);
    }

    /**
     * Creates a new {@code BeanAnnotationFinder} instance without list
     * annotation support.
     * 
     * @return The created instance
     */
    protected BeanAnnotationFinder<TestAnnotation, ?> createInstance(
            @Nullable
            final Cache cache) {
        return new BeanAnnotationFinder<>(TestAnnotation.class)
                .setCache(cache);
    }

    /**
     * Creates a new {@code BeanAnnotationFinder} instance with list
     * annotation support.
     * 
     * @return The created instance
     */
    protected BeanAnnotationFinder<TestAnnotation, TestAnnotation.List> createListInstance() {
        return new BeanAnnotationFinder<>(
                TestAnnotation.class,
                TestAnnotation.List.class,
                TestAnnotation.List::value);
    }

    /**
     * Creates a new {@code BeanAnnotationFinder} instance with list
     * annotation support.
     * 
     * @param cache The cache to use
     * @return The created instance
     */
    protected BeanAnnotationFinder<TestAnnotation, TestAnnotation.List> createListInstance(
            @Nullable
            final Cache cache) {
        return new BeanAnnotationFinder<>(
                    TestAnnotation.class,
                    TestAnnotation.List.class,
                    TestAnnotation.List::value)
                .setCache(cache);
    }

    /**
     * Test {@link BeanAnnotationFinder#setCache(BeanAnnotationFinder.Cache)}.
     */
    @Test
    public void testSetCache() {
        final BeanAnnotationFinder<?, ?> instance = createInstance();
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
     * Test {@link BeanAnnotationFinder#find(Class)}.
     */
    @Test
    public void testFindNullType() {
        final BeanAnnotationFinder<TestAnnotation, ?> instance = spy(createInstance());
        assertThrows(NullPointerException.class, () -> {
            instance.find(null);
        });
        then(instance).should(times(1)).find(any());
        then(instance).shouldHaveNoMoreInteractions();
    }

    /**
     * Test {@link BeanAnnotationFinder#find(Class)}.
     */
    @Test
    public void testFind() {
        final BeanAnnotationFinder<TestAnnotation, ?> instance = spy(createInstance());
        @SuppressWarnings("unchecked")
        final Set<TestAnnotation> mockResult = mock(Set.class);
        @SuppressWarnings("unchecked")
        final ArgumentCaptor<Set<Class<?>>> visited = ArgumentCaptor.forClass(Set.class);
        willReturn(mockResult).given(instance).findAnnotations(
                same(TestBean.class),
                visited.capture());
        final Set<TestAnnotation> result = instance.find(TestBean.class);
        assertNotNull(result);
        assertSame(mockResult, result);
        then(instance).should(times(1)).findAnnotations(same(TestBean.class), any());
        assertNotNull(visited.getValue());
        assertTrue(visited.getValue().isEmpty());
    }

    /**
     * Test {@link BeanAnnotationFinder#findAnnotations(Class, Set)}.
     */
    @Test
    public void testFindAnnotationsAlreadyVisited() {
        final Cache mockCache = mock(Cache.class);
        final BeanAnnotationFinder<TestAnnotation, ?> instance =
                spy(createInstance(mockCache));
        @SuppressWarnings("unchecked")
        final Set<Class<?>> visited = mock(Set.class);
        final Set<TestAnnotation> cacheVale = new HashSet<>();
        willReturn(true).given(visited).contains(TestBean.class);
        final Set<TestAnnotation> result = instance.findAnnotations(TestBean.class, visited);
        assertEquals(cacheVale, result);
        then(instance).should().findAnnotations(TestBean.class, visited);
        then(visited).should(atLeastOnce()).contains(TestBean.class);
        then(mockCache).shouldHaveNoInteractions();
        then(instance).shouldHaveNoMoreInteractions();
    }

    /**
     * Test {@link BeanAnnotationFinder#findAnnotations(Class, Set)}.
     */
    @Test
    public void testFindAnnotationsInCache() {
        final Cache mockCache = mock(Cache.class);
        final BeanAnnotationFinder<TestAnnotation, ?> instance =
                spy(createInstance(mockCache));
        final CacheEntryKey<TestAnnotation> cacheKey = new CacheEntryKey<>(
                TestBean.class, TestAnnotation.class);
        final Set<TestAnnotation> cacheValue = new HashSet<>();
        cacheValue.add(mock(TestAnnotation.class));
        cacheValue.add(mock(TestAnnotation.class));
        @SuppressWarnings("unchecked")
        final Set<Class<?>> visited = mock(Set.class);
        willReturn(true).given(mockCache).contains(eq(cacheKey));
        willReturn(cacheValue).given(mockCache).get(eq(cacheKey));
        final Set<TestAnnotation> result = instance.findAnnotations(TestBean.class, visited);
        assertEquals(cacheValue, result);
        then(instance).should().findAnnotations(TestBean.class, visited);
        then(visited).should(atLeastOnce()).contains(TestBean.class);
        then(mockCache).should(never()).put(any(), any());
        then(instance).shouldHaveNoMoreInteractions();
    }

    /**
     * Test {@link BeanAnnotationFinder#findAnnotations(Class, Set)}.
     */
    @Test
    public void testFindAnnotationsNoCache() {
        final Cache mockCache = mock(Cache.class);
        final BeanAnnotationFinder<TestAnnotation, ?> instance =
                spy(createInstance(mockCache));
        @SuppressWarnings("unchecked")
        final Set<Class<?>> visited = mock(Set.class);
        final CacheEntryKey<TestAnnotation> cacheKey = new CacheEntryKey<>(
                TestBean.class, TestAnnotation.class);
        @SuppressWarnings("unchecked")
        final ArgumentCaptor<Set<TestAnnotation>> cacheValue =
                ArgumentCaptor.forClass(Set.class);
        final Set<TestAnnotation> mockResult = new HashSet<>();
        mockResult.add(mock(TestAnnotation.class));
        mockResult.add(mock(TestAnnotation.class));
        willReturn(mockResult).given(instance).findAllAnnotations(TestBean.class);
        willReturn(false).given(mockCache).contains(eq(cacheKey));
        willReturn(null).given(mockCache).get(eq(cacheKey));
        willDoNothing().given(mockCache).put(eq(cacheKey), cacheValue.capture());
        final Set<TestAnnotation> result = instance.findAnnotations(TestBean.class, visited);
        assertEquals(mockResult, result);
        then(instance).should().findAnnotations(TestBean.class, visited);
        then(instance).should().findAllAnnotations(TestBean.class);
        then(mockCache).should().put(eq(cacheKey), any());
        assertNotNull(cacheValue.getValue());
        assertSame(mockResult, cacheValue.getValue());
    }

    /**
     * Test {@link BeanAnnotationFinder#findAllAnnotations(Class)}.
     */
    @Test
    public void testFindAllAnnotations() {
        final Cache mockCache = mock(Cache.class);
        final BeanAnnotationFinder<TestAnnotation, ?> instance =
                spy(createInstance(mockCache));
        @SuppressWarnings("unchecked")
        final ArgumentCaptor<Set<TestAnnotation>> setDirect =
                ArgumentCaptor.forClass(Set.class);
        @SuppressWarnings("unchecked")
        final ArgumentCaptor<Set<TestAnnotation>> setList =
                ArgumentCaptor.forClass(Set.class);
        @SuppressWarnings("unchecked")
        final ArgumentCaptor<Set<TestAnnotation>> setSuper =
                ArgumentCaptor.forClass(Set.class);
        @SuppressWarnings("unchecked")
        final ArgumentCaptor<Set<TestAnnotation>> setIfaces =
                ArgumentCaptor.forClass(Set.class);
        @SuppressWarnings("unchecked")
        final ArgumentCaptor<Set<Class<?>>> visitedSuper =
                ArgumentCaptor.forClass(Set.class);
        @SuppressWarnings("unchecked")
        final ArgumentCaptor<Set<Class<?>>> visitedIfaces =
                ArgumentCaptor.forClass(Set.class);
        willDoNothing().given(instance).addDirectAnnotation(
                same(TestBean.class), setDirect.capture());
        willDoNothing().given(instance).addDirectAnnotationsList(
                same(TestBean.class), setList.capture());
        willDoNothing().given(instance).addSuperclassAnnotations(
                same(TestBean.class), setSuper.capture(), visitedSuper.capture());
        willDoNothing().given(instance).addInterfacesAnnotations(
                same(TestBean.class), setIfaces.capture(), visitedIfaces.capture());
        final Set<TestAnnotation> result = instance.findAllAnnotations(TestBean.class);
        then(instance).should().addDirectAnnotation(same(TestBean.class), any());
        then(instance).should().addDirectAnnotationsList(same(TestBean.class), any());
        then(instance).should().addSuperclassAnnotations(same(TestBean.class), any(), any());
        then(instance).should().addInterfacesAnnotations(same(TestBean.class), any(), any());
        then(mockCache).shouldHaveNoInteractions();
        assertNotNull(setDirect.getValue());
        assertNotNull(setList.getValue());
        assertSame(setDirect.getValue(), setList.getValue());
        assertNotNull(setSuper.getValue());
        assertSame(setDirect.getValue(), setSuper.getValue());
        assertNotNull(setIfaces.getValue());
        assertSame(setDirect.getValue(), setIfaces.getValue());
        assertNotNull(visitedSuper.getValue());
        assertTrue(visitedSuper.getValue().contains(TestBean.class));
        assertNotNull(visitedIfaces.getValue());
        assertSame(visitedSuper.getValue(), visitedIfaces.getValue());
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Test {@link BeanAnnotationFinder#findAllAnnotations(Class)}.
     */
    @Test
    public void testFindAllAnnotationsAggregate() {
        final Cache mockCache = mock(Cache.class);
        final BeanAnnotationFinder<TestAnnotation, ?> instance =
                spy(createInstance(mockCache));
        will((InvocationOnMock invocation) -> {
            @SuppressWarnings("unchecked")
            final Set<TestAnnotation> result =  invocation.getArgument(1, Set.class);
            result.add(mock(TestAnnotation.class));
            return null;
        }).given(instance).addDirectAnnotation(any(), any());
        will((InvocationOnMock invocation) -> {
            @SuppressWarnings("unchecked")
            final Set<TestAnnotation> result =  invocation.getArgument(1, Set.class);
            result.add(mock(TestAnnotation.class));
            result.add(mock(TestAnnotation.class));
            return null;
        }).given(instance).addDirectAnnotationsList(any(), any());
        will((InvocationOnMock invocation) -> {
            @SuppressWarnings("unchecked")
            final Set<TestAnnotation> result =  invocation.getArgument(1, Set.class);
            result.add(mock(TestAnnotation.class));
            result.add(mock(TestAnnotation.class));
            result.add(mock(TestAnnotation.class));
            return null;
        }).given(instance).addSuperclassAnnotations(any(), any(), any());
        will((InvocationOnMock invocation) -> {
            @SuppressWarnings("unchecked")
            final Set<TestAnnotation> result =  invocation.getArgument(1, Set.class);
            result.add(mock(TestAnnotation.class));
            result.add(mock(TestAnnotation.class));
            result.add(mock(TestAnnotation.class));
            result.add(mock(TestAnnotation.class));
            return null;
        }).given(instance).addInterfacesAnnotations(any(), any(), any());
        final Set<TestAnnotation> result = instance.findAllAnnotations(TestBean.class);
        then(instance).should().addDirectAnnotation(same(TestBean.class), any());
        then(instance).should().addDirectAnnotationsList(same(TestBean.class), any());
        then(instance).should().addSuperclassAnnotations(same(TestBean.class), any(), any());
        then(instance).should().addInterfacesAnnotations(same(TestBean.class), any(), any());
        then(mockCache).shouldHaveNoInteractions();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(10, result.size());
    }

    /**
     * Test {@link BeanAnnotationFinder#addDirectAnnotation(Class, Set)}.
     */
    @Test
    public void testAddDirectAnnotation() {
        final Cache mockCache = mock(Cache.class);
        final BeanAnnotationFinder<TestAnnotation, ?> instance =
                spy(createInstance(mockCache));
        final Set<TestAnnotation> annotations = new HashSet<>();
        instance.addDirectAnnotation(TestBean.class, annotations);
        assertFalse(annotations.isEmpty());
        final Set<String> annotationTags = new HashSet<String>(
                annotations.size());
        for (final TestAnnotation annotation : annotations) {
            annotationTags.add(annotation.value());
        }
        assertTrue(annotationTags.contains("TestBean"));
        then(mockCache).shouldHaveNoInteractions();
    }

    /**
     * Test {@link BeanAnnotationFinder#addDirectAnnotationsList(Class, Set)}.
     */
    @Test
    public void testAddDirectAnnotationsList() {
        final Cache mockCache = mock(Cache.class);
        final BeanAnnotationFinder<TestAnnotation, ?> instance =
                spy(createListInstance(mockCache));
        final Set<TestAnnotation> annotations = new HashSet<>();
        instance.addDirectAnnotationsList(TestBean.class, annotations);
        assertFalse(annotations.isEmpty());
        final Set<String> annotationTags = new HashSet<String>(
                annotations.size());
        for (final TestAnnotation annotation : annotations) {
            annotationTags.add(annotation.value());
        }
        assertTrue(annotationTags.contains("TestBeanList1"));
        assertTrue(annotationTags.contains("TestBeanList2"));
        then(mockCache).shouldHaveNoInteractions();
    }

    /**
     * Test {@link BeanAnnotationFinder#addSuperclassAnnotations(Class, Set, Set)}.
     */
    @Test
    public void testAddSuperAnnotations() {
        final Cache mockCache = mock(Cache.class);
        final BeanAnnotationFinder<TestAnnotation, ?> instance =
                spy(createInstance(mockCache));
        @SuppressWarnings("unchecked")
        final Set<TestAnnotation> annotations = mock(Set.class);
        @SuppressWarnings("unchecked")
        final Set<Class<?>> visited = mock(Set.class);
        @SuppressWarnings("unchecked")
        final Set<TestAnnotation> mockParentResult = mock(Set.class);
        willReturn(mockParentResult).given(instance).findAnnotations(TestParentBean.class, visited);
        instance.addSuperclassAnnotations(TestBean.class, annotations, visited);
        then(instance).should().findAnnotations(TestParentBean.class, visited);
        then(annotations).should().addAll(mockParentResult);
        then(annotations).shouldHaveNoMoreInteractions();
        then(mockCache).shouldHaveNoInteractions();
    }

    /**
     * Test {@link BeanAnnotationFinder#addSuperclassAnnotations(Class, Set, Set)}.
     */
    @Test
    public void testAddSuperAnnotationsNoSuper() {
        final Cache mockCache = mock(Cache.class);
        final BeanAnnotationFinder<TestAnnotation, ?> instance =
                spy(createInstance(mockCache));
        @SuppressWarnings("unchecked")
        final Set<TestAnnotation> annotations = mock(Set.class);
        @SuppressWarnings("unchecked")
        final Set<Class<?>> visited = mock(Set.class);
        instance.addSuperclassAnnotations(Object.class, annotations, visited);
        then(instance).should().addSuperclassAnnotations(Object.class, annotations, visited);
        then(instance).shouldHaveNoMoreInteractions();
        then(mockCache).shouldHaveNoInteractions();
        then(annotations).shouldHaveNoInteractions();
        then(visited).shouldHaveNoInteractions();
    }

    /**
     * Test {@link BeanAnnotationFinder#addInterfacesAnnotations(Class, Set, Set)}.
     */
    @Test
    public void testAddInterfacesAnnotations() {
        final Cache mockCache = mock(Cache.class);
        final BeanAnnotationFinder<TestAnnotation, ?> instance =
                spy(createInstance(mockCache));
        @SuppressWarnings("unchecked")
        final Set<TestAnnotation> annotations = mock(Set.class);
        @SuppressWarnings("unchecked")
        final Set<Class<?>> visited = mock(Set.class);
        @SuppressWarnings("unchecked")
        final Set<TestAnnotation> mockIfaceResult1 = mock(Set.class);
        willReturn(mockIfaceResult1).given(instance).findAnnotations(TestInterface.class, visited);
        @SuppressWarnings("unchecked")
        final Set<TestAnnotation> mockIfaceResult2 = mock(Set.class);
        willReturn(mockIfaceResult2).given(instance).findAnnotations(TestDuplicatedInterface.class, visited);
        instance.addInterfacesAnnotations(TestBean.class, annotations, visited);
        then(instance).should().findAnnotations(TestInterface.class, visited);
        then(instance).should().findAnnotations(TestDuplicatedInterface.class, visited);
        then(annotations).should().addAll(mockIfaceResult1);
        then(annotations).should().addAll(mockIfaceResult2);
        then(annotations).shouldHaveNoMoreInteractions();
        then(mockCache).shouldHaveNoInteractions();
    }

    /**
     * Test {@link BeanAnnotationFinder#addInterfacesAnnotations(Class, Set, Set)}.
     */
    @Test
    public void testAddInterfacesAnnotationsExtends() {
        final Cache mockCache = mock(Cache.class);
        final BeanAnnotationFinder<TestAnnotation, ?> instance =
                spy(createInstance(mockCache));
        @SuppressWarnings("unchecked")
        final Set<TestAnnotation> annotations = mock(Set.class);
        @SuppressWarnings("unchecked")
        final Set<Class<?>> visited = mock(Set.class);
        @SuppressWarnings("unchecked")
        final Set<TestAnnotation> mockParentResult = mock(Set.class);
        willReturn(mockParentResult).given(instance).findAnnotations(TestParentInterface.class, visited);
        instance.addInterfacesAnnotations(TestInterface.class, annotations, visited);
        then(instance).should().findAnnotations(TestParentInterface.class, visited);
        then(annotations).should().addAll(mockParentResult);
        then(annotations).shouldHaveNoMoreInteractions();
        then(mockCache).shouldHaveNoInteractions();
    }

    /**
     * Test {@link BeanAnnotationFinder#addInterfacesAnnotations(Class, Set, Set)}.
     */
    @Test
    public void testAddInterfacesAnnotationsExtendsMultiple() {
        final Cache mockCache = mock(Cache.class);
        final BeanAnnotationFinder<TestAnnotation, ?> instance =
                spy(createInstance(mockCache));
        @SuppressWarnings("unchecked")
        final Set<TestAnnotation> annotations = mock(Set.class);
        @SuppressWarnings("unchecked")
        final Set<Class<?>> visited = mock(Set.class);
        @SuppressWarnings("unchecked")
        final Set<TestAnnotation> mockParentResult1 = mock(Set.class);
        willReturn(mockParentResult1).given(instance).findAnnotations(TestParentInterface.class, visited);
        @SuppressWarnings("unchecked")
        final Set<TestAnnotation> mockParentResult2 = mock(Set.class);
        willReturn(mockParentResult2).given(instance).findAnnotations(TestDuplicatedInterface.class, visited);
        instance.addInterfacesAnnotations(TestMultipleInterface.class, annotations, visited);
        then(instance).should().findAnnotations(TestParentInterface.class, visited);
        then(instance).should().findAnnotations(TestDuplicatedInterface.class, visited);
        then(annotations).should().addAll(mockParentResult1);
        then(annotations).should().addAll(mockParentResult2);
        then(annotations).shouldHaveNoMoreInteractions();
        then(mockCache).shouldHaveNoInteractions();
    }

    /**
     * Tests for {@link BeanAnnotationFinder.CacheEntryKey}.
     */
    @Test
    public void testCacheEntryKey() {
        final CacheEntryKey<TestAnnotation> key =
                new CacheEntryKey<>(TestBean.class, TestAnnotation.class);
        final Object differentClass = new Object();
        final CacheEntryKey<TestAnnotation> equalKey =
                new CacheEntryKey<>(TestBean.class, TestAnnotation.class);
        final CacheEntryKey<TestAnnotation> differentType =
                new CacheEntryKey<>(TestParentBean.class, TestAnnotation.class);
        final CacheEntryKey<NotNull> differentAnnotationType =
                new CacheEntryKey<>(TestParentBean.class, NotNull.class);
        assertSame(TestBean.class, key.getType());
        assertSame(TestAnnotation.class, key.getAnnotationType());
        assertNotNull(key.toString());
        assertFalse(key.equals(null));
        assertFalse(key.equals(differentClass));
        assertNotEquals(key.hashCode(), differentClass.hashCode());
        assertFalse(key.equals(differentType));
        assertNotEquals(key.hashCode(), differentType.hashCode());
        assertFalse(key.equals(differentAnnotationType));
        assertNotEquals(key.hashCode(), differentAnnotationType.hashCode());
        assertTrue(key.equals(key));
        assertEquals(key.hashCode(), key.hashCode());
        assertTrue(key.equals(equalKey));
        assertEquals(key.hashCode(), equalKey.hashCode());
    }

    /**
     * Tests for {@link BeanAnnotationFinder.WeakHashMapCache}.
     */
    @Test
    public void testWeakHashMapCache() {
        final WeakHashMapCache cache = new WeakHashMapCache();
        final CacheEntryKey<TestAnnotation> key =
                new CacheEntryKey<>(TestBean.class, TestAnnotation.class);
        assertFalse(cache.contains(key));
        assertNull(cache.get(key));
        @SuppressWarnings("unchecked")
        final Set<TestAnnotation> annotations = mock(Set.class);
        cache.put(key, annotations);
        assertTrue(cache.contains(key));
        assertSame(annotations, cache.get(key));
    }

    @NotNull
    @TestAnnotation("TestBean")
    @TestAnnotation.List({
        @TestAnnotation("TestBeanList1"),
        @TestAnnotation("TestBeanList2")
    })
    protected static class TestBean
    extends TestParentBean
    implements TestInterface, TestDuplicatedInterface {
        // No extra methods
    }

    @TestAnnotation("TestParentBean")
    @TestAnnotation.List({
        @TestAnnotation("TestBeanList1"),
        @TestAnnotation("TestBeanList2")
    })
    protected static class TestParentBean
    implements TestDuplicatedInterface {
        // No extra methods
    }

    @TestAnnotation("TestInterface")
    @TestAnnotation.List({
        @TestAnnotation("TestInterfaceList1"),
        @TestAnnotation("TestInterfaceList2")
    })
    protected static interface TestInterface
    extends TestParentInterface {
        // No extra methods
    }

    @TestAnnotation("TestMultipleInterface")
    @TestAnnotation.List({
        @TestAnnotation("TestMultipleInterfaceList1"),
        @TestAnnotation("TestMultipleInterfaceList2")
    })
    protected static interface TestMultipleInterface
    extends TestParentInterface, TestDuplicatedInterface {
        // No extra methods
    }

    @TestAnnotation("TestParentInterface")
    @TestAnnotation.List({
        @TestAnnotation("TestParentInterfaceList1"),
        @TestAnnotation("TestParentInterfaceList2")
    })
    protected static interface TestParentInterface {
        // No extra methods
    }

    @TestAnnotation("TestDuplicatedInterface")
    @TestAnnotation.List({
        @TestAnnotation("TestDuplicatedInterfaceList1"),
        @TestAnnotation("TestDuplicatedInterfaceList2")
    })
    protected static interface TestDuplicatedInterface {
        // No extra methods
    }

    /**
     * Test annotation.
     */
    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TestAnnotation {
        /** Test value. */
        String value();
        /**
         * Test annotation.
         */
        @Target({ ElementType.TYPE })
        @Retention(RetentionPolicy.RUNTIME)
        public @interface List {
            /** Test nested annotations. */
            TestAnnotation[] value();
        }
    }
}
