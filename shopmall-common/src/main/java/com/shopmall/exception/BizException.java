package com.shopmall.exception;

import com.shopmall.enums.BizCodeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 自定义返回异常
 *
 * @author xiao
 * @data 2024/3/22 21:24
 */
@Getter
@Setter
public class BizException extends RuntimeException {
    private int code;
    private String msg;

    public BizException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BizException(BizCodeEnum bizCodeEnum) {
        this(bizCodeEnum.getCode(), bizCodeEnum.getMessage());
    }
}
