package com.aanglearning.instructorapp.album;

import com.aanglearning.instructorapp.dao.AlbumDao;
import com.aanglearning.instructorapp.dao.DeletedAlbumImageDao;
import com.aanglearning.instructorapp.model.Album;
import com.aanglearning.instructorapp.model.AlbumImage;
import com.aanglearning.instructorapp.model.DeletedAlbumImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinay on 01-11-2017.
 */

class AlbumPresenterImpl implements AlbumPresenter, AlbumInteractor.OnFinishedListener {
    private AlbumView mView;
    private AlbumInteractor mInteractor;

    AlbumPresenterImpl(AlbumView view, AlbumInteractor interactor) {
        mView = view;
        mInteractor = interactor;
    }

    @Override
    public void getAlbumUpdate(long albumId) {
        mInteractor.getAlbumUpdate(albumId, this);
    }

    @Override
    public void saveAlbumImages(List<AlbumImage> albumImages) {
        mView.showProgress();
        mInteractor.saveAlbumImages(albumImages, this);
    }

    @Override
    public void deleteAlbumImages(ArrayList<DeletedAlbumImage> deletedAlbumImages) {
        mView.showProgress();
        mInteractor.deleteAlbumImages(deletedAlbumImages, this);
    }

    @Override
    public void getAlbumImagesAboveId(long albumId, long id) {
        mInteractor.getAlbumImagesAboveId(albumId, id, this);
    }

    @Override
    public void getAlbumImages(long albumId) {
        mInteractor.getAlbumImages(albumId, this);
    }

    @Override
    public void getRecentDeletedAlbumImages(long albumId, long id) {
        mInteractor.getRecentDeletedAlbumImages(albumId, id, this);
    }

    @Override
    public void getDeletedAlbumImages(long albumId) {
        mInteractor.getDeletedAlbumImages(albumId, this);
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
    public void onAlbumImagesSaved(List<AlbumImage> albumImages) {
        if (mView != null) {
            mView.hideProgress();
            mView.albumImagesSaved(albumImages);
        }
    }

    @Override
    public void onImagesDeleted(ArrayList<DeletedAlbumImage> albumImages) {
        if (mView != null) {
            mView.hideProgress();
            mView.albumImagesDeleted(albumImages);
        }
    }

    @Override
    public void onRecentAlbumImagesReceived(ArrayList<AlbumImage> albumImages) {
        if (mView != null) {
            mView.setRecentAlbumImages(albumImages);
        }
    }

    @Override
    public void onAlbumImagesReceived(ArrayList<AlbumImage> albumImages) {
        if (mView != null) {
            mView.setAlbumImages(albumImages);
            mView.hideProgress();
        }
    }

    @Override
    public void onAlbumImagesDeleted(List<DeletedAlbumImage> deletedAlbumImages) {
        if (mView != null) {
            if (deletedAlbumImages.size() > 0) {
                DeletedAlbumImageDao.insertDeletedAlbumImages(deletedAlbumImages);
            }
            mView.onDeletedAlbumImageSync();
        }
    }
}
