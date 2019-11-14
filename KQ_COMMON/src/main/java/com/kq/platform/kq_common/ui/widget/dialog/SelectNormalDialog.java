package com.kq.platform.kq_common.ui.widget.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoulongmi on 2017/10/23 : 10:33.
 * TODO: 车辆类型.....等,字典通用，弹窗
 */

public class SelectNormalDialog extends SelectBaseDialog<String> {

    public SelectNormalDialog(@NonNull Context context, String hint) {
        super(context);
        search.setHint("" + hint);
    }

    @Override
    public List<ListShowBean> convert(List<String> list) {
        List<ListShowBean> listShowBeenC = new ArrayList<>();
        int id = 0;
        for (String str : list) {
            listShowBeenC.add(new ListShowBean(String.valueOf(id++), str));
        }

        listShowBeen = listShowBeenC;
        return listShowBeen;
    }

    @Override
    public List<String> setData(List<String> listData) {
        mList = listData;
        initViewData();
        return mList;
    }

    public void setSearchHint(String hint){
        search.setHint(hint);
    }

    public void hideSearchView(){
        llSearch.setVisibility(View.GONE);
    }
}
