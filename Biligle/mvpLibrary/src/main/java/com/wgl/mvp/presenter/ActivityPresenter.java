/**
 * 此框架是个人，根据网上代码改写，目前具有局限性，使用者可根据需求添加接口和方法，
 * 如有改动，请联系作者
 * qq：846462358 回答问题：王国立
 * @author wangguoli
 */
package com.wgl.mvp.presenter;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.Toolbar;

import com.wgl.mvp.view.IDelegate;


/**
 * Presenter层的实现基类
 *
 * @param <T>
 * @author wangguoli
 */
public abstract class ActivityPresenter<T extends IDelegate> extends FragmentActivity {
    protected T baseView;

    public ActivityPresenter() {
        try {
            baseView = getDelegateClass().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("create IDelegate error");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("create IDelegate error");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseView.create(getLayoutInflater(), null, savedInstanceState);
        setContentView(baseView.getRootView());
        initToolbar();
        baseView.initWidget();
        setNormal();
        setListener();
        toNet();
        readCache();
    }

    protected void setNormal(){}

    protected void setListener(){}

    protected void toNet(){
        //TODO 读取网络
    }

    protected void readCache(){
        //TODO 读取缓存
    }

    protected void initToolbar() {
        Toolbar toolbar = baseView.getToolbar();
        if (toolbar != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                setActionBar(toolbar);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (baseView.getOptionsMenuId() != 0) {
            getMenuInflater().inflate(baseView.getOptionsMenuId(), menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        baseView = null;
    }

    protected abstract Class<T> getDelegateClass();
}
