package dev.orne.beans;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * Unit tests for {@code JacksonSpiTypeIdResolver}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2023-12
 * @since 0.6
 * @see JacksonSpiTypeIdResolver
 */
@Tag("ut")
class JacksonSpiTypeIdResolverTest {

    /**
     * Test for {@link JacksonSpiTypeIdResolver#JacksonSpiTypeIdResolver()}.
     */
    @Test
    void testConstructor() {
        final JacksonSpiTypeIdResolver resolver = new JacksonSpiTypeIdResolver();
        assertNotNull(resolver.getMechanism());
        assertEquals(JsonTypeInfo.Id.NAME, resolver.getMechanism());
        assertNotNull(resolver.getSubtypes());
        assertTrue(resolver.getSubtypes().isEmpty());
    }

    /**
     * Test for {@link JacksonSpiTypeIdResolver#getIdFromAnnotation(Class)}.
     */
    @Test
    void testgetIdFromAnnotation() {
        final JacksonSpiTypeIdResolver resolver = new JacksonSpiTypeIdResolver();
        assertThrows(NullPointerException.class, () -> {
            resolver.getIdFromAnnotation(null);
        });
        assertNull(resolver.getIdFromAnnotation(AnnotatedTypeImpl.class));
        assertEquals("DERIVED", resolver.getIdFromAnnotation(DerivedTypeImpl.class));
        assertNull(resolver.getIdFromAnnotation(UnnamedDerivedTypeImpl.class));
    }

    /**
     * Test for {@link JacksonSpiTypeIdResolver#defaultTypeId(Class)}.
     */
    @Test
    void testDefaultTypeId_Class() {
        assertThrows(NullPointerException.class, () -> {
            JacksonSpiTypeIdResolver.defaultTypeId((Class<?>) null);
        });
        assertEquals(
                "JacksonSpiTypeIdResolverTest",
                JacksonSpiTypeIdResolver.defaultTypeId(JacksonSpiTypeIdResolverTest.class));
        assertEquals(
                "JacksonSpiTypeIdResolverTest$AnnotatedTypeImpl",
                JacksonSpiTypeIdResolver.defaultTypeId(AnnotatedTypeImpl.class));
    }

    /**
     * Test for {@link JacksonSpiTypeIdResolver#defaultTypeId(String)}.
     */
    @Test
    void testDefaultTypeId_String() {
        assertThrows(NullPointerException.class, () -> {
            JacksonSpiTypeIdResolver.defaultTypeId((String) null);
        });
        assertEquals(
                "DefaultPackageType",
                JacksonSpiTypeIdResolver.defaultTypeId(
                    "DefaultPackageType"));
        assertEquals(
                "JacksonSpiTypeIdResolverTest",
                JacksonSpiTypeIdResolver.defaultTypeId(
                    "dev.orne.beans.JacksonSpiTypeIdResolverTest"));
        assertEquals(
                "JacksonSpiTypeIdResolverTest$AnnotatedTypeImpl",
                JacksonSpiTypeIdResolver.defaultTypeId(
                    "dev.orne.beans.JacksonSpiTypeIdResolverTest$AnnotatedTypeImpl"));
    }

    /**
     * Test for {@link JacksonSpiTypeIdResolver#getIdFromBean(Object)}.
     */
    @Test
    void testgetIdFromBean() {
        final JacksonSpiTypeIdResolver resolver = new JacksonSpiTypeIdResolver();
        assertThrows(NullPointerException.class, () -> {
            resolver.getIdFromBean(null);
        });
        assertEquals(
                JacksonSpiTypeIdResolver.defaultTypeId(AnnotatedTypeImpl.class),
                resolver.getIdFromBean(new AnnotatedTypeImpl()));
        assertEquals(
                "DERIVED",
                resolver.getIdFromBean(new DerivedTypeImpl()));
        assertEquals(
                JacksonSpiTypeIdResolver.defaultTypeId(UnnamedDerivedTypeImpl.class),
                resolver.getIdFromBean(new UnnamedDerivedTypeImpl()));
    }

    /**
     * Test for {@link JacksonSpiTypeIdResolver#idFromValue(Object)}.
     */
    @Test
    void testgetIdFromValue() {
        final JacksonSpiTypeIdResolver resolver = new JacksonSpiTypeIdResolver();
        assertThrows(NullPointerException.class, () -> {
            resolver.idFromValue(null);
        });
        assertEquals(
                resolver.getIdFromBean(new AnnotatedTypeImpl()),
                resolver.idFromValue(new AnnotatedTypeImpl()));
        assertEquals(
                resolver.getIdFromBean(new DerivedTypeImpl()),
                resolver.idFromValue(new DerivedTypeImpl()));
        assertEquals(
                resolver.getIdFromBean(new UnnamedDerivedTypeImpl()),
                resolver.idFromValue(new UnnamedDerivedTypeImpl()));
    }

    /**
     * Test for {@link JacksonSpiTypeIdResolver#idFromValueAndType(Object, Class)}.
     */
    @Test
    void testgetIdFromValueAndType() {
        final JacksonSpiTypeIdResolver resolver = new JacksonSpiTypeIdResolver();
        assertThrows(NullPointerException.class, () -> {
            resolver.idFromValueAndType(null, null);
        });
        assertEquals(
                resolver.idFromValue(new DerivedTypeImpl()),
                resolver.idFromValueAndType(new DerivedTypeImpl(), null));
        assertEquals(
                resolver.getIdFromAnnotation(DerivedTypeImpl.class),
                resolver.idFromValueAndType(null, DerivedTypeImpl.class));
        assertEquals(
                resolver.idFromValue(new AnnotatedTypeImpl()),
                resolver.idFromValueAndType(new AnnotatedTypeImpl(), AnnotatedTypeImpl.class));
        assertEquals(
                resolver.idFromValue(new DerivedTypeImpl()),
                resolver.idFromValueAndType(new DerivedTypeImpl(), DerivedTypeImpl.class));
        assertEquals(
                resolver.idFromValue(new UnnamedDerivedTypeImpl()),
                resolver.idFromValueAndType(new UnnamedDerivedTypeImpl(), UnnamedDerivedTypeImpl.class));
    }

    /**
     * Test for {@link JacksonSpiTypeIdResolver#init(JavaType)}.
     */
    @Test
    void testInit_Base() {
        final JacksonSpiTypeIdResolver resolver = new JacksonSpiTypeIdResolver();
        final JavaType type = TypeFactory.defaultInstance().constructType(AnnotatedType.class);
        resolver.init(type);
        assertFalse(resolver.getSubtypes().isEmpty());
        assertEquals(2, resolver.getSubtypes().size());
        assertTrue(resolver.getSubtypes().containsKey("JacksonSpiTypeIdResolverTest$AnnotatedTypeImpl"));
        assertEquals(AnnotatedTypeImpl.class, resolver.getSubtypes().get("JacksonSpiTypeIdResolverTest$AnnotatedTypeImpl"));
        assertTrue(resolver.getSubtypes().containsKey("DERIVED"));
        assertEquals(DerivedTypeImpl.class, resolver.getSubtypes().get("DERIVED"));
    }

    /**
     * Test for {@link JacksonSpiTypeIdResolver#init(JavaType)}.
     */
    @Test
    void testInit_Derived() {
        final JacksonSpiTypeIdResolver resolver = new JacksonSpiTypeIdResolver();
        final JavaType type = TypeFactory.defaultInstance().constructType(DerivedType.class);
        resolver.init(type);
        assertFalse(resolver.getSubtypes().isEmpty());
        assertEquals(2, resolver.getSubtypes().size());
        assertTrue(resolver.getSubtypes().containsKey("DERIVED"));
        assertEquals(DerivedTypeImpl.class, resolver.getSubtypes().get("DERIVED"));
        assertTrue(resolver.getSubtypes().containsKey("JacksonSpiTypeIdResolverTest$UnnamedDerivedTypeImpl"));
        assertEquals(UnnamedDerivedTypeImpl.class, resolver.getSubtypes().get("JacksonSpiTypeIdResolverTest$UnnamedDerivedTypeImpl"));
    }

    /**
     * Test for {@link JacksonSpiTypeIdResolver#init(JavaType)}.
     */
    @Test
    void testInit_Impl() {
        final JacksonSpiTypeIdResolver resolver = new JacksonSpiTypeIdResolver();
        final JavaType type = TypeFactory.defaultInstance().constructType(DerivedTypeImpl.class);
        resolver.init(type);
        assertFalse(resolver.getSubtypes().isEmpty());
        assertEquals(2, resolver.getSubtypes().size());
        assertTrue(resolver.getSubtypes().containsKey("DERIVED"));
        assertEquals(DerivedTypeImpl.class, resolver.getSubtypes().get("DERIVED"));
        assertTrue(resolver.getSubtypes().containsKey("JacksonSpiTypeIdResolverTest$UnnamedDerivedTypeImpl"));
        assertEquals(UnnamedDerivedTypeImpl.class, resolver.getSubtypes().get("JacksonSpiTypeIdResolverTest$UnnamedDerivedTypeImpl"));
    }

    /**
     * Test for {@link JacksonSpiTypeIdResolver#init(JavaType)}.
     */
    @Test
    void testInit_DuplicatedName() {
        final JacksonSpiTypeIdResolver resolver = new JacksonSpiTypeIdResolver();
        final JavaType type = TypeFactory.defaultInstance().constructType(DuplicatedNameTypeImpl.class);
        assertThrows(IllegalArgumentException.class, () -> {
            resolver.init(type);
        });
    }

    /**
     * Test for {@link JacksonSpiTypeIdResolver#typeFromId(DatabindContext, String)}.
     */
    @Test
    void testTypeFromId() {
        final JacksonSpiTypeIdResolver resolver = new JacksonSpiTypeIdResolver();
        final JavaType type = TypeFactory.defaultInstance().constructType(AnnotatedType.class);
        resolver.init(type);
        final DatabindContext context = mock(DatabindContext.class);
        final JavaType mockType = spy(TypeFactory.defaultInstance().constructType(JavaType.class));
        given(context.constructType(DerivedTypeImpl.class)).willReturn(mockType);
        assertNull(resolver.typeFromId(null, "UNKNOWN"));
        assertNull(resolver.typeFromId(context, "UNKNOWN"));
        then(context).should(never()).constructType(any());
        assertEquals(
                TypeFactory.defaultInstance().constructType(DerivedTypeImpl.class),
                resolver.typeFromId(null, "DERIVED"));
        assertSame(
                mockType,
                resolver.typeFromId(context, "DERIVED"));
        then(context).should().constructType(DerivedTypeImpl.class);
    }

    @JsonTypeIdResolver(JacksonSpiTypeIdResolver.class)
    public static interface AnnotatedType {}
    @JsonTypeIdResolver(JacksonSpiTypeIdResolver.class)
    public static class AnnotatedTypeImpl implements AnnotatedType {}
    @JsonTypeIdResolver(JacksonSpiTypeIdResolver.class)
    public static interface DerivedType extends AnnotatedType {}
    @JsonTypeName("DERIVED")
    public static class DerivedTypeImpl extends AnnotatedTypeImpl implements DerivedType {}
    public static class UnnamedDerivedTypeImpl extends DerivedTypeImpl {}
    @JsonTypeIdResolver(JacksonSpiTypeIdResolver.class)
    public static interface DuplicatedNameType {}
    @JsonTypeName("DERIVED")
    public static class DuplicatedNameTypeImpl extends DerivedTypeImpl implements DuplicatedNameType {}
}
