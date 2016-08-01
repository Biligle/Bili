package com.shanshan.housekeeper.Help.utils;

import android.util.Log;

/**
 * Created by wgl.
 * 打印日志，将flag设置成true时，不会打印
 */
public class LogCatUtil {

    public static void log(String message){
        boolean flag = false;
        if(!flag){
            Log.e("||日志||","================================================" +"\n"+
                    "  ||"+"\n"+
                    "  ||<<<<<<<<LOGCAT>>>>>>>> "+message+"\n"+
                    "  ||"+"\n"+
                    "  ================================================");
        }
        }
}
