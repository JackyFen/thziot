package com.kq.platform.kq_common.ui.widget;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by sjl on 2018/5/2.
 */

public class FloatEditTextTextWatcher implements TextWatcher {

    private int beforeDot;
    private int afterDot;
    private AfterTextChangedListener afterTextChangedListener;


    /**
     * @param beforeDot 小数点前面可以输入的位数  -1表示无限制
     * @param afterDot  小数点后可以输入的位数   -1表示无限制
     */
    public FloatEditTextTextWatcher(int beforeDot, int afterDot) {
        if (beforeDot <= 0) {
            beforeDot = -1;
        }
        if (afterDot <= 0) {
            afterDot = -1;
        }
        this.beforeDot = beforeDot;
        this.afterDot = afterDot;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        getNumber(editable);
        if (afterTextChangedListener != null) {
            afterTextChangedListener.onChanged(editable);
        }
    }

    private void getNumber(Editable editable) {
        String temp = editable.toString();
        int dotNum = temp.indexOf(".");
        //第一个输入".",要在前面加0
        if (dotNum == 0) {
            editable.insert(0, "0");
            return;
        }
        //输入00的处理
        if (temp.equals("00")) {
            editable.delete(0, 1);
            return;
        }
        //以0开头的处理
        if (temp.startsWith("0") && temp.length() - temp.length() > 1 && (dotNum == -1 || dotNum > 1)) {
            editable.delete(0, 1);
            return;
        }
        //整数部分的处理
        if (beforeDot != -1) {
            if (dotNum == -1 && temp.length() > beforeDot) {
                editable.delete(beforeDot, beforeDot + 1);
            } else if (dotNum != -1 && dotNum > beforeDot) {
                editable.delete(beforeDot, beforeDot + 1);
            }
        }
        if(dotNum == -1){
            return;
        }
        //小数部分的整理
        if (dotNum != -1 && ((temp.length() - dotNum - 1) > afterDot)) {
            editable.delete(dotNum + afterDot + 1, dotNum + afterDot + 2);
            return;
        }
    }

    public void setAfterTextChangedListener(AfterTextChangedListener listener) {
        this.afterTextChangedListener = listener;
    }

    public interface AfterTextChangedListener {
        void onChanged(Editable editable);
    }
}
