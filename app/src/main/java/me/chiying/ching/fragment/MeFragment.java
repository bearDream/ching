package me.chiying.ching.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import me.chiying.baselibrary.ioc.OnClick;
import me.chiying.baselibrary.ioc.ViewById;
import me.chiying.ching.LoginActivity;
import me.chiying.ching.R;
import me.chiying.framelibrary.navigationBar.DefaultNavigationBar;

/**
 * Created by soft01 on 2017/5/2.
 * 邮箱：450848477@qq.com
 * Version:1.0
 * Description:
 */

public class MeFragment extends Fragment implements View.OnClickListener {

    @ViewById(R.id.login_layout)
    private LinearLayout mLoginLayout;

    private View mView;

    private Context mContext;

    private DefaultNavigationBar mMeNavigationBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_me,null);
        mContext = getActivity();
        initTitle();
        initView();
        return mView;
    }

    //初始化View
    private void initView() {
        mLoginLayout = (LinearLayout) mView.findViewById(R.id.login_layout);
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

    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(mContext, "viewResume",Toast.LENGTH_SHORT).show();
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
}
