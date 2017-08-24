package com.aanglearning.instructorapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aanglearning.instructorapp.dao.TeacherDao;
import com.aanglearning.instructorapp.dashboard.DashboardActivity;
import com.aanglearning.instructorapp.login.LoginActivity;
import com.aanglearning.instructorapp.model.TeacherCredentials;
import com.aanglearning.instructorapp.service.FCMIntentService;
import com.aanglearning.instructorapp.util.AppGlobal;
import com.aanglearning.instructorapp.util.SharedPreferenceUtil;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppGlobal.setSqlDbHelper(getApplicationContext());

        TeacherCredentials credentials = SharedPreferenceUtil.getTeacher(this);
        if(!credentials.getMobileNo().equals("") && !SharedPreferenceUtil.isFcmTokenSaved(this)) {
            startService(new Intent(this, FCMIntentService.class));
        }

        if (TeacherDao.getTeacher().getId() == 0) {
            SharedPreferenceUtil.logout(this);
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else if (credentials.getAuthToken().equals("")) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, DashboardActivity.class));
            finish();
        }

    }
}
