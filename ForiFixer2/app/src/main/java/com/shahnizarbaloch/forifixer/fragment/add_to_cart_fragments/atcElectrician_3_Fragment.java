package com.shahnizarbaloch.forifixer.fragment.add_to_cart_fragments;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.shahnizarbaloch.forifixer.R;
import com.shahnizarbaloch.forifixer.adapter.add_to_cart.ItemRecyclerView;
import com.shahnizarbaloch.forifixer.fragment.main_fragments.OrdersFragment;
import com.shahnizarbaloch.forifixer.model.AddToCart;

import java.util.ArrayList;
import java.util.Objects;

public class atcElectrician_3_Fragment extends Fragment {
    //ArrayList of Everything Class
    private ArrayList<AddToCart> arrayList;
    //View which will be used in some other places
    View view;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.atc_fragment , container,false);
        //Recycler View in Fragment
        RecyclerView recyclerView = view.findViewById(R.id.rv_add_to_cart);
        ItemRecyclerView adapter = new ItemRecyclerView(getContext(),arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(Objects.requireNonNull(getContext()),DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayList= new ArrayList<>();
        //Adding Things Here.. Means when we patch data from firebase then we can add them here ;)
        arrayList.add(new AddToCart("Ceiling / wall Lights","Rs. 250 per light","250"));
        arrayList.add(new AddToCart("Flat Screen TV Installation","Starting From Rs. 15 per inch","15"));
        arrayList.add(new AddToCart("LED Light Installation","Rs. 250 per light","250"));
        arrayList.add(new AddToCart("LED Light Installation Concealed","Starting From Rs. 1500" +
                "Actual Amount to be quoted upon survey","1500"));
        arrayList.add(new AddToCart("LED Light Installation Open","Rs. 450 per light","450"));
        arrayList.add(new AddToCart("Spot Light Installation","Rs. 500 per light","500"));



    }
}
