package com.shopmall.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 新用户注册
 *
 * @author xiao
 * @data 2024/4/1 8:32
 */
@Getter
@Setter
public class NewUserCouponRequest {
    @JsonProperty("user_id")
    private long userId;

    @JsonProperty("name")
    private String name;
}
