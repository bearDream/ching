package me.chiying.framelibrary.db.curd;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.chiying.framelibrary.db.DaoUtil;


/**
 * Created by soft01 on 2017/4/26.
 * 邮箱：450848477@qq.com
 * Version:1.0
 * Description: 所有查询方法
 */

public class QuerySupport<T> {

    //查询的列
    private String[] mQueryColumns;

    //查询条件
    private String mQuerySelection;

    //查询的参数
    private String[] mQuerySelectionArgs;

    //查询分组
    private String mQueryGroupBy;

    //查询对结果集进行过滤
    private String mQueryHaving;

    //查询排序
    private String mQueryOrderBy;

    //查询可用于分页
    private String mQueryLimit;

    private Class<T> mClass;
    private SQLiteDatabase mSQLiteDatabase;

    public QuerySupport(SQLiteDatabase sqLiteDatabase, Class<T> clazz){
        this.mClass = clazz;
        this.mSQLiteDatabase = sqLiteDatabase;
    }

    public QuerySupport columns(String... columns){
        this.mQueryColumns = columns;
        return this;
    }

    public QuerySupport selectionArgs(String... selectionArgs){
        this.mQuerySelectionArgs = selectionArgs;
        return this;
    }

    public QuerySupport having(String having){
        this.mQueryHaving = having;
        return this;
    }

    public QuerySupport orderBy(String orderBy){
        this.mQueryOrderBy = orderBy;
        return this;
    }

    public QuerySupport limit(String limit){
        this.mQueryLimit = limit;
        return this;
    }

    public QuerySupport groupBy(String groupBy){
        this.mQueryGroupBy = groupBy;
        return this;
    }

    public QuerySupport selection(String selection){
        this.mQuerySelection = selection;
        return this;
    }

    public List<T> query() {
        Cursor cursor = mSQLiteDatabase.query(DaoUtil.getTableName(mClass),mQueryColumns,mQuerySelection,
                mQuerySelectionArgs,mQueryGroupBy,mQueryHaving
                ,mQueryOrderBy,mQueryLimit);
        clearQueryParams();
        return cursorToList(cursor);
    }

    public List<T> queryAll() {
        Cursor cursor = mSQLiteDatabase.query(DaoUtil.getTableName(mClass),null,null,null,null,null,null);
        return cursorToList(cursor);
    }

    private void clearQueryParams() {
        mQuerySelection = null;
        mQuerySelectionArgs = null;
        mQueryGroupBy = null;
        mQueryHaving = null;
        mQueryLimit = null;
        mQueryColumns = null;
        mQueryOrderBy = null;
    }

    private List<T> cursorToList(Cursor cursor) {
        List<T> list = new ArrayList<>();
        if(cursor != null && cursor.moveToFirst()){
            //不断从游标中获取数据
            do {
                try {
                    //反射new对象
                    T instance = mClass.newInstance();
                    Field[] fields = mClass.getDeclaredFields();
                    //遍历属性
                    for (Field field : fields) {
                        field.setAccessible(true);
                        String name = field.getName();
                        //获取角标
                        int index = cursor.getColumnIndex(name);
                        if (index == -1){
                            continue;
                        }
                        //反射获取游标
                        Method cursorMethod = cursorMethod(field.getType());
                        if (cursorMethod != null) {
                            Object value = cursorMethod.invoke(cursor, index);
                            if (value == null){
                                continue;
                            }

                            //处理一些特殊方法
                            if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                                if ("0".equals(String.valueOf(value))) {
                                    value = false;
                                } else if ("1".equals(String.valueOf(value))) {
                                    value = true;
                                }
                            } else if (field.getType() == char.class || field.getType() == Character.class) {
                                value = ((String) value).charAt(0);
                            } else if (field.getType() == Date.class) {
                                long date = (Long) value;
                                if (date <= 0) {
                                    value = null;
                                } else {
                                    value = new Date(date);
                                }
                            }

                            field.set(instance, value);
                        }
                        list.add(instance);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    private Method cursorMethod(Class<?> type) throws Exception{
        String methodName = getColumMethodName(type);
        Method method = Cursor.class.getMethod(methodName, int.class);
        return method;

    }

    private String getColumMethodName(Class<?> fieldType){
        String typeName;
        if (fieldType.isPrimitive()){
            typeName = DaoUtil.capitalize(fieldType.getName());
        }else {
            typeName = fieldType.getSimpleName();
        }
        String methodName = "get" + typeName;
        if ("getBoolean".equals(methodName)){
            methodName = "getInt";
        }else if ("getChar".equals(methodName) || "getCharacter".equals(methodName)){
            methodName = "getString";
        }else if ("getDate".equals(methodName)){
            methodName = "getLong";
        }else if ("getInteger".equals(methodName)){
            methodName = "getInt";
        }
        return methodName;
    }

}
