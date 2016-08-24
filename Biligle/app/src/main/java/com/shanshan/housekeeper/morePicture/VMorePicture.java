package com.shanshan.housekeeper.morePicture;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.shanshan.housekeeper.R;
import com.wgl.mvp.view.AppDelegate;

/**
 * Created by wgl.
 */
public class VMorePicture extends AppDelegate{

    public GridView gridView;
    public ListView listView;
    public PopupWindow popupWindow;
    public View view;
    public Button btPic;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_moreicture;
    }

    @Override
    public int getBrotherLayoutId() {
        return R.layout.dialog_picture;
    }

    @Override
    public int getBrotherLayoutId2() {
        return 0;
    }

    @Override
    public void initWidget() {
        gridView = get(R.id.gridView);
        listView = getBrother(R.id.listView);
        view = getBrotherView();
        btPic = get(R.id.btPic);
        popupWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
