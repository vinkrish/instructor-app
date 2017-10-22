package com.aanglearning.instructorapp.usergroup;

import com.aanglearning.instructorapp.model.DeletedGroup;

/**
 * Created by Vinay on 01-04-2017.
 */

interface UserGroupView {
    void showProgress();

    void hideProgress();

    void showError(String message);

    void showUserGroup(GroupUsers groupUsers);

    void userGroupSaved();

    void userGroupDeleted();

    void groupDeleted();

    void onDeletedGroupSync();
}
