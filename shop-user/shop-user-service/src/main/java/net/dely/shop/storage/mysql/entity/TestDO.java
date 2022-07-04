package net.dely.shop.storage.mysql.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 测试类
 * @author: dely
 * Date: 2022/7/4
 * Time: 0:12
 */
@Data
public class TestDO implements Serializable {
    /**
     * 头像
     */
    private String headImg;

    /**
     * 用户签名
     */
    private String slogan;
}
