package me.chiying.ching;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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

public class LoginActivity extends BaseActivity {

    Properties properties = new Properties();

    //加载loading动画层
    private Dialog loadingDialog;

    //获取手机信息
    private PackageInfo mPackageInfo;

    private loginHandler mLoginHandler;

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
    @ViewById(R.id.login_register)
    private Button mLoginRegister;
    @ViewById(R.id.login_button)
    private Button mLoginButton;
    @ViewById(R.id.login_layout)
    private LinearLayout mLoginLayout;
    @ViewById(R.id.register_layout)
    private LinearLayout mRegisterLayout;
    @ViewById(R.id.register_button)
    private Button mRegisterButton;


    private AnimationDrawable mAnimationDrawable;

    @CheckNet
    @Override
    protected void initData() {
        //new一个handler接收信息
        mLoginHandler = new loginHandler();

        PackageManager mPackageManager = this.getPackageManager();
        try {
            mPackageInfo = mPackageManager.getPackageInfo(
                    this.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initView() {
        loadingDialog = LoadingDialog.createAnimationLoading(this, "loading...");

        mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                                            -1.0f,Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(500);

        mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                                            0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
        mHiddenAction.setDuration(500);
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
    private void registerButtonClick(Button registerButton) {
        mLoginLayout.startAnimation(mHiddenAction);
        mLoginLayout.setVisibility(View.GONE);
        mRegisterLayout.startAnimation(mShowAction);
        mRegisterLayout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.register_button)
    private void registerClick(){
        mRegisterLayout.startAnimation(mHiddenAction);
        mRegisterLayout.setVisibility(View.GONE);
        mLoginLayout.startAnimation(mShowAction);
        mLoginLayout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.login_button)
    private void loginButtonClick(Button loginButton) {
        String username = mLoginUsername.getText().toString().trim();
        String password = mLoginPassword.getText().toString().trim();
        if(checkLogin(username, password)){
            Log.d("TAG","MODE -->"+Build.MODEL);
            mModel = Build.MODEL;
            login(username, password, mModel);
        }else {
            Toast.makeText(this, "请输入用户名和密码哦~",Toast.LENGTH_SHORT).show();
        }
    }

    private void login(String username, String password, String model){
        String url = this.getString(R.string.url);
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("model", model);
        //发送请求打开加载层
        loadingDialog.show();
        //发送请求给登录请求   0:响应成功   -1:网络错误
        HttpUtils.with(this).addParams(params)
                .post()
                .url(url+"/login")
                .execute(new EngineCallBack() {
                    @Override
                    public void onPreExecute(Context context, Map<String, Object> params) {
//                        mLoading.setVisibility(View.VISIBLE);
                        Log.e("网络请求之前：","预处理");
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("网络出错：",e+"");
                        //new一个message对象
                        Message msg = new Message();
                        //new一个bundle对象用以存放数据
                        Bundle bundle = new Bundle();
                        bundle.putString("-1","网络出错");
                        //将数据存放到Bundle中
                        msg.setData(bundle);
                        //发送数据给Handler
                        LoginActivity.this.mLoginHandler.sendMessage(msg);
                    }

                    @Override
                    public void onSuccess(String result) {
                        Log.d("success: ->",result);
                        Message msg = new Message();
                        //new一个bundle对象用以存放数据
                        Bundle bundle = new Bundle();
                        bundle.putString("0",result);
                        msg.setData(bundle);
                        LoginActivity.this.mLoginHandler.sendMessage(msg);
                    }
                });
    }

    private boolean checkLogin(String username, String password){
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            return false;
        }
        return true;
    }

    //handler的内部类
    class loginHandler extends Handler{

        public loginHandler(){}

        public loginHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.d("Log","Handler --> "+msg);
            Log.d("Log","msg --> "+msg.getData());
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String username = mLoginUsername.getText().toString().trim();

            if (bundle.getString("0") != null){
                loadingDialog.cancel();
                Gson gson = new Gson();
                ResultModel resultJson = gson.fromJson(bundle.getString("0"), ResultModel.class);
                System.out.println(resultJson.getCode());
                //返回code为0说明登陆成功，-1则可能用户名出错
                if (resultJson.getCode() == 0){
                    //正常登陆，将username和token写入sqllite
                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    IDaoSupport<UserCacheModel> daoSupport = DaoSupportFactory.getFactory().getDao(UserCacheModel.class);
                    List<UserCacheModel> userCache = daoSupport.querrySupport().selection("username = ?").selectionArgs(username).query();
                    if (userCache.size() != 0){
                        //如果有数据则更新数据库缓存的token
                        daoSupport.update(new UserCacheModel(username, resultJson.getData()), "username = ?", username);
                    }else
                        daoSupport.insert(new UserCacheModel(username, resultJson.getData()));
                }else {
                    Toast.makeText(LoginActivity.this, "登陆失败，请确认用户名和密码是否正确", Toast.LENGTH_LONG).show();
                }
            }
            if (bundle.getString("-1") != null){
                loadingDialog.cancel();
                Toast.makeText(LoginActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
