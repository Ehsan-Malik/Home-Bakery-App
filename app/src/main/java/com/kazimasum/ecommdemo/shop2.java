package com.kazimasum.ecommdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class shop2 extends AppCompatActivity {
    public myadapter adapter;
    RecyclerView recview;
    myadapter.RecyclerViewClickListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop2);
        setTitle("Home Bakery App");
        recview= findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        processdata();
    }


    public void processdata(){
        Call<List<responsemodel>> call=apicontroller.getInstance().getapi().getshop2data();
        call.enqueue(new Callback<List<responsemodel>>() {
            @Override
            public void onResponse(Call<List<responsemodel>> call, Response<List<responsemodel>> response) {
                List<responsemodel> data=response.body();

                listener =new myadapter.RecyclerViewClickListener() {
                    @Override
                    public void onClick(View v, int position) {
                        Intent intent=new Intent(getApplicationContext(), product_details.class);
                        intent.putExtra("pimage", data.get(position).getPimage());
                        intent.putExtra("pname", data.get(position).getPname());
                        intent.putExtra("pdesc", data.get(position).getPdesc());
                        intent.putExtra("pprice", data.get(position).getPprice());
                        intent.putExtra("pid", data.get(position).getId());
                        intent.putExtra("shop", data.get(position).getShop());




                        //  intent.putExtra("imagename",temp.getImgname());

                        startActivity(intent);

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