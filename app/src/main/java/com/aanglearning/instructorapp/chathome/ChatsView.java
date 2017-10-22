package com.aanglearning.instructorapp.chathome;

import com.aanglearning.instructorapp.model.Chat;

import java.util.List;

/**
 * Created by Vinay on 28-04-2017.
 */

interface ChatsView {
    void showProgress();

    void hideProgress();

    void showError(String message);

    void setChats(List<Chat> chats);

    void onChatDeleted();
}
