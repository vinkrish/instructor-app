package com.aanglearning.instructorapp.usergroup;

import com.aanglearning.instructorapp.model.Student;
import com.aanglearning.instructorapp.model.Teacher;

import java.util.List;

/**
 * Created by Vinay on 01-04-2017.
 */

public interface UserGroupInteractor {

    interface OnFinishedListener {
        void onError();

        void onAPIError(String message);

        void onStudentsReceived(List<Student> students);

        void onTeachersReceived(List<Teacher> teachers);
    }

    void getClassStudents(long classId, UserGroupInteractor.OnFinishedListener listener);

    void getSectionStudents(long sectionId, UserGroupInteractor.OnFinishedListener listener);

    void getClassSubjectTeachers(long classId, UserGroupInteractor.OnFinishedListener listener);

    void getSectionSubjectTeachers(long sectionId, UserGroupInteractor.OnFinishedListener listener);
}
