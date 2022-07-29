package com.kazimasum.ecommdemo;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ordersadapter extends RecyclerView.Adapter<ordersadapter.myviewholder> {
    List<ordermodel> data;
    Context context;

    public ordersadapter(List<ordermodel> data) {
        this.data = data;
    }

    private ordersadapter.RecyclerViewClickListener listener;

    public ordersadapter(List<ordermodel> data, RecyclerViewClickListener listener) {
        this.data=data;
        this.listener=listener;
    }





    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderrow, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.orderid.setText(data.get(position).getId());
        holder.orderdate.setText(data.get(position).getOrderdate());
        holder.orderdetails.setText(data.get(position).getOrderdetails());
        holder.orderprice.setText(data.get(position).getAmount());
        holder.paymentstatus.setText(data.get(position).getPaymentstatus());
        holder.orderstatus.setText(data.get(position).getOrderstatus());
        holder.feedback.setText(data.get(position).getFeedback());
        holder.shop.setText(data.get(position).getShop());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class myviewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView orderid, orderdate, orderdetails, orderprice, paymentstatus, orderstatus, feedback, shop;


        public myviewholder(@NonNull View itemView) {
            super(itemView);
            orderid = itemView.findViewById(R.id.orderid);
            orderdate = itemView.findViewById(R.id.orderdate);
            orderdetails = itemView.findViewById(R.id.orderdetails);
            orderprice = itemView.findViewById(R.id.orderprice);
            paymentstatus = itemView.findViewById(R.id.paymentstatus);
            orderstatus = itemView.findViewById(R.id.orderstatus);
            feedback = itemView.findViewById(R.id.feedback);
            shop = itemView.findViewById(R.id.shop);
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
