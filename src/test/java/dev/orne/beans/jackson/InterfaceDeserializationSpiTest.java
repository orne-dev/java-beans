package dev.orne.beans.jackson;

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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;

import dev.orne.beans.JacksonSpiTypeIdResolver;

/**
 * Integration tests for serialization and de-serialization of
 * interface based polymorphic beans with Jackson.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2023-11
 * @since 0.6
 */
@Tag("it")
class InterfaceDeserializationSpiTest {

    @BeforeAll
    public static void cleanTypeFactoryCache() {
        TypeFactory.defaultInstance().clearCache();
    }

    @AfterEach
    public void resetTypeFactoryCache() {
        TypeFactory.defaultInstance().clearCache();
    }

    /**
     * Tests for {@link JacksonTypeSpiResolver#typeFromId(DatabindContext, String)}.
     */
    @Test
    void testSerialize_Default() {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode result = mapper.valueToTree(new DefaultImpl());
        assertNotNull(result);
        assertTrue(result.isObject());
        assertNotNull(result.get("@type"));
        assertTrue(result.get("@type").isTextual());
        assertEquals(DefaultImpl.TYPE, result.get("@type").asText());
    }

    /**
     * Tests for {@link JacksonTypeSpiResolver#typeFromId(DatabindContext, String)}.
     */
    @Test
    void testSerialize_Extra() {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode result = mapper.valueToTree(new ExtraImpl());
        assertNotNull(result);
        assertTrue(result.isObject());
        assertNotNull(result.get("@type"));
        assertTrue(result.get("@type").isTextual());
        assertEquals(ExtraImpl.TYPE, result.get("@type").asText());
    }

    /**
     * Tests for {@link JacksonTypeSpiResolver#typeFromId(DatabindContext, String)}.
     */
    @Test
    void testSerialize_Derived() {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode result = mapper.valueToTree(new DerivedImpl());
        assertNotNull(result);
        assertTrue(result.isObject());
        assertNotNull(result.get("@type"));
        assertTrue(result.get("@type").isTextual());
        assertEquals(DerivedImpl.TYPE, result.get("@type").asText());
    }

    /**
     * Tests for {@link JacksonTypeSpiResolver#typeFromId(DatabindContext, String)}.
     */
    @Test
    void testSerialize_Unnamed() {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode result = mapper.valueToTree(new UnnamedImpl());
        assertNotNull(result);
        assertTrue(result.isObject());
        assertNotNull(result.get("@type"));
        assertTrue(result.get("@type").isTextual());
        assertEquals(UnnamedImpl.EXPECTED_TYPE, result.get("@type").asText());
    }

    /**
     * Tests for {@link JacksonTypeSpiResolver#typeFromId(DatabindContext, String)}.
     */
    @Test
    void testSerialize_Missing() {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode result = mapper.valueToTree(new MissingImpl());
        assertNotNull(result);
        assertTrue(result.isObject());
        assertNotNull(result.get("@type"));
        assertTrue(result.get("@type").isTextual());
        assertEquals(MissingImpl.EXPECTED_TYPE, result.get("@type").asText());
    }

    /**
     * Tests for {@link JacksonTypeSpiResolver#typeFromId(DatabindContext, String)}.
     */
    @Test
    void testDeserialize()
    throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNodeFactory factory = mapper.getNodeFactory();
        final ObjectNode root = factory.objectNode();
        root.set("@type", factory.textNode(DefaultImpl.TYPE));
        
        final IBase result = mapper.treeToValue(root, IBase.class);
        assertNotNull(result);
        assertEquals(DefaultImpl.class, result.getClass());
    }

    @Test
    void testDeserialize_Default()
    throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNodeFactory factory = mapper.getNodeFactory();
        final ObjectNode root = factory.objectNode();
        
        final IBase result = mapper.treeToValue(root, IBase.class);
        assertNotNull(result);
        assertEquals(DefaultImpl.class, result.getClass());
    }

    @Test
    void testDeserialize_Extra()
    throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNodeFactory factory = mapper.getNodeFactory();
        final ObjectNode root = factory.objectNode();
        root.set("@type", factory.textNode(ExtraImpl.TYPE));
        
        final IBase result = mapper.treeToValue(root, IBase.class);
        assertNotNull(result);
        assertEquals(ExtraImpl.class, result.getClass());
    }

    @Test
    void testDeserialize_Derived()
    throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNodeFactory factory = mapper.getNodeFactory();
        final ObjectNode root = factory.objectNode();
        root.set("@type", factory.textNode(DerivedImpl.TYPE));
        
        final IBase result = mapper.treeToValue(root, IBase.class);
        assertNotNull(result);
        assertEquals(DerivedImpl.class, result.getClass());
    }

    @Test
    void testDeserialize_Unrelated()
    throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNodeFactory factory = mapper.getNodeFactory();
        final ObjectNode root = factory.objectNode();
        root.set("@type", factory.textNode(UnrelatedImpl.TYPE));
        
        final IBase result = mapper.treeToValue(root, IBase.class);
        assertNotNull(result);
        assertEquals(DefaultImpl.class, result.getClass());
    }

    @Test
    void testDeserialize_Unnamed()
    throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNodeFactory factory = mapper.getNodeFactory();
        final ObjectNode root = factory.objectNode();
        root.set("@type", factory.textNode(UnnamedImpl.EXPECTED_TYPE));
        
        final IBase result = mapper.treeToValue(root, IBase.class);
        assertNotNull(result);
        assertEquals(UnnamedImpl.class, result.getClass());
    }

    @Test
    void testDeserialize_Missing()
    throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNodeFactory factory = mapper.getNodeFactory();
        final ObjectNode root = factory.objectNode();
        root.set("@type", factory.textNode(MissingImpl.EXPECTED_TYPE));
        
        final IBase result = mapper.treeToValue(root, IBase.class);
        assertNotNull(result);
        assertEquals(DefaultImpl.class, result.getClass());
    }

    @Test
    void testDeserialize_ToExtra()
    throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNodeFactory factory = mapper.getNodeFactory();
        final ObjectNode root = factory.objectNode();
        root.set("@type", factory.textNode(DerivedImpl.TYPE));
        
        final ExtraImpl result = mapper.treeToValue(root, ExtraImpl.class);
        assertNotNull(result);
        assertEquals(DerivedImpl.class, result.getClass());
    }

    @Test
    void testDeserialize_ToExtra_Default()
    throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNodeFactory factory = mapper.getNodeFactory();
        final ObjectNode root = factory.objectNode();
        
        assertThrows(JsonProcessingException.class, () -> {
            mapper.treeToValue(root, ExtraImpl.class);
        });
    }

    @Test
    void testDeserialize_ToExtra_UseBaseClass()
    throws JsonProcessingException {
        final ObjectMapper mapper = JsonMapper.builder()
                .enable(MapperFeature.USE_BASE_TYPE_AS_DEFAULT_IMPL)
                .build();
        final JsonNodeFactory factory = mapper.getNodeFactory();
        final ObjectNode root = factory.objectNode();
        
        final Object result = mapper.treeToValue(root, ExtraImpl.class);
        assertNotNull(result);
        assertEquals(ExtraImpl.class, result.getClass());
    }

    @Test
    void testDeserialize_ToExtra_NoTypeId()
    throws JsonProcessingException {
        final ObjectMapper mapper = JsonMapper.builder()
                .disable(MapperFeature.REQUIRE_TYPE_ID_FOR_SUBTYPES)
                .build();
        final JsonNodeFactory factory = mapper.getNodeFactory();
        final ObjectNode root = factory.objectNode();
        
        final Object result = mapper.treeToValue(root, ExtraImpl.class);
        assertNotNull(result);
        assertEquals(ExtraImpl.class, result.getClass());
    }

    @Test
    void testDeserialize_ToDerived()
    throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNodeFactory factory = mapper.getNodeFactory();
        final ObjectNode root = factory.objectNode();
        root.set("@type", factory.textNode(DerivedImpl.TYPE));
        
        final IDerived result = mapper.treeToValue(root, IDerived.class);
        assertNotNull(result);
        assertEquals(DerivedImpl.class, result.getClass());
    }

    @Test
    void testDeserialize_ToDerived_Default()
    throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNodeFactory factory = mapper.getNodeFactory();
        final ObjectNode root = factory.objectNode();
        
        final IDerived result = mapper.treeToValue(root, IDerived.class);
        assertNotNull(result);
        assertEquals(DerivedImpl.class, result.getClass());
    }

    @Test
    void testDeserialize_ToDerived_Unrelated()
    throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNodeFactory factory = mapper.getNodeFactory();
        final ObjectNode root = factory.objectNode();
        root.set("@type", factory.textNode(UnrelatedImpl.TYPE));
        
        final IDerived result = mapper.treeToValue(root, IDerived.class);
        assertNotNull(result);
        assertEquals(UnrelatedImpl.class, result.getClass());
    }

    @Test
    void testDeserializeContainer_Default()
    throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNodeFactory factory = mapper.getNodeFactory();
        final ObjectNode root = factory.objectNode();
        final ObjectNode bean = factory.objectNode();
        bean.set("base", factory.textNode("expected-base"));
        root.set("bean", bean);
        final BaseContainer result = mapper.treeToValue(root, BaseContainer.class);
        assertNotNull(result);
        final DefaultImpl resultBean = assertInstanceOf(DefaultImpl.class, result.bean);
        assertEquals("expected-base", resultBean.getBase()) ;
        assertEquals(DefaultImpl.class, resultBean.getClass());
    }

    @Test
    void testDeserializeContainer_ToExtra()
    throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNodeFactory factory = mapper.getNodeFactory();
        final ObjectNode root = factory.objectNode();
        final ObjectNode bean = factory.objectNode();
        bean.set("@type", factory.textNode(ExtraImpl.TYPE));
        bean.set("extra", factory.textNode("expected-extra"));
        root.set("bean", bean);
        final BaseContainer result = mapper.treeToValue(root, BaseContainer.class);
        assertNotNull(result);
        final ExtraImpl resultBean = assertInstanceOf(ExtraImpl.class, result.bean);
        assertEquals("expected-extra", resultBean.getExtra()) ;
        assertEquals(ExtraImpl.class, resultBean.getClass());
    }

    @Test
    void testDeserializeContainer_ToDerived()
    throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNodeFactory factory = mapper.getNodeFactory();
        final ObjectNode root = factory.objectNode();
        final ObjectNode bean = factory.objectNode();
        bean.set("@type", factory.textNode(DerivedImpl.TYPE));
        bean.set("extra", factory.textNode("expected-extra"));
        bean.set("derived", factory.textNode("expected-derived"));
        root.set("bean", bean);
        final BaseContainer result = mapper.treeToValue(root, BaseContainer.class);
        assertNotNull(result);
        final DerivedImpl resultBean = assertInstanceOf(DerivedImpl.class, result.bean);
        assertEquals("expected-extra", resultBean.getExtra()) ;
        assertEquals("expected-derived", resultBean.getDerived()) ;
        assertEquals(DerivedImpl.class, resultBean.getClass());
    }

    @Test
    void testDeserializeContainer_ToUnnamed()
    throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNodeFactory factory = mapper.getNodeFactory();
        final ObjectNode root = factory.objectNode();
        final ObjectNode bean = factory.objectNode();
        bean.set("@type", factory.textNode(UnnamedImpl.EXPECTED_TYPE));
        bean.set("unnamed", factory.textNode("expected-unnamed"));
        root.set("bean", bean);
        final BaseContainer result = mapper.treeToValue(root, BaseContainer.class);
        assertNotNull(result);
        final UnnamedImpl resultBean = assertInstanceOf(UnnamedImpl.class, result.bean);
        assertEquals("expected-unnamed", resultBean.getUnnamed()) ;
        assertEquals(UnnamedImpl.class, resultBean.getClass());
    }

    @Test
    void testDeserializeContainer_ToMissing()
    throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNodeFactory factory = mapper.getNodeFactory();
        final ObjectNode root = factory.objectNode();
        final ObjectNode bean = factory.objectNode();
        bean.set("@type", factory.textNode(MissingImpl.EXPECTED_TYPE));
        bean.set("missing", factory.textNode("expected-missing"));
        root.set("bean", bean);
        assertThrows(JsonProcessingException.class, () -> {
            mapper.treeToValue(root, BaseContainer.class);
        });
    }

    @Test
    void testDeserializeDerivedContainer_Default()
    throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNodeFactory factory = mapper.getNodeFactory();
        final ObjectNode root = factory.objectNode();
        final ObjectNode bean = factory.objectNode();
        bean.set("extra", factory.textNode("expected-extra"));
        bean.set("derived", factory.textNode("expected-derived"));
        root.set("bean", bean);
        final DerivedContainer result = mapper.treeToValue(root, DerivedContainer.class);
        assertNotNull(result);
        final DerivedImpl resultBean = assertInstanceOf(DerivedImpl.class, result.bean);
        assertEquals("expected-extra", resultBean.getExtra()) ;
        assertEquals("expected-derived", resultBean.getDerived()) ;
        assertEquals(DerivedImpl.class, resultBean.getClass());
    }

    @Test
    void testDeserializeDerivedContainer_ToDerived()
    throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNodeFactory factory = mapper.getNodeFactory();
        final ObjectNode root = factory.objectNode();
        final ObjectNode bean = factory.objectNode();
        bean.set("@type", factory.textNode(DerivedImpl.TYPE));
        bean.set("extra", factory.textNode("expected-extra"));
        bean.set("derived", factory.textNode("expected-derived"));
        root.set("bean", bean);
        final DerivedContainer result = mapper.treeToValue(root, DerivedContainer.class);
        assertNotNull(result);
        final DerivedImpl resultBean = assertInstanceOf(DerivedImpl.class, result.bean);
        assertEquals("expected-extra", resultBean.getExtra()) ;
        assertEquals("expected-derived", resultBean.getDerived()) ;
        assertEquals(DerivedImpl.class, resultBean.getClass());
    }

    @Test
    void testDeserializeDerivedContainer_ToUnrelated()
    throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNodeFactory factory = mapper.getNodeFactory();
        final ObjectNode root = factory.objectNode();
        final ObjectNode bean = factory.objectNode();
        bean.set("@type", factory.textNode(UnrelatedImpl.TYPE));
        bean.set("derived", factory.textNode("expected-derived"));
        bean.set("unrelated", factory.textNode("expected-unrelated"));
        root.set("bean", bean);
        final DerivedContainer result = mapper.treeToValue(root, DerivedContainer.class);
        assertNotNull(result);
        final UnrelatedImpl resultBean = assertInstanceOf(UnrelatedImpl.class, result.bean);
        assertEquals("expected-derived", resultBean.getDerived()) ;
        assertEquals("expected-unrelated", resultBean.getUnrelated()) ;
        assertEquals(UnrelatedImpl.class, resultBean.getClass());
    }

    @Test
    void testDeserializeUnrelatedContainer_Default()
    throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNodeFactory factory = mapper.getNodeFactory();
        final ObjectNode root = factory.objectNode();
        final ObjectNode bean = factory.objectNode();
        bean.set("derived", factory.textNode("expected-derived"));
        bean.set("unrelated", factory.textNode("expected-unrelated"));
        root.set("bean", bean);
        final UnrelatedContainer result = mapper.treeToValue(root, UnrelatedContainer.class);
        assertNotNull(result);
        final UnrelatedImpl resultBean = assertInstanceOf(UnrelatedImpl.class, result.bean);
        assertEquals("expected-derived", resultBean.getDerived()) ;
        assertEquals("expected-unrelated", resultBean.getUnrelated()) ;
        assertEquals(UnrelatedImpl.class, resultBean.getClass());
    }

    @Test
    void testDeserializeUnrelatedContainer_ToUnrelated()
    throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNodeFactory factory = mapper.getNodeFactory();
        final ObjectNode root = factory.objectNode();
        final ObjectNode bean = factory.objectNode();
        bean.set("@type", factory.textNode(UnrelatedImpl.TYPE));
        bean.set("derived", factory.textNode("expected-derived"));
        bean.set("unrelated", factory.textNode("expected-unrelated"));
        root.set("bean", bean);
        final UnrelatedContainer result = mapper.treeToValue(root, UnrelatedContainer.class);
        assertNotNull(result);
        final UnrelatedImpl resultBean = assertInstanceOf(UnrelatedImpl.class, result.bean);
        assertEquals("expected-derived", resultBean.getDerived()) ;
        assertEquals("expected-unrelated", resultBean.getUnrelated()) ;
        assertEquals(UnrelatedImpl.class, resultBean.getClass());
    }

    @Test
    void testDeserializeMissingContainer_Default()
    throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNodeFactory factory = mapper.getNodeFactory();
        final ObjectNode root = factory.objectNode();
        final ObjectNode bean = factory.objectNode();
        bean.set("missing", factory.textNode("expected-missing"));
        root.set("bean", bean);
        assertThrows(JsonProcessingException.class, () -> {
            mapper.treeToValue(root, MissingContainer.class);
        });
    }

    @Test
    void testDeserializeMissingContainer_UseBaseClass()
    throws JsonProcessingException {
        final ObjectMapper mapper = JsonMapper.builder()
                .enable(MapperFeature.USE_BASE_TYPE_AS_DEFAULT_IMPL)
                .build();
        final JsonNodeFactory factory = mapper.getNodeFactory();
        final ObjectNode root = factory.objectNode();
        final ObjectNode bean = factory.objectNode();
        bean.set("missing", factory.textNode("expected-missing"));
        root.set("bean", bean);
        final MissingContainer result = mapper.treeToValue(root, MissingContainer.class);
        assertNotNull(result);
        final MissingImpl resultBean = assertInstanceOf(MissingImpl.class, result.bean);
        assertEquals("expected-missing", resultBean.getMissing()) ;
        assertEquals(MissingImpl.class, resultBean.getClass());
    }

    @Test
    void testDeserializeMissingContainer_NoTypeId()
    throws JsonProcessingException {
        final ObjectMapper mapper = JsonMapper.builder()
                .disable(MapperFeature.REQUIRE_TYPE_ID_FOR_SUBTYPES)
                .build();
        final JsonNodeFactory factory = mapper.getNodeFactory();
        final ObjectNode root = factory.objectNode();
        final ObjectNode bean = factory.objectNode();
        bean.set("missing", factory.textNode("expected-missing"));
        root.set("bean", bean);
        final MissingContainer result = mapper.treeToValue(root, MissingContainer.class);
        assertNotNull(result);
        final MissingImpl resultBean = assertInstanceOf(MissingImpl.class, result.bean);
        assertEquals("expected-missing", resultBean.getMissing()) ;
        assertEquals(MissingImpl.class, resultBean.getClass());
    }

    @Test
    void testDeserializeMissingContainer_ToExtra()
    throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNodeFactory factory = mapper.getNodeFactory();
        final ObjectNode root = factory.objectNode();
        final ObjectNode bean = factory.objectNode();
        bean.set("@type", factory.textNode(ExtraImpl.TYPE));
        bean.set("extra", factory.textNode("expected-extra"));
        root.set("bean", bean);
        assertThrows(JsonProcessingException.class, () -> {
            mapper.treeToValue(root, MissingContainer.class);
        });
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, defaultImpl = DefaultImpl.class)
    @JsonTypeIdResolver(JacksonSpiTypeIdResolver.class)
    public static interface IBase {}
    @JsonTypeName(DefaultImpl.TYPE)
    public static class DefaultImpl implements IBase {
        public static final String TYPE = "DEFAULT";
        private String base;
        public String getBase() {
            return base;
        }
        public void setBase(String base) {
            this.base = base;
        }
    }
    @JsonTypeName(ExtraImpl.TYPE)
    public static class ExtraImpl implements IBase {
        public static final String TYPE = "EXTRA";
        private String extra;
        public String getExtra() {
            return extra;
        }
        public void setExtra(String extra) {
            this.extra = extra;
        }
    }
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, defaultImpl = DerivedImpl.class)
    @JsonTypeIdResolver(JacksonSpiTypeIdResolver.class)
    public static interface IDerived {
        String getDerived();
    }
    @JsonTypeName(DerivedImpl.TYPE)
    public static class DerivedImpl extends ExtraImpl implements IDerived {
        public static final String TYPE = "DERIVED";
        private String derived;
        public String getDerived() {
            return derived;
        }
        public void setDerived(String derived) {
            this.derived = derived;
        }
    }
    @JsonTypeName(UnrelatedImpl.TYPE)
    public static class UnrelatedImpl implements IDerived {
        public static final String TYPE = "UNRELATED";
        private String derived;
        public String getDerived() {
            return derived;
        }
        public void setDerived(String derived) {
            this.derived = derived;
        }
        private String unrelated;
        public String getUnrelated() {
            return unrelated;
        }
        public void setUnrelated(String unrelated) {
            this.unrelated = unrelated;
        }
    }
    public static class UnnamedImpl implements IBase {
        public static final String EXPECTED_TYPE = "InterfaceDeserializationSpiTest$UnnamedImpl";
        private String unnamed;
        public String getUnnamed() {
            return unnamed;
        }
        public void setUnnamed(String unnamed) {
            this.unnamed = unnamed;
        }
    }
    public static class MissingImpl implements IBase {
        public static final String EXPECTED_TYPE = "InterfaceDeserializationSpiTest$MissingImpl";
        private String missing;
        public String getMissing() {
            return missing;
        }
        public void setMissing(String missing) {
            this.missing = missing;
        }
    }
    public static class BaseContainer {
        private IBase bean;
        public IBase getBean() {
            return bean;
        }
        public void setBean(IBase bean) {
            this.bean = bean;
        }
    }
    public static class DerivedContainer {
        private IDerived bean;
        public IDerived getBean() {
            return bean;
        }
        public void setBean(IDerived bean) {
            this.bean = bean;
        }
    }
    public static class UnrelatedContainer {
        private UnrelatedImpl bean;
        public UnrelatedImpl getBean() {
            return bean;
        }
        public void setBean(UnrelatedImpl bean) {
            this.bean = bean;
        }
    }
    public static class MissingContainer {
        private MissingImpl bean;
        public MissingImpl getBean() {
            return bean;
        }
        public void setBean(MissingImpl bean) {
            this.bean = bean;
        }
    }
}
