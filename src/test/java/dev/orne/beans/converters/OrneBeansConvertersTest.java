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
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dev.orne.beans.Identity;

/**
 * Unit tests for {@code OrneBeansConverters}.
 *
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2020-05
 * @since 0.1
 * @see OrneBeansConverters
 */
@Tag("ut")
class OrneBeansConvertersTest {

    /**
     * Restored registered conversors in {@code ConvertUtils}.
     */
    @AfterEach
    public void cleanConversors() {
        ConvertUtils.deregister();
    }

    /**
     * Test for {@code OrneBeansConverters#register()}.
     */
    @Test
    void testRegister() {
        OrneBeansConverters.register();
        assertNotNull(ConvertUtils.lookup(Identity.class));
        assertNotNull(ConvertUtils.lookup(Calendar.class));
        assertNotNull(ConvertUtils.lookup(Date.class));
        assertNotNull(ConvertUtils.lookup(Locale.class));
        assertNotNull(ConvertUtils.lookup(Instant.class));
        assertNotNull(ConvertUtils.lookup(Year.class));
        assertNotNull(ConvertUtils.lookup(YearMonth.class));
        assertNotNull(ConvertUtils.lookup(Month.class));
        assertNotNull(ConvertUtils.lookup(MonthDay.class));
        assertNotNull(ConvertUtils.lookup(DayOfWeek.class));
        assertNotNull(ConvertUtils.lookup(LocalDate.class));
        assertNotNull(ConvertUtils.lookup(LocalTime.class));
        assertNotNull(ConvertUtils.lookup(LocalDateTime.class));
        assertNotNull(ConvertUtils.lookup(OffsetTime.class));
        assertNotNull(ConvertUtils.lookup(OffsetDateTime.class));
        assertNotNull(ConvertUtils.lookup(ZonedDateTime.class));
        assertNotNull(ConvertUtils.lookup(ZoneId.class));
        assertNotNull(ConvertUtils.lookup(ZoneOffset.class));
        assertNotNull(ConvertUtils.lookup(Duration.class));
        assertNotNull(ConvertUtils.lookup(Period.class));
    }

    /**
     * Test for {@code OrneBeansConverters#register(boolean)}.
     */
    @Test
    void testRegisterNulls() {
        OrneBeansConverters.register(true);
        assertNotNull(ConvertUtils.lookup(Identity.class));
        assertNotNull(ConvertUtils.lookup(Calendar.class));
        assertNotNull(ConvertUtils.lookup(Date.class));
        assertNotNull(ConvertUtils.lookup(Locale.class));
        assertNotNull(ConvertUtils.lookup(Instant.class));
        assertNotNull(ConvertUtils.lookup(Year.class));
        assertNotNull(ConvertUtils.lookup(YearMonth.class));
        assertNotNull(ConvertUtils.lookup(Month.class));
        assertNotNull(ConvertUtils.lookup(MonthDay.class));
        assertNotNull(ConvertUtils.lookup(DayOfWeek.class));
        assertNotNull(ConvertUtils.lookup(LocalDate.class));
        assertNotNull(ConvertUtils.lookup(LocalTime.class));
        assertNotNull(ConvertUtils.lookup(LocalDateTime.class));
        assertNotNull(ConvertUtils.lookup(OffsetTime.class));
        assertNotNull(ConvertUtils.lookup(OffsetDateTime.class));
        assertNotNull(ConvertUtils.lookup(ZonedDateTime.class));
        assertNotNull(ConvertUtils.lookup(ZoneId.class));
        assertNotNull(ConvertUtils.lookup(ZoneOffset.class));
        assertNotNull(ConvertUtils.lookup(Duration.class));
        assertNotNull(ConvertUtils.lookup(Period.class));
    }

    /**
     * Test for {@code OrneBeansConverters#registerBeansConversors()}.
     */
    @Test
    void testRegisterBeans() {
        OrneBeansConverters.registerBeansConversors();
        assertNotNull(ConvertUtils.lookup(Identity.class));
    }

    /**
     * Test for {@code OrneBeansConverters#registerBeansConversors(boolean)}.
     */
    @Test
    void testRegisterBeansNulls() {
        OrneBeansConverters.registerBeansConversors(true);
        assertNotNull(ConvertUtils.lookup(Identity.class));
    }

    /**
     * Test for {@code OrneBeansConverters#registerNetConversors()}.
     */
    @Test
    void testRegisterNet() {
        OrneBeansConverters.registerNetConversors();
        assertNotNull(ConvertUtils.lookup(URI.class));
    }

    /**
     * Test for {@code OrneBeansConverters#registerNetConversors(boolean)}.
     */
    @Test
    void testRegisterNetNulls() {
        OrneBeansConverters.registerNetConversors(true);
        assertNotNull(ConvertUtils.lookup(URI.class));
    }

    /**
     * Test for {@code OrneBeansConverters#registerUtilConversors()}.
     */
    @Test
    void testRegisterUtil() {
        OrneBeansConverters.registerUtilConversors();
        assertNotNull(ConvertUtils.lookup(Calendar.class));
        assertNotNull(ConvertUtils.lookup(Date.class));
        assertNotNull(ConvertUtils.lookup(Locale.class));
    }

    /**
     * Test for {@code OrneBeansConverters#registerUtilConversors(boolean)}.
     */
    @Test
    void testRegisterUtilNulls() {
        OrneBeansConverters.registerUtilConversors(true);
        assertNotNull(ConvertUtils.lookup(Calendar.class));
        assertNotNull(ConvertUtils.lookup(Date.class));
        assertNotNull(ConvertUtils.lookup(Locale.class));
    }

    /**
     * Test for {@code OrneBeansConverters#registerTimeConversors()}.
     */
    @Test
    void testRegisterTime() {
        OrneBeansConverters.registerTimeConversors();
        assertNotNull(ConvertUtils.lookup(Instant.class));
        assertNotNull(ConvertUtils.lookup(Year.class));
        assertNotNull(ConvertUtils.lookup(YearMonth.class));
        assertNotNull(ConvertUtils.lookup(Month.class));
        assertNotNull(ConvertUtils.lookup(MonthDay.class));
        assertNotNull(ConvertUtils.lookup(DayOfWeek.class));
        assertNotNull(ConvertUtils.lookup(LocalDate.class));
        assertNotNull(ConvertUtils.lookup(LocalTime.class));
        assertNotNull(ConvertUtils.lookup(LocalDateTime.class));
        assertNotNull(ConvertUtils.lookup(OffsetTime.class));
        assertNotNull(ConvertUtils.lookup(OffsetDateTime.class));
        assertNotNull(ConvertUtils.lookup(ZonedDateTime.class));
        assertNotNull(ConvertUtils.lookup(ZoneId.class));
        assertNotNull(ConvertUtils.lookup(ZoneOffset.class));
        assertNotNull(ConvertUtils.lookup(Duration.class));
        assertNotNull(ConvertUtils.lookup(Period.class));
    }

    /**
     * Test for {@code OrneBeansConverters#registerTimeConversors(boolean)}.
     */
    @Test
    void testRegisterTimeNulls() {
        OrneBeansConverters.registerTimeConversors(true);
        assertNotNull(ConvertUtils.lookup(Instant.class));
        assertNotNull(ConvertUtils.lookup(Year.class));
        assertNotNull(ConvertUtils.lookup(YearMonth.class));
        assertNotNull(ConvertUtils.lookup(Month.class));
        assertNotNull(ConvertUtils.lookup(MonthDay.class));
        assertNotNull(ConvertUtils.lookup(DayOfWeek.class));
        assertNotNull(ConvertUtils.lookup(LocalDate.class));
        assertNotNull(ConvertUtils.lookup(LocalTime.class));
        assertNotNull(ConvertUtils.lookup(LocalDateTime.class));
        assertNotNull(ConvertUtils.lookup(OffsetTime.class));
        assertNotNull(ConvertUtils.lookup(OffsetDateTime.class));
        assertNotNull(ConvertUtils.lookup(ZonedDateTime.class));
        assertNotNull(ConvertUtils.lookup(ZoneId.class));
        assertNotNull(ConvertUtils.lookup(ZoneOffset.class));
        assertNotNull(ConvertUtils.lookup(Duration.class));
        assertNotNull(ConvertUtils.lookup(Period.class));
    }

    /**
     * Test for {@code OrneBeansConverters#register(ConvertUtilsBean)}.
     */
    @Test
    void testRegisterInBean() {
        final ConvertUtilsBean converter = new ConvertUtilsBean();
        OrneBeansConverters.register(converter);
        assertNotNull(converter.lookup(Identity.class));
        assertNotNull(converter.lookup(Calendar.class));
        assertNotNull(converter.lookup(Date.class));
        assertNotNull(converter.lookup(Locale.class));
        assertNotNull(converter.lookup(Instant.class));
        assertNotNull(converter.lookup(Year.class));
        assertNotNull(converter.lookup(YearMonth.class));
        assertNotNull(converter.lookup(Month.class));
        assertNotNull(converter.lookup(MonthDay.class));
        assertNotNull(converter.lookup(DayOfWeek.class));
        assertNotNull(converter.lookup(LocalDate.class));
        assertNotNull(converter.lookup(LocalTime.class));
        assertNotNull(converter.lookup(LocalDateTime.class));
        assertNotNull(converter.lookup(OffsetTime.class));
        assertNotNull(converter.lookup(OffsetDateTime.class));
        assertNotNull(converter.lookup(ZonedDateTime.class));
        assertNotNull(converter.lookup(ZoneId.class));
        assertNotNull(converter.lookup(ZoneOffset.class));
        assertNotNull(converter.lookup(Duration.class));
        assertNotNull(converter.lookup(Period.class));
    }

    /**
     * Test for {@code OrneBeansConverters#register(ConvertUtilsBean, boolean)}.
     */
    @Test
    void testRegisterInBeanNulls() {
        final ConvertUtilsBean converter = new ConvertUtilsBean();
        OrneBeansConverters.register(converter, true);
        assertNotNull(converter.lookup(Identity.class));
        assertNotNull(converter.lookup(Calendar.class));
        assertNotNull(converter.lookup(Date.class));
        assertNotNull(converter.lookup(Locale.class));
        assertNotNull(converter.lookup(Instant.class));
        assertNotNull(converter.lookup(Year.class));
        assertNotNull(converter.lookup(YearMonth.class));
        assertNotNull(converter.lookup(Month.class));
        assertNotNull(converter.lookup(MonthDay.class));
        assertNotNull(converter.lookup(DayOfWeek.class));
        assertNotNull(converter.lookup(LocalDate.class));
        assertNotNull(converter.lookup(LocalTime.class));
        assertNotNull(converter.lookup(LocalDateTime.class));
        assertNotNull(converter.lookup(OffsetTime.class));
        assertNotNull(converter.lookup(OffsetDateTime.class));
        assertNotNull(converter.lookup(ZonedDateTime.class));
        assertNotNull(converter.lookup(ZoneId.class));
        assertNotNull(converter.lookup(ZoneOffset.class));
        assertNotNull(converter.lookup(Duration.class));
        assertNotNull(converter.lookup(Period.class));
    }

    /**
     * Test for {@code OrneBeansConverters#registerBeansConversors(ConvertUtilsBean)}.
     */
    @Test
    void testRegisterBeansInBean() {
        final ConvertUtilsBean converter = new ConvertUtilsBean();
        OrneBeansConverters.registerBeansConversors(converter);
        assertNotNull(converter.lookup(Identity.class));
    }

    /**
     * Test for {@code OrneBeansConverters#registerBeansConversors(ConvertUtilsBean, boolean)}.
     */
    @Test
    void testRegisterBeansInBeanNulls() {
        final ConvertUtilsBean converter = new ConvertUtilsBean();
        OrneBeansConverters.registerBeansConversors(converter, true);
        assertNotNull(converter.lookup(Identity.class));
    }

    /**
     * Test for {@code OrneBeansConverters#registerNetConversors(ConvertUtilsBean)}.
     */
    @Test
    void testRegisterNetInBean() {
        final ConvertUtilsBean converter = new ConvertUtilsBean();
        OrneBeansConverters.registerNetConversors(converter);
        assertNotNull(converter.lookup(URI.class));
    }

    /**
     * Test for {@code OrneBeansConverters#registerNetConversors(ConvertUtilsBean, boolean)}.
     */
    @Test
    void testRegisterNetInBeanNulls() {
        final ConvertUtilsBean converter = new ConvertUtilsBean();
        OrneBeansConverters.registerNetConversors(converter, true);
        assertNotNull(converter.lookup(URI.class));
    }

    /**
     * Test for {@code OrneBeansConverters#registerUtilConversors(ConvertUtilsBean)}.
     */
    @Test
    void testRegisterUtilInBean() {
        final ConvertUtilsBean converter = new ConvertUtilsBean();
        OrneBeansConverters.registerUtilConversors(converter);
        assertNotNull(converter.lookup(Calendar.class));
        assertNotNull(converter.lookup(Date.class));
        assertNotNull(converter.lookup(Locale.class));
    }

    /**
     * Test for {@code OrneBeansConverters#registerUtilConversors(ConvertUtilsBean, boolean)}.
     */
    @Test
    void testRegisterUtilInBeanNulls() {
        final ConvertUtilsBean converter = new ConvertUtilsBean();
        OrneBeansConverters.registerUtilConversors(converter, true);
        assertNotNull(converter.lookup(Calendar.class));
        assertNotNull(converter.lookup(Date.class));
        assertNotNull(converter.lookup(Locale.class));
    }

    /**
     * Test for {@code OrneBeansConverters#registerTimeConversors(ConvertUtilsBean)}.
     */
    @Test
    void testRegisterTimeInBean() {
        final ConvertUtilsBean converter = new ConvertUtilsBean();
        OrneBeansConverters.registerTimeConversors(converter);
        assertNotNull(converter.lookup(Instant.class));
        assertNotNull(converter.lookup(Year.class));
        assertNotNull(converter.lookup(YearMonth.class));
        assertNotNull(converter.lookup(Month.class));
        assertNotNull(converter.lookup(MonthDay.class));
        assertNotNull(converter.lookup(DayOfWeek.class));
        assertNotNull(converter.lookup(LocalDate.class));
        assertNotNull(converter.lookup(LocalTime.class));
        assertNotNull(converter.lookup(LocalDateTime.class));
        assertNotNull(converter.lookup(OffsetTime.class));
        assertNotNull(converter.lookup(OffsetDateTime.class));
        assertNotNull(converter.lookup(ZonedDateTime.class));
        assertNotNull(converter.lookup(ZoneId.class));
        assertNotNull(converter.lookup(ZoneOffset.class));
        assertNotNull(converter.lookup(Duration.class));
        assertNotNull(converter.lookup(Period.class));
    }

    /**
     * Test for {@code OrneBeansConverters#registerTimeConversors(ConvertUtilsBean, boolean)}.
     */
    @Test
    void testRegisterTimeInBeanNullst() {
        final ConvertUtilsBean converter = new ConvertUtilsBean();
        OrneBeansConverters.registerTimeConversors(converter, true);
        assertNotNull(converter.lookup(Instant.class));
        assertNotNull(converter.lookup(Year.class));
        assertNotNull(converter.lookup(YearMonth.class));
        assertNotNull(converter.lookup(Month.class));
        assertNotNull(converter.lookup(MonthDay.class));
        assertNotNull(converter.lookup(DayOfWeek.class));
        assertNotNull(converter.lookup(LocalDate.class));
        assertNotNull(converter.lookup(LocalTime.class));
        assertNotNull(converter.lookup(LocalDateTime.class));
        assertNotNull(converter.lookup(OffsetTime.class));
        assertNotNull(converter.lookup(OffsetDateTime.class));
        assertNotNull(converter.lookup(ZonedDateTime.class));
        assertNotNull(converter.lookup(ZoneId.class));
        assertNotNull(converter.lookup(ZoneOffset.class));
        assertNotNull(converter.lookup(Duration.class));
        assertNotNull(converter.lookup(Period.class));
    }
}
