/*
 * Copyright (C) 2008-2016 GLAD!! (ITO Yoshiichi)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.glad2121.calendar.chrono;

import static org.assertj.core.api.StrictAssertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JapaneseDateTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFirstDay() {
        JapaneseDate jdate = JapaneseDate.of(1873, 1, 1);
        assertThat(JapaneseDate.of(JapaneseEra.MEIJI, 6, 1, 1)).isEqualTo(jdate);
        assertThat(jdate.getChronology()).isSameAs(JapaneseChronology.INSTANCE);
        assertThat(jdate.getEra()).isSameAs(JapaneseEra.MEIJI);
        assertThat(jdate.getYearOfEra()).isEqualTo(6);
        assertThat(jdate.getMonthValue()).isEqualTo(1);
        assertThat(jdate.getDayOfMonth()).isEqualTo(1);
        assertThat(jdate.toString()).isEqualTo("M06.01.01");
    }

    @Test
    public void testLastDayOfMeiji() {
        JapaneseDate jdate = JapaneseDate.of(1912, 7, 29);
        assertThat(JapaneseDate.of(JapaneseEra.MEIJI, 45, 7, 29)).isEqualTo(jdate);
        assertThat(jdate.getChronology()).isSameAs(JapaneseChronology.INSTANCE);
        assertThat(jdate.getEra()).isSameAs(JapaneseEra.MEIJI);
        assertThat(jdate.getYearOfEra()).isEqualTo(45);
        assertThat(jdate.getMonthValue()).isEqualTo(7);
        assertThat(jdate.getDayOfMonth()).isEqualTo(29);
        assertThat(jdate.toString()).isEqualTo("M45.07.29");
    }

    @Test
    public void testFirstDayOfTaisho() {
        JapaneseDate jdate = JapaneseDate.of(1912, 7, 30);
        assertThat(JapaneseDate.of(JapaneseEra.MEIJI, 45, 7, 30)).isEqualTo(jdate);
        assertThat(JapaneseDate.of(JapaneseEra.TAISHO, 1, 7, 30)).isEqualTo(jdate);
        assertThat(jdate.getChronology()).isSameAs(JapaneseChronology.INSTANCE);
        assertThat(jdate.getEra()).isSameAs(JapaneseEra.TAISHO);
        assertThat(jdate.getYearOfEra()).isEqualTo(1);
        assertThat(jdate.getMonthValue()).isEqualTo(7);
        assertThat(jdate.getDayOfMonth()).isEqualTo(30);
        assertThat(jdate.toString()).isEqualTo("T01.07.30");
    }

    @Test
    public void testLastDayOfTaisho() {
        JapaneseDate jdate = JapaneseDate.of(1926, 12, 24);
        assertThat(JapaneseDate.of(JapaneseEra.TAISHO, 15, 12, 24)).isEqualTo(jdate);
        assertThat(jdate.getChronology()).isSameAs(JapaneseChronology.INSTANCE);
        assertThat(jdate.getEra()).isSameAs(JapaneseEra.TAISHO);
        assertThat(jdate.getYearOfEra()).isEqualTo(15);
        assertThat(jdate.getMonthValue()).isEqualTo(12);
        assertThat(jdate.getDayOfMonth()).isEqualTo(24);
        assertThat(jdate.toString()).isEqualTo("T15.12.24");
    }

    @Test
    public void testFirstDayOfShowa() {
        JapaneseDate jdate = JapaneseDate.of(1926, 12, 25);
        assertThat(JapaneseDate.of(JapaneseEra.TAISHO, 15, 12, 25)).isEqualTo(jdate);
        assertThat(JapaneseDate.of(JapaneseEra.SHOWA, 1, 12, 25)).isEqualTo(jdate);
        assertThat(jdate.getChronology()).isSameAs(JapaneseChronology.INSTANCE);
        assertThat(jdate.getEra()).isSameAs(JapaneseEra.SHOWA);
        assertThat(jdate.getYearOfEra()).isEqualTo(1);
        assertThat(jdate.getMonthValue()).isEqualTo(12);
        assertThat(jdate.getDayOfMonth()).isEqualTo(25);
        assertThat(jdate.toString()).isEqualTo("S01.12.25");
    }

    @Test
    public void testLastDayOfShowa() {
        JapaneseDate jdate = JapaneseDate.of(1989, 1, 7);
        assertThat(JapaneseDate.of(JapaneseEra.SHOWA, 64, 1, 7)).isEqualTo(jdate);
        assertThat(jdate.getChronology()).isSameAs(JapaneseChronology.INSTANCE);
        assertThat(jdate.getEra()).isSameAs(JapaneseEra.SHOWA);
        assertThat(jdate.getYearOfEra()).isEqualTo(64);
        assertThat(jdate.getMonthValue()).isEqualTo(1);
        assertThat(jdate.getDayOfMonth()).isEqualTo(7);
        assertThat(jdate.toString()).isEqualTo("S64.01.07");
    }

    @Test
    public void testFirstDayOfHeisei() {
        JapaneseDate jdate = JapaneseDate.of(1989, 1, 8);
        assertThat(JapaneseDate.of(JapaneseEra.SHOWA, 64, 1, 8)).isEqualTo(jdate);
        assertThat(JapaneseDate.of(JapaneseEra.HEISEI, 1, 1, 8)).isEqualTo(jdate);
        assertThat(jdate.getChronology()).isSameAs(JapaneseChronology.INSTANCE);
        assertThat(jdate.getEra()).isSameAs(JapaneseEra.HEISEI);
        assertThat(jdate.getYearOfEra()).isEqualTo(1);
        assertThat(jdate.getMonthValue()).isEqualTo(1);
        assertThat(jdate.getDayOfMonth()).isEqualTo(8);
        assertThat(jdate.toString()).isEqualTo("H01.01.08");
    }

    @Test
    public void testNow() {
        JapaneseDate jdate = JapaneseDate.now();
        assertThat(jdate).isNotNull();
    }

}
