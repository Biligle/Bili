package com.wgl.mvp.call;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.wgl.mvp.requestPermission.RequestPermissionUtil;

/**
 * 打电话
 * Created by wgl.
 */
public class Call {
    private Context context;
    public Call(Context context){
        this.context = context;
    }

    public void call(String mobile){
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
                + mobile));
        if(RequestPermissionUtil.getRequestPermissionUtilInstance().
                insertDummyContactWrapper((Activity) context, Manifest.permission.CALL_PHONE,RequestPermissionUtil.REQUEST_CALL_PHONE_PERMISSIONS)){
            context.startActivity(intent);
        }
    }
}
