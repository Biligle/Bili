package com.shanshan.housekeeper.login;

import android.content.Context;

import com.shanshan.housekeeper.Help.consts.Tip;
import com.shanshan.housekeeper.Help.utils.CommonUtil;
import com.shanshan.housekeeper.interfaces.IPublic;
import com.shanshan.housekeeper.interfaces.IResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 登录接口的实现（网络层从P层隔离）
 * Created by wgl.
 */
public class NetLogin implements ILogin {

    private Context context;
    private IResponse IResponseListener;

    public NetLogin(Context context, IResponse IResponseListener){
        this.context = context;
        this.IResponseListener = IResponseListener;
    }


    @Override
    public void tonetLogin(IPublic iPublic, String phone, String password, boolean flagDialog, String dialogStr) {
        Call<MLogin> call = null;
        try {
            call = iPublic.login(phone,password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (flagDialog){
            if(CommonUtil.isNull(dialogStr)){
                dialogStr = Tip.PROGRESS;
            }
            CommonUtil.startProgressDialog(context, dialogStr);
        }
        if(null != call){
            call.enqueue(new Callback<MLogin>() {
                @Override
                public void onResponse(Response<MLogin> response) {
                    if (response.isSuccess()) {
                        if (response.body() != null) {
                            if(response.body().retCode){//成功
                                IResponseListener.onSuccess(response.body());
                            }else{
                                if ( response.body().retMsg != null) {//失败，且返回了失败原因
                                    IResponseListener.onFailure(response.body().retMsg);
                                } else {//失败，没有返回失败原因
                                    IResponseListener.onFailure(Tip.ERROR);
                                }

                            }

                        }

                    } else {
                        if (response.body() != null && response.body().retMsg != null) {
                            IResponseListener.onFailure(response.body().retMsg);

                        } else {
                            IResponseListener.onFailure(Tip.ERROR);
                        }

                    }
                    CommonUtil.stopProgressDialog();
                }

                @Override
                public void onFailure(Throwable t) {
                    IResponseListener.onFailure(Tip.ERROR);
                    t.printStackTrace();
                    CommonUtil.stopProgressDialog();
                }
            });
        }

    }

}
