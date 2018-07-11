package com.test.testschultegrid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class SettingActivity extends Activity {

    private Button sizeBtn;
    private Button difficultyBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);

        Constant.init(this);

        sizeBtn = findViewById(R.id.size_btn);
        difficultyBtn = findViewById(R.id.difficulty_btn);

        SharedPreferences sp = this.getSharedPreferences(Constant.SETTING, MODE_PRIVATE);
        String currSize = sp.getString(Constant.SIZE, "");
        String currDifficulty = sp.getString(Constant.DIFFICULTY, "");

        if(currDifficulty.equals("") && currSize.equals("")){
            currDifficulty = Constant.difficulties[1];
            currSize = Constant.sizes[2];

            SharedPreferences.Editor editor = sp.edit();
            editor.putString(Constant.DIFFICULTY, currDifficulty);
            editor.putString(Constant.SIZE, currSize);
            editor.apply();
        }

        sizeBtn.setText(currSize);
        difficultyBtn.setText(currDifficulty);

        Button historyBtn = findViewById(R.id.history_btn);
        historyBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SettingActivity.this, HistoryActivity.class);
                startActivity(intent);

            }
        });


        Button aboutBtn = findViewById(R.id.about_btn);
        aboutBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setTitle("关于")
                        .setMessage("舒尔特方格是全世界范围内最简单，最有效也是最科学的注意力训练方法。寻找目标数字时，注意力是需要极度集中的，把这短暂的高强度的集中精力过程反复练习，大脑的集中注意力功能就会不断的加固，提高。注意水平越来越高。")
                        .show();
            }
        });
    }


    private void makeOptions(String title, final String type, final String[] options,
                             final OptionCallBack cb){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(title)
                .setItems(options, new DialogInterface.OnClickListener(){
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i){
                       SharedPreferences sp = SettingActivity.this.getSharedPreferences(Constant.SETTING, MODE_PRIVATE);
                       SharedPreferences.Editor editor = sp.edit();
                       editor.putString(type, options[i]);
                       editor.apply();
                       cb.call(options[i]);
                   }
                });

        alertDialog.show();

    }


    private interface OptionCallBack{
        void call(String result);
    }



    public void onChooseDifficulty(View view){
        makeOptions(getString(R.string.setting_difficulty), Constant.DIFFICULTY,
                Constant.difficulties, new OptionCallBack(){

                    @Override
                    public void call(String result) {
                        difficultyBtn.setText(result);

                        if(result.equals(getString(R.string.difficulty_hard)) ){
                            Toast.makeText(SettingActivity.this, "按错罚100ms", Toast.LENGTH_SHORT).show();
                        }

                        if(result.equals(getString(R.string.difficulty_easy))){
                            Toast.makeText(SettingActivity.this, "按对有馈", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void  onPlay(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }


    public void onChooseSize(View view){
        makeOptions(getString(R.string.setting_size), Constant.SIZE, Constant.sizes,
                new OptionCallBack(){
                    @Override
                    public void call(String result){
                        sizeBtn.setText(result);
                    }
                });
    }
}
