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

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Unit tests for {@code IdentityTokenFormatter}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see IdentityTokenFormatter
 */
@Tag("ut")
class IdentityTokenFormatterTest {

    /**
     * Test {@link IdentityTokenFormatter#encodeBody(String)}.
     */
    @ParameterizedTest
    @CsvSource({
        "," + IdentityTokenFormatter.NULL_BODY,
        "ThisIsAValidBody,ThisIsAValidBody",
        ".," + IdentityTokenFormatter.ENCODED_BODY_PREFIX + "Lg",
        ".a," + IdentityTokenFormatter.ENCODED_BODY_PREFIX + "LmE",
        ".aa," + IdentityTokenFormatter.ENCODED_BODY_PREFIX + "LmFh",
        ".aaa," + IdentityTokenFormatter.ENCODED_BODY_PREFIX + "LmFhYQ",
        ".aaaa," + IdentityTokenFormatter.ENCODED_BODY_PREFIX + "LmFhYWE",
        "4455456057.5454861231321357," + IdentityTokenFormatter.ENCODED_BODY_PREFIX + "NDQ1NTQ1NjA1Ny41NDU0ODYxMjMxMzIxMzU3",
    })
    void testEncodeBody(
            final String body,
            final String expectedResult) {
        final String result = IdentityTokenFormatter.encodeBody(body);
        assertEquals(expectedResult, result);
    }

    /**
     * Test {@link IdentityTokenFormatter#encodeBody(String)}.
     */
    @Test
    void testEncodeBodyEmpty() {
        final String body = "";
        final String result = IdentityTokenFormatter.encodeBody(body);
        assertEquals(body, result);
    }

    /**
     * Test {@link IdentityTokenFormatter#encodeBody(String)}.
     */
    @Test
    void testEncodeInvalidBody() {
        String body = RandomStringUtils.random(20);
        while (body.matches(IdentityTokenFormatter.UNENCODED_BODY)) {
            body = RandomStringUtils.random(20);
        }
        final String result = IdentityTokenFormatter.encodeBody(body);
        assertNotNull(result);
        assertTrue(result.startsWith(
                IdentityTokenFormatter.ENCODED_BODY_PREFIX));
    }

    /**
     * Test {@link IdentityTokenFormatter#decodeBody(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testDecodeBodyNull()
    throws Throwable {
        assertThrows(NullPointerException.class, () -> {
            IdentityTokenFormatter.decodeBody(null);
        });
    }

    /**
     * Test {@link IdentityTokenFormatter#decodeBody(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testDecodeBodyInvalid()
    throws Throwable {
        assertThrows(UnrecognizedIdentityTokenException.class, () -> {
            IdentityTokenFormatter.decodeBody("invalid body");
        });
    }

    /**
     * Test {@link IdentityTokenFormatter#encodeBody(String)}.
     * @throws Throwable Should not happen
     */
    @ParameterizedTest
    @CsvSource({
        IdentityTokenFormatter.NULL_BODY + ",",
        "ThisIsAValidBody,ThisIsAValidBody",
        IdentityTokenFormatter.ENCODED_BODY_PREFIX + "Lg" + ",.",
        IdentityTokenFormatter.ENCODED_BODY_PREFIX + "LmE" + ",.a",
        IdentityTokenFormatter.ENCODED_BODY_PREFIX + "LmFh" + ",.aa",
        IdentityTokenFormatter.ENCODED_BODY_PREFIX + "LmFhYQ" + ",.aaa",
        IdentityTokenFormatter.ENCODED_BODY_PREFIX + "LmFhYWE" + ",.aaaa",
        IdentityTokenFormatter.ENCODED_BODY_PREFIX + "NDQ1NTQ1NjA1Ny41NDU0ODYxMjMxMzIxMzU3" + ",4455456057.5454861231321357",
    })
    void testDecodeBody(
            final String body,
            final String expectedResult)
    throws Throwable {
        final String result = IdentityTokenFormatter.decodeBody(body);
        assertEquals(expectedResult, result);
    }

    /**
     * Test {@link IdentityTokenFormatter#encodeBody(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testDecodeBodyEmpty()
    throws Throwable {
        final String body = "";
        final String result = IdentityTokenFormatter.decodeBody(body);
        assertEquals(body, result);
    }

    /**
     * Test {@link IdentityTokenFormatter#encodeBody(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testDecodeEncodedBody()
    throws Throwable {
        String body = RandomStringUtils.random(20);
        while (body.matches(IdentityTokenFormatter.UNENCODED_BODY)) {
            body = RandomStringUtils.random(20);
        }
        final String encoded = IdentityTokenFormatter.encodeBody(body);
        final String result = IdentityTokenFormatter.decodeBody(encoded);
        assertNotNull(result);
        assertEquals(body, result);
    }

    /**
     * Test {@link IdentityTokenFormatter#format(String)}.
     */
    @Test
    void testFormat() {
        final String body = RandomStringUtils.random(20);
        final String expectedResult =
                IdentityTokenFormatter.DEFAULT_PREFIX +
                IdentityTokenFormatter.encodeBody(body);
        final String result = IdentityTokenFormatter.format(body);
        assertNotNull(result);
        assertEquals(expectedResult, result);
    }

    /**
     * Test {@link IdentityTokenFormatter#format(String, String)}.
     */
    @Test
    void testFormatPrefixNullPrefix() {
        final String body = "valid unencoded body";
        assertThrows(NullPointerException.class, () -> {
            IdentityTokenFormatter.format(null, body);
        });
    }

    /**
     * Test {@link IdentityTokenFormatter#format(String, String)}.
     */
    @Test
    void testFormatPrefixInvalidPrefix() {
        final String body = "valid unencoded body";
        assertThrows(IllegalArgumentException.class, () -> {
            IdentityTokenFormatter.format("invalid prefix", body);
        });
    }

    /**
     * Test {@link IdentityTokenFormatter#format(String, String)}.
     */
    @Test
    void testFormatPrefix() {
        final String prefix = "CustomPrefix";
        final String body = RandomStringUtils.random(20);
        final String expectedResult =
                prefix +
                IdentityTokenFormatter.encodeBody(body);
        final String result = IdentityTokenFormatter.format(prefix, body);
        assertNotNull(result);
        assertEquals(expectedResult, result);
    }

    /**
     * Test {@link IdentityTokenFormatter#parse(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testParseNullToken()
    throws Throwable {
        assertThrows(NullPointerException.class, () -> {
            IdentityTokenFormatter.parse(null);
        });
    }

    /**
     * Test {@link IdentityTokenFormatter#parse(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testParseInvalidToken()
    throws Throwable {
        assertThrows(UnrecognizedIdentityTokenException.class, () -> {
            IdentityTokenFormatter.parse("invalid token");
        });
    }

    /**
     * Test {@link IdentityTokenFormatter#parse(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testParse()
    throws Throwable {
        final String body = RandomStringUtils.random(20);
        final String token =
                IdentityTokenFormatter.DEFAULT_PREFIX +
                IdentityTokenFormatter.encodeBody(body);
        final String result = IdentityTokenFormatter.parse(token);
        assertNotNull(result);
        assertEquals(body, result);
    }

    /**
     * Test {@link IdentityTokenFormatter#parse(String, String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testParsePrefixNullPrefix() {
        final String token = "IDVALID";
        assertThrows(NullPointerException.class, () -> {
            IdentityTokenFormatter.parse(null, token);
        });
    }

    /**
     * Test {@link IdentityTokenFormatter#parse(String, String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testParsePrefixInvalidPrefix() {
        final String token = "IDVALID";
        assertThrows(IllegalArgumentException.class, () -> {
            IdentityTokenFormatter.parse("invalid prefix", token);
        });
    }

    /**
     * Test {@link IdentityTokenFormatter#parse(String, String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testParsePrefixNullToken() {
        final String prefix = "CustomPrefix";
        assertThrows(NullPointerException.class, () -> {
            IdentityTokenFormatter.parse(prefix, null);
        });
    }

    /**
     * Test {@link IdentityTokenFormatter#parse(String, String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testParsePrefixInvalidToken() {
        final String prefix = "CustomPrefix";
        assertThrows(UnrecognizedIdentityTokenException.class, () -> {
            IdentityTokenFormatter.parse(prefix, "invalid token");
        });
    }

    /**
     * Test {@link IdentityTokenFormatter#parse(String, String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testParsePrefixUnexpectedTokenPrefix() {
        final String prefix = "CustomPrefix";
        assertThrows(UnrecognizedIdentityTokenException.class, () -> {
            IdentityTokenFormatter.parse(prefix, "OtherPrefix");
        });
    }

    /**
     * Test {@link IdentityTokenFormatter#parse(String, String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testParsePrefix()
            throws Throwable  {
        final String prefix = "CustomPrefix";
        final String body = RandomStringUtils.random(20);
        final String token =
                prefix +
                IdentityTokenFormatter.encodeBody(body);
        final String result = IdentityTokenFormatter.parse(prefix, token);
        assertNotNull(result);
        assertEquals(body, result);
    }

    /**
     * Test {@link IdentityTokenFormatter#parse(String, String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testParseEmptyPrefix()
            throws Throwable  {
        final String prefix = "";
        final String body = RandomStringUtils.random(20);
        final String token =
                prefix +
                IdentityTokenFormatter.encodeBody(body);
        final String result = IdentityTokenFormatter.parse(prefix, token);
        assertNotNull(result);
        assertEquals(body, result);
    }
}
