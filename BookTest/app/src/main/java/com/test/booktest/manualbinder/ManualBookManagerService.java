package com.test.booktest.manualbinder;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;


public class ManualBookManagerService extends Service {

    private static final String TAG = "#####ManualBinder###";
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<Book>();

    private Binder mBinder = new com.test.booktest.manualbinder.BookManagerImpl(){
        @Override
        public List<Book> getBookList() throws RemoteException{
            SystemClock.sleep(3000);
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException{
            mBookList.add(book);
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException{
            String packageName = null;
            String[] packages = getPackageManager().getPackagesForUid(getCallingUid());

            if(packages != null && packages.length > 0){
                packageName = packages[0];
            }

            Log.d(TAG, "######  Manual onTransact: " + packageName);
            return super.onTransact(code, data, reply, flags);
        }
    };

    @Override
    public void onCreate(){
        super.onCreate();
        mBookList.add(new Book(1, "Manual_Book1_########"));
        mBookList.add(new Book(2,"Manual_Book2_########"));
    }

    @Override
    public IBinder onBind(Intent intent){
        Log.d(TAG, "Manual onBind #######");
        return mBinder;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
