package com.sgkj.face.common.constants;

/**
 * @Author HPC
 * @Description 活体检测
 * @Date 2019/7/8 16:56
 */
public class LivenessType {
    /**
     * 不进行控制
     */
    public static final String NONE = "NONE";
    /**
     * 较低的活体要求(高通过率 低攻击拒绝率)
     */
    public static final String LOW = "LOW";
    /**
     * 一般的活体要求(平衡的攻击拒绝率, 通过率)
     */
    public static final String NORMAL = "NORMAL";
    /**
     * 较高的活体要求(高攻击拒绝率 低通过率)
     */
    public static final String HIGH = "HIGH";
}
