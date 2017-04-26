package me.chiying.framelibrary.http;

/**
 * Created by soft01 on 2017/4/26.
 * 邮箱：450848477@qq.com
 * Version:1.0
 * Description:
 */

public class CacheData {

    private String mUrlKey;

    private String mResultJson;

    public CacheData() {
    }

    public CacheData(String mUrlKey, String mResultJson) {
        this.mUrlKey = mUrlKey;
        this.mResultJson = mResultJson;
    }

    public String getmResultJson() {
        return mResultJson;
    }

    public String getmUrlKey() {
        return mUrlKey;
    }
}
