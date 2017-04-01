package com.aanglearning.instructorapp.usergroup;

import com.aanglearning.instructorapp.model.Student;
import com.aanglearning.instructorapp.model.Teacher;

import java.util.List;

/**
 * Created by Vinay on 01-04-2017.
 */

public class UserGroupPresenterImpl implements UserGroupPresenter, UserGroupInteractor.OnFinishedListener {

    private UserGroupView mView;
    private UserGroupInteractor mInteractor;

    UserGroupPresenterImpl(UserGroupView view, UserGroupInteractor interactor) {
        mView = view;
        mInteractor = interactor;
    }

    @Override
    public void getClassStudents(long classId) {
        if (mView != null) {
            mView.showProgress();
            mInteractor.getClassStudents(classId, this);
        }
    }

    @Override
    public void getSectionStudents(long sectionId) {
        if (mView != null) {
            mView.showProgress();
            mInteractor.getSectionStudents(sectionId, this);
        }
    }

    @Override
    public void getClassSubjectTeachers(long classId) {
        if (mView != null) {
            mView.showProgress();
            mInteractor.getClassSubjectTeachers(classId, this);
        }
    }

    @Override
    public void getSectionSubjectTeachers(long sectionId) {
        if (mView != null) {
            mView.showProgress();
            mInteractor.getSectionSubjectTeachers(sectionId, this);
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
    public void onStudentsReceived(List<Student> students) {
        if (mView != null) {
            mView.showStudents(students);
            mView.hideProgress();
        }
    }

    @Override
    public void onTeachersReceived(List<Teacher> teachers) {
        if (mView != null) {
            mView.showTeachers(teachers);
            mView.hideProgress();
        }
    }
}
