package com.shanshan.housekeeper.morePicture;

import android.widget.GridView;
import android.widget.ImageView;

import com.shanshan.housekeeper.R;
import com.wgl.mvp.view.AppDelegate;

/**
 * Created by wgl.
 */
public class VMorePicture extends AppDelegate{

    public GridView gridView;
    public ImageView iv2;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_moreicture;
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
        gridView = get(R.id.gridView);
        iv2 = get(R.id.iv2);
    }
}
