package com.kazimasum.ecommdemo;


import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView email;
    private TextView phonenumber;
    private CircleImageView profileimage;
    private Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("My Profile");


        email = findViewById(R.id.email);
        phonenumber = findViewById(R.id.phonenumber);
        backButton = findViewById(R.id.backButton);



        phonenumber.setText(dashboard.mobile);



        processemail(dashboard.mobile);



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, dashboard.class);
                startActivity(intent);
                finish();
            }
        });
    }



    public void processemail(String mobile){
        Log.d(TAG, "My Mobile Number :" + mobile);

        Call<login_response_model> call=apicontroller.getInstance()
                .getapi()
                .getemail(mobile);

        call.enqueue(new Callback<login_response_model>() {
            @Override
            public void onResponse(Call<login_response_model> call, Response<login_response_model> response) {
                login_response_model obj=response.body();
                Log.d(TAG, "My Message Response from server" + obj.getMessage());
                String useremail=obj.getMessage();
                Log.d(TAG, "My Email from server" + useremail);

                email.setText(useremail);
            }

            @Override
            public void onFailure(Call<login_response_model> call, Throwable t) {
                email.setText("Something went wrong");
            }
        });

    }

}