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
package jp.group.android.atec.sf.unit;

import java.lang.reflect.Method;

import jp.group.android.atec.sf.data.ColumnInfo;
import jp.group.android.atec.sf.importer.FileType;
import jp.group.android.atec.sf.importer.SQLiteImportFactory;
import jp.group.android.atec.sf.importer.SQLiteImporter;
import jp.group.android.atec.sf.util.SQLiteMetaLoader;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

/**
 * データベースを利用したテストを行うための基底テストケースクラスです。
 * 
 * @author ussy
 */
public abstract class DatabaseTestCase extends AndroidTestCase {

    private SQLiteDatabase db;

    /**
     * @return {@link #createSQLiteOpenHelper()} で生成した {@link SQLiteDatabase}
     */
    protected SQLiteDatabase getSQLiteDatabase() {
        return db;
    }

    private Context context;

    /**
     * {@link #createDatabaseContext()} で生成した {@link Context} を取得します。
     * 
     * <p>
     * デフォルトでは {@link #getContext()} で取得したコンテキストを {@link RenamingDelegatingContext} でラップしたものを取得します。
     * </p>
     * 
     * @return データベースに使用するテスト用コンテキスト
     */
    protected Context getDatabaseContext() {
        return context;
    }

    /**
     * データベースに使用するコンテキストを生成します。
     * 
     * デフォルトでは {@link RenamingDelegatingContext} を使用して、元のデータベースには影響を与えないようにします。
     * 
     * @return {@link RenamingDelegatingContext}
     */
    protected Context createDatabaseContext() {
        return new RenamingDelegatingContext(getContext(), "test_");
    }

    /**
     * データベースのテストに使用する {@link SQLiteOpenHelper} を生成します。
     * 
     * {@link #getDatabaseContext()} を利用して、 {@link SQLiteOpenHelper} を生成してください。
     * 
     * @return {@link SQLiteDatabase}
     */
    protected abstract SQLiteOpenHelper createSQLiteOpenHelper();

    /**
     * テストプロジェクトのコンテキストを取得します。
     * 
     * <p>
     * テストプロジェクトのリソースにアクセスしたい場合に利用します。
     * </p>
     * 
     * @return テストプロジェクトのコンテキスト
     */
    protected Context getTestProjectContext() {
        try {
            // テスト用コンテキストを取得する方法がないため、リフレクションで取得
            Method method = getClass().getMethod("getTestContext");
            return (Context) method.invoke(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * データベースから指定したテーブルのカラム情報を取得します。
     * 
     * @param table
     *            テーブル名
     * @return カラム情報
     */
    public ColumnInfo[] loadColumnInfos(String table) {
        return new SQLiteMetaLoader(db).load(table);
    }

    /**
     * データベースを切り替えます。
     * 
     * <p>
     * バージョンが違う {@link SQLiteOpenHelper} を指定した場合は、アップグレード処理が実行されます。
     * </p>
     * 
     * @param helper
     *            {@link SQLiteOpenHelper}
     */
    public void replaceSQLiteOpenHelper(SQLiteOpenHelper helper) {
        if (db.isOpen()) {
            db.close();
        }

        db = helper.getWritableDatabase();
    }

    /**
     * 指定したファイル種別、パスから SQLite にデータをインポートします。
     * 
     * <p>
     * テストデータは UTF-8 で記述されていることを想定します。
     * </p>
     * 
     * @param t
     *            インポートするデータ種別
     * @param path
     *            インポートするパス
     */
    protected void importData(FileType t, String path) {
        importData(t, path, "utf-8");
    }

    /**
     * 指定した種別、パスから SQLite にデータをインポートします。
     * 
     * @param t
     *            インポートするデータ種別
     * @param path
     *            インポートする パス
     * @param charset
     *            インポートするファイルの文字コード
     */
    protected void importData(FileType t, String path, String charset) {
        SQLiteImportFactory factory = new SQLiteImportFactory(getTestProjectContext());
        SQLiteImporter importer = factory.create(t, charset);
        importer.importData(path, db);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void runBare() throws Throwable {
        context = createDatabaseContext();
        SQLiteOpenHelper helper = createSQLiteOpenHelper();
        if (helper == null) {
            throw new NullPointerException("helper must not be null");
        }

        try {
            db = helper.getWritableDatabase();
            super.runBare();
        } finally {
            if (db.isOpen()) {
                db.close();
            }
        }
    }
}
