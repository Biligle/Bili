package com.wgl.mvp.headerPicture;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

import com.wgl.mvp.presenter.ActivityPresenter;
import com.wgl.mvp.view.IDelegate;

import java.io.File;

/**
 * Created by wgl.
 */
public abstract class HeaderActivity<T extends IDelegate> extends ActivityPresenter<T> {

    protected HeaderPicture headerPicture = new HeaderPicture(this);
    protected String base64;
    protected abstract ImageView setPhoto();
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED){
            Toast.makeText(this,"取消",Toast.LENGTH_SHORT).show();
            return;
        }
        switch (resultCode) {
            case RESULT_OK:
                if(requestCode == HeaderPicture.SELECT_PIC_BY_TACK_PHOTO){
                    File temp = new File(Environment.getExternalStorageDirectory()
                            + "/" + HeaderPicture.IMAGE_FILE_NAME);
                    headerPicture.startPhotoZoom(Uri.fromFile(temp),300,300);
                    return;
                }
                if(requestCode == HeaderPicture.SELECT_PIC_BY_PICK_PHOTO){
                    try {
                        headerPicture.startPhotoZoom(data.getData(),300,300);
                    } catch (NullPointerException e) {
                        e.printStackTrace();//用户点击取消操作
                    }
                    return;
                }
                if(requestCode == HeaderPicture.REQUESTCODE_CUTTING){
                    if (data != null) {
                        base64 = headerPicture.setPicgetBase64(data,setPhoto());
                    }
                }
                break;

        }
    }
}
