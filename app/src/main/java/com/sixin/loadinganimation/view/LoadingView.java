package com.sixin.loadinganimation.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.sixin.loadinganimation.R;

import static android.animation.ObjectAnimator.ofFloat;


public class LoadingView extends LinearLayout {

    private static final String TAG = LoadingView.class.getName();
    //// TODO: 2017/8/31 这儿的inflate方法为什么会出现坑  如果不写布局文件怎么通过代码实现控件的添加
    // TODO: 2017/8/31 性能优化部分后期再去完成
    // TODO: 2017/8/31 正方形中有一部分没有出现的原因的是什么
    /**
     * 能够实现几何图形变换的view
     * */
    private ShapeView mShapeView;

    /**
     * 实现伸缩的view
     * */
    private View mScaleLine;

    /**
     * 下落的距离
     * */
    private float mDownDistance;

    private static final int ANIMATIOR_DURATION = 500;

    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view  = inflate(context, R.layout.loading_view, null);
        mScaleLine = view.findViewById(R.id.scaleLine);
        mShapeView = (ShapeView) view.findViewById(R.id.shapeView);
        addView(view);
        Log.d(TAG, "");
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        MarginLayoutParams params = (MarginLayoutParams) mShapeView.getLayoutParams();
        mDownDistance = params.bottomMargin;
        startDownAnimation();
    }

    /**
     * 实现下落的动画
     * */
    public void startDownAnimation(){
        //几何view下落的动画
        ObjectAnimator downAnimator = ofFloat(mShapeView, "translationY", 0f, mDownDistance);

        //中部线条缩放动画
        ObjectAnimator scaleAnimator = ofFloat(mScaleLine,"scaleX",0.5f,1.0f);

        //同时执行下落动画和缩放动画
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //组合动画结束时执行上抛动画以及缩放动画
                startUpAnimation();
            }
        });
        animatorSet.setDuration(ANIMATIOR_DURATION);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.playTogether(downAnimator,scaleAnimator);
        animatorSet.start();
    }

    /**
     * 实现上抛动画
     * */
    private void startUpAnimation() {
        //上抛动画
        ObjectAnimator upAnimator = ObjectAnimator.ofFloat(mShapeView,"translationY", mDownDistance, 0f);

        //旋转动画
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(mShapeView, "rotation", 0f, 45f);

        //缩放动画
        ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(mScaleLine,"scaleX",1.0f,0.5f);

        //执行组合动画：缩放动画以及上抛动画
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //动画结束时执行下落动画
                startDownAnimation();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                //动画开始时，切换几何view的形状
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mShapeView.exChangeShape();
                    }
                }).start();
            }
        });
        animatorSet.setDuration(ANIMATIOR_DURATION);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.playTogether(upAnimator,scaleAnimator,rotationAnimator);
        animatorSet.start();
    }
}
