package com.aanglearning.instructorapp.api;

import com.aanglearning.instructorapp.attendance.AttendanceSet;
import com.aanglearning.instructorapp.model.Attendance;
import com.aanglearning.instructorapp.model.Chat;
import com.aanglearning.instructorapp.model.Clas;
import com.aanglearning.instructorapp.model.Groups;
import com.aanglearning.instructorapp.model.Homework;
import com.aanglearning.instructorapp.model.Message;
import com.aanglearning.instructorapp.model.Section;
import com.aanglearning.instructorapp.model.UserGroup;
import com.aanglearning.instructorapp.usergroup.GroupUsers;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Vinay on 29-03-2017.
 */

public interface TeacherApi {

    @GET("class/school/{schoolId}")
    Call<List<Clas>> getClassList(@Path("schoolId") long schoolId);

    @GET("class/teacher/{teacherId}")
    Call<List<Clas>> getSectionTeacherClasses(@Path("teacherId") long teacherId);

    @GET("class/subjectteacher/{teacherId}")
    Call<List<Clas>> getSubjectTeacherClasses(@Path("teacherId") long teacherId);

    @GET("section/class/{classId}")
    Call<List<Section>> getSectionList(@Path("classId") long classId);

    @GET("section/class/{classId}/teacher/{teacherId}")
    Call<List<Section>> getSectionTeacherSections(@Path("classId") long classId,
                                                  @Path("teacherId") long teacherId);

    @GET("section/class/{classId}/subjectteacher/{teacherId}")
    Call<List<Section>> getSubjectTeacherSections(@Path("classId") long classId,
                                                  @Path("teacherId") long teacherId);

    //Groups and MessageGroup API

    @POST("groups")
    Call<Groups> saveGroup(@Body Groups groups);

    @GET("groups/teacher/{id}")
    Call<List<Groups>> getGroups(@Path("id") long id);

    @GET("groups/{groupId}")
    Call<Void> deleteGroup(@Path("groupId") long groupId);

    @POST("message")
    Call<Message> saveMessage(@Body Message message);

    @GET("message/group/{groupId}")
    Call<ArrayList<Message>> getGroupMessages(@Path("groupId") long groupId);

    @GET("message/group/{groupId}/message/{messageId}")
    Call<ArrayList<Message>> getGroupMessagesFromId(@Path("groupId") long groupId,
                                                    @Path("messageId") long messageId);

    //UserGroup API

    @GET("usergroup/groupusers/groups/{groupId}")
    Call<GroupUsers> getUserGroup(@Path("groupId") long groupId);

    @POST("usergroup")
    Call<Void> saveUserGroupList(@Body ArrayList<UserGroup> userGroupList);

    @POST("usergroup/delete")
    Call<Void> deleteUserGroupUsers(@Body ArrayList<UserGroup> userGroups);

    //Chat API
    @POST("chat")
    Call<Chat> saveChat(@Body Chat chat);

    @GET("chat/teacher/{id}")
    Call<List<Chat>> getChats(@Path("id") long id);

    @GET("chat/{chatId}")
    Call<Void> deleteChat(@Path("chatId") long chatId);

    @GET("message/{senderRole}/{senderId}/{recipientRole}/{recipientId}")
    Call<ArrayList<Message>> getChatMessages(@Path("groupId") long groupId);

    @GET("message/{senderRole}/{senderId}/{recipientRole}/{recipientId}/message/{messageId}")
    Call<ArrayList<Message>> getChatMessagesFromId(@Path("groupId") long groupId,
                                                    @Path("messageId") long messageId);

    //Attendance API

    @GET("app/attendance/section/{sectionId}/date/{dateAttendance}/session/{session}")
    Call<AttendanceSet> getAttendanceSet(@Path("sectionId") long sectionId,
                                         @Path("dateAttendance") String dateAttendance,
                                         @Path("session") int session);

    @POST("app/attendance")
    Call<Void> saveAttendance(@Body ArrayList<Attendance> attendances);

    @POST("app/attendance/delete")
    Call<Void> deleteAttendance(@Body ArrayList<Attendance> attendanceList);

    //Homework API

    @GET("homework/section/{sectionId}/date/{homeworkDate}")
    Call<List<Homework>> getHomework(@Path("sectionId") long sectionId,
                                     @Path("homeworkDate") String homeworkDate);

    @POST("homework")
    Call<Homework> saveHomework(@Body Homework homework);

    @PUT("homework")
    Call<Void> updateHomework(@Body Homework homework);

    @POST("homework/delete")
    Call<Void> deleteHomework(@Body List<Homework> homeworks);
}
