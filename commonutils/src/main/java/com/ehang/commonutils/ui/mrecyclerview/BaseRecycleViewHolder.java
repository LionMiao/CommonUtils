package com.ehang.commonutils.ui.mrecyclerview;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 用于DataBinding的RecycleView ViewHolder。
 *
 * @author Guoshaohong
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