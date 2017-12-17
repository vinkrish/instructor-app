package com.aanglearning.instructorapp.attendance;

import com.aanglearning.instructorapp.model.Attendance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinay on 21-04-2017.
 */

interface AttendancePresenter {
    void getClassList(long teacherId);

    void getSectionList(long classId, long teacherId);

    void getAttendance(long sectionId, String date, int session);

    void getTimetable(long sectionId, String dayOfWeek);

    void saveAttendance(List<Attendance> attendances);

    void deleteAttendance(List<Attendance> attendances);

    void onDestroy();
}
