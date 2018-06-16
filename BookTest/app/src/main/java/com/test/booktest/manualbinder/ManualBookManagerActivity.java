package com.test.booktest.manualbinder;

import java.util.List;
import com.test.booktest.R;

import com.test.booktest.manualbinder.IBookManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ManualBookManagerActivity extends Activity {

    private static final String TAG = "Manual_Activity";
    TextView textView = null;

    private IBookManager mManualBookManager;

    private ServiceConnection mConnection = new ServiceConnection(){
       public void onServiceConnected(ComponentName className, IBinder service){
           mManualBookManager = BookManagerImpl.asInterface(service);

           try{
               List<Book> list = mManualBookManager.getBookList();
               System.out.println("****qery Manual Book list, list type: "+list.getClass().getCanonicalName());
               System.out.println("****Manula Book list: " + list.toString());

               Book newBook = new Book(3, "Manual addNew");
               mManualBookManager.addBook(newBook);
               System.out.println("***Manual add Book: " +newBook);
               List<Book> newList = mManualBookManager.getBookList();
               System.out.println("****Manula Book list: " + newList.toString());

               textView.setText(newList.toString());
           }catch(RemoteException e){
               e.printStackTrace();
           }
       }

       public void onServiceDisconnected(ComponentName className){
           mManualBookManager = null;
           Log.d(TAG, "Manual onServiceDisconnected. tname: "+ Thread.currentThread().getName());
       }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_book_manager);
        textView = (TextView)findViewById(R.id.textView1);

        Intent intent = new Intent(this, ManualBookManagerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onDestroy(){
        unbindService(mConnection);
        super.onDestroy();
    }
}
