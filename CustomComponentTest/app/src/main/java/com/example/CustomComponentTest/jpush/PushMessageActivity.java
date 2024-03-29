package com.example.CustomComponentTest.jpush;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.CustomComponentTest.R;
import com.example.CustomComponentTest.activity.base.BaseActivity;
import com.example.CustomComponentTest.module.PushMessage;
import com.example.mysdk.activity.AdBrowserActivity;

public class PushMessageActivity extends BaseActivity {
    /*
    * UI
    * */
    private TextView mTypeView;
    private TextView mTypeValueView;
    private TextView mContentView;
    private TextView mContentValueView;

    /*
    * data
    * */
    private PushMessage mPushMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jpush_layout);
        initData();
        initView();
    }

    private void initView() {
        mTypeView = (TextView)findViewById(R.id.message_type_view);
        mTypeValueView = (TextView)findViewById(R.id.message_type_value_view);
        mContentView = (TextView)findViewById(R.id.message_content_view);
        mContentValueView = (TextView)findViewById(R.id.message_content_value_view);

        mTypeValueView.setText(mPushMessage.messageType);
        mContentValueView.setText(mPushMessage.messageContent);
        if(!TextUtils.isEmpty(mPushMessage.messageUrl)){
            gotoWebView();
        }

    }

    private void gotoWebView() {
        Intent intent = new Intent(this, AdBrowserActivity.class);
        intent.putExtra(AdBrowserActivity.KEY_URL,mPushMessage.messageUrl);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initData() {
        Intent intent = getIntent();
        mPushMessage = (PushMessage)intent.getSerializableExtra("pushMessage");
    }

}
