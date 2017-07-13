package com.example.test.mydesignpattern.XMLParser.XMLHelper;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.example.test.mydesignpattern.XMLParser.entity.Channel;

public class SAXParserHelper extends DefaultHandler{
    final int  ITEM = 0x0005;

    List<Channel> list;
    Channel chann;
    int currentState = 0;

    public  List<Channel> getList(){
        return list;
    }

    @Override
    public void characters(char[] ch, int start, int length)throws SAXException{
        String theString = String.valueOf(ch, start, length);
        if(currentState != 0){
            chann.setName(theString);
            currentState = 0;
        }
    }

    @Override
    public void endDocument() throws SAXException{
        super.endDocument();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException{
        if(localName.equals("item"))
            list.add(chann);
    }

    @Override
    public void startDocument() throws SAXException{
        list = new ArrayList<Channel>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        chann = new Channel();

        if(localName.equals("item")){
            for(int i=0; i<attributes.getLength(); ++i){
                if(attributes.getLocalName(i).equals("id")){
                    chann.setId(attributes.getValue(i));
                }else if(attributes.getLocalName(i).equals("url")){
                    chann.setUrl(attributes.getValue(i));
                }
            }
            currentState = ITEM;
            return;
        }

        currentState = ITEM;
        return;
    }
}
