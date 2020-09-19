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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;

import org.apache.commons.beanutils.ConvertUtilsBean;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Integration tests for time class converters. Test the following converters:
 * 
 * <ul>
 * <li>{@code DayOfWeekConverter}</li>
 * <li>{@code InstantConverter}</li>
 * <li>{@code LocalDateConverter}</li>
 * <li>{@code LocalDateTimeConverter}</li>
 * <li>{@code LocaleConverter}</li>
 * <li>{@code LocalTimeConverter}</li>
 * <li>{@code MonthConverter}</li>
 * <li>{@code MonthDayConverter}</li>
 * <li>{@code OffsetDateTimeConverter}</li>
 * <li>{@code OffsetTimeConverter}</li>
 * <li>{@code UriConverter}</li>
 * <li>{@code YearConverter}</li>
 * <li>{@code YearMonthConverter}</li>
 * <li>{@code ZonedDateTimeConverter}</li>
 * </ul>
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see DayOfWeekConverter
 * @see InstantConverter
 * @see LocalDateConverter
 * @see LocalDateTimeConverter
 * @see LocaleConverter
 * @see LocalTimeConverter
 * @see MonthConverter
 * @see MonthDayConverter
 * @see OffsetDateTimeConverter
 * @see OffsetTimeConverter
 * @see UriConverter
 * @see YearConverter
 * @see YearMonthConverter
 * @see ZonedDateTimeConverter
 */
@Tag("it")
class OrneBeansConvertersIT {

    private static ConvertUtilsBean converter;

    @BeforeAll
    public static void createConverter() {
        converter = new ConvertUtilsBean();
        OrneBeansConverters.register(converter);
    }

    /**
     * Integration test for {@code DayOfWeekConverter}.
     */
    @Test
    void testDayOfWeekConverter() {
        assertEquals(DayOfWeek.FRIDAY, converter.convert("5", DayOfWeek.class));
    }
}
