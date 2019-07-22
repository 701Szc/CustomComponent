package com.example.CustomComponentTest.view.fragment.home;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.CustomComponentTest.R;
import com.example.CustomComponentTest.module.recommand.BaseRecommandModel;
import com.example.CustomComponentTest.network.http.RequestCenter;
import com.example.CustomComponentTest.view.fragment.BaseFragment;
import com.example.mysdk.okhttp.CommonOkHttpClient;
import com.example.mysdk.okhttp.listener.DisposeDataListener;

import org.w3c.dom.Text;

import static android.content.ContentValues.TAG;

public class HomeFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    /*
    * UI
    * */

    private View mContentView;
    private ListView mListView;
    private TextView mCategoryView;
    private TextView mSearchView;
    private ImageView mLoadingView;
    /*
    *  data
    * */
//    private CourseAdapter mAdapter;
    private BaseRecommandModel mRecommandData;

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
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
    /*
    * 发送首页列表数据请求
    * */
    private void requestRecommandData() {
        RequestCenter.requestRecommandData(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
//                完成真正的功能逻辑
                Log.e(TAG, responseObj.toString());
            }

            @Override
            public void onFailure(Object reasonObj) {
//                提示用户网络有问题
                Log.e(TAG, "onFailure: "+ reasonObj.toString()  );
            }
        });

    }
}
