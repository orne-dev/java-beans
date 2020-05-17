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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ToStringBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Integration tests for {@code IdentityBean}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see IdentityBean
 */
@Tag("it")
public class IdentityBeanIT {

    /**
     * Test {@link IdentityBean} Jackson serialization.
     * @throws Throwable Should not happen
     */
    @Test
    public void testJacksonSerialization()
    throws Throwable {
        final String identityToken = "mock identity token";
        final Identity identity = mock(Identity.class);
        given(identity.getIdentityToken()).willReturn(identityToken);
        final TestBean bean = new TestBean();
        final String expectedResult = String.format(
                "{" +
                    "\"identity\":\"%s\""+
                "}",
                identityToken);
        final ObjectMapper mapper = new ObjectMapper();
        bean.setIdentity(identity);
        assertEquals(expectedResult, mapper.writeValueAsString(bean));
        bean.setIdentity(new TokenIdentity(identityToken));
        assertEquals(expectedResult, mapper.writeValueAsString(bean));
    }

    /**
     * Test {@link IdentityBean} Jackson serialization.
     * @throws Throwable Should not happen
     */
    @Test
    public void testJacksonSerializationNullIdentity()
    throws Throwable {
        final TestBean bean = new TestBean();
        final String expectedResult =
                "{" +
                    "\"identity\":null"+
                "}";
        final ObjectMapper mapper = new ObjectMapper();
        assertEquals(expectedResult, mapper.writeValueAsString(bean));
    }

    /**
     * Test {@link IdentityBean} Jackson serialization.
     * @throws Throwable Should not happen
     */
    @Test
    public void testJacksonDeserialization()
    throws Throwable {
        final String identityToken = "mock identity token";
        final String json = String.format(
                "{" +
                    "\"identity\":\"%s\""+
                "}",
                identityToken);
        final TestBean expectedBean = new TestBean();
        expectedBean.setIdentity(new TokenIdentity(identityToken));
        final ObjectMapper mapper = new ObjectMapper();
        assertEquals(expectedBean, mapper.readValue(json, TestBean.class));
    }

    /**
     * Test {@link IdentityBean} Jackson serialization.
     * @throws Throwable Should not happen
     */
    @Test
    public void testJacksonDeserializationNullIdentity()
    throws Throwable {
        final String json =
                "{" +
                    "\"identity\":null"+
                "}";
        final TestBean expectedBean = new TestBean();
        final ObjectMapper mapper = new ObjectMapper();
        assertEquals(expectedBean, mapper.readValue(json, TestBean.class));
    }

    /**
     * Test {@link IdentityBean} Jackson serialization.
     * @throws Throwable Should not happen
     */
    @Test
    public void testJacksonDeserializationNoIdentity()
    throws Throwable {
        final String json = "{}";
        final TestBean expectedBean = new TestBean();
        final ObjectMapper mapper = new ObjectMapper();
        assertEquals(expectedBean, mapper.readValue(json, TestBean.class));
    }

    /**
     * Test {@link IdentityBean} Jackson serialization.
     * @throws Throwable Should not happen
     */
    @Test
    public void testJacksonContainerDeserialization()
    throws Throwable {
        final String identityToken = "mock identity token";
        final String json = String.format(
                "{" +
                    "\"bean\": {" +
                        "\"identity\": \"%s\"" +
                    "}" +
                "}",
                identityToken);
        final TestBean expectedBean = new TestBean();
        expectedBean.setIdentity(new TokenIdentity(identityToken));
        final IdentityBeanContainer expectedResult = new IdentityBeanContainer();
        expectedResult.setBean(expectedBean);
        final ObjectMapper mapper = new ObjectMapper();
        assertEquals(expectedResult, mapper.readValue(json, IdentityBeanContainer.class));
    }

    /**
     * Test {@link IdentityBean} Jackson serialization.
     * @throws Throwable Should not happen
     */
    @Test
    public void testJacksonContainerDeserializationNullIdentity()
    throws Throwable {
        final String json =
                "{" +
                    "\"bean\": {" +
                        "\"identity\": null" +
                    "}" +
                "}";
        final TestBean expectedBean = new TestBean();
        final IdentityBeanContainer expectedResult = new IdentityBeanContainer();
        expectedResult.setBean(expectedBean);
        final ObjectMapper mapper = new ObjectMapper();
        assertEquals(expectedResult, mapper.readValue(json, IdentityBeanContainer.class));
    }

    /**
     * Test {@link IdentityBean} Jackson serialization.
     * @throws Throwable Should not happen
     */
    @Test
    public void testJacksonContainerDeserializationNoIdentity()
    throws Throwable {
        final String json =
                "{" +
                    "\"bean\": {" +
                        "\"identity\": null" +
                    "}" +
                "}";
        final TestBean expectedBean = new TestBean();
        final IdentityBeanContainer expectedResult = new IdentityBeanContainer();
        expectedResult.setBean(expectedBean);
        final ObjectMapper mapper = new ObjectMapper();
        assertEquals(expectedResult, mapper.readValue(json, IdentityBeanContainer.class));
    }

    /**
     * Test {@link IdentityBean} JAXB serialization.
     * @throws Throwable Should not happen
     */
    @Test
    public void testJaxbSerialization()
    throws Throwable {
        final String identityToken = "mock identity token";
        final Identity identity = mock(Identity.class);
        given(identity.getIdentityToken()).willReturn(identityToken);
        final TestBean bean = new TestBean();
        final IdentityBeanContainer container = new IdentityBeanContainer();
        container.setBean(bean);
        final String expectedResult = String.format(
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                    "<container>" +
                    "<bean identity=\"%s\"/>" +
                    "</container>",
                identityToken);
        final JAXBContext context = JAXBContext.newInstance(IdentityBeanContainer.class);
        final Marshaller marshaller = context.createMarshaller();
        bean.setIdentity(identity);
        StringWriter writer = new StringWriter();
        marshaller.marshal(container, writer);
        assertEquals(expectedResult, writer.toString());
        bean.setIdentity(new TokenIdentity(identityToken));
        writer = new StringWriter();
        marshaller.marshal(container, writer);
        assertEquals(expectedResult, writer.toString());
    }

    /**
     * Test {@link IdentityBean} JAXB serialization.
     * @throws Throwable Should not happen
     */
    @Test
    public void testJaxbSerializationNullIdentity()
    throws Throwable {
        final TestBean bean = new TestBean();
        final IdentityBeanContainer container = new IdentityBeanContainer();
        container.setBean(bean);
        final String expectedResult =
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                    "<container>" +
                    "<bean/>" +
                    "</container>";
        final JAXBContext context = JAXBContext.newInstance(IdentityBeanContainer.class);
        final Marshaller marshaller = context.createMarshaller();
        final StringWriter writer = new StringWriter();
        marshaller.marshal(container, writer);
        assertEquals(expectedResult, writer.toString());
    }

    /**
     * Test {@link IdentityBean} JAXB serialization.
     * @throws Throwable Should not happen
     */
    @Test
    public void testJaxbDeserialization()
    throws Throwable {
        final String identityToken = "mock identity token";
        final String xml = String.format(
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                    "<container>" +
                    "<bean identity=\"%s\" />" +
                    "</container>",
                identityToken);
        final TestBean expectedBean = new TestBean();
        expectedBean.setIdentity(new TokenIdentity(identityToken));
        final IdentityBeanContainer expectedResult = new IdentityBeanContainer();
        expectedResult.setBean(expectedBean);
        final JAXBContext context = JAXBContext.newInstance(IdentityBeanContainer.class);
        final Unmarshaller unmarshaller = context.createUnmarshaller();
        final StringReader reader = new StringReader(xml);
        final IdentityBeanContainer result = (IdentityBeanContainer) unmarshaller.unmarshal(reader);
        assertEquals(expectedResult, result);
    }

    /**
     * Test {@link IdentityBean} JAXB serialization.
     * @throws Throwable Should not happen
     */
    @Test
    public void testJaxbDeserializationNoIdentity()
    throws Throwable {
        final String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                    "<container>" +
                    "<bean />" +
                    "</container>";
        final TestBean expectedBean = new TestBean();
        final IdentityBeanContainer expectedResult = new IdentityBeanContainer();
        expectedResult.setBean(expectedBean);
        final JAXBContext context = JAXBContext.newInstance(IdentityBeanContainer.class);
        final Unmarshaller unmarshaller = context.createUnmarshaller();
        final StringReader reader = new StringReader(xml);
        final IdentityBeanContainer result = (IdentityBeanContainer) unmarshaller.unmarshal(reader);
        assertEquals(expectedResult, result);
    }

    /**
     * Test {@link IdentityBean} JAXB serialization.
     * @throws Throwable Should not happen
     */
    @Test
    public void testJaxbDeserializationEmptyIdentity()
    throws Throwable {
        final String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                    "<container>" +
                    "<bean identity=\"\"/>" +
                    "</container>";
        final TestBean expectedBean = new TestBean();
        final IdentityBeanContainer expectedResult = new IdentityBeanContainer();
        expectedResult.setBean(expectedBean);
        final JAXBContext context = JAXBContext.newInstance(IdentityBeanContainer.class);
        final Unmarshaller unmarshaller = context.createUnmarshaller();
        final StringReader reader = new StringReader(xml);
        final IdentityBeanContainer result = (IdentityBeanContainer) unmarshaller.unmarshal(reader);
        assertEquals(expectedResult, result);
    }

    /**
     * Test {@link IdentityBean} JAXB serialization.
     * @throws Throwable Should not happen
     */
    @Test
    public void testJaxbDeserializationNullIdentity()
    throws Throwable {
        final String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                    "<container xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                        "<bean>" +
                            "<identity xsi:nil=\"true\" />" +
                        "</bean>" +
                    "</container>";
        final TestBean expectedBean = new TestBean();
        final IdentityBeanContainer expectedResult = new IdentityBeanContainer();
        expectedResult.setBean(expectedBean);
        final JAXBContext context = JAXBContext.newInstance(IdentityBeanContainer.class);
        final Unmarshaller unmarshaller = context.createUnmarshaller();
        final StringReader reader = new StringReader(xml);
        final IdentityBeanContainer result = (IdentityBeanContainer) unmarshaller.unmarshal(reader);
        assertEquals(expectedResult, result);
    }

    /**
     * Test {@link IdentityBean} validation.
     * @throws Throwable Should not happen
     */
    @Test
    public void testValidation()
    throws Throwable {
        final TestBean bean = new TestBean();
        assertTrue(BeanValidationUtils.isValid(bean));
        assertFalse(BeanValidationUtils.isValid(bean, IdentityBean.RequireIdentity.class));
        assertFalse(ValidBeanReference.ValidBeanReferenceValidator.isValid(bean));
        bean.setIdentity(new TokenIdentity("mockIdentityToken"));
        assertTrue(BeanValidationUtils.isValid(bean));
        assertTrue(BeanValidationUtils.isValid(bean, IdentityBean.RequireIdentity.class));
        assertTrue(ValidBeanReference.ValidBeanReferenceValidator.isValid(bean));
    }

    /**
     * Test bean.
     */
    @XmlRootElement(name = "bean")
    private static class TestBean
    implements IdentityBean {
        private Identity identity;
        /**
         * {@inheritDoc}
         */
        @Override
        @XmlAttribute
        public Identity getIdentity() {
            return this.identity;
        }
        public void setIdentity(final Identity identity) {
            this.identity = identity;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                    .append(getClass())
                    .append(this.identity)
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
            final TestBean other = (TestBean) obj;
            return new EqualsBuilder()
                    .append(this.identity, other.identity)
                    .isEquals();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("identity", identity)
                    .toString();
        }
    }

    /**
     * Test container.
     */
    @XmlRootElement(name = "container")
    private static class IdentityBeanContainer {
        private TestBean bean;
        @XmlElement
        public TestBean getBean() {
            return bean;
        }
        public void setBean(final TestBean bean) {
            this.bean = bean;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                    .append(getClass())
                    .append(this.bean)
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
            final IdentityBeanContainer other = (IdentityBeanContainer) obj;
            return new EqualsBuilder()
                    .append(this.bean, other.bean)
                    .isEquals();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("bean", bean)
                    .toString();
        }
    }
}
