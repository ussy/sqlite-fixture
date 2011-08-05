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
 * カラム情報を保持するクラス
 * 
 * @author ussy
 */
public class ColumnInfo {

    private int index;

    /**
     * インデックスを取得します。
     * 
     * @return インデックス
     */
    public int getIndex() {
        return index;
    }

    private String name;

    /**
     * カラム名を取得します。
     * 
     * @return カラム名
     */
    public String getName() {
        return name;
    }

    private ColumnType type;

    /**
     * データ型を取得します。
     * 
     * @return データ型
     */
    public ColumnType getType() {
        return type;
    }

    private boolean notNull;

    /**
     * NotNull 制約であるか確認します。
     * 
     * @return NotNull であれば <code>true</code>
     */
    public boolean isNotNull() {
        return notNull;
    }

    private String defaultValue;

    /**
     * デフォルト値を取得します。
     * 
     * @return デフォルト値
     */
    public String getDefalutValue() {
        return defaultValue;
    }

    private boolean primaryKey;

    /**
     * プライマリーキーであるか確認します。
     * 
     * @return プライマリーキーであれば <code>true</code>
     */
    public boolean isPrimaryKey() {
        return primaryKey;
    }

    /**
     * @param index
     *            インデックス
     * @param name
     *            カラム名
     * @param type
     *            カラム型
     * @param notNull
     *            NotNull 制約
     * @param defaultValue
     *            デフォルト値
     * @param primaryKey
     *            プライマリーキー
     */
    public ColumnInfo(int index, String name, ColumnType type, boolean notNull, String defaultValue, boolean primaryKey) {
        this.index = index;
        this.name = name;
        this.type = type;
        this.notNull = notNull;
        this.defaultValue = defaultValue;
        this.primaryKey = primaryKey;
    }
}
