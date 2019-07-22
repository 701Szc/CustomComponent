package com.example.CustomComponentTest.network.http;

import com.example.CustomComponentTest.module.recommand.BaseRecommandModel;
import com.example.mysdk.okhttp.CommonOkHttpClient;

import com.example.mysdk.okhttp.HttpConstant;
import com.example.mysdk.okhttp.listener.DisposeDataHandle;
import com.example.mysdk.okhttp.listener.DisposeDataListener;
import com.example.mysdk.okhttp.request.CommonRequest;
import com.example.mysdk.okhttp.request.RequestParams;


/*
* @function 存放应用中所有的请求
* */
public class RequestCenter {
//    根据参数发送post请求
    private static void postRequest(String url, RequestParams params, DisposeDataListener listener,Class<?> clazz){
        CommonOkHttpClient.get(CommonRequest.createGetRequest(url,params), new DisposeDataHandle(listener, clazz));
    }

    /**
     * Request recommand data.
     * @function 真正的发送我们的首页请求
     * @param listener the listener
     */

    public static void requestRecommandData(DisposeDataListener listener){
        RequestCenter.postRequest(HttpConstans.HOME_RECOMMAND,null,listener, BaseRecommandModel.class);
    }
}
