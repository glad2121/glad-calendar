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

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.time.chrono.AbstractChronology;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Era;
import java.time.chrono.IsoChronology;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.List;

/**
 * 和暦。
 *
 * @author GLAD!!
 */
public class JapaneseChronology extends AbstractChronology
        implements Serializable {

    private static final long serialVersionUID = 1L;

    // ---- constructors

    JapaneseChronology() {
    }

    // ----

    @Override
    public String getId() {
        return "GladJapanese";
    }

    @Override
    public String getCalendarType() {
        return "japanese";
    }

    // ---- factories

    @Override
    public JapaneseDate dateNow() {
        return JapaneseDate.now();
    }

    @Override
    public JapaneseDate dateNow(ZoneId zone) {
        return JapaneseDate.now(zone);
    }

    @Override
    public JapaneseDate dateNow(Clock clock) {
        return JapaneseDate.now(clock);
    }

    @Override
    public JapaneseDate date(Era era, int yearOfEra, int month, int dayOfMonth) {
        if (!(era instanceof JapaneseEra)) {
            throw new ClassCastException("Invalid era: " + era);
        }
        return JapaneseDate.of((JapaneseEra) era, yearOfEra, month, dayOfMonth);
    }

    @Override
    public JapaneseDate date(int prolepticYear, int month, int dayOfMonth) {
        return JapaneseDate.of(prolepticYear, month, dayOfMonth);
    }

    @Override
    public JapaneseDate dateYearDay(Era era, int year, int dayOfYear) {
        return JapaneseDate.ofYearDay((JapaneseEra) era, year, dayOfYear);
    }

    @Override
    public JapaneseDate dateYearDay(int prolepticYear, int dayOfYear) {
        return new JapaneseDate(LocalDate.ofYearDay(prolepticYear, dayOfYear));
    }

    @Override
    public JapaneseDate dateEpochDay(long epochDay) {
        return new JapaneseDate(LocalDate.ofEpochDay(epochDay));
    }

    @Override
    public JapaneseDate date(TemporalAccessor temporal) {
        if (temporal instanceof JapaneseDate) {
            return (JapaneseDate) temporal;
        }
        return new JapaneseDate(LocalDate.from(temporal));
    }

    @Override
    @SuppressWarnings("unchecked")
    public ChronoLocalDateTime<JapaneseDate> localDateTime(TemporalAccessor temporal) {
        return (ChronoLocalDateTime<JapaneseDate>) super.localDateTime(temporal);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ChronoZonedDateTime<JapaneseDate> zonedDateTime(TemporalAccessor temporal) {
        return (ChronoZonedDateTime<JapaneseDate>) super.zonedDateTime(temporal);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ChronoZonedDateTime<JapaneseDate> zonedDateTime(Instant instant, ZoneId zone) {
        return (ChronoZonedDateTime<JapaneseDate>) super.zonedDateTime(instant, zone);
    }

    // ----

    @Override
    public boolean isLeapYear(long prolepticYear) {
        return IsoChronology.INSTANCE.isLeapYear(prolepticYear);
    }

    @Override
    public int prolepticYear(Era era, int yearOfEra) {
        if (!(era instanceof JapaneseEra)) {
            throw new ClassCastException("Invalid era: " + era);
        }
        JapaneseEra jera = (JapaneseEra) era;
        if (yearOfEra <= 0) {
            throw new DateTimeException("Invalid yearOfEra: " + yearOfEra);
        }
        int year = jera.getSince().getYear() + yearOfEra - 1;
        if (year < JapaneseDate.START_DATE.getYear() || Year.MAX_VALUE < year) {
            throw new DateTimeException("Invalid proleptic year: " + year);
        }
        return year;
    }

    @Override
    public JapaneseEra eraOf(int eraValue) {
        return JapaneseEra.of(eraValue);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Era> eras() {
        return List.class.cast(JapaneseEras.INSTANCE.getEras());
    }

    JapaneseEra getFirstEra() {
        return JapaneseEras.INSTANCE.get(0);
    }

    JapaneseEra getCurrentEra() {
        List<JapaneseEra> eras = JapaneseEras.INSTANCE.getEras();
        return eras.get(eras.size() - 1);
    }

    // ----

    @Override
    public ValueRange range(ChronoField field) {
        switch (field) {
            case ALIGNED_DAY_OF_WEEK_IN_MONTH:
            case ALIGNED_DAY_OF_WEEK_IN_YEAR:
            case ALIGNED_WEEK_OF_MONTH:
            case ALIGNED_WEEK_OF_YEAR:
                throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
            case YEAR:
                ValueRange.of(JapaneseDate.START_DATE.getYear(), Year.MAX_VALUE);
            case YEAR_OF_ERA:
                ValueRange.of(1, Year.MAX_VALUE - getFirstEra().getSince().getYear() + 1);
            case ERA:
                ValueRange.of(getFirstEra().getValue(), getCurrentEra().getValue());
            default:
                return field.range();
        }
    }

    private Object readResolve() throws ObjectStreamException {
        return INSTANCE;
    }

    public static final JapaneseChronology INSTANCE = new JapaneseChronology();

}
