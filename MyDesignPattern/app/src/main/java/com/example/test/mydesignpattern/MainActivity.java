package com.example.test.mydesignpattern;

import android.content.Intent;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import DesignPatternClass.Singleton;
import android.os.Handler;
import android.widget.TextView;

import com.example.test.mydesignpattern.DictProvider.DictProviderMainActivity;
import com.example.test.mydesignpattern.XMLParser.XmlParserDemo.DomParserDemo;
import com.example.test.mydesignpattern.XMLParser.XmlParserDemo.PullParserDemo;
import com.example.test.mydesignpattern.XMLParser.XmlParserDemo.SAXParserDemo;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainThread";
    private Button singleton_button = null;
    private Handler mMainHandler, mChildHandler;
    private TextView info;
    private Button msgBtn;

    private Button btnSax;
    private Button btnPull;
    private Button btnDom;
    private Button btnDictProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        singleton_button = (Button)findViewById(R.id.singleton_button);
        singleton_button.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                for(int i=0; i<5; ++i){
                    new Thread(new Runnable(){
                        @Override
                        public void run(){
                            Singleton s = Singleton.getInstance();
                            Log.i("######singleton#######", s.toString());
                        }
                    }).start();

                }
            }
        });

        info = (TextView)findViewById(R.id.info);
        msgBtn = (Button)findViewById(R.id.msgBtn);

        mMainHandler = new Handler(){
          @Override
            public void handleMessage(Message msg){
              Log.i(TAG, "Got an incoming message from the child thread - " + (String)msg.obj);
              //UI线程中设置控件
              info.setText((String)msg.obj);
          }
        };

        new ChildThread().start();

        msgBtn.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                if(mChildHandler != null){
                    //向子线程发送消息
                    Message childMsg = mChildHandler.obtainMessage();
                    childMsg.obj = mMainHandler.getLooper().getThread().getName()+ "Hello";
                    mChildHandler.sendMessage(childMsg);
                    Log.i(TAG, "向子线程发送消息-" +(String)childMsg.obj );
                }
            }
        });


        btnSax = (Button)findViewById(R.id.btnSAX);
        btnPull = (Button)findViewById(R.id.btnPull);
        btnDom = (Button)findViewById(R.id.btnDom);

        btnSax.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SAXParserDemo.class);
                startActivity(intent);
            }
        });

        btnPull.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PullParserDemo.class);
                startActivity(intent);
            }
        });

        btnDom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, DomParserDemo.class);
                startActivity(intent);
            }
        });

        btnDictProvider = (Button)findViewById(R.id.btnDictProvider);

        btnDictProvider.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, DictProviderMainActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i(TAG, "停止子线程的消息队列");
        mChildHandler.getLooper().quit();
    }

    class ChildThread extends Thread{
        private static final String CHILD_TAG = "ChildThread";

        public void run(){
            this.setName(CHILD_TAG);

            Looper.prepare();
            mChildHandler = new Handler(){
                @Override
                public void handleMessage(Message msg){
                    Log.i(CHILD_TAG, "获得了从主线程发来的消息-"+(String)msg.obj);

                    try{
                        sleep(1000);
                        Message toMain= mMainHandler.obtainMessage();
                        toMain.obj = "这是："+ this.getLooper().getThread().getName() + "要发送吗" + (String)msg.obj;

                        mMainHandler.sendMessage(toMain);
                        Log.i(CHILD_TAG, "向主线程发送了信息-"+ (String)toMain.obj);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            };

            Log.i(CHILD_TAG, "子线程Handler已绑定到- "+ mChildHandler.getLooper().getThread().getName());
            Looper.loop();
        }
    }
}
