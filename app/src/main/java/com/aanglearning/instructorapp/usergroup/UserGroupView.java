package com.aanglearning.instructorapp.usergroup;

import com.aanglearning.instructorapp.model.Student;
import com.aanglearning.instructorapp.model.Teacher;

import java.util.List;

/**
 * Created by Vinay on 01-04-2017.
 */

public interface UserGroupView {
    void showProgress();

    void hideProgress();

    void setError();

    void showAPIError(String message);

    void showStudents(List<Student> students);

    void showTeachers(List<Teacher> teachers);
}
