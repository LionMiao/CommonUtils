package com.ehang.commonutils.ui.mrecyclerview;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * RecycleView + DataBinding的基础adapter。
 *
 * @author Guoshaohong
 * @date 2018/04/03 12:03
 */
public class BaseRecycleAdapter<T> extends RecyclerView.Adapter<BaseRecycleViewHolder> {
    /**
     * 数据源。
     */
    private List<T> mData;

    /**
     * 布局id。
     */
    private int layoutId;

    /**
     * data binding id。
     */
    private int brId;

    private OnItemClickListener<T> onItemClickListener;

    /**
     * @param mData    数据源。
     * @param layoutId item的布局id。
     * @param brId     对应的BR。
     */
    public BaseRecycleAdapter(@NonNull List<T> mData, int layoutId, int brId) {
        this.mData = mData;
        this.layoutId = layoutId;
        this.brId = brId;
    }

    /**
     * 注册一个点击的监听器。当列表中的选项被点击或者长按的时候，此监听器会被调用。
     *
     * @param listener 监听器。
     */
    public void setItemClickListener(OnItemClickListener<T> listener) {
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
                onItemClickListener.onItemClick(holder.getBinding().getRoot(), mData.get(position));
            }
        });
        holder.getBinding().getRoot().setOnLongClickListener(v -> {
            if (null != onItemClickListener) {
                onItemClickListener.onLongClick(holder.getBinding().getRoot(), mData.get(position));
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    /**
     * 传入一个新的数据列表，更新RecycleView中的数据。
     *
     * @param data 数据列表。
     */
    public void updateData(List<T> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    /**
     * 对选项的点击监听器。包括了点击事件和长按事件。
     *
     * @param <T> 和adapter相同的数据类型。
     */
    public interface OnItemClickListener<T> {
        /**
         * 当选项被点击时，调用此方法。
         *
         * @param view 被点击的view。
         * @param data 被点击的view所装载的数据。
         */
        void onItemClick(View view, T data);


        /**
         * 当选项被长按时，调用此方法。
         *
         * @param view 被长按的view。
         * @param data 被长按的view所装载的数据。
         */
        void onLongClick(View view, T data);
    }
}

