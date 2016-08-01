package com.shanshan.housekeeper.Help.utils;

import android.content.Context;

import com.shanshan.housekeeper.Internet.BaseNet;
import com.shanshan.housekeeper.interfaces.IPublic;

/**
 * Created by wgl.
 */
public class OtherCommon {
    private BaseNet mBaseNet;
    private IPublic iPublic;
    private Context context;
    public OtherCommon(Context context){
        this.context = context;
    }
    public IPublic getIPublic(boolean flagIsCache){
        if(mBaseNet==null){
            mBaseNet= new BaseNet();
        }
        if(iPublic==null){
            /*缓存地址：/data/user/0/com.shanshan.housekeeper/cache*/
            iPublic=mBaseNet.getApiClient(IPublic.class,context.getCacheDir(), CommonUtil.isNetworkAvailable(context),flagIsCache);
        }
        return iPublic;
    }
}
