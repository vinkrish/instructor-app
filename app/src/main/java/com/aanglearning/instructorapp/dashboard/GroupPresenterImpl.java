package com.aanglearning.instructorapp.dashboard;

import com.aanglearning.instructorapp.dao.DeletedGroupDao;
import com.aanglearning.instructorapp.dao.GroupDao;
import com.aanglearning.instructorapp.model.Authorization;
import com.aanglearning.instructorapp.model.DeletedGroup;
import com.aanglearning.instructorapp.model.Groups;

import java.util.List;

/**
 * Created by Vinay on 02-04-2017.
 */

class GroupPresenterImpl implements GroupPresenter, GroupInteractor.OnFinishedListener {
    private GroupView mView;
    private GroupInteractor mInteractor;

    GroupPresenterImpl(GroupView view, GroupInteractor interactor) {
        mView = view;
        mInteractor = interactor;
    }

    @Override
    public void getGroup(long groupId) {
        mView.showProgress();
        mInteractor.getGroup(groupId, this);
    }

    @Override
    public void getGroupsAboveId(long teacherId, long id) {
        mInteractor.getGroupsAboveId(teacherId, id, this);
    }

    @Override
    public void getGroups(long userId) {
        mView.showProgress();
        mInteractor.getGroups(userId, this);
    }

    @Override
    public void getRecentDeletedGroups(long schoolId, long id) {
        mInteractor.getRecentDeletedGroups(schoolId, id, this);
    }

    @Override
    public void getDeletedGroups(long schoolId) {
        mInteractor.getDeletedGroups(schoolId, this);
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
    public void onGroupReceived(Groups group) {
        if (mView != null) {
            mView.hideProgress();
            mView.setGroup(group);
        }
    }

    @Override
    public void onRecentGroupsReceived(List<Groups> groupsList) {
        if (mView != null) {
            mView.setRecentGroups(groupsList);
            mView.hideProgress();
        }
    }

    @Override
    public void onGroupsReceived(List<Groups> groupsList) {
        if (mView != null) {
            mView.setGroups(groupsList);
            mView.hideProgress();
        }
    }

    @Override
    public void onDeletedGroupsReceived(List<DeletedGroup> deletedGroups) {
        if (mView != null) {
            DeletedGroupDao.insertDeletedGroups(deletedGroups);
        }
    }

}
