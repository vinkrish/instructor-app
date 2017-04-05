package com.aanglearning.instructorapp.usergroup;

import com.aanglearning.instructorapp.model.Student;
import com.aanglearning.instructorapp.model.Teacher;
import com.aanglearning.instructorapp.model.UserGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinay on 03-04-2017.
 */

public class GroupUsers {
    private ArrayList<UserGroup> userGroupList;
    private ArrayList<Student> students;
    private ArrayList<Teacher> teachers;

    public ArrayList<UserGroup> getUserGroupList() {
        return userGroupList;
    }

    public void setUserGroupList(ArrayList<UserGroup> userGroupList) {
        this.userGroupList = userGroupList;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(ArrayList<Teacher> teachers) {
        this.teachers = teachers;
    }
}
