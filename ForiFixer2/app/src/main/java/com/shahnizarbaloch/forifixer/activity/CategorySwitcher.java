package com.shahnizarbaloch.forifixer.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.nex3z.notificationbadge.NotificationBadge;
import com.shahnizarbaloch.forifixer.R;
import com.shahnizarbaloch.forifixer.database.DatabaseHelper;
import com.shahnizarbaloch.forifixer.fragment.main_fragments.OrdersFragment;
import com.shahnizarbaloch.forifixer.fragment.sub_fragment.SubCarpenterFragment;
import com.shahnizarbaloch.forifixer.fragment.sub_fragment.SubElectricianFragment;
import com.shahnizarbaloch.forifixer.fragment.sub_fragment.SubPainterFragment;
import com.shahnizarbaloch.forifixer.fragment.sub_fragment.SubPlumberFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CategorySwitcher extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    private TextView ToolbarName;
    public static NotificationBadge mBadge;
    @SuppressLint("StaticFieldLeak")
    public static ImageView gotoCart;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        /*//these two lines for full screen activity
        //note that it should be call before super.onCreate
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_switcher);

        LinearLayout toolbarLL = findViewById(R.id.toolbarLayout);

        ImageView go_back_home = findViewById(R.id.go_back_home);
        go_back_home.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                CategorySwitcher.this.onBackPressed();
            }
        });
        gotoCart= findViewById(R.id.goto_cart);
        gotoCart.setVisibility(View.VISIBLE);
        gotoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToolbarName.setText("Cart Items");
                getSupportFragmentManager().beginTransaction().replace(R.id.container_switcher,
                        new OrdersFragment()).commit();
            }
        });

        mBadge = findViewById(R.id.badge);


        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        mBadge.setNumber(databaseHelper.cartItems());
        databaseHelper.close();
        ToolbarName= findViewById(R.id.name);

        Intent intent = getIntent();
        String fragment = intent.getStringExtra("fragment");
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);



        assert fragment != null;
        switch (fragment){
            case "Carpenter":
                ToolbarName.setText("Carpenter");
                toolbarLL.setBackgroundColor(getResources().getColor(R.color.colorCarpenter));
                getSupportFragmentManager().beginTransaction().replace(R.id.container_switcher,
                                new SubCarpenterFragment()).commit();
                break;
            case "Electrician":
                toolbarLL.setBackgroundColor(getResources().getColor(R.color.colorElectrician));
                ToolbarName.setText("Electrician");
                getSupportFragmentManager().beginTransaction().replace(R.id.container_switcher,
                        new SubElectricianFragment()).commit();
                break;
            /*case "Mechanical":
                toolbarLL.setBackgroundColor(getResources().getColor(R.color.colorMechanical));
                ToolbarName.setText("Mechanical");
                getSupportFragmentManager().beginTransaction().replace(R.id.container_switcher,
                        new SubMechanicalFragment()).commit();
                break;*/
            case "Painter":
                toolbarLL.setBackgroundColor(getResources().getColor(R.color.colorPainter));
                ToolbarName.setText("Painter");
                getSupportFragmentManager().beginTransaction().replace(R.id.container_switcher,
                        new SubPainterFragment()).commit();
                break;
            case "Plumber":
                toolbarLL.setBackgroundColor(getResources().getColor(R.color.colorPlumber));
                ToolbarName.setText("Plumber");
                getSupportFragmentManager().beginTransaction().replace(R.id.container_switcher,
                        new SubPlumberFragment()).commit();
                break;

            default:
                ToolbarName.setText("Cart Items");
                getSupportFragmentManager().beginTransaction().replace(R.id.container_switcher,
                        new OrdersFragment()).commit();
        }

    }
}
