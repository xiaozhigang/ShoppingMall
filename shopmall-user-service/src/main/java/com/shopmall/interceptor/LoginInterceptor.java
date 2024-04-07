package com.shopmall.interceptor;

import com.shopmall.enums.BizCodeEnum;
import com.shopmall.model.LoginUser;
import com.shopmall.util.CommonUtil;
import com.shopmall.util.JWTUtil;
import com.shopmall.util.JsonData;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * @author xiao
 * @data 2024/4/7 8:20
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    public static ThreadLocal<LoginUser> threadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        String token = request.getHeader("token");
        if(token == null){
            token = request.getParameter("token");
        }

        if(StringUtils.isNotBlank(token)){
            Claims claims = JWTUtil.checkJWT(token);
            if(claims == null){
                CommonUtil.sendJsonMessage(response, JsonData.buildResult(BizCodeEnum.ACCOUNT_UNLOGIN));
                return false;
            }
            Long id = Long.valueOf(claims.get("id").toString());
            String headImg = claims.get("head_img").toString();
            String name = claims.get("name").toString();
            String mail = claims.get("mail").toString();

            LoginUser loginUser = new LoginUser(id, headImg, name, mail);
            threadLocal.set(loginUser);
            return true;
        }
        CommonUtil.sendJsonMessage(response, JsonData.buildResult(BizCodeEnum.ACCOUNT_UNLOGIN));
        return false;
    }

}
