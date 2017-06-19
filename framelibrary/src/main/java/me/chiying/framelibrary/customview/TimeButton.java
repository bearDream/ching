package me.chiying.framelibrary.customview;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.Button;

import me.chiying.framelibrary.R;

/**
 * Created by soft01 on 2017/5/5.
 * 邮箱：450848477@qq.com
 * Version:1.0
 * Description:
 */

public class TimeButton extends Button {

    private int mCount;

    private Handler mCountHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //不断倒计时
            --mCount;
            if (mCount >= 0){
                countDown(mCount);
            }else {
                ableStatus(R.color.bg_splash_guide1);
            }
        }
    };


    public TimeButton(Context context) {
        super(context , null);
    }

    public TimeButton(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public TimeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //倒计时的button， 三种状态：能够点击  不能点击(请稍候)  倒计时

    //状态更改
    public void ableStatus(int color){
        setEnabled(true);
        setText("获取验证码");
        setTextColor(Color.BLACK);
        setBackgroundResource(R.drawable.circle_button);
    }

    //请稍候状态
    public void laterOnStatus(int color){
        setEnabled(false);
        setText("请稍候");
        setTextColor(Color.BLACK);
        setBackgroundResource(R.drawable.circle_button_press);
        mCount = 10;
        countDown(mCount);
    }

    //倒计时
    public void countDown(int count){
        this.setEnabled(false);//设置为不可点击
        this.setText(count+"秒后重新获取");
        mCountHandler.sendEmptyMessageDelayed(0, 1000);
    }


}
