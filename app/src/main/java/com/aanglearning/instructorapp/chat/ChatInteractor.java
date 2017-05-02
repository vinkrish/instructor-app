package com.aanglearning.instructorapp.chat;

import com.aanglearning.instructorapp.model.Message;

import java.util.ArrayList;

/**
 * Created by Vinay on 28-04-2017.
 */

public interface ChatInteractor {
    interface OnFinishedListener {
        void onError(String message);

        void onMessageSaved(Message message);

        void onMessageReceived(ArrayList<Message> messages);

        void onFollowupMessagesReceived(ArrayList<Message> messages);
    }

    void saveMessage(Message message, ChatInteractor.OnFinishedListener listener);

    void getMessages(String senderRole, long senderId, String recipientRole, long recipeintId,
                     ChatInteractor.OnFinishedListener listener);

    void getFollowupMessages(String senderRole, long senderId, String recipientRole, long recipeintId,
                             long messageId, ChatInteractor.OnFinishedListener listener);
}
