package com.example.CustomComponentTest.view.fragment.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;



import com.example.CustomComponentTest.R;
import com.example.CustomComponentTest.module.recommand.RecommandHeadValue;
import com.example.CustomComponentTest.util.ImageLoaderManager;
import com.example.CustomComponentTest.view.viewpagerindictor.CirclePageIndicator;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;


public class HomeHeaderLayout extends RelativeLayout {

    private Context mContext;

    /*
    * UI
    * */

    private RelativeLayout mRootView;
    private AutoScrollViewPager mViewPager;
    private CirclePageIndicator mPageIndictor;
    private TextView mHotView;
//    private PhotoPagerAdapter mAdapter;
    private ImageView[] mImageViews = new ImageView[4];
    private LinearLayout mFootLayout;

    /*
    * Data
    * */

    private RecommandHeadValue mHeaderValue;
    private ImageLoaderManager mImageLoader;

    public HomeHeaderLayout(Context context,RecommandHeadValue headerValue) {
        this(context, null, headerValue);

    }

    public HomeHeaderLayout(Context context, AttributeSet attrs,RecommandHeadValue headerValue){
        super(context, attrs);
        mContext = context;
        mHeaderValue = headerValue;
        mImageLoader = ImageLoaderManager.getInstance(mContext);
        initView();

    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mRootView = (RelativeLayout) inflater.inflate(R.layout.listview_home_head_layout,this);
        mViewPager = (AutoScrollViewPager) mRootView.findViewById(R.id.pager);


    }
}
