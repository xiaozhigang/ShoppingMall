package com.shopmall.controller;

import com.google.code.kaptcha.Producer;
import com.shopmall.enums.BizCodeEnum;
import com.shopmall.enums.SendCodeEnum;
import com.shopmall.service.NotifyService;
import com.shopmall.util.CommonUtil;
import com.shopmall.util.JsonData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author xiao
 * @data 2024/3/22 23:44
 */
@Api(tags = "通知模块")
@RestController
@RequestMapping("/api/user/v1")
@Slf4j
public class NotifyController {
    /**
     * 临时使用10分钟有效，方便测试
     */
    private static final long CAPTCHA_CODE_EXPIRED = 60 * 1000 * 10;

    @Autowired
    private Producer captchaProducer;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private NotifyService notifyService;


    /**
     * 获取图形验证码
     *
     * @param request
     * @param response
     */
    @ApiOperation("获取图形验证码")
    @GetMapping("captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) {

        String cacheKey = getCaptchaKey(request);

        String capText = captchaProducer.createText();

        // 存储
        redisTemplate.opsForValue().set(cacheKey, capText, CAPTCHA_CODE_EXPIRED, TimeUnit.MILLISECONDS);

        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = null;
        try {
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "create_date-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            out = response.getOutputStream();
            ImageIO.write(bi, "jpg", out);
            out.flush();
            out.close();

        } catch (IOException e) {
            log.error("获取验证码失败:{}", e);
        }
    }

    public JsonData sendRegisterCode(@ApiParam("收信人") @RequestParam(value = "to", required = true) String to,
                                     @ApiParam("图形验证码") @RequestParam(value = "captcha", required = true) String captcha,
                                     HttpServletRequest request) {
        String captchaKey = getCaptchaKey(request);
        String cacheCaptcha = redisTemplate.opsForValue().get(captchaKey);
        if(cacheCaptcha != null && cacheCaptcha.equalsIgnoreCase(captcha)){
            redisTemplate.delete(captchaKey);
            return notifyService.sendCode(SendCodeEnum.USER_REGISTER, to);
        }
        return JsonData.buildResult(BizCodeEnum.CODE_CAPTCHA_ERROR);
    }

    /**
     * 获取缓存的key ，根据浏览器内核生成唯一值
     *
     * @param request request
     * @return String
     */
    private String getCaptchaKey(HttpServletRequest request) {
        String ip = CommonUtil.getIpAddr(request);
        String userAgent = request.getHeader("User-Agent");

        String key = "user-service:captcha:" + CommonUtil.MD5(ip + userAgent);

        log.info("ip={}", ip);
        log.info("userAgent={}", userAgent);
        log.info("key={}", key);

        return key;
    }
}
