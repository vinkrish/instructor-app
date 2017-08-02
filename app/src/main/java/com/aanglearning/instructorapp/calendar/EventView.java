package com.aanglearning.instructorapp.calendar;

import com.aanglearning.instructorapp.model.Evnt;

import java.util.List;

/**
 * Created by Vinay on 31-07-2017.
 */

public interface EventView {
    void showProgress();

    void hideProgess();

    void showError(String message);

    void setEvents(List<Evnt> events);
}
