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

/**
 * データ変換時に例外が発生したことを通知するクラスです。
 * 
 * @author ussy
 */
public class ConvertException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String value;

    /**
     * 例外が発生した値を取得します。
     * 
     * @return 例外値
     */
    public String getValue() {
        return value;
    }

    /**
     * @param throwable
     *            例外原因
     * @param value
     *            例外値
     */
    public ConvertException(Throwable throwable, String value) {
        super(throwable);

        this.value = value;
    }
}
