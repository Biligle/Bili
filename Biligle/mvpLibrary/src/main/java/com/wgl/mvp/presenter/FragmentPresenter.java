/**
 * 此框架是个人，根据网上代码改写，目前具有局限性，使用者可根据需求添加接口和方法，
 * 如有改动，请联系作者
 * qq：846462358 回答问题：王国立
 * @author wangguoli
 */
package com.wgl.mvp.presenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wgl.mvp.view.IDelegate;

/**
 * Presenter base class for Fragment
 * Presenter层的实现基类
 *
 * @param <T> View delegate class type
 * @author
 */
public abstract class FragmentPresenter<T extends IDelegate> extends Fragment {
    public T baseView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            baseView = getDelegateClass().newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        baseView.create(inflater, container, savedInstanceState);
        baseView.initWidget();
        return baseView.getRootView();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        baseView.initWidget();
        setListener();
        toNet();
        readCache();
    }

    protected void setListener(){}

    protected void toNet(){}

    protected void readCache(){}

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (baseView.getOptionsMenuId() != 0) {
            inflater.inflate(baseView.getOptionsMenuId(), menu);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        baseView = null;
    }

    protected abstract Class<T> getDelegateClass();
}
