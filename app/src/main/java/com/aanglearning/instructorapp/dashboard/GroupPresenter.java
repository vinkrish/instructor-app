package com.aanglearning.instructorapp.dashboard;

/**
 * Created by Vinay on 02-04-2017.
 */

interface GroupPresenter {
    void getGroups(long teacherId);

    void onDestroy();
}
