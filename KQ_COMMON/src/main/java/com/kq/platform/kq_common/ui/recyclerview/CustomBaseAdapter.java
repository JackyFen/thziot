package com.kq.platform.kq_common.ui.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Zhou jiaqi on 2018/8/6.
 */

public abstract class CustomBaseAdapter<VH extends BaseViewHolder,D extends Object> extends RecyclerView.Adapter<VH>
        implements BaseViewHolder.OnItemClickListener, BaseViewHolder.OnItemLongClickListener  {

    //item点击回调
    private BaseViewHolder.OnItemClickListener mOnItemClickListener;

    //item长按回调
    private BaseViewHolder.OnItemLongClickListener mOnItemLongClickListener;

    protected ArrayList<D> data;

    protected Context context;

    public CustomBaseAdapter(Context context){
        this.context = context;
    }

    public CustomBaseAdapter(Context context, ArrayList<D> data){
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(getLayoutId(viewType), parent, false);
        VH viewHolder = getViewHolder(itemView, viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        if(mOnItemClickListener != null){
            holder.setOnItemClickListener(this);
        }

        if(mOnItemLongClickListener != null){
            holder.setOnItemLongClickListener(this);
        }
    }

    @Override
    public int getItemCount() {
        return data == null? 0: data.size();
    }

    @Override
    public void onItemClick(int position, @NonNull View view) {
        mOnItemClickListener.onItemClick(position, view);
    }

    @Override
    public boolean onItemLongClick(int position, @NonNull View view) {
        return mOnItemLongClickListener.onItemLongClick(position, view);
    }

    public void setData(ArrayList<D> data){
        this.data = data;
    }

    public void refreshData(ArrayList<D> data){
        this.data = data;
        notifyDataSetChanged();
    }

    /**
     * 获取ViewHolder
     * @param itemView
     * @param viewType
     * @return
     */
    protected abstract VH getViewHolder(View itemView, int viewType);

    /**
     * 获取item布局文件id
     * @param viewType 类别
     * @return
     */
    protected abstract int getLayoutId(int viewType);

    /**
     * 设置item点击事件
     * @param onItemClickListener
     */
    public void setOnItemClickListener(BaseViewHolder.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    /**
     * 设置item长按事件
     * @param onItemLongClickListener
     */
    public void setOnItemLongClickListener(BaseViewHolder.OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }
}
