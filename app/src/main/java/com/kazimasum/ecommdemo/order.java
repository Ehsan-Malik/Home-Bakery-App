package com.kazimasum.ecommdemo;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class order extends AppCompatActivity {
    public static String myorder=checkout.orderID;

    String paymentmethod=" ";
    myadapter adapter;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView nav;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button confirmOrder;
    EditText addressBar;
    String address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Log.d("ID ",""+myorder);
        setTitle("Order Confirmation");
        radioGroup = findViewById(R.id.paymethod);
        confirmOrder = findViewById(R.id.confirmorder);
        addressBar = findViewById(R.id.editTextAddress);

        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedID = radioGroup.getCheckedRadioButtonId();
                if(TextUtils.isEmpty(addressBar.getText()))
                {
                    addressBar.setError("Please Enter the Delivery Address");
                }else {
                    address=addressBar.getText().toString();
                }

                if((selectedID!=-1)) {
                    radioButton = findViewById(selectedID);
                    if (radioButton.getText().equals("Easypaisa")) {
                        paymentmethod="Easypaisa";
                        Log.d(TAG, "My Radio Button" + selectedID);

                    } else if (radioButton.getText().equals("Cash On Delivery")) {
                        paymentmethod="Cash On Delivery";
                        Log.d(TAG, "My Radio Button" + selectedID);
                    } else {
                        Log.d(TAG, "My Radio Button" + selectedID);
                        Toast.makeText(order.this, "Select Payment method first!",Toast.LENGTH_SHORT).show();

                    }



                }

                else{
                    Log.d(TAG, "My Radio Button" + selectedID);
                    Toast.makeText(order.this, "Select Payment method first!",Toast.LENGTH_SHORT).show();

                }

            if(paymentmethod!=" "&& address!=null){
                if(paymentmethod=="Easypaisa"){
                    delivery(checkout.orderID,paymentmethod,address);
                    Log.d("Check out Order ID ",""+checkout.orderID);
                    startActivity(new Intent(getApplicationContext(), easyPaisa.class));
                }
                else{
                    delivery(checkout.orderID,paymentmethod,address);
                    startActivity(new Intent(getApplicationContext(), orderSuccess.class));
                }

            }
            else {
                Toast.makeText(order.this, "Please fill out the required forms!",Toast.LENGTH_SHORT).show();
            }



                Log.d(TAG, "\n\nPayment Method :" + paymentmethod);
                Log.d(TAG, "Delivery Address :" + address);

                SharedPreferences sp=getSharedPreferences("credentials",MODE_PRIVATE);
                String mobile = sp.getString("mobile", "defaultValue");
                Log.d(TAG, "Contact Number :" + mobile);

                Log.d("ID ",""+myorder);


            }
        });

        nav=findViewById(R.id.navmenuview);
        drawerLayout=findViewById(R.id.my_drawer_layout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_logout:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        SharedPreferences sp=getSharedPreferences("credentials",MODE_PRIVATE);
                        sp.edit().remove("mobile").commit();
                        sp.edit().remove("password").commit();
                        sp.edit().apply();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                        break;
                    case R.id.nav_my_cart:
                        startActivity(new Intent(getApplicationContext(),checkout.class));
                        break;
                    case R.id.nav_account:
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        break;
                    case R.id.nav_my_orders:
                        startActivity(new Intent(getApplicationContext(),userOrders.class));
                        break;
                    case R.id.nav_all_categories:
                        startActivity(new Intent(getApplicationContext(),categories.class));
                        break;

                    case R.id.nav_bakeries:
                        startActivity(new Intent(getApplicationContext(),bakery.class));
                        break;
                }

                return true;
            }
        });








    }


    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void delivery(String id, String paymentmethod, String address)
    {

        Call<orderresponsemodel> call=apicontroller.getInstance()
                .getapi()
                .delivery(id,paymentmethod,address);

        call.enqueue(new Callback<orderresponsemodel>() {
            @Override
            public void onResponse(Call<orderresponsemodel> call, Response<orderresponsemodel> response) {
                orderresponsemodel obj=response.body();
                String result=obj.getMessage().trim();
                myorder=result;
            }

            @Override
            public void onFailure(Call<orderresponsemodel> call, Throwable t) {
                Toast.makeText(order.this, "Something went wrong",Toast.LENGTH_SHORT).show();


            }
        });
    }
}