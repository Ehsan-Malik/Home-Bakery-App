package com.kazimasum.ecommdemo;
import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;

import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class userOrders extends AppCompatActivity {
    public static String mobile;
    RecyclerView orderrecview;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView nav;
    Button btnfeedback;
    ordersadapter.RecyclerViewClickListener listener;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_orders);
        setTitle("Home Bakery App");

        nav=findViewById(R.id.navmenuview);
        drawerLayout=findViewById(R.id.my_drawer_layout);
        btnfeedback= findViewById(R.id.feedback);

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
                }

                return true;
            }
        });




        orderrecview= findViewById(R.id.orderrecview);
        orderrecview.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sp=getSharedPreferences("credentials",MODE_PRIVATE);
        mobile = sp.getString("mobile", "defaultValue");
        processdata();


    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void processdata(){
        Log.d(TAG, "\n\nMobile :" + dashboard.mobile);
        Call<List<ordermodel>> call=apicontroller.getInstance().getapi().getorders(mobile);
        call.enqueue(new Callback<List<ordermodel>>() {
            @Override
            public void onResponse(Call<List<ordermodel>> call, Response<List<ordermodel>> response) {
                List<ordermodel> data=response.body();

                listener =new ordersadapter.RecyclerViewClickListener() {
                    @Override
                    public void onClick(View v, int position) {
                        Intent intent=new Intent(getApplicationContext(), feedback.class);
                        intent.putExtra("id", data.get(position).getId());



                        //  intent.putExtra("imagename",temp.getImgname());

                        startActivity(intent);

                    }
                };




                ordersadapter adapter= new ordersadapter(data, listener);
                orderrecview.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<ordermodel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();
            }
        });

    }

}

