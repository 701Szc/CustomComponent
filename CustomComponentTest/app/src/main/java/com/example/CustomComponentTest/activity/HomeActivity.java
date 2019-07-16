package com.example.CustomComponentTest.activity;

/*
* @function
*   1.创建首页所有的fragment，以及fragmen
* */

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.CustomComponentTest.R;
import com.example.CustomComponentTest.activity.base.BaseActivity;
import com.example.CustomComponentTest.view.fragment.home.HomeFragment;
import com.example.CustomComponentTest.view.fragment.home.MessageFragment;
import com.example.CustomComponentTest.view.fragment.home.MineFragment;


/**
 * The type Home activity.
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {

    /*
    * fragment相关
    * */
    private FragmentManager fm;
    private HomeFragment mHomeFragment;
    private MessageFragment mMessageFragment;
    private MineFragment mMineFragment;
    private Fragment mCurrent;

    /*
    * UI
    * */

    private RelativeLayout mHomeLayout;
    private RelativeLayout mPondLayout;
    private RelativeLayout mMessageLayout;
    private RelativeLayout mMineLayout;
    private TextView mHomeView;
    private TextView mPondView;
    private TextView mMessageView;
    private TextView mMineView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_layout);
//        初始化页面所有控件
        initView();
//        添加默认要显示的fragment
        mHomeFragment = new HomeFragment();
//        获取fragmentManager
        fm = getFragmentManager();
//        开始事务
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.content_layout,mHomeFragment);//登录界面直接就选中了homeFragment

//        提交事务
        fragmentTransaction.commit();


    }

    private void initView() {
        mHomeLayout = (RelativeLayout)findViewById(R.id.home_layout_view);
        mHomeLayout.setOnClickListener(this);
        mPondLayout = (RelativeLayout)findViewById(R.id.pond_layout_view);
        mPondLayout.setOnClickListener(this);
        mMessageLayout = (RelativeLayout)findViewById(R.id.message_layout_view);
        mMessageLayout.setOnClickListener(this);
        mMineLayout = (RelativeLayout)findViewById(R.id.mine_layout_view);
        mMineLayout.setOnClickListener(this);

        mHomeView = (TextView)findViewById(R.id.home_image_view);
        mPondView = (TextView)findViewById(R.id.fish_image_view);
        mMessageView = (TextView)findViewById(R.id.message_image_view);
        mMineView = (TextView)findViewById(R.id.mine_image_view);
//        设置点中时候的背景图片
        mHomeView.setBackgroundResource(R.drawable.comui_tab_home_selected);

    }

    /*
     *  用来隐藏具体的fragment
     *  @param fragment
     *  @param ft
     * */
    private void hideFragment(Fragment fragment, FragmentTransaction ft){
        if(fragment != null){
            ft.hide(fragment);
        }
    }






    @Override
    public void onClick(View v) {
        //开始事务
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        switch(v.getId()){
            case R.id.home_layout_view:
                mHomeView.setBackgroundResource(R.drawable.comui_tab_home_selected);
                mPondView.setBackgroundResource(R.drawable.comui_tab_pond);
                mMessageView.setBackgroundResource(R.drawable.comui_tab_message);
                mMineView.setBackgroundResource(R.drawable.comui_tab_person);
//                隐藏其他两个fragment
                hideFragment(mMessageFragment,fragmentTransaction);
                hideFragment(mMineFragment,fragmentTransaction);
//                将我们的HomeFragment显示出来
                if(null == mHomeFragment){
                    mHomeFragment = new HomeFragment();
                    fragmentTransaction.add(R.id.content_layout,mHomeFragment);
                }else{
//                    已经创建过了
                    fragmentTransaction.show(mHomeFragment);
                }
                break;

            case R.id.message_layout_view:
                mHomeView.setBackgroundResource(R.drawable.comui_tab_home);
                mPondView.setBackgroundResource(R.drawable.comui_tab_pond);
                mMessageView.setBackgroundResource(R.drawable.comui_tab_message_selected);
                mMineView.setBackgroundResource(R.drawable.comui_tab_person);
                //                隐藏其他两个fragment
                hideFragment(mHomeFragment,fragmentTransaction);
                hideFragment(mMineFragment,fragmentTransaction);
//                将我们的HomeFragment显示出来
                if(null == mMessageFragment){
                    mMessageFragment = new MessageFragment();
                    fragmentTransaction.add(R.id.content_layout,mMessageFragment);
                }else{
//                    已经创建过了
                    fragmentTransaction.show(mMessageFragment);
                }
                break;



            case R.id.mine_layout_view:

                mMineView.setBackgroundResource(R.drawable.comui_tab_person_selected);
                mHomeView.setBackgroundResource(R.drawable.comui_tab_home);
                mPondView.setBackgroundResource(R.drawable.comui_tab_pond);
                mMessageView.setBackgroundResource(R.drawable.comui_tab_message);
                hideFragment(mMessageFragment, fragmentTransaction);
                hideFragment(mHomeFragment, fragmentTransaction);
                if (mMineFragment == null) {
                    mMineFragment = new MineFragment();
                    fragmentTransaction.add(R.id.content_layout, mMineFragment);
                } else {
                    mCurrent = mMineFragment;
                    fragmentTransaction.show(mMineFragment);
                }
                break;
        }
        fragmentTransaction.commit();//提交界面图标改变

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
