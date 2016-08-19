package com.shanshan.housekeeper.morePicture;

import android.util.Log;

import com.shanshan.housekeeper.adapter.GrideViewAdapter;
import com.wgl.mvp.presenter.ActivityPresenter;

import java.util.ArrayList;

/**
 * Created by wgl.
 */
public class PPageActivity extends ActivityPresenter<VMorePicture>{
    @Override
    protected Class<VMorePicture> getDelegateClass() {
        return VMorePicture.class;
    }

    @Override
    protected void setNormal() {
        super.setNormal();
        ArrayList<String> picture = getIntent().getExtras().getStringArrayList("picture");
        baseView.gridView.setAdapter(new GrideViewAdapter(this,picture));
    }
}
