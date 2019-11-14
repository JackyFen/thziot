package com.hnhz.thziot.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hnhz.thziot.R;
import com.hnhz.thziot.bean.ListPopDownBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhou jiaqi on 2019/8/6.
 */
public class ListPopDownAdapter extends BaseAdapter {

    public int mBackgroundColor;
    private Context mContext;
    private List<ListPopDownBean> mOptions = new ArrayList();
    private int mSelectedIndex = -1;
    public int mTextColor;
    public int mTextSize;

    public ListPopDownAdapter(Context context) {
        this.mContext = context;
        this.mBackgroundColor = ContextCompat.getColor(context, R.color.white);
        this.mTextSize = (int) TypedValue.applyDimension(2, 16.0f, context.getResources().getDisplayMetrics());
        this.mTextColor = ContextCompat.getColor(context, R.color.gray_3);
    }

    public void setSelectedByIndex(int i) {
        int count = getCount();
        if (i < 0 || i >= count) {
            this.mSelectedIndex = -1;
        }
        this.mSelectedIndex = i;
        notifyDataSetChanged();
    }

    public List<ListPopDownBean> getData() {
        return this.mOptions;
    }

    public void setData(List<ListPopDownBean> list) {
        this.mOptions.clear();
        this.mOptions.addAll(list);
        notifyDataSetChanged();
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ListPopDownViewHolder holder;
        int dividerIsVisible;
        if (view == null) {
            holder = new ListPopDownViewHolder();
            view = LayoutInflater.from(this.mContext).inflate(R.layout.listitem_dropdown, viewGroup, false);
            holder.tvMain = view.findViewById(R.id.tv_main);
            holder.divider = view.findViewById(R.id.divider);
            holder.ivCheck = view.findViewById(R.id.iv_check);
            holder.tvSub = view.findViewById(R.id.tv_sub);
            holder.tvMain.setTextSize(0, (float) this.mTextSize);
            view.setBackgroundColor(this.mBackgroundColor);
            view.setTag(holder);
        } else {
            holder = (ListPopDownViewHolder) view.getTag();
        }
        ListPopDownBean bean = this.mOptions.get(i);
        holder.tvMain.setText(bean.display);
        if (this.mSelectedIndex == i) {
            holder.ivCheck.setVisibility(View.VISIBLE);
            holder.tvMain.setTextColor(this.mContext.getResources().getColor(R.color.base_color));
        } else {
            holder.ivCheck.setVisibility(View.GONE);
            holder.tvMain.setTextColor(this.mTextColor);
        }
        if (TextUtils.isEmpty(bean.subTitle)) {
            holder.tvSub.setVisibility(View.GONE);
        } else {
            holder.tvSub.setVisibility(View.VISIBLE);
            holder.tvSub.setText(bean.subTitle);
        }
        View view2 = holder.divider;
        if (i == getCount() - 1) {
            dividerIsVisible = View.GONE;
        } else {
            dividerIsVisible = View.VISIBLE;
        }
        view2.setVisibility(dividerIsVisible);
        return view;
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public ListPopDownBean getItem(int i) {
        return this.mOptions.get(i);
    }

    public int getCount() {
        return this.mOptions.size();
    }

    public class ListPopDownViewHolder{
        TextView tvMain;
        TextView tvSub;
        ImageView ivCheck;
        View divider;
    }

}
