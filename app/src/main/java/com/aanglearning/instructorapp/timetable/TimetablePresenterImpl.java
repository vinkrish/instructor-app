package com.aanglearning.instructorapp.timetable;

import com.aanglearning.instructorapp.model.TeacherTimetable;
import com.aanglearning.instructorapp.model.Timetable;

import java.util.List;

/**
 * Created by Vinay on 13-06-2017.
 */

public class TimetablePresenterImpl implements TimetablePresenter, TimetableInteractor.OnFinishedListener {
    private TimetableView mView;
    private TimetableInteractor mInteractor;

    TimetablePresenterImpl(TimetableView view, TimetableInteractor interactor) {
        mView = view;
        mInteractor = interactor;
    }

    @Override
    public void getTimetable(long teacherId) {
        mView.showProgress();
        mInteractor.getTimetable(teacherId, this);
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public void onError(String message) {
        if (mView != null) {
            mView.hideProgess();
            mView.showError(message);
        }
    }

    @Override
    public void onTimetableReceived(List<TeacherTimetable> timetableList) {
        if (mView != null) {
            mView.showTimetable(timetableList);
            mView.hideProgess();
        }
    }
}
