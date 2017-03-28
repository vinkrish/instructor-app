package com.aanglearning.instructorapp.login;

import com.aanglearning.instructorapp.model.Credentials;
import com.aanglearning.instructorapp.model.TeacherCredentials;

/**
 * Created by Vinay on 28-03-2017.
 */

public class LoginPresenterImpl implements LoginPresenter, LoginInteractor.OnLoginFinishedListener {

    private LoginView loginView;
    private LoginInteractor interactor;

    LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.interactor = new LoginInteractorImpl();
    }

    @Override
    public void validateCredentials(Credentials credentials) {
        if(loginView != null) {
            loginView.showProgress();
            interactor.login(credentials, this);
        }
    }

    @Override
    public void pwdRecovery(String authToken, String newPassword) {
        if(loginView != null) {
            loginView.showProgress();
            interactor.recoverPwd(authToken, newPassword, this);
        }
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
    public void onError() {
        if(loginView != null) {
            loginView.hideProgress();
            loginView.setError();
        }
    }

    @Override
    public void onAPIError(String message) {
        if(loginView != null) {
            loginView.hideProgress();
            loginView.showAPIError(message);
        }
    }
}
