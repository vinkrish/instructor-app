package com.aanglearning.instructorapp.chat;

import com.aanglearning.instructorapp.model.Message;

import java.util.List;

/**
 * Created by Vinay on 28-04-2017.
 */

interface ChatView {
    void showProgress();

    void hideProgress();

    void showError(String message);

    void onMessageSaved(Message message);

    void showRecentMessages(List<Message> messages);

    void showMessages(List<Message> messages);

    void showFollowupMessages(List<Message> messages);
}
