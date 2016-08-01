package com.shanshan.housekeeper.picture;

import com.wgl.mvp.model.IModel;

import java.util.List;

/**
 * Created by wgl.
 */
public class MPicture extends IModel{

    /**
     * 轮播图片
     */
    public List<MainPictureModle> indexPicture;//ret_list;

    public class MainPictureModle extends IModel{
        /**
         * url
         */
        public String interimPathUrl;
        /**
         * 详情 活动
         */
        public String activityUrl;
    }

}
