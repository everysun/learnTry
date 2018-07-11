package com.test.testschultegrid;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "Main";
    private GridLayout gridLayout;
    private int colCount;
    private GridItemClick gridItemClick = new GridItemClick();
    private String difficulty;
    private Long startTime;
    private int bill;
    private int nextExpected = 1;

    private TextView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences sp = this.getSharedPreferences(Constant.SETTING, MODE_PRIVATE);
        String currSize = sp.getString(Constant.SIZE, "");
        String currdifficulty = sp.getString(Constant.DIFFICULTY, "");
        colCount = Integer.parseInt(currSize.substring(0,1));
        difficulty = currdifficulty;

        result = findViewById(R.id.result);
        gridLayout = findViewById(R.id.grid);

    }


    private void initGrid(int colCount){
        gridLayout.removeAllViews();
        gridLayout.setColumnCount(colCount);
        gridLayout.setVisibility(View.VISIBLE);

        ArrayList<Integer> list = new ArrayList<>();
        list.add(0);

        RelativeLayout layout = findViewById(R.id.root_layout);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int layoutWidth = layout.getWidth();

        //density  ：  其实是 DPI / (160像素/英寸) 后得到的值
        //int itemSquareLen = (layoutWidth -(metrics.densityDpi/160) *colCount*2)/colCount;
        int itemSquareLen = (layoutWidth -(metrics.densityDpi/160) *colCount)/colCount;

        int index = 0;
        for(int i=0; i<colCount; i++){
            for(int j=0; j<colCount; j++){
                Random random = new Random();
                while(list.contains(index)){
                    index = random.nextInt(colCount * colCount+1);
                }
                list.add(index);

                String indexStr = String.valueOf(index);

                Button btn = (Button) Tpl.instance(this, R.layout.tpl, R.id.grid_item_btn);
                ViewGroup.LayoutParams layoutParams = btn.getLayoutParams();
                layoutParams.height = itemSquareLen;
                layoutParams.width = itemSquareLen;

                btn.setLayoutParams(layoutParams);
                btn.setText(indexStr);
                btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 37-(colCount-3)*5);
                btn.setOnClickListener(gridItemClick);

                gridLayout.addView(btn);

            }
        }

    }


    public void onReady(View view){
        view.setVisibility(View.GONE);
        initGrid(colCount);
        startTime = System.currentTimeMillis();
        bill = 0;

        result.setVisibility(View.GONE);
    }


    public void onFinish(){
        Button btn = findViewById(R.id.ready_btn);

        btn.setVisibility(View.VISIBLE);
        btn.setText(getString(R.string.again));
        gridLayout.setVisibility(View.GONE);

        long endTime = System.currentTimeMillis();
        float ms = (float)(endTime - startTime + bill)/1000;

        result.setText(ms + "s");
        result.setVisibility(View.VISIBLE);
        nextExpected = 1;

        DB db = DB.getInstance(this);
        db.record(ms);
    }


    public class GridItemClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Button btn = (Button) v;
            String indexStr = (String)btn.getText();
            int index = Integer.parseInt(indexStr);

            if(nextExpected == index){
                nextExpected++;

                if(nextExpected == colCount*colCount+1){
                    onFinish();
                }

                if(difficulty.equals(getString(R.string.difficulty_easy))){
                    btn.setBackgroundColor(Color.GRAY);
                }
            }else{
                if(difficulty.equals(getString(R.string.difficulty_hard))){
                    bill += 100;
                }

            }
        }
    }




    public static class Tpl{
        public static View instance(Context ctx, int layoutId, int resId){
            LayoutInflater inflater = LayoutInflater.from(ctx);
            View view = inflater.inflate(layoutId, null);
            ViewGroup root = (ViewGroup)view.getRootView();
            View result = root.findViewById(resId);
            root.removeAllViews();

            return result;

        }
    }
}
