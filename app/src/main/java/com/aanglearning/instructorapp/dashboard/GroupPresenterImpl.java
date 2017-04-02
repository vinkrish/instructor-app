package com.aanglearning.instructorapp.dashboard;

import com.aanglearning.instructorapp.model.Groups;

import java.util.List;

/**
 * Created by Vinay on 02-04-2017.
 */

public class GroupPresenterImpl implements GroupPresenter, GroupInteractor.OnFinishedListener {
    private GroupView mView;
    private GroupInteractor mInteractor;

    GroupPresenterImpl(GroupView view, GroupInteractor interactor) {
        mView = view;
        mInteractor = interactor;
    }

    @Override
    public void getGroups(long userId) {
        if (mView != null) {
            mView.showProgress();
            mInteractor.getGroups(userId, this);
        }
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public void onError() {
        if (mView != null) {
            mView.hideProgess();
            mView.showError();
        }
    }

    @Override
    public void onAPIError(String message) {
        if (mView != null) {
            mView.hideProgess();
            mView.showAPIError(message);
        }
    }

    @Override
    public void onGroupsReceived(List<Groups> groupsList) {
        if (mView != null) {
            mView.setGroups(groupsList);
            mView.hideProgess();
        }
    }

}
