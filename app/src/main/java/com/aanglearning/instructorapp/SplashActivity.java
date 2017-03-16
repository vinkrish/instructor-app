package com.aanglearning.instructorapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aanglearning.instructorapp.dashboard.DashboardActivity;
import com.aanglearning.instructorapp.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
    }
}
