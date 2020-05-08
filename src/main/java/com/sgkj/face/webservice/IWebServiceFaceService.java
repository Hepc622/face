package com.sgkj.face.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * @Author HPC
 * @Description faceService
 * @Date 2019/7/8 16:34
 */
@WebService
public interface IWebServiceFaceService {
    /**
     * @return 返回人脸对应的数据
     * @author : HPC
     * @description :  检测人脸
     * @date : 2019/7/8 16:37
     */
    @WebMethod
    @WebResult(name = "String")
    String faceDetect(@WebParam(name = "data") String json);


    /**
     * @return 返回比较数据以及facetoken
     * @author : HPC
     * @description : 比较两张图片是否一致
     * @date : 2019/7/8 17:01
     */
    @WebMethod
    @WebResult(name = "String")
    String faceMatch(@WebParam(name = "data") String json);


    /**
     * @return json字符串数据
     * @author : HPC
     * @description :  返回人脸库搜索出的人脸信息数据
     * @date : 2019/7/8 17:04
     */
    @WebMethod
    @WebResult(name = "String")
    String faceSearch(@WebParam(name = "data") String json);

    /**
     * @return 字符串信息
     * @author : HPC
     * @description :  添加人脸数据到百度
     * @date : 2019/7/8 17:41
     */
    @WebMethod
    @WebResult(name = "String")
    String registerFace(@WebParam(name = "data") String json);


    /**
     * @author : HPC
     * @description :  更新数据库人脸数据
     * @date : 2019/7/8 17:42
     */
    @WebMethod
    @WebResult(name = "String")
    String updateFace(@WebParam(name = "data") String json);

    /**
     * @author : HPC
     * @description : 删除人脸数据
     * @date : 2019/7/8 17:43
     */
    @WebMethod
    @WebResult(name = "String")
    String deleteFace(@WebParam(name = "data") String json);

    /**
     * @author : HPC
     * @description : 在线活体检测
     * @date : 2019/7/10 9:01
     */
    @WebMethod
    @WebResult(name = "String")
    String faceVerify(@WebParam(name = "data") String json);
}
