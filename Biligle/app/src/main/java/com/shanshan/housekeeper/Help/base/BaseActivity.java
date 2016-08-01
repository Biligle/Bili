package com.shanshan.housekeeper.Help.base;

import android.os.Bundle;
import android.view.Window;

import com.shanshan.housekeeper.Help.utils.CommonUtil;
import com.shanshan.housekeeper.Help.utils.MyToastView;
import com.shanshan.housekeeper.Internet.BaseNet;
import com.shanshan.housekeeper.interfaces.IPublic;
import com.wgl.mvp.presenter.ActivityPresenter;
import com.wgl.mvp.view.IDelegate;

/**
 * 基类，继承了父类，并添加了具体的方法
 * Created by wgl.
 */
public abstract class BaseActivity<T extends IDelegate> extends ActivityPresenter<T> {

    private BaseNet mBaseNet;
    private IPublic iPublic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        MyApplication.list.add(this);
    }

    public IPublic getIPublic(boolean flagIsCache){
        if(mBaseNet==null){
            mBaseNet= new BaseNet();
        }
        if(iPublic==null){
            /*缓存地址：/data/user/0/com.shanshan.housekeeper/cache*/
            iPublic=mBaseNet.getApiClient(IPublic.class,this.getCacheDir(), CommonUtil.isNetworkAvailable(this),flagIsCache);
        }
        return iPublic;
    }

    private long time;
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if(System.currentTimeMillis() - time >2000){
            time = System.currentTimeMillis();
            MyToastView.showToast("再按一次退出", this);
        }else{
            MyApplication.exit();
        }
        return false;
    }
}
