package com.aanglearning.instructorapp.login;

import android.content.Intent;

import com.aanglearning.instructorapp.dao.ServiceDao;
import com.aanglearning.instructorapp.dao.TeacherDao;
import com.aanglearning.instructorapp.fcm.FCMIntentService;
import com.aanglearning.instructorapp.model.Credentials;
import com.aanglearning.instructorapp.model.TeacherCredentials;
import com.aanglearning.instructorapp.util.SharedPreferenceUtil;

/**
 * Created by Vinay on 28-03-2017.
 */

class LoginPresenterImpl implements LoginPresenter, LoginInteractor.OnLoginFinishedListener {

    private LoginView loginView;
    private LoginInteractor interactor;

    LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.interactor = new LoginInteractorImpl();
    }

    @Override
    public void validateCredentials(Credentials credentials) {
        loginView.showProgress();
        interactor.login(credentials, this);
    }

    @Override
    public void pwdRecovery(String username) {
        loginView.showProgress();
        interactor.recoverPwd(username, this);
    }

    @Override
    public void onDestroy() {
        loginView = null;
    }

    @Override
    public void onSuccess(TeacherCredentials credentials) {
        if(loginView != null) {
            loginView.saveUser(credentials);
            loginView.hideProgress();

            /*
            TeacherDao.clear();
            TeacherDao.insert(credentials.getTeacher());
            ServiceDao.clear();
            ServiceDao.insert(credentials.getService());
            SharedPreferenceUtil.saveTeacher(this, credentials);
            */

            loginView.navigateToDashboard();
        }
    }

    @Override
    public void onPwdRecovered() {
        if(loginView != null) {
            loginView.hideProgress();
            loginView.pwdRecovered();
        }
    }

    @Override
    public void onNoUser() {
        if(loginView != null) {
            loginView.hideProgress();
            loginView.noUser();
        }
    }

    @Override
    public void onError(String message) {
        if(loginView != null) {
            loginView.hideProgress();
            loginView.showError(message);
        }
    }
}
