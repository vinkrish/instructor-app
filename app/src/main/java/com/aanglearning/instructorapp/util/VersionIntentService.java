package com.aanglearning.instructorapp.util;

import android.app.IntentService;
import android.content.Intent;

import com.aanglearning.instructorapp.App;
import com.aanglearning.instructorapp.BuildConfig;
import com.aanglearning.instructorapp.api.ApiClient;
import com.aanglearning.instructorapp.api.TeacherApi;
import com.aanglearning.instructorapp.model.AppVersion;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VersionIntentService extends IntentService {

    public VersionIntentService() {
        super("VersionIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        TeacherApi api = ApiClient.getAuthorizedClient().create(TeacherApi.class);

        Call<AppVersion> queue = api.getAppVersion(BuildConfig.VERSION_CODE, "teacher");
        queue.enqueue(new Callback<AppVersion>() {
            @Override
            public void onResponse(Call<AppVersion> call, Response<AppVersion> response) {
                if(response.isSuccessful()) {
                    SharedPreferenceUtil.saveAppVersion(App.getInstance(), response.body());
                }
            }

            @Override
            public void onFailure(Call<AppVersion> call, Throwable t) {
            }
        });
    }

}
