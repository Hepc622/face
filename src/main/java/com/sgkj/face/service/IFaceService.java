package com.sgkj.face.service;

import com.alibaba.fastjson.JSONObject;
import com.sgkj.face.common.dto.Result;

import javax.jws.WebMethod;

/**
 * @Author HPC
 * @Description faceService
 * @Date 2019/7/8 16:34
 */
public interface IFaceService {
    /**
     * @return 返回人脸对应的数据
     * @author : HPC
     * @description :  检测人脸
     * @date : 2019/7/8 16:37
     */
    @WebMethod
    Result<JSONObject> faceDetect(JSONObject json);


    /**
     * @return 返回比较数据以及facetoken
     * @author : HPC
     * @description : 比较两张图片是否一致
     * @date : 2019/7/8 17:01
     */
    @WebMethod
    Result<JSONObject> faceMatch(JSONObject json);


    /**
     * @return json字符串数据
     * @author : HPC
     * @description :  返回人脸库搜索出的人脸信息数据
     * @date : 2019/7/8 17:04
     */
    @WebMethod
    Result<JSONObject> faceSearch(JSONObject json);

    /**
     * @return 字符串信息
     * @author : HPC
     * @description :  添加人脸数据到百度
     * @date : 2019/7/8 17:41
     */
    @WebMethod
    Result<JSONObject> registerFace(JSONObject json);


    /**
     * @author : HPC
     * @description :  更新数据库人脸数据
     * @date : 2019/7/8 17:42
     */
    @WebMethod
    Result<JSONObject> updateFace(JSONObject json);

    /**
     * @author : HPC
     * @description : 删除人脸数据
     * @date : 2019/7/8 17:43
     */
    @WebMethod
    Result<JSONObject> deleteFace(JSONObject json);

    /**
     * @author : HPC
     * @description : 在线活体检测
     * @date : 2019/7/10 9:01
     */
    @WebMethod
    Result<JSONObject> faceVerify(JSONObject json);
}
