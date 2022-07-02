package net.dely.shop.storage.mysql.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.dely.shop.annotation.Encrypt;

import java.io.Serializable;
import java.util.Date;


/**
 * <p>
 *
 * </p>
 *
 * @author dely
 * @since 2022-06-25
 */
@Data
@TableName("user")
public class UserDO implements Serializable, Encrypted {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 昵称
     */
    private String name;

    /**
     * 密码
     */
    @Encrypt
    private String pwd;

    /**
     * 头像
     */
    private String headImg;

    /**
     * 用户签名
     */
    private String slogan;

    /**
     * 0表示女，1表示男
     */
    private Integer sex;

    /**
     * 积分
     */
    private Integer points;

    private Date createTime;

    /**
     * 邮箱
     */
//    @Sensitive(strategy = SensitiveStrategy.EMAIL)
    @Encrypt
    private String mail;

    /**
     * 盐，用于个人敏感信息处理
     */
    private String secret;

//    @Sensitive(strategy = SensitiveStrategy.PHONE)
    @TableField(exist = false)
    private String phone="13144127277";


    @Override
    public String[] getEncryptFields() {
        return new String[]{"pwd","mail"};
    }

}
