package com.example.vukhachoi.blogapp;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

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
    StatusAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        lsvStatus=findViewById(R.id.lsvStatus);
        btnInput=findViewById(R.id.btnInput);
        myDatabase = new Databasehelper(this);
        myDatabase.Khoitai();
        database = myDatabase.getMyDatabase();
        blogList=new ArrayList<>();
        Cursor cursor =database.rawQuery("Select * from Blog ",null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            blogList.add(0,new Blog(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getString(5)));
            cursor.moveToNext();
        }
adapter=new StatusAdapter(Home.this,R.layout.status_item,blogList);
        lsvStatus.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        AddEvent();
    }

    private void AddEvent() {
        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                dialog = new Dialog(Home.this);
//
//                dialog.setContentView(R.layout.dialog_input);
//
//                dialog.setTitle("Đăng kí");
//
//
//                Button btnDang = (Button) dialog.findViewById(R.id.btnDang);
//                // khai báo control trong dialog để bắt sự kiện
//                btnDang.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        Cursor cursor =database.rawQuery("Select * from Blog ",null);
//                        cursor.moveToFirst();
//
//                        while (!cursor.isAfterLast())
//                        {
//                            Log.d("xx",cursor.getString(1));
//                            Log.d("xx",cursor.getString(2));
//                            Log.d("xx",cursor.getString(3));
//                            Log.d("xx",cursor.getInt(4)+"");
//                            Log.d("xx",cursor.getString(5));
//                            cursor.moveToNext();
//                        }
//                    }
//                });
//                // bắt sự kiện cho nút đăng kí
//                dialog.show();
                Intent intent=new Intent(Home.this,Input.class);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Cursor cursor =database.rawQuery("Select * from Blog ",null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            blogList.add(0,new Blog(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getString(5)));
            cursor.moveToNext();
        }
        try{
            adapter.notifyDataSetChanged();
        }
        catch (Exception e){}
    }
}
