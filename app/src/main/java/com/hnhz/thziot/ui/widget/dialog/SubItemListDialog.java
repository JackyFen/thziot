package com.hnhz.thziot.ui.widget.dialog;

import android.content.Context;

import com.hnhz.thziot.bean.ListPopDownBean;
import com.hnhz.thziot.bean.SubItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhou jiaqi on 2019/8/7.
 */
public class SubItemListDialog extends ListPopDownDialog<SubItemBean> {

    public SubItemListDialog(Context context) {
        super(context);
    }

    @Override
    public List<ListPopDownBean> convert(List<SubItemBean> list) {
        List<ListPopDownBean> listPopDown = new ArrayList<>();
        for(SubItemBean subItemBean: list){
            ListPopDownBean listPopDownBean = new ListPopDownBean();
            listPopDownBean.display = subItemBean.getSubitemsName();
            listPopDown.add(listPopDownBean);
        }
        return listPopDown;
    }

}
