package com.sgkj.face.common.dto;

/**
 * 描述:
 * 响应码
 *
 * @author HPC
 * @create 2019-01-31
 */
public class Code {
    /**
     * 0开头的是请求成功
     */
    public static final String SUCCESS = "0000";
    /**
     * 1开头的请求失败
     */
    public static final String FAIL = "1000";

    /**
     * 2开头的参数错误
     */
    public static final String PARAM = "2000";


    /**
     * 修改密码成功
     */
    public static final String UPDATE_PW_SUCCESS = "0001";

    /**
     * token错误
     */
    public static final String TOKEN_ERR = "1001";

    /**
     * 登陆错误，密码或者账号不存在
     */
    public static final String LOGIN_PWD_OR_CONUNT_ERR = "1002";

    /**
     * 没有操作权限
     */
    public static final String NO_RIGHT = "1003";

    /**
     * 该人不是活体检测
     */
    public static final String NO_LIVE = "1004";
    /**
     * 该人是活体检测
     */
    public static final String LIVE = "1005";

    /**
     * 缺少图片参数
     */
    public static final String PARAM_IMAGE = "2001";

    /**
     * 缺失userId
     */
    public static final String PARAM_USER_ID = "2002";

    /**
     * 缺失face_token 参数
     */
    public static final String PARAM_FACE_TOKEN = "2003";
}
