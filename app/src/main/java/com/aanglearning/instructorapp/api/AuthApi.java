package com.aanglearning.instructorapp.api;

import com.aanglearning.instructorapp.model.Authorization;
import com.aanglearning.instructorapp.model.CommonResponse;
import com.aanglearning.instructorapp.model.Credentials;
import com.aanglearning.instructorapp.model.TeacherCredentials;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Vinay on 16-02-2017.
 */

public interface AuthApi {

    @Headers("content-type: application/json")
    @POST("teacher/login")
    Call<TeacherCredentials> login(@Body Credentials credentials);

    @Headers("content-type: application/json")
    @POST("teacher/newPassword")
    Call<CommonResponse> newPassword(@Body String updatedPassword);

    @Headers("content-type: application/json")
    @POST("authorization/fcm")
    Call<Void> updateFcmToken(@Body Authorization authorization);

}
