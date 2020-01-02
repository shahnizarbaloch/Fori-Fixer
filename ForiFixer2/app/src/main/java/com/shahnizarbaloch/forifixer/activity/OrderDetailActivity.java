package com.shahnizarbaloch.forifixer.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shahnizarbaloch.forifixer.R;
import com.shahnizarbaloch.forifixer.adapter.itemRecyclerView;
import com.shahnizarbaloch.forifixer.model.itemDetail;

import java.util.ArrayList;
import java.util.Objects;

public class OrderDetailActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    TextView id;
    //ArrayList of Everything Class
    private ArrayList<itemDetail> arrayList;
    itemRecyclerView adapter;
    ProgressBar progressBar;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail_layout);
        initialize();
    }


    Intent intent;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void initialize(){
        id=findViewById(R.id.key);
        progressBar=findViewById(R.id.progressBar);
        databaseReference= FirebaseDatabase.getInstance().getReference("Orders").child("OrderDetails");
        RecyclerView recyclerView = findViewById(R.id.item_recycler_view);
        arrayList= new ArrayList<>();
        adapter = new itemRecyclerView(arrayList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
        arrayList=getItemDetail();

    }

    private ArrayList<itemDetail> getItemDetail(){
        progressBar.setVisibility(View.VISIBLE);
        intent=getIntent();
        final String Key=intent.getStringExtra("Key");
        assert Key != null;
        DatabaseReference myRef=databaseReference.child(Key);
        Log.d("myRef",""+myRef);
        myRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String uid = ds.getKey();
                    assert uid != null;
                    String itemName= Objects.requireNonNull(dataSnapshot.child(uid).child("heading").getValue()).toString();
                    String itemPrice= Objects.requireNonNull(dataSnapshot.child(uid).child("price").getValue()).toString();
                    Log.d("itemName", itemName);
                    Log.d("itemPrice", itemPrice);
                    arrayList.add(new itemDetail(itemName,itemPrice));
                    adapter.notifyDataSetChanged();
                }
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
            }
        });
        return arrayList;
    }



}
