/*
 * Copyright (C) 2008-2015 GLAD!! (ITO Yoshiichi)
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
        JapaneseEra meiji = JapaneseEra.MEIJI;
        assertThat(JapaneseEra.of(1)).isSameAs(meiji);
        assertThat(JapaneseEra.valueOf("Meiji")).isSameAs(meiji);
        assertThat(meiji.getValue()).isEqualTo(1);
        assertThat(meiji.getName()).isEqualTo("Meiji");
        assertThat(meiji.getAbbr()).isEqualTo("M");
        assertThat(meiji.getSince()).isEqualTo(LocalDate.of(1868, 1, 1));
        assertThat(meiji.getShortName()).isEqualTo("M");
        assertThat(meiji.getShortName(Locale.US)).isEqualTo("M");
        assertThat(meiji.getMediumName()).isEqualTo("明");
        assertThat(meiji.getMediumName(Locale.US)).isEqualTo("M");
        assertThat(meiji.getLongName()).isEqualTo("明治");
        assertThat(meiji.getLongName(Locale.US)).isEqualTo("Meiji");
        assertThat(meiji.toString()).isEqualTo("Meiji");
    }

    @Test
    public void testTaisho() {
        JapaneseEra taisho = JapaneseEra.TAISHO;
        assertThat(taisho.getValue()).isEqualTo(2);
    }

    @Test
    public void testShowa() {
        JapaneseEra showa = JapaneseEra.SHOWA;
        assertThat(showa.getValue()).isEqualTo(3);
    }

    @Test
    public void testHeisei() {
        JapaneseEra heisei = JapaneseEra.HEISEI;
        assertThat(heisei.getValue()).isEqualTo(4);
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
