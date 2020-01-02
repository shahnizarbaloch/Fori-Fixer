package com.shahnizarbaloch.forifixer.adapter.sub_category;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.shahnizarbaloch.forifixer.R;
import com.shahnizarbaloch.forifixer.activity.CategorySwitcher;
import com.shahnizarbaloch.forifixer.activity.MainActivity;
import com.shahnizarbaloch.forifixer.database.DatabaseHelper;
import com.shahnizarbaloch.forifixer.model.AddToCart;

import java.util.ArrayList;

public class PainterRecycler extends RecyclerView.Adapter<PainterRecycler.MyViewHolder> {

    private ArrayList<AddToCart> arrayList;
    private Context context;

    public PainterRecycler(ArrayList<AddToCart> arrayList, Context context){
        this.arrayList=arrayList;
        this.context=context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") final View view = inflater.inflate(R.layout.rv_add_to_cart_item, null, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        final DatabaseHelper db = new DatabaseHelper(context);
        myViewHolder.addPrice.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                db.insert(context,new AddToCart(myViewHolder.Heading.getText().toString(),myViewHolder.Price.getText().toString()));
                Toast.makeText(context, myViewHolder.Heading.getText().toString()+" has been Added!", Toast.LENGTH_SHORT).show();
                MainActivity.mBadge.setNumber(db.cartItems());
                CategorySwitcher.mBadge.setNumber(db.cartItems());
                myViewHolder.addPrice.setText("Added!");
                myViewHolder.addPrice.setTextColor(Color.RED);
            }
        });
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        AddToCart obj = arrayList.get(i);
        myViewHolder.Heading.setText(obj.getHeading());
        myViewHolder.SubHeading.setText(obj.getSubHeading());
        myViewHolder.Price.setText(obj.getPrice());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rv_add_to_cart;
        TextView Heading,SubHeading,Rs,Price,addPrice;;
        RecyclerView rv;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rv_add_to_cart = itemView.findViewById(R.id.rl_add_to_cart);
            Heading = itemView.findViewById(R.id.atc_heading);
            SubHeading=itemView.findViewById(R.id.atc_detail);
            Rs=itemView.findViewById(R.id.atc_rs_label);
            Price=itemView.findViewById(R.id.atc_rs_price);
            addPrice=itemView.findViewById(R.id.addPrice);
            rv=itemView.findViewById(R.id.rv_add_to_cart);

        }
    }
}
