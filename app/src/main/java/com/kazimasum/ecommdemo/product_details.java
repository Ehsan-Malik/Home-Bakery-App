package com.kazimasum.ecommdemo;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class product_details extends AppCompatActivity{
    int count=1;
    int totalprice, unitprice;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView nav;
    List<responsemodel> data;
    private myadapter.myviewholder holder;
    public static String shop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        setTitle("Home Bakery App");


        nav=findViewById(R.id.navmenuview);
        drawerLayout=findViewById(R.id.my_drawer_layout);
        TextView lbl=findViewById(R.id.label);

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




        TextView nameText = findViewById(R.id.name);
        TextView descText = findViewById(R.id.pdesc);
        ImageView prodimage = findViewById(R.id.pimg);
        TextView prodprice = findViewById(R.id.price);
        TextView prid = findViewById(R.id.pid);



        ImageView icr = findViewById(R.id.incbtn);
        ImageView dcr = findViewById(R.id.decbtn);




        icr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count>29)
                {
                    Toast.makeText(getApplicationContext(), "Maximum Order Limit Reached", Toast.LENGTH_LONG).show();
                    count=30;
                    displaycount();
                    displayprice();

                }
                else {
                    count++;
                    displaycount();
                    displayprice();
                }
            }
        });



        dcr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count==1)
                {
                    displaycount();
                    displayprice();
                }
                else {
                    count--;
                    displaycount();
                    displayprice();
                }
            }
        });

        String pName = "Product Name not set";
        String pdesc = "No Description available";
        String pimage = "No Image available";
        String pprice = "Price not Set";
        String pid = "ID not Set";



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (getIntent().hasExtra("pimage")) {
                Log.d(TAG, "Image found in intent");
            }
            pName = extras.getString("pname");
            pdesc = extras.getString("pdesc");
            pimage = extras.getString("pimage");
            pprice = extras.getString("pprice");
            pid = extras.getString("pid");
            shop = extras.getString("shop");
            Log.d(TAG, "Shop :" +shop);



        }


        //prodimage.setImageDrawable(Drawable.createFromPath(photo));

        descText.setText(pdesc);
        nameText.setText(pName);
        pimage = "https://trafficwardenatd.com/bakeryapi/admin/productimages/" + pimage;

        Glide.with(this).asBitmap().load(pimage).into(prodimage);



        unitprice=Integer.parseInt(pprice);
        displayprice();



        Button cartBtn;
        cartBtn = findViewById(R.id.btncart);
        Log.d(TAG, "Button Referenced");

        Button checkBtn;
        checkBtn = findViewById(R.id.btncheckout);

        Log.d(TAG, "Button2 Referenced");

        String finalPName = pName;
        String finalPid = pid;

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, ""+totalprice);
                Log.d(TAG, ""+count);
                Log.d(TAG, finalPName);
                Log.d(TAG, finalPid);


                AppDatabase db= Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"cart_db").allowMainThreadQueries().build();
                ProductDao productDao=db.ProductDao();
                Boolean check=productDao.is_exist(Integer.parseInt(finalPid));
                if(check==false)
                {
                    int pid=Integer.parseInt(finalPid);
                    String pname=finalPName;
                    int price=Integer.parseInt(String.valueOf(totalprice));
                    int qnt=Integer.parseInt(String.valueOf(count));
                    productDao.insertrecord(new Product(pid,pname,price,qnt));


                    lbl.setText("Product Successfully Added to Cart");
                }
                else
                {

                    lbl.setText("Product Already in Cart");
                }




            }
        });




        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "I am in checkout listener");
                Intent intent=new Intent(getApplicationContext(), checkout.class);
                startActivity(intent);
            }

        });









    }

void displaycount()
{
    TextView qunatity=findViewById(R.id.recqnt);
    qunatity.setText(""+count);
}

    void displayprice()
    {
        totalprice=unitprice*count;
        TextView price=findViewById(R.id.price);
        price.setText(""+totalprice);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}






