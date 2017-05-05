package me.chiying.framelibrary.ioc;

import android.content.Context;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by soft01 on 2017/5/5.
 * 邮箱：450848477@qq.com
 * Version:1.0
 * Description: 检测用户是否登录
 */
// @Target(ElementType.FIELD) 代表Annotation的位置  FIELD属性  TYPE类上  CONSTRUCTOR 构造函数上
@Target(ElementType.METHOD)
// @Retention(RetentionPolicy.CLASS) 什么时候生效 CLASS编译时   RUNTIME运行时  SOURCE源码资源
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckLogin {
    Context contex = null;
}
