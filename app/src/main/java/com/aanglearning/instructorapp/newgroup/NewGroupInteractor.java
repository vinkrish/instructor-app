package com.aanglearning.instructorapp.newgroup;

import com.aanglearning.instructorapp.model.Clas;
import com.aanglearning.instructorapp.model.Groups;
import com.aanglearning.instructorapp.model.Section;

import java.util.List;

/**
 * Created by Vinay on 30-03-2017.
 */

interface NewGroupInteractor {
    interface OnFinishedListener {
        void onError();

        void onAPIError(String message);

        void onClasReceived(List<Clas> clasList);

        void onSectionReceived(List<Section> sectionList);

        void onGroupSaved(Groups groups);
    }

    void getClassList(long schoolId, NewGroupInteractor.OnFinishedListener listener);

    void getSectionList(long classId, NewGroupInteractor.OnFinishedListener listener);

    void saveGroup(Groups groups, NewGroupInteractor.OnFinishedListener listener);
}
