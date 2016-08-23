package com.wgl.mvp.selectPicture;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wgl.
 */
public interface ISelect {
//    /**
//     * 获取所有图片文件夹
//     * @param pictureList
//     */
//    void getPicture(List<ImageFloder> pictureList);
    /**
     * 获取所有图片
     * @param pictureList
     */
    void getPicture(ArrayList<String> pictureList,ArrayList<String> firstPictureList);

}
