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

        void onRecentMessagesReceived(List<Message> messages);

        void onMessageReceived(List<Message> messages);

        //void onFollowupMessagesReceived(List<Message> messages);
    }

    void saveMessage(Message message, OnFinishedListener listener);

    void getRecentMessages(long groupId, long messageId, OnFinishedListener listener);

    void getMessages(long groupId, OnFinishedListener listener);

    //void getFollowupMessages(long groupId, long messageId, MessageInteractor.OnFinishedListener listener);
}
