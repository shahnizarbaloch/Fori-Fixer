package com.shahnizarbaloch.forifixer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shahnizarbaloch.forifixer.R;
import com.shahnizarbaloch.forifixer.activity.OrderDetailActivity;
import com.shahnizarbaloch.forifixer.model.getOrderBy;

import java.util.ArrayList;

public class OrderRecyclerView extends RecyclerView.Adapter<OrderRecyclerView.MyViewHolder>{

    private ArrayList<getOrderBy> arrayList;
    private Context context;


    public OrderRecyclerView(ArrayList<getOrderBy> arrayList, Context context) {
        this.arrayList=arrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public OrderRecyclerView.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") final View view = inflater.inflate(R.layout.design_order_by_home_screen, null, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderRecyclerView.MyViewHolder myViewHolder, final int i) {
        final getOrderBy obj = arrayList.get(i);
        myViewHolder.tvName.setText(obj.getName());
        myViewHolder.tvNumber.setText(obj.getNumber());
        myViewHolder.tvEmail.setText(obj.getEmail());
        myViewHolder.tvLocation.setText(obj.getLocation());
        myViewHolder.tvTotal.setText(obj.getTotal());

        myViewHolder.orderBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Key=obj.getId();
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("Key",Key);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout orderBy;
        TextView tvName,tvNumber,tvEmail,tvLocation,tvTotal;
        RecyclerView rv;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            orderBy = itemView.findViewById(R.id.item_rv);
            tvName=itemView.findViewById(R.id.tv_name);
            tvNumber=itemView.findViewById(R.id.tv_contact_number);
            tvEmail=itemView.findViewById(R.id.tv_email_address);
            tvLocation=itemView.findViewById(R.id.tv_location);
            tvTotal=itemView.findViewById(R.id.total);
            rv = itemView.findViewById(R.id.submitted_order_rv);
        }
    }
}
