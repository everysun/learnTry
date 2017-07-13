package com.example.test.mydesignpattern.DictProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.test.mydesignpattern.R;

import java.util.List;
import java.util.Map;

public class ResultActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);

        ListView listview = (ListView)findViewById(R.id.show);
        Intent intent = getIntent();
        Bundle data = intent.getExtras();

        @SuppressWarnings("unchecked")
        List<Map<String,String>> list = (List<Map<String,String>>)data.getSerializable("data");
        SimpleAdapter adapter = new SimpleAdapter(ResultActivity.this, list,
                R.layout.line, new String[]{"word", "detail"}, new int[]{R.id.word, R.id.detail});

        listview.setAdapter(adapter);
    }

}
