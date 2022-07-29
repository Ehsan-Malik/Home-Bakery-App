package com.kazimasum.ecommdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder>{
    List<responsemodel> data;
    List<responsemodel> dataFull;



    Context context;
    myadapter adapter;
    private RecyclerViewClickListener listener;


    public myadapter(List<responsemodel> data, RecyclerViewClickListener listener) {
        this.data=data;
        this.listener=listener;
    }
    public myadapter(Context context, List<responsemodel> data) {
        this.context=context;
        this.data=data;
        dataFull=new ArrayList<>(data);
    }



    public myadapter(List<responsemodel> data) {
        this.data=data;
    }



    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow, parent, false);
        return new myviewholder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.t1.setText(data.get(position).getPname());
        holder.t2.setText(data.get(position).getPprice());
        holder.shop.setText(data.get(position).getShop());

        Glide.with(holder.t1.getContext()).load("https://trafficwardenatd.com/bakeryapi/admin/productimages/"+data.get(position).getPimage()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }




    class myviewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img;
        TextView t1, t2, shop;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
            t1=itemView.findViewById(R.id.t1);
            t2=itemView.findViewById(R.id.t2);
            shop=itemView.findViewById(R.id.shop);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View itemView) {
            listener.onClick(itemView, getAdapterPosition());

        }
    }

public interface RecyclerViewClickListener{
        void onClick(View v, int position);
}

}
