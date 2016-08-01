package com.shanshan.housekeeper.picture;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.shanshan.housekeeper.Help.base.BaseActivity;
import com.shanshan.housekeeper.Help.utils.CommonUtil;
import com.shanshan.housekeeper.Help.utils.MyToastView;
import com.shanshan.housekeeper.adapter.MyAdapter;
import com.shanshan.housekeeper.interfaces.IResponse;
import com.wgl.mvp.model.IModel;

/**
 * Created by wgl.
 */
public class PPictureActivity extends BaseActivity<VPicture> implements IResponse {

    private LinearLayout.LayoutParams params;
    private int width;
    private MPicture mModle;

    @Override
    protected Class<VPicture> getDelegateClass() {
        return VPicture.class;
    }

    @Override
    protected void toNet() {
        super.toNet();
        NetPicture manager = new NetPicture(this,this);
        manager.tonetPicture(getIPublic(true),true,"");
    }

    @Override
    public void onSuccess(IModel modle) {
        //数据处理,需要一个接口或者基类
        MyToastView.showToast("图片成功",this);
        mModle = (MPicture) modle;
        setAdapter();
    }

    @Override
    public void onFailure(String error) {
//        MyToastView.showToast(error,this);
    }

    private void setAdapter(){
        width = CommonUtil.getAttribute(this).width/mModle.indexPicture.size();
        params = new LinearLayout.LayoutParams(width,15);
        MyAdapter adapter = new MyAdapter(this,mModle);
        baseView.vp.setAdapter(adapter);
        baseView.vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(baseView.layout.getChildAt(0) != null){
                    baseView.layout.removeAllViews();
                }
                params.leftMargin = width*position+positionOffsetPixels/5;
                View line = new View(PPictureActivity.this);
                line.setLayoutParams(params);
                line.setBackgroundColor(Color.parseColor("#1E9CD7"));//#ff7200
                baseView.layout.addView(line);
            }

            @Override
            public void onPageSelected(int position) {
                if(baseView.layout.getChildAt(0) != null){
                    baseView.layout.removeAllViews();
                }
                params.leftMargin = width*position;
                View line = new View(PPictureActivity.this);
                line.setLayoutParams(params);
                line.setBackgroundColor(Color.parseColor("#1E9CD7"));//#ff7200
                baseView.layout.addView(line);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
