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
package jp.group.android.atec.sf.importer;

import java.io.InputStream;

import jp.group.android.atec.sf.data.ColumnInfo;
import jp.group.android.atec.sf.data.Row;

/**
 * SQLite に投入するデータを読み込むインターフェースです。
 * 
 * @author ussy
 */
public interface SQLiteDataReader {

    /**
     * 対象データを読み込みます。
     * 
     * @param in
     *            入力ストリーム
     * @param columns
     *            読み込むデータの対象カラム
     * @param callback
     *            1 行分読み込んだときに行うコールバック処理
     * @return 正常に読み込んだ件数を返します
     */
    int read(InputStream in, ColumnInfo[] columns, Callback callback);

    /**
     * 行毎にデータをハンドリングして処理します。
     * 
     * @author ussy
     */
    public static interface Callback {

        /**
         * データをハンドリングし、データベースにデータを追加します。
         * 
         * @param row
         *            行データ
         */
        void handleRow(Row row);

    }
}
