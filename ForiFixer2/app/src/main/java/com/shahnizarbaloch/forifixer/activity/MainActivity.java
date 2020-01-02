package com.shahnizarbaloch.forifixer.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.nex3z.notificationbadge.NotificationBadge;
import com.shahnizarbaloch.forifixer.R;
import com.shahnizarbaloch.forifixer.database.DatabaseHelper;
import com.shahnizarbaloch.forifixer.fragment.main_fragments.ContactForiFixerFragment;
import com.shahnizarbaloch.forifixer.fragment.main_fragments.HomeFragment;
import com.shahnizarbaloch.forifixer.fragment.main_fragments.LogoutFragment;
import com.shahnizarbaloch.forifixer.fragment.main_fragments.PaymentFragment;
import com.shahnizarbaloch.forifixer.fragment.main_fragments.ProfileFragment;
import com.shahnizarbaloch.forifixer.fragment.main_fragments.ServicesFragment;
import com.shahnizarbaloch.forifixer.fragment.main_fragments.SubmittedOrderFragment;
import com.squareup.picasso.Picasso;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    public NavigationView navigationView;

    private void initialize(){
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public static NotificationBadge mBadge;
    @SuppressLint("StaticFieldLeak")
    public static ImageView gotoCart;
    private Toolbar toolbar;
    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        toolbar =findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        mBadge = findViewById(R.id.badge);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        mBadge.setNumber(databaseHelper.cartItems());
        gotoCart= findViewById(R.id.goto_cart);
        gotoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategorySwitcher.class);
                intent.putExtra("fragment","order");
                startActivity(intent);
            }
        });

        drawer =findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        CircularImageView imgDP = hView.findViewById(R.id.navigation_profile);

        if(mUser.getPhotoUrl()!=null){
            Picasso.get().load(mUser.getPhotoUrl()).fit().centerCrop().into(imgDP);
        }
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,toolbar,
        R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }



    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            boolean value = navigationView.getMenu().getItem(0).isChecked();
            if(value){
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Exit Confirmation!")
                        .setMessage("Are you sure you want to Exit?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
            else{
                toolbar.setTitle("Fori Fixer");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_home);
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                toolbar.setTitle("Fori Fixer");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                break;
            case R.id.nav_profile:
                toolbar.setTitle("Profile");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                break;
            case R.id.nav_forifixer_service:
                toolbar.setTitle("Services");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ServicesFragment()).commit();
                break;
            case R.id.nav_payment:
                toolbar.setTitle("Payment Methods");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PaymentFragment()).commit();
                break;

            case R.id.nav_contact_forifixer:
                toolbar.setTitle("Contact Us");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ContactForiFixerFragment()).commit();
                break;
            case R.id.nav_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setTitle("Logout");
                builder.setMessage("Are You Sure Want to Logout? Pending Order will be Vanished!");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mAuth.signOut();
                                deleteOrderFromDatabase();
                                Intent intent = new Intent(MainActivity.this,StartLoginScreen.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                        new LogoutFragment()).commit();
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Cancelled!", Toast.LENGTH_SHORT).show();
                        navigationView.setCheckedItem(R.id.nav_home);
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                break;

            case R.id.nav_submitted_order:
                toolbar.setTitle("Submitted Orders");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SubmittedOrderFragment()).commit();
                break;

            case R.id.nav_rate_app:
                Toast.makeText(this, "Rate App", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_share_app:
                Toast.makeText(this, "Share App", Toast.LENGTH_SHORT).show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    DatabaseHelper databaseHelper;
    /**
     * This Method will Delete Order After Sending it to Admin
     */
    private void deleteOrderFromDatabase() {
        databaseHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete("ORDERS",null,null);
        db.close();
    }
}
