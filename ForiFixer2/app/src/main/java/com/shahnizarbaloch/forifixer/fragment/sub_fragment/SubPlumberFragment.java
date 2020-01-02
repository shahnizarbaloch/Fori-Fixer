package com.shahnizarbaloch.forifixer.fragment.sub_fragment;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.shahnizarbaloch.forifixer.R;
import com.shahnizarbaloch.forifixer.adapter.sub_category.PlumberRecycler;
import com.shahnizarbaloch.forifixer.model.Category;

import java.util.ArrayList;
import java.util.Objects;

public class SubPlumberFragment extends Fragment {
    //ArrayList of Everything Class
    private ArrayList<Category> arrayList;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View which will be used in some other places
        View view = inflater.inflate(R.layout.fragment_plumber, container, false);
        //Recycler View in Fragment
        RecyclerView recyclerView = view.findViewById(R.id.rv_home_categories);
        PlumberRecycler adapter = new PlumberRecycler(arrayList,getContext());
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
        arrayList.add(new Category("Geyser"));
        arrayList.add(new Category("Pumps and Tanks"));
        arrayList.add(new Category("Installation/Repair"));
        arrayList.add(new Category("Bathroom"));



    }
}
