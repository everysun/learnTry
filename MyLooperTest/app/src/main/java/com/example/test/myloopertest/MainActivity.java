package com.example.test.myloopertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyLooper";
    private static final int IN_THREAD = 1;
    private TextView mTextView = null;
    private MyHandlerThread mHandlerThread;
    private Handler mHandlerMain;
    Boolean once = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView)findViewById(R.id.textView);


    }

    @Override
    protected void onResume(){
        super.onResume();

        //子线程向自身的Looper中发送消息
        new Thread(){
            public void run(){
                Looper.prepare();

                mHandlerThread = new MyHandlerThread(Looper.myLooper());
                Message message = mHandlerThread.obtainMessage();
                message.what = IN_THREAD;
                message.obj = "子线程发送到自身Looper的消息 mHandlerThread!!!"+ Thread.currentThread().getName();
                mHandlerThread.sendMessage(message);
                Looper.loop();
            }
        }.start();


        //默认会关联到UI线程的mainLooper;
        mHandlerMain = new Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                mTextView.setText("主线程中收到子线程消息： "+ msg.obj);
                Log.d(TAG, "主线程收到：" + msg.obj);
                //向子线程发送消息  用子线程的handler sendMessage　发送消息到子线程的Laoper中
                if(once){
                    Message message = mHandlerThread.obtainMessage();
                    message.obj = "主线程－》子线程发送的消息！！！" + Thread.currentThread().getName();
                    mHandlerThread.sendMessage(message);
                    once = true;
                }

            }
        };
    }

    private class MyHandlerThread extends Handler{
        private MyHandlerThread(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            Log.d(TAG, "子线程收到：" + msg.obj);

            if(msg.what == IN_THREAD){
                Toast.makeText(MainActivity.this, "子线程自身Looper中消息"+msg.obj, Toast.LENGTH_LONG).show();
                Log.d(TAG, "子线程收到：" + msg.obj);
            }
            //向UI主线程发送消息
            Message message = mHandlerMain.obtainMessage();
            message.obj = "子线程-》主线程发送的消息！！！"+Thread.currentThread().getName();
            mHandlerMain.sendMessage(message);
        }
    }


}
