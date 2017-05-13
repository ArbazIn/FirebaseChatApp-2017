package com.tech.chatapp.model;

import java.io.Serializable;

/**
 * Created by arbaz on 9/5/17.
 */

public class UserListMain implements Serializable {
    public String userName;

    public UserListMain(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
