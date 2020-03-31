package com.lv.core.recycler;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * @author 345 QQ:1831712732
 * @name CarSteward
 * @class name：com.car.core.ui.recycler
 * @time 2019/11/2 20:33
 * @description
 */
public class MultipleRecyclerAdapter extends BaseMultiItemQuickAdapter<MultipleItemEntity, MultipleViewHolder>
        implements BaseQuickAdapter.SpanSizeLookup {

    public MultipleRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        //初始化布局
        init();
    }

    public static MultipleRecyclerAdapter create(List<MultipleItemEntity> data) {
        return new MultipleRecyclerAdapter(data);
    }

    public static MultipleRecyclerAdapter create(DataConverter converter) {
        return new MultipleRecyclerAdapter(converter.convert());
    }

    private void init() {

        //设置 宽度监听
        setSpanSizeLookup(this);
        //关闭动画
        closeLoadAnimation();
        //多次执行动画
        isFirstOnly(false);
    }

    /**
     * 如果你想在适配器中使用BaseViewHolder的子类，您必须重写该方法来创建新的ViewHolder。
     */
    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }

    /**
     * 现此方法，并使用helper将视图调整为给定项
     */
    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity entity) {

    }

    /**
     * 设置宽度
     */
    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getField(MultipleFields.SPAN_SIZE);
    }

}
