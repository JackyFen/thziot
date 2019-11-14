package com.hnhz.thziot.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hnhz.thziot.R;

/**
 * Created by Zhou jiaqi on 2019/8/5.
 */
public class ListPopDownTitleBar extends RelativeLayout {

    private TextView title;
    private TextView titleDesc;
    private ImageView ivTitleDescArrow;

    private ImageView imgBtnLeft;
    private ImageView imgBtnRight;
    private TextView tvBtnRight;

    private Context context;

    private View viewTitleBar;

    private LinearLayout titleView;

    private OnClickLeftListener leftListener;
    private OnTitleClickListener titleClickListener;
    private OnClickRightListener rightListener;
    private OnClickRightTextViewListener rightTextViewListener;
    private TextView tvBtnLeft;
    private OnClickLeftTextViewListener onClickLeftTextViewListener;

    public ListPopDownTitleBar(Context context) {
        this(context, null, -1);
    }

    public ListPopDownTitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ListPopDownTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    public void initView() {
        viewTitleBar = LayoutInflater.from(context).inflate(R.layout.layout_popdown_title_bar, this,true);
        initWidget();
    }

    public void initVisible(boolean leftBtn, boolean leftText, boolean rightBtn, boolean rightText) {
        imgBtnLeft.setVisibility(leftBtn ? VISIBLE : INVISIBLE);
        imgBtnRight.setVisibility(rightBtn ? VISIBLE : INVISIBLE);
        tvBtnRight.setVisibility(rightText ? VISIBLE : GONE);
        tvBtnLeft.setVisibility(leftText ? VISIBLE : GONE);
    }

    public void setTitle(CharSequence sequence) {
        title.setText(sequence);
    }

    public void setRightTVText(CharSequence sequence) {
        tvBtnRight.setText(sequence);
    }

    public void setLeftTVText(CharSequence sequence) {
        tvBtnLeft.setText(sequence);
    }

    public void setImgBtnLeftRe(int id) {
        imgBtnLeft.setImageResource(id);
    }

    public void setImgBtnRightRe(int id) {
        imgBtnRight.setImageResource(id);
    }

    public void setTitleDesc(CharSequence sequence) {
        titleDesc.setText(sequence);
    }

    public void setIvTitleDescArrow(int id){ivTitleDescArrow.setImageResource(id);}

    public LinearLayout getTitleView(){
        return titleView;
    }

    public void initWidget() {
        title = get(viewTitleBar, R.id.title_bar_title_tv);
        titleDesc = get(viewTitleBar, R.id.title_bar_title_desc_tv);
        ivTitleDescArrow = get(viewTitleBar,R.id.title_bar_title_desc_arrow);

        tvBtnRight = get(viewTitleBar, R.id.title_bar_tv_btn_right);
        imgBtnLeft = get(viewTitleBar, R.id.title_bar_img_btn_left);
        imgBtnRight = get(viewTitleBar, R.id.title_bar_img_btn_right);
        tvBtnLeft = get(viewTitleBar, R.id.title_bar_tv_btn_left);

        titleView = get(viewTitleBar, R.id.title_bar_title_ll);


        tvBtnRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rightTextViewListener != null) {
                    rightTextViewListener.onClickRightTextView(view);
                }
            }
        });
        tvBtnLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickLeftTextViewListener != null) {
                    onClickLeftTextViewListener.onClickLeftTextView(view);
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


        imgBtnLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (leftListener != null) {
                    leftListener.onClickLeft(view);
                }
            }
        });

        titleView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(titleClickListener !=null){
                    titleClickListener.onTitleClick(v);
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

    public void setRightTextViewListener(OnClickRightTextViewListener rightListener) {
        this.rightTextViewListener = rightListener;
    }

    public void setLeftTextViewListener(OnClickLeftTextViewListener leftListener) {
        this.onClickLeftTextViewListener = leftListener;
    }

    public void setOnTitleClickListener(OnTitleClickListener titleClickListener){
        this.titleClickListener = titleClickListener;
    }

    public interface OnClickLeftListener {
        void onClickLeft(View v);
    }

    public interface OnClickRightListener {
        void onClickRight(View v);
    }

    public interface OnClickRightTextViewListener {
        void onClickRightTextView(View v);
    }

    public interface OnClickLeftTextViewListener {
        void onClickLeftTextView(View v);
    }

    public interface OnTitleClickListener{
        void onTitleClick(View v);
    }


    /**
     * View 一定要是容器
     *
     * @param target
     * @param id
     * @param <T>
     * @return
     */
    private <T extends View> T get(View target, int id) {
        return (T) target.findViewById(id);
    }

}
