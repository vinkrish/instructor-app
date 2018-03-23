package com.aanglearning.instructorapp.gallery;

import com.aanglearning.instructorapp.dao.DeletedAlbumDao;
import com.aanglearning.instructorapp.model.Album;
import com.aanglearning.instructorapp.model.DeletedAlbum;

import java.util.List;

/**
 * Created by Vinay on 30-10-2017.
 */

class GalleryPresenterImpl implements GalleryPresenter, GalleryInteractor.OnFinishedListener {
    private GalleryView mView;
    private GalleryInteractor mInteractor;

    GalleryPresenterImpl(GalleryView view, GalleryInteractor interactor) {
        mView = view;
        mInteractor = interactor;
    }

    @Override
    public void deleteAlbum(DeletedAlbum deletedAlbum) {
        mView.showProgress();
        mInteractor.deleteAlbum(deletedAlbum, this);
    }

    @Override
    public void getAlbumsAboveId(long schoolId, long teacherId, long id) {
        mInteractor.getAlbumsAboveId(schoolId, teacherId, id, this);
    }

    @Override
    public void getAlbums(long schoolId, long teacherId) {
        mView.showProgress();
        mInteractor.getAlbums(schoolId, teacherId, this);
    }

    @Override
    public void getRecentDeletedAlbums(long schoolId, long teacherId, long id) {
        mInteractor.getRecentDeletedAlbums(schoolId, teacherId, id, this);
    }

    @Override
    public void getDeletedAlbums(long schoolId, long teacherId) {
        mInteractor.getDeletedAlbums(schoolId, teacherId, this);
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
    public void onAlbumDeleted() {
        if (mView != null) {
            mView.hideProgress();
            mView.albumDeleted();
        }
    }

    @Override
    public void onRecentAlbumsReceived(List<Album> albumList) {
        if (mView != null) {
            mView.setRecentAlbums(albumList);
        }
    }

    @Override
    public void onAlbumsReceived(List<Album> groupsList) {
        if (mView != null) {
            mView.setAlbums(groupsList);
            mView.hideProgress();
        }
    }

    @Override
    public void onDeletedAlbumsReceived(List<DeletedAlbum> deletedAlbums) {
        if (mView != null) {
            DeletedAlbumDao.insertDeletedAlbums(deletedAlbums);
        }
    }
}
