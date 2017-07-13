package com.example.test.mydesignpattern.XMLParser.XmlParserDemo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParserException;

import android.app.ListActivity;
import android.os.Bundle;
import android.content.res.XmlResourceParser;
import android.widget.SimpleAdapter;

import com.example.test.mydesignpattern.R;

public class PullParserDemo extends ListActivity{
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.list,
                new String[]{"id", "name"}, new int[]{R.id.textId, R.id.textName});

        setListAdapter(adapter);
    }

    private List<Map<String, String>> getData(){
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        XmlResourceParser xrp = getResources().getXml(R.xml.channels);
        try{
            while(xrp.getEventType() != XmlResourceParser.END_DOCUMENT){
                if(xrp.getEventType() == XmlResourceParser.START_TAG){
                    String tagName = xrp.getName();
                    if(tagName.equals("item")){
                        Map<String,String> map = new HashMap<String,String>();
                        String id = xrp.getAttributeValue(null, "id");
                        map.put("id", id);
                        String url = xrp.getAttributeValue(1);
                        map.put("url", url);
                        map.put("name", xrp.nextText());
                        list.add(map);
                    }
                }
                xrp.next();
            }
        }catch(XmlPullParserException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

        return list;
    }
}
