package com.ehang.commonutils.widget.mrecyclerview;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 通用的viewHolder
 * Created by KwokSiuWang on 2017/9/24.
 */

class BaseRecycleViewHolder extends RecyclerView.ViewHolder {
    private ViewDataBinding binding;

    BaseRecycleViewHolder(View itemView) {
        super(itemView);
    }

    ViewDataBinding getBinding() {
        return binding;
    }

    void setBinding(ViewDataBinding binding) {
        this.binding = binding;
    }
}