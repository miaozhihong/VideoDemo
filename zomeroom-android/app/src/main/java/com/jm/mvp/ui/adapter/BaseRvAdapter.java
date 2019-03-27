package com.jm.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jm.utils.CommonUtils;

import java.util.List;

/**
 * @author pc 张立男
 * @Description BaseRvAdapter 简单的封装RecyclerView.Adapter
 * @date 2018/3/27 13:57
 * o(＞﹏＜)o
 */
public abstract class BaseRvAdapter<T, K extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<K> {
    /**
     * 需要子类实现 获取holder
     *
     * @param view 当前的布局view
     * @return holder
     */
    protected abstract K getHolder(View view);

    /**
     * 需要子类实现 获取布局id
     *
     * @return 布局id
     */
    protected abstract int getResId();

    /**
     * 需要子类实现 处理展示逻辑
     *
     * @param holder   当前holder
     * @param t        list中的bean类
     * @param position 当前的item的position
     */
    protected abstract void convert(K holder, T t, int position);

    private List<T> mList;
    protected Context mContext;

    public List<T> getList() {
        return mList;
    }

    public Context getContext() {
        return mContext;
    }

    public BaseRvAdapter(List<T> list, Context context) {
        mList = CommonUtils.checkNotNull(list, "传入adapter中的数据集合为空！");
        mContext = context;
    }

    @Override
    public K onCreateViewHolder(ViewGroup parent, int viewType) {
        return getHolder(LayoutInflater.from(mContext).inflate(getResId(), parent, false));
    }

    @Override
    public void onBindViewHolder(K holder, final int position) {
        convert(holder, mList.get(position), position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.itemClick(position);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mLongListener != null) {
                    mLongListener.itemLongClick(position);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    protected int mSelect = -1;

    /**
     * 选中的更新列表
     *
     * @param position 选中的值
     */
    public void selectPosition(int position) {
        mSelect = position;
        notifyDataSetChanged();
    }

    /**
     * 列表的更新方法
     *
     * @param list 更新的数据集合
     */
    public void replace(List<T> list) {
        mList = CommonUtils.checkNotNull(list);
        notifyDataSetChanged();
    }

    private OnItemClickListener mListener;

    public void setLongListener(OnItemLongClickListener longListener) {
        mLongListener = longListener;
    }

    private OnItemLongClickListener mLongListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        /**
         * 点击item的回调
         *
         * @param position item的位置
         */
        void itemClick(int position);
    }

    public interface OnItemLongClickListener {
        /**
         * 长按item的回调
         *
         * @param position item的位置
         */
        void itemLongClick(int position);
    }
}