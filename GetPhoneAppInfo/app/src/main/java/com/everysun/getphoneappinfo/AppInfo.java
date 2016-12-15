package com.everysun.getphoneappinfo;

/**
 * Created by everysun on 16/12/05.
 */

import android.content.Intent;
import android.graphics.drawable.Drawable;

public class  AppInfo {
    private String appLabel;
    private Drawable appIcon;
    private String pkgName;
    private Intent intent;  //启动应用程序的Intent

    public AppInfo(){}

    public String getAppLabel(){
        return appLabel;
    }
    public void setAppLabel(String appName){
        this.appLabel = appName;
    }

    public Drawable getAppIcon(){
        return appIcon;
    }
    public void setAppIcon(Drawable appIcon){
        this.appIcon = appIcon;
    }

    public Intent getIntent() {
        return intent;
    }
    public void setIntent(Intent intent){
        this.intent = intent;
    }

    public String getPkgName(){
        return pkgName;
    }
    public void setPkgName(String pkgName){
        this.pkgName = pkgName;
    }

}
