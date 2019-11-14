package com.kq.platform.kq_common.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.kq.platform.kq_common.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import kq.platform.kq_common.R;

/**
 * Created by zhoulongmi on 2017/10/22 : 10:56.
 * TODO: 车辆类型
 */

public abstract class SelectBaseDialog<T> extends Dialog {

    private ListView listView;
    private TextView cancel;
    private DialogListAdapter dialogListAdapter;
    protected List<ListShowBean> listShowBeen;
    protected List<T> mList;
    protected EditText search;
    protected LinearLayout llSearch;

    List<ListShowBean> listShowBeenSearch = new ArrayList<>();
    Context mContext;
    OnClickBaseChoiceListener listener;
    boolean isSearch = false;

    public SelectBaseDialog(@NonNull Context context) {
        super(context, R.style.DialogStyle);
        mContext = context;
        setContentView(R.layout.dialog_base_choice);
        initView();
        setAttributes();
        setCanceledOnTouchOutside(true);
    }


    public void initView() {
        search = get(R.id.et_search);
        listView = get(R.id.dialog_base_choice_list);
        cancel = get(R.id.dialog_base_choice_btn_cancel);
        llSearch = get(R.id.layout_search);

        search = get(R.id.et_search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String input = editable.toString();
                if (input.equals("")) {
                    //还原
                    isSearch = false;
                    initViewData();
                } else {
                    isSearch = true;
                    dialogListAdapter.notifyData(searchData(listShowBeen, input));
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (listener != null) {
                    if (isSearch) {
                        ListShowBean showBean = (ListShowBean) adapterView.getAdapter().getItem(i);
                        int index = getShowBeanPosition(showBean);
                        listener.onItemClick(view, mList.get(index));
                    } else {
                        listener.onItemClick(view, mList.get(i));
                    }
                }

                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)
                    listener.onCancel(view);

                dismiss();
            }
        });
    }

    public int getShowBeanPosition(ListShowBean showBean) {
        int index = listShowBeen.size();
        for (int i = 0; i < index; i++) {
            ListShowBean showBean1 = listShowBeen.get(i);
            if (showBean.id.equals(showBean1.id)) {
                return i;
            }
        }
        return 0;
    }

    public List<ListShowBean> searchData(List<ListShowBean> listShowBeen, String deriction) {
        listShowBeenSearch.clear();
        for (ListShowBean bean : listShowBeen) {
            if (bean.name.contains(deriction)) {
                listShowBeenSearch.add(bean);
            }
        }
        return listShowBeenSearch;

    }

    public void setListener(OnClickBaseChoiceListener listener) {
        this.listener = listener;
    }

    public void initViewData() {
        dialogListAdapter = new DialogListAdapter(convert(mList), mContext);
        listView.setAdapter(dialogListAdapter);
    }

    public void setSearchVisibility(boolean b) {
        llSearch.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    public void setCancelText(CharSequence cancelText) {
        cancel.setText(cancelText);
    }

    private void setAttributes() {
        Window window = getWindow();
        WindowManager.LayoutParams wp = window.getAttributes();
        wp.width = (int) (ScreenUtils.getScreenWidth(mContext) * 0.9);
        wp.height = (int) (ScreenUtils.getScreenHeight(mContext) * 0.7);
        wp.gravity = Gravity.CENTER;
        window.setAttributes(wp);
    }

    public interface OnClickBaseChoiceListener<T> {
        void onItemClick(View v, T obj);

        void onCancel(View v);
    }


    private <T extends View> T get(int id) {
        return findViewById(id);
    }

    abstract public List<ListShowBean> convert(List<T> list);

    abstract public List<T> setData(List<T> listData);
}
