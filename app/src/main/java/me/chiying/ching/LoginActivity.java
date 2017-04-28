package me.chiying.ching;

import android.content.Context;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import me.chiying.baselibrary.base.BaseActivity;
import me.chiying.baselibrary.http.EngineCallBack;
import me.chiying.baselibrary.http.HttpUtils;
import me.chiying.baselibrary.ioc.OnClick;
import me.chiying.baselibrary.ioc.ViewById;
import me.chiying.ching.model.ResultModel;

public class LoginActivity extends BaseActivity {

    Properties properties = new Properties();

    @ViewById(R.id.login_username)
    private EditText mLoginUsername;
    @ViewById(R.id.login_password)
    private EditText mLoginPassword;
    @ViewById(R.id.login_register)
    private Button mLoginRegister;
    @ViewById(R.id.login_button)
    private Button mLoginButton;

    @Override
    protected void initData() {
        //加载配置文件
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_login);
    }

    @OnClick(R.id.login_register)
    private void loginRegisterClick(Button loginRegister) {

    }

    @OnClick(R.id.login_button)
    private void loginButtonClick(Button loginButton) {
//        Toast.makeText(this,mLoginUsername.getText(),Toast.LENGTH_SHORT).show();
//        Toast.makeText(this,mLoginPassword.getText(),Toast.LENGTH_SHORT).show();

        String username = mLoginUsername.getText().toString().trim();
        String password = mLoginPassword.getText().toString().trim();

        Toast.makeText(this,R.string.url,Toast.LENGTH_SHORT).show();
        String url = this.getString(R.string.url);
        //发送请求给登录请求
        HttpUtils.with(this).addParam("username",username).addParam("password",password)
                 .post()
                .url(url+"/login")
                .execute(new EngineCallBack() {
                    @Override
                    public void onPreExecute(Context context, Map<String, Object> params) {
                        Log.e("网络请求之前：","预处理");
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("网络出错：",e+"");
                    }

                    @Override
                    public void onSuccess(String result) {
                        Log.d("success: ->",result);
                        Gson gson = new Gson();
                        gson.fromJson(result, ResultModel.class);
                    }
                });
    }
}
