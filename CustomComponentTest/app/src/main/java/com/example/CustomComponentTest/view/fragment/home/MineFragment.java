package com.example.CustomComponentTest.view.fragment.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.CustomComponentTest.R;
import com.example.CustomComponentTest.activity.LoginActivity;
import com.example.CustomComponentTest.activity.SettingActivity;
import com.example.CustomComponentTest.manager.UserManager;
import com.example.CustomComponentTest.module.update.UpdateModel;
import com.example.CustomComponentTest.network.http.RequestCenter;
import com.example.CustomComponentTest.service.UpdateService;
import com.example.CustomComponentTest.share.ShareDialog;
import com.example.CustomComponentTest.share.ShareManager;
import com.example.CustomComponentTest.util.Util;
import com.example.CustomComponentTest.view.CommonDialog;
import com.example.CustomComponentTest.view.MyQrCodeDialog;
import com.example.CustomComponentTest.view.fragment.BaseFragment;
import com.example.mysdk.okhttp.listener.DisposeDataListener;

import de.hdodenhof.circleimageview.CircleImageView;
import cn.sharesdk.framework.Platform;

/*
* @function: 个人信息Fragment
* */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    /**
     * UI
     */
    private View mContentView;
    private RelativeLayout mLoginLayout;
    private CircleImageView mPhotoView;
    private TextView mLoginInfoView;
    private TextView mLoginView;
    private RelativeLayout mLoginedLayout;
    private TextView mUserNameView;
    private TextView mTickView;
    private TextView mVideoPlayerView;
    private TextView mShareView;
    private TextView mQrCodeView;
    private TextView mUpdateView;

    /*
    * 广播接收器
    * */
    private LoginBroadcastReceiver mReceiver = new LoginBroadcastReceiver();

    public MineFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        registerLoginBroadcast();

    }
    //封装广播注册的方法
    private void registerLoginBroadcast(){
        IntentFilter intentFilter = new IntentFilter(LoginActivity.LOGIN_ACTION);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(mReceiver,intentFilter);

    }

    private void unRegisterLoginBroadcast(){
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(mReceiver);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_mine_layout,null,false);
        initView();
        return mContentView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegisterLoginBroadcast();
    }

    private void initView() {
        mLoginLayout = (RelativeLayout) mContentView.findViewById(R.id.login_layout);
        mLoginLayout.setOnClickListener(this);
        mLoginedLayout = (RelativeLayout) mContentView.findViewById(R.id.logined_layout);
        mLoginedLayout.setOnClickListener(this);

        mPhotoView = (CircleImageView) mContentView.findViewById(R.id.photo_view);
        mPhotoView.setOnClickListener(this);
        mLoginView = (TextView) mContentView.findViewById(R.id.login_view);
        mLoginView.setOnClickListener(this);
        mVideoPlayerView = (TextView) mContentView.findViewById(R.id.video_setting_view);
        mVideoPlayerView.setOnClickListener(this);
        mShareView = (TextView) mContentView.findViewById(R.id.share_imooc_view);
        mShareView.setOnClickListener(this);
        mQrCodeView = (TextView) mContentView.findViewById(R.id.my_qrcode_view);
        mQrCodeView.setOnClickListener(this);
        mLoginInfoView = (TextView) mContentView.findViewById(R.id.login_info_view);
        mUserNameView = (TextView) mContentView.findViewById(R.id.username_view);
        mTickView = (TextView) mContentView.findViewById(R.id.tick_view);

        mUpdateView = (TextView) mContentView.findViewById(R.id.update_view);
        mUpdateView.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        //根据用户信息更新我们的fragment

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.video_setting_view:
                mContext.startActivity(new Intent(mContext, SettingActivity.class));
                break;

            case R.id.update_view:
                checkVersion();
                break;

            case R.id.login_layout:
            case R.id.login_view:
                //未登录，则跳转到登录页面
                if(!UserManager.getInstance().hasLogined()){
                    toLogin();
                }
                break;
            case R.id.my_qrcode_view:
                if(!UserManager.getInstance().hasLogined()){
                    toLogin();
                }else{
                    //已登录根据用户ID生成二维码显示
                    MyQrCodeDialog dialog = new MyQrCodeDialog(mContext);
                    dialog.show();
                }
                break;

            case R.id.share_imooc_view:
                shareImoocToFriend();
                break;
        }
    }

    private void shareImoocToFriend() {
        ShareDialog dialog = new ShareDialog(mContext,false);
        dialog.setShareType(Platform.SHARE_IMAGE);
        dialog.setShareTitle("慕课网");
        dialog.setShareTitleUrl("http://www.imooc.com");
        dialog.setShareText("慕课网");
        dialog.setShareSite("imooc");
        dialog.setShareSiteUrl("http://www.imooc.com");
        dialog.setImagePhoto(Environment.getExternalStorageDirectory() + "/test2.jpg");
        dialog.show();

    }

    /*
    * 跳转到登录页面
    * */
    private void toLogin() {
        Intent intent = new Intent(mContext,LoginActivity.class);
        mContext.startActivity(intent);
    }

    //发送版本检查请求
    private void checkVersion(){
        RequestCenter.checkVersion(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                final UpdateModel updateModel = (UpdateModel)responseObj;
                //判断本地服务器版本号与服务器返回的版本号大小
                if(Util.getVersionCode(mContext)<updateModel.data.currentVersion){
                    //说明有新版本
                    CommonDialog dialog = new CommonDialog(mContext, getString(R.string.update_new_version), getString(R.string.update_title), getString(R.string.update_install),
                            getString(R.string.cancel), new CommonDialog.DialogClickListener() {
                        @Override
                        public void onDialogClick() {
                            //安装事件回调处理   就是启动我们的更新服务
                            Intent intent = new Intent(mContext, UpdateService.class);
                            mContext.startService(intent);
                        }
                    });
                    dialog.show();
                }else{
                    //没有新版本
                }
            }

            @Override
            public void onFailure(Object reasonObj) {

            }
        });
    }

    /*
    * 自定义广播接收器，用来处理我们的登录广播
    * */
    private class LoginBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            //接收到广播  更新UI
            mLoginLayout.setVisibility(View.GONE);
            mLoginedLayout.setVisibility(View.VISIBLE);
            mUserNameView.setText(UserManager.getInstance().getUser().data.tick);
            mTickView.setText(UserManager.getInstance().getUser().data.tick);

        }
    }



}
