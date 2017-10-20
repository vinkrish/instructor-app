package com.aanglearning.instructorapp.dashboard;

/**
 * Created by Vinay on 02-04-2017.
 */

interface GroupPresenter {
    void getGroup(long groupId);

    void getGroupsAboveId(long teacherId, long id);

    void getGroups(long teacherId);

    void getRecentDeletedGroups(long schoolId, long id);

    void getDeletedGroups(long schoolId);

    void onDestroy();
}
