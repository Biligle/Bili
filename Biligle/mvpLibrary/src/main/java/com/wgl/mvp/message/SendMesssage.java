package com.wgl.mvp.message;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Telephony;

import com.wgl.mvp.requestPermission.RequestPermissionUtil;

/**
 * Created by wgl.
 */
public class SendMesssage {
    private Context context;
    public SendMesssage(Context context){
        this.context = context;
    }

    /**
     * 发短信
     */
    public void sendSMS(String content){
        String smsBody = content;
        Intent sendIntent = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){//版本大于等于4.4（API>=19）
            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(context); //Need to change the build to API 19
            sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT, smsBody);
            if (defaultSmsPackageName != null){
                sendIntent.setPackage(defaultSmsPackageName);
            }
            if(RequestPermissionUtil.getRequestPermissionUtilInstance().insertDummyContactWrapper((Activity) context, Manifest.permission.SEND_SMS, RequestPermissionUtil.REQUEST_SEND_SMS)){
                context.startActivity(sendIntent);//版本大于等于6.0(API>=23)
            }
        }else {//版本低于4.4(API<19)
            sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.setData(Uri.parse("sms:"));
            sendIntent.putExtra("sms_body", smsBody);
            context.startActivity(sendIntent);
        }

    }

}
