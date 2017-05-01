package com.aanglearning.instructorapp.dashboard;

import com.aanglearning.instructorapp.App;
import com.aanglearning.instructorapp.R;
import com.aanglearning.instructorapp.api.ApiClient;
import com.aanglearning.instructorapp.api.TeacherApi;
import com.aanglearning.instructorapp.model.Groups;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinay on 02-04-2017.
 */

class GroupInteractorImpl implements GroupInteractor {
    @Override
    public void getGroups(long userId, final OnFinishedListener listener) {
        TeacherApi api = ApiClient.getAuthorizedClient().create(TeacherApi.class);

        Call<List<Groups>> classList = api.getGroups(userId);
        classList.enqueue(new Callback<List<Groups>>() {
            @Override
            public void onResponse(Call<List<Groups>> call, Response<List<Groups>> response) {
                if(response.isSuccessful()) {
                    listener.onGroupsReceived(response.body());
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                }
            }

            @Override
            public void onFailure(Call<List<Groups>> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
            }
        });
    }
}
