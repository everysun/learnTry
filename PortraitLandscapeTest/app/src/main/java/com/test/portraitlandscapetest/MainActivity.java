package com.test.portraitlandscapetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.os.Handler;
import android.os.Message;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Arrays;
import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    private String TAG = "||||||||||||||";
    private TextView text_screen;
    private final static int QUICKSORT = 1;
    private int[] myarray={6,1,8,10,3,0,2,5};

//    private Handler myhandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg){
//            if(msg.what == QUICKSORT){
//               if(text_screen != null){
//                   text_screen.setText(Arrays.toString((int[])msg.obj));
//               }else{
//                   text_screen.setText("quicksort result = null");
//               }
//            }
//        }
//    };

    private final MyHandler mHandler = new MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_screen = (TextView)findViewById(R.id.text_screen);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strTime = format.format(new Date());
        text_screen.setText("onCreate " + strTime);

        text_screen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                text_screen.setText("reNew");
            }
        });

        Log.e(TAG, "onCreate");

    }

    public void changeScreen(View view){

        int screenType = getResources().getConfiguration().orientation;
        if(screenType == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            TAG = "=============";
        }else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            TAG = "|||||||||||||";
        }
    }

    @Override
    public void onConfigurationChanged(Configuration conf){
        if(conf.orientation == Configuration.ORIENTATION_LANDSCAPE){
            text_screen.append("横屏");
        }else{
            text_screen.append("竖屏");
        }
        super.onConfigurationChanged(conf);
        Log.e(TAG, "onConfigurationChanged");
    }

    @Override
    protected void onStart(){
        super.onStart();
        text_screen.append("\n onStart");
        Log.e(TAG, "onStart");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        text_screen.append("\n onRestart");
        Log.e(TAG, "onRestart");
    }

    @Override
    protected void onResume(){
        super.onResume();
        text_screen.append("\n onResume");
    }

    @Override
    protected void onPause(){
        super.onPause();
        text_screen.append("\n onPause");
        Log.e(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        text_screen.append("\n onStop");
        Log.e(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        text_screen.append("\n onDestroy");
        Log.e(TAG, "onDestroy");
        mHandler.removeCallbacksAndMessages(null);
    }


    private static class MyHandler extends Handler {
        private final WeakReference<MainActivity> mainActivityweakReference;

        public MyHandler(MainActivity activity) {
            mainActivityweakReference = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mainActivityweakReference.get();

            if (activity != null) {
                if (msg.what == QUICKSORT) {
                    if (activity.text_screen != null) {
                        activity.text_screen.setText(Arrays.toString((int[]) msg.obj));
                    } else {
                        activity.text_screen.setText("quicksort result = null");
                    }
                }
            }

        }
    }

    public void quickSort(View view){
        new Thread(){
            @Override
            public void run(){
                Log.e(TAG, "准备运行QuickSort ");
                Message msg = Message.obtain();
                normalQuickSort(myarray,0,7);
                msg.what = QUICKSORT;
                msg.obj = myarray;
                mHandler.sendMessage(msg);
            }
        }.start();
    }

    private void normalQuickSort(int[] a, int start, int end){
        LinkedList<Integer> stack = new LinkedList<Integer>();
        int l = -1;
        int r = -1;
        int index = -1;

        if(start < end){
            stack.push(end);
            stack.push(start);

            while(!stack.isEmpty()){
                l = stack.pop();
                r = stack.pop();
                index = partition(a, l, r);

                if(l < index-1){
                    stack.push(index-1);
                    stack.push(l);
                }
                if(r > index+1){
                    stack.push(r);
                    stack.push(index+1);
                }
            }
        }

    }

    private int partition(int[] a, int start, int end){
        int pivot = a[start];

        while(start < end){
            while(start < end && a[end] >= pivot){
                --end;
            }
            a[start] = a[end];

            while(start < end && a[start] <= pivot){
                ++start;
            }
            a[end] = a[start];
        }

        a[start] = pivot;
        return start;
    }

}
