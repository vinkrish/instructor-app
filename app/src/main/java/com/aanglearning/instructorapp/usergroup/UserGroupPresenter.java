package com.aanglearning.instructorapp.usergroup;

/**
 * Created by Vinay on 01-04-2017.
 */

public interface UserGroupPresenter {
    void getClassStudents(long classId);

    void getSectionStudents(long sectionId);

    void getClassSubjectTeachers(long classId);

    void getSectionSubjectTeachers(long sectionId);

    void onDestroy();
}
