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

public interface GroupInteractor {
    interface OnFinishedListener {
        void onError();

        void onAPIError(String message);

        void onClasReceived(List<Clas> clasList);

        void onSectionReceived(List<Section> sectionList);

        void onGroupSaved(Groups groups);
    }

    void getClassList(long schoolId, GroupInteractor.OnFinishedListener listener);

    void getSectionList(long classId, GroupInteractor.OnFinishedListener listener);

    void saveGroup(Groups groups, GroupInteractor.OnFinishedListener listener);
}
