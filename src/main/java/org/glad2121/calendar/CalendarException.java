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
package org.glad2121.calendar;

/**
 * カレンダーライブラリで発生する例外です。
 *
 * @author GLAD!!
 */
public class CalendarException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 例外を構築します。
     */
    public CalendarException() {
    }

    /**
     * 例外を構築します。
     *
     * @param message メッセージ
     */
    public CalendarException(String message) {
        super(message);
    }

    /**
     * 例外を構築します。
     *
     * @param cause 原因
     */
    public CalendarException(Throwable cause) {
        super(cause);
    }

    /**
     * 例外を構築します。
     *
     * @param message メッセージ
     * @param cause 原因
     */
    public CalendarException(String message, Throwable cause) {
        super(message, cause);
    }

}
