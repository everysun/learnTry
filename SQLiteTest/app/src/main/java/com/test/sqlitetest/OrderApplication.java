package com.test.sqlitetest;

import android.app.Application;
import android.util.Log;



public class OrderApplication extends Application {
    @Override
    public  void onCreate(){
        Log.e("Application!! ", "###############Application onCreate() start *******");
        super.onCreate();
        if(!OrderContext.isInitialized()){
            OrderContext.init(getApplicationContext());
        }

        CrashHandler crashHandler = CrashHandler.getsInstance();
        crashHandler.init(this);




        Log.e("Application!! ", "###############Application onCreate() end *******");
    }
}
