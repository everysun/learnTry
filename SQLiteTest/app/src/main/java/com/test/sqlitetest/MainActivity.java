package com.test.sqlitetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import java.util.List;
import com.test.sqlitetest.utils.PressUtil;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "OrderActivity";
    private  OrderDao orderDao;
    private TextView showSQLMsg;
    private EditText inputSqlMsg;
    private ListView showDataListView;
    private List<Order> orderList;
    private OrderListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        orderDao = new OrderDao(this);
        if(!orderDao.isDataExist()){
            orderDao.initTable();
        }

        initComponent();
        orderList = orderDao.getAllData();
        if(orderList != null){
            adapter = new OrderListAdapter(this, orderList);
            showDataListView.setAdapter(adapter);
        }
    }

    private void initComponent(){
        Button executeButton = findViewById(R.id.executeButton);
        Button insertButton = (Button)findViewById(R.id.insertButton);
        Button deleteButton = (Button)findViewById(R.id.deleteButton);
        Button updateButton = (Button)findViewById(R.id.updateButton);
        Button query1Button = (Button)findViewById(R.id.query1Button);
        Button query2Button = (Button)findViewById(R.id.query2Button);
        Button query3Button = (Button)findViewById(R.id.query3Button);

        executeButton.setBackground (PressUtil.getBgDrawable(executeButton.getBackground()));
        insertButton.setBackground (PressUtil.getBgDrawable(insertButton.getBackground()));
        deleteButton.setBackground (PressUtil.getBgDrawable(deleteButton.getBackground()));
        updateButton.setBackground (PressUtil.getBgDrawable(updateButton.getBackground()));
        query1Button.setBackground (PressUtil.getBgDrawable(query1Button.getBackground()));
        query2Button.setBackground (PressUtil.getBgDrawable(query2Button.getBackground()));
        query3Button.setBackground (PressUtil.getBgDrawable(query3Button.getBackground()));

        SQLBtnOnClickListener onClickListener = new SQLBtnOnClickListener();
        executeButton.setOnClickListener(onClickListener);
        insertButton.setOnClickListener(onClickListener);
        deleteButton.setOnClickListener(onClickListener);
        updateButton.setOnClickListener(onClickListener);
        query1Button.setOnClickListener(onClickListener);
        query2Button.setOnClickListener(onClickListener);
        query3Button.setOnClickListener(onClickListener);

        inputSqlMsg = (EditText)findViewById(R.id.inputSqlMsg);
        showSQLMsg = (TextView)findViewById(R.id.showSQLMsg);
        showDataListView = (ListView)findViewById(R.id.showDateListView);
        showDataListView.addHeaderView(LayoutInflater.from(this).inflate(R.layout.show_sql_item,null),
                    null,false);
    }

    private void refreshOrderList(){
        orderList.clear();
        orderList.addAll(orderDao.getAllData());
        adapter.notifyDataSetChanged();
    }

    public class SQLBtnOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.executeButton:
                    showSQLMsg.setVisibility(View.GONE);
                    String sql = inputSqlMsg.getText().toString();
                    if (! TextUtils.isEmpty(sql)){
                        orderDao.execSQL(sql);
                    }else {
                        Toast.makeText(MainActivity.this, R.string.strInputSql, Toast.LENGTH_SHORT).show();
                    }

                    refreshOrderList();
                    break;

                case R.id.insertButton:
                    showSQLMsg.setVisibility(View.VISIBLE);
                    showSQLMsg.setText("新增一条数据：\n添加数据(7, \"Jne\", 700, \"China\")\ninsert into Orders(Id, CustomName, OrderPrice, Country) values (7, \"Jne\", 700, \"China\")");
                    orderDao.insertData();
                    refreshOrderList();
                    break;

                case R.id.deleteButton:
                    showSQLMsg.setVisibility(View.VISIBLE);
                    showSQLMsg.setText("删除一条数据：\n删除Id为7的数据\ndelete from Orders where Id = 7");
                    orderDao.deleteOrder();
                    refreshOrderList();
                    break;

                case R.id.updateButton:
                    showSQLMsg.setVisibility(View.VISIBLE);
                    showSQLMsg.setText("修改一条数据：\n将Id为6的数据的OrderPrice修改了800\nupdate Orders set OrderPrice = 800 where Id = 6");
                    orderDao.updateOrder();
                    refreshOrderList();
                    break;

                case R.id.query1Button:
                    showSQLMsg.setVisibility(View.VISIBLE);
                    StringBuilder msg = new StringBuilder();
                    msg.append("数据查询：\n此处将用户名为\"Bor\"的信息提取出来\nselect * from Orders where CustomName = 'Bor'");
                    List<Order> borOrders = orderDao.getBorOrder();
                    for (Order order : borOrders){
                        msg.append("\n(" + order.id + ", " + order.customName + ", " + order.orderPrice + ", " + order.country + ")");
                    }
                    showSQLMsg.setText(msg);
                    break;

                case R.id.query2Button:
                    showSQLMsg.setVisibility(View.VISIBLE);
                    int chinaCount = orderDao.getChinaCount();
                    showSQLMsg.setText("统计查询：\n此处查询Country为China的用户总数\nselect count(Id) from Orders where Country = 'China'\ncount = " + chinaCount);
                    break;

                case R.id.query3Button:
                    showSQLMsg.setVisibility(View.VISIBLE);
                    StringBuilder msg2 = new StringBuilder();
                    msg2.append("比较查询：\n此处查询单笔数据中OrderPrice最高的\nselect Id, CustomName, Max(OrderPrice) as OrderPrice, Country from Orders");
                    Order order = orderDao.getMaxOrderPrice();
                    msg2.append("\n(" + order.id + ", " + order.customName + ", " + order.orderPrice + ", " + order.country + ")");
                    showSQLMsg.setText(msg2);
                    break;

                default:

                    break;
            }
        }
    }
}
