package com.kq.platform.kq_common.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import kq.platform.kq_common.R;


/**
 * Created by Zhou jiaqi on 2017/12/28.
 */

@SuppressLint("ClickableViewAccessibility")
public class EditItemLayout extends LinearLayout {

    private final static String TAG = EditItemLayout.class.getSimpleName();

    public static final int LINE_NONE = 0;
    public static final int LINE_TOP = 1;
    public static final int LINE_BOTTOM = 2;
    public static final int LINE_BOTH = 3;

    public static final int ATTACH_TYPE_NONE = 0;
    public static final int ATTACH_TYPE_TEXT = 11;
    public static final int ATTACH_TYPE_IMG = 12;

    private TextView tvLabel;
    private EditText etValue;
    private RelativeLayout rlValue;
    private View lineTop;
    private View lineBottom;

    private String labelStr;
    private String attachText;
    private String hintStr;
    private int hintColor = 0xffcccccc;
    private int labelColor = 0xff333333;
    private int showLine;
    private int attachType;
    private int attachImgSrc;
    private int inputType;
    private int attachTextBg;
    private int lines;
    private int maxLines;
    private int imeOptions;
    private String digitsStr;
    private Boolean isShow = false;

    private OnAttachClickListener attachClickListener;
    private ImageView ivAttach;

    public EditItemLayout(Context context) {
        this(context, null, -1);
    }

    public EditItemLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public EditItemLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EditItemLayout);
        labelStr = a.getString(R.styleable.EditItemLayout_celLabel);
        labelColor = a.getColor(R.styleable.EditItemLayout_celLabelColor, labelColor);
        attachText = a.getString(R.styleable.EditItemLayout_celAttachText);
        showLine = a.getInt(R.styleable.EditItemLayout_celShowLine, LINE_NONE);
        attachType = a.getInt(R.styleable.EditItemLayout_celAttachType, ATTACH_TYPE_NONE);
        attachImgSrc = a.getResourceId(R.styleable.EditItemLayout_celAttachImgSrc, 0);
        attachTextBg = a.getResourceId(R.styleable.EditItemLayout_celAttachTextBg, 0);
        hintColor = a.getColor(R.styleable.EditItemLayout_celHintColor, hintColor);
        hintStr = a.getString(R.styleable.EditItemLayout_celHint);
        inputType = a.getInt(R.styleable.EditItemLayout_android_inputType, InputType.TYPE_CLASS_TEXT);
        lines = a.getInt(R.styleable.EditItemLayout_android_lines, 0);
        maxLines = a.getInt(R.styleable.EditItemLayout_android_maxLines, 0);
        imeOptions = a.getInt(R.styleable.EditItemLayout_android_imeOptions, 0);
        digitsStr = a.getString(R.styleable.EditItemLayout_android_digits);
        a.recycle();
        initView(context);
    }

    private void initView(final Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_common_edit, this, true);
        tvLabel = findViewById(R.id.tv_label);
        etValue = findViewById(R.id.et_value);
        rlValue = findViewById(R.id.rl_value);
        lineTop = findViewById(R.id.line_top);
        lineBottom = findViewById(R.id.line_bottom);

        tvLabel.setText(labelStr);
        tvLabel.setTextColor(labelColor);
        etValue.setHint(hintStr);
        etValue.setHintTextColor(hintColor);
        etValue.setInputType(inputType);
        if (lines != 0) {
            etValue.setLines(lines);
        }
        if (maxLines != 0) {
            etValue.setMaxLines(maxLines);
        }

        if (imeOptions != 0) {
            etValue.setImeOptions(imeOptions);
        }

        if(!TextUtils.isEmpty(digitsStr)) {
            etValue.setKeyListener(new DigitsKeyListener(){
                @Override
                public int getInputType() {
                    return InputType.TYPE_CLASS_TEXT;
                }
                @Override
                protected char[] getAcceptedChars() {
                    return digitsStr.toCharArray();
                }
            });
        }

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


        etValue.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //触摸的是EditText控件，且当前EditText可以滚动，则将事件交给EditText处理；否则将事件交由其父类处理
                if ((v.getId() == R.id.et_value && canVerticalScroll(etValue))) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
                return false;

            }
        });

        setShowLine();
    }

    /**
     * EditText竖直方向是否可以滚动
     *
     * @param editText 需要判断的EditText
     * @return true：可以滚动   false：不可以滚动
     */
    private boolean canVerticalScroll(EditText editText) {
        if (editText.canScrollVertically(-1) || editText.canScrollVertically(1)) {
            //垂直方向上可以滚动
            return true;
        }
        return false;
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
            tvAttach.setId(R.id.edit_item_attach_text);
            tvAttach.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (attachClickListener != null) {
                        attachClickListener.OnAttachClick(EditItemLayout.this);
                    }
                }
            });
            if (attachTextBg != 0) {
                tvAttach.setBackgroundResource(attachTextBg);
            }
            rlValue.addView(tvAttach);

            RelativeLayout.LayoutParams etValueLp = (RelativeLayout.LayoutParams) etValue.getLayoutParams();
            etValueLp.addRule(RelativeLayout.LEFT_OF, tvAttach.getId());
            etValue.setLayoutParams(etValueLp);
        } else {
            Log.e(TAG, "attach text is null");
        }
    }

    private void addImageAttach(Context context) {
        if (attachImgSrc != 0) {
            ivAttach = new ImageView(context);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(context.getResources().getDimensionPixelSize(R.dimen.height_common_text_layout),
                    context.getResources().getDimensionPixelSize(R.dimen.height_common_text_layout));
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            lp.addRule(RelativeLayout.CENTER_VERTICAL);
            ivAttach.setLayoutParams(lp);
            ivAttach.setScaleType(ImageView.ScaleType.CENTER);
            ivAttach.setId(R.id.edit_item_attach_img);
            ivAttach.setImageResource(attachImgSrc);
            ivAttach.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (attachClickListener != null) {
                        attachClickListener.OnAttachClick(EditItemLayout.this);
                    }
                }
            });
            rlValue.addView(ivAttach);

            RelativeLayout.LayoutParams etValueLp = (RelativeLayout.LayoutParams) etValue.getLayoutParams();
            etValueLp.addRule(RelativeLayout.LEFT_OF, ivAttach.getId());
            etValue.setLayoutParams(etValueLp);
        } else {
            Log.e(TAG, "attach img src is null");
        }
    }

    /**
     * 判断当前是否隐藏文本
     *
     * @return
     */

    public boolean isShowPassword() {
        return isShow;
    }

    /**
     * 隐藏文本
     */
    public void setEditTextHide() {
        etValue.setTransformationMethod(PasswordTransformationMethod.getInstance());
        isShow = false;
        etValue.setSelection(etValue.getText().length());
    }

    /**
     * 显示文本
     */
    public void setEditTextShow() {
        etValue.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        isShow = true;
        etValue.setSelection(etValue.getText().length());
    }

    public void setInputType(int inputType) {
        etValue.setInputType(inputType);
        etValue.setSelection(etValue.getText().length());
    }

    public void setRightIcon(int rightIcon) {
        ivAttach.setImageResource(rightIcon);
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

    /**
     * 设置最大长度
     *
     * @param maxLength
     */
    public void setMaxLength(int maxLength) {
        etValue.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
    }

    public void setNotEdit() {
        etValue.setFocusable(false);
        etValue.setEnabled(false);
    }

    public void setLabel(CharSequence text) {
        tvLabel.setText(text);
    }

    public void setValue(CharSequence text) {
        etValue.setText(text);
    }

    public void setHint(CharSequence text) {
        etValue.setHint(text);
    }

    public String getText() {
        return etValue.getText().toString().trim();
    }

    public EditText getEtValue() {
        return etValue;
    }

    /**
     * 添加监听，有些地方要限制输入的小数点位数
     *
     * @param watcher
     */
    public void setTextWatcher(FloatEditTextTextWatcher watcher) {
        etValue.addTextChangedListener(watcher);
    }

    public void setTextWatcher(TextWatcher watcher) {
        etValue.addTextChangedListener(watcher);
    }

    public void setOnAttachImgClickListener(OnAttachClickListener listener) {
        this.attachClickListener = listener;
    }

    public interface OnAttachClickListener {
        void OnAttachClick(View view);
    }
}
