package me.chiying.ching.model;

import java.security.PublicKey;

/**
 * Created by laxzh on 2017/5/1.
 */

public class UserCacheModel {

    private String username;

    private String token;

    public UserCacheModel(){}

    public UserCacheModel(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
