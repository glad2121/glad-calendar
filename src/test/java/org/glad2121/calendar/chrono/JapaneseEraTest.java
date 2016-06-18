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

import java.time.LocalDate;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link JapaneseEra}.
 *
 * @author GLAD!!
 */
public class JapaneseEraTest {

    Locale defaultLocale;

    @Before
    public void setUp() throws Exception {
        defaultLocale = Locale.getDefault();
        setLocale(Locale.JAPAN);
    }

    @After
    public void tearDown() throws Exception {
        setLocale(defaultLocale);
        defaultLocale = null;
    }

    void setLocale(Locale locale) {
        if (locale != Locale.getDefault()) {
            Locale.setDefault(locale);
        }
    }

    @Test
    public void testMeiji() {
        JapaneseEra era = JapaneseEra.MEIJI;
        assertThat(JapaneseEra.of(1)).isSameAs(era);
        assertThat(JapaneseEra.valueOf("Meiji")).isSameAs(era);
        assertThat(era.getValue()).isEqualTo(1);
        assertThat(era.getName()).isEqualTo("Meiji");
        assertThat(era.getAbbr()).isEqualTo("M");
        assertThat(era.getSince()).isEqualTo(LocalDate.of(1868, 1, 1));
        assertThat(era.getShortName()).isEqualTo("M");
        assertThat(era.getShortName(Locale.US)).isEqualTo("M");
        assertThat(era.getMediumName()).isEqualTo("明");
        assertThat(era.getMediumName(Locale.US)).isEqualTo("M");
        assertThat(era.getLongName()).isEqualTo("明治");
        assertThat(era.getLongName(Locale.US)).isEqualTo("Meiji");
        assertThat(era.toString()).isEqualTo("Meiji");
    }

    @Test
    public void testTaisho() {
        JapaneseEra era = JapaneseEra.TAISHO;
        assertThat(JapaneseEra.of(2)).isSameAs(era);
        assertThat(JapaneseEra.valueOf("Taisho")).isSameAs(era);
        assertThat(era.getValue()).isEqualTo(2);
        assertThat(era.getName()).isEqualTo("Taisho");
        assertThat(era.getAbbr()).isEqualTo("T");
        assertThat(era.getSince()).isEqualTo(LocalDate.of(1912, 7, 30));
        assertThat(era.getShortName()).isEqualTo("T");
        assertThat(era.getShortName(Locale.US)).isEqualTo("T");
        assertThat(era.getMediumName()).isEqualTo("大");
        assertThat(era.getMediumName(Locale.US)).isEqualTo("T");
        assertThat(era.getLongName()).isEqualTo("大正");
        assertThat(era.getLongName(Locale.US)).isEqualTo("Taisho");
        assertThat(era.toString()).isEqualTo("Taisho");
    }

    @Test
    public void testShowa() {
        JapaneseEra era = JapaneseEra.SHOWA;
        assertThat(JapaneseEra.of(3)).isSameAs(era);
        assertThat(JapaneseEra.valueOf("Showa")).isSameAs(era);
        assertThat(era.getValue()).isEqualTo(3);
        assertThat(era.getName()).isEqualTo("Showa");
        assertThat(era.getAbbr()).isEqualTo("S");
        assertThat(era.getSince()).isEqualTo(LocalDate.of(1926, 12, 25));
        assertThat(era.getShortName()).isEqualTo("S");
        assertThat(era.getShortName(Locale.US)).isEqualTo("S");
        assertThat(era.getMediumName()).isEqualTo("昭");
        assertThat(era.getMediumName(Locale.US)).isEqualTo("S");
        assertThat(era.getLongName()).isEqualTo("昭和");
        assertThat(era.getLongName(Locale.US)).isEqualTo("Showa");
        assertThat(era.toString()).isEqualTo("Showa");
    }

    @Test
    public void testHeisei() {
        JapaneseEra era = JapaneseEra.HEISEI;
        assertThat(JapaneseEra.of(4)).isSameAs(era);
        assertThat(JapaneseEra.valueOf("Heisei")).isSameAs(era);
        assertThat(era.getValue()).isEqualTo(4);
        assertThat(era.getName()).isEqualTo("Heisei");
        assertThat(era.getAbbr()).isEqualTo("H");
        assertThat(era.getSince()).isEqualTo(LocalDate.of(1989, 1, 8));
        assertThat(era.getShortName()).isEqualTo("H");
        assertThat(era.getShortName(Locale.US)).isEqualTo("H");
        assertThat(era.getMediumName()).isEqualTo("平");
        assertThat(era.getMediumName(Locale.US)).isEqualTo("H");
        assertThat(era.getLongName()).isEqualTo("平成");
        assertThat(era.getLongName(Locale.US)).isEqualTo("Heisei");
        assertThat(era.toString()).isEqualTo("Heisei");
    }

    @Test
    public void testValues() {
        JapaneseEra[] values = JapaneseEra.values();
        assertThat(values.length).isGreaterThanOrEqualTo(4);
        assertThat(values[0]).isSameAs(JapaneseEra.MEIJI);
        assertThat(values[1]).isSameAs(JapaneseEra.TAISHO);
        assertThat(values[2]).isSameAs(JapaneseEra.SHOWA);
        assertThat(values[3]).isSameAs(JapaneseEra.HEISEI);
    }

}
