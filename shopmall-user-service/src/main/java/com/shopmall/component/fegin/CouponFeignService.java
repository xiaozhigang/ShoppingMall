package com.shopmall.component.fegin;


import com.shopmall.request.NewUserCouponRequest;
import com.shopmall.util.JsonData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "shopmall-coupon-service")
public interface CouponFeignService {
    /**
     * 新用户注册发放优惠券
     *
     * @param newUserCouponRequest newUserCouponRequest
     * @return JsonData
     */
    @PostMapping("/api/coupon/v1/new_user_coupon")
    JsonData addNewUserCoupon(@RequestBody NewUserCouponRequest newUserCouponRequest);
}
