package com.test.sqlitetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OrderDBHelper extends SQLiteOpenHelper{
    private static final int DB_VERSION = 1;
    public static final String DB_NAME = "myTest.db";
    public  static final String TABLE_NAME = "Orders";
    private Context mcontext = null;

    public OrderDBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        mcontext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists " + TABLE_NAME + " (Id integer primary key," +
                "CustomName text, OrderPrice integer, Country text) ";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
}
