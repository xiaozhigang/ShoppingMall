package com.shopmall.service;

import com.shopmall.request.UserLoginRequest;
import com.shopmall.request.UserRegisterRequest;
import com.shopmall.util.JsonData;
import com.shopmall.vo.UserVO;

public interface UserService {
    /**
     * 用户注册
     *
     * @param registerRequest registerRequest
     * @return JsonData
     */
    JsonData register(UserRegisterRequest registerRequest);

    /**
     * 用户登录
     *
     * @param userLoginRequest userLoginRequest
     * @return JsonData
     */
    JsonData login(UserLoginRequest userLoginRequest);

    /**
     * 查找用户详情
     *
     * @return UserVO
     */
    UserVO findUserDetail();
}
