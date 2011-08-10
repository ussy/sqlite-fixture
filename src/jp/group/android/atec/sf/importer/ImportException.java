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

/**
 * インポートエラー時に発生する例外
 * 
 * @author ussy
 */
public class ImportException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * @param message
     *            例外メッセージ
     */
    public ImportException(String message) {
        super(message);
    }

    /**
     * @param cause
     *            例外原因
     */
    public ImportException(Exception cause) {
        super(cause);
    }
}
