package com.test.testschultegrid;

import android.content.Context;
import android.content.res.Resources;

public class Constant {
    private static Constant instance = null;

    public static final String DIFFICULTY = "difficulty";
    public static final String SIZE = "size";
    public static final String SCORE = "score";
    public static final String SETTING = "setting";

    public static String[] difficulties = null;
    public static String[] sizes = null;

    private Constant(Context ctx){
        sizes = new String[]{
                ctx.getString(R.string.size_3_3),
                ctx.getString(R.string.size_4_4),
                ctx.getString(R.string.size_5_5),
                ctx.getString(R.string.size_6_6)};

        difficulties = new String[]{
                ctx.getString(R.string.difficulty_easy),
                ctx.getString(R.string.difficulty_normal),
                ctx.getString(R.string.difficulty_hard)};

    }


    public static void init(Context ctx){
        if(instance == null){
            instance = new Constant(ctx);
        }
    }


}
