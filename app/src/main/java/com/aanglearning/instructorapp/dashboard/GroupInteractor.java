package com.aanglearning.instructorapp.dashboard;

import com.aanglearning.instructorapp.model.DeletedGroup;
import com.aanglearning.instructorapp.model.Groups;

import java.util.List;

/**
 * Created by Vinay on 02-04-2017.
 */

interface GroupInteractor {
    interface OnFinishedListener {
        void onError(String message);

        void onGroupReceived(Groups group);

        void onRecentGroupsReceived(List<Groups> groupsList);

        void onGroupsReceived(List<Groups> groupsList);

        void onDeletedGroupsReceived(List<DeletedGroup> deletedGroups);
    }

    void getGroup(long groupId, GroupInteractor.OnFinishedListener listener);

    void getGroupsAboveId(long userId, long id, GroupInteractor.OnFinishedListener listener);

    void getGroups(long userId, GroupInteractor.OnFinishedListener listener);

    void getRecentDeletedGroups(long schoolId, long id, GroupInteractor.OnFinishedListener listener);

    void getDeletedGroups(long schoolId, GroupInteractor.OnFinishedListener listener);
}
