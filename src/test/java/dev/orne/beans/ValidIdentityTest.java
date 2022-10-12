package dev.orne.beans;

/*-
 * #%L
 * Orne Beans
 * %%
 * Copyright (C) 2022 Orne Developments
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

import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotBlank;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.beans.ValidIdentity.ValidIdentityValidator;
import dev.orne.beans.ValidIdentity.ValidIdentityValidatorForString;

/**
 * Unit tests for {@code ValidIdentity}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-10
 * @since 0.4
 * @see ValidIdentity
 */
@Tag("ut")
class ValidIdentityTest {

    /**
     * Test {@link ValidIdentityValidator#isValid(Identity)}.
     */
    @Test
    void testIsValidIdentity() {
        final Identity mockIdentity = mock(Identity.class);
        assertThrows(NullPointerException.class, () -> {
            ValidIdentityValidator.isValid(null);
        });
        assertTrue(ValidIdentityValidator.isValid(new TokenIdentity("some token")));
        assertTrue(ValidIdentityValidator.isValid(new TestIdentity("some token")));
        assertTrue(ValidIdentityValidator.isValid(mockIdentity));
        then(mockIdentity).shouldHaveNoInteractions();
    }

    /**
     * Test {@link ValidIdentityValidator#isValid(Identity, Class)}.
     */
    @Test
    void testIsValidIdentityClass() {
        final Identity mockIdentity = mock(Identity.class);
        assertThrows(NullPointerException.class, () -> {
            ValidIdentityValidator.isValid(null, (Class<? extends Identity>) null);
        });
        assertThrows(NullPointerException.class, () -> {
            ValidIdentityValidator.isValid(null, Identity.class);
        });
        assertThrows(NullPointerException.class, () -> {
            ValidIdentityValidator.isValid(mockIdentity, (Class<? extends Identity>) null);
        });
        willReturn("Some token").given(mockIdentity).getIdentityToken();
        assertTrue(ValidIdentityValidator.isValid(mockIdentity, Identity.class));
        assertTrue(ValidIdentityValidator.isValid(mockIdentity, TestIdentity.class));
        assertFalse(ValidIdentityValidator.isValid(mockIdentity, UnrecognizedIdentity.class));
        assertFalse(ValidIdentityValidator.isValid(mockIdentity, IllegalIdentity.class));
    }

    /**
     * Test {@link ValidIdentityValidator#isValid(Identity, ConstraintValidatorContext)}.
     */
    @Test
    void testIsValidIdentityContext() {
        final Identity mockIdentity = mock(Identity.class);
        final ValidIdentity annot = mock(ValidIdentity.class);
        final ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        final ValidIdentityValidator validator = new ValidIdentityValidator();
        willReturn("Some token").given(mockIdentity).getIdentityToken();
        willReturn(Identity.class).given(annot).value();
        validator.initialize(annot);
        assertTrue(validator.isValid(null, context));
        assertTrue(validator.isValid(mockIdentity, context));
        willReturn(TestIdentity.class).given(annot).value();
        validator.initialize(annot);
        assertTrue(validator.isValid(null, context));
        assertTrue(validator.isValid(mockIdentity, context));
        willReturn(UnrecognizedIdentity.class).given(annot).value();
        validator.initialize(annot);
        assertTrue(validator.isValid(null, context));
        assertFalse(validator.isValid(mockIdentity, context));
        willReturn(IllegalIdentity.class).given(annot).value();
        validator.initialize(annot);
        assertTrue(validator.isValid(null, context));
        assertFalse(validator.isValid(mockIdentity, context));
    }

    /**
     * Test {@link ValidIdentityValidatorForString#isValid(String)}.
     */
    @Test
    void testIsValidString() {
        assertThrows(NullPointerException.class, () -> {
            ValidIdentityValidatorForString.isValid(null);
        });
        assertTrue(ValidIdentityValidatorForString.isValid("any"));
    }

    /**
     * Test {@link ValidIdentityValidatorForString#isValid(String, Class)}.
     */
    @Test
    void testIsValidStringClass() {
        final String token = "Some token";
        assertThrows(NullPointerException.class, () -> {
            ValidIdentityValidatorForString.isValid(null, (Class<? extends Identity>) null);
        });
        assertThrows(NullPointerException.class, () -> {
            ValidIdentityValidatorForString.isValid(null, Identity.class);
        });
        assertThrows(NullPointerException.class, () -> {
            ValidIdentityValidatorForString.isValid(token, (Class<? extends Identity>) null);
        });
        assertTrue(ValidIdentityValidatorForString.isValid(token, Identity.class));
        assertTrue(ValidIdentityValidatorForString.isValid(token, TestIdentity.class));
        assertFalse(ValidIdentityValidatorForString.isValid(token, UnrecognizedIdentity.class));
        assertFalse(ValidIdentityValidatorForString.isValid(token, IllegalIdentity.class));
    }

    /**
     * Test {@link ValidIdentityValidatorForString#isValid(Identity, ConstraintValidatorContext)}.
     */
    @Test
    void testIsValidStringContext() {
        final String token = "Some token";
        final ValidIdentity annot = mock(ValidIdentity.class);
        final ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        final ValidIdentityValidatorForString validator = new ValidIdentityValidatorForString();
        willReturn(Identity.class).given(annot).value();
        validator.initialize(annot);
        assertTrue(validator.isValid(null, context));
        assertTrue(validator.isValid(token, context));
        willReturn(TestIdentity.class).given(annot).value();
        validator.initialize(annot);
        assertTrue(validator.isValid(null, context));
        assertTrue(validator.isValid(token, context));
        willReturn(UnrecognizedIdentity.class).given(annot).value();
        validator.initialize(annot);
        assertTrue(validator.isValid(null, context));
        assertFalse(validator.isValid(token, context));
        willReturn(IllegalIdentity.class).given(annot).value();
        validator.initialize(annot);
        assertTrue(validator.isValid(null, context));
        assertFalse(validator.isValid(token, context));
    }

    protected static class TestIdentity
    implements Identity {
        private static final long serialVersionUID = 1L;
        private String token;
        public TestIdentity(
                final String token) {
            this.token = token;
        }
        @Override
        public @NotBlank String getIdentityToken() {
            return token;
        }
    }

    protected static class UnrecognizedIdentity
    implements Identity {
        private static final long serialVersionUID = 1L;
        public UnrecognizedIdentity(
                final String token)
        throws UnrecognizedIdentityTokenException {
            throw new UnrecognizedIdentityTokenException(token);
        }
        @Override
        public @NotBlank String getIdentityToken() {
            return null;
        }
    }

    protected static class IllegalIdentity
    implements Identity {
        private static final long serialVersionUID = 1L;
        public IllegalIdentity(
                final String token) {
            throw new IllegalArgumentException(token);
        }
        @Override
        public @NotBlank String getIdentityToken() {
            return null;
        }
    }
}
