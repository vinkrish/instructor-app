package com.aanglearning.instructorapp.dashboard;

import com.aanglearning.instructorapp.model.Groups;

import java.util.List;

/**
 * Created by Vinay on 02-04-2017.
 */

public interface GroupInteractor {
    interface OnFinishedListener {
        void onError();

        void onAPIError(String message);

        void onGroupsReceived(List<Groups> groupsList);
    }

    void getGroups(long userId, GroupInteractor.OnFinishedListener listener);
}
