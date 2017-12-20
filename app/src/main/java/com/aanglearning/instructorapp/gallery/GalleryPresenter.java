package com.aanglearning.instructorapp.gallery;

import com.aanglearning.instructorapp.model.Album;
import com.aanglearning.instructorapp.model.DeletedAlbum;

import java.util.ArrayList;

/**
 * Created by Vinay on 30-10-2017.
 */

interface GalleryPresenter {
    void deleteAlbum(DeletedAlbum deletedAlbum);

    void getAlbumsAboveId(long schoolId, long teacherId, long id);

    void getAlbums(long schoolId, long teacherId);

    void getRecentDeletedAlbums(long schoolId, long teacherId, long id);

    void getDeletedAlbums(long schoolId, long teacherId);

    void onDestroy();
}
