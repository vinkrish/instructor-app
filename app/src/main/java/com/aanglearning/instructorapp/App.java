package com.aanglearning.instructorapp;

import android.app.Application;

/**
 * Created by Vinay on 10-03-2017.
 */

public class App extends Application {
    private static App Instance;
    private static boolean activityVisible;

    @Override
    public void onCreate() {
        super.onCreate();
        Instance = this;
    }

    public static App getInstance() {
        return Instance;
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }
}
