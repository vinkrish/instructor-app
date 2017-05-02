package com.aanglearning.instructorapp.messagegroup;

import com.aanglearning.instructorapp.model.Message;

import java.util.ArrayList;

/**
 * Created by Vinay on 07-04-2017.
 */

interface MessageView {
    void showProgress();

    void hideProgress();

    void setError();

    void showAPIError(String message);

    void onMessageSaved(Message message);

    void showMessages(ArrayList<Message> messages);

    void showFollowupMessages(ArrayList<Message> messages);
}
