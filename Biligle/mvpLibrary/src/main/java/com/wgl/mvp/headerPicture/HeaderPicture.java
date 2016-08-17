package com.wgl.mvp.headerPicture;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.wgl.mvp.requestPermission.RequestPermissionUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by wgl.
 */
public class HeaderPicture {

    private Context context;
    /***
     * 使用照相机拍照获取图片
     */
    public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
    /***
     * 使用相册中的图片
     */
    public static final int SELECT_PIC_BY_PICK_PHOTO = 2;
    /***
     * 从Intent获取图片路径的KEY
     */
    public static final String IMAGE_FILE_NAME = "image_biLiGle";
    /***
     * 剪裁图片
     */
    public static final int REQUESTCODE_CUTTING = 3;

    public HeaderPicture(Context context) {
        this.context = context;
    }

    /**
     * 照相
     */
    public void camera() {
        Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 下面这句指定调用相机拍照后的照片存储的路径
        if (hasSdcard()) {
            takeIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(Environment
                            .getExternalStorageDirectory(), IMAGE_FILE_NAME)));
        }
        if (RequestPermissionUtil.getRequestPermissionUtilInstance().insertDummyContactWrapper(
                (Activity) context, Manifest.permission.CAMERA, RequestPermissionUtil.REQUEST_CAMERA_PERMISSIONS
        )) {
            ((Activity) context).startActivityForResult(takeIntent, SELECT_PIC_BY_TACK_PHOTO);
        }
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }

    /**
     * 相册
     */
    public void gallery() {
        // 图库选择
        Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
        // 也可以直接写如："image/jpeg 、 image/png等的类型"
        pickIntent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        if (RequestPermissionUtil.getRequestPermissionUtilInstance().insertDummyContactWrapper(
                (Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE, RequestPermissionUtil.REQUEST_GALLERY_PERMISSIONS
        )) {
            ((Activity) context).startActivityForResult(pickIntent, SELECT_PIC_BY_PICK_PHOTO);
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri, int width, int height) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("return-data", true);
        if (RequestPermissionUtil.getRequestPermissionUtilInstance().insertDummyContactWrapper((Activity) context, Manifest.permission.CAMERA, RequestPermissionUtil.REQUEST_CAMERA_PERMISSIONS)) {
            ((Activity) context).startActivityForResult(intent, REQUESTCODE_CUTTING);
        }
    }

    /**
     * 根据剪裁，设置头像，并将头像base64转码
     */
    public String setPicgetBase64(Intent picdata, ImageView imageView) {
        String imageBase64 = "";
        Bundle extras = picdata.getExtras();
        if (null != extras) {
            /**----------------------设置图片开始----------------------------**/
            //取得SDCard图片路径做显示
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(null, photo);
            imageView.setImageDrawable(drawable);
            /**----------------------设置图片结束----------------------------**/


            /**----------------------转码开始----------------------------**/
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] encode = encode(photo, out);
            imageBase64 = new String(encode);
            /**----------------------转码结束----------------------------**/
        }
        return imageBase64;
    }

    /**
     * 不剪裁，设置图片，转码
     */
    public String setPicgetBase64_2(Bitmap bitmap, ImageView imageView) {
        String imageBase64 = "";
        imageView.setImageBitmap(bitmap);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] encode = encode(bitmap, out);
        imageBase64 = new String(encode);
        return imageBase64;
    }

    /**
     * 将图片进行base64编码
     *
     * @param photo
     * @param out
     */
    private byte[] encode(Bitmap photo, ByteArrayOutputStream out) {
        try {
            photo.compress(Bitmap.CompressFormat.JPEG, 100, out);
//            Log.e("==========",""+out.toByteArray().length/1024);
            out.flush();
            out.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        byte[] buffer = out.toByteArray();

        byte[] encode = Base64.encode(buffer, Base64.DEFAULT);
        return encode;
    }

    /**
     * 压缩图片
     * @param path
     * @param maxSize 压缩尺寸
     * @return
     */
    public static Bitmap compress(String path,double maxSize) {

        BitmapFactory.Options op = new BitmapFactory.Options();
        //inJustDecodeBounds
        //If set to true, the decoder will return null (no bitmap), but the out…
        op.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, op); //获取尺寸信息
        //获取比例大小
        int wRatio = (int)Math.ceil(op.outWidth/200);
        int hRatio = (int)Math.ceil(op.outHeight/200);
        //如果超出指定大小，则缩小相应的比例
        if(wRatio > 1 && hRatio > 1){
            if(wRatio > hRatio){
                op.inSampleSize = wRatio;
            }else{
                op.inSampleSize = hRatio;
            }
        }
        op.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(path, op);
        //图片允许最大空间   单位：KB
//        maxSize =100.00;
        //将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        //将字节换成KB
        double mid = b.length/1024;
        //判断bitmap占用空间是否大于允许最大空间  如果大于则压缩 小于则不压缩
        if (mid > maxSize) {
            //获取bitmap大小 是允许最大大小的多少倍
            double i = mid / maxSize;
            //开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
            bitmap = zoomImage(bitmap, bitmap.getWidth() / Math.sqrt(i),
                    bitmap.getHeight() / Math.sqrt(i));
        }
        return bitmap;
    }

    /***
     * 图片的缩放方法
     *
     * @param bgimage
     *            ：源图片资源
     * @param newWidth
     *            ：缩放后宽度
     * @param newHeight
     *            ：缩放后高度
     * @return
     */
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }
}
