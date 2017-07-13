package com.example.test.dictresolver;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.example.test.dictresolver.content.Words;
import com.example.test.dictresolver.content.Person;
import com.example.test.dictresolver.content.Pen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;


public class MainActivity extends AppCompatActivity {
    ContentResolver contentResolver;
    Button insert = null;
    Button search = null;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    final String FILE_NAME = "example.bin";
    final String SD_FILE_NAME = "/example.bin";

    Button btnSdFileExplorer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println(new StringBuilder("a").append("b").append("c").toString());

        contentResolver = getContentResolver();
        insert = (Button)findViewById(R.id.insert);
        search = (Button)findViewById(R.id.search);

        preferences = getSharedPreferences("example", MODE_PRIVATE);
        editor = preferences.edit();
        Button read = (Button)findViewById(R.id.read);
        Button write = (Button)findViewById(R.id.write);
        final EditText edit1 = (EditText)findViewById(R.id.edit1);
        final EditText edit2 = (EditText)findViewById(R.id.edit2);

        insert.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View source){
                String word = ((EditText) findViewById(R.id.word))
                        .getText().toString();
                String detail = ((EditText) findViewById(R.id.detail))
                        .getText().toString();

                ContentValues values = new ContentValues();
                values.put(Words.Word.WORD, word);
                values.put(Words.Word.DETAIL, detail);

                contentResolver.insert(Words.Word.DICT_CONTENT_URI, values);
                Toast.makeText(MainActivity.this, "添加生词成功",Toast.LENGTH_LONG).show();
            }
        });


        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = ((EditText) findViewById(R.id.key))
                        .getText().toString();

                Cursor cursor = contentResolver.query(
                        Words.Word.DICT_CONTENT_URI, null,
                        "word like ? or detail like ?", new String[]{"%" + key +"%", "%" + key +"%" },
                        null);


                Bundle data = new Bundle();
                data.putSerializable("data", converCursorToList(cursor));
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtras(data);

                //实现Serializable和Parcelable两种方式在Intent中传递类对象
                Person person = new Person();
                person.setName("Serializable测试");
                person.setAge(22);
                intent.putExtra("putExtraPerson", person);
                data.putSerializable("putBundlePerson", person);
                intent.putExtras(data);

                Pen pen = new Pen("RED", 18);
                intent.putExtra("putExtraParcel", pen);
                data.putParcelable("putParcelable", pen);
                intent.putExtras(data);

                Bundle bundle = new Bundle();
                bundle.putParcelable("putParcelable2", pen);
                intent.putExtra("putExtraParcel2", bundle);

                startActivity(intent);
            }
        });


        read.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = preferences.getString("time", null);
                int randNum = preferences.getInt("random", 0);

                String result = time==null ? "未写入数据":"写时间为: " + time
                        + "\n上次生成的随机数为: " + randNum;

                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();

                //读取文件
                edit2.setText(read());
            }
        });


        write.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat  sdf = new SimpleDateFormat("yyyy年MM月dd日" + "hh:mm:ss");

                editor.putString("time", sdf.format(new Date()));
                editor.putInt("random", (int)(Math.random()*100));
                editor.commit();

                //写文件
                write(edit1.getText().toString());
                edit1.setText("");
            }
        });

        btnSdFileExplorer = (Button)findViewById(R.id.sdfileexplorer);
        btnSdFileExplorer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SDFileExplorerActivity.class);
                startActivity(intent);
            }
        });
    }

    private ArrayList<Map<String,String>> converCursorToList(Cursor cursor){
        ArrayList<Map<String,String>> result = new ArrayList<>();

        while(cursor.moveToNext()){
            Map<String, String> map = new HashMap<>();

            map.put(Words.Word.WORD, cursor.getString(1));
            map.put(Words.Word.DETAIL, cursor.getString(2));
            result.add(map);
        }
        return result;
    }


    private String read(){
        try{

            StringBuilder sb = new StringBuilder("");
            //sdcard
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                File sdCardDir = Environment.getExternalStorageDirectory();
                System.out.println("==============" + sdCardDir);
                FileInputStream fis = new FileInputStream(sdCardDir.getCanonicalPath()+SD_FILE_NAME);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));


                String line = null;

                while((line = br.readLine()) != null){
                    sb.append(line);
                }
                sb.append("===SDCard end===     ");
                br.close();
            }

            FileInputStream fis = openFileInput(FILE_NAME);
            byte[] buff = new byte[1024];
            int hasRead = 0;
            //sb = new StringBuilder("");

            while((hasRead = fis.read(buff)) > 0){
                sb.append(new String(buff, 0, hasRead));
            }

            fis.close();
            return sb.toString();

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private void write(String content){
        try{

            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_APPEND);
            PrintStream ps = new PrintStream(fos);
            ps.println(content);
            ps.close();

            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                File sdCardDir = Environment.getExternalStorageDirectory();
                File targetFile = new File(sdCardDir.getCanonicalPath() + SD_FILE_NAME);

                System.out.println("==============#######" + sdCardDir);

                RandomAccessFile raf = new RandomAccessFile(targetFile, "rw");
                raf.seek(targetFile.length());
                raf.write(content.getBytes());

                raf.close();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
