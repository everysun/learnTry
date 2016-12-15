package com.everysun.getphoneappinfo;

import java.util.List;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
/**
 * Created by everysun on 16/12/06.
 */

public class BrowseApplicationInfoAdapter extends BaseAdapter {
    private List<AppInfo> mlistAppInfo = null;

    LayoutInflater inflater = null;

    public BrowseApplicationInfoAdapter(Context context, List<AppInfo> apps){
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mlistAppInfo = apps;
    }

    @Override
    public int getCount(){
        System.out.println("Size" + mlistAppInfo.size());
        return mlistAppInfo.size();
    }

    @Override
    public Object getItem(int position){
        return mlistAppInfo.get(position);
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup arg2){
        System.out.println("getView at" + position);
        View view = null;
        ViewHolder holder = null;

        if(convertview==null || convertview.getTag()==null){
            view = inflater.inflate(R.layout.browse_app_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        else{
            view = convertview;
            holder = (ViewHolder) convertview.getTag();
        }
        AppInfo appinfo= (AppInfo)getItem(position);
        holder.appIcon.setImageDrawable(appinfo.getAppIcon());
        holder.tvAppLabel.setText(appinfo.getAppLabel());
        holder.tvPkgName.setText(appinfo.getPkgName());
        return view;
    }

    class ViewHolder{
        ImageView appIcon;
        TextView tvAppLabel;
        TextView tvPkgName;

        public ViewHolder(View view){
            this.appIcon = (ImageView)view.findViewById(R.id.imgApp);
            this.tvAppLabel = (TextView)view.findViewById(R.id.tvAppLabel);
            this.tvPkgName = (TextView)view.findViewById(R.id.tvPkgName);
        }
    }

}
