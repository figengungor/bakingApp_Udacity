package com.figengungor.bakingapp_udacity;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by figengungor on 4/30/2018.
 */

public class BakingApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
