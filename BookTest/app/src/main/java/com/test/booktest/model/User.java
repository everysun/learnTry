package com.test.booktest.model;

import java.io.Serializable;
import com.test.booktest.aidl.Book;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable,Serializable{
    private static final long serialVersionUID = 51906712372L;

    public int userId;
    public String userName;
    public boolean isMale;
    public Book book;

    public User(){

    }

    public User(int userId, String userName, boolean isMale){
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
    }

    public int describeContents(){
        return 0;
    }

    public void writeToParcel(Parcel out, int flags){
        out.writeInt(userId);
        out.writeString(userName);
        out.writeInt(isMale ? 1 : 0);
        out.writeParcelable(book, 0);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>(){
      public User createFromParcel(Parcel in){
          return new User(in);
      }

      public User[] newArray(int size){
          return new User[size];
      }
    };

    private User(Parcel in){
        userId = in.readInt();
        userName = in.readString();
        isMale = in.readInt()==1;
        book = in.readParcelable(Thread.currentThread().getContextClassLoader());
    }

    @Override
    public String toString(){
        return String.format("User:{userId:%s, userName:%s, isMale:%s}, whith child:{%s}",
                userId, userName, isMale, book);
    }
}
