package com.example.CustomComponentTest.activity;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.CustomComponentTest.R;
import com.example.CustomComponentTest.activity.base.BaseActivity;
import com.example.CustomComponentTest.manager.DialogManager;
import com.example.CustomComponentTest.manager.UserManager;
import com.example.CustomComponentTest.module.user.User;
import com.example.CustomComponentTest.module.user.UserContent;
import com.example.CustomComponentTest.network.http.RequestCenter;
import com.example.CustomComponentTest.view.associatemail.MailBoxAssociateView;
import com.example.mysdk.okhttp.listener.DisposeDataListener;

import org.w3c.dom.Text;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    //自定义登陆广播Action
    public static final String LOGIN_ACTION = "com.imooc.action.LOGIN_ACTION";
    /**
     * UI
     */
    private EditText mUserNameAssociateView;
    private EditText mPasswordView;
    private TextView mLoginView;
    private ImageView mQQLoginView; //用来实现QQ登陆


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        initView();
    }



    private void initView() {

        mPasswordView = (EditText) findViewById(R.id.login_input_password);
        mLoginView = (TextView) findViewById(R.id.login_button);
        mLoginView.setOnClickListener(this);

        mUserNameAssociateView = (EditText)findViewById(R.id.associate_email_input);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.login_button:
                login();
                break;
        }
    }
    //发送登录请求
    private void login() {
        String userName = mUserNameAssociateView.getText().toString().trim();
        String pwd = mPasswordView.getText().toString().toString().trim();
        if(TextUtils.isEmpty(userName)){
            return;
        }
        if(TextUtils.isEmpty(pwd)){
            return;
        }
        DialogManager.getInstnce().showProgressDialog(this);

        //发送登录请求
        RequestCenter.login(userName, pwd, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {

                DialogManager.getInstnce().dismissProgressDialog();

                //拿到user对象
                User user = (User)responseObj;
                UserManager.getInstance().setUser(user);//通过UserManager来管理用户信息
                //发送我们的登录广播
                sendLoginBroadcast();
                //销毁当前页面  返回我的
                finish();
            }

            @Override
            public void onFailure(Object reasonObj) {
                Log.e(TAG, "onFailure: +++++++");
            }
        });
    }
    //发送我们的登录广播
    private void sendLoginBroadcast(){
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(LOGIN_ACTION));
    }

}
