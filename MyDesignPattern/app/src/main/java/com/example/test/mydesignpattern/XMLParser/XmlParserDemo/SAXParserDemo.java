package com.example.test.mydesignpattern.XMLParser.XmlParserDemo;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.example.test.mydesignpattern.XMLParser.XMLHelper.SAXParserHelper;
import com.example.test.mydesignpattern.XMLParser.entity.Channel;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

import com.example.test.mydesignpattern.R;


public class SAXParserDemo extends ListActivity{
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        SimpleAdapter adapter = null;
        try{
            adapter = new SimpleAdapter(this, getData(), R.layout.list,
                    new String[]{"name","id"},new int[]{R.id.textName, R.id.textId});
        }catch(ParserConfigurationException e){
            e.printStackTrace();
        }catch(SAXException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

        setListAdapter(adapter);
    }

    private List<Map<String,String>> getData()throws ParserConfigurationException,SAXException,IOException{
        List<Channel> list;
        list = getChannelList();
        List<Map<String,String>> mapList = new ArrayList<Map<String, String>>();

        for(int i=0; i<list.size(); ++i){
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", list.get(i).getName());
            map.put("id", list.get(i).getId());
            mapList.add(map);
        }
        return mapList;

    }

    private List<Channel> getChannelList() throws  ParserConfigurationException, SAXException, IOException{

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLReader xmlreader = parser.getXMLReader();
        SAXParserHelper helpHandler = new SAXParserHelper();

        xmlreader.setContentHandler(helpHandler);

        InputStream stream = getResources().openRawResource(R.raw.channels);
        InputSource is = new InputSource(stream);

        xmlreader.parse(is);
        return helpHandler.getList();
    }
}
