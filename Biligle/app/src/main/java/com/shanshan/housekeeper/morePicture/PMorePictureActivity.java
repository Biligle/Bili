package com.shanshan.housekeeper.morePicture;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

import com.shanshan.housekeeper.Help.utils.LogCatUtil;
import com.shanshan.housekeeper.adapter.GrideViewAdapter;
import com.wgl.mvp.presenter.ActivityPresenter;
import com.wgl.mvp.selectPicture.GetPicture;
import com.wgl.mvp.selectPicture.ISelect;

import java.util.ArrayList;

/**
 * Created by wgl on 2016/8/15.
 */
public class PMorePictureActivity extends ActivityPresenter<VMorePicture> implements ISelect{

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    if(null != msg.getData()){
                        ArrayList<String> data = msg.getData().getStringArrayList("data");
                        baseView.gridView.setAdapter(new GrideViewAdapter(PMorePictureActivity.this,data));
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    protected Class<VMorePicture> getDelegateClass() {
        return VMorePicture.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        new GetPicture(this,this);
    }

    @Override
    protected void setNormal() {
        super.setNormal();
    }

    @Override
    public void getPicture(ArrayList<String> pictureList) {
        if(pictureList.size() > 0){
            Message msg = new Message();
            msg.what = 1;
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("data",pictureList);
            msg.setData(bundle);
            handler.sendMessage(msg);
        }
    }

}
