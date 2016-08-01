package com.shanshan.housekeeper.picture;

import android.content.Context;

import com.shanshan.housekeeper.Help.consts.Tip;
import com.shanshan.housekeeper.Help.utils.CommonUtil;
import com.shanshan.housekeeper.Help.utils.LogCatUtil;
import com.shanshan.housekeeper.interfaces.IPublic;
import com.shanshan.housekeeper.interfaces.IResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 实现图片接口
 * Created by wgl.
 */
public class NetPicture implements IPicture {

    private Context context;
    private IResponse IResponseListener;

    public NetPicture(Context context, IResponse IResponseListener){
        this.context = context;
        this.IResponseListener = IResponseListener;
    }

    @Override
    public void tonetPicture(IPublic iPublic, boolean flagDialog, String dialogStr) {
        Call<MPicture> call = null;
        try {
            call = iPublic.getPicture();
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
            call.enqueue(new Callback<MPicture>() {
                @Override
                public void onResponse(Response<MPicture> response) {
                    if (response.isSuccess()) {
                        if (response.body() != null) {
                            LogCatUtil.log(response.body().indexPicture.size()+"");
                            if(response.body().indexPicture.size()>0){
                                IResponseListener.onSuccess(response.body());
                            }else{
                                IResponseListener.onFailure(Tip.ERROR);

                            }
                        }

                    } else {
                        IResponseListener.onFailure(Tip.ERROR);

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
