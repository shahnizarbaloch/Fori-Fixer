package com.shahnizarbaloch.forifixer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.shahnizarbaloch.forifixer.R;
import com.shahnizarbaloch.forifixer.activity.CategorySwitcher;
import com.shahnizarbaloch.forifixer.fragment.main_fragments.OrdersFragment;
import com.shahnizarbaloch.forifixer.model.Category;

import java.util.ArrayList;


public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MyViewHolder>{

    private ArrayList<Category> arrayList;
    private Context context;

    public MainRecyclerViewAdapter(ArrayList<Category> arrayList, Context context){
        this.arrayList=arrayList;
        this.context=context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") final View view = inflater.inflate(R.layout.design_main_item, null, false);

        final MyViewHolder myViewHolder = new MyViewHolder(view);

        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Category obj = arrayList.get(myViewHolder.getAdapterPosition());
                String subCategories = obj.getName();
                Intent intent = new Intent(context, CategorySwitcher.class);
                switch (subCategories){
                    case "Carpenter":
                        intent.putExtra("fragment","Carpenter");
                        context.startActivity(intent);
                        break;
                    case "Electrician":
                        intent.putExtra("fragment","Electrician");
                        context.startActivity(intent);
                        break;
                    case "Mechanical":
                        Toast.makeText(context, "Not Available Now! Sorry!", Toast.LENGTH_SHORT).show();
                        /*intent.putExtra("fragment","Mechanical");
                        context.startActivity(intent);*/
                        break;
                    case "Painter":
                        intent.putExtra("fragment","Painter");
                        context.startActivity(intent);
                        break;
                    case "Plumber":
                        intent.putExtra("fragment","Plumber");
                        context.startActivity(intent);
                        break;



                }
            }
        });


        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        //Getting the current adapter position, through which we can get current item name etc.
        final Category obj = arrayList.get(i);

        myViewHolder.item_category_name.setText(obj.getName());
        myViewHolder.imageView.setImageResource(obj.getImage());

        switch (obj.getName()){
            case "Carpenter":
                myViewHolder.cardView.setBackgroundResource(R.drawable.card_background_one);
                break;
            case "Electrician":
                myViewHolder.cardView.setBackgroundResource(R.drawable.card_background_two);
                break;
            case "Mechanical":
                myViewHolder.cardView.setBackgroundResource(R.drawable.card_background_three);
                break;
            case "Painter":
                myViewHolder.cardView.setBackgroundResource(R.drawable.card_background_four);
                break;
            case "Plumber":
                myViewHolder.cardView.setBackgroundResource(R.drawable.card_background_five);
                break;

        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout itemCategory;
        CardView cardView;
        TextView item_category_name;
        RecyclerView rv;
        ImageView imageView;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemCategory = itemView.findViewById(R.id.item_rv);
            cardView = itemView.findViewById(R.id.cardview_main_screen);
            item_category_name = itemView.findViewById(R.id.tv_item_name);
            imageView = itemView.findViewById(R.id.icon_recycler_view);
            rv = itemView.findViewById(R.id.rv_home_categories);

        }
    }

}




