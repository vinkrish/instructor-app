package com.aanglearning.instructorapp.newalbum;

import com.aanglearning.instructorapp.model.Album;

/**
 * Created by Vinay on 19-12-2017.
 */

public interface NewAlbumPresenter {
    void getClassList(long teacherId);

    void getSectionList(long classId);

    void saveAlbum(Album album);

    void onDestroy();
}
