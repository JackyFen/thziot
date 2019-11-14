package com.kq.platform.kq_common.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import kq.platform.kq_common.R;
import com.kq.platform.kq_common.utils.ScreenUtils;

/**
 * Created by Zhou jiaqi on 2018/4/9.
 */

public class TwoBtnDialog extends Dialog {

    private TextView info;
    private TextView btnLeft;
    private TextView btnRight;
    private Context context;
    private OnDialogClickListener listener;

    public TwoBtnDialog(@NonNull Context context) {
        super(context, R.style.DialogStyle);
        this.context = context;
        setContentView(R.layout.dialog_layout_two_btn);
        initView();
        setAttributes();
        setCanceledOnTouchOutside(true);
    }


    public void setListener(OnDialogClickListener listener) {
        this.listener = listener;
    }


    private void initView() {
        info = get(R.id.dialog_toBtn_tv_info);
        btnLeft = get(R.id.dialog_toBtn_btnLeft);
        btnRight = get(R.id.dialog_toBtn_btnRight);

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClickLeft(view);
                }
                dismiss();
            }
        });
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClickRight(view);
                }
                dismiss();
            }
        });
    }

    public void setTitle(CharSequence title) {
        this.info.setText(title);
    }

    public void setBtnLeftText(String leftText) {
        this.btnLeft.setText(leftText + "");
    }

    public void setBtnRightText(CharSequence rightText) {
        this.btnRight.setText(rightText + "");
    }



    private <T extends View> T get(int id) {
        return findViewById(id);
    }


    private void setAttributes() {
        Window window = getWindow();
        WindowManager.LayoutParams wp = window.getAttributes();
        wp.width = (int) (ScreenUtils.getScreenWidth(context) * 0.8);
        wp.gravity = Gravity.CENTER;
        window.setAttributes(wp);
    }


    public static class Builder {
        private CharSequence info;
        private String btnLeftText;
        private CharSequence btnRightText;
        private OnDialogClickListener listener;
        private Context context;
        private  boolean isCanOut;
        private  boolean isCancelable;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(CharSequence title) {
            info = title;
            return this;
        }

        public Builder setBtnLeftText(String leftText) {
            btnLeftText = leftText;
            return this;
        }

        public Builder setBtnRightText(CharSequence rightText) {
            btnRightText = rightText;
            return this;
        }

        public Builder setListener(OnDialogClickListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean flag){
            this.isCanOut=flag;
            return this;
        }

        public Builder setCancelable(boolean flag){
            this.isCancelable=flag;
            return this;
        }


        public TwoBtnDialog build() {
            TwoBtnDialog twoBtnDialog = new TwoBtnDialog(this.context);
            twoBtnDialog.setTitle(this.info);
            twoBtnDialog.setBtnLeftText(this.btnLeftText);
            twoBtnDialog.setBtnRightText(btnRightText);
            twoBtnDialog.setListener(this.listener);
            twoBtnDialog.setCanceledOnTouchOutside(isCanOut);
            twoBtnDialog.setCancelable(isCancelable);
            return twoBtnDialog;
        }
    }

    public interface OnDialogClickListener {
        void onClickLeft(View v);

        void onClickRight(View v);
    }

}
