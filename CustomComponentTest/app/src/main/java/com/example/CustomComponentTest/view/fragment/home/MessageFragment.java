package com.example.CustomComponentTest.view.fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.CustomComponentTest.R;
import com.example.CustomComponentTest.view.fragment.BaseFragment;

public class MessageFragment extends BaseFragment implements View.OnClickListener {

    private View mContentView;


    public void MessageFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_message_layout,null,false);
        return mContentView;
    }

    @Override
    public void onClick(View v) {

    }
}
