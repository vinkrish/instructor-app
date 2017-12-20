package com.aanglearning.instructorapp.newalbum;

import com.aanglearning.instructorapp.model.Album;
import com.aanglearning.instructorapp.model.Clas;
import com.aanglearning.instructorapp.model.Section;

import java.util.List;

/**
 * Created by Vinay on 19-12-2017.
 */

public interface NewAlbumInteractor {
    interface OnFinishedListener {
        void onError(String message);

        void onClassReceived(List<Clas> classList);

        void onSectionReceived(List<Section> sectionList);

        void onAlbumSaved(Album album);
    }

    void getClassList(long teacherId, NewAlbumInteractor.OnFinishedListener listener);

    void getSectionList(long classId, NewAlbumInteractor.OnFinishedListener listener);

    void saveAlbum(Album album, NewAlbumInteractor.OnFinishedListener listener);
}
