package com.aanglearning.instructorapp.homework;

import com.aanglearning.instructorapp.App;
import com.aanglearning.instructorapp.R;
import com.aanglearning.instructorapp.api.ApiClient;
import com.aanglearning.instructorapp.api.TeacherApi;
import com.aanglearning.instructorapp.model.Clas;
import com.aanglearning.instructorapp.model.Groups;
import com.aanglearning.instructorapp.model.Homework;
import com.aanglearning.instructorapp.model.Section;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinay on 21-04-2017.
 */

class HomeworkInteractorImpl implements HomeworkInteractor {

    @Override
    public void getClassList(long teacherId, final OnFinishedListener listener) {
        TeacherApi api = ApiClient.getAuthorizedClient().create(TeacherApi.class);

        Call<List<Clas>> queue = api.getSubjectTeacherClasses(teacherId);
        queue.enqueue(new Callback<List<Clas>>() {
            @Override
            public void onResponse(Call<List<Clas>> call, Response<List<Clas>> response) {
                if(response.isSuccessful()) {
                    listener.onClassReceived(response.body());
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                    listener.loadOffline("class");
                }
            }
            @Override
            public void onFailure(Call<List<Clas>> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
                listener.loadOffline("class");
            }
        });
    }

    @Override
    public void getSectionList(long classId, long teacherId, final OnFinishedListener listener) {
        TeacherApi api = ApiClient.getAuthorizedClient().create(TeacherApi.class);

        Call<List<Section>> queue = api.getSectionList(classId);
        queue.enqueue(new Callback<List<Section>>() {
            @Override
            public void onResponse(Call<List<Section>> call, Response<List<Section>> response) {
                if(response.isSuccessful()) {
                    listener.onSectionReceived(response.body());
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                    listener.loadOffline("section");
                }
            }
            @Override
            public void onFailure(Call<List<Section>> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
                listener.loadOffline("section");
            }
        });
    }

    @Override
    public void getHomework(long sectionId, String date, final OnFinishedListener listener) {
        TeacherApi api = ApiClient.getAuthorizedClient().create(TeacherApi.class);

        Call<List<Homework>> queue = api.getHomework(sectionId, date);
        queue.enqueue(new Callback<List<Homework>>() {
            @Override
            public void onResponse(Call<List<Homework>> call, Response<List<Homework>> response) {
                if(response.isSuccessful()) {
                    listener.onHomeworkReceived(response.body());
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                    listener.loadOffline("homework");
                }
            }

            @Override
            public void onFailure(Call<List<Homework>> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
                listener.loadOffline("homework");
            }
        });
    }

    @Override
    public void saveHomework(Homework homework, final OnFinishedListener listener) {
        TeacherApi api = ApiClient.getAuthorizedClient().create(TeacherApi.class);

        Call<Homework> queue = api.saveHomework(homework);
        queue.enqueue(new Callback<Homework>() {
            @Override
            public void onResponse(Call<Homework> call, Response<Homework> response) {
                if(response.isSuccessful()) {
                    listener.onHomeworkSaved(response.body());
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                }
            }

            @Override
            public void onFailure(Call<Homework> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
            }
        });
    }

    @Override
    public void updateHomework(Homework homework, final OnFinishedListener listener) {
        TeacherApi api = ApiClient.getAuthorizedClient().create(TeacherApi.class);

        Call<Void> queue = api.updateHomework(homework);
        queue.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    listener.onHomeworkUpdated();
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
    public void deleteHomework(ArrayList<Homework> homeworks, final OnFinishedListener listener) {
        TeacherApi api = ApiClient.getAuthorizedClient().create(TeacherApi.class);

        Call<Void> queue = api.deleteHomework(homeworks);
        queue.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    listener.onHomeworkDeleted();
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
}
