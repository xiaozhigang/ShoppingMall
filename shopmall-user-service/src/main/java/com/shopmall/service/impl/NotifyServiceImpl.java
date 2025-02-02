package com.shopmall.service.impl;

import com.shopmall.component.MailService;
import com.shopmall.constant.CacheKey;
import com.shopmall.enums.BizCodeEnum;
import com.shopmall.enums.SendCodeEnum;
import com.shopmall.service.NotifyService;
import com.shopmall.util.CheckUtil;
import com.shopmall.util.CommonUtil;
import com.shopmall.util.JsonData;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author xiao
 * @data 2024/3/22 23:48
 */
@Service
@Slf4j
public class NotifyServiceImpl implements NotifyService {

    @Autowired
    private MailService mailService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 验证码的标题
     */
    private static final String SUBJECT = "shopmall验证码";

    /**
     * 验证码的内容
     */
    private static final String CONTENT = "您的验证码是%s,有效时间是10分钟,打死也不要告诉任何人";

    /**
     * 验证码10分钟有效
     */
    private static final int CODE_EXPIRED = 60 * 1000 * 10;


    @Override
    public JsonData sendCode(SendCodeEnum sendCodeEnum, String to) {
        String cacheKey = String.format(CacheKey.CHECK_CODE_KEY, sendCodeEnum.name(), to);
        String cacheValue = redisTemplate.opsForValue().get(cacheKey);
        if (StringUtils.isNotBlank(cacheValue)) {
            long time = Long.parseLong(cacheValue.split("_")[1]);
            if (CommonUtil.getCurrentTimestamp() - time < 1000 * 60) {
                log.info("重复发送验证码，时间间隔：{}秒", (CommonUtil.getCurrentTimestamp() - time) / 1000);
                return JsonData.buildResult(BizCodeEnum.CODE_LIMITED);
            }
        }
        if (CheckUtil.isEmail(to)) {
            //拼接验证码 2322_324243232424324
            String code = CommonUtil.getRandomCode(6);
            String value = code + "_" + CommonUtil.getCurrentTimestamp();
            redisTemplate.opsForValue().set(cacheKey, value, CODE_EXPIRED, TimeUnit.MILLISECONDS);
            mailService.sendMail(to, SUBJECT, String.format(CONTENT, code));
            return JsonData.buildSuccess();
        } else if (CheckUtil.isPhone(to)) {
            // TODO
            // 短线验证码
        }
        return JsonData.buildResult(BizCodeEnum.CODE_TO_ERROR);
    }

    @Override
    public boolean checkCode(SendCodeEnum sendCodeEnum, String to, String code) {
        String cacheKey = String.format(CacheKey.CHECK_CODE_KEY, sendCodeEnum.name(), to);
        String cacheValue = redisTemplate.opsForValue().get(cacheKey);
        if(StringUtils.isNotBlank(cacheValue)){
            String cacheCode = cacheValue.split("_")[0];
            if(cacheCode.equals(code)){
                redisTemplate.delete(cacheKey);
                return true;
            }
        }
        return false;
    }
}
