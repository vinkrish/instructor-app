package com.aanglearning.instructorapp.usergroup;

import com.aanglearning.instructorapp.model.Teacher;

/**
 * Created by Vinay on 04-04-2017.
 */

public class GroupTeacher extends Teacher {
    private boolean isSelected;

    public GroupTeacher(long id, String name) {
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
