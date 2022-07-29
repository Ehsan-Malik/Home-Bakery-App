package com.kazimasum.ecommdemo;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class feedback extends AppCompatActivity {

    public String id;

    RatingBar rt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setTitle("Feedback / Cancel Order");

        id = getIntent().getStringExtra("id");
        Log.d(TAG, "Order id :" + id);

        //binding MainActivity.java with activity_main.xml file
        rt = (RatingBar) findViewById(R.id.ratingBar);

        //finding the specific RatingBar with its unique ID
        LayerDrawable stars=(LayerDrawable)rt.getProgressDrawable();

        //Use for changing the color of RatingBar
        stars.getDrawable(2).setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
    }

    public void Call(View v)
    {
        // This function is called when button is clicked.
        // Display ratings, which is required to be converted into string first.

        String rate=String.valueOf(rt.getRating());
        Log.d(TAG, "Rating :" + rate);
        processfeedback(id, rate);

    }


    public void Cancel(View v)
    {
        // This function is called when button is clicked.
        // Display ratings, which is required to be converted into string first.
        String status="Cancelled";
        Log.d(TAG, "Order Status :" + status);
        Log.d(TAG, "Order ID :" + id);
        processstatus(id, status);


    }


    public void processfeedback(String id, String rate){
        Call<login_response_model> call=apicontroller.getInstance()
                .getapi()
                .getfeedback(id,rate);

        call.enqueue(new Callback<login_response_model>() {
            @Override
            public void onResponse(Call<login_response_model> call, Response<login_response_model> response) {
                login_response_model obj=response.body();
                String result=obj.getMessage().trim();
                if(result.equals("inserted"))
                {
                    TextView t = (TextView)findViewById(R.id.textView2);
                    t.setText("Thank You for your feedback :"+String.valueOf(rt.getRating()));                }
                if(result.equals("failed"))
                {
                    TextView t = (TextView)findViewById(R.id.textView2);
                    t.setText("Sorry...! Unable to update :"+String.valueOf(rt.getRating()));
                }
            }

            @Override
            public void onFailure(Call<login_response_model> call, Throwable t) {
                TextView t2 = (TextView)findViewById(R.id.textView2);
                t2.setText("Something went wrong :"+ t);

            }
        });

    }


    public void processstatus(String id, String status){
        Log.d(TAG, "Order Status :" + status);
        Log.d(TAG, "Order ID :" + id);
        Call<login_response_model> call=apicontroller.getInstance()
                .getapi()
                .getstatus(id,status);

        call.enqueue(new Callback<login_response_model>() {
            @Override
            public void onResponse(Call<login_response_model> call, Response<login_response_model> response) {
                login_response_model obj=response.body();
                String result=obj.getMessage().trim();
                if(result.equals("inserted"))
                {
                    TextView tt = (TextView)findViewById(R.id.textView3);
                    tt.setText("Your order has been successfully Cancelled :");
                }
                if(result.equals("failed"))
                {
                    TextView tt = (TextView)findViewById(R.id.textView3);
                    tt.setText("Sorry...! Unable to update Status :");
                }
            }

            @Override
            public void onFailure(Call<login_response_model> call, Throwable t) {
                TextView tt2 = (TextView)findViewById(R.id.textView3);
                tt2.setText("Something went wrong :"+ t);

            }
        });

    }

}