package com.aanglearning.instructorapp.messagegroup;

import com.aanglearning.instructorapp.model.DeletedMessage;
import com.aanglearning.instructorapp.model.Message;

/**
 * Created by Vinay on 07-04-2017.
 */

interface MessagePresenter {
    void saveMessage(Message message);

    void getRecentMessages(long groupId, long messageId);

    void getMessages(long groupId);

    void deleteMessage(DeletedMessage deletedMessage);

    void getRecentDeletedMessages(long groupId, long id);

    void getDeletedMessages(long groupId);

    //void getFollowupMessages(long groupId, long messageId);

    void onDestroy();
}
