package me.chiying.ching;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.chiying.baselibrary.base.BaseActivity;
import me.chiying.baselibrary.http.EngineCallBack;
import me.chiying.baselibrary.http.HttpUtils;
import me.chiying.baselibrary.ioc.CheckNet;
import me.chiying.baselibrary.ioc.OnClick;
import me.chiying.baselibrary.ioc.ViewById;
import me.chiying.ching.model.ResultModel;
import me.chiying.ching.model.UserCacheModel;
import me.chiying.framelibrary.db.DaoSupport;
import me.chiying.framelibrary.db.DaoSupportFactory;
import me.chiying.framelibrary.db.IDaoSupport;
import me.chiying.framelibrary.loadingDialog.LoadingDialog;

public class LoginActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    Properties properties = new Properties();

    //加载loading动画层
    private Dialog mLoadingDialog;

    //加载带文字的loading动画层
    private Dialog mLoadingTextDialog;

    //获取手机信息
    private PackageInfo mPackageInfo;

    //处理登录信息的handler
    private loginHandler mLoginHandler;
    private isLoginHandler mIsLoginHandler;

    //处理注册信息的handler
    private registerHandler mRegisterHandler;

    //手机型号
    private String mModel;

    //显示动画动作
    TranslateAnimation mShowAction;

    //隐藏动画动作
    TranslateAnimation mHiddenAction;

    //数据库
    private DaoSupport<UserCacheModel> daoSupport;
    private SQLiteDatabase mSQLiteDatabase;

    @ViewById(R.id.login_username)
    private EditText mLoginUsername;
    @ViewById(R.id.login_password)
    private EditText mLoginPassword;
    @ViewById(R.id.register_username)
    private EditText mRegisterUsername;
    @ViewById(R.id.register_password)
    private EditText mRegisterPassword;
    @ViewById(R.id.login_register)
    private TextView mLoginRegister;
    @ViewById(R.id.login_button)
    private Button mLoginButton;
    @ViewById(R.id.login_layout)
    private LinearLayout mLoginLayout;
    @ViewById(R.id.register_layout)
    private LinearLayout mRegisterLayout;
    @ViewById(R.id.register_button)
    private Button mRegisterButton;
    @ViewById(R.id.register_login_text)
    private TextView mRegisterLoginButton;
    @ViewById(R.id.show_hide_pw_check)
    private CheckBox mPasswordCheckBox;


    private AnimationDrawable mAnimationDrawable;
    @ViewById(R.id.login_forgot)
    private TextView mLoginForgot;

    @CheckNet
    @Override
    protected void initData() {
        //new一个handler接收信息
        mLoginHandler = new loginHandler();
        mIsLoginHandler = new isLoginHandler();
        mRegisterHandler = new registerHandler();

        PackageManager mPackageManager = this.getPackageManager();
        try {
            mPackageInfo = mPackageManager.getPackageInfo(
                    this.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        isLogin();//判断是否登录
    }

    @Override
    protected void initView() {
        mLoadingDialog = LoadingDialog.createAnimationLoading(this);

        mLoadingTextDialog = LoadingDialog.createAnimationLoadingText(this, "稍等一下哦");

        mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(500);

        mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
        mHiddenAction.setDuration(500);

        mPasswordCheckBox.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initTitle() {
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_login);
    }

    /*
        切换登陆和注册界面
     */
    @OnClick(R.id.login_register)
    private void login_to_registerClick() {
        mLoginLayout.startAnimation(mHiddenAction);
        mLoginLayout.setVisibility(View.GONE);
        mRegisterLayout.startAnimation(mShowAction);
        mRegisterLayout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.register_login_text)
    private void register_to_loginClick() {
        mRegisterLayout.startAnimation(mHiddenAction);
        mRegisterLayout.setVisibility(View.GONE);
        mLoginLayout.startAnimation(mShowAction);
        mLoginLayout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.register_button)
    private void registerButtonClick() {
        String username = mRegisterUsername.getText().toString().trim();
        String password = mRegisterPassword.getText().toString().trim();
        if (checkLogin(username, password)) {
            register(username, password, mModel);
        } else {
            Toast.makeText(this, "请输入手机号和密码哦~", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.login_button)
    private void loginButtonClick() {
        String username = mLoginUsername.getText().toString().trim();
        String password = mLoginPassword.getText().toString().trim();
        if (checkLogin(username, password)) {
            Log.d("TAG", "MODE -->" + Build.MODEL);
            mModel = Build.MODEL;
            login(username, password, mModel);
        } else {
            Toast.makeText(this, "请输入手机号和密码哦~", Toast.LENGTH_SHORT).show();
        }
    }

    //发送登录请求
    private void login(String username, String password, String model) {
        String url = this.getString(R.string.url);
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", username);
        params.put("password", password);
        params.put("model", model);
        //发送请求打开加载层
        mLoadingDialog.show();
        //发送请求给登录请求   0:响应成功   -1:网络错误
        HttpUtils.with(this).addParams(params)
                .post()
                .url(url + "/login")
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
                        LoginActivity.this.mLoginHandler.sendMessage(msg);
                    }

                    @Override
                    public void onSuccess(String result) {
                        Log.d("success: ->", result);
                        Message msg = new Message();
                        //new一个bundle对象用以存放数据
                        Bundle bundle = new Bundle();
                        bundle.putString("0", result);
                        msg.setData(bundle);
                        LoginActivity.this.mLoginHandler.sendMessage(msg);
                    }
                });
    }

    //判断是否登录
    private void isLogin() {
        String url = this.getString(R.string.url);
        //发送请求打开加载层
        mLoadingTextDialog.show();
        //发送请求给登录请求   0:响应成功   -1:网络错误
        HttpUtils.with(this)
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
                        LoginActivity.this.mIsLoginHandler.sendMessage(msg);
                    }

                    @Override
                    public void onSuccess(String result) {
                        Log.d("success: ->", result);
                        Message msg = new Message();
                        //new一个bundle对象用以存放数据
                        Bundle bundle = new Bundle();
                        bundle.putString("0", result);
                        msg.setData(bundle);
                        LoginActivity.this.mIsLoginHandler.sendMessage(msg);
                    }
                });
    }

    //发送注册请求
    private void register(String mobile, String password, String model) {
        String url = this.getString(R.string.url);
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("password", password);
        params.put("model", model);
        //发送请求打开加载层
        mLoadingTextDialog.show();
        //发送请求给登录请求   0:响应成功   -1:网络错误
        HttpUtils.with(this).addParams(params)
                .post()
                .url(url + "/register")
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
                        LoginActivity.this.mRegisterHandler.sendMessage(msg);
                    }

                    @Override
                    public void onSuccess(String result) {
                        Log.d("success: ->", result);
                        Message msg = new Message();
                        //new一个bundle对象用以存放数据
                        Bundle bundle = new Bundle();
                        bundle.putString("0", result);
                        msg.setData(bundle);
                        LoginActivity.this.mRegisterHandler.sendMessage(msg);
                    }
                });
    }

    private boolean checkLogin(String mobile, String password) {
        String regExp = "^1[3|4|5|7|8][0-9]\\d{4,8}$";
        Pattern p = Pattern.compile(regExp);
        if (!TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(password)) {
            Matcher m = p.matcher(mobile);
            if (m.find())
                return true;
            else {
                Toast.makeText(this, "请输入正确的手机号哦", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return false;
    }

    @Override
    public void onCheckedChanged(CompoundButton checkbox, boolean checked) {
        if (checked) {
            mLoginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            mLoginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }

        Editable editable = mLoginPassword.getText();
        Selection.setSelection(editable, editable.length());
    }

    //handler的内部类
    class loginHandler extends Handler {

        public loginHandler() {
        }

        public loginHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.d("Log", "Handler --> " + msg);
            Log.d("Log", "msg --> " + msg.getData());
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String mobile = mLoginUsername.getText().toString().trim();

            if (bundle.getString("0") != null) {
                mLoadingDialog.cancel();
                Gson gson = new Gson();
                ResultModel resultJson = gson.fromJson(bundle.getString("0"), ResultModel.class);
                //返回code为0说明登陆成功，-1则可能用户名出错
                if (resultJson.getCode() == 0) {
                    //正常登陆，将username和token写入sqllite
                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();

                    //1、保存登录状况  设置状态为已登录
                    SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                    editor.putBoolean("is_login",true);

                    System.out.println(resultJson.getData());
                    //2、保存用户信息
                    editor.putString("user_info", resultJson.getData());
                    editor.commit();



                    //4、存入数据库持久化
//                    IDaoSupport<UserCacheModel> daoSupport = DaoSupportFactory.getFactory().getDao(UserCacheModel.class);
//                    List<UserCacheModel> userCache = daoSupport.querrySupport().selection("mobile = ?").selectionArgs(mobile).query();
//                    if (userCache.size() != 0) {
//                        //如果有数据则更新数据库缓存的token
//                        daoSupport.update(new UserCacheModel(resultJson.getData()), "mobile = ?", mobile);
//                    } else
//                        daoSupport.insert(new UserCacheModel(resultJson.getData()));

                    //进入主页面
//                    startActivity(MainActivity.class);
                    //3、关闭登录页面
                    finish();
                    overridePendingTransition(R.anim.fade_in_anim, R.anim.fade_out_anim);//activity切换效果
                } else {
                    Toast.makeText(LoginActivity.this, "登陆失败，请确认用户名和密码是否正确", Toast.LENGTH_LONG).show();
                }
            }
            if (bundle.getString("-1") != null) {
                mLoadingDialog.cancel();
                Toast.makeText(LoginActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //handler的内部类
    class isLoginHandler extends Handler {

        public isLoginHandler() {
        }

        public isLoginHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.d("Log", "Handler --> " + msg);
            Log.d("Log", "msg --> " + msg.getData());
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String mobile = mLoginUsername.getText().toString().trim();

            if (bundle.getString("0") != null) {
                //已登录
                mLoadingTextDialog.cancel();
                Gson gson = new Gson();
                ResultModel resultJson = gson.fromJson(bundle.getString("0"), ResultModel.class);
                System.out.println(resultJson.getCode());
                //返回code为0说明登陆成功，-1则可能用户名出错
                if (resultJson.getCode() == 0) {
                    //正常登陆，将username和token写入sqllite
                    Toast.makeText(LoginActivity.this, "登录信息没有过期", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("info", MODE_PRIVATE).edit();
                    editor.remove("is_login");
                    editor.remove("user_info");
                    editor.commit();
                    Toast.makeText(LoginActivity.this, "登录信息已过期，请重新登录", Toast.LENGTH_LONG).show();
                }
            }
            if (bundle.getString("-1") != null) {
                mLoadingTextDialog.cancel();
                Toast.makeText(LoginActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class registerHandler extends Handler {

        public registerHandler() {
        }

        public registerHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.d("Log", "register Handler --> " + msg);
            Log.d("Log", "msg --> " + msg.getData());
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String mobile = mRegisterUsername.getText().toString().trim();
            String password = mRegisterPassword.getText().toString().trim();
            if (bundle.getString("0") != null) {
                //网络通讯正常
                mLoadingTextDialog.cancel();
                Gson gson = new Gson();
                ResultModel resultJson = gson.fromJson(bundle.getString("0"), ResultModel.class);
                Log.d("TAG", "code  --> " + resultJson.getCode());
                //返回code为0说明登陆成功，-1则可能用户名出错
                if (resultJson.getCode() == 0) {
                    //注册成功，自动进行登录，将username和token写入sqllite
                    Toast.makeText(LoginActivity.this, resultJson.getData(), Toast.LENGTH_SHORT).show();
                    login(mobile, password, mModel);
                } else {
                    Toast.makeText(LoginActivity.this, resultJson.getData(), Toast.LENGTH_LONG).show();
                }
            }
            if (bundle.getString("-1") != null) {
                mLoadingTextDialog.cancel();
                Toast.makeText(LoginActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
