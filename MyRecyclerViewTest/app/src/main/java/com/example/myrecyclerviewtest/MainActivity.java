package com.example.myrecyclerviewtest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myrecyclerviewtest.beans.Datas;
import com.example.myrecyclerviewtest.beans.ItemBean;
import com.example.myrecyclerviewtest.adapters.RecyclerViewBaseAdapter;
import com.example.myrecyclerviewtest.adapters.ListViewAdapter;
import com.example.myrecyclerviewtest.adapters.GridViewAdapter;
import com.example.myrecyclerviewtest.adapters.StaggerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView mList;
    private List<ItemBean> mDatas;
    private RecyclerViewBaseAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList = (RecyclerView)this.findViewById(R.id.recycler_view);
        mRefreshLayout = (SwipeRefreshLayout)this.findViewById(R.id.refresh_layout);

        initData();
        showList(true, false); //RecyclerView默认显示样式
        handlerDownPullUpdate(); //下拉刷新
    }

    private void handlerDownPullUpdate(){
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        mRefreshLayout.setEnabled(true);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ItemBean data = new ItemBean();
                data.title = "new data.......";
                data.icon = R.mipmap.pic_09;
                mDatas.add(0, data);

                //模拟数据请求
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        mAdapter.notifyDataSetChanged(); //通知RecyclerView更新列表
                        mRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
    }


    private void initListener(){
        mAdapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(int position){
                Toast.makeText(MainActivity.this, "点击的是第" + position +"个条目", Toast.LENGTH_SHORT).show();
            }
        });

        //加载更多
        if(mAdapter instanceof ListViewAdapter){
            ((ListViewAdapter)mAdapter).setOnRefresListener(new ListViewAdapter.OnRefreshListener(){
                @Override
                public void onUpPullRefresh(final ListViewAdapter.LoaderMoreHolder loaderMoreHolder){
                    new Handler().postDelayed(new Runnable(){
                        @Override
                        public void run() {
                            Random random = new Random();
                            if (random.nextInt() % 2 == 0) {
                                ItemBean data = new ItemBean();
                                data.title = "new data.....";
                                data.icon = R.mipmap.pic_12;
                                mDatas.add(data);

                                mAdapter.notifyDataSetChanged();
                                loaderMoreHolder.update(loaderMoreHolder.LOADER_STATE_NORMAL);

                            } else{
                                loaderMoreHolder.update(loaderMoreHolder.LOADER_STATE_RELOAD);
                            }
                        }
                    },3000);
                }
            });
        }
    }

    private void initData(){
        mDatas = new ArrayList<>();

        for(int i=0; i<Datas.icons.length; ++i){
            ItemBean data = new ItemBean();
            data.icon = Datas.icons[i];
            data.title = "这是第" + i + "个系目";
            mDatas.add(data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();

        switch (itemId) {

            //ListView的部分
            case R.id.list_view_vertical_stander:
                Log.d(TAG, "点击了ListView里头的垂直标准");
                showList(true, false);
                break;
            case R.id.list_view_vertical_reverse:
                Log.d(TAG, "点击了ListView里头的垂直反向");
                showList(true, true);
                break;
            case R.id.list_view_horizontal_stander:
                Log.d(TAG, "点击了ListView里头的水平标准");
                showList(false, false);
                break;
            case R.id.list_view_horizontal_reverse:
                Log.d(TAG, "点击了ListView里头的水平反向");
                showList(false, true);
                break;

            //GridView部分
            case R.id.grid_view_vertical_stander:
                showGrid(true, false);
                break;
            case R.id.grid_view_vertical_reverse:
                showGrid(true, true);
                break;
            case R.id.grid_view_horizontal_stander:
                showGrid(false, false);
                break;
            case R.id.grid_view_horizontal_reverse:
                showGrid(false, true);
                break;

            //瀑布流部分
            case R.id.stagger_view_vertical_stander:
                showStagger(true, false);
                break;
            case R.id.stagger_view_vertical_reverse:
                showStagger(true, true);
                break;
            case R.id.stagger_view_horizontal_stander:
                showStagger(false, false);
                break;
            case R.id.stagger_view_horizontal_reverse:
                showStagger(false, true);
                break;

            // 多种条目类型被点击了
            case R.id.more_type:

                //跳到一个新的Activity里面去实现这个功能
                Intent intent = new Intent(this, MoreTypeActivity.class);
                startActivity(intent);
                break;

        }

        return super.onOptionsItemSelected(item);
    }


    private void showList(boolean isVertical, boolean isReverse){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        layoutManager.setOrientation(isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
        layoutManager.setReverseLayout(isReverse);

        mList.setLayoutManager(layoutManager);
        mAdapter = new ListViewAdapter(mDatas);
        mList.setAdapter(mAdapter);

        initListener();
    }

    private void showStagger(boolean isVertical, boolean isReverse) {
        //准备布局管理器
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, isVertical ? StaggeredGridLayoutManager.VERTICAL : StaggeredGridLayoutManager.HORIZONTAL);
        //设置布局管理器的方向
        layoutManager.setReverseLayout(isReverse);

        //设置布局管理器到RecyclerView里
        mList.setLayoutManager(layoutManager);

        //创建适配器
        mAdapter = new StaggerAdapter(mDatas);

        //设置适配器
        mList.setAdapter(mAdapter);

        //初始化事件
        initListener();

    }

    /**
     * 这个方法用于实现和GridView一样的效果
     */
    private void showGrid(boolean isVertical, boolean isReverse) {

        //创建布局管理器
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        //设置水平还是垂直
        layoutManager.setOrientation(isVertical ? GridLayoutManager.VERTICAL : GridLayoutManager.HORIZONTAL);
        //设置标准(正向)还是反向的
        layoutManager.setReverseLayout(isReverse);

        //设置布局管理器
        mList.setLayoutManager(layoutManager);

        //创建适配器
        mAdapter = new GridViewAdapter(mDatas);

        //设置适配器
        mList.setAdapter(mAdapter);

        //初始化事件
        initListener();
    }


}
