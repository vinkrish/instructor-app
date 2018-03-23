package com.aanglearning.instructorapp.newgroup;

import com.aanglearning.instructorapp.model.Clas;
import com.aanglearning.instructorapp.model.Groups;
import com.aanglearning.instructorapp.model.Section;

import java.util.List;

/**
 * Created by Vinay on 30-03-2017.
 */

public class NewGroupPresenterImpl implements NewGroupPresenter, NewGroupInteractor.OnFinishedListener {
    private NewGroupView mView;
    private NewGroupInteractor mInteractor;

    NewGroupPresenterImpl(NewGroupView view, NewGroupInteractor interactor) {
        mView = view;
        mInteractor = interactor;
    }

    @Override
    public void getClassList(long teacherId) {
        mView.showProgress();
        mInteractor.getClassList(teacherId, this);
    }

    @Override
    public void getSectionList(long classId) {
        mView.showProgress();
        mInteractor.getSectionList(classId, this);
    }

    @Override
    public void saveGroup(Groups groups) {
        mView.showProgress();
        mInteractor.saveGroup(groups, this);
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
    public void onClasReceived(List<Clas> clasList) {
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
    public void onGroupSaved(Groups groups) {
        if (mView != null) {
            mView.groupSaved(groups);
            mView.hideProgress();
        }
    }
}
