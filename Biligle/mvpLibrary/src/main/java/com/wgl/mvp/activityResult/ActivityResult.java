package com.wgl.mvp.activityResult;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;
import com.wgl.mvp.headerPicture.HeaderPicture;
import com.wgl.mvp.presenter.ActivityPresenter;
import com.wgl.mvp.video.VideoUtil;
import com.wgl.mvp.view.IDelegate;
import java.io.File;

/**
 * 处理回调：照相、相册、录音、录视频
 * Created by wgl.
 */
public abstract class ActivityResult<T extends IDelegate> extends ActivityPresenter<T> {

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
            case VideoUtil.RESULT_CAPTURE_RECORDER_SOUND://录音
                    //  file:///storage/emulated/0/MIUI/sound_recorder/9%E6%9C%8818%E6%97%A5%20%E4%B8%8B%E5%8D%884%E7%82%B958%E5%88%86.amr
                    //  content://media/external/audio/media/2468929

                    Uri uriRecorder = data.getData();
//                    Log.i(Tag,"uri:"+uriRecorder.toString());

                    String scheme=uriRecorder.getScheme();
//                    Log.i(Tag,"scheme:"+scheme);

                    if(scheme.equals("file")){
//                        Log.i(Tag,"path:"+uriRecorder.getEncodedPath());
                        Intent it = new Intent(Intent.ACTION_VIEW);
                        it.setDataAndType(uriRecorder, "audio/MP3");
                        startActivity(it);

                    }else if(scheme.equals("content")){
                        Cursor cursor=this.getContentResolver().query(uriRecorder, null, null, null, null);
                        if (cursor.moveToNext()) {
                         /* _data：文件的绝对路径 ，_display_name：文件名 */
                            VideoUtil.strRecorderPath = cursor.getString(cursor.getColumnIndex("_data"));

//                            Log.i(Tag,"path:"+strRecorderPath);

                            Intent it = new Intent(Intent.ACTION_VIEW);
                            it.setDataAndType(Uri.parse("file://" + VideoUtil.strRecorderPath), "audio/MP3");
                            startActivity(it);
                        }
                    }

                break;
            case VideoUtil.REQUEST_CODE_TAKE_VIDEO://拍摄视频
                    //content://media/external/video/media/5743
                    Uri uriVideo = data.getData();
//                    Log.i("Tag","uri:"+uriVideo.toString());

                    Cursor cursor=this.getContentResolver().query(uriVideo, null, null, null, null);
                    if(cursor!=null){
                        if (cursor.moveToNext()) {
                        /* _data：文件的绝对路径 ，_display_name：文件名 */
                            VideoUtil.strVideoPath = cursor.getString(cursor.getColumnIndex("_data"));
//                            Log.i(Tag,"strVideoPath:"+strVideoPath);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(Uri.parse("file://"+VideoUtil.strVideoPath), "video/mp4");
                            startActivity(intent);
                        }
                    }
                break;

        }
    }
}
