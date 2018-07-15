package com.test.schultegrid;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;
import java.util.ArrayList;


public class GameLayout extends Activity implements View.OnClickListener{

    private final String TAG = "###Game";
    private LinearLayout root;
    private WrongAction wrongAction = new WrongAction();
    public Button button;
    private int time = 0;
    private TextView textView;
    final Handler handler = new Handler();
    private int blockNum = 1, width, height;
    private final String BLOCKNUM = "blocknum";
    private final String SCORE = "score";
    private int numberInBlock = 0;
    private int nextExpected = 1;

    private LinearLayout.LayoutParams rootconfig;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        blockNum = intent.getIntExtra(BLOCKNUM, 5);

        wrongAction.setDuration(500);

        Runnable runnable = new Runnable(){
            @Override
            public void run(){
                //Log.i(TAG, "######begin  In Runnable: "+ Thread.currentThread().getName());
                time++;
                handler.postDelayed(this, 1000);
                //textView.setGravity(Gravity.CENTER);
                textView.setText("已用时：" + time +"秒");
                //Log.i(TAG, "######end  In Runnable: "+ Thread.currentThread().getName());
            }
        };
        Log.i(TAG, "######begin  In Activity: "+ Thread.currentThread().getName());
        handler.postDelayed(runnable, 1000);
        Log.i(TAG, "######end  In Activity: "+ Thread.currentThread().getName());

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;

        root = new LinearLayout(this);
        root.setBackgroundColor(getResources().getColor(R.color.seagreen));
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(10,10,10,10);

        rootconfig = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        //rootconfig.gravity = Gravity.CENTER;
        rootconfig.weight = 1;
        rootconfig.setMargins(5,5,5, 5);

        setContentView(root);

        textView = new TextView(this);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER);

        root.addView(textView, rootconfig);//计时器

        CreateGrid(blockNum);

    }


    @Override
    public void onClick(View v) {
        Button btn = (Button)v;
        int index = Integer.parseInt((String)btn.getText());
        int tmep = numberInBlock;

        if(nextExpected == index){  //确保顺序
            nextExpected++;
            if(nextExpected > blockNum*blockNum){
                Intent intent = new Intent(GameLayout.this, Win.class);
                intent.putExtra(SCORE, time);
                intent.putExtra(BLOCKNUM, blockNum);
                startActivity(intent);
                finish();
            }
        }else{
            v.startAnimation(wrongAction); //错误动画
        }
    }


    private void CreateGrid(int rowsAndCols){
        ArrayList<Integer> list  = new ArrayList<>();
        numberInBlock = 0;
        nextExpected = 1;
        list.add(numberInBlock);

        Random random = new Random();

        LinearLayout rowContain;
        LinearLayout.LayoutParams rlp;

        //Grid外围LinearLayout
        rowContain = new LinearLayout(this);
        rowContain.setOrientation(LinearLayout.VERTICAL);


        rlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        //rlp.gravity = Gravity.CENTER;
        rlp.weight = 1;


        for(int i=0; i<rowsAndCols; i++){
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);


            for(int j=0; j<rowsAndCols; j++){
                button = new Button(this);

                while(list.contains(numberInBlock)){
                    numberInBlock = random.nextInt(rowsAndCols*rowsAndCols + 1);
                }
                list.add(numberInBlock);

                //button.setBackground(getResources().getDrawable(R.drawable.button_change_color));
                button.setBackgroundResource(R.drawable.button_change_color);
                button.setText("" + numberInBlock); //转成字符串
                button.setTextSize(36);
                button.setTextColor(getResources().getColor(R.color.white));
                button.setOnClickListener(this);
                button.setGravity(Gravity.CENTER);

                //LinearLayout.LayoutParams buttonLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                //        height/rowsAndCols);
                LinearLayout.LayoutParams buttonLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                buttonLP.weight = 1;
                buttonLP.gravity = Gravity.CENTER;
                buttonLP.setMargins(5,5,5,5);

                row.addView(button, buttonLP);
            }

            rowContain.addView(row,rlp);
        }
        root.addView(rowContain, rootconfig);

    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "######begin  onDestroy !!!");
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();

        Log.i(TAG, "######end  onDestroy !!!");
    }
}
