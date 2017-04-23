package com.aanglearning.instructorapp.usergroup;

/**
 * Created by Vinay on 01-04-2017.
 */

interface UserGroupView {
    void showProgress();

    void hideProgress();

    void setError();

    void showAPIError(String message);

    void showUserGroup(GroupUsers groupUsers);

    void userGroupSaved();

    void userGroupDeleted();
}
