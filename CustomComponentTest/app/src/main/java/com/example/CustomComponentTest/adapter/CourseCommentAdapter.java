package com.example.CustomComponentTest.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.CustomComponentTest.R;
import com.example.CustomComponentTest.module.course.CourseCommentValue;
import com.example.CustomComponentTest.util.ImageLoaderManager;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CourseCommentAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    private ArrayList<CourseCommentValue> mData;
    private ViewHolder mViewHolder;
    private ImageLoaderManager mImageLoader;

    public CourseCommentAdapter(Context context, ArrayList<CourseCommentValue> data){
        mContext = context;
        mData = data;
        mInflater = LayoutInflater.from(mContext);
        mImageLoader = ImageLoaderManager.getInstance(mContext);

    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CourseCommentValue value = (CourseCommentValue) getItem(i);
        if(view == null){
            mViewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.item_comment_layout,viewGroup,false);
            mViewHolder.mImageView = (CircleImageView) view.findViewById(R.id.photo_view);
            mViewHolder.mNameView = (TextView) view.findViewById(R.id.name_view);
            mViewHolder.mCommentView = (TextView) view.findViewById(R.id.text_view);
            mViewHolder.mOwnerView = (TextView) view.findViewById(R.id.owner_view);
            view.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder)view.getTag();
        }
        //开始填充数据
        if(value.type == 0){
            mViewHolder.mOwnerView.setVisibility(View.VISIBLE);
        }else{
            mViewHolder.mOwnerView.setVisibility(View.GONE);
        }
        mViewHolder.mNameView.setText(value.name);
        mViewHolder.mCommentView.setText(value.text);
        mImageLoader.displayImage(mViewHolder.mImageView,value.logo);
        return view;
    }
    private static class ViewHolder {

        CircleImageView mImageView;
        TextView mNameView;
        TextView mCommentView;
        TextView mOwnerView;
    }
    public void addComment(CourseCommentValue value){
        mData.add(0,value);
        notifyDataSetChanged();
    }
    public int getCommentCount(){
        return mData.size();
    }

}
