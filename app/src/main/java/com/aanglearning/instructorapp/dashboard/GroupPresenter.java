package com.aanglearning.instructorapp.dashboard;

/**
 * Created by Vinay on 02-04-2017.
 */

public interface GroupPresenter {
    void getGroups(long userId);

    void onDestroy();
}
