package me.chiying.framelibrary.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import me.chiying.framelibrary.db.curd.QuerySupport;


/**
 * Created by soft01 on 2017/4/25.
 * 邮箱：450848477@qq.com
 * Version:1.0
 * Description: 数据库接口
 */

public interface IDaoSupport<T> {

    //插入单条数据
    public long insert(T t);

    public void init(SQLiteDatabase database, Class<T> clazz);

    //批量插入数据
    public void insert(List<T> data);

    //查询
    QuerySupport<T> querrySupport();

    //按照语句查询
//    public List<T> query();

    //删除
    public int delete(String whereClause, String... whereArgs);

    //更新
    public int update(T obj, String whereClause, String... whereArgs);
}
