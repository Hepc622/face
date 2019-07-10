package com.sgkj.face.common.constants;

/**
 * @Author HPC
 * @Description 脸型类别
 * @Date 2019/7/8 16:50
 */
public class FaceType {
    /**
     * 表示生活照：通常为手机、相机拍摄的人像图片、或从网络获取的人像图片等，
     */
    public static final String LIVE = "LIVE";
    /**
     * 表示身份证芯片照：二代身份证内置芯片中的人像照片
     */
    public static final String IDCARD = "IDCARD";
    /**
     * 表示带水印证件照：一般为带水印的小图，如公安网小图
     */
    public static final String WATERMARK = "WATERMARK";
    /**
     * 表示证件照片：如拍摄的身份证、工卡、护照、学生证等证件图片
     */
    public static final String CERT = "CERT";
}
