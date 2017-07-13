package com.example.test.dictresolver.content;

import android.os.Parcel;
import android.os.Parcelable;

public class Pen implements Parcelable{
    private String color;
    private int size;

    //给createFromParcel使用
    protected Pen(Parcel in){
        color = in.readString();
        size = in.readInt();
    }

    public static final Creator<Pen> CREATOR = new Creator<Pen>(){
        //读取顺序要与写的顺序完全相同

        @Override
        public Pen createFromParcel(Parcel in){
            return new Pen(in);
        }

        //供反序列化本类数组时调用
        @Override
        public Pen[] newArray(int size){
            return new Pen[size];
        }
    };

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(color);
        dest.writeInt(size);
    }



    public Pen(String color, int size){
        this.color = color;
        this.size = size;
    }

    public String getColor(){
        return color;
    }

    public void setColor(String color){
        this.color = color;
    }

    public int getSize(){
        return size;
    }

    public void setSize(int size){
        this.size = size;
    }
}
