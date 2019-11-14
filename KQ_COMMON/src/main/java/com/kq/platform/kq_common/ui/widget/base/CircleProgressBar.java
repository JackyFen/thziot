package com.kq.platform.kq_common.ui.widget.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Zhou jiaqi on 2017/12/20.
 */
public class CircleProgressBar extends View {

    private Paint mPaint;

    private int radius;
    private int height;

    private  int  rote;
    private  int  speed=1;
    private  int  add=1;

    public CircleProgressBar(Context context) {
        super(context);
        init();
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 设置圆环的宽度
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心

        new Thread(new Ru()).start();
    }

    private  class  Ru implements Runnable {

        @Override
        public void run() {
            while (true){
                try {
                    Thread.sleep(100);
                    if (rote>360){
                        rote=360 / 12;
                    }
                    rote+=360 / 12;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                postInvalidate();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth() - getPaddingLeft() - getPaddingRight();
        int heightT= getHeight() - getPaddingTop() - getPaddingBottom();
        int realWidth= Math.min(width,heightT);

        int centreX = getWidth()/2;
        int centreY = getHeight()/2;

        radius =centreY-realWidth/6-5;
        height = realWidth/4 -realWidth/10;
        double a= Math.toRadians(15);
        int strokeCap=(int)((Math.tan(a)*radius));
        mPaint.setStrokeWidth(strokeCap);

        drawOval(canvas, width / 2, radius);
    }

    /**
     * 得到灰色
     *
     * @param color
     * @return
     */
    public int getColor(int color) {

        return Color.rgb(color, color, color);
    }

    /**
     * 根据参数画出每个小块
     *
     * @param canvas
     * @param centre
     * @param radius
     */
    private void drawOval(Canvas canvas, int centre, int radius) {
        mPaint.setColor(Color.GREEN);
        canvas.rotate(rote, centre, centre);
        for (int i = 0; i < 12; i++) {

            mPaint.setColor(getColor(250-(i*10)));
            canvas.drawLine(centre, radius, centre, radius - height, mPaint);
            canvas.rotate(360 / 12, centre, centre); //旋转画纸
        }
    }
}
