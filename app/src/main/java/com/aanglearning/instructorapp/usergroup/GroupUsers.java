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

    ArrayList<UserGroup> getUserGroupList() {
        return userGroupList;
    }

    void setUserGroupList(ArrayList<UserGroup> userGroupList) {
        this.userGroupList = userGroupList;
    }

    ArrayList<Student> getStudents() {
        return students;
    }

    void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    void setTeachers(ArrayList<Teacher> teachers) {
        this.teachers = teachers;
    }
}
