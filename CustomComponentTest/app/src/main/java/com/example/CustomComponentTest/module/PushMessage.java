package com.example.CustomComponentTest.module;

import java.io.Serializable;

public class PushMessage implements Serializable {
    //消息类型，类型为2代表需要登录类型
    public String messageType = null;
    //连接，要打开的Url地址
    public String messageUrl = null;
    //详情内容，要在消息推送页面显示的text
    public String messageContent;
}
