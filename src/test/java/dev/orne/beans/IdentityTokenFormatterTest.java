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

import java.util.Random;

import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

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

    /** The random value generator. */
    private static final Random RND = new Random();

    /**
     * Generates a random {@code String}.
     * 
     * @return a random {@code String}.
     */
    protected @NotNull String randomValue() {
        final byte[] bodyBytes = new byte[RND.nextInt(100) + 1];
        RND.nextBytes(bodyBytes);
        return new String(bodyBytes);
    }

    /**
     * Test {@link IdentityTokenFormatter#encodeBody(String)}.
     */
    @Test
    void testEncodeBodyNull() {
        final String body = null;
        final String result = IdentityTokenFormatter.encodeBody(body);
        assertEquals(IdentityTokenFormatter.NULL_TOKEN, result);
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
    void testEncodeBodyValid() {
        final String body = "ThisIsAValidBody";
        final String result = IdentityTokenFormatter.encodeBody(body);
        assertEquals(body, result);
    }

    /**
     * Test {@link IdentityTokenFormatter#encodeBody(String)}.
     */
    @Test
    void testEncodeBodyB32Length1() {
        final String body = ".";
        final String expectedResult =
                IdentityTokenFormatter.B32_ENCODED_BODY_PREFIX +
                "FY";
        final String result = IdentityTokenFormatter.encodeBody(body);
        assertEquals(expectedResult, result);
    }

    /**
     * Test {@link IdentityTokenFormatter#encodeBody(String)}.
     */
    @Test
    void testEncodeBodyB32Length2() {
        final String body = ".a";
        final String expectedResult =
                IdentityTokenFormatter.B32_ENCODED_BODY_PREFIX +
                "FZQQ";
        final String result = IdentityTokenFormatter.encodeBody(body);
        assertEquals(expectedResult, result);
    }

    /**
     * Test {@link IdentityTokenFormatter#encodeBody(String)}.
     */
    @Test
    void testEncodeBodyB32Length3() {
        final String body = ".aa";
        final String expectedResult =
                IdentityTokenFormatter.B32_ENCODED_BODY_PREFIX +
                "FZQWC";
        final String result = IdentityTokenFormatter.encodeBody(body);
        assertEquals(expectedResult, result);
    }

    /**
     * Test {@link IdentityTokenFormatter#encodeBody(String)}.
     */
    @Test
    void testEncodeBodyB32Length4() {
        final String body = ".aaa";
        final String expectedResult =
                IdentityTokenFormatter.B32_ENCODED_BODY_PREFIX +
                "FZQWCYI";
        final String result = IdentityTokenFormatter.encodeBody(body);
        assertEquals(expectedResult, result);
    }

    /**
     * Test {@link IdentityTokenFormatter#encodeBody(String)}.
     */
    @Test
    void testEncodeBodyB32Length5() {
        final String body = ".aaaa";
        final String expectedResult =
                IdentityTokenFormatter.B32_ENCODED_BODY_PREFIX +
                "FZQWCYLB";
        final String result = IdentityTokenFormatter.encodeBody(body);
        assertEquals(expectedResult, result);
    }

    /**
     * Test {@link IdentityTokenFormatter#encodeBody(String)}.
     */
    @Test
    void testEncodeBodyDecimal() {
        final String body = "4455456057.5454861231321357";
        final String expectedResult =
                IdentityTokenFormatter.B32_ENCODED_BODY_PREFIX +
                "GQ2DKNJUGU3DANJXFY2TINJUHA3DCMRTGEZTEMJTGU3Q";
        final String result = IdentityTokenFormatter.encodeBody(body);
        assertEquals(expectedResult, result);
    }

    /**
     * Test {@link IdentityTokenFormatter#encodeBody(String)}.
     */
    @Test
    void testEncodeBody() {
        final String body = randomValue();
        final String result = IdentityTokenFormatter.encodeBody(body);
        assertNotNull(result);
        assertTrue(result.startsWith(
                IdentityTokenFormatter.B32_ENCODED_BODY_PREFIX));
    }

    /**
     * Test {@link IdentityTokenFormatter#decodeBody(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testDecodeBodyNullEncodedBody()
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
    void testDecodeBodyInvalidEncodedBody()
    throws Throwable {
        assertThrows(UnrecognizedIdentityTokenException.class, () -> {
            IdentityTokenFormatter.decodeBody("invalid body");
        });
    }

    /**
     * Test {@link IdentityTokenFormatter#decodeBody(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testDecodeBodyNull()
    throws Throwable {
        final String result = IdentityTokenFormatter.decodeBody(
                IdentityTokenFormatter.NULL_TOKEN);
        assertNull(result);
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
    void testDecodeBodyValid()
    throws Throwable {
        final String body = "ThisIsAValidBody";
        final String result = IdentityTokenFormatter.decodeBody(body);
        assertEquals(body, result);
    }

    /**
     * Test {@link IdentityTokenFormatter#encodeBody(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testDecodeBodyB32Length1()
    throws Throwable {
        final String expectedResult = ".";
        final String encoded =
                IdentityTokenFormatter.B32_ENCODED_BODY_PREFIX +
                "FY";
        final String result = IdentityTokenFormatter.decodeBody(encoded);
        assertEquals(expectedResult, result);
    }

    /**
     * Test {@link IdentityTokenFormatter#encodeBody(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testDecodeBodyB32Length2()
    throws Throwable {
        final String expectedResult = ".a";
        final String encoded =
                IdentityTokenFormatter.B32_ENCODED_BODY_PREFIX +
                "FZQQ";
        final String result = IdentityTokenFormatter.decodeBody(encoded);
        assertEquals(expectedResult, result);
    }

    /**
     * Test {@link IdentityTokenFormatter#encodeBody(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testDecodeBodyB32Length3()
    throws Throwable {
        final String expectedResult = ".aa";
        final String encoded =
                IdentityTokenFormatter.B32_ENCODED_BODY_PREFIX +
                "FZQWC";
        final String result = IdentityTokenFormatter.decodeBody(encoded);
        assertEquals(expectedResult, result);
    }

    /**
     * Test {@link IdentityTokenFormatter#encodeBody(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testDecodeBodyB32Length4()
    throws Throwable {
        final String expectedResult = ".aaa";
        final String encoded =
                IdentityTokenFormatter.B32_ENCODED_BODY_PREFIX +
                "FZQWCYI";
        final String result = IdentityTokenFormatter.decodeBody(encoded);
        assertEquals(expectedResult, result);
    }

    /**
     * Test {@link IdentityTokenFormatter#encodeBody(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testDecodeBodyB32Length5()
    throws Throwable {
        final String expectedResult = ".aaaa";
        final String encoded =
                IdentityTokenFormatter.B32_ENCODED_BODY_PREFIX +
                "FZQWCYLB";
        final String result = IdentityTokenFormatter.decodeBody(encoded);
        assertEquals(expectedResult, result);
    }

    /**
     * Test {@link IdentityTokenFormatter#encodeBody(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testDecodeBodyDecimal()
    throws Throwable {
        final String expectedResult = "4455456057.5454861231321357";
        final String encoded =
                IdentityTokenFormatter.B32_ENCODED_BODY_PREFIX +
                "GQ2DKNJUGU3DANJXFY2TINJUHA3DCMRTGEZTEMJTGU3Q";
        final String result = IdentityTokenFormatter.decodeBody(encoded);
        assertEquals(expectedResult, result);
    }

    /**
     * Test {@link IdentityTokenFormatter#encodeBody(String)}.
     * @throws Throwable Should not happen
     */
    @Test
    void testDecodeBody()
    throws Throwable {
        final String body = randomValue();
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
        final String body = randomValue();
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
        final String body = randomValue();
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
        final String body = randomValue();
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
        final String body = randomValue();
        final String token =
                prefix +
                IdentityTokenFormatter.encodeBody(body);
        final String result = IdentityTokenFormatter.parse(prefix, token);
        assertNotNull(result);
        assertEquals(body, result);
    }
}
