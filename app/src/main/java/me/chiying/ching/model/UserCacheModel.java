package me.chiying.ching.model;

/**
 * Created by laxzh on 2017/5/1.
 */

public class UserCacheModel {
    /**
     * code : 0
     * msg : 后台正常
     * data : User{userId=14, username='null', password='e10adc3949ba59abbe56e057f20f883e', salt='null', email='null', mobile='13759498504', realName='null', gender=0, birthDate=null, type=0, status=0, orgId=3, openid='null', isNew=1, lastLoginDate=Thu May 04 16:54:38 CST 2017, lastLoginIp='10.1.180.145', logins=3, avatar='null', token='82e1c1bf2af78c646586d613f26be9f8'}
     */

    private int code;
    private String msg;
    private String data;

    public UserCacheModel() {
    }

    public UserCacheModel(String data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UserCacheModel{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

//    private Integer userId;
//    private String username;
//    private String salt;
//    private String email;
//    private String mobile;
//    private String realName;
//    private Integer gender;
//    private Date birthDate;
//    private Integer type;
//    private Integer status;
//    private Integer orgId;
//    private String openid;
//    private Integer isNew;
//    private Date lastLoginDate;
//    private String lastLoginIp;
//    private Integer logins;
//    private String avatar;
//    private String token;

}
