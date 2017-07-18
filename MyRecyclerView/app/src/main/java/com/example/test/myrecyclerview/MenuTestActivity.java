package com.example.test.myrecyclerview;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.example.test.myrecyclerview.common.logger.Log;

public class MenuTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_test);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        Log.i("######", "############ create menu ##############");

        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }
}
