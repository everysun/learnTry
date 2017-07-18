package com.example.test.myrecyclerview.common.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.test.myrecyclerview.R;
import com.example.test.myrecyclerview.common.logger.Log;
import com.example.test.myrecyclerview.common.logger.LogWrapper;



public class SampleActivityBase extends FragmentActivity {
    public static final String TAG = "SampleActivityBase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_sample_base2);
    }

    @Override
    protected void onStart(){
        super.onStart();
        initializeLogging();
    }

    public void initializeLogging(){
        LogWrapper logWrapper  = new LogWrapper();
        Log.setLogNode(logWrapper);
        Log.i(TAG, "Ready");
    }
}
