package com.aanglearning.instructorapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.aanglearning.instructorapp.dao.TeacherDao;
import com.aanglearning.instructorapp.dashboard.DashboardActivity;
import com.aanglearning.instructorapp.fcm.FCMIntentService;
import com.aanglearning.instructorapp.login.LoginActivity;
import com.aanglearning.instructorapp.model.AppVersion;
import com.aanglearning.instructorapp.model.TeacherCredentials;
import com.aanglearning.instructorapp.util.AppGlobal;
import com.aanglearning.instructorapp.util.SharedPreferenceUtil;
import com.aanglearning.instructorapp.util.VersionIntentService;

import java.util.List;

public class SplashActivity extends AppCompatActivity {
    private TeacherCredentials credentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppGlobal.setSqlDbHelper(getApplicationContext());
        init();
    }

    private void init() {
        credentials = SharedPreferenceUtil.getTeacher(this);
        if(!credentials.getMobileNo().equals("") && !SharedPreferenceUtil.isFcmTokenSaved(this)) {
            startService(new Intent(this, FCMIntentService.class));
        }

        AppVersion appVersion = SharedPreferenceUtil.getAppVersion(getApplicationContext());
        if(BuildConfig.VERSION_CODE == appVersion.getVersionId() &&
                appVersion.getStatus().equals("obsolete")){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("New Version Available");
            alertDialog.setMessage("This version is no more supported, please update to continue.");
            alertDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    openPlayStore(getApplicationContext());
                }
            });
            alertDialog.setCancelable(false);
            alertDialog.show();
        } else if(BuildConfig.VERSION_CODE == appVersion.getVersionId() &&
                appVersion.getStatus().equals("update") &&
                !SharedPreferenceUtil.isUpdatePrompted(getApplicationContext())) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Update Alert");
            alertDialog.setMessage("New version of app released!");
            alertDialog.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SharedPreferenceUtil.updatePrompted(getApplicationContext(), true);
                    dialogInterface.dismiss();
                    launchNextActivity();
                }
            });
            alertDialog.setNegativeButton("Update", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //dialogInterface.dismiss();
                    openPlayStore(getApplicationContext());
                }
            });
            alertDialog.setCancelable(false);
            alertDialog.show();
        } else {
            startService(new Intent(this, VersionIntentService.class));
            launchNextActivity();
        }
    }

    private void launchNextActivity() {
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

    private void openPlayStore(Context context) {
        // you can also use BuildConfig.APPLICATION_ID
        String appId = context.getPackageName();
        Intent rateIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("market://details?id=" + appId));
        boolean marketFound = false;

        // find all applications able to handle our rateIntent
        final List<ResolveInfo> otherApps = context.getPackageManager()
                .queryIntentActivities(rateIntent, 0);
        for (ResolveInfo otherApp: otherApps) {
            // look for Google Play application
            if (otherApp.activityInfo.applicationInfo.packageName
                    .equals("com.android.vending")) {

                ActivityInfo otherAppActivity = otherApp.activityInfo;
                ComponentName componentName = new ComponentName(
                        otherAppActivity.applicationInfo.packageName,
                        otherAppActivity.name
                );
                // make sure it does NOT open in the stack of your activity
                rateIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // task reparenting if needed
                rateIntent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                // if the Google Play was already open in a search result
                //  this make sure it still go to the app page you requested
                rateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // this make sure only the Google Play app is allowed to
                // intercept the intent
                rateIntent.setComponent(componentName);
                context.startActivity(rateIntent);
                marketFound = true;
                break;

            }
        }

        // if GP not present on device, open web browser
        if (!marketFound) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id="+appId));
            context.startActivity(webIntent);
        }
    }
}
