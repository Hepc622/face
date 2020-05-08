package com.sgkj.face.config;

import com.baidu.aip.face.AipFace;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @Author HPC
 * @Description 百度人脸识别配置
 * @Date 2019/7/8 16:18
 */
@Configuration
public class BaiDuFaceConfig {
    /**
     * 百度应用appKey
     */
    @Value("${baidu.face.appKey}")
    private String appKey;
    /**
     * 百度应用appId
     */
    @Value("${baidu.face.appId}")
    private String appId;
    /**
     * 百度应用secretkey
     */
    @Value("${baidu.face.secretKey}")
    private String secretKey;

    /**
     * @return  AipFace 人脸识别Client
     * @author : HPC
     * @description : 创建人脸识别Client
     * @date : 2019/7/8 17:53
     */
    @Bean
    @Primary
    public AipFace aipFaceClient(){
        AipFace client = new AipFace(appId, appKey, secretKey);
//        建立连接的超时时间（单位：毫秒）
        client.setConnectionTimeoutInMillis(2000);
//        通过打开的连接传输数据的超时时间（单位：毫秒）
        client.setSocketTimeoutInMillis(60000);
        return client;
    }

}
