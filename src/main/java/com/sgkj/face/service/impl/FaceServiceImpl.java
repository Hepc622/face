package com.sgkj.face.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.face.AipFace;
import com.sgkj.face.common.dto.Code;
import com.sgkj.face.common.dto.Result;
import com.sgkj.face.service.IFaceService;
import com.sgkj.face.utils.FaceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Author HPC
 * @Description 人脸搜索对应api服务
 * @Date 2019/7/8 17:05
 */
@Slf4j
@Service
public class FaceServiceImpl implements IFaceService {

    private final String SUCCESS = "SUCCESS";

    /**
     * 匹配度多少算合格
     */
    @Value("${face.matchScore}")
    private int matchScore;

    /**
     * 身份验证闸值
     */
    @Value("${face.verifyScore}")
    private int verifyScore;


    @Autowired
    private AipFace client;

    /**
     * @return 返回人脸对应的数据
     * @author : HPC
     * @description :  检测人脸
     * @date : 2019/7/8 16:37
     */
    @Override
    public Result<JSONObject> faceDetect(JSONObject json) {
        boolean image = json.containsKey("image");
        if (!image) {
            return Result.fail(Code.PARAM_IMAGE, "请求参数缺失【image】字段");
        }
        JSONObject detect = FaceUtils.detect(client, json);
        String error_msg = detect.getString("error_msg");
        if (SUCCESS.equals(error_msg)) {
            return Result.success(detect.getJSONObject("result"));
        } else {
            return Result.fail(error_msg);
        }
    }

    /**
     * @return 返回比较数据以及facetoken
     * @author : HPC
     * @description : 比较两张图片是否一致
     * @date : 2019/7/8 17:01
     */
    @Override
    public Result<JSONObject> faceMatch(JSONObject json) {
        boolean images = json.containsKey("images");
        if (!images) {
            return Result.fail(Code.PARAM_IMAGE, "请求参数缺失【image】字段");
        }
        JSONObject match = FaceUtils.match(client, json);
        String error_msg = match.getString("error_msg");
        if (SUCCESS.equals(error_msg)) {
            JSONObject result = match.getJSONObject("result");
            Integer score = result.getInteger("score");
            if (matchScore > score) {
                return Result.fail("两张图片的人物相似度低于: " + score + " 认为不是同一个人");
            } else {
                return Result.success("人物相似度为: " + score + " 认为是同一个人");
            }
        } else {
            return Result.fail(error_msg);
        }
    }

    /**
     * @return json字符串数据
     * @author : HPC
     * @description :  返回人脸库搜索出的人脸信息数据
     * @date : 2019/7/8 17:04
     */
    @Override
    public Result<JSONObject> faceSearch(JSONObject json) {
        /*判断请求必要参数*/
        boolean images = json.containsKey("image");
        if (!images) {
            return Result.fail(Code.PARAM_IMAGE, "请求参数缺失【image】字段");
        }

        /*搜索人脸数据*/
        JSONObject search = FaceUtils.search(client, json);
        String error_msg = search.getString("error_msg");
        /*判断是否请求成功*/
        if (SUCCESS.equals(error_msg)) {
            /*获取返回结果及数据*/
            JSONObject result = search.getJSONObject("result");
            /*获取匹配的用户人脸数据*/
            JSONArray user_list = result.getJSONArray("user_list");
            /*循环对比把大于90的数据找出来，就是目标人脸数据*/
            for (int i = 0; i < user_list.size(); i++) {
                JSONObject jsonObject = user_list.getJSONObject(i);
                /*获取对比分数*/
                Integer score = jsonObject.getInteger("score");
                /*如果达到指定分数标识合格返回该数据*/
                if (matchScore <= score) {
                    return Result.success(jsonObject);
                }
            }
            return Result.fail("人脸信息与人脸库信息不符合");
        } else {
            return Result.fail(error_msg);
        }
    }

    /**
     * @return 字符串信息
     * @author : HPC
     * @description :  添加人脸数据到百度
     * @date : 2019/7/8 17:41
     */
    @Override
    public Result<JSONObject> registerFace(JSONObject json) {
        log.info("register face in params user_id={}", json.getString("user_id"));
        /*判断请求必要参数*/
        boolean images = json.containsKey("image");
        if (!images) {
            log.info("register face 请求参数缺失【image】字段");
            return Result.failParam("请求参数缺失【image】字段");
        }
        boolean userId = json.containsKey("user_id");
        if (!userId) {
            log.info("register face 请求参数缺失【user_id】字段");
            return Result.failParam("请求参数缺失【user_id】字段");
        } else {
            //身份证|姓名|性别(1男2女)|出生日期(yyyy-mm-dd)|手机号码|地址|
            String user_id = json.getString("user_id");
            if (user_id.contains("|")) {
                String[] split = user_id.split("\\|");
                json.put("user_info", user_id);
                json.put("user_id", split[0]);
            } else {
                log.info("register face 请求参数【user_id】字段 格式不正确");
                return Result.failParam("请求参数【user_id】字段格式不正确！正确格式 身份证|姓名|性别(1男2女)|出生日期(yyyy-mm-dd)|手机号码|地址| ");
            }
        }

        /*添加人脸数据*/
        JSONObject res = FaceUtils.add(client, json);
        String error_msg = res.getString("error_msg");
        if (SUCCESS.equals(error_msg)) {
            log.info("register face success");
            return Result.success();
        } else {
            log.info("register face fail" + error_msg);
            return Result.fail(error_msg);
        }
    }

    /**
     * @author : HPC
     * @description :  更新数据库人脸数据
     * @date : 2019/7/8 17:42
     */
    @Override
    public Result<JSONObject> updateFace(JSONObject json) {
        /*判断请求必要参数*/
        boolean images = json.containsKey("image");
        if (!images) {
            return Result.fail(Code.PARAM_IMAGE, "请求参数缺失【image】字段");
        }
        boolean userId = json.containsKey("user_id");
        if (!userId) {
            return Result.fail(Code.PARAM_USER_ID, "请求参数缺失【user_id】字段");
        }

        /*更新人脸数据*/
        JSONObject res = FaceUtils.update(client, json);
        String error_msg = res.getString("error_msg");
        if (SUCCESS.equals(error_msg)) {
            return Result.success(res.getJSONObject("result"));
        } else {
            return Result.fail(error_msg);
        }
    }

    /**
     * @author : HPC
     * @description : 删除人脸数据
     * @date : 2019/7/8 17:43
     */
    @Override
    public Result<JSONObject> deleteFace(JSONObject json) {
        /*判断请求必要参数*/
        boolean userId = json.containsKey("user_id");
        if (!userId) {
            return Result.fail(Code.PARAM_USER_ID, "请求参数缺失【user_id】字段");
        }
        boolean faceToken = json.containsKey("face_token");
        if (!faceToken) {
            return Result.fail(Code.PARAM_FACE_TOKEN, "请求参数缺失【face_token】字段");
        }
        /*删除人脸数据*/
        JSONObject res = FaceUtils.delete(client, json);
        String error_msg = res.getString("error_msg");
        if (SUCCESS.equals(error_msg)) {
            return Result.success(res.getJSONObject("result"));
        } else {
            return Result.fail(error_msg);
        }
    }

    /**
     * @author : HPC
     * @description : 在线活体检测
     * @date : 2019/7/10 9:01
     */
    @Override
    public Result<JSONObject> faceVerify(JSONObject json) {
        /*判断必要参数*/
        boolean images = json.containsKey("images");
        if (!images) {
            return Result.fail(Code.PARAM_USER_ID, "请求参数缺失【images】字段");
        }
        /*请求百度活体测试*/
        JSONObject res = FaceUtils.faceVerify(client, json);

        /*判断是否请求成功了*/
        String error_msg = res.getString("error_msg");
        if (SUCCESS.equals(error_msg)) {
            JSONObject result = res.getJSONObject("result");
            /*活体分数值*/
            Float face_liveness = result.getFloat("face_liveness");
            /*活体闸值*/
            JSONObject thresholds = result.getJSONObject("thresholds");
            /*循环判断图片质量*/
            /*每张图片的返回数据*/
            /*原因*/
            String reason = "";
            /*返回code*/
            String code = "";
            JSONArray face_list = result.getJSONArray("face_list");
            for (int i = 0; i < face_list.size(); i++) {
                JSONObject face = face_list.getJSONObject(i);
                /*判断是否为活体*/
                Float frr_le_3 = thresholds.getFloat("frr_1e-3");
                if (face_liveness < frr_le_3) {
                    code = Code.NO_LIVE;
                } else {
                    code = Code.LIVE;
                }
                /*判断质量是否有问题*/
                JSONObject quality = face.getJSONObject("quality");
                /*人脸各部分遮挡的概率*/
                JSONObject occlusion = quality.getJSONObject("occlusion");
                /*左眼被遮挡的阈值 0.6*/
                Float left_eye = occlusion.getFloat("left_eye");
                if (left_eye > 0.6) {
                    reason = "左眼被遮挡";
                    break;
                }

                /*右眼被遮挡的阈值 0.6*/
                Float right_eye = occlusion.getFloat("right_eye");
                if (right_eye > 0.6) {
                    reason = "右眼被遮挡";
                    break;
                }

                /*鼻子被遮挡的阈值 0.7*/
                Float nose = occlusion.getFloat("nose");
                if (nose > 0.7) {
                    reason = "鼻子被遮挡";
                    break;
                }

                /*嘴巴被遮挡的阈值 0.7*/
                Float mouth = occlusion.getFloat("mouth");
                if (mouth > 0.7) {
                    reason = "嘴巴被遮挡";
                    break;
                }

                /*左脸颊被遮挡的阈值 0.8*/
                Float left_check = occlusion.getFloat("left_cheek");
                if (left_check > 0.8) {
                    reason = "左脸颊被遮挡";
                    break;
                }
                /*右脸颊被遮挡的阈值 0.8*/
                Float right_check = occlusion.getFloat("right_cheek");
                if (right_check > 0.8) {
                    reason = "右脸颊被遮挡";
                    break;
                }
                /*下巴被遮挡阈值 0.6*/
                Float chin_contour = occlusion.getFloat("chin_contour");
                if (chin_contour > 0.6) {
                    reason = "下巴被遮挡";
                    break;
                }

                /*图片模糊度范围 小于0.7  取值范围[0~1]，0是最清晰，1是最模糊*/
                Float blur = quality.getFloat("blur");
                if (blur >= 0.7f) {
                    reason = "图片过于模糊";
                    break;
                }

                /*
                照片光照 大于40
                取值范围[0~255]
                脸部光照的灰度值，0表示光照不好
                以及对应客户端SDK中，YUV的Y分量
                */
                Integer illumination = quality.getInteger("illumination");
                if (illumination < 40) {
                    reason = "光线不足";
                    break;
                }

                /*0为人脸溢出图像边界，1为人脸都在图像边界内*/
                Integer completeness = quality.getInteger("completeness");
                if (completeness == 0) {
                    reason = "人脸溢出图像边界";
                    break;
                }

                /*判断人脸空间姿态角是否有问题*/
                /*
                Pitch：三维旋转之俯仰角度，范围：[-90（上）, 90（下）]，推荐俯仰角绝对值不大于20度；
                Roll：平面内旋转角，范围：[-180（逆时针）, 180（顺时针）]，推荐旋转角绝对值不大于20度；
                Yaw：三维旋转之左右旋转角，范围：[-90（左）, 90（右）]，推荐旋转角绝对值不大于20度；
                */
                JSONObject angel = face.getJSONObject("angle");
                /*左右角度*/
                Double yaw = angel.getDouble("yaw");
                yaw = Math.abs(yaw);
                if (yaw > 20) {
                    reason = "头部过于向左偏或向右偏";
                    break;
                }
                /*上下角度*/
                Double pitch = angel.getDouble("pitch");
                pitch = Math.abs(pitch);
                if (pitch > 20) {
                    reason = "头部过于向上抬或向下低";
                    break;
                }
                /*平面角度*/
                Double roll = angel.getDouble("roll");
                roll = Math.abs(roll);
                if (roll > 20) {
                    reason = "头部歪了";
                    break;
                }
            }
            JSONObject jsonObject = null;
            if (code.equals(Code.LIVE) && "".equals(reason)) {
                jsonObject = new JSONObject();
                JSONObject object = face_list.getJSONObject(0);
                String face_token = object.getString("face_token");
                jsonObject.put("face_token", face_token);
            }
            return Result.success(code, reason, jsonObject);
        } else {
            return Result.fail(error_msg);
        }
    }

    /**
     * @author : HPC
     * @date : Created in 2020/5/29
     * @description : 验证身份信息与人脸是否一致
     */
    @Override
    public Result<JSONObject> personVerify(JSONObject json) {
        /*判断必要参数*/
        if (StringUtils.isEmpty(json.getString("images"))) {
            return Result.fail(Code.PARAM_IMAGE, "请求参数缺失【images】字段");
        }
        if (StringUtils.isEmpty(json.getString("id_card_number"))) {
            return Result.fail(Code.PARAM_ID_CARD, "请求参数缺失【id_card_number】字段");
        }
        if (StringUtils.isEmpty(json.getString("name"))) {
            return Result.fail(Code.PARAM_USER_NAME, "请求参数缺失【name】字段");
        }
        /*请求百度活体测试*/
        JSONObject res = FaceUtils.personVerify(client, json);
        if (res != null) {
            JSONObject result = res.getJSONObject("result");
            float score = result.getFloatValue("score");
            if (score >= verifyScore) {
                return Result.success("身份证信息与人脸信息是同一个人!");
            } else {
                return Result.fail("身份证与人脸信息不是同一个人!");
            }
        } else {
            return Result.fail("发起身份证与人脸信息验证失败,请重试!");
        }
    }
}
