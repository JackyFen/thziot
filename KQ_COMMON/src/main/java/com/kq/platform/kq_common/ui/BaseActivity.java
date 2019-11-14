package com.kq.platform.kq_common.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import com.kq.platform.kq_common.presenter.BasePresenter;
import com.kq.platform.kq_common.ui.widget.base.TitleBar;
import com.kq.platform.kq_common.utils.StatusBarUtil;

import butterknife.ButterKnife;
import kq.platform.kq_common.R;

/**
 * 基础公用Activity
 * Created by Zhou jiaqi on 2017/12/20.
 */

public abstract class BaseActivity<P extends BasePresenter> extends BaseCompactActivity<P> {

    protected TitleBar baseTitleBar;

    private LinearLayout baseContainer;

    /**
     * 设置视图布局
     *
     * @return
     */
    abstract protected int getLayoutId();

    /**
     * 控件初始化
     */
    abstract protected void initWidget();

    /**
     * 绑定逻辑
     */
    abstract protected void bindLogic();


    protected SparseArray<View> views = new SparseArray<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        addContentView();
        ButterKnife.bind(this);
        initWidget();
        bindLogic();
    }

    public void setCommonTitle(String title) {
        baseTitleBar.setTitle(title);
        baseTitleBar.initVisible(true, false, false, false);
        baseTitleBar.setImgBtnLeftRe(R.mipmap.icon_back);
        baseTitleBar.setLeftListener(new TitleBar.OnClickLeftListener() {
            @Override
            public void onClickLeft(View v) {
                finish();
            }
        });
    }

    public void addContentView() {
        if(Build.VERSION.SDK_INT>=21){
            StatusBarUtil.setStatusBarColor(this,R.color.base_head_color,false);
        }
        baseTitleBar = (TitleBar) findViewById(R.id.base_title_bar);
        baseContainer =  findViewById(R.id.base_container);
        if (baseContainer.getChildCount() == 1)
            LayoutInflater.from(this).inflate(getLayoutId(), baseContainer,true);
    }

    public void initBarVisible(boolean v) {
        baseTitleBar.setVisibility(v ? View.VISIBLE : View.GONE);
    }

    public void initBarItemVisible(boolean leftBtn, boolean leftText, boolean rightBtn, boolean rightText) {
        baseTitleBar.initVisible(leftBtn, leftText, rightBtn, rightText);
    }

    public void setBarBtnLeftRe(int id) {
        baseTitleBar.setImgBtnLeftRe(id);
    }

    public void setBarBtnRightRe(int id) {
        baseTitleBar.setImgBtnRightRe(id);
    }

    public void setBarRightText(CharSequence charSequence) {
        baseTitleBar.setRightTVText(charSequence);
    }

    /**
     * （参数不要时填null）
     *
     * @param leftListener
     * @param rightListener
     * @param rightTextViewListener
     */
    public void setBarListener(TitleBar.OnClickLeftListener leftListener, TitleBar.OnClickRightListener rightListener, TitleBar.OnClickRightTextViewListener rightTextViewListener) {
        if (leftListener != null)
            baseTitleBar.setLeftListener(leftListener);
        if (rightListener != null)
            baseTitleBar.setRightListener(rightListener);
        if (rightTextViewListener != null)
            baseTitleBar.setRightTextViewListener(rightTextViewListener);
    }


    public void setTitle(CharSequence charSequence) {
        baseTitleBar.setTitle(charSequence);
    }




}
