package me.chiying.framelibrary.customview;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.Button;

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
            if (mCount > 0){
                countDown(mCount);
            }else {
                ableStatus(Color.GREEN);
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
    private void ableStatus(int color){
        setEnabled(true);
        setBackgroundColor(color);
    }

    //请稍候状态
    private void laterOnStatus(int color){
        setEnabled(false);
        setText("请稍候");
        setTextColor(color);
    }

    //倒计时
    private void countDown(int count){
        this.setEnabled(false);//设置为不可点击
        this.setText(mCount+"秒后重新获取验证码");
        mCountHandler.sendEmptyMessageDelayed(0, 1000);
    }


}
