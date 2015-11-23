package com.anthole.quickdev.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.anthole.quickdev.ui.ptr.PtrFrameLayout;
import com.anthole.quickdev.ui.ptr.PtrUIHandlerHook;
import com.anthole.quickdev.ui.ptr.header.MaterialProgressDrawable;


/**
 * A Material style progress wheel, compatible up to 2.2. Todd Davies' Progress
 * Wheel https://github.com/Todd-Davies/ProgressWheel
 * 
 * @author Nico Hormazábal
 *         <p/>
 *         Licensed under the Apache License 2.0 license see:
 *         http://www.apache.org/licenses/LICENSE-2.0
 */
public class ProgressWheel extends View {

    private MaterialProgressDrawable mDrawable;
    private float mScale = 1f;
    private PtrFrameLayout mPtrFrameLayout;

    private Animation mScaleAnimation = new Animation() {
        @Override
        public void applyTransformation(float interpolatedTime, Transformation t) {
            mScale = 1f - interpolatedTime;
            mDrawable.setAlpha((int) (255 * mScale));
            invalidate();
        }
    };

    public ProgressWheel(Context context) {
        super(context);
        initView();
    }

    public ProgressWheel(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ProgressWheel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void setPtrFrameLayout(PtrFrameLayout layout) {

        final PtrUIHandlerHook mPtrUIHandlerHook = new PtrUIHandlerHook() {
            @Override
            public void run() {
                startAnimation(mScaleAnimation);
            }
        };

        mScaleAnimation.setDuration(200);
        mScaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mPtrUIHandlerHook.resume();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mPtrFrameLayout = layout;
        mPtrFrameLayout.setRefreshCompleteHook(mPtrUIHandlerHook);
    }

    private void initView() {
        mDrawable = new MaterialProgressDrawable(getContext(), this);
        mDrawable.setBackgroundColor(Color.WHITE);
        mDrawable.setCallback(this);
    }

    @Override
    public void invalidateDrawable(Drawable dr) {
        if (dr == mDrawable) {
            invalidate();
        } else {
            super.invalidateDrawable(dr);
        }
    }

    public void setColorSchemeColors(int[] colors) {
        mDrawable.setColorSchemeColors(colors);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = mDrawable.getIntrinsicHeight() + getPaddingTop() + getPaddingBottom();
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int size = mDrawable.getIntrinsicHeight();
        mDrawable.setBounds(0, 0, size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int saveCount = canvas.save();
        Rect rect = mDrawable.getBounds();
        int l = getPaddingLeft() + (getMeasuredWidth() - mDrawable.getIntrinsicWidth()) / 2;
        canvas.translate(l, getPaddingTop());
        canvas.scale(mScale, mScale, rect.exactCenterX(), rect.exactCenterY());
        mDrawable.draw(canvas);
        canvas.restoreToCount(saveCount);
    }
    
    public void start() {
    	mDrawable.setAlpha(255);
    	mDrawable.start();
	}

    public void stop() {
    	mDrawable.stop();
	}

}