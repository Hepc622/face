package com.sgkj.face.controller;

import com.alibaba.fastjson.JSONObject;
import com.sgkj.face.common.dto.Result;
import com.sgkj.face.service.IFaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author HPC
 * @Description faceController
 * @Date 2019/7/8 16:32
 */
@RestController
@RequestMapping("/face")
public class FaceController {

    @Autowired
    private IFaceService iFaceService;

    /**
     * @return 返回人脸对应的数据
     * @author : HPC
     * @description :  检测人脸
     * @date : 2019/7/8 16:37
     */
    @RequestMapping(value = "/faceDetect", method = {RequestMethod.POST})
    public Result<JSONObject> faceDetect(@RequestBody JSONObject json) {
        return iFaceService.faceDetect(json);
    }


    /**
     * @return 返回比较数据以及facetoken
     * @author : HPC
     * @description : 比较两张图片是否一致
     * @date : 2019/7/8 17:01
     */
    @RequestMapping(value = "/faceMatch", method = {RequestMethod.POST})
    public Result<JSONObject> faceMatch(@RequestBody JSONObject json) {
        return iFaceService.faceMatch(json);
    }


    /**
     * @return json字符串数据
     * @author : HPC
     * @description :  返回人脸库搜索出的人脸信息数据
     * @date : 2019/7/8 17:04
     */
    @RequestMapping(value = "/faceSearch", method = {RequestMethod.POST})
    public Result<JSONObject> faceSearch(@RequestBody JSONObject json) {
        return iFaceService.faceSearch(json);
    }

    /**
     * @return 字符串信息
     * @author : HPC
     * @description :  添加人脸数据到百度
     * @date : 2019/7/8 17:41
     */
    @RequestMapping(value = "/registerFace", method = {RequestMethod.POST})
    public Result<JSONObject> registerFace(@RequestBody JSONObject json) {
        return iFaceService.registerFace(json);
    }

    /**
     * @author : HPC
     * @description :  更新数据库人脸数据
     * @date : 2019/7/8 17:42
     */
    @RequestMapping(value = "/updateFace", method = {RequestMethod.POST})
    public Result<JSONObject> updateFace(@RequestBody JSONObject json) {
        return iFaceService.updateFace(json);
    }

    /**
     * @author : HPC
     * @description : 删除人脸数据
     * @date : 2019/7/8 17:43
     */
    @RequestMapping(value = "/deleteFace", method = {RequestMethod.POST})
    public Result<JSONObject> deleteFace(@RequestBody JSONObject json) {
        return iFaceService.deleteFace(json);
    }

    /**
     * @author : HPC
     * @description : 在线活体检测
     * @date : 2019/7/8 17:43
     */
    @RequestMapping(value = "/faceVerify", method = {RequestMethod.POST})
    public Result<JSONObject> faceVerify(@RequestBody JSONObject json) {
        return iFaceService.faceVerify(json);
    }
}
