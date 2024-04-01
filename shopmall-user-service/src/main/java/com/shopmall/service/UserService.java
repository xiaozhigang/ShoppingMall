package com.shopmall.service;

import com.shopmall.request.UserRegisterRequest;
import com.shopmall.util.JsonData;

public interface UserService {
    /**
     * 用户注册
     * @param registerRequest registerRequest
     * @return JsonData
     */
    JsonData register(UserRegisterRequest registerRequest);
}
