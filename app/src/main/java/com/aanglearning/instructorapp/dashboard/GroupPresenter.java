package com.aanglearning.instructorapp.dashboard;

import com.aanglearning.instructorapp.model.Authorization;

/**
 * Created by Vinay on 02-04-2017.
 */

interface GroupPresenter {
    void getGroups(long teacherId);

    void updateFcmToken(Authorization authorization);

    void onDestroy();
}
