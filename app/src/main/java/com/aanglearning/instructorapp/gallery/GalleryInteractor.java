package com.aanglearning.instructorapp.gallery;

import com.aanglearning.instructorapp.model.Album;
import com.aanglearning.instructorapp.model.DeletedAlbum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinay on 30-10-2017.
 */

interface GalleryInteractor {
    interface OnFinishedListener {
        void onError(String message);

        void onAlbumSaved(Album album);

        void onAlbumDeleted();

        void onRecentAlbumsReceived(List<Album> albumList);

        void onAlbumsReceived(List<Album> albumList);

        void onDeletedAlbumsReceived(List<DeletedAlbum> deletedAlbums);
    }

    void saveAlbum(Album album, GalleryInteractor.OnFinishedListener listener);

    void deleteAlbum(DeletedAlbum deletedAlbum, GalleryInteractor.OnFinishedListener listener);

    void getAlbumsAboveId(long schoolId, long teacherId, long id, GalleryInteractor.OnFinishedListener listener);

    void getAlbums(long schoolId, long teacherId, GalleryInteractor.OnFinishedListener listener);

    void getRecentDeletedAlbums(long schoolId, long teacherId, long id, GalleryInteractor.OnFinishedListener listener);

    void getDeletedAlbums(long schoolId, long teacherId, GalleryInteractor.OnFinishedListener listener);
}
