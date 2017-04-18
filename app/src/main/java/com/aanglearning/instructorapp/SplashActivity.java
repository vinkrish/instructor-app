package com.aanglearning.instructorapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aanglearning.instructorapp.dashboard.DashboardActivity;
import com.aanglearning.instructorapp.login.LoginActivity;
import com.aanglearning.instructorapp.util.AppGlobal;
import com.aanglearning.instructorapp.util.SharedPreferenceUtil;

import org.joda.time.LocalDate;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppGlobal.setSqlDbHelper(getApplicationContext());
        LocalDate localDate = new LocalDate();
        SharedPreferenceUtil.saveAttendanceDate(this, localDate.toString());
        SharedPreferenceUtil.saveHomeworkDate(this, localDate.toString());

        if (SharedPreferenceUtil.getTeacher(this).getAuthToken() != "") {
            startActivity(new Intent(this, DashboardActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

    }
}
