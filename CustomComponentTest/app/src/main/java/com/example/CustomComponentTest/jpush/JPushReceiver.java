package com.example.CustomComponentTest.jpush;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.CustomComponentTest.activity.HomeActivity;
import com.example.CustomComponentTest.activity.LoginActivity;
import com.example.CustomComponentTest.manager.UserManager;
import com.example.CustomComponentTest.module.PushMessage;
import com.example.mysdk.adutil.ResponseEntityToModule;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

import static android.content.ContentValues.TAG;

/*
* @function 用来接收极光SDK推送给app的消息
* */

public class JPushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //获取bundle
        Bundle bundle = intent.getExtras();
        //当前intent是否是我们需要跳转的类型
        if(intent.getAction().equals(JPushInterface.ACTION_NOTIFICATION_RECEIVED)){
        //不需要跳转
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[JPushReceiver] 接收到推送下来的通知的ID: " + notifactionId);
        }else if(intent.getAction().equals(JPushInterface.ACTION_NOTIFICATION_OPENED)){
        //需要跳转的action
        /**
         * 此处可以通过写一个方法，决定出要跳转到那些页面，一些细节的处理，可以通过是不是从推送过来的，去多一个分支去处理。
         * 1.应用未启动,------>启动主页----->不需要登陆信息类型，直接跳转到消息展示页面
         *                         ----->需要登陆信息类型，由于应用都未启动，肯定不存在已经登陆这种情况------>跳转到登陆页面
         *                                                                                                 ----->登陆完毕，跳转到信息展示页面。
         *                                                                                                 ----->取消登陆，返回首页。
         * 2.如果应用已经启动，------>不需要登陆的信息类型，直接跳转到信息展示页面。
         *                 ------>需要登陆的信息类型------>已经登陆----->直接跳转到信息展示页面。
         *                                      ------>未登陆------->则跳转到登陆页面
         *                                                                      ----->登陆完毕，跳转到信息展示页面。
         *                                                                      ----->取消登陆，回到首页。
         *
         * 3.startActivities(Intent[]);在推送中的妙用,注意startActivities在生命周期上的一个细节,
         * 前面的Activity是不会真正创建的，直到要到对应的页面
         * 4.如果为了复用，可以将极光推送封装到一个Manager类中,为外部提供init, setTag, setAlias,
         * setNotificationCustom等一系列常用的方法。
         */
            PushMessage message = (PushMessage) ResponseEntityToModule.parseJsonToModule(bundle.getString(JPushInterface.EXTRA_EXTRA),PushMessage.class);
            if(getCurrentTask(context)){
                //应用已经启动
                Intent pushIntent = new Intent();
                pushIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//创建一个任务栈  若有 则复用  没有则创建一个新的
                pushIntent.putExtra("pushMessage",message);
                //如果需要登录且当前没有登录
                if(message != null && message.messageType.equals(2) && !UserManager.getInstance().hasLogined()){
                    pushIntent.setClass(context, LoginActivity.class);
                    pushIntent.putExtra("fromPush",true);//表示当前页面是推送过去的
                }
                //用户已经登录或者不需要登录, 直接跳转到消息展示页面
                else{
                    pushIntent.setClass(context, PushMessageActivity.class);
                }
                context.startActivity(pushIntent);
            } else{
                //应用未启动
                Intent mainIntent = new Intent(context, HomeActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //需要登录
                if(message != null && message.messageType.equals("2")){
                    Intent loginIntent = new Intent(context,LoginActivity.class);
                    mainIntent.putExtra("pushMessage",message);
                    mainIntent.putExtra("fromPush",true);
                    context.startActivities(new Intent[]{mainIntent,loginIntent});//先运行loginIntent   再运行mainIntent
                }
                //不需要登录
                else{
                    Intent pushIntent = new Intent(context,PushMessageActivity.class);
                    pushIntent.putExtra("pushMessage",message);
                    context.startActivities(new Intent[]{mainIntent,pushIntent});
                }

            }
        }

    }
    /**
     * 这个是真正的获取指定包名的应用程序是否在运行(无论前台还是后台)
     *
     * @return
     */
    private boolean getCurrentTask(Context context) {
        ActivityManager activityManager = (ActivityManager)context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> appProcessInfos = activityManager.getRunningTasks(50);
        for(RunningTaskInfo process : appProcessInfos){
            if(process.baseActivity.getPackageName().equals(context.getPackageName()) ||
                process.topActivity.getPackageName().equals(context.getPackageName())){
                    return true;
            }
        }
        return false;
    }
}
