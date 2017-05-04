package com.aanglearning.instructorapp.usergroup;

import com.aanglearning.instructorapp.model.Student;
import com.aanglearning.instructorapp.model.Teacher;
import com.aanglearning.instructorapp.model.UserGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinay on 01-04-2017.
 */

public interface UserGroupInteractor {

    interface OnFinishedListener {
        void onError(String message);

        void onUserGroupReceived(GroupUsers groupUsers);

        void onUserGroupSaved();

        void onUsersDeleted();
    }

    void getUserGroup(long groupId, UserGroupInteractor.OnFinishedListener listener);

    void saveUserGroup(ArrayList<UserGroup> userGroups, UserGroupInteractor.OnFinishedListener listener);

    void deleteUsers(ArrayList<UserGroup> userGroups, UserGroupInteractor.OnFinishedListener listener);
}
