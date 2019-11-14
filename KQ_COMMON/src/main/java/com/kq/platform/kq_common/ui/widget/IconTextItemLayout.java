package com.kq.platform.kq_common.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import kq.platform.kq_common.R;


/**
 * Created by sjl on 2018/4/3.
 */

public class IconTextItemLayout extends RelativeLayout {

    private int leftIcon;
    private String text;
    private float textSize;
    private int textColor;
    private View lineTop;
    private View lineBottom;
    private ImageView ivLeftIcon;
    private TextView tvText;
    public static final int LINE_NONE = 0;
    public static final int LINE_TOP = 1;
    public static final int LINE_BOTTOM = 2;
    public static final int LINE_BOTH = 3;
    private int showLine;

    public IconTextItemLayout(Context context) {
        this(context, null, -1);
    }

    public IconTextItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    @SuppressLint("ResourceAsColor")
    public IconTextItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IconTextItemLayout);
        leftIcon = a.getResourceId(R.styleable.IconTextItemLayout_itlIcon, 0);
        text = a.getString(R.styleable.IconTextItemLayout_itlText);
        textSize = a.getDimension(R.styleable.IconTextItemLayout_itlTextSize, 13);
        textColor = a.getColor(R.styleable.IconTextItemLayout_itlTextColor, R.color.default_text_color);
        showLine = a.getInt(R.styleable.IconTextItemLayout_itlShowLine, LINE_NONE);
        a.recycle();
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_common_icon, this, true);
        lineTop = findViewById(R.id.line_top);
        lineBottom = findViewById(R.id.line_bottom);
        ivLeftIcon = findViewById(R.id.iv_left_icon);
        tvText = findViewById(R.id.tv_text);
        tvText.setText(text);
        tvText.setTextColor(textColor);
        tvText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        ivLeftIcon.setImageResource(leftIcon);
        setShowLine();
    }

    private void setShowLine() {
        switch (showLine) {
            case LINE_NONE:
                lineTop.setVisibility(View.GONE);
                lineBottom.setVisibility(View.GONE);
                break;
            case LINE_TOP:
                lineTop.setVisibility(View.VISIBLE);
                lineBottom.setVisibility(View.GONE);
                break;
            case LINE_BOTTOM:
                lineTop.setVisibility(View.GONE);
                lineBottom.setVisibility(View.VISIBLE);
                break;
            case LINE_BOTH:
                lineTop.setVisibility(View.VISIBLE);
                lineBottom.setVisibility(View.VISIBLE);
                break;
            default:
                lineTop.setVisibility(View.GONE);
                lineBottom.setVisibility(View.GONE);
                break;
        }
    }

}
