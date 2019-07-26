package com.example.CustomComponentTest.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.example.CustomComponentTest.R;
import com.example.CustomComponentTest.module.recommand.RecommandBodyValue;
import com.example.mysdk.adutil.Utils;
import com.example.mysdk.imageloader.ImageLoaderManager;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import com.example.CustomComponentTest.util.Util;


public class CourseAdapter extends BaseAdapter {

    /*
    * ListView不同类型的Item标识
    * */

    private static final int CARD_COUNT = 4;//
    private static final int VIDOE_TYPE = 0x00;//
    private static final int CARD_SIGNAL_PIC = 0x01;//单一图片类型
    private static final int CARD_MULTI_PIC = 0x02;//多图片类型
    private static final int CARD_VIEW_PAGER = 0x03;//page类型


    private Context mContext;
    private ViewHolder mViewHolder;
    private LayoutInflater mInflate;

    private ArrayList<RecommandBodyValue> mData;
//    异步图片加载工具
    private ImageLoaderManager mImagerLoader;

    /**
     * 构造方法
     *
     * @param context context
     * @param data    data
     */
    public CourseAdapter(Context context,ArrayList<RecommandBodyValue> data){
        mContext = context;
        mData = data;
        mInflate = LayoutInflater.from(mContext);
        mImagerLoader = ImageLoaderManager.getInstance(mContext);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //1.获取数据的type类型
        int type = getItemViewType(position);
        final RecommandBodyValue value = (RecommandBodyValue)getItem(position);
        Log.e("测试type", "type===="+type );
//        为空表明当前没有使用的缓存View
        if(convertView == null){
            switch (type){
//                一张图片
                case CARD_SIGNAL_PIC:
                    mViewHolder = new ViewHolder();
                    convertView = mInflate.inflate(R.layout.item_product_card_one_layout, parent, false);
                    mViewHolder.mLogoView = (CircleImageView) convertView.findViewById(R.id.item_logo_view);
                    mViewHolder.mTitleView = (TextView) convertView.findViewById(R.id.item_title_view);
                    mViewHolder.mInfoView = (TextView) convertView.findViewById(R.id.item_info_view);
                    mViewHolder.mFooterView = (TextView) convertView.findViewById(R.id.item_footer_view);
                    mViewHolder.mPriceView = (TextView) convertView.findViewById(R.id.item_price_view);
                    mViewHolder.mFromView = (TextView) convertView.findViewById(R.id.item_from_view);
                    mViewHolder.mZanView = (TextView) convertView.findViewById(R.id.item_zan_view);
                    mViewHolder.mProductLayout = (LinearLayout) convertView.findViewById(R.id.product_photo_layout);
                    break;
//               多张图片
                case CARD_MULTI_PIC:
                    mViewHolder = new ViewHolder();
                    convertView = mInflate.inflate(R.layout.item_product_card_two_layout, parent, false);
//                    初始化多图
                    mViewHolder.mLogoView = (CircleImageView) convertView.findViewById(R.id.item_logo_view);
                    mViewHolder.mTitleView = (TextView) convertView.findViewById(R.id.item_title_view);
                    mViewHolder.mInfoView = (TextView) convertView.findViewById(R.id.item_info_view);
                    mViewHolder.mFooterView = (TextView) convertView.findViewById(R.id.item_footer_view);
                    mViewHolder.mProductView = (ImageView) convertView.findViewById(R.id.product_photo_view);
                    mViewHolder.mPriceView = (TextView) convertView.findViewById(R.id.item_price_view);
                    mViewHolder.mFromView = (TextView) convertView.findViewById(R.id.item_from_view);
                    mViewHolder.mZanView = (TextView) convertView.findViewById(R.id.item_zan_view);
                    break;
//              ViewPage类型
                case CARD_VIEW_PAGER:
                    mViewHolder = new ViewHolder();
                    convertView = mInflate.inflate(R.layout.item_product_card_three_layout,parent,false);
                    mViewHolder.mViewPager = (ViewPager)convertView.findViewById(R.id.pager);
                    mViewHolder.mViewPager.setPageMargin(Utils.dip2px(mContext,12));
//                    填充ViewPage数据
                    ArrayList<RecommandBodyValue> recommandList = Util.handleData(value);
                    mViewHolder.mViewPager.setAdapter(new HotSalePagerAdapter(mContext,recommandList));
//                    实现左右两边无限滑动  当前处于500位置  所以取余可以无限滑动
//                    一开始就让我们的ViewPager处于一个比较靠中间的Item
                    mViewHolder.mViewPager.setCurrentItem(recommandList.size()*100);
            }
            convertView.setTag(mViewHolder);
        }
//        有可用的ConvertView
        else{
            mViewHolder = (ViewHolder)convertView.getTag();

//
        }
//      开始绑定数据
        switch(type){
            case CARD_SIGNAL_PIC:
                /*
                 * 为ImageView完成图片的加载
                 * */
                mImagerLoader.displayImage(mViewHolder.mLogoView, value.logo);
                mViewHolder.mTitleView.setText(value.title);
                mViewHolder.mInfoView.setText(value.info.concat(mContext.getString(R.string.tian_qian)));
                mViewHolder.mFooterView.setText(value.text);
                mViewHolder.mPriceView.setText(value.price);
                mViewHolder.mFromView.setText(value.from);
                mViewHolder.mZanView.setText(mContext.getString(R.string.dian_zan).concat(value.zan));
                mViewHolder.mProductLayout.removeAllViews();
                //动态添加多个imageview
                for (String url : value.url) {
                    mViewHolder.mProductLayout.addView(createImageView(url));
                }
                break;

            case CARD_MULTI_PIC:
                mImagerLoader.displayImage(mViewHolder.mLogoView, value.logo);
                mViewHolder.mTitleView.setText(value.title);
                mViewHolder.mInfoView.setText(value.info.concat(mContext.getString(R.string.tian_qian)));
                mViewHolder.mFooterView.setText(value.text);
                mViewHolder.mPriceView.setText(value.price);
                mViewHolder.mFromView.setText(value.from);
                mViewHolder.mZanView.setText(mContext.getString(R.string.dian_zan).concat(value.zan));
                //为单个ImageView加载远程图片
                mImagerLoader.displayImage(mViewHolder.mProductView, value.url.get(0));
                break;

            case CARD_VIEW_PAGER:
                break;

        }

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return CARD_COUNT;
    }
    /*
    * 通知Adapter使用哪种类型的Item去加载数据
    * @param position
    * @return
    * */
    @Override
    public int getItemViewType(int position) {
        RecommandBodyValue value = (RecommandBodyValue) getItem(position);
        return value.type;
    }


    /*
    * 缓存我们已经创建号的item
    *
    * */
    private static  class ViewHolder {
    //所有Card共有属性
    private CircleImageView mLogoView;
    private TextView mTitleView;
    private TextView mInfoView;
    private TextView mFooterView;
    //Video Card 特有属性
    private RelativeLayout mVideoContentLayout;
    private ImageView mShareView;

    //Video Card外所有Card具有属性
    private TextView mPriceView;
    private TextView mFromView;
    private TextView mZanView;
    //Card One特有属性
    private LinearLayout mProductLayout;
    //Card Two特有属性
    private ImageView mProductView;
    //Caed Three特有属性
    private ViewPager mViewPager;

    }
/*
* 动态的创建我们的ImageView
* */
    private ImageView createImageView(String url){
        ImageView imageView = new ImageView(mContext);
//        与要添加到的ViewGroup要保持一致
//       设置linearLayout  宽MATCH_PARENT   高 100dp
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Utils.dip2px(mContext,100),LinearLayout.LayoutParams.MATCH_PARENT);
//        间距5dp
        params.leftMargin = Utils.dip2px(mContext,5);
        imageView.setLayoutParams(params);
        mImagerLoader.displayImage(imageView,url);
        return imageView;
    }
}
