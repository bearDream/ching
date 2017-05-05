package me.chiying.baselibrary.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by soft01 on 2017/5/5.
 * 邮箱：450848477@qq.com
 * Version:1.0
 * Description:
 */

public class GetDrawable {

    private Context mContext;

    public static Drawable getDrawableFromImage(Context context, int image){
        Drawable imageDrawable = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            imageDrawable = context.getResources().getDrawable(image, null);
        }else {
            imageDrawable = context.getResources().getDrawable(image);
        }
        return imageDrawable;
    }
}
