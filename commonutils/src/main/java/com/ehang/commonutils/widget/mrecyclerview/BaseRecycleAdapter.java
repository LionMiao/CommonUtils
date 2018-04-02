package com.ehang.commonutils.widget.mrecyclerview;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.List;

/**
 * RecycleView + DataBinding的基础adapter
 * Created by KwokSiuWang on 2017/9/24.
 */
public class BaseRecycleAdapter<T> extends RecyclerView.Adapter<BaseRecycleViewHolder> {
    /**
     * 数据源
     */
    private List<T> mData;

    /**
     * 布局id
     */
    private int layoutId;

    /**
     * data binding id
     */
    private int brId;

    private OnItemClickListener onItemClickListener;

    /**
     * @param mData 数据源
     * @param layoutId item的布局id
     * @param brId 对应的BR
     */
    public BaseRecycleAdapter(@NonNull List<T> mData, int layoutId, int brId) {
        this.mData = mData;
        this.layoutId = layoutId;
        this.brId = brId;
    }

    public void setItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    @NonNull
    @Override
    public BaseRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, layoutId, parent, false);
        BaseRecycleViewHolder baseViewHolder = new BaseRecycleViewHolder(binding.getRoot());
        baseViewHolder.setBinding(binding);
        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecycleViewHolder holder, int position) {
        holder.getBinding().setVariable(brId, mData.get(position));
        holder.getBinding().executePendingBindings();
        holder.getBinding().getRoot().setOnClickListener(v -> {
            if (null != onItemClickListener) {
                onItemClickListener.onItemClick(holder.getBinding().getRoot(), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void updateData(List<T> data) {
        this.mData = data;
        notifyDataSetChanged();
    }
}