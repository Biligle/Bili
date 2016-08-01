/**
 * 此框架是个人，根据网上代码改写，目前具有局限性，使用者可根据需求添加接口和方法，
 * 如有改动，请联系作者
 * qq：846462358 回答问题：王国立
 * @author wangguoli
 */
package com.wgl.mvp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

/**
 * 视图层代理的接口协议
 * @author
 */
public interface IDelegate {
    void create(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    int getOptionsMenuId();

    Toolbar getToolbar();

    View getRootView();
    View getBrotherView();
    View getBrotherView2();

    void initWidget();
}
