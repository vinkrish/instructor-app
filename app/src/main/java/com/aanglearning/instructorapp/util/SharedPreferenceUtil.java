package com.aanglearning.instructorapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.aanglearning.instructorapp.model.TeacherCredentials;

public class SharedPreferenceUtil {

    public static void saveTeacher(Context context, TeacherCredentials credentials) {
        SharedPreferences sharedPref = context.getSharedPreferences("credentials", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("authToken", credentials.getAuthToken());
        editor.putLong("schoolId", credentials.getSchoolId());
        editor.putString("schoolName", credentials.getSchoolName());
        editor.apply();
    }

    public static TeacherCredentials getTeacher(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("credentials", Context.MODE_PRIVATE);
        TeacherCredentials response = new TeacherCredentials();
        response.setAuthToken(sharedPref.getString("authToken", ""));
        response.setSchoolId(sharedPref.getLong("schoolId", 0));
        response.setSchoolName(sharedPref.getString("schoolName", ""));
        return response;
    }

    public static void logout(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("credentials", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("authToken", "");
        editor.putLong("schoolId", 0);
        editor.apply();
    }

    public static void saveAttendanceDate(Context context, String date) {
        SharedPreferences sharedPref = context.getSharedPreferences("profile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("attendanceDate", date);
        editor.apply();
    }

    public static String getAttendanceDate(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("profile", Context.MODE_PRIVATE);
        return sharedPref.getString("attendanceDate", "");
    }
    public static void saveHomeworkDate(Context context, String date) {
        SharedPreferences sharedPref = context.getSharedPreferences("profile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("homeworkDate", date);
        editor.apply();
    }

    public static String getHomeworkDate(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("profile", Context.MODE_PRIVATE);
        return sharedPref.getString("homeworkDate", "");
    }

}
