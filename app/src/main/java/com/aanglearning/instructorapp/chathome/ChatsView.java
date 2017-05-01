package com.aanglearning.instructorapp.chathome;

import com.aanglearning.instructorapp.model.Chat;

import java.util.List;

/**
 * Created by Vinay on 28-04-2017.
 */

interface ChatsView {
    void showProgress();

    void hideProgess();

    void showError(String message);

    void setGroups(List<Chat> chats);
}
