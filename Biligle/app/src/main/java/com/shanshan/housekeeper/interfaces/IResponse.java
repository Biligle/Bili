package com.shanshan.housekeeper.interfaces;

import com.wgl.mvp.model.IModel;

/**
 * 成功或者失败的响应接口
 * Created by wgl.
 */
public interface IResponse {
    void onSuccess(IModel modle);
    void onFailure(String error);
}
