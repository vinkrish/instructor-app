package com.aanglearning.instructorapp.api;

import com.aanglearning.instructorapp.model.Clas;
import com.aanglearning.instructorapp.model.Groups;
import com.aanglearning.instructorapp.model.Section;
import com.aanglearning.instructorapp.model.Student;
import com.aanglearning.instructorapp.model.Teacher;
import com.aanglearning.instructorapp.model.UserGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Vinay on 29-03-2017.
 */

public interface TeacherApi {

    @GET("class/school/{schoolId}")
    Call<List<Clas>> getClassList(@Path("schoolId") long schoolId);

    @GET("section/class/{classId}")
    Call<List<Section>> getSectionList(@Path("classId") long classId);

    @POST("groups")
    Call<Groups> saveGroup(@Body Groups groups);

    @GET("student/class/{classId}")
    Call<List<Student>> getClassStudents(@Path("classId") long classId);

    @GET("student/section/{sectionId}")
    Call<List<Student>> getSectionStudents(@Path("sectionId") long sectionId);

    @GET("teacher/class/{classId}")
    Call<List<Teacher>> getClassSubjectTeachers(@Path("classId") long classId);

    @GET("teacher/section/{sectionId}")
    Call<List<Teacher>> getSectionSubjectTeachers(@Path("sectionId") long sectionId);

    @POST("usergroup")
    Call<Void> saveUserGroupList(@Body UserGroup userGroupList);

    @GET("usergroup/groups/{groupId}")
    Call<Void> deleteUserGroup(@Path("groupId") long groupId);

    @DELETE("usergroup/{id}")
    Call<Void> deleteUserGroupUser(@Path("id") long id);
}
