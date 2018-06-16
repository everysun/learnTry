package com.test.booktest.messenger;

import com.test.booktest.R;
import com.test.booktest.R.layout;
import com.test.booktest.model.User;
import com.test.booktest.utils.MyConstants;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.TextView;

public class MessengerActivity extends Activity{
    private static final String TAG = "MessengerActivity";

    private Messenger mService;
    private Messenger mGetReplyMessenger = new Messenger(new MessengerHandler());

    private static TextView textView = null;

    private static class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                case MyConstants.MSG_FROM_SERVICE:
                    Log.i(TAG, "receive msg from Service: " + msg.getData().getString("reply"));
                    textView.setText("receive msg from Service: " + msg.getData().getString("reply"));
                    break;
                default:
                    super.handleMessage(msg);

            }
        }
    }

    private ServiceConnection mConnection = new ServiceConnection(){
        public void onServiceConnected(ComponentName className, IBinder service){
            mService = new Messenger(service);
            Log.d(TAG, "bind service");

            Message msg = Message.obtain(null, MyConstants.MSG_FROM_CLIENT);
            Bundle data = new Bundle();
            data.putString("msg", "hello, this is client.");
            msg.setData(data);
            msg.replyTo = mGetReplyMessenger;

            try{
                mService.send(msg);
            }catch(RemoteException e){
                e.printStackTrace();
            }
        }

        public void onServiceDisconnected(ComponentName className){

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_messenger);

        textView = findViewById(R.id.textView1);
        //Intent intent = new Intent("com.test.testbook.MessengerService.launch");
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);


    }

    @Override
    protected void onDestroy(){
        unbindService(mConnection);

        super.onDestroy();
    }
}
