package com.aanglearning.instructorapp.newalbum;

import com.aanglearning.instructorapp.model.Album;
import com.aanglearning.instructorapp.model.Clas;
import com.aanglearning.instructorapp.model.Section;

import java.util.List;

/**
 * Created by Vinay on 19-12-2017.
 */

public interface NewAlbumView {
    void showProgress();

    void hideProgress();

    void showError(String message);

    void showClass(List<Clas> clasList);

    void showSection(List<Section> sectionList);

    void albumSaved(Album album);
}
