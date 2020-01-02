package com.shahnizarbaloch.forifixer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.shahnizarbaloch.forifixer.R;
import com.shahnizarbaloch.forifixer.database.DatabaseHelper;
import com.shahnizarbaloch.forifixer.model.AddToCart;

import java.util.ArrayList;


public class OrderItemsRecyclerViewAdapter extends RecyclerView.Adapter<OrderItemsRecyclerViewAdapter.MyViewHolder>{

    private ArrayList<AddToCart> arrayList;
    private Context context;
    private DatabaseHelper db;
    public OrderItemsRecyclerViewAdapter(ArrayList<AddToCart> arrayList, Context context){
        this.arrayList=arrayList;
        this.context=context;
        db = new DatabaseHelper(context);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") final View view = inflater.inflate(R.layout.rv_order_item, null, false);

        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, @SuppressLint("RecyclerView") final int position) {
        AddToCart obj = arrayList.get(position);
        myViewHolder.itemName.setText(obj.getHeading());
        myViewHolder.itemPrice.setText("Rs. "+obj.getPrice());
        myViewHolder.itemRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout itemList;
        TextView itemName,itemPrice,itemRemove;
        RecyclerView rv;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemList=itemView.findViewById(R.id.item_rv);
            itemName=itemView.findViewById(R.id.tv_item_name);
            itemPrice=itemView.findViewById(R.id.tv_item_price);
            itemRemove=itemView.findViewById(R.id.tv_item_remove);
            rv = itemView.findViewById(R.id.rv_order_categories);
        }
    }



    @SuppressLint("SetTextI18n")
    private void deleteData(int position) {
        AddToCart n = arrayList.get(position);
        long check = db.delete(n.getId());
        if (check > 0) {
            //Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
            arrayList.remove(position);
            notifyDataSetChanged();
        } else {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }
    }

}




