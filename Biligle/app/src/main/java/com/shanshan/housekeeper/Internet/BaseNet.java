package com.shanshan.housekeeper.Internet;


import android.util.Log;

import com.shanshan.housekeeper.Help.consts.NetURL;
import com.shanshan.housekeeper.Help.utils.LogCatUtil;
import com.shanshan.housekeeper.interfaces.IPublic;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;


/**
 * Created by wgl.
 * 自定义Retrofit
 */
public class BaseNet {
    /**
     * 设置缓存 10M
     */
    public static final long HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024;
    /**
     * 是否连网
     */
    private boolean flagToNet;
    public BaseNet( ) {

    }

    /**
     * 自定义Retrofit. 返回接口类IPublic
     *
     * @param intance     所有接口类
     * @param baseDir     缓存文件
     * @param flagToNet   false:未联网 true:联网
     * @param flagIsCache false:不缓存  true:缓存
     */
    public IPublic getApiClient(Class<IPublic> intance, File baseDir, boolean flagToNet, boolean flagIsCache) {
        this.flagToNet = flagToNet;
        Retrofit retrofit;
        if (flagIsCache) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(NetURL.URL_BASE)
                    .client(getOkHttpClient(baseDir))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } else {
            retrofit = new Retrofit.Builder()
                    .baseUrl(NetURL.URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }


        return retrofit.create(intance);
    }

    // 缓存根目录，由这里推荐 -> http://stackoverflow.com/a/32752861/400717.
// 小心可能为空，参考下面两个链接
// https://groups.google.com/d/msg/android-developers/-694j87eXVU/YYs4b6kextwJ 和
// http://stackoverflow.com/q/4441849/400717.
//  // final  File baseDir = context.getCacheDir();
    public OkHttpClient getOkHttpClient(File baseDir) {
        OkHttpClient client = null;
        if (baseDir != null) {
            //设置缓存路径
            File httpCacheDirectory = new File(baseDir, "responses");
            //   LogUtils.e("网络缓存路径",httpCacheDirectory.getAbsolutePath());
            //设置缓存 10M
            Cache cache = new Cache(httpCacheDirectory, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
            //创建OkHttpClient，并添加拦截器和缓存代码
            client = new OkHttpClient.Builder()
                    ./*addNetwork*/addInterceptor(interceptor)
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .readTimeout(5000, TimeUnit.MILLISECONDS)
                    .writeTimeout(5000, TimeUnit.MILLISECONDS)
                    .cache(cache)
                    .build();

        }
        return client;
    }

    /**
     * 拦截器(离线可以缓存，在线就获取最新数据)
     */
    Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!flagToNet) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response response = chain.proceed(request);
         if(flagToNet){
             Response response2 = chain.proceed(request);
//             LogCatUtil.log("有网数据解析--"+request+"    "+  response2.body().string());
            }

            if (flagToNet) {
                int maxAge = 60 * 60; // 有网络时 设置缓存超时时间0个小时  0*60
                response = response.newBuilder()
                        .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;

        }
    };
}