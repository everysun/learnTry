// IBinderPool.aidl
package com.test.booktest.binderpool;

// Declare any non-default types here with import statements

interface IBinderPool {
  IBinder queryBinder(int binderCode);
}
