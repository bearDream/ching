package me.chiying.ching.model;

/**
 * Created by soft01 on 2017/4/28.
 * 邮箱：450848477@qq.com
 * Version:1.0
 * Description:
 */

public class ResultModel {

    private int code;
    private String msg;
    private String data;

    public ResultModel(int code, String msg, String data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getData() {
        return data;
    }
}
