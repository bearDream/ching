package me.chiying.framelibrary.loadingDialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

import me.chiying.framelibrary.R;

/**
 * Created by laxzh on 2017/5/1.
 * 创建一个loading弹层
 */

public class LoadingDialog {

    //创建loading动画弹层
    public static Dialog createAnimationLoading(Context context, String tips) {
        Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.loading_anim);
        dialog.setCanceledOnTouchOutside(false);
        ImageView animationView = (ImageView) dialog.findViewById(R.id.loading_anim);
        //loading如果需要动画，则在layout中加一个textview
//        TextView tv = (TextView) dialog.findViewById(R.id.loading_text);
//        tv.setText(tips);
        animationView.setBackgroundResource(R.drawable.loading_anim);
        AnimationDrawable animationDrawable = (AnimationDrawable) animationView.getBackground();
        animationDrawable.start();
        return dialog;
    }
}
