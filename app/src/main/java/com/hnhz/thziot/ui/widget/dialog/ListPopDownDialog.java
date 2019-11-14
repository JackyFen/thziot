package com.hnhz.thziot.ui.widget.dialog;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.hnhz.thziot.R;
import com.hnhz.thziot.bean.ListPopDownBean;
import com.hnhz.thziot.ui.adapter.ListPopDownAdapter;
import com.kq.platform.kq_common.ui.widget.dialog.ListShowBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Zhou jiaqi on 2019/8/6.
 */
public abstract class ListPopDownDialog<T> extends PopDownDialog implements AdapterView.OnItemClickListener {

    protected ListPopDownAdapter mAdapter;
    protected ListView mListView;
    protected List<T> mList;
    protected setOnDropdownItemSelected<T> mOnDropdownItemSelectedListener;

    public ListPopDownDialog(Context context) {
        super(context);
        setListView(context);
    }

    private void setListView(Context context) {
        this.mListView = new ListView(context);
        this.mAdapter = new ListPopDownAdapter(context);
        this.mListView.setAdapter(this.mAdapter);
        this.mListView.setSelector(R.color.transparent);
        this.mListView.setDivider(null);
        this.mListView.setDividerHeight(0);
        this.mListView.setOnItemClickListener(this);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        View view = new View(context);
        view.setBackgroundResource(R.color.color_dddddd);
        linearLayout.addView(view, -1, 1);
        linearLayout.addView(this.mListView);setContentView(linearLayout, new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT));
    }

    public void setDisplayOptions(T[] tArr) {
        setDisplayOptions(Arrays.asList(tArr));
    }

    public void setDisplayOptions(List<T> list) {
        if (list == null) {
            list = new ArrayList<>(0);
        }
        this.mList = list;
        this.mAdapter.setData(convert(list));
        this.mAdapter.setSelectedByIndex(-1);
    }

    public void setSelectedOption(int i) {
        this.mAdapter.setSelectedByIndex(i);
    }

    public void setAdapter(ListPopDownAdapter adapter) {
        this.mAdapter = adapter;
        this.mListView.setAdapter(adapter);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        setSelectedOption(i);
        if (this.mOnDropdownItemSelectedListener != null) {
            this.mOnDropdownItemSelectedListener.onDropdownItemSelected(i, mList.get(i));
        }
        dismiss();
    }

    public void setOnDropdownItemSelectedListener(setOnDropdownItemSelected<T> znd) {
        this.mOnDropdownItemSelectedListener = znd;
    }

    public void setTextSize(int i) {
        this.mAdapter.mTextSize = i;
    }

    public void setTextColor(int i) {
        this.mAdapter.mTextColor = i;
    }

    public void setBackgroundColor(int i) {
        this.mAdapter.mBackgroundColor = i;
    }

    public interface setOnDropdownItemSelected<T> {
        void onDropdownItemSelected(int position, T t);
    }

    abstract public List<ListPopDownBean> convert(List<T> list);

}
