package com.example.mysdk.imageloader;

import android.graphics.Bitmap;
import android.view.View;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ImageLoaderTest {
//    private void testApi(){
//        /*
//        * 先来获取实例:ImageLoader
//        * 单例
//        * */
//
//        ImageLoader imageLoader = ImageLoader.getInstance();
////      配置imageLoader   下载 获取图片时候的配置
//        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration
//                                                    .Builder(context).build();
//        /*
//        * 显示图片的时候去配置
//        * */
//        DisplayImageOptions options = new DisplayImageOptions.Builder().build();
//
//
//
//
//        /*
//        * 使用displayImage去加载图片
//        * */
//        imageLoader.displayImage("url",imageView,options,new SimpleImageLoadingListener(){
//            @Override
//            public void onLoadingCancelled(String imageUri, View view) {
//                super.onLoadingCancelled(imageUri, view);
//            }
//
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                super.onLoadingComplete(imageUri, view, loadedImage);
//            }
//
//            @Override
//            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//                super.onLoadingFailed(imageUri, view, failReason);
//            }
//
//            @Override
//            public void onLoadingStarted(String imageUri, View view) {
//                super.onLoadingStarted(imageUri, view);
//            }
//        });
//
//        ImageLoaderManager.getInstance(context).displayImage();
//
//    }

}
