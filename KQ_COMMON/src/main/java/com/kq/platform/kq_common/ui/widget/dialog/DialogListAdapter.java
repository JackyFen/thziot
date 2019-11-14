package com.kq.platform.kq_common.ui.widget.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.List;

import kq.platform.kq_common.R;

/**
 * Created by zhoulongmi on 2017/10/22 : 13:18.
 * TODO: Write something
 */

public class DialogListAdapter extends BaseAdapter {

    public List<ListShowBean> listShowBeen;
    private Context context;

    public DialogListAdapter(List<ListShowBean> listShowBeen, Context context) {
        this.context = context;
        this.listShowBeen = listShowBeen;

    }

    public void notifyData(List<ListShowBean> listShowBeen) {
        this.listShowBeen = listShowBeen;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listShowBeen.size();
    }

    @Override
    public Object getItem(int i) {
        return listShowBeen.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_list, null);
            holder = new ViewHolder();
            holder.textView = view.findViewById(R.id.textView);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ListShowBean showBean = listShowBeen.get(i);
        holder.textView.setText(showBean.name);
        return view;
    }

    static class ViewHolder {
        TextView textView;
    }
}
