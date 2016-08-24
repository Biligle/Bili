package com.shanshan.housekeeper.morePicture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.PopupWindow;

import com.shanshan.housekeeper.Help.utils.CommonUtil;
import com.shanshan.housekeeper.Help.utils.LogCatUtil;
import com.shanshan.housekeeper.R;
import com.shanshan.housekeeper.adapter.GrideViewAdapter;
import com.shanshan.housekeeper.adapter.ListViewAdapter;
import com.wgl.mvp.presenter.ActivityPresenter;
import com.wgl.mvp.selectPicture.GetPicture;
import com.wgl.mvp.selectPicture.ISelect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wgl on 2016/8/15.
 */
public class PMorePictureActivity extends ActivityPresenter<VMorePicture> implements ISelect{

    private ArrayList<String> data2 = null;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    if(null != msg.getData()){
                        ArrayList<String> data = msg.getData().getStringArrayList("data");
                        data2 = msg.getData().getStringArrayList("data2");
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
    protected void setListener() {
        super.setListener();
        baseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btPic:
                        initPopuWindow(PMorePictureActivity.this,baseView.popupWindow,baseView.view,3);
                        baseView.listView.setAdapter(new ListViewAdapter(PMorePictureActivity.this,data2));
                        baseView.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(PMorePictureActivity.this,PPageActivity.class);
                                ArrayList<String> picture = GetPicture.getFilePic(data2.get(position));
                                intent.putStringArrayListExtra("picture",picture);
                                startActivity(intent);
                            }
                        });
                        break;
                }
            }
        }, R.id.btPic);
    }

    @Override
    public void getPicture(ArrayList<String> pictureList,ArrayList<String> firstPictureList) {
        if(pictureList.size() > 0){
//            LogCatUtil.log("size:"+pictureList.size());
            Message msg = new Message();
            msg.what = 1;
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("data",pictureList);
            bundle.putStringArrayList("data2",firstPictureList);
            msg.setData(bundle);
            handler.sendMessage(msg);
        }
    }

    /**
     * 弹出自定义PopupWindow
     *
     * @param popupWindow : PopupWindow popuppWindow = new PopupWinsow();
     */
    public void initPopuWindow(final Context context,
                               PopupWindow popupWindow, View view1, int n) {
        if (popupWindow == null) {
            popupWindow = new PopupWindow(view1,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        }

        ColorDrawable cd = new ColorDrawable(0x000000);
//        popupWindow.setBackgroundDrawable(baseView.drawable);
        popupWindow.setBackgroundDrawable(cd);
        // 产生背景变暗效果
        WindowManager.LayoutParams lp = ((Activity) context).getWindow()
                .getAttributes();
        lp.alpha = 0.4f;// 透明度(0.0-1.0)
        ((Activity) context).getWindow().setAttributes(lp);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        // 居中显示
        if (n == 1) {
            popupWindow.showAtLocation(view1, Gravity.CENTER
                    | Gravity.CENTER_VERTICAL, 0, 0);
        }
        // 底部显示
        if (n == 2) {
            popupWindow.showAtLocation(view1, Gravity.BOTTOM, 0, 0);
        }
        if (n == 3) {
            popupWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);
            int margin = CommonUtil.getAttribute(PMorePictureActivity.this).height-baseView.btPic.getHeight()-popupWindow.getContentView().getMeasuredHeight();
            popupWindow.showAtLocation(view1,
                    Gravity.NO_GRAVITY,
                    10, margin);
        }

        popupWindow.update();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = ((Activity) context)
                        .getWindow().getAttributes();
                lp.alpha = 1f;
                ((Activity) context).getWindow().setAttributes(lp);
            }
        });
    }
}
