// IOnNewBookArrivedListener.aidl
package com.test.booktest.aidl;

import com.test.booktest.aidl.Book;
// Declare any non-default types here with import statements

interface IOnNewBookArrivedListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

    void onNewBookArrived(in Book newBook);
}