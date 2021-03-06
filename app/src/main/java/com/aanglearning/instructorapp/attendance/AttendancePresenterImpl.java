package com.aanglearning.instructorapp.attendance;

import com.aanglearning.instructorapp.model.Attendance;
import com.aanglearning.instructorapp.model.Clas;
import com.aanglearning.instructorapp.model.Section;
import com.aanglearning.instructorapp.model.Timetable;

import java.util.List;

/**
 * Created by Vinay on 21-04-2017.
 */

public class AttendancePresenterImpl implements AttendancePresenter,
        AttendanceInteractor.OnFinishedListener {

    private AttendanceView mView;
    private AttendanceInteractor mInteractor;

    AttendancePresenterImpl(AttendanceView view, AttendanceInteractor interactor) {
        mView = view;
        mInteractor = interactor;
    }

    @Override
    public void getClassList(long teacherId) {
        mView.showProgress();
        mInteractor.getClassList(teacherId, this);
    }

    @Override
    public void getSectionList(long classId, long teacherId) {
        mView.showProgress();
        mInteractor.getSectionList(classId, teacherId, this);
    }

    @Override
    public void getAttendance(long sectionId, String date, int session) {
        mView.showProgress();
        mInteractor.getAttendance(sectionId, date, session, this);
    }

    @Override
    public void getTimetable(long sectionId, String dayOfWeek) {
        mView.showProgress();
        mInteractor.getTimetable(sectionId, dayOfWeek, this);
    }

    @Override
    public void saveAttendance(List<Attendance> attendances) {
        mView.showProgress();
        mInteractor.saveAttendance(attendances, this);
    }

    @Override
    public void deleteAttendance(List<Attendance> attendances) {
        mView.showProgress();
        mInteractor.deleteAttendance(attendances, this);
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public void onError(String message) {
        if (mView != null) {
            mView.hideProgress();
            mView.showError(message);
        }
    }

    @Override
    public void loadOffline(String tableName) {
        if (mView != null) {
            mView.showOffline(tableName);
        }
    }

    @Override
    public void onClassReceived(List<Clas> clasList) {
        if (mView != null) {
            mView.showClass(clasList);
            mView.hideProgress();
        }
    }

    @Override
    public void onSectionReceived(List<Section> sectionList) {
        if (mView != null) {
            mView.showSection(sectionList);
            mView.hideProgress();
        }
    }

    @Override
    public void onTimetableReceived(List<Timetable> timetables) {
        if (mView != null) {
            mView.showTimetable(timetables);
            mView.hideProgress();
        }
    }

    @Override
    public void onAttendanceReceived(AttendanceSet attendanceSet) {
        if (mView != null) {
            mView.showAttendance(attendanceSet);
            mView.hideProgress();
        }
    }

    @Override
    public void onAttendanceSaved() {
        if (mView != null) {
            mView.hideProgress();
            mView.attendanceSaved();
        }
    }

    @Override
    public void onAttendanceDeleted() {
        if (mView != null) {
            mView.hideProgress();
            mView.attendanceDeleted();
        }
    }
}
