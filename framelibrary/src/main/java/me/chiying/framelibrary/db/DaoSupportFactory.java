package me.chiying.framelibrary.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;

/**
 * Created by soft01 on 2017/4/25.
 * 邮箱：450848477@qq.com
 * Version:1.0
 * Description: 数据库工厂类
 */

public class DaoSupportFactory {

    private static DaoSupportFactory mFactory;

    private SQLiteDatabase mSQLiteDatabase;

    public DaoSupportFactory(){
        //打开（如果不存在则创建）一个数据库
        File dbRoot = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator
                +"nhdz"
                + File.separator
                + "database");
        if (!dbRoot.exists()){
            dbRoot.mkdirs();
        }
        File dbFile = new File(dbRoot,"nhdz.db");
        mSQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(dbFile,null);
        System.out.println(mSQLiteDatabase);
    }

    public static DaoSupportFactory getFactory(){
        if (mFactory == null){
            synchronized (DaoSupportFactory.class){
                if (mFactory == null){
                    mFactory = new DaoSupportFactory();
                }
            }
        }
        return mFactory;
    }

    public <T> IDaoSupport<T> getDao(Class<T> clazz){
        IDaoSupport<T> daoSupport = new DaoSupport();
        daoSupport.init(mSQLiteDatabase,clazz);
        return daoSupport;
    }
}
