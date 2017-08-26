package com.aanglearning.instructorapp.timetable;

import com.aanglearning.instructorapp.App;
import com.aanglearning.instructorapp.R;
import com.aanglearning.instructorapp.api.ApiClient;
import com.aanglearning.instructorapp.api.TeacherApi;
import com.aanglearning.instructorapp.model.TeacherTimetable;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinay on 13-06-2017.
 */

class TimetableInteractorImpl implements TimetableInteractor {

    @Override
    public void getTimetable(long teacherId, final OnFinishedListener listener) {
        TeacherApi api = ApiClient.getAuthorizedClient().create(TeacherApi.class);

        Call<List<TeacherTimetable>> queue = api.getTimetable(teacherId);
        queue.enqueue(new Callback<List<TeacherTimetable>>() {
            @Override
            public void onResponse(Call<List<TeacherTimetable>> call, Response<List<TeacherTimetable>> response) {
                if(response.isSuccessful()) {
                    listener.onTimetableReceived(response.body());
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                }
            }

            @Override
            public void onFailure(Call<List<TeacherTimetable>> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
            }
        });
    }
}
