package com.aanglearning.instructorapp.model;

/**
 * Created by Vinay on 20-06-2017.
 */

public class MessageEvent {
    public final String message;
    public final long senderId;

    public MessageEvent(String message, long senderId) {
        this.message = message;
        this.senderId = senderId;
    }
}
