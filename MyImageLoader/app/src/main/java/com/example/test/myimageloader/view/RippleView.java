package com.example.test.myimageloader.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.test.myimageloader.R;

public class RippleView extends View {

    private int mWidth;
    private int mHeight;
    private int mMaxWidth;
    private int mAlpha1 = 0;
    private int mAlpha2 = 0;
    private int mAlpha3 = 0;


    private Bitmap mRippleBitmap;
    private Paint mPaint;

    private int mBitmapWidth;
    private int mBitmapHeight;

    private boolean isStartRipple;

    private int heightPaddingTop;
    private int heightPanddingBottom;
    private int widthPaddingLeft;
    private int widthPaddingRight;

    private int rippleFirstRadius = 0;
    private int rippleSecondRadius = -33;
    private int rippleThirdRadius = -66;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            invalidate();

            if(isStartRipple){
                rippleFirstRadius++;
                mAlpha1 = 255-rippleFirstRadius*4;
                if(mAlpha1<=0){
                    mAlpha1=0;
                }
                if (rippleFirstRadius > mMaxWidth/10) {
                    rippleFirstRadius = 0;
                }

                rippleSecondRadius++;
                mAlpha2 =255-rippleSecondRadius*4;
                if(mAlpha2<=0){
                    mAlpha2=0;
                }
                if (rippleSecondRadius > mMaxWidth/10) {
                    rippleSecondRadius = 0;

                }

                rippleThirdRadius++;
                mAlpha3 = 255-rippleThirdRadius*4;
                if(mAlpha3<=0 ){
                    mAlpha3=0;
                }
                if (rippleThirdRadius > mMaxWidth/10) {
                    rippleThirdRadius = 0;
                }
                sendEmptyMessageDelayed(0, 40);
            }
        }
    };


    public RippleView(Context context){
        this(context, null);
    }

    public RippleView(Context context, @Nullable AttributeSet attrs){
        this(context, attrs, 0);
    }

    public RippleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(4961729);
        mPaint.setStyle(Paint.Style.STROKE);

        mRippleBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
        mBitmapWidth = mRippleBitmap.getWidth();
        mBitmapHeight = mRippleBitmap.getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        mMaxWidth = (mWidth - mRippleBitmap.getWidth()) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        drawBitmap(canvas);
        if(isStartRipple){
            drawRipple(canvas);
        }

    }

    private void drawRipple(Canvas canvas){
        mPaint.setAlpha(mAlpha1);
        mPaint.setStrokeWidth(30);
        canvas.drawCircle(mWidth/2, mHeight/2, rippleFirstRadius+mBitmapWidth/2, mPaint);

        if(rippleSecondRadius>=0) {
            mPaint.setAlpha(mAlpha2);
            mPaint.setStrokeWidth(30);
            canvas.drawCircle(mWidth / 2, mHeight / 2, rippleSecondRadius + mBitmapWidth/2, mPaint);
        }
        if(rippleThirdRadius>=0) {
            mPaint.setAlpha(mAlpha3);
            mPaint.setStrokeWidth(30);
            canvas.drawCircle(mWidth / 2, mHeight / 2, rippleThirdRadius + mBitmapWidth/2, mPaint);
        }
    }

    private void drawBitmap(Canvas canvas){
        int left = (mWidth - mRippleBitmap.getWidth())/2;
        int top = (mHeight - mRippleBitmap.getHeight())/2;
        canvas.drawBitmap(mRippleBitmap, left, top, null);

    }

    public void startRipple(){
        isStartRipple = true;
        handler.sendEmptyMessage(0);
    }
}
