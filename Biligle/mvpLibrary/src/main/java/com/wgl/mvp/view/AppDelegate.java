/**
 * 此框架是个人，根据网上代码改写，目前具有局限性，使用者可根据需求添加接口和方法，
 * 如有改动，请联系作者
 * qq：846462358 回答问题：王国立
 * @author wangguoli
 */
package com.wgl.mvp.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

/**
 * 视图层代理的基类
 *
 * @author wangguoli
 */
public abstract class AppDelegate implements IDelegate {
    protected final SparseArray<View> mViews = new SparseArray<View>();
    protected View rootView;
    protected View rootView2;
    protected View rootView3;

    public abstract int getRootLayoutId();
    public abstract int getBrotherLayoutId();
    public abstract int getBrotherLayoutId2();

    @Override
    public void create(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int rootLayoutId = getRootLayoutId();
        int brotherLayoutId = getBrotherLayoutId();
        int brotherLayoutId2 = getBrotherLayoutId2();
        rootView = inflater.inflate(rootLayoutId, container, false);
        if(brotherLayoutId != 0){
            rootView2 = inflater.inflate(brotherLayoutId, container, false);
        }
        if(brotherLayoutId2 != 0){
            rootView3 = inflater.inflate(brotherLayoutId2, container, false);
        }
    }

    @Override
    public int getOptionsMenuId() {
        return 0;
    }

    public Toolbar getToolbar() {
        return null;
    }

    /**
     * 获得根部局
     * @return
     */
    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public View getBrotherView() {
        return rootView2;
    }

    @Override
    public View getBrotherView2() {
        return rootView3;
    }

    /**
     * 获得根部局，内部的控件
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T get(int id) {
        return (T) bindView(id);
    }

    public <T extends View> T bindView(int id) {
        T view = (T) mViews.get(id);
        if (view == null) {
            view = (T) rootView.findViewById(id);
            mViews.put(id, view);
        }
        return view;
    }

    /**
     * 获得相关布局，内部的控件
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T getBrother(int id) {
        return (T) bindBrother(id);
    }

    public <T extends View> T bindBrother(int id) {
        T view = (T) mViews.get(id);
        if (view == null) {
            view = (T) rootView2.findViewById(id);
            mViews.put(id, view);
        }
        return view;
    }
    /**
     * 获得相关布局，内部的控件
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T getBrother2(int id) {
        return (T) bindBrother2(id);
    }

    public <T extends View> T bindBrother2(int id) {
        T view = (T) mViews.get(id);
        if (view == null) {
            view = (T) rootView3.findViewById(id);
            mViews.put(id, view);
        }
        return view;
    }


    /**
     * 长按事件
     * @param listener
     * @param ids
     */
    public void setOnLongClickListener(View.OnLongClickListener listener, int... ids) {
        if (ids == null) {
            return;
        }
        for (int id : ids) {
            if(get(id) != null){
                get(id).setOnLongClickListener(listener);
            }else{
                getBrother(id).setOnLongClickListener(listener);
            }
        }
    }
    /**
     * 触碰事件
     * @param listener
     * @param ids
     */
    public void setOnTouchListener(View.OnTouchListener listener, int... ids) {
        if (ids == null) {
            return;
        }
        for (int id : ids) {
            if(get(id) != null){
                get(id).setOnTouchListener(listener);
            }else{
                getBrother(id).setOnTouchListener(listener);
            }
        }
    }
    /**
     * 点击事件(针对根部局中的控件)
     * @param listener
     * @param ids
     */
    public void setOnClickListener(View.OnClickListener listener, int... ids) {
        if (ids == null) {
            return;
        }
        for (int id : ids) {
            if(get(id) != null){
                get(id).setOnClickListener(listener);
            }else{
                getBrother(id).setOnClickListener(listener);
            }
        }
    }

    /**
     * 获得activity
     * @param <T>
     * @return
     */
    public <T extends Activity> T getActivity() {
        return (T) rootView.getContext();
    }

}
