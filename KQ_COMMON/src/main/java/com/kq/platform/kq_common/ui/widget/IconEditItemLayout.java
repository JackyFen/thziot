package com.kq.platform.kq_common.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kq.platform.kq_common.utils.SizeUtils;

import kq.platform.kq_common.R;

/**
 * Created by sjl on 2018/3/27.
 */

public class IconEditItemLayout extends RelativeLayout {

    private final static String TAG = IconEditItemLayout.class.getSimpleName();
    /**
     * 控件右边字体（也有可能是图片或者为空）
     **/
    private String attachText;
    /**
     * 控件右边字体颜色（也有可能是图片或者为空）
     **/
    private int attachTextColor = 0x000000;
    /**
     * ediTtext隐藏字体颜色
     **/
    private int hintColor = 0xffcccccc;
    /**
     * ediTtext隐藏字体
     **/
    private String hintText;
    /**
     * ediTtext字体value值
     **/
    private String editTextValue;
    /**
     * ediTtext字体颜色
     **/
    private int editTextColor = 0x333333;
    /**
     * ediTtext字体大小
     **/
    private int editTextSize = 14;
    /**
     * 左侧小图标
     **/
    private int leftIcon;
    /**
     * 左侧小图标宽
     **/
    private float iconWidth = 20;
    /**
     * 左侧小图标高
     **/
    private float iconHeight = 20;
    /**
     * 右侧button宽
     **/
    private float btWidth = 20;
    /**
     * 右侧button高
     **/
    private float btHeight = 20;
    /**
     * type类型  11表示文本，12表示图片，0表示没有
     **/
    private int attachType;
    public static final int ATTACH_TYPE_NONE = 0;
    public static final int ATTACH_TYPE_TEXT = 11;
    public static final int ATTACH_TYPE_IMG = 12;
    /**
     * 右侧小图标
     **/
    private int rightIcon;
    private int inputType;
    private EditText editText;
    private ImageView leftImageView;
    private RelativeLayout rlContainer;
    private OnAttachClickListener attachClickListener;
    private ImageView ivAttach;
    private Button btAttach;
    private boolean isShow = false;

    public IconEditItemLayout(Context context) {
        this(context, null, -1);
    }

    public IconEditItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public IconEditItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.IconEditItemLayout);
        attachText = ta.getString(R.styleable.IconEditItemLayout_cielAttachText);
        attachTextColor = ta.getColor(R.styleable.IconEditItemLayout_cielAttachTextColor, attachTextColor);
        hintColor = ta.getColor(R.styleable.IconEditItemLayout_cielHintColor, hintColor);
        hintText = ta.getString(R.styleable.IconEditItemLayout_cielHint);
        editTextValue = ta.getString(R.styleable.IconEditItemLayout_cielEditText);
        editTextColor = ta.getColor(R.styleable.IconEditItemLayout_cielEditTextColor, Color.BLACK);
        editTextSize = ta.getInteger(R.styleable.IconEditItemLayout_cielEditTextSize, editTextSize);
        leftIcon = ta.getResourceId(R.styleable.IconEditItemLayout_cielIcon, 0);
        iconWidth = ta.getDimension(R.styleable.IconEditItemLayout_cielIconWidth, iconWidth);
        iconHeight = ta.getDimension(R.styleable.IconEditItemLayout_cielIconHeight, iconHeight);
        attachType = ta.getInt(R.styleable.IconEditItemLayout_cielAttachType, ATTACH_TYPE_NONE);
        rightIcon = ta.getResourceId(R.styleable.IconEditItemLayout_cielAttachImgSrc, 0);
        inputType = ta.getInt(R.styleable.IconEditItemLayout_android_inputType, InputType.TYPE_CLASS_TEXT);
        btHeight = ta.getDimension(R.styleable.IconEditItemLayout_cielBtHeight, 0);
        btWidth = ta.getDimension(R.styleable.IconEditItemLayout_cielBtWidth, 0);
        ta.recycle();
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_common_icon_edit, this, true);
        editText = findViewById(R.id.et_inputValue);
        leftImageView = findViewById(R.id.left_icon);
        rlContainer = findViewById(R.id.rl_container);
        if (TextUtils.isEmpty(editTextValue)) {
            editText.setHint(hintText);
            editText.setHintTextColor(hintColor);
        } else {
            editText.setText(editTextValue);
            editText.setSelection(editTextValue.length());
        }
        editText.setTextColor(editTextColor);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, editTextSize);
        editText.setInputType(inputType);
        leftImageView.setImageResource(leftIcon);

        if (attachType != 0) {
            switch (attachType) {
                case ATTACH_TYPE_TEXT:
                    addButtonAttach(context);
                    break;
                case ATTACH_TYPE_IMG:
                    addImageAttach(context);
                    break;
            }
        }
    }

    @SuppressLint("NewApi")
    private void addButtonAttach(Context context) {
        if (!TextUtils.isEmpty(attachText)) {
            btAttach = new Button(context);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams((int) btWidth, (int) btHeight);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            lp.addRule(RelativeLayout.CENTER_VERTICAL);
            lp.setMargins(0, 0, context.getResources().getDimensionPixelSize(R.dimen.margin_8dp), 0);
            btAttach.setLayoutParams(lp);
            btAttach.setText(attachText);
            btAttach.setWidth(SizeUtils.dp2px(context, (int) btWidth));
            btAttach.setHeight(SizeUtils.dp2px(context, (int) btHeight));
            btAttach.setTextColor(attachTextColor);
            btAttach.setTextSize(TypedValue.COMPLEX_UNIT_SP, editTextSize);
            btAttach.setId(View.generateViewId());
            btAttach.setBackgroundResource(R.drawable.bg_message_code_bt);
            btAttach.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (attachClickListener != null) {
                        attachClickListener.OnAttachClick(IconEditItemLayout.this);
                    }
                }
            });
            rlContainer.addView(btAttach);

            RelativeLayout.LayoutParams etValueLp = (RelativeLayout.LayoutParams) editText.getLayoutParams();
            etValueLp.addRule(RelativeLayout.LEFT_OF, btAttach.getId());
            editText.setLayoutParams(etValueLp);
        } else {
            Log.e(TAG, "attach text is null");
        }
    }

    @SuppressLint("NewApi")
    private void addImageAttach(Context context) {
        if (rightIcon != 0) {
            ivAttach = new ImageView(context);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            lp.addRule(RelativeLayout.CENTER_VERTICAL);
            lp.setMargins(0, 0, 13, 0);
            ivAttach.setLayoutParams(lp);
            ivAttach.setId(View.generateViewId());
            ivAttach.setImageResource(rightIcon);
            ivAttach.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (attachClickListener != null) {
                        attachClickListener.OnAttachClick(IconEditItemLayout.this);
                    }
                }
            });
            rlContainer.addView(ivAttach);
            RelativeLayout.LayoutParams etValueLp = (RelativeLayout.LayoutParams) editText.getLayoutParams();
            etValueLp.addRule(RelativeLayout.LEFT_OF, ivAttach.getId());
            editText.setLayoutParams(etValueLp);
        } else {
            Log.e(TAG, "attach img src is null");
        }
    }

    public String getValue() {
        return editText.getText().toString();
    }

    public void setValue(String text) {
        editText.setText(text);
    }

    public void setBtnEnable(boolean enable) {
        btAttach.setEnabled(enable);
    }

    public void setAttachValue(String text) {
        btAttach.setText(text);
    }

    /**
     * 设置最大长度
     *
     * @param maxLength
     */
    public void setMaxLength(int maxLength) {
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
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
        editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        isShow = false;
        editText.setSelection(editText.getText().length());
    }

    /**
     * 显示文本
     */
    public void setEditTextShow() {
        editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        isShow = true;
        editText.setSelection(editText.getText().length());
    }

    public void setInputType(int inputType) {
        editText.setInputType(inputType);
        editText.setSelection(editText.getText().length());
    }

    public void setRightIcon(int rightIcon) {
        ivAttach.setImageResource(rightIcon);
    }

    public void setOnAttachClickListener(OnAttachClickListener listener) {
        this.attachClickListener = listener;
    }

    /**
     * 右边图标或者按钮的点击回调
     */
    public interface OnAttachClickListener {
        void OnAttachClick(IconEditItemLayout view);
    }
}
