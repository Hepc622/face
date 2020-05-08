package com.sgkj.face.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.xml.soap.SOAPException;


import org.apache.commons.codec.binary.Base64;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.springframework.util.StringUtils;

/**
 * @author : HPC
 * @date : Created in 2020/5/8
 * @description : webService Basic验证
 */
public class FaceAuthInterceptor extends AbstractSoapInterceptor{

    private static final String BASIC_PREFIX = "Basic ";
    private static final String USERNAME = "sgkj-face";
    private static final String PASSWORD = "#sgkj@face#310";
    
    /**
     * 指定加入拦截器到某个阶段 
     */
    public FaceAuthInterceptor() {
        super(Phase.PRE_INVOKE);
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {

        // 获取HttpServletRequest
        HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
        
        String auth = request.getHeader("Authorization");
        
        if (auth == null) {
            SOAPException exception = new SOAPException("Authorization 授权信息为空！");
            throw new Fault(exception);
        }
        if (!auth.startsWith(BASIC_PREFIX)) {
            SOAPException exception = new SOAPException("Authorization 非baisc验证");
            throw new Fault(exception);
        }
        // 合法的baisc格式为username:password  例如：admin:123456
        String plaintext = new String(Base64.decodeBase64(auth.substring(BASIC_PREFIX.length())));
        if (StringUtils.isEmpty(plaintext) || !plaintext.contains(":")) {
            SOAPException exception = new SOAPException("Authorization 非baisc验证");
            throw new Fault(exception);
        }
        
        String[] usernameAndPass = plaintext.split(":");
        String username = usernameAndPass[0];
        String password = usernameAndPass[1];
        if (!USERNAME.equals(username) || !PASSWORD.equals(password)) {
            SOAPException exception = new SOAPException("用户名或密码不正确！");
            throw new Fault(exception);
        }

    }

}
