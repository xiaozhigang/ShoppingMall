package com.shopmall.util;

import com.shopmall.enums.BizCodeEnum;
import lombok.*;

/**
 * @author xiao
 * @data 2024/3/22 8:37
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JsonData {
    /**
     * 状态码 0 表示成功
     */

    private Integer code;
    /**
     * 数据
     */
    private Object data;
    /**
     * 描述
     */
    private String msg;

    /**
     * 成功，不传入数据
     * @return JsonData
     */
    public static JsonData buildSuccess() {
        return new JsonData(0, null, null);
    }

    /**
     *  成功，传入数据
     * @param data data
     * @return JsonData
     */
    public static JsonData buildSuccess(Object data) {
        return new JsonData(0, data, null);
    }

    /**
     * 失败，传入描述信息
     * @param msg msg
     * @return JsonData
     */
    public static JsonData buildError(String msg) {
        return new JsonData(-1, null, msg);
    }



    /**
     * 自定义状态码和错误信息
     * @param code code
     * @param msg msg
     * @return JsonData
     */
    public static JsonData buildCodeAndMsg(int code, String msg) {
        return new JsonData(code, null, msg);
    }

    /**
     * 传入枚举，返回信息
     * @param codeEnum codeEnum
     * @return JsonData
     */
    public static JsonData buildResult(BizCodeEnum codeEnum){
        return JsonData.buildCodeAndMsg(codeEnum.getCode(),codeEnum.getMessage());
    }
}
