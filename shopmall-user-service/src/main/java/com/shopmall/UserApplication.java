package com.shopmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 启动类
 *
 * @author xiao
 * @data 2024/3/19 23:22
 */
@EnableFeignClients
@SpringBootApplication
@MapperScan("com.shopmall.mapper")
public class UserApplication {
    public static void main(String[] args){
        SpringApplication.run(UserApplication.class,args);
    }
}
