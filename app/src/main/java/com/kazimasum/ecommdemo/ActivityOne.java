package com.kazimasum.ecommdemo;

import static android.content.ContentValues.TAG;

import static java.util.Collections.addAll;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityOne extends AppCompatActivity {
    List<responsemodel> data;
    Context context;
    myadapter adapter;
    RecyclerView recview;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView nav;
    myadapter.RecyclerViewClickListener listener;
    public static String mobile;
    String info="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

            info = getIntent().getStringExtra("info");
            Log.d(TAG, "Category :" + info);




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
        Call<List<responsemodel>> call=apicontroller.getInstance().getapi().getcategory(info);
        call.enqueue(new Callback<List<responsemodel>>() {
            @Override
            public void onResponse(Call<List<responsemodel>> call, Response<List<responsemodel>> response) {
                List<responsemodel> data=(response.body());

                listener =new myadapter.RecyclerViewClickListener() {
                    @Override
                    public void onClick(View v, int position) {
                        Intent intent=new Intent(getApplicationContext(), product_details.class);
                        intent.putExtra("pimage", data.get(position).getPimage());
                        intent.putExtra("pname", data.get(position).getPname());
                        intent.putExtra("pdesc", data.get(position).getPdesc());
                        intent.putExtra("pprice", data.get(position).getPprice());
                        intent.putExtra("pid", data.get(position).getId());



                        //  intent.putExtra("imagename",temp.getImgname());

                        startActivity(intent);

                    }
                };





                myadapter adapter= new myadapter(data, listener);
                recview.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<responsemodel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();

            }
        });

    }


}