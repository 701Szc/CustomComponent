package com.example.CustomComponentTest.application;

import android.app.Application;

import com.example.CustomComponentTest.share.ShareManager;

import cn.sharesdk.framework.ShareSDK;


/*
* @function
 * 1.他是整个程序的入口
 * 2.初始化工作
 * 3.为整个应用的其他模块提供上下文环境
* */
public class CustomComponentApplication extends Application {

    private static CustomComponentApplication mApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        initShareSDK();
    }

    public static CustomComponentApplication getInstance(){
        return mApplication;
    }
    //初始化我们的share SDK
    public void initShareSDK(){
        ShareManager.initSDK(this);
    }
}

