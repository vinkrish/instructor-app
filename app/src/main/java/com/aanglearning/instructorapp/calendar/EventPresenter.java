package com.aanglearning.instructorapp.calendar;

/**
 * Created by Vinay on 31-07-2017.
 */

interface EventPresenter {
    void getEvents(long schoolId, long teacherId);

    void onDestroy();
}
