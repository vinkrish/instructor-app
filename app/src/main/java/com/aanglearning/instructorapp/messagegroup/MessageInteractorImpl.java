package com.aanglearning.instructorapp.messagegroup;

import com.aanglearning.instructorapp.api.ApiClient;
import com.aanglearning.instructorapp.api.TeacherApi;
import com.aanglearning.instructorapp.model.Message;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinay on 07-04-2017.
 */

public class MessageInteractorImpl implements MessageInteractor {
    @Override
    public void getMessages(long groupId, final OnFinishedListener listener) {
        TeacherApi api = ApiClient.getAuthorizedClient().create(TeacherApi.class);

        Call<ArrayList<Message>> classList = api.getGroupMessages(groupId);
        classList.enqueue(new Callback<ArrayList<Message>>() {
            @Override
            public void onResponse(Call<ArrayList<Message>> call, Response<ArrayList<Message>> response) {
                if(response.isSuccessful()) {
                    listener.onMessageReceived(response.body());
                } else {
                    listener.onError();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Message>> call, Throwable t) {
                listener.onError();
            }
        });
    }

    @Override
    public void getFollowupMessages(long groupId, long messageId, final OnFinishedListener listener) {
        TeacherApi api = ApiClient.getAuthorizedClient().create(TeacherApi.class);

        Call<ArrayList<Message>> classList = api.getGroupMessagesFromId(groupId, messageId);
        classList.enqueue(new Callback<ArrayList<Message>>() {
            @Override
            public void onResponse(Call<ArrayList<Message>> call, Response<ArrayList<Message>> response) {
                if(response.isSuccessful()) {
                    listener.onFollowupMessagesReceived(response.body());
                } else {
                    listener.onError();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Message>> call, Throwable t) {
                listener.onError();
            }
        });
    }
}
