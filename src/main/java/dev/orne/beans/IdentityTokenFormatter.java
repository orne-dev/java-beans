package dev.orne.beans;

import java.nio.charset.StandardCharsets;
import java.util.function.Predicate;
import java.util.regex.Pattern;

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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * Formatter and parser of identity tokens.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 */
public final class IdentityTokenFormatter {

    /** Null token. */
    public static final String NULL_TOKEN = "_";

    /** Regular expression for valid token prefix characters. */
    public static final String VALID_PREFIX_CHAR_REGEX = "[a-zA-Z]";
    /** Regular expression for valid token body starting characters. */
    public static final String VALID_BODY_STARTING_CHAR_REGEX = "[a-zA-Z0-9]";
    /** Regular expression for valid token body non starting characters. */
    public static final String VALID_BODY_CHAR_REGEX = "[a-zA-Z0-9_]";
    /** Regular expression for valid Base32 characters. */
    public static final String VALID_B32_CHAR_REGEXP = "[A-Z2-7]";
    /** The Base32 encoded identity token body prefix. */
    public static final String B32_ENCODED_BODY_PREFIX = "_";
    /** The Base32 padding character. */
    public static final char B32_PADDING_CHAR = '=';

    /** Regular expression for valid identity token prefixes. */
    public static final String VALID_PREFIX_REGEX =
            VALID_PREFIX_CHAR_REGEX + "+";
    /** Regular expression for valid identity token prefix strings. */
    public static final String VALID_PREFIX_FULL_REGEX =
            "^" + VALID_PREFIX_CHAR_REGEX + "+$";
    /** Regular expression for valid identity token unencoded bodies. */
    public static final String VALID_UNENCODED_BODY_REGEX =
            VALID_BODY_STARTING_CHAR_REGEX + VALID_BODY_CHAR_REGEX + "*";
    /** Regular expression for valid identity token Base32 encoded bodies. */
    public static final String VALID_BASE32_ENCODED_BODY_REGEX =
            B32_ENCODED_BODY_PREFIX
                + "(?:" + VALID_B32_CHAR_REGEXP + "{8})*"
                + "(?:"
                    + VALID_B32_CHAR_REGEXP + "{2}" + "|"
                    + VALID_B32_CHAR_REGEXP + "{4}" + "|"
                    + VALID_B32_CHAR_REGEXP + "{5}" + "|"
                    + VALID_B32_CHAR_REGEXP + "{7}" + "|"
                    + VALID_B32_CHAR_REGEXP + "{8}"
                + ")";
    /** Regular expression for valid identity token bodies. */
    public static final String VALID_BODY_REGEX =
            "(?:"
                + NULL_TOKEN + "|"
                + VALID_UNENCODED_BODY_REGEX + "|"
                + VALID_BASE32_ENCODED_BODY_REGEX
            + "*)?";

    /** Regular expression for valid identity tokens. */
    public static final String VALID_TOKEN_REGEX =
            "^" + VALID_PREFIX_REGEX
                + VALID_BODY_REGEX
            + "$";
    /** Default identity token prefix. */
    public static final String DEFAULT_PREFIX = "ID";

    /** Compiled pattern to detect valid identity token prefixes as predicate. */
    protected static final Predicate<String> VALID_PREFIX_PREDICATE =
            Pattern.compile(VALID_PREFIX_FULL_REGEX).asPredicate();
    /** Compiled pattern to detect valid identity token unencoded bodies as predicate. */
    protected static final Predicate<String> VALID_UNENCODED_BODY_PREDICATE =
            Pattern.compile("^" + VALID_UNENCODED_BODY_REGEX + "$").asPredicate();
    /** Compiled pattern to detect valid identity token bodies as predicate. */
    protected static final Predicate<String> VALID_TOKEN_BODY_PREDICATE =
            Pattern.compile("^" + VALID_BODY_REGEX + "$").asPredicate();
    /** Compiled pattern to detect valid identity tokens as predicate. */
    protected static final Predicate<String> VALID_TOKEN_PREDICATE =
            Pattern.compile(VALID_TOKEN_REGEX).asPredicate();

    /** The Base32 encoder and decoder. */
    private static final Base32 BASE32 = new Base32();

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
     * <li>If the specified body is {@code null} {@link #NULL_TOKEN} is
     * returned.</li>
     * <li>If the specified body is empty an empty {@code String} is
     * returned.</li>
     * <li>If the specified body is a valid unencoded body the passed body is
     * returned.</li>
     * <li>Otherwise the passed body is encoded in Base32, with paddings
     * removed and prefixed by {@link #B32_ENCODED_BODY_PREFIX}.</li>
     * </ol>
     * 
     * @param part The identity token body to encode
     * @return The encoded identity token part
     */
    @Nonnull
    protected static String encodeBody(
            @Nullable
            final String body) {
        final String result;
        if (body == null) {
            result = NULL_TOKEN;
        } else if (body.isEmpty() || VALID_UNENCODED_BODY_PREDICATE.test(body)) {
            result = body;
        } else {
            final String b32 = BASE32.encodeAsString(body.getBytes(StandardCharsets.UTF_8));
            final String b32NoPadding = b32.replaceAll(String.valueOf(B32_PADDING_CHAR), "");
            result = new StringBuilder(B32_ENCODED_BODY_PREFIX)
                    .append(b32NoPadding)
                    .toString();
        }
        return result;
    }

    /**
     * Decodes the specified valid identity token body to the original identity
     * token body.</p>
     * 
     * @param part The valid identity token body to decode
     * @return The original identity token body
     * @throws NullPointerException If the encoded body is {@code null}
     * @throws UnrecognizedIdentityTokenException If the encoded body is not a
     * valid identity token body
     */
    @Nullable
    protected static String decodeBody(
            @Nonnull
            final String encoded)
    throws UnrecognizedIdentityTokenException {
        Validate.notNull(encoded, "Encoded identity token body is required");
        if (!VALID_TOKEN_BODY_PREDICATE.test(encoded)) {
            throw new UnrecognizedIdentityTokenException(String.format(
                    "Invalid identity token body: %s",
                    encoded));
        }
        final String result;
        if (NULL_TOKEN.equals(encoded)) {
            result = null;
        } else if (encoded.isEmpty()) {
            result = encoded;
        } else if (encoded.startsWith(B32_ENCODED_BODY_PREFIX)) {
            String b32 = encoded.substring(1);
            if (b32.length() % 8 != 0) {
                b32 = StringUtils.rightPad(b32, 8 - (b32.length() % 8), B32_PADDING_CHAR);
            }
            result = new String(
                    BASE32.decode(b32),
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
    @Nonnull
    public static String format(
            @Nullable
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
    @Nonnull
    public static String format(
            @Nonnull
            final String prefix,
            @Nullable
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
    @Nullable
    public static String parse(
            @Nonnull
            final String token)
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
    @Nullable
    public static String parse(
            @Nonnull
            final String prefix,
            @Nonnull
            final String token)
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
