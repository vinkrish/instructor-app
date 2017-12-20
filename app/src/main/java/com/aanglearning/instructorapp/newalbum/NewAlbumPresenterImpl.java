package com.aanglearning.instructorapp.newalbum;

import com.aanglearning.instructorapp.model.Album;
import com.aanglearning.instructorapp.model.Clas;
import com.aanglearning.instructorapp.model.Section;

import java.util.List;

/**
 * Created by Vinay on 19-12-2017.
 */

public class NewAlbumPresenterImpl implements NewAlbumPresenter, NewAlbumInteractor.OnFinishedListener {
    private NewAlbumView mView;
    private NewAlbumInteractor mInteractor;

    NewAlbumPresenterImpl(NewAlbumView view, NewAlbumInteractor interactor) {
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
    public void getSectionList(long classId) {
        if (mView != null) {
            mView.showProgress();
            mInteractor.getSectionList(classId, this);
        }
    }

    @Override
    public void saveAlbum(Album album) {
        if (mView != null) {
            mView.showProgress();
            mInteractor.saveAlbum(album, this);
        }
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
    public void onClassReceived(List<Clas> classList) {
        if (mView != null) {
            mView.showClass(classList);
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
    public void onAlbumSaved(Album album) {
        if (mView != null) {
            mView.albumSaved(album);
            mView.hideProgress();
        }
    }
}
