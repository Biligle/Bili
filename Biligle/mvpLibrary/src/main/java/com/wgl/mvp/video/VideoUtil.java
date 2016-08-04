package com.wgl.mvp.video;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;

import com.wgl.mvp.requestPermission.RequestPermissionUtil;

/**
 * Created by wgl.
 */
public class VideoUtil {

    private Context context;
    /** 拍摄视频*/
    public static final int REQUEST_CODE_TAKE_VIDEO = 3;
    /** 录音功能*/
    public static final int RESULT_CAPTURE_RECORDER_SOUND = 4;
    public static String strVideoPath = "";// 视频文件的绝对路径
    public static String strRecorderPath = "";// 录音文件的绝对路径

    public VideoUtil(Context context){
       this.context = context;
    }

    /**
     * 拍摄视频
     */
    public void videoMethod() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        if(RequestPermissionUtil.getRequestPermissionUtilInstance().
                insertDummyContactWrapper((Activity) context, Manifest.permission.CAMERA,RequestPermissionUtil.REQUEST_CAMERA_PERMISSIONS)){

            ((Activity)context).startActivityForResult(intent, REQUEST_CODE_TAKE_VIDEO);
        }
    }

    /**
     * 录音功能
     */
    public void soundRecorderMethod() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/amr");
        if(RequestPermissionUtil.getRequestPermissionUtilInstance().
                insertDummyContactWrapper((Activity) context, Manifest.permission.RECORD_AUDIO,RequestPermissionUtil.REQUEST_RECORD_AUDIO)){
            ((Activity)context).startActivityForResult(intent, RESULT_CAPTURE_RECORDER_SOUND);
        }
    }
}
