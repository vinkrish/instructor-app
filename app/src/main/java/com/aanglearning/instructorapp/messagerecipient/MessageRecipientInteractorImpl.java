package com.aanglearning.instructorapp.messagerecipient;

import com.aanglearning.instructorapp.App;
import com.aanglearning.instructorapp.R;
import com.aanglearning.instructorapp.api.ApiClient;
import com.aanglearning.instructorapp.api.TeacherApi;
import com.aanglearning.instructorapp.model.MessageRecipient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinay on 25-08-2017.
 */

public class MessageRecipientInteractorImpl implements MessageRecipientInteractor {
    @Override
    public void getMessageRecipient(long groupId, long groupMessageId, final OnFinishedListener listener) {
        TeacherApi api = ApiClient.getAuthorizedClient().create(TeacherApi.class);

        Call<ArrayList<MessageRecipient>> queue = api.getMessageRecipients(groupId, groupMessageId);
        queue.enqueue(new Callback<ArrayList<MessageRecipient>>() {
            @Override
            public void onResponse(Call<ArrayList<MessageRecipient>> call, Response<ArrayList<MessageRecipient>> response) {
                if(response.isSuccessful()) {
                    listener.onMessageRecipientReceived(response.body());
                } else {
                    listener.onError(App.getInstance().getString(R.string.request_error));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MessageRecipient>> call, Throwable t) {
                listener.onError(App.getInstance().getString(R.string.network_error));
            }
        });
    }
}
