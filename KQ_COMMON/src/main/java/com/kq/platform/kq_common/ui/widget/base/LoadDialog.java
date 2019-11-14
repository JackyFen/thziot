package com.kq.platform.kq_common.ui.widget.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import kq.platform.kq_common.R;


/**
 * Created by Zhou jiaqi on 2017/12/20.
 */

public class LoadDialog extends Dialog {


    public TextView tvShowTip;

    public LoadDialog(@NonNull Context context) {
        super(context, R.style.DialogStyle);

        setContentView(R.layout.dialog_base_load);
        initView();
        setWindowAttr();

        setCanceledOnTouchOutside(false);
        //setCancelable(false);
        setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Log.i("LoadDialog", "onCancel: ");
            }
        });

    }

    public void setTextTip(String tip){
        tvShowTip.setText(tip);
    }

    public void initView(){
        tvShowTip =findViewById(R.id.tv_load_tip);
    }

    public void setWindowAttr(){
        Window window=getWindow();
        WindowManager.LayoutParams wlp=window.getAttributes();
        wlp.width= WindowManager.LayoutParams.WRAP_CONTENT;
        wlp.gravity= Gravity.CENTER;
        window.setAttributes(wlp);
    }
}
