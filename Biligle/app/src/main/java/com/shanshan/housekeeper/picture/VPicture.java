package com.shanshan.housekeeper.picture;

import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.shanshan.housekeeper.R;
import com.wgl.mvp.view.AppDelegate;

/**
 * Created by wgl.
 */
public class VPicture extends AppDelegate{

    public ViewPager vp;
    public LinearLayout layout;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_picture;
    }

    @Override
    public int getBrotherLayoutId() {
        return 0;
    }

    @Override
    public int getBrotherLayoutId2() {
        return 0;
    }

    @Override
    public void initWidget() {
        vp = get(R.id.vp);
        layout = get(R.id.indicator);
    }
}
