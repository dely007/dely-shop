package net.dely.shop.enums;

import java.util.function.Function;

/**
 * 校验数据类型枚举
 * User: dengjiaxin
 * Date: 2022/6/27
 * Time: 23:17
 * To change this template use File | Settings | File Templates.
 * @author admin
 */
public enum SensitiveStrategy {

    /**
     * Username sensitive strategy.  $1 替换为正则的第一组  $2 替换为正则的第二组
     */
    USERNAME(s -> s.replaceAll("(\\S)\\S(\\S*)", "$1*$2")),
    /**
     * Id card sensitive type.
     */
    ID_CARD(s -> s.replaceAll("(\\d{3})\\d{13}(\\w{2})", "$1****$2")),
    /**
     * Phone sensitive type.
     */
    PHONE(s -> s.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2")),
    /**
     * email sensitive type.
     */
    EMAIL(s -> s.substring(0,2)+"****"+s.substring(s.indexOf("@"))),
    /**
     * Address sensitive type.
     */
    ADDRESS(s -> s.replaceAll("(\\S{3})\\S{2}(\\S*)\\S{2}", "$1****$2****"));



    private final Function<String, String> desensitizer;

    /**
     * 定义构造函数，传入一个函数
     */
    SensitiveStrategy(Function<String, String> desensitizer) {
        this.desensitizer = desensitizer;
    }

    /**
     * getter方法
     */
    public Function<String, String> desensitizer() {
        return desensitizer;
    }
}
