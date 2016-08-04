package com.shanshan.housekeeper.login;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;
import com.shanshan.housekeeper.Help.utils.CommonUtil;
import com.shanshan.housekeeper.Help.utils.LogCatUtil;
import com.shanshan.housekeeper.Help.utils.MyToastView;
import com.shanshan.housekeeper.Help.utils.OtherCommon;
import com.shanshan.housekeeper.picture.PPictureActivity;
import com.shanshan.housekeeper.R;
import com.shanshan.housekeeper.interfaces.IResponse;
import com.wgl.mvp.activityResult.ActivityResult;
import com.wgl.mvp.call.Call;
import com.wgl.mvp.headerPicture.HeaderPicture;
import com.wgl.mvp.message.GetMessageUtil;
import com.wgl.mvp.message.IGetMessage;
import com.wgl.mvp.message.SendMesssage;
import com.wgl.mvp.model.IModel;
import com.wgl.mvp.requestPermission.RequestPermissionUtil;
import com.wgl.mvp.slideholder.LayoutRelative;
import com.wgl.mvp.slideholder.SlideHolder;
import com.wgl.mvp.video.VideoUtil;

/**
 * Created by wgl.
 * P层（将原来的activity加以改造）
 */
public class PLoginActivity extends ActivityResult<VLogin> implements IResponse,IGetMessage {

    private HeaderPicture headerPicture = new HeaderPicture(PLoginActivity.this);
    private int animMoveClass = 0;
    private GetMessageUtil autoGetCodeUtil;

    /**
     * 实例化View
     * @return
     */
    @Override
    protected Class<VLogin> getDelegateClass() {
        return VLogin.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setNormal() {
        super.setNormal();
        baseView.slideHolder.setEnabled(false);
        baseView.slideHolder.setSpeed(2);
//        baseView.slideHolder.setDirection(-1);//右边滑出
        baseView.slideHolder.setOnSlideListener(new SlideHolder.OnSlideListener() {
            @Override
            public void onSlideCompleted(boolean opened) {
                baseView.slideHolder.setEnabled(false);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册截取短信监听
        autoGetCodeUtil = new GetMessageUtil(this,this,
                new Handler(),null);
        getContentResolver().registerContentObserver(Uri.parse("content://sms/"),true,autoGetCodeUtil);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //解绑截取短信监听
        if(getContentResolver() != null && autoGetCodeUtil != null){
            getContentResolver().unregisterContentObserver(autoGetCodeUtil);
        }
    }

    /**
     * 按钮监听
     */
    @Override
    protected void setListener() {
        super.setListener();
        baseView.etUser.setText("15201163153");
        baseView.etPassword.setText("woaini1314");
        baseView.setOnClickListener(onClickListener,
                R.id.et_password,
                R.id.et_user,
                R.id.button1,
                R.id.tou,
                R.id.bt1_touxiang,
                R.id.bt2_touxiang,
                R.id.bt3_touxiang,
                R.id.left,
                R.id.right,
                R.id.frame,
                R.id.bt1,
                R.id.bt2,
                R.id.bt3,
                R.id.bt5,
                R.id.bt7);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.et_password:
                    //点击外边，侧滑消失
                    if (animMoveClass == 1) {
                        setSlidingMenu();
                        return;
                    }
                    break;
                case R.id.et_user:
                    if (animMoveClass == 1) {
                        setSlidingMenu();
                        return;
                    }
                    break;
                case R.id.button1:
                    if (animMoveClass == 1) {
                        setSlidingMenu();
                        return;
                    }
                    //网络层的实现(登录)
                    LogCatUtil.log("打印");
                    NetLogin mPresenter = new NetLogin(PLoginActivity.this, PLoginActivity.this);
                    //getIPublic(true): false:不缓存，true:缓存
                    mPresenter.tonetLogin(new OtherCommon(PLoginActivity.this).getIPublic(false), baseView.etUser.getText().toString(), baseView.etPassword.getText().toString(), true, "正在登陆...");
                    break;
                case R.id.tou:
                    if (animMoveClass == 1) {
                        setSlidingMenu();
                        return;
                    }
                    initPopuWindow(PLoginActivity.this, baseView.popupWindow, baseView.view, 1);
                    break;
                case R.id.bt1_touxiang:
                    headerPicture.camera();
                    baseView.popupWindow.dismiss();
                    break;
                case R.id.bt2_touxiang:
                    headerPicture.gallery();
                    baseView.popupWindow.dismiss();
                    break;
                case R.id.bt3_touxiang:
                    baseView.popupWindow.dismiss();
                    break;
                case R.id.left:
                    if (animMoveClass == 1) {
                        setSlidingMenu();
                        return;
                    }
                    baseView.slideHolder.setEnabled(true);
                    baseView.slideHolder.toggle();
                    baseView.layoutRelativeLeft.setOnScrollListener(new LayoutRelative.OnScrollListener() {
                        @Override
                        public void doOnScrollRight(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//                            右边滑出
//                            try {
//                                if(null != baseView.slideHolder){
//                                    if(baseView.slideHolder.isOpened()){
//                                        baseView.slideHolder.close();
//                                        return;
//                                    }
//                                    baseView.slideHolder.setEnabled(true);
//                                    baseView.slideHolder.toggle();
//                                }
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
                        }

                        @Override
                        public void doOnScrollLeft(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                            //左边滑出
                            try {
                                if(null != baseView.slideHolder){
                                    if(baseView.slideHolder.isOpened()){
                                        baseView.slideHolder.close();
                                        return;
                                    }
                                    baseView.slideHolder.setEnabled(true);
                                    baseView.slideHolder.toggle();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                        }}

                        @Override
                        public void doOnRelease() {

                        }
                    });
                    break;
                case R.id.right:
                    setSlidingMenu();
                    baseView.layoutRelativeRight.setOnScrollListener(new LayoutRelative.OnScrollListener() {
                        @Override
                        public void doOnScrollRight(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                            baseView.layoutRelativeRight.startAnimation(baseView.animToRight);
                            baseView.layoutRelativeRight.setVisibility(View.GONE);
                        }

                        @Override
                        public void doOnScrollLeft(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

                        }

                        @Override
                        public void doOnRelease() {

                        }
                    });
                    break;
                case R.id.frame:
                    if (animMoveClass == 1) {
                        setSlidingMenu();
                    }
                    break;
                case R.id.bt1:
                    //发短信
                    if (animMoveClass == 1) {
                        setSlidingMenu();
                    }
                    new SendMesssage(PLoginActivity.this).sendSMS("hello，world！");
                    break;
                case R.id.bt2:
                    baseView.slideHolder.setEnabled(true);
                    baseView.slideHolder.toggle();
                    if (animMoveClass == 1) {
                        setSlidingMenu();
                    }
                    MyToastView.showToast("收到短信时，才会截获",PLoginActivity.this);
                    break;
                case R.id.bt3:
                    //录音
                    if (animMoveClass == 1) {
                        setSlidingMenu();
                    }
                    new VideoUtil(PLoginActivity.this).soundRecorderMethod();
                    break;
                case R.id.bt5:
                    //录视频
                    if (animMoveClass == 1) {
                        setSlidingMenu();
                    }
                    new VideoUtil(PLoginActivity.this).videoMethod();
                    break;
                case R.id.bt7:
                    if (animMoveClass == 1) {
                        setSlidingMenu();
                    }
                    new Call(PLoginActivity.this).call("10086");
                    break;
            }
        }
    };
    /**
     * 设置侧滑
     */
    public void setSlidingMenu() {
        if (animMoveClass == 0) {//滑出
            baseView.layoutRelativeRight.setVisibility(View.VISIBLE);
            baseView.layoutRelativeRight.startAnimation(baseView.animToLeft);
            baseView.layoutRelativeRight.setFocusableInTouchMode(true);
            animMoveClass = 1;
        } else {//消失
            baseView.layoutRelativeRight.startAnimation(baseView.animToRight);
            baseView.layoutRelativeRight.setVisibility(View.GONE);
            animMoveClass = 0;
            baseView.layoutRelativeRight.setFocusableInTouchMode(false);
        }
    }

    @Override
    public void onSuccess(IModel modle) {
        MyToastView.showToast("登陆成功",this);
        CommonUtil.toActivity(this,PPictureActivity.class);
    }

    @Override
    public void onFailure(String error) {
        MyToastView.showToast(error,this);
        CommonUtil.toActivity(this,PPictureActivity.class);
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

    @Override
    protected ImageView setPhoto() {
        return baseView.imageView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case RequestPermissionUtil.REQUEST_SEND_SMS:
                if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"权限被拒绝,请检查权限",Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    @Override
    public void getMessage(String ms) {
        MyToastView.showToast(ms,this);
    }
}
