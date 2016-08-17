package com.wgl.mvp.activityResult;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;
import com.wgl.mvp.headerPicture.HeaderPicture;
import com.wgl.mvp.presenter.ActivityPresenter;
import com.wgl.mvp.video.VideoUtil;
import com.wgl.mvp.view.IDelegate;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 处理回调：照相、相册、录音、录视频
 * Created by wgl.
 */
public abstract class ActivityResult<T extends IDelegate> extends ActivityPresenter<T> {

    protected HeaderPicture headerPicture = new HeaderPicture(this);
    public String base64;
    protected abstract ImageView getPhoto();
    protected abstract String getBase64(String base64);
    public static boolean crop = true;
    private Bitmap bm,bm2;
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
                    if(temp.exists()){
                        if(!crop){
                            headerPicture.startPhotoZoom(Uri.fromFile(temp),300,300);
                        }else{
                            //压缩到100kb
                            bm2 = headerPicture.compress(Environment.getExternalStorageDirectory()
                                    + "/" + HeaderPicture.IMAGE_FILE_NAME,100);
                            base64 = headerPicture.setPicgetBase64_2(bm2,getPhoto());
                            if(!bm.isRecycled() && !bm2.isRecycled()){
                                bm.recycle();
                                bm2.recycle();
                            }
                            getBase64(base64);
                        }
                    }
                    return;
                }
                if(requestCode == HeaderPicture.SELECT_PIC_BY_PICK_PHOTO){
                    try {
                        if(!crop){
                            headerPicture.startPhotoZoom(data.getData(),300,300);
                        }else{
//                            bm = MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
                            //压缩到100kb
                            bm2 = headerPicture.compress(getPath(data.getData()),100);
                            base64 = headerPicture.setPicgetBase64_2(bm2,getPhoto());
                            if(/*!bm.isRecycled() && */!bm2.isRecycled()){
                                /*bm.recycle();*/
                                bm2.recycle();
                            }
                            getBase64(base64);
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();//用户点击取消操作
                    }
                    return;
                }
                if(requestCode == HeaderPicture.REQUESTCODE_CUTTING){
                    if (data != null) {
                        base64 = headerPicture.setPicgetBase64(data,getPhoto());
                        getBase64(base64);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != bm && null != bm2){
            //回收Bitmap,防止内存溢出OOM
            bm.recycle();
            bm2.recycle();
        }}

    private String getPath(Uri originalUri){
        String[] proj = {MediaStore.Images.Media.DATA};

        //好像是android多媒体数据库的封装接口，具体的看Android文档
        Cursor cursor = managedQuery(originalUri, proj, null, null, null);
        //按我个人理解 这个是获得用户选择的图片的索引值
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        //将光标移至开头 ，这个很重要，不小心很容易引起越界
        cursor.moveToFirst();
        //最后根据索引值获取图片路径
        String path = cursor.getString(column_index);
        return path;
    }
}
