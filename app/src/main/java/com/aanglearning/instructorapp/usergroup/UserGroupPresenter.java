package com.aanglearning.instructorapp.usergroup;

import com.aanglearning.instructorapp.model.UserGroup;

import java.util.ArrayList;

/**
 * Created by Vinay on 01-04-2017.
 */

interface UserGroupPresenter {
    void getUserGroup(long groupId);

    void saveUserGroup(ArrayList<UserGroup> userGroups);

    void deleteUsers(ArrayList<UserGroup> userGroups);

    void onDestroy();
}
