package com.aanglearning.instructorapp.messagegroup;

import com.aanglearning.instructorapp.model.Message;

import java.util.List;

/**
 * Created by Vinay on 07-04-2017.
 */

interface MessageInteractor {
    interface OnFinishedListener {
        void onError(String message);

        void onMessageSaved(Message message);

        void onMessageReceived(List<Message> messages);

        void onFollowupMessagesReceived(List<Message> messages);
    }

    void saveMessage(Message message, MessageInteractor.OnFinishedListener listener);

    void getMessages(long groupId, MessageInteractor.OnFinishedListener listener);

    void getFollowupMessages(long groupId, long messageId, MessageInteractor.OnFinishedListener listener);
}
