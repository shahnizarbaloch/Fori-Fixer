package com.shahnizarbaloch.forifixer.adapter.sub_category;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.shahnizarbaloch.forifixer.R;
import com.shahnizarbaloch.forifixer.fragment.add_to_cart_fragments.atcPlumber_1_Fragment;
import com.shahnizarbaloch.forifixer.fragment.add_to_cart_fragments.atcPlumber_2_Fragment;
import com.shahnizarbaloch.forifixer.fragment.add_to_cart_fragments.atcPlumber_3_Fragment;
import com.shahnizarbaloch.forifixer.fragment.add_to_cart_fragments.atcPlumber_4_Fragment;
import com.shahnizarbaloch.forifixer.model.Category;

import java.util.ArrayList;

public class PlumberRecycler extends RecyclerView.Adapter<PlumberRecycler.MyViewHolder> {

    private ArrayList<Category> arrayList;
    private Context context;

    public PlumberRecycler(ArrayList<Category> arrayList, Context context){
        this.arrayList=arrayList;
        this.context=context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") final View view = inflater.inflate(R.layout.rv_categories_item, null, false);
        //AppCompatActivity is for transaction of fragments
        final AppCompatActivity activity = (AppCompatActivity) view.getContext();
        //Here Adding Animation to The Fragments
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        myViewHolder.itemCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (myViewHolder.getAdapterPosition()){
                    case 0:
                        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right);
                        fragmentTransaction.replace(R.id.container_switcher,new atcPlumber_1_Fragment(),"");
                        fragmentTransaction.addToBackStack(null).commit();
                        break;
                    case 1:
                        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right);
                        fragmentTransaction.replace(R.id.container_switcher,new atcPlumber_2_Fragment(),"");
                        fragmentTransaction.addToBackStack(null).commit();
                        break;
                    case 2:
                        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right);
                        fragmentTransaction.replace(R.id.container_switcher,new atcPlumber_3_Fragment(),"");
                        fragmentTransaction.addToBackStack(null).commit();
                        break;
                    case 3:
                        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right);
                        fragmentTransaction.replace(R.id.container_switcher,new atcPlumber_4_Fragment(),"");
                        fragmentTransaction.addToBackStack(null).commit();
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
