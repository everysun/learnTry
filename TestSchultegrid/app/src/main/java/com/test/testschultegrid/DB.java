package com.test.testschultegrid;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.ArrayMap;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;


public class DB extends SQLiteOpenHelper {
    private static DB db;
    private Context ctx;

    private DB(Context context){
        super(context, "history.db", null, 1);
        ctx = context;
    }

    public static DB getInstance(Context context){
       if(db == null){
           db = new DB(context);
       }

       return db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE score ("+
                "time timestap primary key not null default (datetime('now','localtime'))," +
        "size text," + "difficulty text," + "score real);");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists score;");
        onCreate(db);
    }


    public void record(float score){
        SharedPreferences sp = ctx.getSharedPreferences(Constant.SETTING, Context.MODE_PRIVATE);
        String size = sp.getString(Constant.SIZE, "");
        String difficulty = sp.getString(Constant.DIFFICULTY, "");

        ContentValues values = new ContentValues();
        values.put(Constant.SIZE, size);
        values.put(Constant.DIFFICULTY, difficulty);
        values.put(Constant.SCORE, score);
        getWritableDatabase().insert("score", null, values);
    }


    public ArrayList<HistoryItem> getHistory(){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<HistoryItem> list = new ArrayList<>();

        Cursor cursor = db.rawQuery("select datetime(time), size, difficulty, score "+
                    "from score order by time desc limit 100", null);

        try{
            cursor.moveToFirst();
            list.ensureCapacity(cursor.getCount());

            while(cursor.moveToNext()){
                list.add(new HistoryItem(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getFloat(3)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            if(cursor != null){
                cursor.close();
                cursor = null;
            }
        }


        return list;
    }



    public ArrayMap<String, String> getBest(){
        ArrayMap<String, String> map = new ArrayMap<>();

        Cursor cursor = db.getReadableDatabase().rawQuery(
               "select size, min(score) best from score group by size", null);


        try{

            cursor.moveToFirst();
            while(cursor.moveToNext()){
                map.put(cursor.getString(0), cursor.getString(1) + 's');
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(cursor != null){
                cursor.close();
                cursor = null;
            }
        }

        return map;
    }



    public static class HistoryItem{
        private String size;
        private String difficulty;
        private String time;
        private float score;

        public HistoryItem(String time, String size, String difficulty, float score){
            this.time = time;
            this.size = size;
            this.difficulty = difficulty;
            this.score = score;
        }


        public String getDifficulty() {
            return difficulty;
        }

        public String getSize() {
            return size;
        }

        public String getTime() {
            return time;
        }

        public float getScore() {
            return score;
        }
    }
}
