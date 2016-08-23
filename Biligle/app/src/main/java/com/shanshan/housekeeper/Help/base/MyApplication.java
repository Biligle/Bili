package com.shanshan.housekeeper.Help.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.shanshan.housekeeper.R;
import com.shanshan.housekeeper.login.PLoginActivity;
import com.wgl.mvp.crashHandler.CrashHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wgl
 */
public class MyApplication extends Application {

    public static ArrayList<Activity> list = new ArrayList<Activity>();
    /**
     * DisplayImageOptions是用于设置图片显示的类
     */
    public static DisplayImageOptions options=  new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.ic_launcher) //设置图片在下载期间显示的图片
    .showImageForEmptyUri(R.mipmap.ic_launcher)//设置图片Uri为空或是错误的时候显示的图片
    .showImageOnFail(R.mipmap.ic_launcher)  //设置图片加载/解码过程中错误时候显示的图片
    .cacheInMemory(true)//设置下载的图片是否缓存在内存中
    .cacheOnDisc(true)//设置下载的图片是否缓存在SD卡中
//    .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
    .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
    .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
    //.preProcessor(BitmapProcessor preProcessor)
//    .displayer(new RoundedBitmapDisplayer(100))//是否设置为圆角，弧度为多少
            .build();//构建完成


    /**
     * 图片加载监听类
     */
    public static ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(getApplicationContext());
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this, PLoginActivity.class);//处理未捕捉的异常，避免出现崩溃
    }

    /**
     * 完全退出
     */
    public static void exit(){
        if(list != null){
            for(Activity activity : list){
                activity.finish();
            }
            System.exit(0);
            list.clear();
        }
    }

    /**
     * 图片加载第一次显示监听器
     *
     * @author Administrator
     */
    private static class AnimateFirstDisplayListener extends
            SimpleImageLoadingListener {

        final List<String> displayedImages = Collections
                .synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingStarted(String imageUri, View view) {
            // spinner.setVisibility(View.VISIBLE); 显示进度条
        }

        @Override
        public void onLoadingComplete(String imageUri, View view,
                                      Bitmap loadedImage) {
            // spinner.setVisibility(View.GONE); // 不显示圆形进度条
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                // 是否第一次显示
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    // 图片淡入效果
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }

        @Override
        public void onLoadingFailed(String imageUri, View view,
                                    FailReason failReason) {
            String message = null;
            switch (failReason.getType()) { // 获取图片失败类型
                case IO_ERROR: // 文件I/O错误
                    message = "Input/Output error";
                    break;
                case DECODING_ERROR: // 解码错误
                    message = "Image can't be decoded";
                    break;
                case NETWORK_DENIED: // 网络延迟
                    message = "Downloads are denied";
                    break;
                case OUT_OF_MEMORY: // 内存不足
                    message = "Out Of Memory error";
                    break;
                case UNKNOWN: // 原因不明
                    message = "Unknown error";
                    break;
            }

            // spinner.setVisibility(View.GONE);
        }
    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }
}
