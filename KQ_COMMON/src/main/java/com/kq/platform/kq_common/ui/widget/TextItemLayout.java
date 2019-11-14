package com.kq.platform.kq_common.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import kq.platform.kq_common.R;


/**
 * Created by Zhou jiaqi on 2017/12/25.
 */

public class TextItemLayout extends LinearLayout {

    private final static String TAG = TextItemLayout.class.getSimpleName();


    public static final int LINE_NONE = 0;
    public static final int LINE_TOP = 1;
    public static final int LINE_BOTTOM = 2;
    public static final int LINE_BOTH = 3;

    public static final int ATTACH_TYPE_NONE = 0;
    public static final int ATTACH_TYPE_TEXT = 11;
    public static final int ATTACH_TYPE_IMG = 12;

    private TextView tvLabel;
    private TextView tvValue;
    private RelativeLayout rlValue;
    private View lineTop;
    private View lineBottom;

    private String hintStr;
    private int hintColor = 0xffcccccc;
    private int labelColor = 0xff666666;
    private String labelStr;

    private int attachType;
    private int attachImgSrc;
    private int attachTextBg;
    private String attachText;

    private int showLine;

    private OnValueClickListener valueClickListener;

    private OnAttachClickListener attachClickListener;

    public TextItemLayout(Context context) {
        this(context, null, -1);
    }

    public TextItemLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TextItemLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextItemLayout);
        labelStr = a.getString(R.styleable.TextItemLayout_ctlLabel);

        labelColor = a.getColor(R.styleable.TextItemLayout_ctlLabelColor, labelColor);
        attachImgSrc = a.getResourceId(R.styleable.TextItemLayout_ctlAttachImgSrc, attachImgSrc);
        hintColor = a.getColor(R.styleable.TextItemLayout_ctlHintColor, hintColor);
        hintStr = a.getString(R.styleable.TextItemLayout_ctlHint);
        showLine = a.getInt(R.styleable.TextItemLayout_ctlShowLine, LINE_NONE);
        attachTextBg = a.getResourceId(R.styleable.TextItemLayout_ctlAttachTextBg, 0);
        attachType = a.getInt(R.styleable.TextItemLayout_ctlAttachType, ATTACH_TYPE_NONE);
        attachText = a.getString(R.styleable.TextItemLayout_ctlAttachText);
        a.recycle();
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_common_text, this, true);
        tvLabel = findViewById(R.id.tv_label);
        tvLabel.setTextColor(labelColor);
        tvValue = findViewById(R.id.tv_value);
        rlValue = findViewById(R.id.rl_value);
        lineTop = findViewById(R.id.line_top);
        lineBottom = findViewById(R.id.line_bottom);

        tvValue.setHint(hintStr);
        tvValue.setHintTextColor(hintColor);

        tvLabel.setText(labelStr);

        if (attachType != 0) {
            switch (attachType) {
                case ATTACH_TYPE_TEXT:
                    addTextAttach(context);
                    break;
                case ATTACH_TYPE_IMG:
                    addImageAttach(context);
                    break;
            }
        }

        rlValue.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valueClickListener != null) {
                    valueClickListener.OnValueClick(TextItemLayout.this);
                }
            }
        });

        setShowLine();
    }

    /**
     * 设置他的事件分发机制----解决点击一整条时候的冲突
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (valueClickListener == null && attachClickListener == null) {
            return true;
        }
        return false;
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

    private void addTextAttach(Context context) {
        if (!TextUtils.isEmpty(attachText)) {
            TextView tvAttach = new TextView(context);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            lp.addRule(RelativeLayout.CENTER_VERTICAL);
            lp.setMargins(0, 0, context.getResources().getDimensionPixelSize(R.dimen.margin_right_common_text_layout_unit), 0);
            tvAttach.setLayoutParams(lp);
            tvAttach.setText(attachText);
            tvAttach.setId(R.id.text_item_attach_text);
            tvAttach.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (attachClickListener != null) {
                        attachClickListener.OnAttachClick(TextItemLayout.this);
                    }
                }
            });

            if (attachTextBg != 0) {
                tvAttach.setBackgroundResource(attachTextBg);
            }
            rlValue.addView(tvAttach);

            RelativeLayout.LayoutParams tvValueLp = (RelativeLayout.LayoutParams) tvValue.getLayoutParams();
            tvValueLp.addRule(RelativeLayout.LEFT_OF, tvAttach.getId());
            tvValue.setLayoutParams(tvValueLp);
        } else {
            Log.e(TAG, "attach text is null");
        }
    }

    private void addImageAttach(Context context) {
        if (attachImgSrc != 0) {
            ImageView ivAttach = new ImageView(context);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(context.getResources().getDimensionPixelSize(R.dimen.height_common_text_layout),
                    context.getResources().getDimensionPixelSize(R.dimen.height_common_text_layout));
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            lp.addRule(RelativeLayout.CENTER_VERTICAL);
            ivAttach.setLayoutParams(lp);
            ivAttach.setScaleType(ImageView.ScaleType.CENTER);
            ivAttach.setId(R.id.text_item_attach_img);
            ivAttach.setImageResource(attachImgSrc);
            ivAttach.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (attachClickListener != null) {
                        attachClickListener.OnAttachClick(TextItemLayout.this);
                    }
                }
            });
            rlValue.addView(ivAttach);

            RelativeLayout.LayoutParams tvValueLp = (RelativeLayout.LayoutParams) tvValue.getLayoutParams();
            tvValueLp.addRule(RelativeLayout.LEFT_OF, ivAttach.getId());
            tvValue.setLayoutParams(tvValueLp);
        } else {
            Log.e(TAG, "attach img src is null");
        }
    }

    public void setLabel(CharSequence text) {
        tvLabel.setText(text);
    }

    public void setValue(CharSequence text) {
        tvValue.setText(text);
    }

    public String getValue() {
        return tvValue.getText().toString().trim();
    }

    public interface OnValueClickListener {
        void OnValueClick(View v);
    }

    public void setOnValueClickListener(OnValueClickListener listener) {
        this.valueClickListener = listener;
    }

    public void setOnAttachImgClickListener(OnAttachClickListener listener) {
        this.attachClickListener = listener;
    }

    public interface OnAttachClickListener {
        void OnAttachClick(View v);
    }
}
