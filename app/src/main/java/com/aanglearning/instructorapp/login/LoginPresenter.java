package com.aanglearning.instructorapp.login;

import com.aanglearning.instructorapp.model.Credentials;

/**
 * Created by Vinay on 28-03-2017.
 */

public interface LoginPresenter {

    void validateCredentials(Credentials credentials);

    void pwdRecovery(String authToken, String newPassword);

    void onDestroy();
}
