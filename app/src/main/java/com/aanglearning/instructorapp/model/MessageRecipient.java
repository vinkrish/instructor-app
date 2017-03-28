package com.aanglearning.instructorapp.model;

public class MessageRecipient {
    private long id;
    private long recipientId;
    private long recipientGroupId;
    private long messageId;
    private boolean isRead;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(long recipientId) {
        this.recipientId = recipientId;
    }

    public long getRecipientGroupId() {
        return recipientGroupId;
    }

    public void setRecipientGroupId(long recipientGroupId) {
        this.recipientGroupId = recipientGroupId;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

}
