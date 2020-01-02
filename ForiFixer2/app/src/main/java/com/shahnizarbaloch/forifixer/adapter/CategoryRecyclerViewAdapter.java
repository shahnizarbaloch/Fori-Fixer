package com.shahnizarbaloch.forifixer.adapter;

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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.shahnizarbaloch.forifixer.R;
import com.shahnizarbaloch.forifixer.fragment.sub_fragment.SubCarpenterFragment;
import com.shahnizarbaloch.forifixer.fragment.sub_fragment.SubElectricianFragment;
import com.shahnizarbaloch.forifixer.fragment.sub_fragment.SubPainterFragment;
import com.shahnizarbaloch.forifixer.fragment.sub_fragment.SubPlumberFragment;
import com.shahnizarbaloch.forifixer.model.Category;

import java.util.ArrayList;


public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.MyViewHolder>{

    private ArrayList<Category> arrayList;
    private Context context;

    public CategoryRecyclerViewAdapter(ArrayList<Category> arrayList, Context context){
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
                        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right);
                        fragmentTransaction.replace(R.id.fragment_container,new SubCarpenterFragment(),"");
                        fragmentTransaction.addToBackStack(null).commit();
                        break;
                    case 1:

                        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right);
                        fragmentTransaction.replace(R.id.fragment_container,new SubElectricianFragment(),"");
                        fragmentTransaction.addToBackStack(null).commit();
                        break;
                    case 2:
                        Toast.makeText(activity, "Not Available Now! Sorry!", Toast.LENGTH_SHORT).show();
                        /*fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right);
                        fragmentTransaction.replace(R.id.fragment_container,new SubMechanicalFragment(),"");
                        fragmentTransaction.addToBackStack(null).commit();*/
                        break;
                    case 3:
                        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right);
                        fragmentTransaction.replace(R.id.fragment_container,new SubPainterFragment(),"");
                        fragmentTransaction.addToBackStack(null).commit();
                        break;
                    case 4:
                        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right);
                        fragmentTransaction.replace(R.id.fragment_container,new SubPlumberFragment(),"");
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
        myViewHolder.imageView.setImageResource(obj.getImage());


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout itemCategory;
        TextView item_category_name;
        RecyclerView rv;
        ImageView imageView;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemCategory = itemView.findViewById(R.id.item_rv);
            item_category_name = itemView.findViewById(R.id.tv_item_name);
            imageView = itemView.findViewById(R.id.icon_recycler_view);
            rv = itemView.findViewById(R.id.rv_home_categories);

        }
    }

}




