package com.example.CustomComponentTest.network.http;

import com.example.CustomComponentTest.module.course.BaseCourseModel;
import com.example.CustomComponentTest.module.recommand.BaseRecommandModel;
import com.example.CustomComponentTest.module.update.UpdateModel;
import com.example.CustomComponentTest.module.user.User;
import com.example.mysdk.okhttp.CommonOkHttpClient;

import com.example.mysdk.okhttp.listener.DisposeDataHandle;
import com.example.mysdk.okhttp.listener.DisposeDataListener;
import com.example.mysdk.okhttp.listener.DisposeDownloadListener;
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
    public static void downloadFile(String url, String path, DisposeDownloadListener listener) {
        CommonOkHttpClient.downloadFile(CommonRequest.createGetRequest(url, null),
                new DisposeDataHandle(listener, path));
    }

    /**
     * 应用版本号请求
     *
     * @param listener
     */
    public static void checkVersion(DisposeDataListener listener) {
        RequestCenter.postRequest(HttpConstans.CHECK_UPDATE, null, listener, UpdateModel.class);
    }
    /**
     * 用户登陆请求
     *
     * @param listener
     * @param userName
     * @param passwd
     */
    public static void login(String userName, String passwd, DisposeDataListener listener) {

        RequestParams params = new RequestParams();
        params.put("mb", userName);
        params.put("pwd", passwd);
        RequestCenter.postRequest(HttpConstants.LOGIN, params, listener, User.class);
    }
    /**
     * 请求课程详情
     *
     * @param listener
     */
    public static void requestCourseDetail(String courseId, DisposeDataListener listener) {
        RequestParams params = new RequestParams();
        params.put("courseId", courseId);
        RequestCenter.postRequest(HttpConstants.COURSE_DETAIL, params, listener, BaseCourseModel.class);
    }

}
