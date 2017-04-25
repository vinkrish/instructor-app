package com.aanglearning.instructorapp.homework;

import com.aanglearning.instructorapp.model.Clas;
import com.aanglearning.instructorapp.model.Homework;
import com.aanglearning.instructorapp.model.Section;

import java.util.List;

/**
 * Created by Vinay on 21-04-2017.
 */

public interface HomeworkView {
    void showProgress();

    void hideProgress();

    void setError();

    void showAPIError(String message);

    void showClass(List<Clas> clasList);

    void showSection(List<Section> sectionList);

    void showHomeworks(List<Homework> homeworks);

    void homeworkSaved(Homework homework);

    void homeworkUpdated();

    void homeworkDeleted();
}
