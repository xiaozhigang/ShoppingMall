package com.shopmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shopmall.component.fegin.CouponFeignService;
import com.shopmall.enums.BizCodeEnum;
import com.shopmall.enums.SendCodeEnum;
import com.shopmall.interceptor.LoginInterceptor;
import com.shopmall.mapper.UserMapper;
import com.shopmall.model.LoginUser;
import com.shopmall.model.UserDO;
import com.shopmall.request.NewUserCouponRequest;
import com.shopmall.request.UserLoginRequest;
import com.shopmall.request.UserRegisterRequest;
import com.shopmall.service.NotifyService;
import com.shopmall.service.UserService;
import com.shopmall.util.CommonUtil;
import com.shopmall.util.JWTUtil;
import com.shopmall.util.JsonData;
import com.shopmall.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

/**
 * 用户服务类
 *
 * @author xiao
 * @data 2024/4/1 7:59
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private NotifyService notifyService;

    @Autowired
    private CouponFeignService couponFeignService;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public JsonData register(UserRegisterRequest registerRequest) {
        boolean checkCode = false;
        //校验验证码
        if(StringUtils.isNotBlank(registerRequest.getMail())){
            checkCode = notifyService.checkCode(SendCodeEnum.USER_REGISTER, registerRequest.getMail(), registerRequest.getCode());
        }
        if(!checkCode){
            return JsonData.buildResult(BizCodeEnum.CODE_ERROR);
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(registerRequest,userDO);

        userDO.setCreateTime(new Date());
        userDO.setSlogan("一个大逼兜");

        // 设置密码 生成秘钥 盐
        userDO.setSecret("$1$" + CommonUtil.getStringNumRandom(8));
        // 密码+盐处理
        String pwd = Md5Crypt.md5Crypt(registerRequest.getPwd().getBytes(StandardCharsets.UTF_8),userDO.getSecret());
        userDO.setPwd(pwd);

        // 账号唯一性检查 1604754079@qq.com
        if(checkUnique(userDO.getMail())){
            int rows = userMapper.insert(userDO);
            log.info("rows:{}，注册成功：{}",rows,userDO.toString());

            // 新用户注册成功，初始化信息，发放福利等 TODO
            userRegisterInitTask(userDO);
            return  JsonData.buildSuccess();

        }else {
            return JsonData.buildResult(BizCodeEnum.ACCOUNT_REPEAT);
        }
    }

    /**
     * 校验用户账号唯一
     *
     * @param mail mail
     * @return boolean
     */
    private boolean checkUnique(String mail) {

        List<UserDO> list = userMapper.selectList(new QueryWrapper<UserDO>().eq("mail", mail));

        return list.size() <= 0 ;

    }

    private void userRegisterInitTask(UserDO userDO){
        NewUserCouponRequest request = new NewUserCouponRequest();
        request.setUserId(userDO.getId());
        request.setName(userDO.getName());
        JsonData jsonData = couponFeignService.addNewUserCoupon(request);
//        if(jsonData.getCode()!=0){
//            throw new RuntimeException("发放优惠卷异常");
//        }
        log.info("发放新用户注册优惠卷:{}，结果:{}",request.toString(),jsonData.toString());

    }

    @Override
    public JsonData login(UserLoginRequest  request){
        List<UserDO> userList = userMapper.selectList(new QueryWrapper<UserDO>().eq("mail", request.getMail()));
        if(userList != null && userList.size() ==1){
            UserDO user = userList.get(0);
            String crypt = Md5Crypt.md5Crypt(request.getPwd().getBytes(), user.getSecret());
            if(crypt.equals(user.getPwd())){
                LoginUser loginUser = LoginUser.builder().build();
                BeanUtils.copyProperties(user,loginUser);
                String token = JWTUtil.geneJsonWebToken(loginUser);
                // accessToken
                // accessToken的过期时间
                // UUID生成一个token
                //String refreshToken = CommonUtil.generateUUID();
                //redisTemplate.opsForValue().set(refreshToken,"1",1000*60*60*24*30);
                return JsonData.buildSuccess(token);
            }else {
                return JsonData.buildResult(BizCodeEnum.ACCOUNT_PWD_ERROR);
            }
        }else {
            return JsonData.buildResult(BizCodeEnum.ACCOUNT_UNREGISTER);
        }
    }

    /**
     * 查找用户详情
     *
     * @return UserVO
     */
    @Override
    public UserVO findUserDetail(){
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        UserDO userDO = userMapper.selectOne(new QueryWrapper<UserDO>().eq("id", loginUser.getId()));
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDO, userVO);
        return userVO;
    }

}
