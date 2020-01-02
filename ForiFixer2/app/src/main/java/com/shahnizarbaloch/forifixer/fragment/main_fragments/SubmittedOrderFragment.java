package com.shahnizarbaloch.forifixer.fragment.main_fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shahnizarbaloch.forifixer.R;
import com.shahnizarbaloch.forifixer.adapter.OrderRecyclerView;
import com.shahnizarbaloch.forifixer.model.getOrderBy;

import java.util.ArrayList;
import java.util.Objects;


public class SubmittedOrderFragment extends Fragment {

    private ArrayList<getOrderBy> arrayList;
    private OrderRecyclerView adapter;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_submitted_orders, container, false);
        //Recycler View in Fragment
        RecyclerView recyclerView = view.findViewById(R.id.submitted_order_rv);
        progressBar = view.findViewById(R.id.progressBar);
        adapter = new OrderRecyclerView(arrayList,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(Objects.requireNonNull(getContext()),DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
        return view;
    }


    private boolean checkConnection(){
        boolean connected;
        ConnectivityManager connectivityManager = (ConnectivityManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CONNECTIVITY_SERVICE);
        //we are connected to a network
        assert connectivityManager != null;
        connected = Objects.requireNonNull(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)).getState() == NetworkInfo.State.CONNECTED ||
                Objects.requireNonNull(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)).getState() == NetworkInfo.State.CONNECTED;
        return connected;
    }



    private String name,email;
    /**
     * This Method will get User Email Number and Name From Firebase Database
     * When User Information will be being Gather then Scroll Bar will be Shown on Screen,
     * When User Information will be Loaded Then ScrollBar will be Gone!!
     */
    private void getProfileInfo() {
        boolean connectivity=checkConnection();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser =mAuth.getCurrentUser();
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Users");
        if(connectivity){
            assert currentUser != null;
            String userid = currentUser.getUid();
            databaseReference.child(userid).addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    name = Objects.requireNonNull(dataSnapshot.child("name").getValue()).toString();
                    email = currentUser.getEmail();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else{
            Toast.makeText(getContext(), "No Internet Connectivity!", Toast.LENGTH_SHORT).show();
        }


    }

    private ArrayList<getOrderBy> getYourOrders(){
        getProfileInfo();
        final ArrayList<getOrderBy> arrayListUser = new ArrayList<>();
        final DatabaseReference myRef= FirebaseDatabase.getInstance().getReference("Orders").child("OrderBy");
        myRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    String uid = ds.getKey();
                    assert uid != null;
                    Log.d("TAG", uid);
                    String Name= Objects.requireNonNull(dataSnapshot.child(uid).child("Name").getValue()).toString();
                    String Email= Objects.requireNonNull(dataSnapshot.child(uid).child("Email").getValue()).toString();
                    String Number= Objects.requireNonNull(dataSnapshot.child(uid).child("Number").getValue()).toString();
                    String Location= Objects.requireNonNull(dataSnapshot.child(uid).child("Location").getValue()).toString();
                    String Total= Objects.requireNonNull(dataSnapshot.child(uid).child("Total").getValue()).toString();

                    if(Email.equals(email)&&Name.equals(name)){
                        arrayListUser.add(new getOrderBy(uid,Name,Email,Number,Location,Total));
                    }

                    adapter.notifyDataSetChanged();
                }
                progressBar.setVisibility(View.GONE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Toast.makeText(getContext(), "Failed To Load Data!", Toast.LENGTH_SHORT).show();
            }
        });
        return arrayListUser;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayList = new ArrayList<>();
        arrayList= getYourOrders();

        //Adding Things Here.. Means when we patch data from firebase then we can add them here ;)

    }

}
