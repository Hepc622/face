package com.sgkj.face.common.constants;

/**
 * @Author HPC
 * @Description 图片类别
 * @Date 2019/7/8 16:49
 */
public class ImageType {
    /**
     * 图片的base64值，base64编码后的图片数据，编码后的图片大小不超过2M；
     */
    public static final String BASE64 = "BASE64";
    /**
     * 图片的 URL地址( 可能由于网络等原因导致下载图片时间过长)；
     */
    public static final String URL = "URL";
    /**
     * 人脸图片的唯一标识，调用人脸检测接口时，会为每个人脸图片赋予一个唯一的FACE_TOKEN，同一张图片多次检测得到的FACE_TOKEN是同一个。
     */
    public static final String FACE_TOKEN = "FACE_TOKEN";
}
