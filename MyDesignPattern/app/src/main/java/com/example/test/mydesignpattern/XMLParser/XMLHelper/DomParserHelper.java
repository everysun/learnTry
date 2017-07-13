package com.example.test.mydesignpattern.XMLParser.XMLHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.example.test.mydesignpattern.XMLParser.entity.Channel;

public class DomParserHelper {

    public static List<Channel> getChannelList(InputStream stream){
        List<Channel> list = new ArrayList<Channel>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try{
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(stream);
            Element root = document.getDocumentElement();
            NodeList items = root.getElementsByTagName("item");

            for(int i=0; i<items.getLength(); ++i){
                Channel chann = new Channel();
                Element item = (Element)items.item(i);
                chann.setId(item.getAttribute("id"));
                chann.setUrl(item.getAttribute("url"));
                chann.setName(item.getFirstChild().getNodeValue());
                list.add(chann);
            }

        }catch(ParserConfigurationException e){
            e.printStackTrace();
        }catch(SAXException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

        return list;
    }
}
