package com.shanshan.housekeeper.welcom;

import android.widget.ImageView;

import com.shanshan.housekeeper.R;
import com.wgl.mvp.view.AppDelegate;

/**
 * Created by wgl.
 */
public class SplashView extends AppDelegate {

    public ImageView ivSplash;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_splash;
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
        ivSplash = get(R.id.iv_splash);
    }
}
