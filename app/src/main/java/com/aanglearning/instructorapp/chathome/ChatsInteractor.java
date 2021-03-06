package com.aanglearning.instructorapp.chathome;

import com.aanglearning.instructorapp.model.Chat;

import java.util.List;

/**
 * Created by Vinay on 28-04-2017.
 */

interface ChatsInteractor {
    interface OnFinishedListener {
        void onError(String message);

        void onChatsReceived(List<Chat> chats);

        void onChatDeleted();
    }

    void getChats(long teacherId, ChatsInteractor.OnFinishedListener listener);

    void deleteChat(long id, ChatsInteractor.OnFinishedListener listener);
}
