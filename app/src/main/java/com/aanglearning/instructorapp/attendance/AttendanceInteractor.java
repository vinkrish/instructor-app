package com.aanglearning.instructorapp.attendance;

import com.aanglearning.instructorapp.model.Attendance;
import com.aanglearning.instructorapp.model.Clas;
import com.aanglearning.instructorapp.model.Section;
import com.aanglearning.instructorapp.model.Timetable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinay on 21-04-2017.
 */

public interface AttendanceInteractor {
    interface OnFinishedListener {
        void onError(String message);

        void onClassReceived(List<Clas> clasList);

        void onSectionReceived(List<Section> sectionList);

        void onTimetableReceived(List<Timetable> timetables);

        void onAttendanceReceived(AttendanceSet attendanceSet);

        void onAttendanceSaved();

        void onAttendanceDeleted();
    }

    void getClassList(long teacherId, AttendanceInteractor.OnFinishedListener listener);

    void getSectionList(long classId, long teacherId, AttendanceInteractor.OnFinishedListener listener);

    void getTimetable(long sectionId, String dayOfWeek, AttendanceInteractor.OnFinishedListener listener);

    void getAttendance(long sectionId, String date, int session, AttendanceInteractor.OnFinishedListener listener);

    void saveAttendance(ArrayList<Attendance> attendances, AttendanceInteractor.OnFinishedListener listener);

    void deleteAttendance(ArrayList<Attendance> attendances, AttendanceInteractor.OnFinishedListener listener);
}
