package com.example.CustomComponentTest.view.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.CustomComponentTest.constant.Constant;


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

    private void doOpenCamera() {

    }
    public void doWriteSDCard() {

    }

    /*
    * 是否拥有指定权限
    * */

    public boolean hasPermission(String... permissions){
        for(String permission: permissions){
            if(ContextCompat.checkSelfPermission(getActivity(),permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }
    /*
    * 申请权限
    * */
    public void requestPermission(int code,String... permissions){
        if(Build.VERSION.SDK_INT >= 23){
            requestPermissions(permissions,code);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Constant.HARDWEAR_CAMERA_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doOpenCamera();
                }
                break;
            case Constant.WRITE_READ_EXTERNAL_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doWriteSDCard();
                }
                break;
        }
    }

}

