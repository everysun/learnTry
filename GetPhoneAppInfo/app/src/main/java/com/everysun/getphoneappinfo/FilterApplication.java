package com.everysun.getphoneappinfo;

/**
 * Created by everysun on 16/12/05.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FilterApplication extends Activity implements OnClickListener {

    private Button btallapp;
    private Button btsystemapp;
    private Button btthirdapp;
    private Button btsdcardapp;

    private int filter = MainActivity.FILTER_ALL_APP;

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);

        btallapp = (Button)findViewById(R.id.btallapp);
        btsystemapp = (Button)findViewById(R.id.btsystemapp);
        btthirdapp = (Button)findViewById(R.id.btthirdapp);
        btsdcardapp = (Button)findViewById(R.id.btsdcardapp);

        btallapp.setOnClickListener(this);
        btsystemapp.setOnClickListener(this);
        btthirdapp.setOnClickListener(this);
        btsdcardapp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        System.out.println(" " + view.getId());

        switch(view.getId()){
            case R.id.btallapp:
                filter = MainActivity.FILTER_ALL_APP;
                break;
            case R.id.btsystemapp:
                filter = MainActivity.FILTER_SYSTEM_APP;
                break;
            case R.id.btthirdapp:
                filter = MainActivity.FILTER_THIRD_APP;
                break;
            case R.id.btsdcardapp:
                filter = MainActivity.FILTER_SDCARD_APP;
                break;
            default:
                filter = -1;
        }

        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("filter", filter);
        startActivity(intent);
    }

}
