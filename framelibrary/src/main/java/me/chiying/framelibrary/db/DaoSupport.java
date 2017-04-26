package me.chiying.framelibrary.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.chiying.framelibrary.db.curd.QuerySupport;


/**
 * Created by soft01 on 2017/4/25.
 * 邮箱：450848477@qq.com
 * Version:1.0
 * Description: 数据库接口的实现类
 */

public class DaoSupport<T> implements IDaoSupport<T> {

    private SQLiteDatabase mSQLiteDatabase;
    private Class<T> mClazz;

    private QuerySupport<T> mQuerySupport;

    public void init(SQLiteDatabase database, Class<T> clazz){
        this.mSQLiteDatabase = database;
        this.mClazz = clazz;

        //创建表(如果表不存在)
        StringBuffer sb = new StringBuffer();

        /*
            create table if not exists Person (
                id Integer  primary key autoincrement,
                name text,
                age Integer)

         */

        sb.append("create table if not exists ").append(DaoUtil.getTableName(clazz)).append(" (id Integer  primary key autoincrement, ");

        Field[] fields = mClazz.getDeclaredFields();
        for (Field field : fields){
            field.setAccessible(true);
            //将属性名当作字段名放到创建表语句中
            sb.append(field.getName()).append(DaoUtil.getColumnType(field.getType().getSimpleName()));
            sb.append(", ");
        }

        sb.replace(sb.length()-2,sb.length(),")");

        mSQLiteDatabase.execSQL(sb.toString());
    }

    @Override
    public void insert(List<T> data) {
        mSQLiteDatabase.beginTransaction();
        for (T da : data) {
            insert(da);
        }
        mSQLiteDatabase.setTransactionSuccessful();
        mSQLiteDatabase.endTransaction();
    }

    @Override
    public QuerySupport<T> querrySupport() {
        if(mQuerySupport == null){
            mQuerySupport = new QuerySupport<>(mSQLiteDatabase ,mClazz);
        }
        return mQuerySupport;
    }

    public int delete(String sql, String[] args) {
        return mSQLiteDatabase.delete(DaoUtil.getTableName(mClazz), sql, args);
    }

    public int update(T obj, String sql, String... args) {
        ContentValues value = contentValuesByObj(obj);
        return mSQLiteDatabase.update(DaoUtil.getTableName(mClazz), value, sql, args);
    }

    //插入的为任意的bean对象
    @Override
    public long insert(T obj) {
        /*
            ContentValues values = new ContentValues();
            values.put("name","zc");
            values.put("age","20");
            db.insert(""Person",null,values);
         */
        ContentValues values = contentValuesByObj(obj);

        return mSQLiteDatabase.insert(DaoUtil.getTableName(mClazz), null, values);
    }

    private ContentValues contentValuesByObj(T obj) {

        ContentValues values = new ContentValues();
        Field[] fields = mClazz.getDeclaredFields();

        for (Field field : fields){
            try {
                field.setAccessible(true);
                String key = field.getName();
                //获取value值
                Object value = field.get(obj);

                Method putMethod = ContentValues.class.getDeclaredMethod("put", String.class, value.getClass());

                //通过反射执行put
                putMethod.invoke(values, key, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return values;
    }
}
