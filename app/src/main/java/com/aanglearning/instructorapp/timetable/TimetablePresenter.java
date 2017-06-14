package com.aanglearning.instructorapp.timetable;

/**
 * Created by Vinay on 13-06-2017.
 */

interface TimetablePresenter {
    void getTimetable(long teacherId);

    void onDestroy();
}
