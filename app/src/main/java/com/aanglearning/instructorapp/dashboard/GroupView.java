package com.aanglearning.instructorapp.dashboard;

import com.aanglearning.instructorapp.model.Groups;

import java.util.List;

/**
 * Created by Vinay on 02-04-2017.
 */

public interface GroupView {
    void showProgress();

    void hideProgess();

    void showError();

    void showAPIError(String message);

    void setGroups(List<Groups> groups);
}
