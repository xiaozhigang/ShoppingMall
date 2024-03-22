package com.shopmall.service;

import com.shopmall.enums.SendCodeEnum;
import com.shopmall.util.JsonData;

public interface NotifyService {
    /**
     * 发送验证码
     *
     * @param sendCodeEnum sendCodeEnum
     * @param to to
     * @return JsonData
     */
    JsonData sendCode(SendCodeEnum sendCodeEnum, String to );


    /**
     * 判断验证码是否一样
     *
     * @param sendCodeEnum sendCodeEnum
     * @param to to
     * @param code code
     * @return boolean
     */
    boolean checkCode(SendCodeEnum sendCodeEnum,String to, String code);

}
