package net.dely.shop.exception;

import lombok.extern.slf4j.Slf4j;
import net.dely.shop.util.ResultData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常
 * @author: dely
 * Date: 2022/7/17
 * Time: 0:44
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultData Handle(Exception e) {

        if (e instanceof BizException) {
            BizException bizException = (BizException) e;
            log.info("[业务异常]", e);
            return ResultData.buildCodeAndMsg(bizException.getCode(),bizException.getMsg());

        } else {
            log.info("[系统异常]", e);
            return ResultData.buildError(e.getMessage());
        }

    }
}
