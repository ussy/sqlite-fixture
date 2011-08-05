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

import android.database.sqlite.SQLiteDatabase;

/**
 * SQLite にインポートするデータを取得するインターフェース
 * 
 * @author ussy
 */
public interface SQLiteImporter {

    /**
     * 指定したパスからデータを取得し SQLite にインポートします。
     * 
     * @param path
     *            インポートするエントリパス
     * @param db
     *            データベース
     */
    void importData(String path, SQLiteDatabase db);

}
