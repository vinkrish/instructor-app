package com.aanglearning.instructorapp.api;

import com.aanglearning.instructorapp.model.Clas;
import com.aanglearning.instructorapp.model.Groups;
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

    @GET("groups/user/{id}")
    Call<List<Groups>> getGroups(@Path("id") long id);

    @GET("message/group/{groupId}")
    Call<ArrayList<Message>> getGroupMessages(@Path("groupId") long groupId);

    @GET("message/group/{groupId}/message/{messageId}")
    Call<ArrayList<Message>> getGroupMessagesFromId(@Path("groupId") long groupId,
                                               @Path("messageId") long messageId);

    @GET("usergroup/groupusers/groups/{groupId}")
    Call<GroupUsers> getUserGroup(@Path("groupId") long groupId);

    @POST("usergroup")
    Call<Void> saveUserGroupList(@Body ArrayList<UserGroup> userGroupList);

    @GET("groups/{groupId}")
    Call<Void> deleteGroup(@Path("groupId") long groupId);

    @POST("usergroup/delete")
    Call<Void> deleteUserGroupUsers(@Body ArrayList<UserGroup> userGroups);
}
