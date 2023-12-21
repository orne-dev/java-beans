package dev.orne.beans;

/*-
 * #%L
 * Orne Beans
 * %%
 * Copyright (C) 2020 - 2023 Orne Developments
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

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * Formatter and parser of identity tokens.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 2.0, 2023-12
 * @since 0.1
 */
public final class IdentityTokenFormatter {

    /** Regular expression for valid token characters. */
    public static final String TOKEN_CHAR = "[\\w-]";
    /** Regular expression for valid token prefix strings. */
    public static final String PREFIX =  "^" + TOKEN_CHAR + "*$";
    /** Regular expression for valid unencoded body starting character. */
    public static final String UNENCODED_BODY_STARTING_CHAR = "[a-zA-Z0-9-]";
    /** Regular expression for valid identity token unencoded bodies. */
    public static final String UNENCODED_BODY =
            UNENCODED_BODY_STARTING_CHAR + TOKEN_CHAR + "*";
    /** Encoded body prefix. */
    public static final String ENCODED_BODY_PREFIX = "_";
    /** Null body. */
    public static final String NULL_BODY = ENCODED_BODY_PREFIX;
    /** Regular expression for valid identity token Base64 encoded bodies. */
    public static final String ENCODED_BODY =
            ENCODED_BODY_PREFIX + TOKEN_CHAR + "*";
    /** Regular expression for valid identity token bodies. */
    public static final String BODY =
            "(?:"
                + "|"
                + UNENCODED_BODY + "|"
                + ENCODED_BODY
            + ")";
    /** Regular expression for valid identity tokens. */
    public static final String TOKEN =
            "^" + TOKEN_CHAR + "*$";

    /** The Base64 padding character. */
    private static final char B32_PADDING_CHAR = '=';

    /** Default identity token prefix. */
    public static final String DEFAULT_PREFIX = "";

    /** Compiled pattern to detect valid identity token prefixes as predicate. */
    protected static final Predicate<String> VALID_PREFIX_PREDICATE =
            Pattern.compile(PREFIX).asPredicate();
    /** Compiled pattern to detect valid identity token unencoded bodies as predicate. */
    protected static final Predicate<String> VALID_UNENCODED_BODY_PREDICATE =
            Pattern.compile("^" + UNENCODED_BODY + "$").asPredicate();
    /** Compiled pattern to detect valid identity token encoded bodies as predicate. */
    protected static final Predicate<String> VALID_ENCODED_BODY_PREDICATE =
            Pattern.compile("^" + ENCODED_BODY + "$").asPredicate();
    /** Compiled pattern to detect valid identity token bodies as predicate. */
    protected static final Predicate<String> VALID_BODY_PREDICATE =
            Pattern.compile("^" + BODY + "$").asPredicate();
    /** Compiled pattern to detect valid identity tokens as predicate. */
    protected static final Predicate<String> VALID_TOKEN_PREDICATE =
            Pattern.compile(TOKEN).asPredicate();

    /** The Base64 encoder. */
    private static final Base64.Encoder ENCODER = Base64.getUrlEncoder();
    /** The Base64 decoder. */
    private static final Base64.Decoder DECODER = Base64.getUrlDecoder();

    /**
     * Private constructor.
     */
    private IdentityTokenFormatter() {
        // Utility class
    }

    /**
     * <p>Encodes the specified identity token body as a valid identity token
     * body.</p>
     * 
     * <ol>
     * <li>If the specified body is {@code null} {@link #NULL_BODY} is
     * returned.</li>
     * <li>If the specified body is empty an empty {@code String} is
     * returned.</li>
     * <li>If the specified body is a valid unencoded body the passed body is
     * returned.</li>
     * <li>Otherwise the passed body is encoded in URI friendly Base64, with
     * paddings removed and prefixed by {@link #ENCODED_BODY_PREFIX}.</li>
     * </ol>
     * 
     * @param body The identity token body to encode
     * @return The encoded identity token part
     */
    protected static @NotNull String encodeBody(
            final String body) {
        final String result;
        if (body == null) {
            result = NULL_BODY;
        } else if (body.isEmpty() || VALID_UNENCODED_BODY_PREDICATE.test(body)) {
            result = body;
        } else {
            final String b64 = ENCODER.encodeToString(
                    body.getBytes(StandardCharsets.UTF_8));
            final String b64NoPadding = b64.replaceAll(String.valueOf(B32_PADDING_CHAR), "");
            result = new StringBuilder(ENCODED_BODY_PREFIX)
                    .append(b64NoPadding)
                    .toString();
        }
        return result;
    }

    /**
     * <p>Decodes the specified valid identity token body to the original identity
     * token body.</p>
     * 
     * @param encoded The valid identity token body to decode
     * @return The original identity token body
     * @throws NullPointerException If the encoded body is {@code null}
     * @throws UnrecognizedIdentityTokenException If the encoded body is not a
     * valid identity token body
     */
    protected static String decodeBody(
            final @NotNull String encoded)
    throws UnrecognizedIdentityTokenException {
        Validate.notNull(encoded, "Encoded identity token body is required");
        if (!VALID_BODY_PREDICATE.test(encoded)) {
            throw new UnrecognizedIdentityTokenException(String.format(
                    "Invalid identity token body: %s",
                    encoded));
        }
        final String result;
        if (encoded.isEmpty()) {
            result = encoded;
        } else if (ENCODED_BODY_PREFIX.equals(encoded)) {
            result = null;
        } else if (encoded.startsWith(ENCODED_BODY_PREFIX)) {
            String b64 = encoded.substring(1);
            if (b64.length() % 3 != 0) {
                b64 = StringUtils.rightPad(b64, 3 - (b64.length() % 3), B32_PADDING_CHAR);
            }
            result = new String(
                    DECODER.decode(b64),
                    StandardCharsets.UTF_8);
        } else {
            result = encoded;
        }
        return result;
    }

    /**
     * Formats a valid identity token for the specified identity token body
     * and the default identity token prefix.
     * 
     * @param body The identity token body
     * @return The formatted identity token
     */
    public static @NotNull String format(
            final String body) {
        return format(DEFAULT_PREFIX, body);
    }

    /**
     * Formats a valid identity token for the specified identity token prefix
     * and body.
     * 
     * @param prefix The identity token prefix
     * @param body The identity token body
     * @return The formatted identity token
     * @throws NullPointerException If the prefix is {@code null}
     * @throws IllegalArgumentException If the prefix is not a valid identity
     * token prefix
     */
    public static @NotNull String format(
            final @NotNull String prefix,
            final String body) {
        Validate.notNull(prefix, "Identity token prefix is required");
        if (!VALID_PREFIX_PREDICATE.test(prefix)) {
            throw new IllegalArgumentException(String.format(
                    "Identity token prefix is not valid: %s",
                    prefix));
        }
        return new StringBuilder(prefix)
                .append(encodeBody(body))
                .toString();
    }

    /**
     * Returns {@code true} if the specified identity token prefix is valid.
     * 
     * @param prefix The identity token prefix.
     * @return If the identity token prefix is valid.
     */
    public static boolean isValidPrefix(
            final @NotNull String prefix) {
        return VALID_PREFIX_PREDICATE.test(prefix);
    }

    /**
     * Returns {@code true} if the specified identity token body is valid.
     * 
     * @param body The identity token body.
     * @return If the identity token body is valid.
     */
    public static boolean isValidBody(
            final @NotNull String body) {
        return VALID_BODY_PREDICATE.test(body);
    }

    /**
     * Returns {@code true} if the specified identity token body is a valid
     * unencoded body.
     * 
     * @param body The identity token body.
     * @return If the identity token body is valid.
     */
    public static boolean isValidUncodedBody(
            final @NotNull String body) {
        return VALID_UNENCODED_BODY_PREDICATE.test(body);
    }

    /**
     * Returns {@code true} if the specified identity token body is a valid
     * encoded body.
     * 
     * @param body The identity token body.
     * @return If the identity token body is valid.
     */
    public static boolean isValidEncodedBody(
            final @NotNull String body) {
        return VALID_ENCODED_BODY_PREDICATE.test(body);
    }

    /**
     * Returns {@code true} if the specified identity token is valid.
     * 
     * @param token The identity token.
     * @return If the identity token is valid.
     */
    public static boolean isValidToken(
            final @NotNull String token) {
        return VALID_TOKEN_PREDICATE.test(token);
    }

    /**
     * Parses the specified identity token for the default identity token
     * prefix. If the token is valid and starts with the default prefix
     * the original identity token body used when formatted is returned.
     * 
     * @param token The identity token
     * @return The original identity token body
     * @throws NullPointerException If the identity token is {@code null}
     * @throws UnrecognizedIdentityTokenException If the identity token is not a
     * valid identity token or it doesn't start with the expected prefix
     */
    public static String parse(
            final @NotNull String token)
    throws UnrecognizedIdentityTokenException {
        return parse(DEFAULT_PREFIX, token);
    }

    /**
     * Parses the specified identity token for the specified expected identity
     * token prefix. If the token is valid and starts with the specified prefix
     * the original identity token body used when formatted is returned.
     * 
     * @param prefix The expected identity token prefix
     * @param token The identity token
     * @return The original identity token body
     * @throws NullPointerException If the prefix or the identity token are
     * {@code null}
     * @throws IllegalArgumentException If the prefix is not a valid identity
     * token prefix
     * @throws UnrecognizedIdentityTokenException If the identity token is not a
     * valid identity token or it doesn't start with the expected prefix
     */
    public static String parse(
            final @NotNull String prefix,
            final @NotNull String token)
    throws UnrecognizedIdentityTokenException {
        Validate.notNull(prefix, "Identity token prefix is required");
        if (!VALID_PREFIX_PREDICATE.test(prefix)) {
            throw new IllegalArgumentException(String.format(
                    "Identity token prefix is not valid: %s",
                    prefix));
        }
        Validate.notNull(token, "Identity token is required");
        if (!VALID_TOKEN_PREDICATE.test(token)) {
            throw new UnrecognizedIdentityTokenException(String.format(
                    "Invalid identity token: %s",
                    token));
        }
        if (!token.startsWith(prefix)) {
            throw new UnrecognizedIdentityTokenException(String.format(
                    "Unrecognized identity token: %s",
                    token));
        }
        return decodeBody(token.substring(prefix.length()));
    }
}
