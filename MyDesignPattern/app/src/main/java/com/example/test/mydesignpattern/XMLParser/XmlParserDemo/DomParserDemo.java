package com.example.test.mydesignpattern.XMLParser.XmlParserDemo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.test.mydesignpattern.XMLParser.XMLHelper.DomParserHelper;
import com.example.test.mydesignpattern.XMLParser.entity.Channel;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

import com.example.test.mydesignpattern.R;


public class DomParserDemo extends ListActivity {
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.list,
                new String[]{"id", "name"}, new int[]{R.id.textId, R.id.textName});

        setListAdapter(adapter);
    }

    private List<Map<String, String>> getData(){
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        InputStream stream = getResources().openRawResource(R.raw.channels);
        List<Channel> channelList = DomParserHelper.getChannelList(stream);

        for(int i=0; i<channelList.size(); ++i){
            Map<String, String> map = new HashMap<String, String>();
            Channel chann = (Channel)channelList.get(i);
            map.put("id", chann.getId());
            map.put("url", chann.getUrl());
            map.put("name", chann.getName());
            list.add(map);
        }

        return list;
    }
}
