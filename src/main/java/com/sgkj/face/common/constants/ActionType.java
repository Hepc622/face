package com.sgkj.face.common.constants;

/**
 * @Author HPC
 * @Description 人脸数据库操作type
 * @Date 2019/7/8 17:35
 */
public class ActionType {
    /**
     * 当user_id在库中已经存在时，对此user_id重复注册时，新注册的图片默认会追加到该user_id下
     */
    public static final String APPEND = "APPEND";
    /**
     * 当对此user_id重复注册时,则会用新图替换库中该user_id下所有图片
     * 默认使用APPEND
     */
    public static final String REPLACE = "REPLACE";
}
