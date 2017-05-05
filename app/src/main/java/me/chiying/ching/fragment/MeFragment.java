package me.chiying.ching.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import me.chiying.baselibrary.Utils.GetDrawable;
import me.chiying.baselibrary.ioc.OnClick;
import me.chiying.baselibrary.ioc.ViewById;
import me.chiying.ching.LoginActivity;
import me.chiying.ching.R;
import me.chiying.ching.model.UserCacheModel;
import me.chiying.ching.model.UserInfo;
import me.chiying.framelibrary.navigationBar.DefaultNavigationBar;

/**
 * Created by soft01 on 2017/5/2.
 * 邮箱：450848477@qq.com
 * Version:1.0
 * Description:
 */

public class MeFragment extends Fragment implements View.OnClickListener {

    //登录页面的组件
    private LinearLayout mLoginLayout;

    private LinearLayout mUserInfoLayout;

    private ImageView mUserAvatar;

    private TextView mUserName;

    private View mView;

    private Context mContext;

    private Boolean is_login = false;

    private UserCacheModel userModel;

    private DefaultNavigationBar mMeNavigationBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_me,null);
        mContext = getActivity();
//        initTitle();
        initView();
        return mView;
    }

    //初始化View
    private void initView() {
        mLoginLayout = (LinearLayout) mView.findViewById(R.id.login_layout);
        mUserInfoLayout = (LinearLayout) mView.findViewById(R.id.user_layout);
        mUserAvatar = (ImageView) mView.findViewById(R.id.user_avatar);
        mUserName = (TextView) mView.findViewById(R.id.user_name);
        mLoginLayout.setOnClickListener(this);

    }

    //初始化Title
    private void initTitle() {
        mMeNavigationBar = new DefaultNavigationBar
                .Builder(mContext, (ViewGroup) mView)
                .setTitle("我的")
                .setRightText("编辑")
                .builder();
    }

    //当Activity创建完成时
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        IsLogin();
    }

    @Override
    public void onResume() {
        super.onResume();
        IsLogin();
    }

    //点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_layout:
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    //判断是否登录
    private void IsLogin(){
        SharedPreferences sp = mContext.getSharedPreferences("info", Context.MODE_PRIVATE);
        is_login = sp.getBoolean("is_login",true);
        String userInfo = sp.getString("user_info","");
        System.out.println(userInfo);
        //判断是否登录
        if (is_login && !TextUtils.isEmpty(userInfo)){
            //将登录信息显示，并更换mLoginLayout 和  mUserInfoLayout的显示状态
            Gson gson = new Gson();
            UserInfo user = gson.fromJson(userInfo, UserInfo.class);
            String avatarImage = user.getAvatar();
            if (TextUtils.isEmpty(avatarImage)){
                //没有头像加载默认头像
                Glide.with(mContext).load(R.drawable.login_user).into(mUserAvatar);
            }else {
                Glide.with(mContext).load(avatarImage).into(mUserAvatar);
            }
            mUserName.setText(user.getUsername());
            mLoginLayout.setVisibility(View.GONE);
            mUserInfoLayout.setVisibility(View.VISIBLE);
        }else {
            Glide.with(mContext).load(R.drawable.login_user).into(mUserAvatar);
        }
    }
}
