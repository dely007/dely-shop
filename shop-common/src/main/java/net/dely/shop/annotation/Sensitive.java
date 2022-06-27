package net.dely.shop.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import net.dely.shop.enums.SensitiveStrategy;
import net.dely.shop.util.SensitiveJsonSerializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by IntelliJ IDEA.
 * User: dengjiaxin
 * Date: 2022/6/27
 * Time: 23:15
 * To change this template use File | Settings | File Templates.
 * @author admin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside //这个注解用来标记Jackson复合注解,当你使用多个Jackson注解组合成一个自定义注解时会用到它
@JsonSerialize(using = SensitiveJsonSerializer.class) //指定使用自定义的序列化器
public @interface Sensitive {

    SensitiveStrategy strategy();   //该自定义注解需要的参数   strategy-参数名称    SensitiveStrategy-参数类型
}
