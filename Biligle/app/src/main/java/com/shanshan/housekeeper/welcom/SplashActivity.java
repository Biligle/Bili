package com.shanshan.housekeeper.welcom;

import android.os.Bundle;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.shanshan.housekeeper.Help.utils.CommonUtil;
import com.shanshan.housekeeper.login.PLoginActivity;
import com.shanshan.housekeeper.R;
import com.wgl.mvp.presenter.ActivityPresenter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wgl.
 */
public class SplashActivity extends ActivityPresenter<SplashView> {

    private android.os.Handler handler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    CommonUtil.toActivity(SplashActivity.this,PLoginActivity.class);
                    overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
                    handler.sendEmptyMessage(2);
                    break;
                case 2:
                    SplashActivity.this.finish();
                    break;
            }
        }
    };

    @Override
    protected Class getDelegateClass() {
        return SplashView.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Animation animation = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.zoom_enter);
        baseView.ivSplash.startAnimation(animation);
    }

    @Override
    protected void setListener() {
        super.setListener();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                handler.sendEmptyMessage(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
