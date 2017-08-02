package com.aanglearning.instructorapp.calendar;

import com.aanglearning.instructorapp.model.Evnt;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Vinay on 31-07-2017.
 */

public class Evnts implements Serializable {
    private List<Evnt> events;

    public List<Evnt> getEvents() {
        return events;
    }

    public void setEvents(List<Evnt> events) {
        this.events = events;
    }
}
