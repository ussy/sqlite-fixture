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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 行データを保持するクラスです。
 * 
 * @author ussy
 */
public class Row implements Iterable<Item> {

    private int index;

    private List<Item> items = new ArrayList<Item>();

    /**
     * @param index
     *            行データインデックス。 1 オリジン。
     */
    public Row(int index) {
        this.index = index;
    }

    /**
     * 行データにおけるインデックスを返します。
     * 
     * <p>
     * 行データを表すため、 1 オリジンであることに注意してください。
     * </p>
     * 
     * @return インデックス
     */
    public int getIndex() {
        return index;
    }

    /**
     * 行データの項目数を返します。
     * 
     * @return 行データの項目数
     */
    public int getCount() {
        return items.size();
    }

    /**
     * 指定したインデックスの項目情報を返します。
     * 
     * @param index
     *            インデックス。 0 オリジン。
     * @return 項目情報
     * @exception IndexOutOfBoundsException
     *                保持している項目以外のインデックスを指定した場合
     */
    public Item getItem(int index) {
        if (index < 0 || index > items.size() - 1) {
            throw new IndexOutOfBoundsException(String.format("items: %d, index; %d", items.size(), index));
        }

        return items.get(index);
    }

    /**
     * 指定したインデックスのカラム情報を取得します。
     * 
     * @param index
     *            インデックス
     * @return カラム情報
     */
    public ColumnInfo getColumn(int index) {
        Item item = getItem(index);
        return item.getColumn();
    }

    /**
     * 指定したインデックスの項目に含まれる値を取得します。
     * 
     * @param index
     *            インデックス
     * @return 項目に含まれる値
     */
    public String getValue(int index) {
        Item item = getItem(index);
        return item.getValue();
    }

    /**
     * 項目を追加します。
     * 
     * @param column
     *            カラム情報
     * @param value
     *            項目値
     */
    public void add(ColumnInfo column, String value) {
        this.items.add(new Item(column, value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<Item> iterator() {
        return items.iterator();
    }
}
