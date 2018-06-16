// ISercurityCenter.aidl
package com.test.booktest.binderpool;

// Declare any non-default types here with import statements

interface ISercurityCenter {
   String encrypt(String content);
   String decrypt(String password);
}
