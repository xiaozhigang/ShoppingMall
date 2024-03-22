package com.shopmall.exception;

import com.shopmall.util.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 自定义异常
 *
 * @author xiao
 * @data 2024/3/22 21:37
 */
@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonData handle(Exception e) {
        if (e instanceof BizException) {
            BizException bizException = (BizException) e;
            log.error("[业务异常 {}]", e);
            return JsonData.buildCodeAndMsg(bizException.getCode(), bizException.getMsg());
        }
        log.error("[系统异常 {}]", e);
        return JsonData.buildError("全局异常，未知错误");

    }
}
