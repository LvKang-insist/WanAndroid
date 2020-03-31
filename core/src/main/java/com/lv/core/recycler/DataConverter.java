package com.lv.core.recycler;

import java.util.ArrayList;

/**
 * @author 345 QQ:1831712732
 * @name CarSteward
 * @class name：com.car.core.ui.recycler
 * @time 2019/11/2 20:27
 * @description
 */
public abstract class DataConverter {

    /**
     * convert 方法解析完数据后 会将结果存进这个集合中
     */
    protected final ArrayList<MultipleItemEntity> ENTITLES = new ArrayList<>();
    private String mJsonData = null;

    /**
     * @return size
     */
    public int size() {
        return ENTITLES.size();
    }

    /**
     * 清空
     */
    public void clear() {
        ENTITLES.clear();
    }

    /**
     * 解析数据
     */
    public abstract ArrayList<MultipleItemEntity> convert();

    public DataConverter setJsonData(String json) {
        this.mJsonData = json;
        return this;
    }

    protected String getJsonData() {
        if (mJsonData == null || mJsonData.isEmpty()) {
            throw new NullPointerException("DATA IS NULL");
        }
        return mJsonData;
    }
}
