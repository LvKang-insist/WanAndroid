package com.lv.core.recycler;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * @author 345 QQ:1831712732
 * @name CarSteward
 * @class name：com.car.core.ui.recycler
 * @time 2019/11/2 20:34
 * @description 自定义 holder
 */
public class MultipleViewHolder extends BaseViewHolder {

    private MultipleViewHolder(View view) {
        super(view);

    }

    public static MultipleViewHolder create(View view) {
        return new MultipleViewHolder(view);
    }
}