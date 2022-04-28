package com.example.moduledframe.utils.spfkey;

import android.content.SharedPreferences;


import com.example.moduledframe.BaseManager;

import static android.content.Context.MODE_PRIVATE;

/**
 * SharedPreferences Util
 */

public class SPfUtil {

    private static SPfUtil SpUtil = null;
    private static SharedPreferences sharedPreferences;
    private  SharedPreferences.Editor editor;
    private SPfUtil(){}

    public static synchronized SPfUtil getInstance(){
        if(SpUtil == null){
            SpUtil = new SPfUtil();
            if(sharedPreferences == null){
                sharedPreferences = BaseManager.getInstance().getContext().getSharedPreferences("second", MODE_PRIVATE);
            }
        }
        return SpUtil;
    }

    /**
     * 存储string值
     * @param key
     * @param value
     * @return
     */
    public Boolean setString(String key,String value){
        editor = sharedPreferences.edit();
        editor.putString(key,value);
        return  editor.commit();
    }

    /**
     * 获取String 值
     * @param key
     * @return
     */
    public  String getString(String key){
        String str = sharedPreferences.getString(key,"");
        return str;
    }

    /**
     * 存储booleans值
     * @param key
     * @param booleans
     * @return
     */
    public Boolean setBoolean(String key,Boolean booleans){
        editor = sharedPreferences.edit();
        editor.putBoolean(key,booleans);
        return  editor.commit();
    }

    /**
     * 获取boolean 值
     * @param key
     * @return
     */
    public Boolean getBoolean(String key){
        boolean aBoolean =  sharedPreferences.getBoolean(key,false);
        return aBoolean;
    }

    public Boolean getBoolean(String key, boolean defValue){
        boolean aBoolean =  sharedPreferences.getBoolean(key,defValue);
        return aBoolean;
    }

    /**
     * 存储int类型值
     * @param key
     * @param value
     * @return
     */
    public Boolean setInt(String key,int value){
        editor = sharedPreferences.edit();
        editor.putInt(key,value);
        return  editor.commit();
    }
    /**
     * 获取Int类型值
     * @param key
     * @return
     */
    public int getInt(String key){
        int mInt =  sharedPreferences.getInt(key,-99);
        return mInt;
    }

    public Boolean setLong(String key,long value){
        editor = sharedPreferences.edit();
        editor.putLong(key,value);
        return  editor.commit();
    }

    public long getLong(String key){
        long mLong =  sharedPreferences.getLong(key,0);
        return mLong;
    }


    /**
     * 清除数据
     */
    public void cleanData(){
        sharedPreferences.edit().clear().commit();
    }

    public void remove(String key){
        sharedPreferences.edit().remove(key).commit();
    }


}
