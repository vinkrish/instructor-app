package com.aanglearning.instructorapp.messagerecipient;

import com.aanglearning.instructorapp.model.MessageRecipient;

import java.util.List;

/**
 * Created by Vinay on 25-08-2017.
 */

interface MessageRecipientView {
    void showProgress();

    void hideProgress();

    void showError(String message);

    void showMessageRecipient(List<MessageRecipient> messageRecipient);
}
