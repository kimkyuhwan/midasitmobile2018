package com.example.hoyeonlee.example.DataSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hoyeonlee on 2018. 5. 27..
 */

public class LoginResult {

    @SerializedName("key")
    @Expose
    private String key;

    @SerializedName("user")
    @Expose
    private User user;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
