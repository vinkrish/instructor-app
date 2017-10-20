package com.aanglearning.instructorapp.usergroup;

import com.aanglearning.instructorapp.App;
import com.aanglearning.instructorapp.R;
import com.aanglearning.instructorapp.api.ApiClient;
import com.aanglearning.instructorapp.api.TeacherApi;
import com.aanglearning.instructorapp.model.DeletedGroup;
import com.aanglearning.instructorapp.model.Groups;
import com.aanglearning.instructorapp.model.UserGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinay on 01-04-2017.
 */

class UserGroupInteractorImpl implements UserGroupInteractor {

    @Override
    public void getUserGroup(long groupId, final OnFinishedListener listener) {
        TeacherApi api = ApiClient.getAuthorizedClient().create(TeacherApi.class);

        Call<GroupUsers> queue = api.getUserGroup(groupId);
        queue.enqueue(new Callback<GroupUsers>() {
            @Override
            public void onResponse(Call<GroupUsers> call, Response<GroupUsers> response) {
                if(response.isSuccessful()) {
                    listener.onUserGroupReceived(response.body());
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                }
            }

            @Override
            public void onFailure(Call<GroupUsers> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
            }
        });
    }

    @Override
    public void saveUserGroup(ArrayList<UserGroup> userGroups, final OnFinishedListener listener) {
        TeacherApi api = ApiClient.getAuthorizedClient().create(TeacherApi.class);

        Call<Void> queue = api.saveUserGroupList(userGroups);
        queue.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    listener.onUserGroupSaved();
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
            }
        });
    }

    @Override
    public void deleteUsers(ArrayList<UserGroup> userGroups, final OnFinishedListener listener) {
        TeacherApi api = ApiClient.getAuthorizedClient().create(TeacherApi.class);

        Call<Void> queue = api.deleteUserGroupUsers(userGroups);
        queue.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    listener.onUsersDeleted();
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
            }
        });
    }

    @Override
    public void deleteGroup(DeletedGroup deletedGroup, final OnFinishedListener listener) {
        TeacherApi api = ApiClient.getAuthorizedClient().create(TeacherApi.class);

        Call<DeletedGroup> queue = api.deleteGroup(deletedGroup);
        queue.enqueue(new Callback<DeletedGroup>() {
            @Override
            public void onResponse(Call<DeletedGroup> call, Response<DeletedGroup> response) {
                if(response.isSuccessful()) {
                    listener.onGroupDeleted(response.body());
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                }
            }

            @Override
            public void onFailure(Call<DeletedGroup> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
            }
        });
    }

}
