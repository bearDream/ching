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

public class MeFragment extends Fragment {

    @ViewById(R.id.login_button)
    private Button mLoginButton;

    private View mView;

    private Context mContext;

    private DefaultNavigationBar mMeNavigationBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_me,null);
        mContext = getActivity();
        Log.e("mNavigationBar","mNavigationBar ------ ");
        mMeNavigationBar = new DefaultNavigationBar
                .Builder(mContext, (ViewGroup) mView)
                .setTitle("我的")
                .builder();
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button loginButton = (Button) mView.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}
