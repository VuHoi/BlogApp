package com.example.vukhachoi.blogapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import sql.Databasehelper;

public class Input extends AppCompatActivity {
    Databasehelper myDatabase;
    SQLiteDatabase database;
    Button btnchonhinh,btnDang;
    EditText edtTitle,edtInput;
    Spinner spnStatus;
    ImageView imgtest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        myDatabase = new Databasehelper(this);
        myDatabase.Khoitai();
        database = myDatabase.getMyDatabase();
        btnchonhinh=findViewById(R.id.btnchonhinh);
        btnDang=findViewById(R.id.btnDang);
        edtTitle=findViewById(R.id.edttitle);
        edtInput=findViewById(R.id.edtInput);
imgtest=findViewById(R.id.imgtest);
        spnStatus=findViewById(R.id.spnStatus);

        AddEvent();
    }

    private void AddEvent() {

        btnchonhinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);

            }
        });

        btnDang.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Bitmap bitmap=((BitmapDrawable)imgtest.getDrawable()).getBitmap();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100,byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream .toByteArray();
                String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

                try {
                ContentValues values=new ContentValues();
                values.put("title",edtTitle.getText().toString());
                values.put("description",edtInput.getText().toString());

                values.put("image",encoded);
                values.put("pos",1);
                values.put("status",spnStatus.getSelectedItem().toString());

                    database.insertWithOnConflict("Blog", null, values, SQLiteDatabase.CONFLICT_FAIL);

                    finish();
                }
                catch (Exception e)
                {
                    Toast.makeText(Input.this, "Các trường không được bỏ trống", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                imgtest.setVisibility(View.VISIBLE);
                imgtest.setImageURI(data.getData());
                return;
            }

            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
        }
    }
}
