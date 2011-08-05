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

import java.io.IOException;

import net.iharder.Base64;
import android.content.ContentValues;

/**
 * 行データ変換クラスです。
 * 
 * @author ussy
 */
public class RowConverter {

    /**
     * 登録する行データを {@link ContentValues} に変換します。
     * 
     * @param row
     *            行データ
     * @return {@link ContentValues}
     * @throws ConvertException
     *             変換に失敗したときに発生します。
     */
    public ContentValues toContentValues(Row row) {
        ContentValues values = new ContentValues();
        for (Item item : row) {
            ColumnInfo column = item.getColumn();
            String name = column.getName();
            String value = item.getValue();
            if (value == null || value == "") {
                if (column.isPrimaryKey() || column.isNotNull()) {
                    throw new SQLNotNullException(name);
                }

                values.putNull(name);
            } else {
                ColumnType type = column.getType();
                switch (type) {
                case INTEGER:
                    values.put(name, parseInteger(value));
                    break;
                case TEXT:
                    values.put(name, parseText(value));
                    break;
                case NONE:
                    values.put(name, parseNone(value));
                    break;
                case REAL:
                    values.put(name, parseReal(value));
                    break;
                case NUMERIC:
                    values.put(name, parseNumeric(value));
                    break;
                default:
                    throw new IllegalStateException(String.format("not supported type: %s", type.name()));
                }
            }
        }

        return values;
    }

    protected Long parseInteger(String value) {
        if (value == null || value.length() == 0) {
            return null;
        }

        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new ConvertException(e, value);
        }
    }

    protected String parseText(String value) {
        return value;
    }

    protected byte[] parseNone(String value) {
        if (value == null || value.length() == 0) {
            return null;
        }

        try {
            return Base64.decode(value);
        } catch (IllegalArgumentException e) {
            throw new ConvertException(e, value);
        } catch (IOException e) {
            throw new ConvertException(e, value);
        }
    }

    protected Double parseReal(String value) {
        if (value == null || value.length() == 0) {
            return null;
        }

        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new ConvertException(e, value);
        }
    }

    protected String parseNumeric(String value) {
        return value;
    }
}
