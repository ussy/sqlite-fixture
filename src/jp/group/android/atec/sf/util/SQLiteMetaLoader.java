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
package jp.group.android.atec.sf.util;

import java.util.ArrayList;

import jp.group.android.atec.sf.data.ColumnInfo;
import jp.group.android.atec.sf.data.ColumnType;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * SQLite のメタデータをロードするクラス
 * 
 * @author ussy
 */
public class SQLiteMetaLoader {

    private SQLiteDatabase db;

    /**
     * @param db
     *            対象とするデータベース
     */
    public SQLiteMetaLoader(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * テーブルのすべてのカラム情報を取得します。
     * 
     * @param table
     *            テーブル
     * @return すべてのカラム情報。存在しないテーブルの場合は空。
     */
    public ColumnInfo[] load(String table) {
        String query = String.format("PRAGMA table_info(%s)", table);
        Cursor cursor = db.rawQuery(query, null);
        try {
            ArrayList<ColumnInfo> columns = new ArrayList<ColumnInfo>();
            while (cursor.moveToNext()) {
                int index = cursor.getInt(0);
                String name = cursor.getString(1);
                ColumnType type = ColumnType.parse(cursor.getString(2));
                boolean notNull = cursor.getInt(3) != 0;
                String defaultValue = cursor.getString(4);
                boolean primaryKey = cursor.getInt(5) == 1;

                columns.add(new ColumnInfo(index, name, type, notNull, defaultValue, primaryKey));
            }

            return columns.toArray(new ColumnInfo[0]);
        } finally {
            cursor.close();
        }
    }
}
