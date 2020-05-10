package dev.orne.beans;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.beans.BeanAnnotationFinder.Cache;
import dev.orne.beans.BeanAnnotationFinder.CacheEntryKey;

/**
 * Integration tests for {@code BeanAnnotationFinder}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see BeanAnnotationFinder
 */
@Tag("it")
public class BeanAnnotationFinderIT {

    /**
     * Test {@link BeanAnnotationFinder#find(Class)}.
     */
    @Test
    public void testFindInDepth() {
        final HashMap<CacheEntryKey<?>, Set<?>> cacheMap = new HashMap<>();
        final TestCache testCache = new TestCache(cacheMap);
        final BeanAnnotationFinder<TestAnnotation, ?> finder =
                new BeanAnnotationFinder<>(
                    TestAnnotation.class)
                .setCache(testCache);
        final Set<TestAnnotation> annotations = finder.find(TestBean.class);
        final Set<String> annotationTags = new HashSet<String>(
                annotations.size());
        for (final TestAnnotation annotation : annotations) {
            annotationTags.add(annotation.value());
        }
        assertEquals(6, annotationTags.size());
        assertTrue(annotationTags.contains("TestBean"));
        assertTrue(annotationTags.contains("TestParentBean"));
        assertTrue(annotationTags.contains("TestInterface"));
        assertTrue(annotationTags.contains("TestParentInterface"));
        assertTrue(annotationTags.contains("TestNestedInterface"));
        assertTrue(annotationTags.contains("TestDuplicatedInterface"));
    }

    /**
     * Test {@link BeanAnnotationFinder#find(Class)}.
     */
    @Test
    public void testFindInDepthWithList() {
        final HashMap<CacheEntryKey<?>, Set<?>> cacheMap = new HashMap<>();
        final TestCache testCache = new TestCache(cacheMap);
        final BeanAnnotationFinder<TestAnnotation, TestAnnotation.List> finder =
                new BeanAnnotationFinder<>(
                    TestAnnotation.class,
                    TestAnnotation.List.class,
                    TestAnnotation.List::value)
                .setCache(testCache);
        final Set<TestAnnotation> annotations = finder.find(TestBean.class);
        final Set<String> annotationTags = new HashSet<String>(
                annotations.size());
        for (final TestAnnotation annotation : annotations) {
            annotationTags.add(annotation.value());
        }
        assertEquals(18, annotationTags.size());
        assertTrue(annotationTags.contains("TestBean"));
        assertTrue(annotationTags.contains("TestBeanList1"));
        assertTrue(annotationTags.contains("TestBeanList2"));
        assertTrue(annotationTags.contains("TestParentBean"));
        assertTrue(annotationTags.contains("TestParentBeanList1"));
        assertTrue(annotationTags.contains("TestParentBeanList2"));
        assertTrue(annotationTags.contains("TestInterface"));
        assertTrue(annotationTags.contains("TestInterfaceList1"));
        assertTrue(annotationTags.contains("TestInterfaceList2"));
        assertTrue(annotationTags.contains("TestParentInterface"));
        assertTrue(annotationTags.contains("TestParentInterfaceList1"));
        assertTrue(annotationTags.contains("TestParentInterfaceList2"));
        assertTrue(annotationTags.contains("TestNestedInterface"));
        assertTrue(annotationTags.contains("TestNestedInterfaceList1"));
        assertTrue(annotationTags.contains("TestNestedInterfaceList2"));
        assertTrue(annotationTags.contains("TestDuplicatedInterface"));
        assertTrue(annotationTags.contains("TestDuplicatedInterfaceList1"));
        assertTrue(annotationTags.contains("TestDuplicatedInterfaceList2"));
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
        @TestAnnotation("TestParentBeanList1"),
        @TestAnnotation("TestParentBeanList2")
    })
    protected static class TestParentBean
    implements TestNestedInterface, TestDuplicatedInterface {
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

    @TestAnnotation("TestParentInterface")
    @TestAnnotation.List({
        @TestAnnotation("TestParentInterfaceList1"),
        @TestAnnotation("TestParentInterfaceList2")
    })
    protected static interface TestParentInterface {
        // No extra methods
    }

    @TestAnnotation("TestNestedInterface")
    @TestAnnotation.List({
        @TestAnnotation("TestNestedInterfaceList1"),
        @TestAnnotation("TestNestedInterfaceList2")
    })
    protected static interface TestNestedInterface {
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

    private static class TestCache
    implements Cache {
        private final HashMap<CacheEntryKey<?>, Set<?>> map;
        public TestCache(
                final HashMap<CacheEntryKey<?>, Set<?>> map) {
            this.map = map;
        }
        @Override
        public <T extends Annotation> void put(
                final CacheEntryKey<T> key,
                final Set<T> value) {
            this.map.put(key, value);
        }
        @Override
        @SuppressWarnings("unchecked")
        public <T extends Annotation> Set<T> get(
                final CacheEntryKey<T> key) {
            return (Set<T>) this.map.get(key);
        }
        
        @Override
        public boolean contains(
                final CacheEntryKey<?> key) {
            return this.map.containsKey(key);
        }
    }
}
