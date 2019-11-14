package com.kq.platform.kq_common.ui.widget.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.TypedValue;
import android.widget.TextView;
import android.widget.Toast;

import com.kq.platform.kq_common.utils.SizeUtils;

import kq.platform.kq_common.R;


/**
 * Created by Zhou jiaqi on 2017/12/20.
 */

public class BaseToast extends Toast {

    public TextView textView;

    public int TB = 0;
    public int LR = 0;

    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    @TargetApi(21)
    public BaseToast(Context context, String mes) {
        super(context);
        TB = SizeUtils.dp2px(context, 8);
        LR = SizeUtils.dp2px(context, 16);
        textView = new TextView(context);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setText(mes);
        textView.setTextColor(context.getResources().getColor(R.color.black));
        textView.setPadding(LR, TB, LR, TB);
        textView.setBackground(context.getResources().getDrawable(R.drawable.bg_base_toast));
        setView(textView);
    }


    public static BaseToast makeToast(Context context, String mes, int duration) {
        BaseToast baseToast = new BaseToast(context, mes);
        baseToast.setDuration(duration);
        return baseToast;
    }
}
