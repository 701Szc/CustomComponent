package com.example.CustomComponentTest.view.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.CustomComponentTest.R;
import com.example.CustomComponentTest.view.fragment.BaseFragment;
/*
* @function: 个人信息Fragment
* */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private View mContentView;

    public MineFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_mine_layout,null,false);
        return mContentView;

    }

    @Override
    public void onClick(View v) {

    }
}
