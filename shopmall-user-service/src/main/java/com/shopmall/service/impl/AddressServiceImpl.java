package com.shopmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shopmall.mapper.AddressMapper;
import com.shopmall.model.AddressDO;
import com.shopmall.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 电商-公司收发货地址
 * </p>
 *
 * @author xiao
 * @since 2024-03-20
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public AddressDO detail(long id) {
        return addressMapper.selectOne(new QueryWrapper<AddressDO>().eq("id", id));
    }
}
