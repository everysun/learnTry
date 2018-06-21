package com.test.sqlitetest.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.test.sqlitetest.OrderContext;

public class PressUtil {
    private static Context context = OrderContext.getInstance().getApplicationContext();

    public static int getColor(int colorResId){
        return context.getResources().getColor(colorResId);
    }

    public static Drawable getDrawable(int drawableResId){
       return context.getResources().getDrawable(drawableResId);
    }

    public static AutoBuildBackgroundDrawable getBgDrawable(Drawable drawable){
        return new AutoBuildBackgroundDrawable(drawable);
    }

    public static AutoBuildBackgroundDrawable getBgDrawable(int resId){
        return new AutoBuildBackgroundDrawable(getDrawable(resId));
    }
}
