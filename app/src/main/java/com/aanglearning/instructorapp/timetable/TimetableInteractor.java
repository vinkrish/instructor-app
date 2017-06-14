package com.aanglearning.instructorapp.timetable;

import com.aanglearning.instructorapp.model.TeacherTimetable;

import java.util.List;

/**
 * Created by Vinay on 13-06-2017.
 */

interface TimetableInteractor {
    interface OnFinishedListener {
        void onError(String message);

        void onTimetableReceived(List<TeacherTimetable> timetableList);
    }

    void getTimetable(long teacherId, TimetableInteractor.OnFinishedListener listener);
}
