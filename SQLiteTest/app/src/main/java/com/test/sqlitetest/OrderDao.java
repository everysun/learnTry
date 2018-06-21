package com.test.sqlitetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    private static final String TAG = "OrderDao";

    private final String[] ORDER_COLUMNS = new String[]{"Id", "CustomName", "OrderPrice", "Country"};

    private Context context;
    private OrderDBHelper orderDBHelper;

    public OrderDao(Context context){
        this.context = context;
        orderDBHelper = new OrderDBHelper(context);
    }

    public boolean isDataExist(){
        int count = 0;

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try{
            db = orderDBHelper.getReadableDatabase();
            cursor = db.query(OrderDBHelper.TABLE_NAME, new String[]{"COUNT(Id)"},null,
                    null,null,null,null);

            if(cursor.moveToFirst()){
                count = cursor.getInt(0);
            }
            if(count >0)
                return true;
        }catch(Exception e){
            Log.e(TAG, "",e);
        }
        finally {
            if(cursor != null){
                cursor.close();
            }
            if(db !=  null){
                db.close();
            }
        }
        return false;
    }

    public void initTable(){
        SQLiteDatabase db = null;

        try{
            db = orderDBHelper.getWritableDatabase();
            db.beginTransaction();

            db.execSQL("insert into " + OrderDBHelper.TABLE_NAME + " (Id, CustomName ,OrderPrice, Country) values (1, 'arc', 100, 'china')");
            db.execSQL("insert into " + OrderDBHelper.TABLE_NAME + " (Id, CustomName, OrderPrice, Country) values (2, 'Bor', 200, 'USA')");
            db.execSQL("insert into " + OrderDBHelper.TABLE_NAME + " (Id, CustomName, OrderPrice, Country) values (3, 'Cut', 500, 'Japan')");
            db.execSQL("insert into " + OrderDBHelper.TABLE_NAME + " (Id, CustomName, OrderPrice, Country) values (4, 'Bor', 300, 'USA')");
            db.execSQL("insert into " + OrderDBHelper.TABLE_NAME + " (Id, CustomName, OrderPrice, Country) values (5, 'Arc', 600, 'China')");
            db.execSQL("insert into " + OrderDBHelper.TABLE_NAME + " (Id, CustomName, OrderPrice, Country) values (6, 'Doom', 200, 'China')");

            db.setTransactionSuccessful();
        }catch(Exception e){
            Log.e(TAG, "", e);
        }finally {
            if(db != null){
                db.endTransaction();
                db.close();
            }
        }
    }

    public void  execSQL(String sql){
        SQLiteDatabase db = null;

        try{
            if(sql.contains("select")){
                Toast.makeText(context,R.string.strUnableSql,Toast.LENGTH_SHORT).show();
            }else if(sql.contains("insert") || sql.contains("update") || sql.contains("delete")){
                db = orderDBHelper.getWritableDatabase();
                db.beginTransaction();;
                db.execSQL(sql);
                db.setTransactionSuccessful();;
                Toast.makeText(context, R.string.strSuccessSql, Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            Toast.makeText(context, R.string.strErrorSql, Toast.LENGTH_SHORT).show();
            Log.e(TAG, "", e);
        }finally {
            if(db != null){
                db.endTransaction();
                db.close();
            }
        }
    }

    public List<Order> getAllData(){
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try{
            db = orderDBHelper.getReadableDatabase();
            cursor = db.query(OrderDBHelper.TABLE_NAME, ORDER_COLUMNS,null,null,null,null,null);

            //cursor = db.query(OrderDBHelper.TABLE_NAME, null, null,null,null,null,null);
            if(cursor.getCount()>0){
                List<Order> orderList = new ArrayList<Order>(cursor.getCount());
                while(cursor.moveToNext()){
                    orderList.add(parseOrder(cursor));
                }
                return orderList;
            }
        }catch (Exception e){
            Log.e(TAG, "",e);
        }finally {
            if(cursor != null){
                cursor.close();
            }
            if(db != null){
                db.close();
            }
        }

        return null;
    }

    public boolean insertData(){
        SQLiteDatabase db = null;

        try{
            db = orderDBHelper.getWritableDatabase();
            db.beginTransaction();

            ContentValues contentValues = new ContentValues();
            contentValues.put("Id", 7);
            contentValues.put("CustomName", "Jne");
            contentValues.put("OrderPrice", 700);
            contentValues.put("Country", "China");
            db.insertOrThrow(OrderDBHelper.TABLE_NAME, null, contentValues);
            db.setTransactionSuccessful();
            return true;
        }catch (SQLiteConstraintException e){
            Log.e(TAG, "", e);
        }finally {
            if(db != null){
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }

    public boolean deleteOrder(){
        SQLiteDatabase db = null;

        try{
            db = orderDBHelper.getWritableDatabase();
            db.beginTransaction();

            db.delete(OrderDBHelper.TABLE_NAME, "Id = ?", new String[]{String.valueOf(7)});
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }

    public boolean updateOrder() {
        SQLiteDatabase db = null;

        try {
            db = orderDBHelper.getWritableDatabase();
            db.beginTransaction();

            ContentValues contentValues = new ContentValues();
            contentValues.put("OrderPrice", 800);

            db.update(OrderDBHelper.TABLE_NAME, contentValues, "Id=?", new String[]{String.valueOf(6)});
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Log.e(TAG, "", e);

        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }

    public List<Order> getBorOrder(){
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = orderDBHelper.getReadableDatabase();

            // select * from Orders where CustomName = 'Bor'
            cursor = db.query(OrderDBHelper.TABLE_NAME,
                    ORDER_COLUMNS,
                    null,
                    null,
                    null, null, null);

            if (cursor.getCount() > 0) {
                List<Order> orderList = new ArrayList<Order>(cursor.getCount());
                while (cursor.moveToNext()) {
                    Order order = parseOrder(cursor);
                    orderList.add(order);
                }
                return orderList;
            }
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return null;
    }


    public int getChinaCount(){
        int count = 0;

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = orderDBHelper.getReadableDatabase();
            // select count(Id) from Orders where Country = 'China'
            cursor = db.query(OrderDBHelper.TABLE_NAME,
                    new String[]{"COUNT(Id)"},
                    "Country = ?",
                    new String[] {"China"},
                    null, null, null);

            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return count;
    }


    public Order getMaxOrderPrice(){
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = orderDBHelper.getReadableDatabase();
            // select Id, CustomName, Max(OrderPrice) as OrderPrice, Country from Orders
            cursor = db.query(OrderDBHelper.TABLE_NAME, new String[]{"Id", "CustomName", "Max(OrderPrice) as OrderPrice", "Country"}, null, null, null, null, null);

            if (cursor.getCount() > 0){
                if (cursor.moveToFirst()) {
                    return parseOrder(cursor);
                }
            }
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return null;
    }

    private Order parseOrder(Cursor cursor){
        Order order = new Order();
        order.id = (cursor.getInt(cursor.getColumnIndex("Id")));
        order.customName = (cursor.getString(cursor.getColumnIndex("CustomName")));
        order.orderPrice = (cursor.getInt(cursor.getColumnIndex("OrderPrice")));
        order.country = (cursor.getString(cursor.getColumnIndex("Country")));
        return order;
    }

}
