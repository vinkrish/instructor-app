package com.aanglearning.instructorapp.newgroup;

import com.aanglearning.instructorapp.model.Clas;
import com.aanglearning.instructorapp.model.Groups;
import com.aanglearning.instructorapp.model.Section;
import com.aanglearning.instructorapp.model.Student;
import com.aanglearning.instructorapp.model.Teacher;

import java.util.List;

/**
 * Created by Vinay on 30-03-2017.
 */

interface NewGroupView {
    void showProgress();

    void hideProgress();

    void setError();

    void showAPIError(String message);

    void showClass(List<Clas> clasList);

    void showSection(List<Section> sectionList);

    void groupSaved(Groups groups);
}
