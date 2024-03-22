package com.shopmall.service.impl;

import com.shopmall.enums.SendCodeEnum;
import com.shopmall.service.NotifyService;
import com.shopmall.util.JsonData;

/**
 * @author xiao
 * @data 2024/3/22 23:48
 */
public class NotifyServiceImpl implements NotifyService {
    @Override
    public JsonData sendCode(SendCodeEnum sendCodeEnum, String to) {
        return null;
    }

    @Override
    public boolean checkCode(SendCodeEnum sendCodeEnum, String to, String code) {
        return false;
    }
}
