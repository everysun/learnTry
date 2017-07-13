package com.example.test.mydesignpattern;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ExampleAppWidgetService extends Service {
    private static final String TAG="ExampleAppWidgetService";

    private final String ACTION_UPDATE_ALL = "com.example.test.mydesignpattern.widget.UPDATE_ALL";
    private static final int UPDATE_TIME = 1000;
    private UpdateThread mUpdateThread;
    private Context mContext;

    @Override
    public void onCreate(){
        Log.d(TAG, "widgetService ----- onCreate------");
        mUpdateThread = new UpdateThread();
        mUpdateThread.start();
        mContext = this.getApplicationContext();

        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d(TAG, "widgetService ----- onStartCommand------");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        if(mUpdateThread != null){
            mUpdateThread.interrupt();
        }

        super.onDestroy();
    }

    private class UpdateThread extends Thread{
        @Override
        public void run(){
            super.run();
            try{
                while(true){
                    Log.d(TAG, "run ...........");

                    Intent updateIntent = new Intent();
                    updateIntent.setAction(ACTION_UPDATE_ALL);
                    updateIntent.setPackage(mContext.getPackageName());
                    mContext.sendBroadcast(updateIntent);

                    Thread.sleep(UPDATE_TIME);
                }

            }catch(InterruptedException e){
                e.printStackTrace();
            }

        }
    }

}
