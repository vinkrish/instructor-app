package com.aanglearning.instructorapp.album;

import com.aanglearning.instructorapp.model.AlbumImage;
import com.aanglearning.instructorapp.model.DeletedAlbumImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinay on 01-11-2017.
 */

interface AlbumView {
    void showProgress();

    void hideProgress();

    void showError(String message);

    void albumImagesSaved(List<AlbumImage> albumImages);

    void albumImagesDeleted(ArrayList<DeletedAlbumImage> albumImages);

    void setRecentAlbumImages(ArrayList<AlbumImage> albumImages);

    void setAlbumImages(ArrayList<AlbumImage> albumImages);

    void onDeletedAlbumImageSync();
}
