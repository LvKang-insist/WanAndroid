package com.lv.core.recycler;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

/**
 * @author 345 QQ:1831712732
 * @name CarSteward
 * @class name：com.car.core.ui.recycler
 * @time 2019/11/2 20:27
 * @description
 */
public class MultipleItemEntity implements MultiItemEntity {

    private final LinkedHashMap<Object, Object> MULTIPLE_FIELDS = new LinkedHashMap<>();
    private final ReferenceQueue<LinkedHashMap<Object, Object>> ITEM_QUENE = new ReferenceQueue<>();
    /**
     * 软引用：SoftReference
     *       如果内存空间足够，垃圾回收器绝不会回收他，如果内存不足，则会回收这些对象的内存。
     */
    private final SoftReference<LinkedHashMap<Object, Object>> FIELDS_RETERENCE
            = new SoftReference<>(MULTIPLE_FIELDS, ITEM_QUENE);

    MultipleItemEntity(LinkedHashMap<Object,Object> fields) {
        FIELDS_RETERENCE.get().putAll(fields);
    }

    /**
     * 使用 建造者来构建数据
     */
    public static MultipleItemEntityBuilder builder(){
        return new MultipleItemEntityBuilder();
    }

    /**
     * 控制RecyclerView 中每一个item的样式和他的表现特征,强制实现
     * 这个type 用于区分当前数据模型需要展示在哪一种 item 之上
     *
     *   之后使用addItemType(int type ,int layoutResld)方法中输入对应的参数，
     *   你有几个布局，就写几个addItemType ，如果你的List中有10中不同的数据类型，
     *   但是你只写了8种，一定会报错的。
     *
     */
    @Override
    public int getItemType() {
        //返回每条数据的类型  ITEM_TYPE 为键
        return (int) FIELDS_RETERENCE.get().get(MultipleFields.ITEM_TYPE);
    }

    /**
     * @param key 要查询的键
     * @param <T> 返回类型
     * @return 返回根据键查询到的结果
     */
    public final <T> T getField(Object key){
        return (T)FIELDS_RETERENCE.get().get(key);
    }

    /**
     * @return 返回数据集合
     */
    public final LinkedHashMap<?,?> getFields(){
        return FIELDS_RETERENCE.get();
    }

    /**
     * 添加 数据
     * @param key 键
     * @param value 值
     * @return 返回当前对象
     */
    public final MultipleItemEntity setField(Object key, Object value){
        FIELDS_RETERENCE.get().put(key,value);
        return this;
    }
}
