package com.example.test.pullrefresh;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.pulllibrary.listener.OnLoadListener;
import com.example.test.pulllibrary.listener.OnRefreshListener;
import com.example.test.pulllibrary.scroller.impl.RefreshGridView;
import com.example.test.pulllibrary.scroller.impl.RefreshListView;
import com.example.test.pulllibrary.scroller.impl.RefreshTextView;
import com.example.test.pulllibrary.swipe.RefreshLayout;
import com.example.test.pulllibrary.swipe.RefreshLvLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {

    public static final int REFRESH_LV = 1;
    public static final int REFRESH_GV = 2;
    public static final int REFRESH_TV = 3;
    public static final int REFRESH_SLIDE_LV = 4;
    public static final int SWIPE_LV = 5;

    final List<String> dataStrings = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_show);

        int index = 0;
        Bundle extraBundle = getIntent().getExtras();
        if(extraBundle != null && extraBundle.containsKey("index")){
            index = extraBundle.getInt("index");
        }

        for(int i=0; i<20; ++i){
            dataStrings.add("item - " + i);
        }

        switch(index){
            case REFRESH_LV:
                setListView();
                break;
            case REFRESH_GV:
                setGridView();
                break;
            case REFRESH_TV:
                setTextView();
                break;
            case REFRESH_SLIDE_LV:
                //setSlideListView();
                break;
            case SWIPE_LV:
                setSwipeRefreshListView();
                break;
            default:
                break;
        }
    }

    private void setListView(){
        final RefreshListView refreshLayout = new RefreshListView(this);

        refreshLayout.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, dataStrings));

        refreshLayout.setOnRefreshListener(new OnRefreshListener(){
            @Override
            public void onRefresh(){
                Toast.makeText(getApplicationContext(), "refreshing", Toast.LENGTH_SHORT)
                        .show();

                refreshLayout.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        refreshLayout.refreshComplete();
                    }
                }, 1500);
            }
        });

        refreshLayout.setOnLoadListener(new OnLoadListener(){
            @Override
            public void onLoadMore(){
                Toast.makeText(getApplicationContext(), "loading", Toast.LENGTH_SHORT)
                        .show();

                refreshLayout.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        refreshLayout.loadCompelte();
                    }
                },1500);
            }
        });

        setContentView(refreshLayout);
    }

    private void setGridView(){
        final RefreshGridView gv = new RefreshGridView(this);
        String[] dataStrings = new String[20];
        for(int i=0; i<dataStrings.length; ++i){
            dataStrings[i] = "item - "+ i;
        }

        gv.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, dataStrings));

        gv.setOnRefreshListener(new OnRefreshListener(){
            @Override
            public void onRefresh(){
                Toast.makeText(getApplicationContext(), "refreshing", Toast.LENGTH_SHORT)
                        .show();
                gv.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        gv.refreshComplete();
                    }
                },1500);
            }
        });

        gv.setOnLoadListener(new OnLoadListener(){
            @Override
            public void onLoadMore(){
                Toast.makeText(getApplicationContext(), "loading", Toast.LENGTH_SHORT)
                        .show();

                gv.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        gv.loadCompelte();
                    }
                }, 1500);

            }
        });

        setContentView(gv);
    }

    private void setTextView(){
        final RefreshTextView refreshTextView = new RefreshTextView(this);
        final TextView tv = refreshTextView.getContentView();
        tv.setText("下拉更新时间的TextView");
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(30f);
        tv.setBackgroundColor(Color.YELLOW);

        refreshTextView.setOnRefreshListener(new OnRefreshListener(){
            @Override
            public void onRefresh() {
                refreshTextView.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        SimpleDateFormat sdf = (SimpleDateFormat)SimpleDateFormat.getInstance();
                        sdf.applyPattern("yyyy-MM-dd hh:mm:ss");
                        refreshTextView.getContentView().setText("下拉更新时间TextView" +
                                System.getProperty("line.separator")+sdf.format(new Date()));
                        refreshTextView.refreshComplete();
                    }
                }, 1500);
            }
        });

        setContentView(refreshTextView);
    }


    private void setSwipeRefreshListView(){
        setContentView(R.layout.swipe_lv_layout);

        final RefreshLvLayout refreshLvLayout = (RefreshLvLayout)findViewById(R.id.swipe_layout);
        refreshLvLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh(){
                Toast.makeText(getApplicationContext(),"on refresh", Toast.LENGTH_SHORT).show();
                refreshLvLayout.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        refreshLvLayout.setRefreshing(false);
                    }
                },1500);
            }
        });

        refreshLvLayout.setOnLoadListener(new RefreshLayout.OnLoadListener(){
            @Override
            public void onLoad(){
                Toast.makeText(getApplicationContext(), "on load 1.5秒", Toast.LENGTH_SHORT).show();
                refreshLvLayout.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        refreshLvLayout.setLoading(false);
                    }
                },1500);
            }
        });

        final ListView listView = (ListView)findViewById(R.id.swipe_listview);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                dataStrings));
    }
}
