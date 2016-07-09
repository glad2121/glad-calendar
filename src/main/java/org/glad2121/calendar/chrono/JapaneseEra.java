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

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.chrono.Era;
import java.util.Locale;

/**
 * 和暦の元号。
 * <p>
 * Date Time API と異なり、明治から順に1、2、… の順に番号付けされます。
 *
 * @author GLAD!!
 */
public final class JapaneseEra
        implements Era, Serializable, Comparable<JapaneseEra> {

    private static final long serialVersionUID = 1L;

    // ---- constants

    /**
     * 明治 (1)。
     */
    public static final JapaneseEra MEIJI =
            new JapaneseEra(1, "Meiji", "M", LocalDate.of(1868, 1, 1));

    /**
     * 大正 (2)。
     */
    public static final JapaneseEra TAISHO =
            new JapaneseEra(2, "Taisho", "T", LocalDate.of(1912, 7, 30));

    /**
     * 昭和 (3)。
     */
    public static final JapaneseEra SHOWA =
            new JapaneseEra(3, "Showa", "S", LocalDate.of(1926, 12, 25));

    /**
     * 平成 (4)。
     */
    public static final JapaneseEra HEISEI =
            new JapaneseEra(4, "Heisei", "H", LocalDate.of(1989, 1, 8));

    // ---- fields

    /**
     * 元号の値。
     *
     * @serial
     */
    private final int value;

    /**
     * 元号の名前。
     */
    private final transient String name;

    /**
     * 元号の略称。
     */
    private final transient String abbr;

    /**
     * 適用開始日。
     */
    private final transient LocalDate since;

    // ---- constructors

    /**
     * オブジェクトを構築します。
     *
     * @param value 元号の値
     * @param name  元号の名前
     * @param abbr  元号の略称
     * @param since 適用開始日
     */
    JapaneseEra(int value, String name, String abbr, LocalDate since) {
        this.value = value;
        this.name = name;
        this.abbr = abbr;
        this.since = since;
    }

    // ---- static methods

    /**
     * 元号の配列を返します。
     *
     * @return 元号の配列
     */
    public static JapaneseEra[] values() {
        return JapaneseEras.INSTANCE.toArray();
    }

    /**
     * 指定された値をもつ元号を返します。
     *
     * @param value 元号の値
     * @return 元号
     */
    public static JapaneseEra of(int value) {
        return JapaneseEras.INSTANCE.get(value);
    }

    /**
     * 指定された名前をもつ元号を返します。
     *
     * @param name 元号の名前
     * @return 元号
     */
    public static JapaneseEra valueOf(String name) {
        return JapaneseEras.INSTANCE.get(name);
    }

    // ---- accessors

    /**
     * 元号の値を返します。
     * <p>
     * 明治から順に1、2、… の順に番号付けされます。
     *
     * @return 元号の値
     */
    @Override
    public int getValue() {
        return value;
    }

    /**
     * 元号の名前を返します。
     *
     * @return 元号の名前
     */
    public String getName() {
        return name;
    }

    /**
     * 元号の略称を返します。
     *
     * @return 元号の略称
     */
    public String getAbbr() {
        return abbr;
    }

    /**
     * 適用開始日を返します。
     *
     * @return 適用開始日
     */
    public LocalDate getSince() {
        return since;
    }

    // ---- localized names

    /**
     * 短い名前を返します。
     *
     * @return 短い名前。
     */
    public String getShortName() {
        return JapaneseEras.INSTANCE.getShortName(value);
    }

    /**
     * 短い名前を返します。
     *
     * @param locale ロケール
     * @return 短い名前
     */
    public String getShortName(Locale locale) {
        return JapaneseEras.INSTANCE.getShortName(value, locale);
    }

    /**
     * 長さが中位の名前を返します。
     *
     * @return 長さが中位の名前
     */
    public String getMediumName() {
        return JapaneseEras.INSTANCE.getMediumName(value);
    }

    /**
     * 長さが中位の名前を返します。
     *
     * @param locale ロケール
     * @return 長さが中位の名前
     */
    public String getMediumName(Locale locale) {
        return JapaneseEras.INSTANCE.getMediumName(value, locale);
    }

    /**
     * 長い名前を返します。
     *
     * @return 長い名前
     */
    public String getLongName() {
        return JapaneseEras.INSTANCE.getLongName(value);
    }

    /**
     * 長い名前を返します。
     *
     * @param locale ロケール
     * @return 長い名前
     */
    public String getLongName(Locale locale) {
        return JapaneseEras.INSTANCE.getLongName(value, locale);
    }

    /**
     * 最初の年の名前を返します。
     *
     * @return 最初の年の名前
     */
    public static String getFirstYearText() {
        return JapaneseEras.INSTANCE.getFirstYearText();
    }

    // ---- java.lang.Comparable

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(JapaneseEra other) {
        return Integer.compare(value, other.value);
    }

    // ---- java.lang.Object

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getName();
    }

    // ---- serialization

    /**
     * @serialData JapaneseEras に登録されているオブジェクトを返します。
     */
    private Object readResolve() throws ObjectStreamException {
        try {
            return of(value);
        } catch (Exception e) {
            throw new InvalidObjectException(e.getMessage());
        }
    }

}
