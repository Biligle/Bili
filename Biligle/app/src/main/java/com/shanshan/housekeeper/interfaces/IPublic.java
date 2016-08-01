package com.shanshan.housekeeper.interfaces;

import com.shanshan.housekeeper.login.MLogin;
import com.shanshan.housekeeper.picture.MPicture;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by wgl.
 * 所有网络层接口定义
 */
public interface IPublic {

    @POST("AppLogin/login")
    Call<MLogin> login(@Query("phone") String phone, @Query("password") String password);

    @GET("appBulkLoan/toIndex")
    Call<MPicture> getPicture();

    /**
     * @param toIndex 待定url
     * @param map
     * @return
     */
    @GET("appBulkLoan/{toIndex}")
    Call<MLogin> login3(@Path("toIndex") String toIndex, @QueryMap Map<String, Object> map);

    @GET("这里写url后缀儿")
    Call<MLogin> login4(@Query("userName") String userName, @Query("password") String password);

    @GET("这里写url后缀儿")
    Call<MLogin> login5(@Query("userName") String userName, @Query("password") String password);

    @GET("这里写url后缀儿")
    Call<MLogin> login6(@Query("userName") String userName, @Query("password") String password);

    @GET("这里写url后缀儿")
    Call<MLogin> login7(@Query("userName") String userName, @Query("password") String password);

    @GET("这里写url后缀儿")
    Call<MLogin> login8(@Query("userName") String userName, @Query("password") String password);

    @GET("这里写url后缀儿")
    Call<MLogin> login9(@Query("userName") String userName, @Query("password") String password);

    @GET("这里写url后缀儿")
    Call<MLogin> login10(@Query("userName") String userName, @Query("password") String password);

    @GET("这里写url后缀儿")
    Call<MLogin> login11(@Query("userName") String userName, @Query("password") String password);
}
