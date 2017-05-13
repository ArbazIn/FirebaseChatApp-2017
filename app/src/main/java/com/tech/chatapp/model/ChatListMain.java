package com.tech.chatapp.model;

import java.io.Serializable;

/**
 * Created by arbaz on 8/5/17.
 */

public class ChatListMain implements Serializable {
    public String message;
    public String user;
    public int senderType;
    public String time;

    public ChatListMain(String message, String user) {
        this.message = message;
        this.user = user;
    }

    public ChatListMain(String message, int senderType, String time) {
        this.message = message;
        this.senderType = senderType;
        this.time = time;
    }

    public ChatListMain(String message, int senderType) {
        this.message = message;
        this.senderType = senderType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getSenderType() {
        return senderType;
    }

    public void setSenderType(int senderType) {
        this.senderType = senderType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
