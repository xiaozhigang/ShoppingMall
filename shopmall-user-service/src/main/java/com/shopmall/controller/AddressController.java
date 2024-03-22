package com.shopmall.controller;


import com.shopmall.model.AddressDO;
import com.shopmall.service.AddressService;
import com.shopmall.util.JsonData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 电商-公司收发货地址表 前端控制器
 * </p>
 *
 * @author xiao
 * @since 2024-03-20
 */
@Api(tags = "收获地址模块")
@RestController
@RequestMapping("/addressDO")
public class AddressController {

    @Autowired
    private AddressService addressService;

    public Object detail(@ApiParam(value = "地址id", required = true)@PathVariable("address_id") long addressId){
        AddressDO detail = addressService.detail(addressId);
        return JsonData.buildSuccess(detail);
    }
}

