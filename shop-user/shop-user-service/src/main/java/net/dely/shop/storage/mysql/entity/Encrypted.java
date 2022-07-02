package net.dely.shop.storage.mysql.entity;

/**
 * Date: 2022/6/28
 * Time: 19:36
 * @author dely
 * 该接口用于标记实体类需要加密，具体的加密内容字段通过getEncryptFields返回.
 *  注意：getEncryptFields与@Encrypt注解可配合使用也可以互斥使用，根据具体的需求实现。
 */
public interface Encrypted {
    /**
     * 实现该接口，返回需要加密的字段名数组，需与类中字段完全一致，区分大小写
     * @return 返回需要加密的字段
     */
    default String[] getEncryptFields() {
        return new String[0];
    }
}
