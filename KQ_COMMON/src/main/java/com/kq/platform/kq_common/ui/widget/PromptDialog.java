package com.kq.platform.kq_common.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;


import kq.platform.kq_common.R;

/**
 * Created by sjl on 2018/3/28.
 */

public class PromptDialog extends Dialog {
    TextView tvPrompt;
    TextView tvClick;
    TextView tvTitle;
    private OnClickListener onClickListener;

    public PromptDialog(@NonNull Context context) {
        super(context, R.style.DialogStyle);
        setContentView(R.layout.dialog_prompt);
        tvPrompt = (TextView)findViewById(R.id.tv_prompt);
        tvClick = (TextView)findViewById(R.id.tv_click);
        tvTitle = (TextView)findViewById(R.id.tv_title);
        initLogic();
    }

    private void initLogic() {
        tvClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick();
                }
            }
        });
    }

    public void setPrompt(String prompt) {
        tvPrompt.setText(prompt);
    }

    public void setTvClick(String clickText) {
        tvClick.setText(clickText);
    }

    public void setOnclickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setTitle(Boolean visibility){
        if(visibility){
            tvTitle.setVisibility(View.VISIBLE);
        }
    }

    public void setPromptSize(int size){
        tvPrompt.setTextSize(size);
    }

    public interface OnClickListener {
        void onClick();
    }

    public void showDialog(Boolean title, String msg){
        final PromptDialog dialog = new PromptDialog(getContext());
        dialog.setTitle(title);
        dialog.setTvClick("确定");
        dialog.setPrompt(msg);
        dialog.setPromptSize(16);
        dialog.setOnclickListener(new PromptDialog.OnClickListener() {
            @Override
            public void onClick() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
