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

import java.math.BigInteger;

import javax.validation.constraints.NotNull;

import dev.orne.test.rnd.GeneratorMethod;

/**
 * Implementation for {@code Identity} for identities composed
 * of a single inner {@code BigInteger} value.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 */
public class BigIntegerIdentity
extends AbstractSimpleIdentity<BigInteger> {

    /** The serial version UID. */
    private static final long serialVersionUID = 5660267975323191055L;

    /**
     * Creates a new instance.
     * 
     * @param value The identity value
     */
    @GeneratorMethod
    public BigIntegerIdentity(
            final BigInteger value) {
        super(value);
    }

    /**
     * Copy constructor.
     * 
     * @param copy The instance to copy
     */
    public BigIntegerIdentity(
            final @NotNull BigIntegerIdentity copy) {
        super(copy);
    }

    /**
     * Resolves the specified identity token to a valid
     * {@code BigIntegerIdentity}.
     * 
     * @param token The identity token
     * @return The resolved identity token
     * @throws NullPointerException If the identity token is {@code null}
     * @throws UnrecognizedIdentityTokenException If the identity token is not
     * a valid identity token or it doesn't start with the expected prefix
     */
    @IdentityTokenResolver
    public static @NotNull BigIntegerIdentity fromIdentityToken(
            final @NotNull String token)
    throws UnrecognizedIdentityTokenException {
        return new BigIntegerIdentity(extractTokenValue(
                IdentityTokenFormatter.DEFAULT_PREFIX,
                token));
    }

    /**
     * Extracts the {@code BigInteger} value of a token generated by
     * {@code BigIntegerIdentity}.
     * <p>
     * Note that the resulting value can be {@code null}. If a non-null
     * value is expected use{@code extractRequiredTokenValue(String, String)}.
     * 
     * @param prefix The expected identity token prefix.
     * @param token The identity token.
     * @return The extracted {@code BigInteger} value.
     * @throws NullPointerException If the identity token is {@code null}
     * @throws UnrecognizedIdentityTokenException If the identity token is not
     * a valid simple identity token, if it doesn't start with the expected
     * prefix or if the extracted value is not a valid big integer.
     * @see #extractRequiredTokenValue(String, String)
     */
    public static BigInteger extractTokenValue(
            final @NotNull String prefix,
            final @NotNull String token) {
        final String body = IdentityTokenFormatter.parse(prefix, token);
        if (body == null) {
            return null;
        } else {
            try {
                return new BigInteger(body);
            } catch (final NumberFormatException nfe) {
                throw new UnrecognizedIdentityTokenException(
                        "Unrecognized identity token: " + token, nfe);
            }
        }
    }

    /**
     * Extracts the {@code BigInteger} value of a token generated by
     * {@code BigIntegerIdentity}.
     * <p>
     * If the resulting value is {@code null} an exception is thrown.
     * 
     * @param prefix The expected identity token prefix.
     * @param token The identity token.
     * @return The extracted {@code BigInteger} value.
     * @throws NullPointerException If the identity token is {@code null}
     * @throws UnrecognizedIdentityTokenException If the identity token is not
     * a valid simple identity token, if it doesn't start with the expected
     * prefix, if the extracted value is null or if the extracted value is not
     * a valid big integer.
     * @see #extractTokenValue(String, String)
     */
    public static @NotNull BigInteger extractRequiredTokenValue(
            final @NotNull String prefix,
            final @NotNull String token) {
        final BigInteger result = extractTokenValue(prefix, token);
        if (result == null) {
            throw new UnrecognizedIdentityTokenException(
                    "Unrecognized identity token: " + token);
        }
        return result;
    }
}
