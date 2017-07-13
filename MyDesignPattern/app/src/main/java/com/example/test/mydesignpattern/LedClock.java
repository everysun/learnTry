package com.example.test.mydesignpattern;


import android.appwidget.AppWidgetProvider;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.util.Log;
import android.os.Handler;
import android.os.Message;
import android.widget.RemoteViews;
import android.content.ComponentName;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;

public class LedClock extends AppWidgetProvider {
    private static final String TAG = "###   myWidget   ###";
    private Timer timer = new Timer();
    private  AppWidgetManager appWidgetManager;
    private Context context;

    private final Intent EXAMPLE_SERVICE_INTENT = new Intent("android.appwidget.action.EXAMPLE_APP_WIDGET_SERVICE");
    private final String ACTION_UPDATE_ALL = "com.example.test.mydesignpattern.widget.UPDATE_ALL";


    private int[] digits = new int[]{
            R.mipmap.su01,
            R.mipmap.su02,
            R.mipmap.su03,
            R.mipmap.su04,
            R.mipmap.su05,
            R.mipmap.su06,
            R.mipmap.su07,
            R.mipmap.su08,
            R.mipmap.su09,
            R.mipmap.su10
    };

    private int[] digitViews = new int[]{
            R.id.img01,
            R.id.img02,
            R.id.img04,
            R.id.img05,
            R.id.img07,
            R.id.img08
    };

    @Override
    public void onEnabled(Context context){
        Log.d(TAG, "------------onEnabled-----------");
        EXAMPLE_SERVICE_INTENT.setPackage(context.getPackageName());
        context.startService(EXAMPLE_SERVICE_INTENT);
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context){
        Log.d(TAG, "------------onDisabled-----------");
        context.stopService(EXAMPLE_SERVICE_INTENT);
        super.onDisabled(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        Log.d(TAG,"widget ------onUpdate------");
        this.appWidgetManager = appWidgetManager;
        this.context = context;

        /*
        timer = new Timer();
        timer.schedule(new TimerTask(){
            public void run(){
                handler.sendEmptyMessage(0x123);
            }
        },0,1000); */
    }

    @Override
    public void onReceive(Context context, Intent intent){
        final String action = intent.getAction();
        Log.d(TAG, "OnReceitve:Action: " + action);
        appWidgetManager = AppWidgetManager.getInstance(context);

        if(ACTION_UPDATE_ALL.equals(action)){
            RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.main);

            SimpleDateFormat df = new SimpleDateFormat("HHmmss");
            String timeStr = df.format(new Date());
            Log.d(TAG, timeStr);
            for(int i=0; i<timeStr.length(); ++i){
                int num = timeStr.charAt(i)-48;
                views.setImageViewResource(digitViews[i], digits[num]);
            }

            ComponentName componentName = new ComponentName(context, LedClock.class);
            appWidgetManager.updateAppWidget(componentName, views);
        }

        super.onReceive(context, intent);

    }

    /*private Handler handler = new Handler(){
        public void  handlerMessage(Message msg){
            if(msg.what == 0x123){
                RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.main);

                SimpleDateFormat df = new SimpleDateFormat("HHmmss");
                String timeStr = df.format(new Date());
                Log.d("####  myWidget  ###", timeStr);
                for(int i=0; i<timeStr.length(); ++i){
                    int num = timeStr.charAt(i)-48;
                    views.setImageViewResource(digitViews[i], digits[num]);
                }

                ComponentName componentName = new ComponentName(context, LedClock.class);
                appWidgetManager.updateAppWidget(componentName, views);
            }
            super.handleMessage(msg);
        }
    };*/
}
