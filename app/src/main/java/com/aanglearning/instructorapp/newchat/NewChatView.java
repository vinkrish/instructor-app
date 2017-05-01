package com.aanglearning.instructorapp.newchat;

import com.aanglearning.instructorapp.model.Chat;
import com.aanglearning.instructorapp.model.Clas;
import com.aanglearning.instructorapp.model.Section;
import com.aanglearning.instructorapp.model.Student;

import java.util.List;

/**
 * Created by Vinay on 28-04-2017.
 */

public interface NewChatView {
    void showProgress();

    void hideProgess();

    void showError(String message);

    void showClass(List<Clas> clasList);

    void showSection(List<Section> sectionList);

    void showStudent(List<Student> studentList);

    void chatSaved(Chat chat);
}
