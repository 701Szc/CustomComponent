package com.example.CustomComponentTest.network.http;

/**
 * @function: 定义所有请求地址
 */
public class HttpConstans {
    private static final String ROOT_URL = "http://imooc.com/api";
    /*
    * 首页产品请求接口
    * */
    public static String HOME_RECOMMAND = ROOT_URL + "/product/home_recommand.php";
    /**
     * 检查更新接口
     */
    public static String CHECK_UPDATE = ROOT_URL + "/config/check_update.php";
}
