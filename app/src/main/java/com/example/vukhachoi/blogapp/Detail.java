package com.example.vukhachoi.blogapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Display;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import model.Blog;

public class Detail extends AppCompatActivity {

    Spinner spnStatus1;
    ImageView imgdetail;
    TextView txttitle1,txtDiscription1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

Intent intent=getIntent();
Blog blog= (Blog) intent.getSerializableExtra("blog");

        imgdetail=findViewById(R.id.imgdetail);
        txttitle1=findViewById(R.id.txttitle1);
        txtDiscription1=findViewById(R.id.txtDiscription1);
        spnStatus1=findViewById(R.id.spnStatus2);
        int width1 = imgdetail.getDrawable().getIntrinsicWidth();
        int height = imgdetail.getDrawable().getIntrinsicHeight();
txttitle1.setText(blog.getTitle());
txtDiscription1.setText("     "+blog.getDescription());
        byte[] decodedString = Base64.decode(blog.getImage(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        imgdetail.setImageBitmap(decodedByte);


                imgdetail.setMinimumWidth(width);

        if(blog.getStatus().equals("Draff"))
        {
            spnStatus1.setSelection(0);
        }
        else if(blog.getStatus().equals("Pending"))
        {
            spnStatus1.setSelection(1);
        }
        else if(blog.getStatus().equals("Published"))
        {
            spnStatus1.setSelection(2);
        }
        else {
            spnStatus1.setSelection(0);
        }


    }
}
