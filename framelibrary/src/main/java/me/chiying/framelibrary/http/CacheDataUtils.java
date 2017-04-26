package me.chiying.framelibrary.http;

import android.text.TextUtils;
import android.util.Log;


import java.util.List;

import me.chiying.baselibrary.Utils.MD5;
import me.chiying.framelibrary.db.DaoSupportFactory;
import me.chiying.framelibrary.db.IDaoSupport;


/**
 * Created by soft01 on 2017/4/26.
 * 邮箱：450848477@qq.com
 * Version:1.0
 * Description: 获取缓存和装入缓存
 */

public class CacheDataUtils {

    //获取缓存数据
    public static String getCacheData(String mfinalUrl){
        final IDaoSupport<CacheData> dataDaoSupport = DaoSupportFactory.getFactory().getDao(CacheData.class);

        List<CacheData> cacheDatas = dataDaoSupport.querrySupport()
                .selection("mUrlKey=?").selectionArgs(MD5.GetMD5Code(mfinalUrl)).query();
        if (cacheDatas.size() != 0) {
            String resultJson = cacheDatas.get(0).getmResultJson();
            return resultJson;
        }
        return null;
    }

    //设置缓存数据
    public static long setCacheData(String mfinalUrl, String resultJson) {
        final IDaoSupport<CacheData> dataDaoSupport = DaoSupportFactory.getFactory().getDao(CacheData.class);

        dataDaoSupport.delete("mUrlKey=?",MD5.GetMD5Code(mfinalUrl));
        long num = dataDaoSupport.insert(new CacheData(MD5.GetMD5Code(mfinalUrl), resultJson));
        Log.d("num -> ","行数： "+num);
        return num;
    }
}
