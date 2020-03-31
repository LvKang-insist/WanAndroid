package com.lv.core.recycler;

import java.util.LinkedHashMap;

/**
 * @author 345 QQ:1831712732
 * @name CarSteward
 * @class name：com.car.core.ui.recycler
 * @time 2019/11/2 20:30
 * @description
 */
public class MultipleItemEntityBuilder {
    private static final LinkedHashMap<Object,Object> FIELDS = new LinkedHashMap<>();

    MultipleItemEntityBuilder(){
        // 因为这个map是静态的，所以每次使用时先清除之前的数据
        FIELDS.clear();
    }

    public final MultipleItemEntityBuilder setItemType(int itemType){
        FIELDS.put(MultipleFields.ITEM_TYPE,itemType);
        return this;
    }

    public final MultipleItemEntityBuilder setField(Object key,Object value){
        FIELDS.put(key,value);
        return this;
    }

    public final MultipleItemEntityBuilder setField(LinkedHashMap<?,?> map){
        FIELDS.putAll(map);
        return this;
    }

    public final MultipleItemEntity build(){
        return   new MultipleItemEntity(FIELDS);
    }

}
