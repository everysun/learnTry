package com.test.booktest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.test.booktest.R;
import com.test.booktest.aidl.Book;
import com.test.booktest.utils.MyConstants;
import com.test.booktest.utils.MyUtils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView text_view = null;
    private Button start = null;
    private Button stop = null;

    Handler handler = new Handler();
    Runnable update_thread = new Runnable(){
      public void run(){
          text_view.append("\n UpdateThread...");
          handler.postDelayed(update_thread, 1000);
      }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_view = (TextView)findViewById(R.id.text_view);
        start = (Button)findViewById(R.id.start);
        stop = (Button)findViewById(R.id.stop);

        start.setOnClickListener(new StartClickListener());
        stop.setOnClickListener(new StopClickListener());
    }

    private class StartClickListener implements OnClickListener{
        public void onClick(View v){
            handler.post(update_thread);
        }
    }

    private class StopClickListener implements OnClickListener{
        public void onClick(View v){
            handler.removeCallbacks(update_thread);
        }
    }
}
