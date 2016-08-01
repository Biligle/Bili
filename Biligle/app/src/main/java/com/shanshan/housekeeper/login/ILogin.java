package com.shanshan.housekeeper.login;

import com.shanshan.housekeeper.interfaces.IPublic;

/**
 * 登录界面的接口（从IPublic接口的隔离）
 * Created by wgl.
 */
public interface ILogin {
    /**
     *
     * @param iPublic 所有接口,
     * @param phone 参数：手机号
     * @param password 参数：密码
     * @param flagDialog false:不显示progressBar；true:显示
     * @param dialogStr 显示progressBar时，出现的文字。“正在缓冲”之类的提示语
     */
    void tonetLogin(IPublic iPublic, String phone, String password, boolean flagDialog, String dialogStr);
}
