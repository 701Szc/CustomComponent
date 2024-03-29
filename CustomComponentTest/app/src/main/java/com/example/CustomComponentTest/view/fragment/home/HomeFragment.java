package com.example.CustomComponentTest.view.fragment.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.CustomComponentTest.R;
import com.example.CustomComponentTest.activity.PhotoViewActivity;
import com.example.CustomComponentTest.adapter.CourseAdapter;
import com.example.CustomComponentTest.constant.Constant;
import com.example.CustomComponentTest.module.recommand.BaseRecommandModel;
import com.example.CustomComponentTest.module.recommand.RecommandBodyValue;
import com.example.CustomComponentTest.network.http.RequestCenter;
import com.example.CustomComponentTest.view.fragment.BaseFragment;
import com.example.CustomComponentTest.view.home.HomeHeaderLayout;
import com.example.CustomComponentTest.zxing.app.CaptureActivity;
import com.example.mysdk.activity.AdBrowserActivity;
import com.example.mysdk.okhttp.listener.DisposeDataListener;

import static android.content.ContentValues.TAG;


public class HomeFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final int REQUEST_QRCODE = 0x01;

    /*
    * UI
    * */

    private View mContentView;
    private ListView mListView;
    private TextView mQRCodeView;
    private TextView mCategoryView;
    private TextView mSearchView;
    private ImageView mLoadingView;
    /*
    *  data
    * */
    private CourseAdapter mAdapter;
    private BaseRecommandModel mRecommandData;

    private static int i = 0;

    public HomeFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestRecommandData();
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mContentView = inflater.inflate(R.layout.fragment_home_layout,container,false);
        initView();
        return mContentView;
    }

    private void initView() {
        mQRCodeView = (TextView) mContentView.findViewById(R.id.qrcode_view);
        mQRCodeView.setOnClickListener(this);
        mCategoryView = (TextView)mContentView.findViewById(R.id.category_view);
        mCategoryView.setOnClickListener(this);
        mSearchView = (TextView)mContentView.findViewById(R.id.search_view);
        mSearchView.setOnClickListener(this);
        mListView = (ListView) mContentView.findViewById(R.id.list_view);
        mListView.setOnItemClickListener(this);
        mLoadingView = (ImageView)mContentView.findViewById(R.id.loading_view);
//        启动动画
        AnimationDrawable anim = (AnimationDrawable) mLoadingView.getDrawable();
        anim.start();

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.qrcode_view:
                if(hasPermission(Constant.HARDWEAR_CAMERA_PERMISSION)){
                    doOpenCamera();
                }else{
                    requestPermission(Constant.HARDWEAR_CAMERA_CODE,Constant.HARDWEAR_CAMERA_PERMISSION);
                }
                break;
        }

    }

    private void doOpenCamera() {
        Intent intent = new Intent(mContext, CaptureActivity.class);
        startActivityForResult(intent,REQUEST_QRCODE);
    }


    /*
    * 发送首页列表数据请求
    * */
    private void requestRecommandData() {

        RequestCenter.requestRecommandData(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
//                完成真正的功能逻辑
                Log.e(TAG, "onSuccess: "+responseObj.toString() );
                /*
                * 获取数据更新UI
                * */
                mRecommandData = (BaseRecommandModel)responseObj;
                showSuccessView();
            }

            @Override
            public void onFailure(Object reasonObj) {
//                提示用户网络有问题
                Log.e(TAG, "onFailure: "+ reasonObj.toString()  );
            }
        });

    }
/*
*   请求成功执行的方法
* */
    private void showSuccessView() {

//        判断数据是否为空
        if(mRecommandData != null && mRecommandData.data.list.size()>0){
            mLoadingView.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
//            为listView添加列表头
            mListView.addHeaderView(new HomeHeaderLayout(mContext, mRecommandData.data.head));
////             设置内容
            mAdapter = new CourseAdapter(mContext,mRecommandData.data.list);
            mListView.setAdapter(mAdapter);
            mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    mAdapter.updateAdInScrollView();
                }
            });
        }else{
            showErrorView();
        }
    }
    /*
    * 请求失败执行的方法
    * */
    private void showErrorView() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            //扫码处理逻辑
            case REQUEST_QRCODE:
                if(resultCode == Activity.RESULT_OK){
                    String code = data.getStringExtra("SCAN_RESULT");
                        if(code.contains("http") || code.contains("https")){
                            Intent intent = new Intent(mContext, AdBrowserActivity.class);
                            intent.putExtra(AdBrowserActivity.KEY_URL, code);
                            startActivity(intent);
                        }else{
                            Toast.makeText(mContext,code,Toast.LENGTH_LONG).show();
                        }
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        RecommandBodyValue value = (RecommandBodyValue) mAdapter.getItem(i - mListView.getHeaderViewsCount());
        if (value.type != 0) {
            Intent intent = new Intent(mContext, PhotoViewActivity.class);
            intent.putStringArrayListExtra(PhotoViewActivity.PHOTO_LIST, value.url);
            startActivity(intent);
        }
    }
}
