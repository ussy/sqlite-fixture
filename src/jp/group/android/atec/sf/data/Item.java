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

/**
 * 項目を保持するクラスです。
 * 
 * @author ussy
 */
public class Item {

    private ColumnInfo column;

    private String value;

    /**
     * @param column
     *            カラム情報
     * @param value
     *            項目値
     */
    public Item(ColumnInfo column, String value) {
        this.column = column;
        this.value = value;
    }

    /**
     * カラム情報を取得します。
     * 
     * @return カラム情報
     */
    public ColumnInfo getColumn() {
        return column;
    }

    /**
     * 項目値を取得します。
     * 
     * @return 項目値
     */
    public String getValue() {
        return value;
    }

}
