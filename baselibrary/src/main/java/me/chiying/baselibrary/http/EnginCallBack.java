package me.chiying.baselibrary.http;

import android.content.Context;

import java.util.Map;

/**
 * Created by soft01 on 2017/4/24.
 * 邮箱：450848477@qq.com
 * Version:1.0
 * Description:
 */

public interface EnginCallBack {

    void onPreExecuter(Context context, Map<String, Object> params);

    void onError(Exception e);

    void onSuccess(String result);

    //默认的回调
    public final EnginCallBack DEFAULT_CALL_BACK = new EnginCallBack() {

        @Override
        public void onPreExecuter(Context context, Map<String, Object> params) {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onSuccess(String result) {

        }
    };
}
