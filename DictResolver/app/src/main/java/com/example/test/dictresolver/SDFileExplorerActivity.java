package com.example.test.dictresolver;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SDFileExplorerActivity extends Activity{
    ListView listView = null;
    TextView textView = null;

    File currentParent;
    File[] currentFiles;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        listView = (ListView)findViewById(R.id.list);
        textView = (TextView)findViewById(R.id.path);

        File root = new File("/mnt/sdcard/");

        if(root.exists()){
            currentParent = root;
            currentFiles = root.listFiles();

            inflateListView(currentFiles);
        }

        // 为ListView的列表项的单击事件绑定监听器
        listView.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                // 用户单击了文件，直接返回，不做任何处理
                if (currentFiles[position].isFile()) return;
                // 获取用户点击的文件夹下的所有文件
                File[] tmp = currentFiles[position].listFiles();
                if (tmp == null || tmp.length == 0)
                {
                    Toast.makeText(SDFileExplorerActivity.this
                            , "当前路径不可访问或该路径下没有文件",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // 获取用户单击的列表项对应的文件夹，设为当前的父文件夹
                    currentParent = currentFiles[position]; // ②
                    // 保存当前的父文件夹内的全部文件和文件夹
                    currentFiles = tmp;
                    // 再次更新ListView
                    inflateListView(currentFiles);
                }
            }
        });

        Button parent = (Button)findViewById(R.id.parent);
        parent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(!currentParent.getCanonicalPath().equals("/mnt/sdcard")){
                        currentParent = currentParent.getParentFile();
                        currentFiles = currentParent.listFiles();
                        inflateListView(currentFiles);
                    }
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void inflateListView(File[] files){
        ArrayList<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();

        for(int i=0; i<files.length; ++i){
            Map<String, Object> listItem =  new HashMap<String, Object>();

            if(files[i].isDirectory()){
                listItem.put("icon", R.drawable.folder);
            }else{
                listItem.put("icon", R.drawable.file);
            }
            listItem.put("fileName", files[i].getName());
            listItems.add(listItem);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.line2,
                new String[]{"icon", "fileName"}, new int[]{R.id.icon, R.id.file_name});

        listView.setAdapter(simpleAdapter);

        try{
            textView.setText("当前路径：" + currentParent.getCanonicalPath());
        }catch(IOException e){
            e.printStackTrace();
        }
    }


}
