package com.aanglearning.instructorapp.usergroup;

import com.aanglearning.instructorapp.model.Student;

/**
 * Created by Vinay on 04-04-2017.
 */

public class GroupStudent extends Student {
    private boolean isSelected;

    public GroupStudent(long id, String name) {
        super(id, name);
        this.isSelected = false;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
