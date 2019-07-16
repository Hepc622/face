package com.sgkj.face;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author HPC
 * @Description 人脸识别服务
 * @Date 2019/7/8 16:14
 */
@SpringBootApplication
public class FaceApplication {
    public static void main(String[] args) {
        SpringApplication.run(FaceApplication.class, args);

        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
    }
}
