package com.kq.platform.kq_common.ui.widget.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import kq.platform.kq_common.R;

/**
 * Created by Zhou jiaqi on 2017/12/25.
 */

public class TabLayoutTitleBar extends LinearLayout {

    private View viewTitleBar;

    private ImageView imgBtnLeft;
    private ImageView imgBtnRight;

    private TabLayout tbLayout;

    private OnClickLeftListener leftListener;
    private OnClickRightListener rightListener;


    public TabLayoutTitleBar(Context context) {
        this(context, null, -1);
    }

    public TabLayoutTitleBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TabLayoutTitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.layout_tab_title_bar,this,true);
        initWidget();
    }

    public void setImgBtnLeftRe(int id) {
        imgBtnLeft.setImageResource(id);
    }

    public void setImgBtnRightRe(int id) {
        imgBtnRight.setImageResource(id);
    }

    private void initWidget(){

        imgBtnLeft = findViewById(R.id.title_bar_img_btn_left);
        imgBtnRight = findViewById(R.id.title_bar_img_btn_right);
        tbLayout = findViewById(R.id.title_bar_tab);

        imgBtnLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (leftListener != null) {
                    leftListener.onClickLeft(view);
                }
            }
        });

        imgBtnRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rightListener != null) {
                    rightListener.onClickRight(view);
                }
            }
        });
    }

    public void setLeftListener(OnClickLeftListener leftListener) {
        this.leftListener = leftListener;
    }

    public void setRightListener(OnClickRightListener rightListener) {
        this.rightListener = rightListener;
    }

    public interface OnClickLeftListener {
        void onClickLeft(View v);
    }

    public interface OnClickRightListener {
        void onClickRight(View v);
    }

    public TabLayout getTbLayout(){
        return tbLayout;
    }
}
