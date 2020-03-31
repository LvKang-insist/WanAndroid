package com.lv.wanandroid.module.system.adapter

import com.lv.core.recycler.DataConverter
import com.lv.core.recycler.MultipleFields
import com.lv.core.recycler.MultipleItemEntity
import com.lv.wanandroid.module.system.bean.TreeBean
import java.util.*

class RvConverter(val children: List<TreeBean.Data.Children>) : DataConverter() {
    override fun convert(): ArrayList<MultipleItemEntity> {

        val img = MultipleItemEntity.builder()
            .setItemType(SystemItemType.ITEM_TYPE_RIGHT_IMG)
            .setField(MultipleFields.IMAGEi_URL, "")
            .build()
        ENTITLES.add(img)

        val entity = MultipleItemEntity.builder()
            .setItemType(SystemItemType.ITEM_TYPE_RIGHT_CONTENT)
            .setField(MultipleFields.LIST, children)
            .setField(MultipleFields.TEXT, "Wan Android")
            .build()
        ENTITLES.add(entity)

        val str = arrayOf("哈哈哈", "嘻嘻嘻", "吼吼吼", "哼哼哼", "哦哦哦")
        for (i in 0..2) {
            val en = MultipleItemEntity.builder()
                .setItemType(SystemItemType.ITEM_TYPE_RIGHT_CONTENT)
                .setField(MultipleFields.LIST, children)
                .setField(MultipleFields.TEXT, str[Random().nextInt(5)])
                .build()
            ENTITLES.add(en)
        }
        return ENTITLES
    }

}