package com.example.test.dictresolver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.test.dictresolver.content.Words;
import com.example.test.dictresolver.content.Person;
import com.example.test.dictresolver.content.Pen;

import java.util.List;
import java.util.Map;

public class ResultActivity extends Activity {
    Button btn = null;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);

        ListView listview = (ListView)findViewById(R.id.show);
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        List<Map<String,String>> list = (List<Map<String,String>>)data.getSerializable("data");

        SimpleAdapter adapter = new SimpleAdapter(ResultActivity.this, list, R.layout.line,
                new String[]{Words.Word.WORD, Words.Word.DETAIL},
                new int[]{R.id.word, R.id.detail});

        listview.setAdapter(adapter);

        btn = (Button)findViewById(R.id.btnSQLite);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, SQLiteDBTestActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("DictResolver", "################ onResume() ############");

        Intent intent = getIntent();
        Bundle data = intent.getExtras();

        Person p1 = (Person)intent.getSerializableExtra("putExtraPerson");
        //Person p2 = (Person)intent.getSerializableExtra("putBundlePerson");
        Person p2 = (Person)data.getSerializable("putBundlePerson");
        StringBuffer sb = new StringBuffer();
        sb.append("Serializabled \n");
        sb.append("intent.putExtra: ");
        sb.append(p1.getName());
        sb.append(p1.getAge());
        sb.append("\n Bundle.putSerializable: ");
        sb.append(p2.getName());
        sb.append(p2.getAge());

        Log.d("DDDDDDD", "########## "+sb.toString());

        Toast.makeText(ResultActivity.this, sb.toString(), Toast.LENGTH_LONG).show();


        Pen pen1 = (Pen)intent.getParcelableExtra("putExtraParcel");
        Bundle bundle = intent.getExtras();
        Pen pen2 = (Pen)bundle.getParcelable("putParcelable");

        StringBuffer sb2 = new StringBuffer();
        sb2.append("Parcelable \n");
        sb2.append("intent.putExtra: ");
        sb2.append(pen1.getColor());
        sb2.append(pen1.getSize());
        sb2.append("\n Bundle.putParcelable: ");
        sb2.append(pen2.getColor());
        sb2.append(pen2.getSize());

        Log.d("DDDDDDD", "########## "+sb2.toString());

        Toast.makeText(ResultActivity.this, sb2.toString(), Toast.LENGTH_LONG).show();

        Bundle bundle2 = intent.getBundleExtra("putExtraParcel2");
        Pen pen3 = (Pen)bundle2.getParcelable("putParcelable2");
        StringBuffer sb3 = new StringBuffer();
        sb3.append("Parcelable \n");
        sb3.append("intent.putExtra with Bundle: ");
        sb3.append("\n Bundle.putParcelable: ");
        sb3.append(pen3.getColor());
        sb3.append(pen3.getSize());
        Toast.makeText(ResultActivity.this, sb3.toString(), Toast.LENGTH_LONG).show();

    }
}
