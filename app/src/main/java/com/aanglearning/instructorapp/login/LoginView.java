package com.aanglearning.instructorapp.login;

import com.aanglearning.instructorapp.model.TeacherCredentials;

/**
 * Created by Vinay on 28-03-2017.
 */

interface LoginView {
    void showProgress();

    void hideProgress();

    void showError(String message);

    void pwdRecovered();

    void noUser();

    void saveUser(TeacherCredentials teacherCredentials);

    void navigateToDashboard();
}
