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
package jp.group.android.atec.sf.importer.csv;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import jp.group.android.atec.sf.data.ColumnInfo;
import jp.group.android.atec.sf.data.Row;
import jp.group.android.atec.sf.importer.SQLiteDataReader;
import android.util.Log;
import au.com.bytecode.opencsv.CSVReader;

/**
 * CSV 形式のテストデータを読み込むクラスです。
 * 
 * @author ussy
 */
public class CsvDataReader implements SQLiteDataReader {

    private String charset;

    /**
     * UTF-8 のファイルに対応します。
     */
    public CsvDataReader() {
        this("utf-8");
    }

    /**
     * CSV ファイルの文字コードを指定します。
     * 
     * @param charset
     *            文字コード
     */
    public CsvDataReader(String charset) {
        this.charset = charset;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int read(InputStream in, ColumnInfo[] columns, Callback callback) {
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(in, charset));
            int line = 0;
            int count = 0;
            String[] values = null;
            while ((values = reader.readNext()) != null) {
                line++;

                if (values.length == 0) {
                    continue;
                }

                if (columns.length != values.length) {
                    Log.w("Fixtures", String.format("ignore line: %d", line));
                    continue;
                }

                Row row = new Row(++count);
                for (int i = 0; i < values.length; i++) {
                    row.add(columns[i], values[i]);
                }

                callback.handleRow(row);
            }

            return count;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
