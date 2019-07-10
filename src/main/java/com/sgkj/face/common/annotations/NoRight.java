package com.sgkj.face.common.annotations;

import java.lang.annotation.*;

/**
 * @author : HPC
 * @description : 权限注解，如果有这个注解标识无需权限
 * @date : 2019/7/9 15:26
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoRight {

    /**默认是为不需要权限*/
    boolean value() default true;

}
