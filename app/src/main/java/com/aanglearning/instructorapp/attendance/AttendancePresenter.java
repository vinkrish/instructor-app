package com.aanglearning.instructorapp.attendance;

import com.aanglearning.instructorapp.model.Attendance;

import java.util.ArrayList;

/**
 * Created by Vinay on 21-04-2017.
 */

public interface AttendancePresenter {
    void getClassList(long teacherId);

    void getSectionList(long classId, long teacherId);

    void getAttendance(long sectionId, String date, int session);

    void saveAttendance(ArrayList<Attendance> attendances);

    void deleteAttendance(ArrayList<Attendance> attendances);

    void onDestroy();
}
