package me.chiying.ching.model;

import java.util.Date;

/**
 * Created by soft01 on 2017/5/5.
 * 邮箱：450848477@qq.com
 * Version:1.0
 * Description:
 */

public class UserInfo {

    private int userId;
    private String username;
    private String email;
    private String mobile;
    private String real_name;
    private Integer gender;
    private Date birth_date;
    private Integer type;
    private Integer status;
    private Integer org_id;
    private String openid;
    private Date last_login_date;
    private String last_login_ip;
    private Integer logins;
    private String avatar;
    private String token;

    public UserInfo(int userId, String username, String email, String mobile, String real_name, Integer gender, Date birth_date, Integer type, Integer status, Integer org_id, String openid, Date last_login_date, String last_login_ip, Integer logins, String avatar, String token) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.mobile = mobile;
        this.real_name = real_name;
        this.gender = gender;
        this.birth_date = birth_date;
        this.type = type;
        this.status = status;
        this.org_id = org_id;
        this.openid = openid;
        this.last_login_date = last_login_date;
        this.last_login_ip = last_login_ip;
        this.logins = logins;
        this.avatar = avatar;
        this.token = token;
    }

    public UserInfo(int userId, String username, String mobile, String avatar, String token) {
        this.userId = userId;
        this.username = username;
        this.mobile = mobile;
        this.avatar = avatar;
        this.token = token;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrg_id() {
        return org_id;
    }

    public void setOrg_id(Integer org_id) {
        this.org_id = org_id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Date getLast_login_date() {
        return last_login_date;
    }

    public void setLast_login_date(Date last_login_date) {
        this.last_login_date = last_login_date;
    }

    public String getLast_login_ip() {
        return last_login_ip;
    }

    public void setLast_login_ip(String last_login_ip) {
        this.last_login_ip = last_login_ip;
    }

    public Integer getLogins() {
        return logins;
    }

    public void setLogins(Integer logins) {
        this.logins = logins;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", real_name='" + real_name + '\'' +
                ", gender=" + gender +
                ", birth_date=" + birth_date +
                ", type=" + type +
                ", status=" + status +
                ", org_id=" + org_id +
                ", openid='" + openid + '\'' +
                ", last_login_date=" + last_login_date +
                ", last_login_ip='" + last_login_ip + '\'' +
                ", logins=" + logins +
                ", avatar='" + avatar + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
