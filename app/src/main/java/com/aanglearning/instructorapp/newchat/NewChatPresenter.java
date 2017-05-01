package com.aanglearning.instructorapp.newchat;

import com.aanglearning.instructorapp.model.Chat;

/**
 * Created by Vinay on 28-04-2017.
 */

public interface NewChatPresenter {
    void getClassList(long teacherId);

    void getSectionList(long classId, long teacherId);

    void getStudentList(long sectionId);

    void saveChat(Chat chat);

    void onDestroy();
}
