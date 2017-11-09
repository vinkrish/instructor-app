package com.aanglearning.instructorapp.gallery;

import com.aanglearning.instructorapp.model.Album;
import com.aanglearning.instructorapp.model.DeletedAlbum;

import java.util.ArrayList;

/**
 * Created by Vinay on 30-10-2017.
 */

interface GalleryPresenter {
    void saveAlbum(Album album);

    void deleteAlbum(DeletedAlbum deletedAlbum);

    void getAlbumsAboveId(long schoolId, long id);

    void getAlbums(long schoolId);

    void getRecentDeletedAlbums(long schoolId, long id);

    void getDeletedAlbums(long schoolId);

    void onDestroy();
}