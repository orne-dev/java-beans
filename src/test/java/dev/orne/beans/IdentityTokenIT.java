package dev.orne.beans;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Integrity tests for {@code IdentityToken}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see IdentityToken
 */
@Tag("it")
public class IdentityTokenIT {

    /**
     * Test {@link TokenIdentity} and {@link Identity} Jackson serialization.
     * @throws Throwable Should not happen
     */
    @Test
    public void testJacksonSerialization()
    throws Throwable {
        final String identityToken = "mock identity token";
        final Identity identity = mock(Identity.class);
        given(identity.getIdentityToken()).willReturn(identityToken);
        final TokenIdentity tokenIdentity = new TokenIdentity(identityToken);
        final ObjectMapper mapper = new ObjectMapper();
        final String expectedResult = String.format("\"%s\"", identityToken);
        assertEquals(expectedResult, mapper.writeValueAsString(identityToken));
        assertEquals(expectedResult, mapper.writeValueAsString(identity));
        assertEquals(expectedResult, mapper.writeValueAsString(tokenIdentity));
    }

    /**
     * Test {@link TokenIdentity} and {@link Identity} Jackson deserialization.
     * @throws Throwable Should not happen
     */
    @Test
    public void testJacksonDeserialization()
    throws Throwable {
        final String identityToken = "mock identity token";
        final String json = String.format("\"%s\"", identityToken);
        final TokenIdentity tokenIdentity = new TokenIdentity(identityToken);
        final ObjectMapper mapper = new ObjectMapper();
        assertEquals(tokenIdentity, mapper.readValue(json, Identity.class));
        assertEquals(tokenIdentity, mapper.readValue(json, TokenIdentity.class));
    }

    /**
     * Test {@link TokenIdentity} and {@link Identity} Jackson deserialization.
     * @throws Throwable Should not happen
     */
    @Test
    public void testJacksonDeserializationNull()
    throws Throwable {
        final String json = "null";
        final ObjectMapper mapper = new ObjectMapper();
        assertNull(mapper.readValue(json, Identity.class));
        assertNull(mapper.readValue(json, TokenIdentity.class));
    }

    /**
     * Test {@link TokenIdentity} and {@link Identity} JAXB serialization.
     * @throws Throwable Should not happen
     */
    @Test
    public void testJaxbAttributeSerializationIdentity()
    throws Throwable {
        final String identityToken = "mock identity token";
        final Identity identity = mock(Identity.class);
        given(identity.getIdentityToken()).willReturn(identityToken);
        final IdentityAttributeContainer container = new IdentityAttributeContainer();
        container.setValue(identity);
        final String expectedResult = String.format(
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                    "<container value=\"%s\"/>",
                identityToken);
        final JAXBContext context = JAXBContext.newInstance(IdentityAttributeContainer.class);
        final Marshaller marshaller = context.createMarshaller();
        final StringWriter writer = new StringWriter();
        marshaller.marshal(container, writer);
        assertEquals(expectedResult, writer.toString());
    }

    /**
     * Test {@link TokenIdentity} and {@link Identity} JAXB serialization.
     * @throws Throwable Should not happen
     */
    @Test
    public void testJaxbAttributeSerializationTokenIdentity()
    throws Throwable {
        final String identityToken = "mock identity token";
        final TokenIdentity tokenIdentity = new TokenIdentity(identityToken);
        final IdentityAttributeContainer container = new IdentityAttributeContainer();
        container.setValue(tokenIdentity);
        final String expectedResult = String.format(
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<container value=\"%s\"/>",
                identityToken);
        final JAXBContext context = JAXBContext.newInstance(IdentityAttributeContainer.class);
        final Marshaller marshaller = context.createMarshaller();
        final StringWriter writer = new StringWriter();
        marshaller.marshal(container, writer);
        assertEquals(expectedResult, writer.toString());
    }

    /**
     * Test {@link TokenIdentity} and {@link Identity} JAXB serialization.
     * @throws Throwable Should not happen
     */
    @Test
    public void testJaxbAttributeSerializationIdentityNull()
    throws Throwable {
        final IdentityAttributeContainer container = new IdentityAttributeContainer();
        final String expectedResult =
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                    "<container/>";
        final JAXBContext context = JAXBContext.newInstance(IdentityAttributeContainer.class);
        final Marshaller marshaller = context.createMarshaller();
        final StringWriter writer = new StringWriter();
        marshaller.marshal(container, writer);
        assertEquals(expectedResult, writer.toString());
    }

    /**
     * Test {@link TokenIdentity} and {@link Identity} Jaxb deserialization.
     * @throws Throwable Should not happen
     */
    @Test
    public void testJaxbAttributeDeserializationIdentity()
    throws Throwable {
        final String identityToken = "mock identity token";
        final String xml = String.format(
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<container value=\"%s\"/>",
                identityToken);
        final TokenIdentity tokenIdentity = new TokenIdentity(identityToken);
        final JAXBContext context = JAXBContext.newInstance(IdentityAttributeContainer.class);
        final Unmarshaller unmarshaller = context.createUnmarshaller();
        final StringReader reader = new StringReader(xml);
        final IdentityAttributeContainer result = (IdentityAttributeContainer) unmarshaller.unmarshal(reader);
        assertNotNull(result);
        assertNotNull(result.value);
        assertEquals(tokenIdentity, result.value);
    }

    /**
     * Test {@link TokenIdentity} and {@link Identity} Jaxb deserialization.
     * @throws Throwable Should not happen
     */
    @Test
    public void testJaxbAttributeDeserializationIdentityNull()
    throws Throwable {
        final String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<container />";
        final JAXBContext context = JAXBContext.newInstance(IdentityAttributeContainer.class);
        final Unmarshaller unmarshaller = context.createUnmarshaller();
        final StringReader reader = new StringReader(xml);
        final IdentityAttributeContainer result = (IdentityAttributeContainer) unmarshaller.unmarshal(reader);
        assertNotNull(result);
        assertNull(result.value);
    }

    /**
     * Test {@link TokenIdentity} and {@link Identity} Jaxb deserialization.
     * @throws Throwable Should not happen
     */
    @Test
    public void testJaxbAttributeDeserializationIdentityEmpty()
    throws Throwable {
        final String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<container value=\"\" />";
        final JAXBContext context = JAXBContext.newInstance(IdentityAttributeContainer.class);
        final Unmarshaller unmarshaller = context.createUnmarshaller();
        final StringReader reader = new StringReader(xml);
        final IdentityAttributeContainer result = (IdentityAttributeContainer) unmarshaller.unmarshal(reader);
        assertNotNull(result);
        assertNull(result.value);
    }

    /**
     * Test {@link TokenIdentity} and {@link Identity} JAXB serialization.
     * @throws Throwable Should not happen
     */
    @Test
    public void testJaxbElementSerializationIdentity()
    throws Throwable {
        final String identityToken = "mock identity token";
        final Identity identity = mock(Identity.class);
        given(identity.getIdentityToken()).willReturn(identityToken);
        final IdentityElementContainer container = new IdentityElementContainer();
        container.setValue(identity);
        final String expectedResult = String.format(
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                    "<container>" +
                    "<value>%s</value>" +
                    "</container>",
                identityToken);
        final JAXBContext context = JAXBContext.newInstance(IdentityElementContainer.class);
        final Marshaller marshaller = context.createMarshaller();
        final StringWriter writer = new StringWriter();
        marshaller.marshal(container, writer);
        assertEquals(expectedResult, writer.toString());
    }

    /**
     * Test {@link TokenIdentity} and {@link Identity} JAXB serialization.
     * @throws Throwable Should not happen
     */
    @Test
    public void testJaxbElementSerializationTokenIdentity()
    throws Throwable {
        final String identityToken = "mock identity token";
        final TokenIdentity tokenIdentity = new TokenIdentity(identityToken);
        final IdentityElementContainer container = new IdentityElementContainer();
        container.setValue(tokenIdentity);
        final String expectedResult = String.format(
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                    "<container>" +
                    "<value>%s</value>" +
                    "</container>",
                identityToken);
        final JAXBContext context = JAXBContext.newInstance(IdentityElementContainer.class);
        final Marshaller marshaller = context.createMarshaller();
        final StringWriter writer = new StringWriter();
        marshaller.marshal(container, writer);
        assertEquals(expectedResult, writer.toString());
    }

    /**
     * Test {@link TokenIdentity} and {@link Identity} JAXB serialization.
     * @throws Throwable Should not happen
     */
    @Test
    public void testJaxbElementSerializationIdentityNull()
    throws Throwable {
        final IdentityElementContainer container = new IdentityElementContainer();
        final String expectedResult =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                    "<container/>";
        final JAXBContext context = JAXBContext.newInstance(IdentityElementContainer.class);
        final Marshaller marshaller = context.createMarshaller();
        final StringWriter writer = new StringWriter();
        marshaller.marshal(container, writer);
        assertEquals(expectedResult, writer.toString());
    }

    /**
     * Test {@link TokenIdentity} and {@link Identity} Jaxb deserialization.
     * @throws Throwable Should not happen
     */
    @Test
    public void testJaxbElementDeserializationIdentity()
    throws Throwable {
        final String identityToken = "mock identity token";
        final String xml = String.format(
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                    "<container>" +
                    "<value>%s</value>" +
                    "</container>",
                identityToken);
        final TokenIdentity tokenIdentity = new TokenIdentity(identityToken);
        final JAXBContext context = JAXBContext.newInstance(IdentityElementContainer.class);
        final Unmarshaller unmarshaller = context.createUnmarshaller();
        final StringReader reader = new StringReader(xml);
        final IdentityElementContainer result = (IdentityElementContainer) unmarshaller.unmarshal(reader);
        assertNotNull(result);
        assertNotNull(result.value);
        assertEquals(tokenIdentity, result.value);
    }

    /**
     * Test {@link TokenIdentity} and {@link Identity} Jaxb deserialization.
     * @throws Throwable Should not happen
     */
    @Test
    public void testJaxbElementDeserializationIdentityNull()
    throws Throwable {
        final String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                    "<container />";
        final JAXBContext context = JAXBContext.newInstance(IdentityElementContainer.class);
        final Unmarshaller unmarshaller = context.createUnmarshaller();
        final StringReader reader = new StringReader(xml);
        final IdentityElementContainer result = (IdentityElementContainer) unmarshaller.unmarshal(reader);
        assertNotNull(result);
        assertNull(result.value);
    }

    /**
     * Test {@link TokenIdentity} and {@link Identity} Jaxb deserialization.
     * @throws Throwable Should not happen
     */
    @Test
    public void testJaxbElementDeserializationIdentityNull2()
    throws Throwable {
        final String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                    "<container>" +
                    "</container>";
        final JAXBContext context = JAXBContext.newInstance(IdentityElementContainer.class);
        final Unmarshaller unmarshaller = context.createUnmarshaller();
        final StringReader reader = new StringReader(xml);
        final IdentityElementContainer result = (IdentityElementContainer) unmarshaller.unmarshal(reader);
        assertNotNull(result);
        assertNull(result.value);
    }

    /**
     * Test {@link TokenIdentity} and {@link Identity} Jaxb deserialization.
     * @throws Throwable Should not happen
     */
    @Test
    public void testJaxbElementDeserializationIdentityNull3()
    throws Throwable {
        final String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                    "<container xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                    "<value xsi:nil=\"true\" />" +
                    "</container>";
        final JAXBContext context = JAXBContext.newInstance(IdentityElementContainer.class);
        final Unmarshaller unmarshaller = context.createUnmarshaller();
        final StringReader reader = new StringReader(xml);
        final IdentityElementContainer result = (IdentityElementContainer) unmarshaller.unmarshal(reader);
        assertNotNull(result);
        assertNull(result.value);
    }

    /**
     * Test {@link TokenIdentity} and {@link Identity} Jaxb deserialization.
     * @throws Throwable Should not happen
     */
    @Test
    public void testJaxbElementDeserializationIdentityEmpty()
    throws Throwable {
        final String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                    "<container>" +
                    "<value></value>" +
                    "</container>";
        final JAXBContext context = JAXBContext.newInstance(IdentityElementContainer.class);
        final Unmarshaller unmarshaller = context.createUnmarshaller();
        final StringReader reader = new StringReader(xml);
        final IdentityElementContainer result = (IdentityElementContainer) unmarshaller.unmarshal(reader);
        assertNotNull(result);
        assertNull(result.value);
    }

    /**
     * Test {@link TokenIdentity} and {@link Identity} Jaxb deserialization.
     * @throws Throwable Should not happen
     */
    @Test
    public void testJaxbElementDeserializationIdentityEmpty2()
    throws Throwable {
        final String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                    "<container>" +
                    "<value />" +
                    "</container>";
        final JAXBContext context = JAXBContext.newInstance(IdentityElementContainer.class);
        final Unmarshaller unmarshaller = context.createUnmarshaller();
        final StringReader reader = new StringReader(xml);
        final IdentityElementContainer result = (IdentityElementContainer) unmarshaller.unmarshal(reader);
        assertNotNull(result);
        assertNull(result.value);
    }

    @XmlRootElement(name = "container")
    private static class IdentityAttributeContainer {
        private Identity value;
        @XmlAttribute(name="value")
        public Identity getValue() {
            return value;
        }
        public void setValue(Identity value) {
            this.value = value;
        }
    }

    @XmlRootElement(name = "container")
    private static class IdentityElementContainer {
        private Identity value;
        @XmlElement(name="value")
        public Identity getValue() {
            return value;
        }
        public void setValue(Identity value) {
            this.value = value;
        }
    }
}
