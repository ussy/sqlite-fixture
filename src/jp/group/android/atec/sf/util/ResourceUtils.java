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

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;

/**
 * リソース周りの操作を集めたユーティリティクラスです。
 * 
 * @author ussy
 */
public class ResourceUtils {

    /**
     * assets ディレクトリ以下の指定したディレクトリのファイル一覧を取得します。
     * 
     * @param context
     *            対象コンテキスト
     * @param dir
     *            ディレクトリ
     * @return ファイル一覧
     */
    public static String[] listFromAsset(Context context, String dir) {
        if (dir == null) {
            throw new NullPointerException("dir must not be null.");
        }
        
        AssetManager am = context.getResources().getAssets();
        if (dir.endsWith("/")) {
            dir = dir.substring(0, dir.length() - 1);
        }
        
        try {
            return am.list(dir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * assets ディレクトリ以下の指定したファイルを取得します。
     * 
     * @param context
     *            対象コンテキスト
     * @param path
     *            パス
     * @return 対象ファイルの入力ストリーム。ファイルが存在しない、ディレクトリの場合は <code>null</code>
     */
    public static InputStream openFromAsset(Context context, String path) {
        if (path == null) {
            throw new NullPointerException("path must not be null.");
        }
        
        AssetManager am = context.getResources().getAssets();        
        try {
            return am.open(path);
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * リソースの開放処理を行います。
     * 
     * <p>
     * {@link IOException} を例外を握りつぶします。
     * </p>
     * 
     * @param closeable
     *            {@link Closeable} インターフェースを実装しているオブジェクト
     */
    public static void close(Closeable closeable) {
        if (closeable == null) {
            return;
        }

        try {
            closeable.close();
        } catch (IOException e) {
            // ignore
        }
    }
}
