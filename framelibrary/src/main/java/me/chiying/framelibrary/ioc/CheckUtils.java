package me.chiying.framelibrary.ioc;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Map;

import me.chiying.baselibrary.http.EngineCallBack;
import me.chiying.baselibrary.http.HttpUtils;
import me.chiying.framelibrary.R;

/**
 * Created by soft01 on 2017/5/5.
 * 邮箱：450848477@qq.com
 * Version:1.0
 * Description:
 */

public class CheckUtils {

    private static Context mContext = null;

    private static boolean is_login = false;


    public static boolean checkLoginStatus(Context context) {
        mContext = context;
        isLoginHttp();
        return is_login;
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
                        result(-1, "网络出错");
                    }

                    @Override
                    public void onSuccess(String result) {
                        Log.d("success: ->", result);
                        Gson gson = new Gson();

                        result(0, result);
                    }
                });
    }

    private static void result(int status, String result){
        if (status != -1){
            Log.e("Tag",result);
            is_login = true;
//            Toast.makeText(mContext, "已登录", Toast.LENGTH_SHORT).show();
        }else {
            System.out.println("...................");
            Log.e("Tag",result);
            is_login = false;
//            Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
        }
    }
}
