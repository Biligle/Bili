package com.shanshan.housekeeper.Help.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;

import com.shanshan.housekeeper.Help.defaultview.CustomProgressDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wgl.
 */
public class CommonUtil {


    public static CustomProgressDialog progressDialog;

    /**
     * 判断网络是否可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*
     * 判断字符串是否为空
     *
     * @param text
     *
     * @return true null false !null
     */
    public static boolean isNull(String text) {
        if (text == null || "".equals(text.trim()) || "null".equals(text)
                || "null".equals(text.trim()) || "<null>".equals(text))
            return true;
        return false;
    }

    /**
     * 得到自定义的progressDialog
     *
     * @param msg
     * @return
     */
    public static void startProgressDialog(Context mContext, String msg) {
        if (progressDialog != null && progressDialog.isShowing()) {
            try {
                progressDialog.dismiss();
                progressDialog = null;
            } catch (Exception e) {
            }

        }
        if (progressDialog != null) {
            progressDialog = null;
        }
        // if (progressDialog == null) {
        progressDialog = CustomProgressDialog.show(mContext, msg);
        // }
        try {
            progressDialog.setCanceledOnTouchOutside(false);
            if (mContext != null) {
                progressDialog.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 关闭自定义的progressDialog
     */

    public static void stopProgressDialog() {
        if (progressDialog != null) {
            progressDialog.closeDialog();
            progressDialog = null;// 否则下次不显示
        }
    }

    /**
     * 跳转Activity
     * @param context
     * @param class1
     */
    public static void toActivity(Context context, Class class1){
        Intent intent = new Intent(context,class1);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent);
    }

    /**
     * 获得手机屏幕的宽、高、屏幕密度、屏幕密度dpi 若想得到每个值，调用Map的get("key")方法。
     *
     * @param context
     * @return map<String, Object>
     */
    public static Modle getAttribute(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(metric);
        Modle modle = new Modle();
        modle.width = metric.widthPixels;
        modle.height = metric.heightPixels;
        modle.density = metric.density;
        modle.densityDpi = metric.densityDpi;
        return modle;
    }

    public static class Modle{
        public int width; // 屏幕宽度（像素）
        public int height; // 屏幕高度（像素）
        public int densityDpi;// 屏幕密度DPI（120 / 160 / 240）
        public float density;// 屏幕密度（0.75 / 1.0 / 1.5）
    }

}
