package com.example.vukhachoi.blogapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sql.Databasehelper;

public class MainActivity extends AppCompatActivity {

    EditText edtUser,edtpass;
    Button btnLogin,btnSignup;
    Databasehelper myDatabase;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDatabase = new Databasehelper(this);
        myDatabase.Khoitai();
        database = myDatabase.getMyDatabase();
        edtUser=findViewById(R.id.edtUser);
        edtpass=findViewById(R.id.edtpass);
        btnLogin=findViewById(R.id.btnlogin);
        btnSignup=findViewById(R.id.btnsignup);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor cursor =database.rawQuery("Select * from User ",null);
                cursor.moveToFirst();

                Intent intent=new Intent(MainActivity.this,Home.class);
                if(edtpass.getText().toString().equals("admin")&&edtUser.getText().toString().equals("admin"))
                {

                    intent.putExtra("admin","admin");


                }
                else {
                    while (!cursor.isAfterLast()) {
                        if (edtpass.getText().toString().equals(cursor.getString(1)) && edtUser.getText().toString().equals(cursor.getString((2)))) {

                            intent.putExtra("admin","User");
                            break;

                        }
                        cursor.moveToNext();
                    }
                }
                startActivity(intent);

            }


        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ContentValues values = new ContentValues();
                    values.put("UserName", edtUser.getText().toString());
                    values.put("Password", edtpass.getText().toString());
                    database.insertWithOnConflict("User", null, values, SQLiteDatabase.CONFLICT_FAIL);
                }catch (Exception  e)
                {
                    Toast.makeText(MainActivity.this, "Các trường không được bỏ trống", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }



}
