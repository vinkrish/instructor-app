package com.aanglearning.instructorapp.calendar;

import com.aanglearning.instructorapp.App;
import com.aanglearning.instructorapp.R;
import com.aanglearning.instructorapp.api.ApiClient;
import com.aanglearning.instructorapp.api.TeacherApi;
import com.aanglearning.instructorapp.model.Evnt;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinay on 31-07-2017.
 */

public class EventInteractorImpl implements EventInteractor {
    @Override
    public void getEvents(long schoolId, final OnFinishedListener listener) {
        TeacherApi api = ApiClient.getAuthorizedClient().create(TeacherApi.class);

        Call<List<Evnt>> queue = api.getEvents(schoolId);
        queue.enqueue(new Callback<List<Evnt>>() {
            @Override
            public void onResponse(Call<List<Evnt>> call, Response<List<Evnt>> response) {
                if(response.isSuccessful()) {
                    listener.onEventsReceived(response.body());
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                }
            }

            @Override
            public void onFailure(Call<List<Evnt>> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
            }
        });
    }
}
