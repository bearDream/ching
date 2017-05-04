package me.chiying.framelibrary.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import me.chiying.framelibrary.R;

/**
 * Created by soft01 on 2017/5/4.
 * 邮箱：450848477@qq.com
 * Version:1.0
 * Description:
 */

public class ProportionImageView extends ImageView {

    private float mWidthProportion;
    private float mHeightProportion;

    //直接代码中new的时候走这个
    public ProportionImageView(Context context) {
        super(context);
    }

    //在布局文件中声明会走这个方法
    public ProportionImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //在布局文件中写了style属性的话则会走这个构造
    public ProportionImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ProportionImageView);

        //获取单个属性
        mWidthProportion = array.getFloat(R.styleable.ProportionImageView_widthProportion, 0);
        mHeightProportion = array.getFloat(R.styleable.ProportionImageView_heightProportion, 0);

        Log.d("TAG", "宽高比例："+mWidthProportion+" -- "+mHeightProportion);

    }

    //测量距离，显示View的大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);

        int height = (int) (width*mHeightProportion/mWidthProportion);

        setMeasuredDimension(width, height);
    }
}
