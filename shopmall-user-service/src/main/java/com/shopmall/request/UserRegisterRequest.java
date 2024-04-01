package com.shopmall.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户注册对象
 *
 * @author xiao
 * @data 2024/4/1 7:52
 */
@Getter
@Setter
@ApiModel(value = "用户注册对象", description = "用户注册请求对象")
public class UserRegisterRequest {
    @ApiModelProperty(value = "昵称", example = "菜鸡")
    private String name;

    @ApiModelProperty(value = "密码",example = "123456")
    private String pwd;

    @ApiModelProperty(value = "头像",example = "https://shopmall-img.oss-cn-shenzhen.aliyuncs.com/user/2021/02/03/39473aa1029a430298ac2620dd819962.jpeg")
    @JsonProperty("head_img")
    private String headImg;

    @ApiModelProperty(value = "用户个人性签名",example = "人生需要动态规划，学习需要贪心算法")
    private String slogan;

    @ApiModelProperty(value = "0表示女，1表示男",example = "1")
    private Integer sex;

    @ApiModelProperty(value = "邮箱",example = "1604754079@qq.com")
    private String mail;

    @ApiModelProperty(value = "验证码",example = "123456")
    private String code;
}
