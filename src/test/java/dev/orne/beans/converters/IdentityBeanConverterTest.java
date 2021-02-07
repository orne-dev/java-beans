package dev.orne.beans.converters;

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

import org.apache.commons.beanutils.Converter;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.beans.BaseIdentityBean;
import dev.orne.beans.Identity;
import dev.orne.beans.IdentityBean;
import dev.orne.beans.TokenIdentity;

/**
 * Unit tests for {@code IdentityBeanConverter}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see IdentityBeanConverter
 */
@Tag("ut")
class IdentityBeanConverterTest
extends AbstractConverterTest {

    public IdentityBeanConverterTest() {
        super(BaseIdentityBean.class, new IdentityBeanConverter(BaseIdentityBean.class));
    }

    /**
     * Test for {@link IdentityBeanConverter#IdentityBeanConverter()}.
     */
    @Test
    void testConstructorEmpty() {
        final IdentityBeanConverter converter = new IdentityBeanConverter();
        assertSame(BaseIdentityBean.class, converter.getDefaultType());
        assertFalse(converter.isUseDefault());
        assertNotNull(converter.getIdentityConverter());
        assertTrue(converter.getIdentityConverter() instanceof IdentityConverter);
    }

    /**
     * Test for {@link IdentityBeanConverter#IdentityBeanConverter(IdentityBean)}.
     */
    @Test
    void testConstructorDefault() {
        final BaseIdentityBean defaultValue = mock(BaseIdentityBean.class);
        final IdentityBeanConverter converter = new IdentityBeanConverter(
                defaultValue);
        assertSame(BaseIdentityBean.class, converter.getDefaultType());
        assertTrue(converter.isUseDefault());
        assertNotNull(converter.getIdentityConverter());
        assertTrue(converter.getIdentityConverter() instanceof IdentityConverter);
    }

    /**
     * Test for {@link IdentityBeanConverter#IdentityBeanConverter(Converter)}.
     */
    @Test
    void testConstructorConverterEmpty() {
        final Converter idConverter = mock(Converter.class);
        final IdentityBeanConverter converter = new IdentityBeanConverter(
                idConverter);
        assertSame(BaseIdentityBean.class, converter.getDefaultType());
        assertFalse(converter.isUseDefault());
        assertSame(idConverter, converter.getIdentityConverter());
    }

    /**
     * Test for {@link IdentityBeanConverter#IdentityBeanConverter(Converter, IdentityBean)}.
     */
    @Test
    void testConstructorConverterDefault() {
        final Converter idConverter = mock(Converter.class);
        final BaseIdentityBean defaultValue = mock(BaseIdentityBean.class);
        final IdentityBeanConverter converter = new IdentityBeanConverter(
                idConverter,
                defaultValue);
        assertSame(BaseIdentityBean.class, converter.getDefaultType());
        assertTrue(converter.isUseDefault());
        assertSame(idConverter, converter.getIdentityConverter());
    }

    /**
     * Test for {@link IdentityBeanConverter#IdentityBeanConverter()}.
     */
    @Test
    void testConstructorClass() {
        final IdentityBeanConverter converter = new IdentityBeanConverter(
                TestIdentityBean.class);
        assertSame(TestIdentityBean.class, converter.getDefaultType());
        assertFalse(converter.isUseDefault());
        assertNotNull(converter.getIdentityConverter());
        assertTrue(converter.getIdentityConverter() instanceof IdentityConverter);
    }

    /**
     * Test for {@link IdentityBeanConverter#IdentityBeanConverter(IdentityBean)}.
     */
    @Test
    void testConstructorClassDefault() {
        final TestIdentityBean defaultValue = mock(TestIdentityBean.class);
        final IdentityBeanConverter converter = new IdentityBeanConverter(
                TestIdentityBean.class,
                defaultValue);
        assertSame(TestIdentityBean.class, converter.getDefaultType());
        assertTrue(converter.isUseDefault());
        assertNotNull(converter.getIdentityConverter());
        assertTrue(converter.getIdentityConverter() instanceof IdentityConverter);
    }

    /**
     * Test for {@link IdentityBeanConverter#IdentityBeanConverter(Converter)}.
     */
    @Test
    void testConstructorClassConverterEmpty() {
        final Converter idConverter = mock(Converter.class);
        final IdentityBeanConverter converter = new IdentityBeanConverter(
                TestIdentityBean.class,
                idConverter);
        assertSame(TestIdentityBean.class, converter.getDefaultType());
        assertFalse(converter.isUseDefault());
        assertSame(idConverter, converter.getIdentityConverter());
    }

    /**
     * Test for {@link IdentityBeanConverter#IdentityBeanConverter(Converter, IdentityBean)}.
     */
    @Test
    void testConstructorClassConverterDefault() {
        final Converter idConverter = mock(Converter.class);
        final TestIdentityBean defaultValue = mock(TestIdentityBean.class);
        final IdentityBeanConverter converter = new IdentityBeanConverter(
                TestIdentityBean.class,
                idConverter,
                defaultValue);
        assertSame(TestIdentityBean.class, converter.getDefaultType());
        assertTrue(converter.isUseDefault());
        assertSame(idConverter, converter.getIdentityConverter());
    }

    /**
     * Test {@link IdentityBeanConverter#convert(Class, Object)} when
     * {@code type} is {@code null} or {@code Identity} and
     * {@code value} is invalid.
     */
    @Test
    void testFromValueInvalidConversions() {
        final String mockIdentityToken = "mock identity token";
        final IdentityBeanConverter converter = new IdentityBeanConverter();
        assertFail(null);
        assertFail(converter, TestPrivateConstructorIdentityBean.class, mockIdentityToken);
        assertFail(converter, TestNonWritableIdentityBean.class, mockIdentityToken);
    }

    /**
     * Test {@link IdentityBeanConverter#convert(Class, Object)} when
     * {@code type} is {@code null} or {@code Identity}, {@code value}
     * is invalid and a default value is set.
     */
    @Test
    void testFromValueInvalidConversionsWithDefaultValue() {
        final String mockIdentityToken = "mock identity token";
        final TestIdentityBean defaultValue = new TestIdentityBean();
        final IdentityBeanConverter converter = new IdentityBeanConverter(defaultValue);
        assertSuccess(converter, null, null, defaultValue);
        assertSuccess(converter, TestIdentityBean.class, null, defaultValue);
        assertFail(converter, TestPrivateConstructorIdentityBean.class, mockIdentityToken);
        assertFail(converter, TestNonWritableIdentityBean.class, mockIdentityToken);
    }

    /**
     * Test {@link IdentityBeanConverter#convert(Class, Object)} when
     * {@code type} is {@code null} or {@code Identity} and
     * {@code value} is valid.
     */
    @Test
    void testFromValueValidConversions() {
        final Identity identity = mock(Identity.class);
        final String mockIdentityToken = "mock identity token";
        final TokenIdentity tokenIdentity = new TokenIdentity(mockIdentityToken);
        final BaseIdentityBean expectedTokenBean = new BaseIdentityBean();
        expectedTokenBean.setIdentity(tokenIdentity);
        final BaseIdentityBean expectedBean = new BaseIdentityBean();
        expectedBean.setIdentity(identity);
        final BaseIdentityBean expectedNumberBean = new BaseIdentityBean();
        expectedNumberBean.setIdentity(new TokenIdentity("123456"));
        assertSuccess("", null);
        assertSuccess(123456, expectedNumberBean);
        assertSuccess(mockIdentityToken, expectedTokenBean);
        assertSuccess(tokenIdentity, expectedTokenBean);
        assertSuccess(identity, expectedBean);
        assertSuccess(expectedBean, expectedBean);
    }

    /**
     * Test {@link IdentityBeanConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is invalid.
     */
    @Test
    void testInvalidToStringConversions() {
        assertFail(converter, String.class, 123456);
        
    }

    /**
     * Test {@link IdentityBeanConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is invalid.
     */
    @Test
    void testInvalidToStringConversionsWithDefaultValue() {
        final IdentityBean defaultValue = null;
        final IdentityBeanConverter converter = new IdentityBeanConverter(defaultValue);
        assertSuccess(converter, String.class, 123456, defaultValue);
    }

    /**
     * Test {@link IdentityBeanConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is valid.
     */
    @Test
    void testValidToStringConversions() {
        final Identity identity = mock(Identity.class);
        final String mockIdentityToken = "mock identity token";
        final TokenIdentity tokenIdentity = new TokenIdentity(mockIdentityToken);
        final BaseIdentityBean tokenBean = new BaseIdentityBean();
        tokenBean.setIdentity(tokenIdentity);
        final BaseIdentityBean bean = new BaseIdentityBean();
        bean.setIdentity(identity);
        given(identity.getIdentityToken()).willReturn(mockIdentityToken);
        assertNull(converter.convert(String.class, null));
        assertSuccess(converter, String.class, "test string", "test string");
        assertSuccess(converter, String.class, tokenBean, mockIdentityToken);
        assertSuccess(converter, String.class, bean, mockIdentityToken);
    }

    public static class TestIdentityBean
    extends BaseIdentityBean {
        // No extra methods
    }
    public static class TestNonWritableIdentityBean
    implements IdentityBean {
        /**
         * {@inheritDoc}
         */
        @Override
        public Identity getIdentity() {
            return null;
        }
    }
    public static class TestPrivateConstructorIdentityBean
    extends BaseIdentityBean {
        /**
         * Private constructor.
         */
        private TestPrivateConstructorIdentityBean() {
            super();
        }
    }
    public static class TestInstantiationExceptionIdentityBean
    extends BaseIdentityBean {
        /**
         * Constructor that fails.
         */
        public TestInstantiationExceptionIdentityBean() {
            throw new UnsupportedOperationException("Mock exception");
        }
    }
}
