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

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.beans.Identity;
import dev.orne.beans.TokenIdentity;

/**
 * Unit tests for {@code IdentityConverter}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see IdentityConverter
 */
@Tag("ut")
public class IdentityConverterTest
extends AbstractConverterTest {

    public IdentityConverterTest() {
        super(Identity.class, new IdentityConverter());
    }

    /**
     * Test {@link IdentityConverter#convert(Class, Object)} when
     * {@code type} is {@code null} or {@code Identity} and
     * {@code value} is invalid.
     */
    @Test
    public void testFromValueInvalidConversions() {
        assertFail(null);
    }

    /**
     * Test {@link IdentityConverter#convert(Class, Object)} when
     * {@code type} is {@code null} or {@code Identity}, {@code value}
     * is invalid and a default value is set.
     */
    @Test
    public void testFromValueInvalidConversionsWithDefaultValue() {
        final Identity defaultValue = null;
        final IdentityConverter converter = new IdentityConverter(defaultValue);
        assertSuccess(converter, null, defaultValue, defaultValue);
    }

    /**
     * Test {@link IdentityConverter#convert(Class, Object)} when
     * {@code type} is {@code null} or {@code Identity} and
     * {@code value} is valid.
     */
    @Test
    public void testFromValueValidConversions() {
        final Identity identity = mock(Identity.class);
        final String mockIdentityToken = "mock identity token";
        final TokenIdentity tokenIdentity = new TokenIdentity(mockIdentityToken);
        assertSuccess("", null);
        assertSuccess(123456, new TokenIdentity("123456"));
        assertSuccess(mockIdentityToken, tokenIdentity);
        assertSuccess(tokenIdentity, tokenIdentity);
        assertSuccess(identity, identity);
    }

    /**
     * Test {@link IdentityConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is invalid.
     */
    @Test
    public void testInvalidToStringConversions() {
        assertFail(converter, String.class, 123456);
        
    }

    /**
     * Test {@link IdentityConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is invalid.
     */
    @Test
    public void testInvalidToStringConversionsWithDefaultValue() {
        final Identity defaultValue = null;
        final IdentityConverter converter = new IdentityConverter(defaultValue);
        assertSuccess(converter, String.class, 123456, defaultValue);
    }

    /**
     * Test {@link IdentityConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is valid.
     */
    @Test
    public void testValidToStringConversions() {
        final Identity identity = mock(Identity.class);
        final String mockIdentityToken = "mock identity token";
        final TokenIdentity tokenIdentity = new TokenIdentity(mockIdentityToken);
        given(identity.getIdentityToken()).willReturn(mockIdentityToken);
        assertNull(converter.convert(String.class, null));
        assertSuccess(converter, String.class, "test string", "test string");
        assertSuccess(converter, String.class, mockIdentityToken, mockIdentityToken);
        assertSuccess(converter, String.class, identity, mockIdentityToken);
        assertSuccess(converter, String.class, tokenIdentity, mockIdentityToken);
    }
}
