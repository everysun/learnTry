package com.example.test.mydesignpattern.DictProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class DictProvider extends ContentProvider {
    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int WORDS = 1;
    private static final int WORD = 2;

    private MyDatabaseHelper dbOpenHelper;

    static{
        matcher.addURI(Words.AUTHORITY, "words", WORDS);
        matcher.addURI(Words.AUTHORITY, "wrod/#", WORD);
    }

    @Override
    public boolean onCreate(){
        dbOpenHelper = new MyDatabaseHelper(this.getContext(), "myDict.db3", 1);
        return true;
    }

    @Override
    public String getType(Uri uri){
        switch(matcher.match(uri)){
            case WORDS:
                return "vnd.android.cursor.dir/com.example.test.dict";

            case WORD:
                return "vnd.android.curesor.item/com.example.test.dict";
            default:
                throw new IllegalArgumentException("未知Uri:" + uri);
        }

    }

    @Override
    public Cursor query(Uri uri, String[] projection, String where,
                        String[] whereArgs, String sortOrder){
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();

        switch(matcher.match(uri)){
            case WORDS:
                return db.query("dict", projection, where, whereArgs, null, null, sortOrder);

            case WORD:
                long id = ContentUris.parseId(uri);
                String whereClause = Words.Word._ID + "=" + id;

                if(where != null && !"".equals(where)){
                    whereClause = whereClause + " and " + where;
                }
                return db.query("dict", projection, whereClause, whereArgs, null, null, sortOrder);

            default:
                throw new IllegalArgumentException("未知Uri:" + uri);

        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values){
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();

        switch(matcher.match(uri)){
            case WORDS:
                long rowId = db.insert("dict", Words.Word._ID, values);
                if(rowId > 0){
                    Uri wordUri = ContentUris.withAppendedId(uri, rowId);
                    getContext().getContentResolver().notifyChange(wordUri, null);
                    return wordUri;
                }
                break;
            default:
                throw new IllegalArgumentException("未知Uri:" + uri);
        }
        return null;
    }


    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        int num = 0;

        switch(matcher.match(uri)){
            case WORDS:
                num = db.update("dict", values, where, whereArgs);
                break;

            case WORD:
                long id = ContentUris.parseId(uri);
                String whereClause = Words.Word._ID + "=" + id;

                if(where!=null && !where.equals("")){
                    whereClause = whereClause + " and " + where;
                }
                num = db.update("dict", values, whereClause, whereArgs);
                break;
            default:
                throw new IllegalArgumentException("未知Uri:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return num;
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs){
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        int num = 0;

        switch(matcher.match(uri)){
            case WORDS:
                num = db.delete("dict", where, whereArgs);
                break;

            case WORD:
                long id = ContentUris.parseId(uri);
                String whereClause = Words.Word._ID + "=" + id;

                if(where!=null && !where.equals("")){
                   whereClause = whereClause + " and " + where;
                }
                num = db.delete("dict", whereClause, whereArgs);
                break;
            default:
                throw new IllegalArgumentException("未知Uri:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return num;
    }
}
