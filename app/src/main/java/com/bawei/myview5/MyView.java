package com.bawei.myview5;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 作    者：云凯文
 * 时    间：2017/3/12
 * 描    述：
 * 修改时间：
 */

public class MyView extends View {

    private int circle_radius;//圆的半径
    private int width;
    private int height;
    private int text_size;
    private String text;

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        //获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyView);
        circle_radius = (int) typedArray.getDimension(R.styleable.MyView_circle_radius, 50);
        text_size = (int) typedArray.getDimension(R.styleable.MyView_text_size, 10);
        text = typedArray.getString(R.styleable.MyView_text);
        typedArray.recycle();//回收容器
    }

    //测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getWidth() / 2;
        height = getHeight() / 2;
        setMeasuredDimension(circle_radius * 2, circle_radius * 2);
    }

    //绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画圆
        circleview(canvas);

        //写文本
        textString(canvas);

    }

    private void circleview(Canvas canvas) {
        Paint mPaint = new Paint();//实例化画笔
        mPaint.setColor(Color.RED);//画笔颜色
        mPaint.setStrokeWidth(3);//画笔宽度
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setStyle(Paint.Style.FILL);//设置为空心圆

        canvas.drawCircle(width, height, circle_radius, mPaint);
    }

    private void textString(Canvas canvas) {
        Paint zPaint = new Paint();
        zPaint.setColor(Color.BLACK);
        zPaint.setTextSize(text_size);
        //获取随机数
        int sub = (int) (Math.random() * 9000 + 1000);
        text = String.valueOf(sub);
        Rect rect = new Rect();
        zPaint.getTextBounds(text, 0, text.length(), rect);
        int size_width = rect.width();
        int size_height = rect.height();

        canvas.drawText(text, width - size_width / 2, height + size_height / 2, zPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();

                //根据勾股定理获取点击位置到圆中心点的距离来判断点击的位置
                float absx = Math.abs(width - x);
                float absy = Math.abs(height - y);
                double xy = Math.sqrt(absx * absx + absy + absy);

                if (xy < circle_radius) {
                    invalidate();
                }
                break;
        }
        return true;
    }


}
