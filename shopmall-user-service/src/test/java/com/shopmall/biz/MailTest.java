package com.shopmall.biz;

import com.shopmall.UserApplication;
import com.shopmall.component.MailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 邮件服务测试类
 *
 * @author xiao
 * @data 2024/3/24 20:05
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@Slf4j
public class MailTest {
    @Autowired
    private MailService mailService;

    @Test
    public void testSendMail(){
        mailService.sendMail("2508999433@qq.com","小菜鸡", "你是一个小菜鸡，小啊小菜鸡");
    }
}
