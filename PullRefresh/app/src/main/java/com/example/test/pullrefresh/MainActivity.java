package com.example.test.pullrefresh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.test.pulllibrary.base.impl.PullRefreshListView;


public class MainActivity extends AppCompatActivity {
    ListView mListView;
    PullRefreshListView mPullRefreshListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pull_main);

        findViewById(R.id.refresh_listview).setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                intent.putExtra("index", ShowActivity.REFRESH_LV);
                startActivity(intent);
            }
        });

        findViewById(R.id.refresh_gridview).setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                intent.putExtra("index", ShowActivity.REFRESH_GV);
                startActivity(intent);
            }
        });

        findViewById(R.id.refresh_textview).setOnClickListener(new OnClickListener(){
           @Override
            public void onClick(View v){
               Intent intent = new Intent(MainActivity.this, ShowActivity.class);
               intent.putExtra("index", ShowActivity.REFRESH_TV);
               startActivity(intent);
           }
        });

        findViewById(R.id.refresh_slid_lv).setOnClickListener(new OnClickListener(){
           @Override
            public void onClick(View v){
               Intent intent = new Intent(MainActivity.this, ShowActivity.class);
               intent.putExtra("index", ShowActivity.REFRESH_SLIDE_LV);
               startActivity(intent);
           }
        });

        findViewById(R.id.swipe_refresh_lv).setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                intent.putExtra("index", ShowActivity.SWIPE_LV);
            }
        });
    }
}
