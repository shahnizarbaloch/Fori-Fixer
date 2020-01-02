package com.shahnizarbaloch.forifixer.activity;

import android.app.Application;

import com.shahnizarbaloch.forifixer.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Seravek.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
