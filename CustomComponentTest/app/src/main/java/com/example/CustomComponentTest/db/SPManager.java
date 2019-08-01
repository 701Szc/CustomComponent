package com.example.CustomComponentTest.db;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences;

import com.example.CustomComponentTest.application.CustomComponentApplication;

public class SPManager {
    //当前类的实例
    private static SPManager mInstance;

    private static SharedPreferences sp;
    private static Editor editor;

    private static final String SHARE_PRFERENCE_NAME = "customComponentTest.pre";//文件名
    /**
     * 上次基金更新时间
     */
    public static final String LAST_UPDATE_PRODUCT = "last_update_product";
    public static final String VIDEO_PLAY_SETTING = "video_play_setting";
    public static final String IS_SHOW_GUIDE = "is_show_guide";


    public static SPManager getInstance(){
        if (mInstance == null || sp == null || editor == null) {
            mInstance = new SPManager();
        }
        return mInstance;
    }

    private SPManager(){
        sp = CustomComponentApplication.getInstance().getSharedPreferences(SHARE_PRFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
    }
    //对int类型的写入
    public void putInt(String key, int value){
        editor.putInt(key,value);
        editor.commit();
    }
    //对int类型的读取
    public int getInt(String key,int defaultValue){
        return sp.getInt(key,defaultValue);
    }
    public void putLong(String key, Long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public long getLong(String key, int defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public void putFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    public boolean isKeyExist(String key) {
        return sp.contains(key);
    }

    public float getFloat(String key, float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }
}
