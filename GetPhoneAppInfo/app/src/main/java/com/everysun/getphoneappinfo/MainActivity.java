package com.everysun.getphoneappinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {

    public static final int FILTER_ALL_APP = 0; //所有应用
    public static final int FILTER_SYSTEM_APP = 1; //系统应用
    public static final int FILTER_THIRD_APP = 2; //第三方应用
    public static final int FILTER_SDCARD_APP = 3; //SDCard中的应用

    private ListView listview = null;

    private PackageManager pm;
    private int filter = FILTER_ALL_APP;
    private List<AppInfo> mlistAppInfo;
    private BrowseApplicationInfoAdapter browseAppAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.browse_app_list);

        listview = (ListView)findViewById(R.id.listviewApp);
        if(getIntent() != null){
            filter = getIntent().getIntExtra("filter", 0);
        }

        mlistAppInfo = queryFilterAppInfo(filter);

        browseAppAdapter = new BrowseApplicationInfoAdapter(this, mlistAppInfo);
        listview.setAdapter(browseAppAdapter);
    }

    private List<AppInfo> queryFilterAppInfo(int filter){
        pm = this.getPackageManager();

        List<ApplicationInfo> listApplications = pm.getInstalledApplications(PackageManager.MATCH_UNINSTALLED_PACKAGES);
        Collections.sort(listApplications,
                new ApplicationInfo.DisplayNameComparator(pm));

        List<AppInfo> appInfos = new ArrayList<AppInfo>();

        switch(filter) {
            case FILTER_ALL_APP:
                appInfos.clear();
                for (ApplicationInfo app : listApplications) {
                    appInfos.add(getAppInfo(app));
                }
                return appInfos;


            case FILTER_SYSTEM_APP:
                appInfos.clear();
                for (ApplicationInfo app : listApplications) {
                    if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                        appInfos.add(getAppInfo(app));
                    }
                }
                return appInfos;


            case FILTER_THIRD_APP:
                appInfos.clear();
                for (ApplicationInfo app : listApplications) {
                    if ((app.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                        appInfos.add(getAppInfo(app));
                    }
                }
                return appInfos;


            case FILTER_SDCARD_APP:
                appInfos.clear();
                for (ApplicationInfo app : listApplications){
                    if ((app.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0) {
                        appInfos.add(getAppInfo(app));
                }
            }
            break;
            default:
                return null;
        }

        return appInfos;
    }

    private AppInfo getAppInfo(ApplicationInfo app){
        AppInfo appInfo = new AppInfo();
        appInfo.setAppLabel((String)app.loadLabel(pm));
        appInfo.setAppIcon(app.loadIcon(pm));
        appInfo.setPkgName(app.packageName);
        return appInfo;
    }
}
