/*******************************************************************************
 * Copyright 2011 Android Test and Evaluation Club
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package jp.group.android.atec.sf.data;

import jp.group.android.atec.sf.data.ColumnType;

/**
 * カラム型を表します
 * 
 * @author ussy
 */
public enum ColumnType {

    /**
     * 整数値
     */
    INTEGER,

    /**
     * 文字列
     */
    TEXT,

    /**
     * バイナリ
     */
    NONE,

    /**
     * 浮動小数点
     */
    REAL,

    /**
     * 数値、浮動小数点
     */
    NUMERIC;

    /**
     * 文字列を解析し、 SQLite で表される型に変換します。
     * 
     * @param type
     *            型の表す文字列
     * @return SQLite で表わされる型
     */
    public static ColumnType parse(String type) {
        String dataType = type == null ? null : type.toUpperCase();
        if (isInteger(dataType)) {
            return INTEGER;
        } else if (isText(dataType)) {
            return TEXT;
        } else if (isNone(dataType)) {
            return NONE;
        } else if (isReal(dataType)) {
            return REAL;
        }

        return NUMERIC;
    }

    static boolean isInteger(String dataType) {
        if (dataType == null) {
            return false;
        }

        return dataType.contains("INT");
    }

    static boolean isText(String dataType) {
        if (dataType == null) {
            return false;
        }

        return dataType.contains("CHAR") || dataType.contains("CLOB") || dataType.contains("TEXT");
    }

    static boolean isNone(String dataType) {
        if (dataType == null || dataType.trim().equals("")) {
            return true;
        }

        return dataType.contains("BLOB");
    }

    static boolean isReal(String dataType) {
        if (dataType == null) {
            return false;
        }

        return dataType.contains("REAL") || dataType.contains("FLOA") || dataType.contains("DOUB");
    }
}
