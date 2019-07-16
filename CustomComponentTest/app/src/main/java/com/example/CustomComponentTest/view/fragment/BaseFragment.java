package com.example.CustomComponentTest.view.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import androidx.annotation.Nullable;


/*
* @function  为所有的fragment提供公共的行为或事假
* */


public class BaseFragment extends Fragment {

    protected Activity mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
