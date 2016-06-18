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

import java.io.IOException;
import java.io.InputStream;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.glad2121.calendar.CalendarException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 和暦の元号の一覧。
 *
 * @author GLAD!!
 */
class JapaneseEras {

    // ---- constants

    /**
     * 設定ファイルのパス。
     */
    static final String CONFIG_PATH = "japanese-eras.xml";

    /**
     * 明治から平成までの4件は必須。
     */
    static final int MIN_SIZE = 4;

    /**
     * 値のオフセット（明治の値）。
     */
    static final int OFFSET = 1;

    /**
     * リソースの名前。
     */
    static final String RESOURCE_NAME = "japanese-eras";

    /**
     * リソースのキーの接頭辞。
     */
    static final String PREFIX = "japanese-era.";

    // ---- fields

    /**
     * 元号の一覧。
     */
    private final List<JapaneseEra> eras;

    /**
     * リソース。
     */
    private final ResourceBundle resources;

    // ---- constructors

    /**
     * オブジェクトを構築します。
     */
    JapaneseEras() {
        try (InputStream in = getResourceAsStream(CONFIG_PATH)) {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            SaxHandler handler = new SaxHandler();
            parser.parse(in, handler);
            List<JapaneseEra> eras = handler.eras;
            checkJapaneseEras(eras);
            this.eras = Collections.unmodifiableList(eras);
        } catch (ParserConfigurationException e) {
            throw new CalendarException(e);
        } catch (SAXException e) {
            throw new CalendarException(e);
        } catch (IOException e) {
            throw new CalendarException(e);
        }
        this.resources = ResourceBundle.getBundle(RESOURCE_NAME);
    }

    /**
     * 指定された名前のリソースを {@code InputStream} で返します。
     *
     * @param name リソース名
     * @return {@code InputStream}
     */
    InputStream getResourceAsStream(String name) {
        InputStream in = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(name);
        if (in == null) {
            throw new CalendarException("Resource not found: " + name);
        }
        return in;
    }

    /**
     * 元号の一覧をチェックします。
     *
     * @param eras 元号の一覧
     */
    void checkJapaneseEras(List<JapaneseEra> eras) {
        int size = eras.size();
        // MIN_SIZE 件以上登録されていること。
        if (size < MIN_SIZE) {
            throw new CalendarException("Invalid era size: " + size);
        }

        LocalDate prevSince = LocalDate.MIN;
        for (int i = 0; i < size; ++i) {
            JapaneseEra era = eras.get(i);
            int value = era.getValue();
            LocalDate since = era.getSince();
            // 値が OFFSET から始まる連続値であること。
            // 適用開始日順に整列されていること。
            if (value != OFFSET + i || since.compareTo(prevSince) <= 0) {
                throw new CalendarException("Invalid era config: " + era);
            }
            prevSince = since;
        }
    }

    // ---- eras

    /**
     * 元号の一覧を返します。
     *
     * @return 元号の一覧
     */
    public List<JapaneseEra> getEras() {
        return eras;
    }

    /**
     * 元号の配列に変換します。
     *
     * @return 元号の配列
     */
    public JapaneseEra[] toArray() {
        return eras.toArray(new JapaneseEra[eras.size()]);
    }

    /**
     * 指定された値の元号を返します。
     *
     * @param value 元号の値
     * @return 元号
     */
    public JapaneseEra get(int value) {
        int index = value - OFFSET;
        if (index < 0 || eras.size() <= index) {
            throw new DateTimeException("Invalid era value: " + value);
        }
        return eras.get(index);
    }

    /**
     * 指定された名前の元号を返します。
     *
     * @param name 元号の名前
     * @return 元号
     */
    public JapaneseEra get(String name) {
        Objects.requireNonNull(name, "name");
        for (JapaneseEra era : eras) {
            if (era.getName().equals(name)) {
                return era;
            }
        }
        throw new IllegalArgumentException("Invalid era name: " + name);
    }

    /**
     * 指定された日付の元号を返します。
     *
     * @param date 日付
     * @return 元号
     */
    public JapaneseEra get(LocalDate date) {
        Objects.requireNonNull(date, "date");
        for (ListIterator<JapaneseEra> i = eras.listIterator(); i.hasPrevious();) {
            JapaneseEra era = i.previous();
            if (era.getSince().compareTo(date) <= 0) {
                return era;
            }
        }
        throw new DateTimeException("Unsupported date: " + date);
    }

    // ---- localized names

    ResourceBundle getResources() {
        return resources;
    }

    ResourceBundle getResources(Locale locale) {
        if (locale == null || locale.equals(Locale.getDefault())) {
            return resources;
        }
        return ResourceBundle.getBundle(RESOURCE_NAME, locale);
    }

    String getString(String key) {
        return getResources().getString(key);
    }

    String getString(String key, Locale locale) {
        return getResources(locale).getString(key);
    }

    /**
     * 短い名前を返します。
     *
     * @param value 元号の値
     * @return 短い名前
     */
    public String getShortName(int value) {
        return getString(PREFIX + value + ".short");
    }

    /**
     * 短い名前を返します。
     *
     * @param value 元号の値
     * @param locale ロケール
     * @return 短い名前
     */
    public String getShortName(int value, Locale locale) {
        return getString(PREFIX + value + ".short", locale);
    }

    /**
     * 長さが中位の名前を返します。
     *
     * @param value 元号の値
     * @return 長さが中位の名前
     */
    public String getMediumName(int value) {
        return getString(PREFIX + value + ".medium");
    }

    /**
     * 長さが中位の名前を返します。
     *
     * @param value 元号の値
     * @param locale ロケール
     * @return 長さが中位の名前
     */
    public String getMediumName(int value, Locale locale) {
        return getString(PREFIX + value + ".medium", locale);
    }

    /**
     * 長い名前を返します。
     *
     * @param value 元号の値
     * @return 長い名前
     */
    public String getLongName(int value) {
        return getString(PREFIX + value + ".long");
    }

    /**
     * 長い名前を返します。
     *
     * @param value 元号の値
     * @param locale ロケール
     * @return 長い名前
     */
    public String getLongName(int value, Locale locale) {
        return getString(PREFIX + value + ".long", locale);
    }

    /**
     * 最初の年の呼び名を返します。
     *
     * @return 最初の年の呼び名
     */
    public String getFirstYearText() {
        return getString(PREFIX + "first-year");
    }

    /**
     * 最初の年の呼び名を返します。
     *
     * @param locale ロケール
     * @return 最初の年の呼び名
     */
    public String getFirstYearText(Locale locale) {
        return getString(PREFIX + "first-year", locale);
    }

    // ---- nested class

    /**
     * 設定ファイルを解析するハンドラです。
     */
    static class SaxHandler extends DefaultHandler {

        final List<JapaneseEra> eras = new ArrayList<>();

        @Override
        public void startElement(String uri, String localName, String qName,
                Attributes attributes) {
            if ("japanese-era".equals(qName)) {
                addJapaneseEra(attributes);
            }
        }

        void addJapaneseEra(Attributes attrs) {
            int value = Integer.parseInt(getValue(attrs, "value"));
            String name = getValue(attrs, "name");
            String abbr = getValue(attrs, "abbr");
            LocalDate since = LocalDate.parse(getValue(attrs, "since"));
            eras.add(new JapaneseEra(value, name, abbr, since));
        }

        String getValue(Attributes attrs, String qName) {
            String value = attrs.getValue(qName);
            Objects.requireNonNull(value, qName);
            return value;
        }

    }

    // ---- singleton

    /**
     * 唯一のインスタンス。
     */
    public static final JapaneseEras INSTANCE = new JapaneseEras();

}
