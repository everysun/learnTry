package com.test.schultegrid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerButton;
import com.romainpiel.shimmer.ShimmerTextView;


public class MainActivity extends AppCompatActivity {
    private Shimmer tv = new Shimmer();
    private ShimmerButton button33;
    private ShimmerButton button44;
    private ShimmerButton button55;
    private ShimmerButton button66;
    private ShimmerButton buttonExit;
    private ShimmerTextView shimmerTextView;
    private RelativeLayout.LayoutParams lp;
    private EditText editText;
    private int blockNum = 0;
    private final String BLOCKNUM = "blocknum";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);  //去掉标题栏 正常情况下requestWindowFeature(Window.FEATURE_NO_TITLE)是可以生效的，但是当Activity继承子AppCompatActivity的时候，这个就失效了
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        shimmerTextView = (ShimmerTextView)findViewById(R.id.titleTv);
        button33 = (ShimmerButton)findViewById(R.id.button33);
        button44 = (ShimmerButton)findViewById(R.id.button44);
        button55 = (ShimmerButton)findViewById(R.id.button55);
        button66 = (ShimmerButton)findViewById(R.id.button66);
        buttonExit = (ShimmerButton)findViewById(R.id.buttonExit);
        button33.setOnClickListener(new ButtonClick());
        button44.setOnClickListener(new ButtonClick());
        button55.setOnClickListener(new ButtonClick());
        button66.setOnClickListener(new ButtonClick());
        buttonExit.setOnClickListener(new ButtonClick());

        //editText = (EditText)findViewById(R.id.editText);
//
//        lp = (RelativeLayout.LayoutParams)button.getLayoutParams();
//        lp.setMargins(80,80,80,80);
//        button.setLayoutParams(lp);

        tv.setDuration(2000);
        tv.start(shimmerTextView);
        tv.start(button33);
        tv.start(button44);
        tv.start(button55);
        tv.start(button66);
        tv.start(buttonExit);


//        button.setOnClickListener(new View.OnClickListener(){
////            @Override
////            public void onClick(View v){
////                if(editText.getText().toString().equals("")){
////                    blockNum = 5;
////                    Intent intent = new Intent(MainActivity.this, GameLayout.class);
////                    intent.putExtra(BLOCKNUM, blockNum);
////                    startActivity(intent);
////                }else{
////                    Intent intent = new Intent(MainActivity.this, GameLayout.class);
////                    blockNum = Integer.parseInt(editText.getText().toString());
////                    intent.putExtra(BLOCKNUM, blockNum);
////                    startActivity(intent);
////                }
////            }
////        });
    }

    private void  startInfo(int num){
        blockNum = num;

        Intent intent = new Intent(MainActivity.this, GameLayout.class);
        intent.putExtra(BLOCKNUM, blockNum);
        startActivity(intent);
    }

    private class ButtonClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.button33:
                    startInfo(3);
                    break;
                case R.id.button44:
                    startInfo(4);
                    break;
                case R.id.button55:
                    startInfo(5);
                    break;
                case R.id.button66:
                    startInfo(6);
                    break;
                case R.id.buttonExit:
                    finish();
                    break;

            }
        }
    }
}
