package com.shahnizarbaloch.forifixer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shahnizarbaloch.forifixer.R;
import com.shahnizarbaloch.forifixer.model.itemDetail;

import java.util.ArrayList;

public class itemRecyclerView extends RecyclerView.Adapter<itemRecyclerView.MyViewHolder>{

    private ArrayList<itemDetail> arrayList;
    private Context context;


    public itemRecyclerView(ArrayList<itemDetail> arrayList, Context context) {
        this.arrayList=arrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public itemRecyclerView.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") final View view = inflater.inflate(R.layout.design_order_item, null, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull itemRecyclerView.MyViewHolder myViewHolder, int i) {

        itemDetail obj= arrayList.get(i);
        myViewHolder.itemName.setText(obj.getItemName());
        myViewHolder.itemPrice.setText(obj.getItemPrice());

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout itemDetail;
        TextView itemName,itemPrice;
        RecyclerView rv;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemDetail = itemView.findViewById(R.id.item_rv);
            itemName=itemView.findViewById(R.id.tv_item_name);
            itemPrice=itemView.findViewById(R.id.tv_item_price);
            rv = itemView.findViewById(R.id.item_recycler_view);
        }
    }
}
