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

import java.net.URI;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@code UriConverter}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-08
 * @since 0.3
 * @see UriConverter
 */
@Tag("ut")
class UriConverterTest
extends AbstractConverterTest {

    private static final URI EMPTY_URI = URI.create("");
    private static final URI NUMBER_URI = URI.create("123456");
    private static final URI ROOT_URI = URI.create("/");
    private static final URI SERVER_URI = URI.create("scope://example.org:1234/");
    private static final URI FULL_URI = URI.create("scp://example.org:1234/xx/YY/ZZZ");
    private static final URI ABSOLUTE_PATH_URI = URI.create("/xx/YY/ZZZ");
    private static final URI RELATIVE_PATH_URI = URI.create("xx/YY/ZZZ");

    public UriConverterTest() {
        super(URI.class, new UriConverter());
    }

    /**
     * Test {@link UriConverter#convert(Class, Object)} when
     * {@code type} is {@code null} or {@code Locale} and
     * {@code value} is invalid.
     */
    @Test
    void testFromValueInvalidConversions() {
        assertFail(null);
        assertFail(":invalid:uri");
    }

    /**
     * Test {@link UriConverter#convert(Class, Object)} when
     * {@code type} is {@code null} or {@code Locale}, {@code value}
     * is invalid and a default value is set.
     */
    @Test
    void testFromValueInvalidConversionsWithDefaultValue() {
        final URI defaultValue = null;
        final UriConverter converter = new UriConverter(defaultValue);
        assertSuccess(converter, null, defaultValue, defaultValue);
        assertSuccess(converter, ":invalid:uri", defaultValue, defaultValue);
    }

    /**
     * Test {@link UriConverter#convert(Class, Object)} when
     * {@code type} is {@code null} or {@code Locale} and
     * {@code value} is valid.
     */
    @Test
    void testFromValueValidConversions() {
        assertSuccess("", EMPTY_URI);
        assertSuccess(123456L, NUMBER_URI);
        assertSuccess(ROOT_URI.toString(), ROOT_URI);
        assertSuccess(SERVER_URI.toString(), SERVER_URI);
        assertSuccess(FULL_URI.toString(), FULL_URI);
        assertSuccess(ABSOLUTE_PATH_URI.toString(), ABSOLUTE_PATH_URI);
        assertSuccess(RELATIVE_PATH_URI.toString(), RELATIVE_PATH_URI);
    }

    /**
     * Test {@link UriConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is invalid.
     */
    @Test
    void testInvalidToStringConversions() {
        assertFail(converter, String.class, 123456);
        
    }

    /**
     * Test {@link UriConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is invalid.
     */
    @Test
    void testInvalidToStringConversionsWithDefaultValue() {
        final URI defaultValue = null;
        final UriConverter converter = new UriConverter(defaultValue);
        assertSuccess(converter, String.class, 123456, defaultValue);
    }

    /**
     * Test {@link UriConverter#convert(Class, Object)} when
     * {@code type} is {@code String} and {@code value} is valid.
     */
    @Test
    void testValidToStringConversions() {
        assertNull(converter.convert(String.class, null));
        assertSuccess(converter, String.class, "", "");
        assertSuccess(converter, String.class, ROOT_URI.toString(), ROOT_URI.toString());
        assertSuccess(converter, String.class, "test string", "test string");
        assertSuccess(converter, String.class, EMPTY_URI, "");
        assertSuccess(converter, String.class, ROOT_URI, ROOT_URI.toString());
        assertSuccess(converter, String.class, SERVER_URI, SERVER_URI.toString());
        assertSuccess(converter, String.class, FULL_URI, FULL_URI.toString());
        assertSuccess(converter, String.class, ABSOLUTE_PATH_URI, ABSOLUTE_PATH_URI.toString());
        assertSuccess(converter, String.class, RELATIVE_PATH_URI, RELATIVE_PATH_URI.toString());
    }
}
