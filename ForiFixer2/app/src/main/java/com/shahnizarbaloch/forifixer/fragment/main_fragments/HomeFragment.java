package com.shahnizarbaloch.forifixer.fragment.main_fragments;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.shahnizarbaloch.forifixer.R;
import com.shahnizarbaloch.forifixer.activity.MainActivity;
import com.shahnizarbaloch.forifixer.adapter.MainRecyclerViewAdapter;
import com.shahnizarbaloch.forifixer.model.Category;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    //ArrayList of Everything Class
    private ArrayList<Category> arrayList;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View which will be used in some other places
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Try
        MainActivity.gotoCart.setVisibility(View.VISIBLE);
        MainActivity.mBadge.setVisibility(View.VISIBLE);

        //Recycler View in Fragment
        RecyclerView recyclerView = view.findViewById(R.id.rv_home_categories);
        MainRecyclerViewAdapter adapter = new MainRecyclerViewAdapter(arrayList,getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        recyclerView.setAdapter(adapter);
        return view;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayList = new ArrayList<>();
        //Adding Things Here.. Means when we patch data from firebase then we can add them here ;)
        arrayList.add(new Category(R.drawable.carpenter_home_icon, "Carpenter"));
        arrayList.add(new Category(R.drawable.electrician_home_icon, "Electrician"));
        //arrayList.add(new Category(R.drawable.mechanic_home_icon, "Mechanical"));
        arrayList.add(new Category(R.drawable.painter_home_icon, "Painter"));
        arrayList.add(new Category(R.drawable.plumber_home_icon, "Plumber"));
    }



}
