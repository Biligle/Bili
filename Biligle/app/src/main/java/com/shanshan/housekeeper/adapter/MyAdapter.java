package com.shanshan.housekeeper.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shanshan.housekeeper.Help.base.MyApplication;
import com.shanshan.housekeeper.Help.utils.LogCatUtil;
import com.shanshan.housekeeper.picture.MPicture;

/**
 * Created by Administrator on 2016/6/12.
 */
public class MyAdapter extends PagerAdapter {

    private MPicture baseModle;
    private Context context;

    public MyAdapter(Context context, MPicture baseModle){
        this.baseModle = baseModle;
        this.context = context;
    }

    @Override
    public int getCount() {
        return baseModle.indexPicture.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 负责销毁item对象
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    /**
     * 添加图片
     */
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView iv = new ImageView(context);
        // 拉伸图片
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
//        LogCatUtil.log(baseModle.indexPicture.get(position).interimPathUrl);
        try {
            /**
             * 显示图片 参数1：图片url 参数2：显示图片的控件 参数3：显示图片的设置 参数4：监听器
             */
            ImageLoader.getInstance().displayImage(
                    baseModle.indexPicture.get(position).interimPathUrl, iv,
                    MyApplication.options, MyApplication.animateFirstListener);
        } catch (Exception e) {
            e.printStackTrace();
        }

        container.addView(iv);

        return iv;
    }
}
