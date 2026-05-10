package dev.orne.beans.jsonb;

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

import java.io.StringReader;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.json.bind.annotation.JsonbSubtype;
import jakarta.json.bind.annotation.JsonbTypeInfo;

/**
 * Integration tests for serialization and de-serialization of
 * interface based polymorphic beans with JSON-B.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2024-04
 * @since 0.7
 */
@Tag("it")
class InterfaceDeserializationTest {

    private static final Jsonb JSONB = JsonbBuilder.create();

    /**
     * Tests for {@link JacksonTypeSpiResolver#typeFromId(DatabindContext, String)}.
     */
    @Test
    void testSerialize_Default() {
        final String json = JSONB.toJson(new DefaultImpl());
        final JsonValue result;
        try (final JsonReader parser = Json.createReader(new StringReader(json))) {
            result = parser.readValue();
        }
        assertNotNull(result);
        assertEquals(JsonValue.ValueType.OBJECT, result.getValueType());
        final JsonObject jsonObj = result.asJsonObject();
        assertEquals(DefaultImpl.TYPE, jsonObj.getString("@type"));
    }

    /**
     * Tests for {@link JacksonTypeSpiResolver#typeFromId(DatabindContext, String)}.
     */
    @Test
    void testSerialize_Extra() {
        final String json = JSONB.toJson(new ExtraImpl());
        final JsonValue result;
        try (final JsonReader parser = Json.createReader(new StringReader(json))) {
            result = parser.readValue();
        }
        assertNotNull(result);
        assertEquals(JsonValue.ValueType.OBJECT, result.getValueType());
        final JsonObject jsonObj = result.asJsonObject();
        assertEquals(ExtraImpl.TYPE, jsonObj.getString("@type"));
    }

    /**
     * Tests for {@link JacksonTypeSpiResolver#typeFromId(DatabindContext, String)}.
     */
    @Test
    void testSerialize_Derived() {
        final DerivedImpl bean = new DerivedImpl();
        assertThrows(JsonbException.class, () -> {
            JSONB.toJson(bean);
        });
    }

    /**
     * Tests for {@link JacksonTypeSpiResolver#typeFromId(DatabindContext, String)}.
     */
    @Test
    void testSerialize_Missing() {
        final String json = JSONB.toJson(new MissingImpl());
        final JsonValue result;
        try (final JsonReader parser = Json.createReader(new StringReader(json))) {
            result = parser.readValue();
        }
        assertNotNull(result);
        assertEquals(JsonValue.ValueType.OBJECT, result.getValueType());
        final JsonObject jsonObj = result.asJsonObject();
        assertNull(jsonObj.get("@type"));
    }

    /**
     * Tests for {@link JacksonTypeSpiResolver#typeFromId(DatabindContext, String)}.
     */
    @Test
    void testDeserialize() {
        final String json = Json.createObjectBuilder()
            .add("@type", DefaultImpl.TYPE)
            .build()
            .toString();
        final IBase result = JSONB.fromJson(json, IBase.class);
        assertNotNull(result);
        assertEquals(DefaultImpl.class, result.getClass());
    }

    @Test
    void testDeserialize_Extra() {
        final String json = Json.createObjectBuilder()
                .add("@type", ExtraImpl.TYPE)
                .build()
                .toString();
        final IBase result = JSONB.fromJson(json, IBase.class);
        assertNotNull(result);
        assertEquals(ExtraImpl.class, result.getClass());
    }

    @Test
    void testDeserialize_Derived() {
        final String json = Json.createObjectBuilder()
                .add("@type", DerivedImpl.TYPE)
                .build()
                .toString();
        assertThrows(JsonbException.class, () -> {
            JSONB.fromJson(json, IBase.class);
        });
    }

    @Test
    void testDeserialize_Unrelated() {
        final String json = Json.createObjectBuilder()
                .add("@type", UnrelatedImpl.TYPE)
                .build()
                .toString();
        assertThrows(JsonbException.class, () -> {
            JSONB.fromJson(json, IBase.class);
        });
    }

    @Test
    void testDeserialize_Missing() {
        final String json = Json.createObjectBuilder()
                .add("@type", MissingImpl.EXPECTED_TYPE)
                .build()
                .toString();
        assertThrows(JsonbException.class, () -> {
            JSONB.fromJson(json, IBase.class);
        });
    }

    @Test
    void testDeserialize_ToExtra() {
        final String json = Json.createObjectBuilder()
                .add("@type", DerivedImpl.TYPE)
                .build()
                .toString();
        final ExtraImpl result = JSONB.fromJson(json, ExtraImpl.class);
        assertNotNull(result);
        assertEquals(ExtraImpl.class, result.getClass());
    }

    @Test
    void testDeserialize_ToExtra_Default() {
        final String json = Json.createObjectBuilder()
                .build()
                .toString();
        final ExtraImpl result = JSONB.fromJson(json, ExtraImpl.class);
        assertNotNull(result);
        assertEquals(ExtraImpl.class, result.getClass());
    }

    @Test
    void testDeserialize_ToDerived() {
        final String json = Json.createObjectBuilder()
                .add("@type", DerivedImpl.TYPE)
                .build()
                .toString();
        assertThrows(JsonbException.class, () -> {
            JSONB.fromJson(json, IDerived.class);
        });
    }

    @Test
    void testDeserialize_ToDerived_Default() {
        final String json = Json.createObjectBuilder()
                .build()
                .toString();
        assertThrows(JsonbException.class, () -> {
            JSONB.fromJson(json, IDerived.class);
        });
    }

    @Test
    void testDeserialize_ToDerived_Unrelated() {
        final String json = Json.createObjectBuilder()
                .add("@type", UnrelatedImpl.TYPE)
                .build()
                .toString();
        final IDerived result = JSONB.fromJson(json, IDerived.class);
        assertNotNull(result);
        assertEquals(UnrelatedImpl.class, result.getClass());
    }

    @Test
    void testDeserializeContainer_Default() {
        final JsonObject bean = Json.createObjectBuilder()
                .add("base", "expected-base")
                .build();
        final String json = Json.createObjectBuilder()
                .add("bean", bean)
                .build()
                .toString();
        assertThrows(JsonbException.class, () -> {
            JSONB.fromJson(json, BaseContainer.class);
        });
    }

    @Test
    void testDeserializeContainer_ToExtra() {
        final JsonObject bean = Json.createObjectBuilder()
                .add("@type", ExtraImpl.TYPE)
                .add("extra", "expected-extra")
                .build();
        final String json = Json.createObjectBuilder()
                .add("bean", bean)
                .build()
                .toString();
        final BaseContainer result = JSONB.fromJson(json, BaseContainer.class);
        assertNotNull(result);
        final ExtraImpl resultBean = assertInstanceOf(ExtraImpl.class, result.bean);
        assertEquals("expected-extra", resultBean.getExtra()) ;
        assertEquals(ExtraImpl.class, resultBean.getClass());
    }

    @Test
    void testDeserializeContainer_ToDerived() {
        final JsonObject bean = Json.createObjectBuilder()
                .add("@type", DerivedImpl.TYPE)
                .add("extra", "expected-extra")
                .add("derived", "expected-derived")
                .build();
        final String json = Json.createObjectBuilder()
                .add("bean", bean)
                .build()
                .toString();
        assertThrows(JsonbException.class, () -> {
            JSONB.fromJson(json, BaseContainer.class);
        });
    }

    @Test
    void testDeserializeContainer_ToMissing() {
        final JsonObject bean = Json.createObjectBuilder()
                .add("@type", MissingImpl.EXPECTED_TYPE)
                .add("missing", "expected-missing")
                .build();
        final String json = Json.createObjectBuilder()
                .add("bean", bean)
                .build()
                .toString();
        assertThrows(JsonbException.class, () -> {
            JSONB.fromJson(json, BaseContainer.class);
        });
    }

    @Test
    void testDeserializeDerivedContainer_Default() {
        final JsonObject bean = Json.createObjectBuilder()
                .add("extra", "expected-extra")
                .add("derived", "expected-derived")
                .build();
        final String json = Json.createObjectBuilder()
                .add("bean", bean)
                .build()
                .toString();
        assertThrows(JsonbException.class, () -> {
            JSONB.fromJson(json, DerivedContainer.class);
        });
    }

    @Test
    void testDeserializeDerivedContainer_ToDerived() {
        final JsonObject bean = Json.createObjectBuilder()
                .add("@type", DerivedImpl.TYPE)
                .add("extra", "expected-extra")
                .add("derived", "expected-derived")
                .build();
        final String json = Json.createObjectBuilder()
                .add("bean", bean)
                .build()
                .toString();
        assertThrows(JsonbException.class, () -> {
            JSONB.fromJson(json, DerivedContainer.class);
        });
    }

    @Test
    void testDeserializeDerivedContainer_ToUnrelated() {
        final JsonObject bean = Json.createObjectBuilder()
                .add("@type", UnrelatedImpl.TYPE)
                .add("derived", "expected-derived")
                .add("unrelated", "expected-unrelated")
                .build();
        final String json = Json.createObjectBuilder()
                .add("bean", bean)
                .build()
                .toString();
        final DerivedContainer result = JSONB.fromJson(json, DerivedContainer.class);
        assertNotNull(result);
        final UnrelatedImpl resultBean = assertInstanceOf(UnrelatedImpl.class, result.bean);
        assertEquals("expected-derived", resultBean.getDerived()) ;
        assertEquals("expected-unrelated", resultBean.getUnrelated()) ;
        assertEquals(UnrelatedImpl.class, resultBean.getClass());
    }

    @Test
    void testDeserializeUnrelatedContainer_ToUnrelated() {
        final JsonObject bean = Json.createObjectBuilder()
                .add("@type", UnrelatedImpl.TYPE)
                .add("derived", "expected-derived")
                .add("unrelated", "expected-unrelated")
                .build();
        final String json = Json.createObjectBuilder()
                .add("bean", bean)
                .build()
                .toString();
        final UnrelatedContainer result = JSONB.fromJson(json, UnrelatedContainer.class);
        assertNotNull(result);
        final UnrelatedImpl resultBean = assertInstanceOf(UnrelatedImpl.class, result.bean);
        assertEquals("expected-derived", resultBean.getDerived()) ;
        assertEquals("expected-unrelated", resultBean.getUnrelated()) ;
        assertEquals(UnrelatedImpl.class, resultBean.getClass());
    }

    @Test
    void testDeserializeMissingContainer_Default() {
        final JsonObject bean = Json.createObjectBuilder()
                .add("missing", "expected-missing")
                .build();
        final String json = Json.createObjectBuilder()
                .add("bean", bean)
                .build()
                .toString();
        final MissingContainer result = JSONB.fromJson(json, MissingContainer.class);
        assertNotNull(result);
        final MissingImpl resultBean = assertInstanceOf(MissingImpl.class, result.bean);
        assertEquals("expected-missing", resultBean.getMissing()) ;
        assertEquals(MissingImpl.class, resultBean.getClass());
    }

    @Test
    void testDeserializeMissingContainer_ToExtra() {
        final JsonObject bean = Json.createObjectBuilder()
                .add("@type", ExtraImpl.TYPE)
                .add("extra", "expected-extra")
                .build();
        final String json = Json.createObjectBuilder()
                .add("bean", bean)
                .build()
                .toString();
        final MissingContainer result = JSONB.fromJson(json, MissingContainer.class);
        assertNotNull(result);
        final MissingImpl resultBean = assertInstanceOf(MissingImpl.class, result.bean);
        assertNull(resultBean.getMissing()) ;
        assertEquals(MissingImpl.class, resultBean.getClass());
    }

    @JsonbTypeInfo({
        @JsonbSubtype(alias = DefaultImpl.TYPE, type = DefaultImpl.class),
        @JsonbSubtype(alias = ExtraImpl.TYPE, type = ExtraImpl.class),
        @JsonbSubtype(alias = DerivedImpl.TYPE, type = DerivedImpl.class),
    })
    public static interface IBase {}
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
    @JsonbTypeInfo({
        @JsonbSubtype(alias = DerivedImpl.TYPE, type = DerivedImpl.class),
        @JsonbSubtype(alias = UnrelatedImpl.TYPE, type = UnrelatedImpl.class),
    })
    public static interface IDerived {
        String getDerived();
    }
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
    public static class MissingImpl implements IBase {
        public static final String EXPECTED_TYPE = "InterfaceDeserializationTest$MissingImpl";
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
