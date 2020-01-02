package com.shahnizarbaloch.forifixer.adapter.sub_category;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.shahnizarbaloch.forifixer.R;
import com.shahnizarbaloch.forifixer.model.Category;

import java.util.ArrayList;

public class MechanicalRecycler extends RecyclerView.Adapter<MechanicalRecycler.MyViewHolder> {

    private ArrayList<Category> arrayList;
    private Context context;

    public MechanicalRecycler(ArrayList<Category> arrayList, Context context){
        this.arrayList=arrayList;
        this.context=context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") final View view = inflater.inflate(R.layout.rv_categories_item, null, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        myViewHolder.itemCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (myViewHolder.getAdapterPosition()){
                    case 0:
                        Toast.makeText(context, "0", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(context, "1", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(context, "2", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(context, "3", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(context, "4", Toast.LENGTH_SHORT).show();
                        break;


                }
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Category obj = arrayList.get(i);
        myViewHolder.item_category_name.setText(obj.getName());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout itemCategory;
        TextView item_category_name;
        RecyclerView rv;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemCategory = itemView.findViewById(R.id.item_rv);
            item_category_name = itemView.findViewById(R.id.tv_item_name);
            rv = itemView.findViewById(R.id.rv_home_categories);

        }
    }
}
