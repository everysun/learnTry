package com.test.testschultegrid;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

public class HistoryActivity extends Activity {

    private final String TAG = "History";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);

        Constant.init(this);
        DB db = DB.getInstance(this);
        ArrayList<DB.HistoryItem> historyItems = db.getHistory();
        ArrayMap<String, String> bestRecords = db.getBest();

        LinearLayout scrollView = findViewById(R.id.history_best_container);

        for(int i=0; i<Constant.sizes.length; i++){
            View view = LayoutInflater.from(this).inflate(R.layout.history_best_item, null);
            TextView record = view.findViewById(R.id.history_best_item_record);
            TextView type = view.findViewById(R.id.history_best_item_type);

            String best = bestRecords.get(Constant.sizes[i]);

            if(best != null){
                record.setText(best);
            }else{
                record.setText("无记录");
            }

            type.setText(Constant.sizes[i]);
            scrollView.addView(view);

        }


        TableLayout table = findViewById(R.id.history_table);
        Iterator<DB.HistoryItem> iterator = historyItems.iterator();
        DB.HistoryItem item = null;

        while(iterator.hasNext()){
            item = iterator.next();
            View view = MainActivity.Tpl.instance(this, R.layout.tpl, R.id.history_table_row);
            TextView difficultyText = view.findViewById(R.id.history_table_row_difficulty);
            TextView sizeText = view.findViewById(R.id.history_table_row_size);
            TextView timeText = view.findViewById(R.id.history_table_row_time);
            TextView scoreText = view.findViewById(R.id.history_table_row_score);

            difficultyText.setText(item.getDifficulty());
            sizeText.setText(item.getSize());
            timeText.setText(item.getTime());
            scoreText.setText(String.valueOf(item.getScore()) + 's');
            table.addView(view);
        }
    }
}
