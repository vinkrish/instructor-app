package com.aanglearning.instructorapp.newgroup;

import com.aanglearning.instructorapp.model.Groups;

/**
 * Created by Vinay on 30-03-2017.
 */

public interface GroupPresenter {
    void getClassList(long schoolId);

    void getSectionList(long classId);

    void saveGroup(Groups groups);

    void onDestroy();
}
