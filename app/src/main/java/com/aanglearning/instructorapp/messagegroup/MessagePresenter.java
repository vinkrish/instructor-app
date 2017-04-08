package com.aanglearning.instructorapp.messagegroup;

/**
 * Created by Vinay on 07-04-2017.
 */

public interface MessagePresenter {
    void getMessages(long groupId);

    void getFollowupMessages(long groupId, long messageId);

    void onDestroy();
}
