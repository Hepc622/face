//package com.sgkj.face.common.aop;
//
//import com.sgkj.face.common.annotations.NoRight;
//import com.sgkj.face.common.dto.Result;
//import com.sgkj.face.utils.Md5Util;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.annotation.AnnotationUtils;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * @Author HPC
// * @Description 权限拦截
// * @Date 2019/7/9 15:06
// */
//@Component
//@Aspect
//@Slf4j
//public class SecurityAspect {
//    /**
//     * 程序key
//     */
//    @Value("${face.appKey}")
//    private String key;
//
//    /**
//     * 程序秘钥
//     */
//    @Value("${face.appSecret}")
//    private String secret;
//
//    /**
//     * 盐值
//     */
//    @Value("${face.salt}")
//    private String salt;
//
//    /**
//     * @param pjp
//     * @return void
//     * @Description: 环绕触发
//     */
//    @ResponseBody
//    @Around(value = "execution(* com.sgkj.face.controller.*.*(..))")
//    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
//        MethodSignature signature = (MethodSignature) pjp.getSignature();
//        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
//        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
//        assert sra != null;
//        HttpServletRequest request = sra.getRequest();
//        NoRight annotation = AnnotationUtils.findAnnotation(signature.getMethod(), NoRight.class);
//        /*如果没有注解或者注解为false的就是需要校验权限的*/
//        if (annotation == null || !annotation.value()) {
//            /*MD5({appKey+.+salt+.+appSecret}) */
//            String token = request.getHeader("token");
//
//            /*系统内部key*/
//            String sysMd5 = "{" + key + "." + salt + "." + secret + "}";
//            String sysEncodePassword = Md5Util.encodePassword(sysMd5, salt);
//            /*相等说明有权限*/
//            if (sysEncodePassword.equals(token)) {
//                return pjp.proceed();
//            } else {
//                return Result.noRight(null);
//            }
//        }
//        return pjp.proceed();
//    }
//}
