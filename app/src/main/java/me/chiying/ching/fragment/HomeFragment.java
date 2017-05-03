package me.chiying.ching.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.chiying.ching.R;
import me.chiying.framelibrary.navigationBar.DefaultNavigationBar;

/**
 * Created by soft01 on 2017/5/2.
 * 邮箱：450848477@qq.com
 * Version:1.0
 * Description:
 */

public class HomeFragment extends Fragment {

    private View mView;

    private Context mContext;

    private DefaultNavigationBar mHomeNavigationBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home,null);
        mContext = getActivity();
        Log.e("mNavigationBar","mNavigationBar ------ ");
        mHomeNavigationBar = new DefaultNavigationBar
                .Builder(mContext, (ViewGroup) mView)
                .setTitle("首页")
                .builder();
        return mView;
    }
}
