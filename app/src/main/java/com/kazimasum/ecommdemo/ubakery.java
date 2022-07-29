package com.kazimasum.ecommdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class ubakery extends AppCompatActivity {

    ImageView bakery1, bakery2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubakery);
        setTitle("Home Bakery App");
        bakery1=findViewById(R.id.imageView3);
        bakery2=findViewById(R.id.imageView4);

        bakery1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ubakery.this, "Please Login First!",Toast.LENGTH_SHORT).show();
            }
        });

        bakery2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ubakery.this, "Please Login First!",Toast.LENGTH_SHORT).show();
            }
        });


    }
}