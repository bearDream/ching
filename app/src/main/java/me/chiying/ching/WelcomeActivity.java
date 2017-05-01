package me.chiying.ching;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import me.chiying.baselibrary.ioc.ViewById;

public class WelcomeActivity extends AppCompatActivity {

    private static final int WAIT_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //设置图片显示动画效果
        Animation animation = AnimationUtils.loadAnimation(WelcomeActivity.this, R.anim.fade_in_anim);
        animation.setDuration(2000);
        findViewById(R.id.welcome_image).startAnimation(animation);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fade_in_anim,R.anim.fade_out_anim);//activity切换效果
            }
        },WAIT_TIME);
    }

}
