package com.example.test.myimageloader.view;


import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class BounceableLayout extends LinearLayout {
    private Scroller mScroller;
    private GestureDetector mGestureDetector;

    public BounceableLayout(Context context){
        this(context, null);
    }

    public BounceableLayout(Context context, AttributeSet attrs){
        super(context, attrs);
        setClickable(true);
        setLongClickable(true);
        mScroller = new Scroller(context);
        mGestureDetector = new GestureDetector(context, new GestureListenerImpl());

    }

    @Override
    public void computeScroll(){
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch(event.getAction()){
            case MotionEvent.ACTION_UP:
                reset(0, 0);
                break;
            default:
                return mGestureDetector.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    protected void beginScroll(int dx, int dy){
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
        invalidate();
    }

    protected void reset(int x, int y){
        int dx = x - mScroller.getFinalX();
        int dy = y - mScroller.getFinalY();
        beginScroll(dx, dy);
    }

    class GestureListenerImpl implements GestureDetector.OnGestureListener{
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY){
            int disY = (int)((distanceY - 0.5)/2);
            beginScroll(0, disY);
            return false;
        }
        @Override
        public boolean onSingleTapUp(MotionEvent e){
            return false;
        }
        @Override
        public boolean onDown(MotionEvent e){
            return true;

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float x, float y){
            return false;
        }
        @Override
        public void onLongPress(MotionEvent e){

        }

        @Override
        public void onShowPress(MotionEvent e){

        }
    }


}
