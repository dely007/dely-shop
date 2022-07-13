package net.dely.shop.exception;

import lombok.Data;
import net.dely.shop.enums.BizCodeEnum;

/**
 * 全局业务异常
 * @author: dely
 * Date: 2022/7/13
 * Time: 21:31
 */
@Data
public class BizException extends RuntimeException {

    private int code;

    private String msg;

    public BizException(int code, String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BizException(BizCodeEnum bizCodeEnum){
        super(bizCodeEnum.getMessage());
        this.code = bizCodeEnum.getCode();
        this.msg = bizCodeEnum.getMessage();
    }

}
