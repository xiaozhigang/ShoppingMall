package com.shopmall.controller;


import com.shopmall.enums.BizCodeEnum;
import com.shopmall.request.UserLoginRequest;
import com.shopmall.service.FileService;
import com.shopmall.service.UserService;
import com.shopmall.util.JsonData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xiao
 * @since 2024-03-20
 */
@Api(tags = "用户模块")
@RestController
@RequestMapping("/api/user/v1")
public class UserController {
    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @ApiOperation("用户头像上传")
    @PostMapping(value = "upload")
    public JsonData uploadUserImg(@ApiParam(value = "文件上传", required = true) @RequestPart("file") MultipartFile file) {
        String result = fileService.uploadUserImg(file);
        return result != null ? JsonData.buildSuccess(result) : JsonData.buildResult(BizCodeEnum.FILE_UPLOAD_USER_IMG_FAIL);
    }

    /**
     * 用户登录
     *
     *
     */
    @ApiOperation("用户登录")
    @PostMapping("login")
    public JsonData login(@ApiParam("用户登录对象") @RequestBody UserLoginRequest userLoginRequest){


        JsonData jsonData = userService.login(userLoginRequest);

        return jsonData;
    }
}

