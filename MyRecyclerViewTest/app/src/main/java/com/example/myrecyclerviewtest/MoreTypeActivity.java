package com.example.myrecyclerviewtest;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.myrecyclerviewtest.adapters.MoreTypeAdapter;
import com.example.myrecyclerviewtest.beans.Datas;
import com.example.myrecyclerviewtest.beans.MoreTypeBean;



public class MoreTypeActivity extends AppCompatActivity {

    private static final String TAG = "MoreTypeActivity";
    private RecyclerView mRecyclerView;
    private List<MoreTypeBean> mDatas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_type_activity);

        mRecyclerView = (RecyclerView)this.findViewById(R.id.more_type_list);
        mDatas = new ArrayList<>();

        initDatas();

        //RecyclerView布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        //RecyclerView适配器
        MoreTypeAdapter adapter = new MoreTypeAdapter(mDatas);
        mRecyclerView.setAdapter(adapter);

    }


    private void initDatas(){
        Random random = new Random();

        for(int i=0; i<Datas.icons.length; ++i){
            MoreTypeBean data = new MoreTypeBean();
            data.pic = Datas.icons[i];
            data.type = random.nextInt(3);
            Log.d(TAG, "type = " + data.type);
            mDatas.add(data);
        }
    }


}
