package com.kazimasum.ecommdemo;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class checkout extends AppCompatActivity {
    public static String orderID;
    int sum=0;
    String details=" ";
    Button placeOrder;
    RecyclerView recview;
    TextView rateview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        getSupportActionBar().hide(); // hide the title bar

        placeOrder=findViewById(R.id.placeorder);
        rateview=findViewById(R.id.rateview);
        getroomdata();


        String mobile=dashboard.mobile;
        String shop=product_details.shop;

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "\nI am Sending this data :" );
                Log.d(TAG, "Contact Number :" + mobile);
                Log.d(TAG, "Order Details :" + details);
                Log.d(TAG, "Price :" + sum);
                Log.d(TAG, "Shop :" + shop);

                if(sum<1){
                    Toast.makeText(checkout.this, "Please add some products!",Toast.LENGTH_SHORT).show();
                }
                else {
                    String amount=Integer.toString(sum);
                    placeorder(mobile, details, amount, shop);
                    startActivity(new Intent(getApplicationContext(), order.class));
                }

                    }
        });


    }





    public void getroomdata()
    {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "cart_db").allowMainThreadQueries().build();
        ProductDao productDao = db.ProductDao();

        recview=findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        List<Product> products=productDao.getallproduct();

        cartadapter adapter=new cartadapter(products, rateview);
        recview.setAdapter(adapter);
        int i;
        for(i=0;i< products.size();i++) {
            sum = sum + (products.get(i).getPrice() * products.get(i).getQnt());
            details = details + (products.get(i).getQnt() +" "+ products.get(i).getPname()+"\n");
        }
        rateview.setText("Total Amount : PKR "+sum);
        Log.d(TAG, "Order Details :" + details);



    }


    public void placeorder(String mobile, String details, String price, String shop)
    {

        Call<orderresponsemodel> call=apicontroller.getInstance()
                .getapi()
                .placeorder(mobile,details,price,shop);

        call.enqueue(new Callback<orderresponsemodel>() {
            @Override
            public void onResponse(Call<orderresponsemodel> call, Response<orderresponsemodel> response) {
                orderresponsemodel obj=response.body();
                String result=obj.getMessage().trim();
                orderID=result;
                Log.d("ID ",""+orderID);

            }

            @Override
            public void onFailure(Call<orderresponsemodel> call, Throwable t) {
                Toast.makeText(checkout.this, "Something went wrong",Toast.LENGTH_SHORT).show();


            }
        });
    }


}