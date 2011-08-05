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

import jp.group.android.atec.sf.importer.csv.CsvDataReader;
import jp.group.android.atec.sf.importer.yaml.YamlDataReader;
import android.content.Context;

/**
 * {@link SQLiteImporter} を生成するファクトリクラス
 * 
 * @author ussy
 */
public class SQLiteImportFactory {

    private Context context;

    /**
     * @param context
     *            取り込むデータがある {@link Context}
     */
    public SQLiteImportFactory(Context context) {
        this.context = context;
    }

    /**
     * 生成する {@link SQLiteImporter} の種別をユーザーに指定させます。
     * 
     * @param t
     *            ファイル種別
     * @return 指定したファイル種別をサポートする {@link SQLiteImporter}
     */
    public SQLiteImporter create(FileType t) {
        return create(t, "utf-8");
    }

    /**
     * 生成する {@link SQLiteImporter} の種別、文字コードをユーザーに指定させます。
     * 
     * @param t
     *            ファイル種別
     * @param charset
     *            文字コード
     * @return 指定したファイル種別をサポートする {@link SQLiteImporter}
     */
    public SQLiteImporter create(FileType t, String charset) {
        return new DefaultSQLiteImporter(context, createDataReader(t, charset));
    }

    /**
     * {@link FileType} から {@link SQLiteDataReader} 実装クラスを生成します。
     * 
     * @param t
     *            ファイル種別
     * @param charset
     *            ファイル文字コード
     * @return {@link SQLiteDataReader} 実装クラス
     */
    protected SQLiteDataReader createDataReader(FileType t, String charset) {
        switch (t) {
        case Csv:
            return new CsvDataReader(charset);
        case Yaml:
            return new YamlDataReader(charset);
        }

        throw new IllegalArgumentException("not supported type:" + t);
    }
}
