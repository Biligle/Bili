package com.shanshan.housekeeper.picture;

import com.shanshan.housekeeper.interfaces.IPublic;

/**
 * 图片界面的接口
 * Created by wgl.
 */
public interface IPicture {
    /**
     *
     * @param iPublic 所有接口
     * @param flagDialog false:不显示progressBar；true:显示
     * @param dialogStr 显示progressBar时，出现的文字。“正在缓冲”之类的提示语
     */
    void tonetPicture(IPublic iPublic, boolean flagDialog, String dialogStr);
}
