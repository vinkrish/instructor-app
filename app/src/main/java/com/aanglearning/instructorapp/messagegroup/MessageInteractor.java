package com.aanglearning.instructorapp.messagegroup;

import com.aanglearning.instructorapp.model.Message;
import com.aanglearning.instructorapp.usergroup.GroupUsers;
import com.aanglearning.instructorapp.usergroup.UserGroupInteractor;

import java.util.ArrayList;

/**
 * Created by Vinay on 07-04-2017.
 */

public interface MessageInteractor {
    interface OnFinishedListener {
        void onError();

        void onAPIError(String message);

        void onMessageReceived(ArrayList<Message> messages);

        void onFollowupMessagesReceived(ArrayList<Message> messages);
    }

    void getMessages(long groupId, MessageInteractor.OnFinishedListener listener);

    void getFollowupMessages(long groupId, long messageId, MessageInteractor.OnFinishedListener listener);
}
