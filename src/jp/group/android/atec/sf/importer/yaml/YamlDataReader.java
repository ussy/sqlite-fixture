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
package jp.group.android.atec.sf.importer.yaml;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import jp.group.android.atec.sf.data.ColumnInfo;
import jp.group.android.atec.sf.data.Row;
import jp.group.android.atec.sf.importer.SQLiteDataReader;

import org.yaml.snakeyaml.Yaml;

import android.util.Log;

/**
 * YAML 形式のテストデータを読み込むクラスです。
 * 
 * @author ussy
 */
public class YamlDataReader implements SQLiteDataReader {

    private String charset;

    /**
     * UTF-8 のファイルに対応します。
     */
    public YamlDataReader() {
        this("utf-8");
    }

    /**
     * YAML ファイルの文字コードを指定します。
     * 
     * @param charset
     *            文字コード
     */
    public YamlDataReader(String charset) {
        this.charset = charset;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int read(InputStream in, ColumnInfo[] columns, Callback callback) {
        try {
            int count = 0;
            Yaml yaml = new Yaml();
            for (Object o : yaml.loadAll(new InputStreamReader(in, charset))) {
                @SuppressWarnings("unchecked")
                Map<String, Object> param = (Map<String, Object>) o;
                if (param == null || param.isEmpty()) {
                    continue;
                }

                if (!hasAllKeys(param, columns)) {
                    Log.w("Fixtures", String.format("not mutch columns. ignore %d th", count + 1));
                    continue;
                }

                Row row = new Row(++count);
                for (int i = 0; i < columns.length; i++) {
                    Object value = param.get(columns[i].getName());
                    row.add(columns[i], value == null ? null : value.toString());
                }

                callback.handleRow(row);
            }

            return count;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean hasAllKeys(Map<String, Object> param, ColumnInfo[] columns) {
        for (ColumnInfo column : columns) {
            if (!param.containsKey(column.getName())) {
                return false;
            }
        }

        return true;
    }
}
