package com.sixin.loadinganimation.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.sixin.loadinganimation.R;


public class ShapeView extends View {

    private Paint mPaint;

    private Path mPath;


    private int mShape = TRIANGLE;
    private static final int CIRCLE = 1;
    private static final int RECT = 2;
    private static final int TRIANGLE = 3;

    /**
     * cX:圆心的横坐标，
     * cY:圆心的纵坐标,
     * mRadius:圆的半径
     * */
    private int cX;
    private int cY;
    private int mRadius;

    public ShapeView(Context context) {
        super(context);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.shape_blue));
        mPaint.setAntiAlias(true);

        mPath = new Path();

    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int viewWidth = getMeasuredWidth();
        int viewHeight = getMeasuredHeight();
        cX = viewWidth / 2;
        cY = viewHeight / 2;
        if(viewHeight >= viewWidth){
            mRadius = viewWidth / 2;
        }else{
            mRadius = viewHeight / 2;
        }

        mPath.moveTo(cX,0);
        mPath.lineTo(0, viewHeight);
        mPath.lineTo(viewWidth,viewHeight);
        mPath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch(mShape){
            case CIRCLE:
                canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);
                break;
            case RECT:
                canvas.drawCircle(cX,cY,mRadius,mPaint);
                break;
            case TRIANGLE:
                canvas.drawPath(mPath,mPaint);
                break;
        }
    }

    /**
     * 用于切换view形状的方法
     * */
    public void exChangeShape(){
        switch(mShape){
            case CIRCLE:
                //当前为圆，则切换成正方形，颜色为绿色
                mPaint.setColor(getResources().getColor(R.color.shape_yellow));
                mShape = RECT;
                break;
            case RECT:
                //当前为正方形，则切换成三角形，颜色为蓝色
                mPaint.setColor(getResources().getColor(R.color.shape_blue));
                mShape = TRIANGLE;
                break;
            case TRIANGLE:
                //当前为三角形，则切换成圆形,颜色为粉色
                mPaint.setColor((getResources().getColor(R.color.shape_pink)));
                mShape = CIRCLE;
                break;
        }
        postInvalidate();
    }
}
