package com.aanglearning.instructorapp.homework;

import com.aanglearning.instructorapp.model.Clas;
import com.aanglearning.instructorapp.model.Homework;
import com.aanglearning.instructorapp.model.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinay on 21-04-2017.
 */

interface HomeworkInteractor {
    interface OnFinishedListener {
        void onError(String message);

        void loadOffline(String tableName);

        void onClassReceived(List<Clas> clasList);

        void onSectionReceived(List<Section> sectionList);

        void onHomeworkReceived(List<Homework> homeworks);

        void onHomeworkSaved(Homework homework);

        void onHomeworkUpdated();

        void onHomeworkDeleted();
    }

    void getClassList(long teacherId, HomeworkInteractor.OnFinishedListener listener);

    void getSectionList(long classId, long teacherId, HomeworkInteractor.OnFinishedListener listener);

    void getHomework(long sectionId, String date, HomeworkInteractor.OnFinishedListener listener);

    void saveHomework(Homework homework, HomeworkInteractor.OnFinishedListener listener);

    void updateHomework(Homework homework, HomeworkInteractor.OnFinishedListener listener);

    void deleteHomework(ArrayList<Homework> homeworks, HomeworkInteractor.OnFinishedListener listener);
}
