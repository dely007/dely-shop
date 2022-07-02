package net.dely.shop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: dely
 * Date: 2022/6/28
 * Time: 19:38
 * 标识加密的注解,value值暂时没用，根据需要可以考虑采用的加密方式与算法等
 * 注意：Encrypted接口的getEncryptFields与@Encrypt注解可配合使用也可以互斥使用，根据具体的需求实现。
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Encrypt {
    String value() default "";
}
