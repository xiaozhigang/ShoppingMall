package com.shopmall.biz;

import com.shopmall.UserApplication;
import com.shopmall.controller.NotifyController;
import com.shopmall.model.AddressDO;
import com.shopmall.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author xiao
 * @data 2024/3/23 11:57
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@Slf4j
public class NotifyTest {
    private static final long CAPTCHA_CODE_EXPIRED = 60 * 1000 * 10;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void testAddressDetail(){
        redisTemplate.opsForValue().set("test","test", CAPTCHA_CODE_EXPIRED, TimeUnit.MILLISECONDS);
    }
}
