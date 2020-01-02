package com.shahnizarbaloch.forifixer.fragment.main_fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shahnizarbaloch.forifixer.R;
import com.shahnizarbaloch.forifixer.activity.CategorySwitcher;
import com.shahnizarbaloch.forifixer.activity.MainActivity;
import com.shahnizarbaloch.forifixer.adapter.OrderItemsRecyclerViewAdapter;
import com.shahnizarbaloch.forifixer.database.DatabaseHelper;
import com.shahnizarbaloch.forifixer.model.AddToCart;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class OrdersFragment extends Fragment  {

    private TextView Name;
    private TextView Email;
    private TextView Number;
    private ProgressBar progressBar;

    @SuppressLint("StaticFieldLeak")
    public static TextView Total;

    private FirebaseUser currentUser;
    private DatabaseReference databaseReference;

    private EditText Location;
    public ArrayList<AddToCart> arrayList;
    private DatabaseHelper db;
    private String getName,getEmail,getNumber,getLocation;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View which will be used in some other places
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        db = new DatabaseHelper(getContext());
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        currentUser = mAuth.getCurrentUser();
        //Recycler View in Fragment
        RecyclerView recyclerView = view.findViewById(R.id.rv_order_categories);
        Name = view.findViewById(R.id.tv_name_order);
        Email = view.findViewById(R.id.tv_email_address_order);
        Number = view.findViewById(R.id.tv_mobile_number_order);
        progressBar = view.findViewById(R.id.progressBar);
        int totalPrice=db.addAllValues();
        Total = view.findViewById(R.id.tv_total_price_order);
        Total.setText("PKR "+totalPrice);
        final ImageView empty_cart = view.findViewById(R.id.empty_cart);

        //Try
        CategorySwitcher.gotoCart.setVisibility(View.GONE);
        CategorySwitcher.mBadge.setVisibility(View.GONE);

        getProfileInfo();
        Location = view.findViewById(R.id.tv_location_order);
        final ScrollView scrollView = view.findViewById(R.id.scrollView);
        final TextView bookOrder = view.findViewById(R.id.tv_book_order);
        if(arrayList.size()==0){
            empty_cart.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
            bookOrder.setVisibility(View.GONE);
        }
        bookOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getName=Name.getText().toString();
                getEmail=Email.getText().toString();
                getNumber=Number.getText().toString();
                getLocation=Location.getText().toString();
                if(getLocation.isEmpty() || getLocation.length()<8){
                    Toast.makeText(getContext(), "Please Add Your Correct Location", Toast.LENGTH_SHORT).show();
                }
                else if(getName.isEmpty()||getEmail.isEmpty()||getNumber.isEmpty()){
                    Toast.makeText(getContext(), "Internet Seems to be Slow !", Toast.LENGTH_SHORT).show();
                }
                else if(Total.getText().toString().equals("PKR 0")){
                    Toast.makeText(getContext(), "You Have Not Added Any Item.", Toast.LENGTH_SHORT).show();
                }
                else{
                        progressBar.setVisibility(View.VISIBLE);
                        sendData();
                    }
            }
        });

        OrderItemsRecyclerViewAdapter adapter = new OrderItemsRecyclerViewAdapter(arrayList,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(Objects.requireNonNull(getContext()),DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
        return view;
    }

    /**
     * This Method will Delete Order After Sending it to Admin
     */
    private void deleteOrderFromDatabase() {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.delete("ORDERS",null,null);
        sqLiteDatabase.close();
    }



    /**
     * This Method Will Send Order To The Firebase which will be Recieve by Admin
     */
    private void sendData(){
        // Write a message to the database
        final DatabaseReference myRef=FirebaseDatabase.getInstance().getReference("Orders");
        DatabaseReference keyRef=myRef.push();
        final String Key= keyRef.getKey();
        assert Key != null;
        Map<String,Object> taskMap = new HashMap<>();
        taskMap.put("Name", getName);
        taskMap.put("Email", getEmail);
        taskMap.put("Number", getNumber);
        taskMap.put("Location", getLocation);
        taskMap.put("Total",Total.getText().toString());
        myRef.child("OrderBy").child(Key).setValue(taskMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        myRef.child("OrderDetails").child(Key).setValue(db.getOrder());
                        Toast.makeText(getContext(), "Your Order Has Been Submitted Successfully!", Toast.LENGTH_SHORT).show();
                        deleteOrderFromDatabase();
                        progressBar.setVisibility(View.GONE);
                        //This is for going to Home Fragment!
                        assert getFragmentManager() != null;
                        @SuppressLint("CommitTransaction") FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.container_switcher,new HomeFragment());
                        ft.addToBackStack(null).commit();
                        //Try
                        MainActivity.gotoCart.setVisibility(View.VISIBLE);
                        MainActivity.mBadge.setVisibility(View.VISIBLE);
                        MainActivity.mBadge.setNumber(0);
                        CategorySwitcher.mBadge.setNumber(0);
                        //DONE!!
                    }

                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Failed to Submit Order", Toast.LENGTH_SHORT).show();
            }
        });


    }

    /**
     * This Method will get User Email Number and Name From Firebase Database
     * When User Information will be being Gather then Scroll Bar will be Shown on Screen,
     * When User Information will be Loaded Then ScrollBar will be Gone!!
     */
    private void getProfileInfo() {
        boolean connectivity=checkConnection();
        if(connectivity){
            progressBar.setVisibility(View.VISIBLE);
            assert currentUser != null;
            String userid = currentUser.getUid();
            databaseReference.child(userid).addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    @SuppressLint({"NewApi", "LocalSuppress"}) String name = Objects.requireNonNull(dataSnapshot.child("name").getValue()).toString();
                    @SuppressLint({"NewApi", "LocalSuppress"}) String number = Objects.requireNonNull(dataSnapshot.child("number").getValue()).toString();
                    String email = currentUser.getEmail();
                    Name.setText(name);
                    Number.setText("+92" + number);
                    Email.setText(email);
                    progressBar.setVisibility(View.GONE);

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






    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean checkConnection(){
        boolean connected;
        ConnectivityManager connectivityManager = (ConnectivityManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CONNECTIVITY_SERVICE);
        //we are connected to a network
        assert connectivityManager != null;
        connected = Objects.requireNonNull(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)).getState() == NetworkInfo.State.CONNECTED ||
                Objects.requireNonNull(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)).getState() == NetworkInfo.State.CONNECTED;
        return connected;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHelper db = new DatabaseHelper(getContext());
        arrayList= db.getOrder();

    }


}

