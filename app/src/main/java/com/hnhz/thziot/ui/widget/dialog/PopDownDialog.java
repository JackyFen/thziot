package com.hnhz.thziot.ui.widget.dialog;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.hnhz.thziot.R;

/**
 * Created by Zhou jiaqi on 2019/8/6.
 */
public class PopDownDialog extends Dialog {
    private View mAnchor;
    private boolean mAnchorChanged;
    private View mBackground;
    private FrameLayout mContentView;
    private View mRealContent;
    private FrameLayout mRoot;

    public PopDownDialog(Context context) {
        this(context, R.style.Theme_Top_PopDown);
    }

    public PopDownDialog(Context context, int i) {
        super(context, i);
        this.mAnchorChanged = false;
        Window window = getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        this.mRoot = new PopDownFrameLayout(context);
        this.mContentView = new FrameLayout(context);
        this.mBackground = new View(context);
        this.mRoot.addView(this.mContentView);
        this.mContentView.addView(this.mBackground);
        this.mBackground.setBackgroundColor(context.getResources().getColor(R.color.bg_spinner_title));
        this.mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        window.setContentView(this.mRoot);
    }

    public void setAnchor(View view) {
        this.mAnchor = view;
        this.mAnchorChanged = true;
    }

    public void setContentView(View view, WindowManager.LayoutParams layoutParams) {
        this.mRealContent = view;
        this.mContentView.addView(this.mRealContent, layoutParams);
    }

    @Override
    public void show() {
        super.show();
        if (this.mRealContent.getMeasuredHeight() == 0) {
            Point screenSize = getScreenSize(getContext());
            this.mRoot.measure(View.MeasureSpec.makeMeasureSpec(screenSize.x, View.MeasureSpec.AT_MOST),
                    View.MeasureSpec.makeMeasureSpec(screenSize.y, View.MeasureSpec.AT_MOST));
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mRealContent, "translationY",
                new float[]{(float) (-this.mRealContent.getMeasuredHeight()), 0.0f});
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mBackground, "alpha",
                new float[]{0.0f, 1.0f});

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{ofFloat, ofFloat2});
        animatorSet.setDuration(200);
        animatorSet.start();
    }

    @Override
    public void dismiss() {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mRealContent, "translationY",
                new float[]{0.0f, (float) (-this.mRealContent.getMeasuredHeight())});
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mBackground, "alpha",
                new float[]{1.0f, 0.0f});
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{ofFloat, ofFloat2});
        animatorSet.setDuration(200);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                PopDownDialog.super.dismiss();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    public void dismissWithoutAnimation() {
        super.dismiss();
    }

    public Point getScreenSize(Context context) {
        Point point = new Point();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(point);
        return point;
    }


    class PopDownFrameLayout extends FrameLayout {

        public PopDownFrameLayout(@NonNull Context context) {
            super(context);
        }

        public PopDownFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        public PopDownFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }


        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            if (mAnchor != null && mAnchorChanged) {
                mAnchorChanged = false;
                Rect rect = new Rect();
                mAnchor.getWindowVisibleDisplayFrame(rect);
                int[] iArr = new int[2];
                mAnchor.getLocationOnScreen(iArr);
                ((LayoutParams) mContentView.getLayoutParams()).topMargin = mAnchor.getHeight() + (iArr[1] - rect.top);
            }
            super.onLayout(changed, left, top, right, bottom);
        }


    }
}
