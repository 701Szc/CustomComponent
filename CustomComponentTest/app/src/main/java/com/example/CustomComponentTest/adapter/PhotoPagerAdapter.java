package com.example.CustomComponentTest.adapter;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.CustomComponentTest.util.ImageLoaderManager;

import java.util.ArrayList;

public class PhotoPagerAdapter extends PagerAdapter {

    private Context mContext;

    private boolean mIsMatch;
    private ArrayList<String> mData;
    private ImageLoaderManager mLoader;

    public PhotoPagerAdapter(Context context,ArrayList<String> list,boolean isMatch){
        mContext = context;
        mData = list;
        mIsMatch = isMatch;
        mLoader = ImageLoaderManager.getInstance(mContext);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView photoView = new ImageView(mContext);
        photoView.setScaleType(ImageView.ScaleType.FIT_XY);
//        if(mIsMatch){
//            photoView = new ImageView(mContext);
//            photoView.setScaleType(ImageView.ScaleType.FIT_XY);
//            photoView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    Intent intent = new Intent(mContext,
////                            CourseDetailActivity.class);
////                    mContext.startActivity(intent);
//                }
//            });
//        }else{
//            photoView = new PhotoView(mContext);
//        }
        mLoader.displayImage(photoView,mData.get(position));
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return photoView;
    }
}
