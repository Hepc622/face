package com.sgkj.face.config;

import com.sgkj.face.webservice.IWebServiceFaceService;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * create by: HPC
 * description: webService 配置 默认发布在ip:port/services/**
 * create time: 2019/7/16
 */
@Configuration
public class CxfConfig {
    @Autowired
    private Bus bus;

    @Autowired
    private IWebServiceFaceService iWebServiceFaceService;

    /** JAX-WS **/
    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, iWebServiceFaceService);
        endpoint.publish("/face");
        return endpoint;
    }
}