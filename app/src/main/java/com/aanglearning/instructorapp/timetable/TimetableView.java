package com.aanglearning.instructorapp.timetable;

import com.aanglearning.instructorapp.model.TeacherTimetable;

import java.util.List;

/**
 * Created by Vinay on 13-06-2017.
 */

interface TimetableView {
    void showProgress();

    void hideProgess();

    void showError(String message);

    void showTimetable(List<TeacherTimetable> timetableList);
}
