package com.aanglearning.instructorapp.messagegroup;

import com.aanglearning.instructorapp.model.Message;

/**
 * Created by Vinay on 07-04-2017.
 */

public interface MessagePresenter {
    void saveMessage(Message message);

    void getMessages(long groupId);

    void getFollowupMessages(long groupId, long messageId);

    void onDestroy();
}
