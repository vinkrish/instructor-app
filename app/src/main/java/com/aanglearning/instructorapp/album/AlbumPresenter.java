package com.aanglearning.instructorapp.album;

import com.aanglearning.instructorapp.model.AlbumImage;
import com.aanglearning.instructorapp.model.DeletedAlbumImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinay on 01-11-2017.
 */

interface AlbumPresenter {
    void getAlbumUpdate(long albumId);

    void saveAlbumImages(List<AlbumImage> albumImages);

    void deleteAlbumImages(ArrayList<DeletedAlbumImage> deletedAlbumImages);

    void getAlbumImagesAboveId(long albumId, long id);

    void getAlbumImages(long albumId);

    void getRecentDeletedAlbumImages(long albumId, long id);

    void getDeletedAlbumImages(long albumId);

    void onDestroy();
}
