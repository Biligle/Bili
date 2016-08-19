package com.wgl.mvp.selectPicture;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by wgl.
 */
public class GetPicture {

    private ProgressDialog mProgressDialog;
    /**
     * 扫描拿到所有的图片路径
     */
    private ArrayList<String> mImageFloders = new ArrayList<String>();
    /**
     * 扫描拿到每个文件夹的封面图片路径
     */
    private ArrayList<String> firstImages= new ArrayList<String>();
    private Context context;
    private ISelect iSelect;
    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashSet<String> mDirPaths = new HashSet<String>();

    public GetPicture(Context context, ISelect iSelect){
        this.context = context;
        this.iSelect = iSelect;
        getImages();
    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
     */
    private void getImages(){
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)){
            Toast.makeText(context, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }
        // 显示进度条
        mProgressDialog = ProgressDialog.show(context, null, "正在加载...");

        new Thread(new Runnable(){
            @Override
            public void run(){
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = context.getContentResolver();
                // 只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[] { "image/jpeg", "image/png" },
                        MediaStore.Images.Media.DATE_MODIFIED);
//                Log.e("TAG", mCursor.getCount() + "");
                while (mCursor.moveToNext()){
                    // 获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));
//                    Log.e("TAG", path);
//                    if(mImageFloders.contains(path)){
//                        continue;
//                    }else{
                        mImageFloders.add(path);
//                    }
                    // 获取该图片的父路径名
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null) {
                        continue;
                    }
                    String dirPath = parentFile.getAbsolutePath();
                    // 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
                    if (mDirPaths.contains(dirPath)){
                        continue;
                    }else{
                        mDirPaths.add(dirPath);
                        firstImages.add(path);
                    }
                }
                mCursor.close();
                mProgressDialog.dismiss();
                // 通知Handler扫描图片完成
                iSelect.getPicture(mImageFloders,firstImages);

            }
        }).start();

    }

    /**
     * 获取文件夹中的所有图片
     * @param path
     */
    public static ArrayList<String> getFilePic(String path){
        String start = path.substring(0,path.lastIndexOf("/"));
        List<String> paths = Arrays.asList(new File(path).getParentFile().list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                if(filename.endsWith("jpeg") || filename.endsWith(".jpg") || filename.endsWith(".png")){
                    return true;
                }
                return false;
            }
        }));
        ArrayList<String> data = new ArrayList<String>();
        for(String str : paths){
            data.add(start + "/"+str);
        }
        return data;
    }
}
