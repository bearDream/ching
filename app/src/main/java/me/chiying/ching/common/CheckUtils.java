package me.chiying.ching.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Map;

import me.chiying.baselibrary.http.EngineCallBack;
import me.chiying.baselibrary.http.HttpUtils;
import me.chiying.ching.LoginActivity;
import me.chiying.ching.model.ResultModel;
import me.chiying.ching.model.UserCacheModel;
import me.chiying.framelibrary.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by soft01 on 2017/5/5.
 * 邮箱：450848477@qq.com
 * Version:1.0
 * Description:
 */

public class CheckUtils {

    private static Context mContext = null;

    private static isLoginHandler mIsLoginHandler;

    public static void checkLoginStatus(Context context) {
        mContext = context;
        mIsLoginHandler = new isLoginHandler();
        isLoginHttp();
    }

    //判断是否登录
    private static void isLoginHttp() {
        String url = mContext.getString(R.string.url);
        //发送请求打开加载层
        //发送请求给登录请求   0:响应成功   -1:网络错误
        HttpUtils.with(mContext)
                .post()
                .addParam("is_login", "true")
                .url(url + "/isLogin")
                .execute(new EngineCallBack() {
                    @Override
                    public void onPreExecute(Context context, Map<String, Object> params) {
//                        mLoading.setVisibility(View.VISIBLE);
                        Log.e("网络请求之前：", "预处理");
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("网络出错：", e + "");
                        //new一个message对象
                        Message msg = new Message();
                        //new一个bundle对象用以存放数据
                        Bundle bundle = new Bundle();
                        bundle.putString("-1", "网络出错");
                        //将数据存放到Bundle中
                        msg.setData(bundle);
                        //发送数据给Handler
                        mIsLoginHandler.sendMessage(msg);
                    }

                    @Override
                    public void onSuccess(String result) {
                        Log.d("success: ->", result);
                        Gson gson = new Gson();
                        UserCacheModel userCacheModel = gson.fromJson(result, UserCacheModel.class);
                        //new一个message对象
                        Message msg = new Message();
                        //new一个bundle对象用以存放数据
                        Bundle bundle = new Bundle();
                        bundle.putString("0", result);
                        //将数据存放到Bundle中
                        msg.setData(bundle);
                        //发送数据给Handler
                        mIsLoginHandler.sendMessage(msg);
                    }
                });
    }

    //handler的内部类
    static class isLoginHandler extends Handler {

        public isLoginHandler() {}

        public isLoginHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.d("Log", "Handler --> " + msg);
            Log.d("Log", "msg --> " + msg.getData());
            super.handleMessage(msg);
            Bundle bundle = msg.getData();

            if (bundle.getString("0") != null) {
                //已登录
                Gson gson = new Gson();
                ResultModel resultJson = gson.fromJson(bundle.getString("0"), ResultModel.class);
                System.out.println(resultJson.getCode());
                //返回code为0说明登陆成功，-1则可能用户名出错
                if (resultJson.getCode() == 0) {
                    //正常登陆，将username和token写入sqllite
                    Toast.makeText(mContext, "登录信息没有过期", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences.Editor editor = mContext.getSharedPreferences("info", MODE_PRIVATE).edit();
                    editor.remove("is_login");
                    editor.remove("user_info");
                    editor.commit();
                    Toast.makeText(mContext, "登录信息已过期，请重新登录", Toast.LENGTH_LONG).show();
                }
            }
            if (bundle.getString("-1") != null) {
                Toast.makeText(mContext, "网络出错", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
