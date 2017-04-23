package com.aanglearning.instructorapp.attendance;

import com.aanglearning.instructorapp.model.Attendance;
import com.aanglearning.instructorapp.model.Clas;
import com.aanglearning.instructorapp.model.Section;

import java.util.ArrayList;
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
        if (mView != null) {
            mView.showProgress();
            mInteractor.getClassList(teacherId, this);
        }
    }

    @Override
    public void getSectionList(long classId, long teacherId) {
        if (mView != null) {
            mView.showProgress();
            mInteractor.getSectionList(classId, teacherId, this);
        }
    }

    @Override
    public void getAttendance(long sectionId, String date, int session) {
        if (mView != null) {
            mView.showProgress();
            mInteractor.getAttendance(sectionId, date, session, this);
        }
    }

    @Override
    public void saveAttendance(ArrayList<Attendance> attendances) {
        if (mView != null) {
            mView.showProgress();
            mInteractor.saveAttendance(attendances, this);
        }
    }

    @Override
    public void deleteAttendance(ArrayList<Attendance> attendances) {
        if (mView != null) {
            mView.showProgress();
            mInteractor.deleteAttendance(attendances, this);
        }
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public void onError() {
        if (mView != null) {
            mView.hideProgress();
            mView.setError();
        }
    }

    @Override
    public void onAPIError(String message) {
        if (mView != null) {
            mView.hideProgress();
            mView.showAPIError(message);
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
    public void onAttendanceReceived(AttendanceSet attendanceSet) {
        if (mView != null) {
            mView.showAttendance(attendanceSet);
            mView.hideProgress();
        }
    }

    @Override
    public void onAttendanceSaved() {
        if (mView != null) {
            mView.attendanceSaved();
            mView.hideProgress();
        }
    }

    @Override
    public void onAttendanceDeleted() {
        if (mView != null) {
            mView.attendanceDeleted();
            mView.hideProgress();
        }
    }
}
