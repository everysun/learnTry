// IBookManager.aidl
package com.test.booktest.aidl;

// Declare any non-default types here with import statements

import com.test.booktest.aidl.Book;
import com.test.booktest.aidl.IOnNewBookArrivedListener;

interface IBookManager {

     List<Book> getBookList();
     void addBook(in Book book);
     void registerListener(IOnNewBookArrivedListener listener);
     void unregisterListener(IOnNewBookArrivedListener listener);
}
