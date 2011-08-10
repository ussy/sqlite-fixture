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

import java.io.File;
import java.io.InputStream;

import jp.group.android.atec.sf.data.ColumnInfo;
import jp.group.android.atec.sf.data.Row;
import jp.group.android.atec.sf.data.RowConverter;
import jp.group.android.atec.sf.importer.SQLiteDataReader.Callback;
import jp.group.android.atec.sf.util.ResourceUtils;
import jp.group.android.atec.sf.util.SQLiteMetaLoader;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * {@link SQLiteImporter} のデフォルト実装です。
 * 
 * 指定したディレクトリパスを基点に、ファイル一覧をインポート対象とします。
 * 
 * @author ussy
 */
public class DefaultSQLiteImporter implements SQLiteImporter {

    private Context context;

    private SQLiteDataReader reader;

    /**
     * ファイルフォーマットによって {@link SQLiteDataReader} を切り替える必要があります。 
     * 
     * @param context
     *            テストに使用するコンテキスト
     * @param reader
     *            {@link SQLiteDataReader}
     */
    public DefaultSQLiteImporter(Context context, SQLiteDataReader reader) {
        this.context = context;
        this.reader = reader;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void importData(String dir, final SQLiteDatabase db) {
    	if (dir == null || dir.equals("")) {
    		throw new ImportException("dir must not null be or empty");
    	}
    	
        Log.i("fixtures", String.format("find files from %s", dir));
        String[] fileNames = ResourceUtils.listFromAsset(context, dir);
        SQLiteMetaLoader metaLoader = new SQLiteMetaLoader(db);
        for (final String fileName : fileNames) {
            InputStream in = null;
            try {
                String path = dir + File.separator + fileName;
                Log.i("fixtures", String.format("import data from %s", path));

                in = ResourceUtils.openFromAsset(context, path);
                final String table = removeExtension(fileName);
                // All deletions are executed before data is registered. 
                db.delete(table, null, null);
                ColumnInfo[] columns = metaLoader.load(table);
                int count = reader.read(in, columns, new Callback() {
                    @Override
                    public void handleRow(Row row) {
                        RowConverter converter = new RowConverter();
                        ContentValues values = converter.toContentValues(row);
                        db.insert(table, null, values);
                    }
                });
                Log.i("fixtures", String.format("imported %d records in %s", count, table));
            } catch (Exception e) {
                throw new ImportException(e);
            } finally {
                ResourceUtils.close(in);
            }
        }

        Log.i("fixtures", "import completed");
    }

    String removeExtension(String path) {
        int index = path.lastIndexOf(".");
        return (index != -1) ? path.substring(0, index) : path;
    }
}
