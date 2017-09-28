package com.example.vukhachoi.blogapp;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.util.ArrayList;
import java.util.List;

import adapter.StatusAdapter;
import model.Blog;
import sql.Databasehelper;

public class Home extends AppCompatActivity {
    ListView lsvStatus;
    Button btnInput;
    private Dialog dialog;
    Databasehelper myDatabase;
    SQLiteDatabase database;
    List<Blog>blogList;
    String info;
    StatusAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        lsvStatus=findViewById(R.id.lsvStatus);
        btnInput=findViewById(R.id.btnInput);
        myDatabase = new Databasehelper(this);
        myDatabase.Khoitai();
        database = myDatabase.getMyDatabase();
        blogList=new ArrayList<>();
Intent intent1=getIntent();
 info=intent1.getStringExtra("admin");


adapter=new StatusAdapter(Home.this,R.layout.status_item,blogList);
        lsvStatus.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        AddEvent();
    }

    private void AddEvent() {
        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Home.this,Input.class);
                startActivity(intent);

            }
        });

        lsvStatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
Intent intent =new Intent(Home.this,Detail.class);

intent.putExtra("blog",blogList.get(i));
startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Cursor cursor =database.rawQuery("Select * from Blog ",null);
        cursor.moveToFirst();
        try{
blogList.clear();
        while (!cursor.isAfterLast())
        {
            if(info.equals("admin")) {
                blogList.add(0, new Blog(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getString(5), "admin",cursor.getString(0)));
            btnInput.setVisibility(View.VISIBLE);


            } else if(info.equals("User")){
                blogList.add(0, new Blog(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getString(5), "User",cursor.getString(0)));


            }
            cursor.moveToNext();
        }

            adapter.notifyDataSetChanged();
        }
        catch (Exception e){}
    }
}
