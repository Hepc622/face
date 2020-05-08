package com.sgkj.face.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.FaceVerifyRequest;
import com.baidu.aip.face.MatchRequest;
import com.sgkj.face.common.constants.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author : HPC
 * @description : 人脸识别工具类
 * @date : 2019/7/9 8:47
 */
public class FaceUtils {

    /**
     * @author : HPC
     * @description : 人脸检测
     * @date : 2019/7/8 9:54
     */
    public static JSONObject detect(AipFace client, JSONObject json) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<>(json.size());
        String image = json.getString("image");
        String imageType = ImageType.BASE64;

        /*有值就覆盖默认值*/

        /*获取人脸类型字段*/
        boolean face_field = json.containsKey("face_field");
        if (face_field) {
            options.put("face_field", json.getString("face_field"));
        }
        boolean max_face_num = json.containsKey("max_face_num");
        if (max_face_num) {
            options.put("max_face_num", json.getString("max_face_num"));
        }
        boolean face_type = json.containsKey("face_type");
        if (face_type) {
            options.put("face_type", json.getString("face_type"));
        }
        boolean liveness_control = json.containsKey("liveness_control");
        if (liveness_control) {
            options.put("liveness_control", json.getString("liveness_control"));
        }
        boolean image_type = json.containsKey("image_type");
        if (image_type) {
            imageType = json.getString("image_type");
        }

        // 人脸检测
        org.json.JSONObject res = client.detect(image, imageType, options);
        System.out.println(res.toString(2));
        return JSONObject.parseObject(res.toString());
    }

    /**
     * @author : HPC
     * @description : 人脸搜索
     * @date : 2019/7/8 9:56
     */
    public static JSONObject search(AipFace client, JSONObject json) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<>(json.size());

        /*默认值*/
        String image = json.getString("image");
        String imageType = ImageType.BASE64;
        String groupIdList = "default";

        /*更改默认的图片类别*/
        boolean liveness_control = json.containsKey("liveness_control");
        if (liveness_control) {
            options.put("liveness_control", json.getString("liveness_control"));
        }

        /*更改默认的图片类别*/
        boolean image_type = json.containsKey("image_type");
        if (image_type) {
            imageType = json.getString("image_type");
        }

        /*更改默认的图片质量要求*/
        boolean quality_control = json.containsKey("quality_control");
        if (quality_control) {
            options.put("quality_control", json.getString("quality_control"));
        }

        /*当需要对特定用户进行比对时，指定user_id进行比对。即人脸认证功能。*/
        boolean user_id = json.containsKey("user_id");
        if (user_id) {
            options.put("user_id", json.getString("user_id"));
        }

        /*返回的最大人脸数据量默认为1个*/
        boolean max_user_num = json.containsKey("max_user_num");
        if (max_user_num) {
            options.put("max_user_num", json.getString("max_user_num"));
        }

        /*进行搜索的组ID*/
        boolean group_id_list = json.containsKey("group_id_list");
        if (group_id_list) {
            groupIdList = json.getString("group_id_list");
        }

        // 人脸搜索
        org.json.JSONObject res = client.search(image, imageType, groupIdList, options);
        return JSONObject.parseObject(res.toString());

    }

    /**
     * @author : HPC
     * @description : 添加人脸库
     * @date : 2019/7/8 9:49
     */
    public static JSONObject add(AipFace client, JSONObject json) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<>(json.size());
        options.put("user_info", "user's info");
        options.put("quality_control", QualityType.NORMAL);
        options.put("liveness_control", LivenessType.NONE);
        options.put("action_type", ActionType.REPLACE);

        String userId = json.getString("user_id");
        String image = json.getString("image");
        String imageType = ImageType.BASE64;
        String groupId = "default";

        /*更改默认的图片类别*/
        boolean liveness_control = json.containsKey("liveness_control");
        if (liveness_control) {
            options.put("liveness_control", json.getString("liveness_control"));
        }

        /*更改默认的图片类别*/
        boolean image_type = json.containsKey("image_type");
        if (image_type) {
            imageType = json.getString("image_type");
        }

        /*更改默认的图片质量要求*/
        boolean quality_control = json.containsKey("quality_control");
        if (quality_control) {
            options.put("quality_control", json.getString("quality_control"));
        }

        /*当需要对特定用户进行比对时，指定user_id进行比对。即人脸认证功能。*/
        boolean user_id = json.containsKey("user_id");
        if (user_id) {
            options.put("user_id", json.getString("user_id"));
        }

        /*替换动作*/
        boolean action_type = json.containsKey("action_type");
        if (action_type) {
            options.put("action_type", json.getString("action_type"));
        }


        /*添加到指定群组ID*/
        boolean group_id = json.containsKey("group_id");
        if (group_id) {
            groupId = json.getString("group_id");
        }

        /*用户的其他信息*/
        boolean user_info = json.containsKey("user_info");
        if (user_info) {
            options.put("user_info", json.getString("user_info"));
        }

        // 人脸注册
        org.json.JSONObject res = client.addUser(image, imageType, groupId, userId, options);
        System.out.println(res.toString(2));
        return JSONObject.parseObject(res.toString());
    }

    /**
     * @return 返回face_token和对应的人脸特征数据
     * @author : HPC
     * @description : 更新人脸数据
     * @date : 2019/7/9 10:01
     */
    public static JSONObject update(AipFace client, JSONObject json) {
        json.put("action_type", ActionType.REPLACE);
        return add(client, json);
    }

    /**
     * @return 返回操作结果数据
     * @author : HPC
     * @description :  删除一个人脸数据
     * @date : 2019/7/9 9:56
     */
    public static JSONObject delete(AipFace client, JSONObject json) {
        /*默认初始值*/
        String groupId = "default";
        String user_id = json.getString("user_id");
        /*覆盖默认值*/
        boolean group_id = json.containsKey("group_id");
        if (group_id) {
            groupId = json.getString("group_id");
        }
        HashMap<String, String> options = new HashMap<>(json.size());
        options.put("face_token", json.getString("face_token"));
        org.json.JSONObject res = client.deleteUser(groupId, user_id, options);
        return JSONObject.parseObject(res.toString());
    }

    /**
     * @return 返回比较数据
     * @author : HPC
     * @description :
     * @date : 2019/7/9 11:35
     */
    public static JSONObject match(AipFace client, JSONObject json) {
        List<MatchRequest> list = new ArrayList<>();
        JSONArray images = json.getJSONArray("images");
        for (int i = 0; i < images.size(); i++) {
            JSONObject object = images.getJSONObject(i);
            /*初始化默认值*/
            /*获取图片*/
            String image = object.getString("image");

            /*设置图片类别*/
            String image_type = ImageType.BASE64;
            /*设置人脸照片类别*/
            String face_type = FaceType.LIVE;
            /*设置图片质量类别*/
            String quality_control = QualityType.NONE;
            /*设置活体类别*/
            String liveness_control = LivenessType.NONE;

            /*覆盖默认值*/
            boolean faceType = object.containsKey("face_type");
            if (faceType) {
                face_type = object.getString("face_type");
            }

            boolean qualityControl = object.containsKey("quality_control");
            if (qualityControl) {
                quality_control = object.getString("quality_control");
            }


            boolean livenessControl = object.containsKey("liveness_control");
            if (livenessControl) {
                quality_control = object.getString("liveness_control");
            }

            boolean imageType = object.containsKey("image_type");
            if (imageType) {
                image_type = object.getString("image_type");
            }

            MatchRequest matchRequest = new MatchRequest(image, image_type, face_type, quality_control, liveness_control);
            list.add(matchRequest);
        }
        org.json.JSONObject match = client.match(list);
        return JSONObject.parseObject(match.toString());
    }

    /**
     * @author : HPC
     * @description : 在线活体检测
     * @date : 2019/7/10 8:45
     */
    public static JSONObject faceverify(AipFace client, JSONObject json) {
        /*获取活体图片数据*/
        JSONArray images = json.getJSONArray("images");

        /*组装活体请求数据体*/
        List<FaceVerifyRequest> list = new ArrayList<>(images.size());
        for (int i = 0; i < images.size(); i++) {
            /*获取单个图片数据*/
            JSONObject object = images.getJSONObject(i);

            /*设置默认的图片类别值，默认为base64*/
            String imageType = ImageType.BASE64;
            String faceField = "quality,angel,face_token";

            /*获取图片*/
            String image = object.getString("image");
            /*覆盖默认图片类别*/
            if (object.containsKey("image_type")) {
                imageType = object.getString("image_type");
            }

            /*覆盖默认返回字段数据*/
            if (object.containsKey("face_field")) {
                faceField = object.getString("face_field");
            }
            /*创建检测活体对象*/
            FaceVerifyRequest faceVerifyRequest = new FaceVerifyRequest(image, imageType, faceField);
            /*添加到获取检测请求数据list中*/
            list.add(faceVerifyRequest);
        }
        org.json.JSONObject res = client.faceverify(list);
        return JSONObject.parseObject(res.toString());
    }
}