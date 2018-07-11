package com.test.chapter4.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import com.test.chapter4.R;


/**
 * 一个特殊的LinearLayout,任何放入内部的clickable元素都具有波纹效果，当它被点击的时候，
 * 为了性能，尽量不要在内部放入复杂的元素
 * note: long click listener is not supported current for fix compatible bug.
 */

public class RevealLayout extends LinearLayout implements Runnable {

    private static final String TAG = "DxRevealLayout";
    private static final boolean DEBUG = true;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int mTargetWidth;
    private int mTargetHeight;
    private int mMinBetweenWidthAndHeight;
    private int mMaxBetweenWidthAndHeight;
    private int mMaxRevealRadius;
    private int mRevealRadiusGap;
    private int mRevealRadius = 0;
    private float mCenterX;
    private float mCenterY;
    private int[] mLocationInScreen = new int[2];

    private boolean mShouldDoAnimation = false;
    private boolean mIsPressed = false;
    private int INVALIDATE_DURATION = 40;

    private View mTouchTarget;
    private DispatchUpTouchEventRunnable mDispatchUpTouchEventRunnable = new
            DispatchUpTouchEventRunnable();



    public RevealLayout(Context context){
        super(context);
        init();
    }


    public RevealLayout(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }






    private class DispatchUpTouchEventRunnable implements Runnable{
        public MotionEvent event;

        @Override
        public void run(){
            if(mTouchTarget == null || !mTouchTarget.isEnabled()){
                return;
            }

            if(isTouchPointInView(mTouchTarget, (int)event.getRawX(), (int)event.getRawY())){
                mTouchTarget.performClick();
            }
        }
    }


    private boolean isTouchPointInView(View view, int x, int y){
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();

        if(view.isClickable() && y >= top && y <= bottom && x >= left && x <= right) {
            return true;
        }

        return false;
    }

}
