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

import java.io.Serializable;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Objects;

/**
 * 和暦の日付。
 *
 * @author GLAD!!
 */
public class JapaneseDate implements ChronoLocalDate, Serializable {

    private static final long serialVersionUID = 1L;

    // ---- constants

    /**
     * 日本にグレゴリオ暦が導入された日。
     */
    static final LocalDate START_DATE = LocalDate.of(1873, 1, 1);

    // ---- fields

    /**
     * ISO ローカル日付。
     */
    private final LocalDate isoDate;

    /**
     * 元号。
     */
    private transient JapaneseEra era;

    /**
     * 和暦年。
     */
    private transient int yearOfEra;

    // ---- constructors

    /**
     * オブジェクトを構築します。
     *
     * @param isoDate ISO ローカル日付
     */
    JapaneseDate(LocalDate isoDate) {
        if (isoDate.isBefore(START_DATE)) {
            throw new DateTimeException(
                    "JapaneseDate before Meiji 6 is not supported");
        }
        this.isoDate = isoDate;
        this.era = JapaneseEras.INSTANCE.get(isoDate);
        this.yearOfEra = isoDate.getYear() - era.getSince().getYear() + 1;
    }

    /**
     * オブジェクトを構築します。
     *
     * @param era     元号
     * @param year    和暦年
     * @param isoDate ISO ローカル日付
     */
    JapaneseDate(JapaneseEra era, int year, LocalDate isoDate) {
        if (isoDate.isBefore(START_DATE)) {
            throw new DateTimeException(
                    "JapaneseDate before Meiji 6 is not supported");
        }
        this.era = era;
        this.yearOfEra = year;
        this.isoDate = isoDate;
    }

    // ---- static methods

    public static JapaneseDate now() {
        return now(Clock.systemDefaultZone());
    }

    public static JapaneseDate now(ZoneId zone) {
        return now(Clock.system(zone));
    }

    public static JapaneseDate now(Clock clock) {
        return new JapaneseDate(LocalDate.now(clock));
    }

    public static JapaneseDate of(
            JapaneseEra era, int yearOfEra, int month, int dayOfMonth) {
        Objects.requireNonNull(era, "era");
        int year = JapaneseChronology.INSTANCE.prolepticYear(era, yearOfEra);
        LocalDate date = LocalDate.of(year, month, dayOfMonth);
        return new JapaneseDate(era, yearOfEra, date);
    }

    public static JapaneseDate of(
            int prolepticYear, int month, int dayOfMonth) {
        return new JapaneseDate(LocalDate.of(prolepticYear, month, dayOfMonth));
    }

    static JapaneseDate ofYearDay(
            JapaneseEra era, int yearOfEra, int dayOfYear) {
        Objects.requireNonNull(era, "era");
        int year = JapaneseChronology.INSTANCE.prolepticYear(era, yearOfEra);
        LocalDate date = LocalDate.ofYearDay(year, dayOfYear);
        return new JapaneseDate(era, yearOfEra, date);
    }

    public static JapaneseDate from(TemporalAccessor temporal) {
        return JapaneseChronology.INSTANCE.date(temporal);
    }

    // ---- accessors

    @Override
    public JapaneseChronology getChronology() {
        return JapaneseChronology.INSTANCE;
    }

    @Override
    public JapaneseEra getEra() {
        return era;
    }

    public int getYearOfEra() {
        return yearOfEra;
    }

    public int getMonthValue() {
        return isoDate.getMonthValue();
    }

    public int getDayOfMonth() {
        return isoDate.getDayOfMonth();
    }

    @Override
    public int lengthOfMonth() {
        return isoDate.lengthOfMonth();
    }

    // ----

    @Override
    public long getLong(TemporalField field) {
        return field.getFrom(this);
    }

    @Override
    public long until(Temporal endExclusive, TemporalUnit unit) {
        Objects.requireNonNull(endExclusive, "endExclusive");
        Objects.requireNonNull(unit, "unit");
        ChronoLocalDate end = getChronology().date(endExclusive);
        if (!(unit instanceof ChronoUnit)) {
            return unit.between(this, end);
        }
        switch ((ChronoUnit) unit) {
        case DAYS:
        default:
            throw new UnsupportedTemporalTypeException("Unsupported unit: " + unit);
        }
    }

    @Override
    public ChronoPeriod until(ChronoLocalDate endDateExclusive) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public long toEpochDay() {
        return isoDate.toEpochDay();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof JapaneseDate) {
            return isoDate.equals(((JapaneseDate) other).isoDate);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return isoDate.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s%02d.%02d.%02d",
                getEra().getAbbr(), getYearOfEra(), getMonthValue(), getDayOfMonth());
    }

}
