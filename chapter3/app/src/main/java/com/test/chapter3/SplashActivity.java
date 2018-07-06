package com.test.chapter3;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.util.Log;


import java.lang.ref.WeakReference;

public class SplashActivity extends AppCompatActivity {
    private final String TAG = "###Splash###";

    private TextView textView;
    private int count = 5;
    private Animation animation;

    private MyHandler myHandler = new MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //去标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash);




        textView = (TextView)findViewById(R.id.textView);
        animation = AnimationUtils.loadAnimation(this, R.anim.animation_text);

        myHandler.sendEmptyMessageDelayed(0,1000);
    }



    private class MyHandler extends Handler{
        private final WeakReference<SplashActivity> activityWeakReference;

        public MyHandler(SplashActivity activity){
            activityWeakReference = new WeakReference<SplashActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg){
            SplashActivity activity = activityWeakReference.get();

            if(activity != null){
                if(msg.what == 0){
                    textView.setText(getCount()+"");
                    myHandler.sendEmptyMessageDelayed(0, 1000);
                    animation.reset();
                    textView.startAnimation(animation);
                }
            }

        }
    };


    private int getCount(){
        --count;
        if(count == 0){
            Intent  intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        return count;
    }


    @Override
    protected void onDestroy() {
        myHandler.removeCallbacksAndMessages(null);
        super.onDestroy();

        Log.d(TAG, "#################### finish()   onDestroy!!!!!!");
    }
}
