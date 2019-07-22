package com.example.mysdk.okhttp;

import com.example.mysdk.module.AdInstance;
import com.example.mysdk.okhttp.listener.DisposeDataHandle;
import com.example.mysdk.okhttp.listener.DisposeDataListener;
import com.example.mysdk.okhttp.request.CommonRequest;

/**
 * Created by renzhiqiang on 16/10/27.
 *
 * @function sdk请求发送中心
 */
public class RequestCenter {

    /**
     * 发送广告请求
     */
    public static void sendImageAdRequest(String url, DisposeDataListener listener) {

        CommonOkHttpClient.post(CommonRequest.createPostRequest(url, null),
                new DisposeDataHandle(listener, AdInstance.class));
    }
}
