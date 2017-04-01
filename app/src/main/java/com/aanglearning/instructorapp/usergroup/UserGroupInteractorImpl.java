package com.aanglearning.instructorapp.usergroup;

import com.aanglearning.instructorapp.api.ApiClient;
import com.aanglearning.instructorapp.api.TeacherApi;
import com.aanglearning.instructorapp.model.Student;
import com.aanglearning.instructorapp.model.Teacher;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinay on 01-04-2017.
 */

public class UserGroupInteractorImpl implements UserGroupInteractor {

    @Override
    public void getClassStudents(long classId, final UserGroupInteractor.OnFinishedListener listener) {
        TeacherApi api = ApiClient.getAuthorizedClient().create(TeacherApi.class);

        Call<List<Student>> classList = api.getClassStudents(classId);
        classList.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if(response.isSuccessful()) {
                    listener.onStudentsReceived(response.body());
                } else {
                    listener.onError();
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                listener.onError();
            }
        });
    }

    @Override
    public void getSectionStudents(long sectionId, final UserGroupInteractor.OnFinishedListener listener) {
        TeacherApi api = ApiClient.getAuthorizedClient().create(TeacherApi.class);

        Call<List<Student>> classList = api.getSectionStudents(sectionId);
        classList.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if(response.isSuccessful()) {
                    listener.onStudentsReceived(response.body());
                } else {
                    listener.onError();
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                listener.onError();
            }
        });
    }

    @Override
    public void getClassSubjectTeachers(long classId, final UserGroupInteractor.OnFinishedListener listener) {
        TeacherApi api = ApiClient.getAuthorizedClient().create(TeacherApi.class);

        Call<List<Teacher>> classList = api.getClassSubjectTeachers(classId);
        classList.enqueue(new Callback<List<Teacher>>() {
            @Override
            public void onResponse(Call<List<Teacher>> call, Response<List<Teacher>> response) {
                if(response.isSuccessful()) {
                    listener.onTeachersReceived(response.body());
                } else {
                    listener.onError();
                }
            }

            @Override
            public void onFailure(Call<List<Teacher>> call, Throwable t) {
                listener.onError();
            }
        });
    }

    @Override
    public void getSectionSubjectTeachers(long sectionId, final UserGroupInteractor.OnFinishedListener listener) {
        TeacherApi api = ApiClient.getAuthorizedClient().create(TeacherApi.class);

        Call<List<Teacher>> classList = api.getSectionSubjectTeachers(sectionId);
        classList.enqueue(new Callback<List<Teacher>>() {
            @Override
            public void onResponse(Call<List<Teacher>> call, Response<List<Teacher>> response) {
                if(response.isSuccessful()) {
                    listener.onTeachersReceived(response.body());
                } else {
                    listener.onError();
                }
            }

            @Override
            public void onFailure(Call<List<Teacher>> call, Throwable t) {
                listener.onError();
            }
        });
    }

}
