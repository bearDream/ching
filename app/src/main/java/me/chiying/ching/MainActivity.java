package me.chiying.ching;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;

import java.util.ArrayList;

import me.chiying.baselibrary.base.BaseActivity;
import me.chiying.baselibrary.ioc.ViewById;
import me.chiying.ching.adapter.HomeAdapter;
import me.chiying.ching.common.CheckUtils;
import me.chiying.ching.fragment.DynamicFragment;
import me.chiying.ching.fragment.FeaturesFragment;
import me.chiying.ching.fragment.HomeFragment;
import me.chiying.ching.fragment.MeFragment;

/*
    应用的主界面
 */
public class MainActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    @ViewById(R.id.home_viewpager)
    private ViewPager mHomeViewPager;


    //内容fragment区
    private ViewPager mViewPager;

    @ViewById(R.id.home_rb)
    private RadioButton mHomeRb;
    @ViewById(R.id.features_rb)
    private RadioButton mFeaturesRb;
    @ViewById(R.id.dynamic_rb)
    private RadioButton mDynamicRb;
    @ViewById(R.id.me_rb)
    private RadioButton mMeRb;

    @Override
    protected void initData() {
        //设置ViewPager
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new FeaturesFragment());
        fragments.add(new DynamicFragment());
        fragments.add(new MeFragment());

        HomeAdapter adapter = new HomeAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);

        //设置底部tab点击切换fragment事件
        mHomeRb.setOnClickListener(this);
        mFeaturesRb.setOnClickListener(this);
        mDynamicRb.setOnClickListener(this);
        mMeRb.setOnClickListener(this);

        //设置滑动fragment底部tab变化
        mViewPager.addOnPageChangeListener(this);

        //发送请求看后台session是否过期
        CheckUtils.checkLoginStatus(this);

        Log.e("TAG", "是否登录:"+getSharedPreferences("info", MODE_PRIVATE).getBoolean("is_login",false));
        Log.e("TAG", "登录信息:"+getSharedPreferences("info", MODE_PRIVATE).getString("user_info",""));
    }

    @Override
    protected void initView() {
        //将ViewPager赋给mViewPager
        mViewPager = mHomeViewPager;

        //设置沉浸式
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    //底部tab点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home_rb:
                mViewPager.setCurrentItem(0, true);
                break;
            case R.id.features_rb:
                mViewPager.setCurrentItem(1, true);
                break;
            case R.id.dynamic_rb:
                mViewPager.setCurrentItem(2, true);
                break;
            case R.id.me_rb:
                mViewPager.setCurrentItem(3, true);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //切换到相应的fragment
        switch (position){
            case 0:
                mHomeRb.setChecked(true);
                break;
            case 1:
                mFeaturesRb.setChecked(true);
                break;
            case 2:
                mDynamicRb.setChecked(true);
                break;
            case 3:
                mMeRb.setChecked(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
