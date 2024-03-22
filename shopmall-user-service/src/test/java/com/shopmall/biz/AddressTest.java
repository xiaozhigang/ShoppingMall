package com.shopmall.biz;

import com.shopmall.UserApplication;
import com.shopmall.model.AddressDO;
import com.shopmall.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author xiao
 * @data 2024/3/22 22:03
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@Slf4j
public class AddressTest {
    @Autowired
    private AddressService addressService;

    @Test
    public void testAddressDetail(){
        AddressDO detail = addressService.detail(1L);
        log.info(detail.toString());
        Assert.assertNotNull(detail);
    }
}
