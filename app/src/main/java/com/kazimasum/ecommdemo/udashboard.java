package com.kazimasum.ecommdemo;
import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;

import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class udashboard extends AppCompatActivity {
    List<responsemodel> data;
    Context context;
    public myadapter adapter;
    RecyclerView recview;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView nav;
    myadapter.RecyclerViewClickListener listener;
    public static String mobile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udashboard);
        setTitle("Home Bakery App");


        SharedPreferences sp=getSharedPreferences("credentials",MODE_PRIVATE);
        mobile = sp.getString("mobile", "defaultValue");
        Log.d(TAG, "Contact Number :" + mobile);


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
                        Toast.makeText(udashboard.this, "Please Login First!",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_my_cart:
                        Toast.makeText(udashboard.this, "Please Login First!",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_account:
                        Toast.makeText(udashboard.this, "Please Login First!",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_my_orders:
                        Toast.makeText(udashboard.this, "Please Login First!",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_all_categories:
                        startActivity(new Intent(getApplicationContext(),ucategory.class));
                        break;
                    case R.id.nav_bakeries:
                        startActivity(new Intent(getApplicationContext(),ubakery.class));
                        break;
                }

                return true;
            }
        });

        recview= findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        processdata();

    }


    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }








    public void processdata(){
        Call<List<responsemodel>> call=apicontroller.getInstance().getapi().getdata();
        call.enqueue(new Callback<List<responsemodel>>() {
            @Override
            public void onResponse(Call<List<responsemodel>> call, Response<List<responsemodel>> response) {
                List<responsemodel> data=response.body();

                listener =new myadapter.RecyclerViewClickListener() {
                    @Override
                    public void onClick(View v, int position) {
                        Toast.makeText(udashboard.this, "Please Login First!",Toast.LENGTH_SHORT).show();

                    }
                };





                adapter= new myadapter(data, listener);
                recview.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<responsemodel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();

            }
        });









    }








}