package com.aanglearning.instructorapp.newalbum;

import com.aanglearning.instructorapp.App;
import com.aanglearning.instructorapp.R;
import com.aanglearning.instructorapp.api.ApiClient;
import com.aanglearning.instructorapp.api.GalleryApi;
import com.aanglearning.instructorapp.api.TeacherApi;
import com.aanglearning.instructorapp.model.Album;
import com.aanglearning.instructorapp.model.Clas;
import com.aanglearning.instructorapp.model.Section;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinay on 19-12-2017.
 */

public class NewAlbumInteractorImpl implements NewAlbumInteractor {
    @Override
    public void getClassList(long schoolId, final OnFinishedListener listener) {
        TeacherApi api = ApiClient.getAuthorizedClient().create(TeacherApi.class);

        Call<List<Clas>> queue = api.getSubjectTeacherClasses(schoolId);
        queue.enqueue(new Callback<List<Clas>>() {
            @Override
            public void onResponse(Call<List<Clas>> call, Response<List<Clas>> response) {
                if(response.isSuccessful()) {
                    listener.onClassReceived(response.body());
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                }
            }

            @Override
            public void onFailure(Call<List<Clas>> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
            }
        });
    }

    @Override
    public void getSectionList(long classId, final OnFinishedListener listener) {
        TeacherApi api = ApiClient.getAuthorizedClient().create(TeacherApi.class);

        Call<List<Section>> queue = api.getSectionList(classId);
        queue.enqueue(new Callback<List<Section>>() {
            @Override
            public void onResponse(Call<List<Section>> call, Response<List<Section>> response) {
                if(response.isSuccessful()) {
                    listener.onSectionReceived(response.body());
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                }
            }

            @Override
            public void onFailure(Call<List<Section>> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
            }
        });
    }

    @Override
    public void saveAlbum(Album album, final OnFinishedListener listener) {
        GalleryApi api = ApiClient.getAuthorizedClient().create(GalleryApi.class);

        Call<Album> queue = api.saveAlbum(album);
        queue.enqueue(new Callback<Album>() {
            @Override
            public void onResponse(Call<Album> call, Response<Album> response) {
                if(response.isSuccessful()) {
                    listener.onAlbumSaved(response.body());
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                }
            }

            @Override
            public void onFailure(Call<Album> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.request_error));
            }
        });
    }
}
