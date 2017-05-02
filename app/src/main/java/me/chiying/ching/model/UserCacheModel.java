package me.chiying.ching.model;

import java.security.PublicKey;

/**
 * Created by laxzh on 2017/5/1.
 */

public class UserCacheModel {

    private String mobile;

    private String token;

    public UserCacheModel(){}

    public UserCacheModel(String mobile, String token) {
        this.mobile = mobile;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
