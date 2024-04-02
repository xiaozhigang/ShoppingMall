package com.shopmall.util;

import com.shopmall.model.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author xiao
 * @data 2024/4/2 22:13
 */
@Slf4j
public class JWTUtil {
    /**
     * token 过期时间
     */
    private static final long EXPIRE = 1000*60*60*24*7;

    /**
     * 加密的秘钥
     */
    private static final String SECRET = "com.shopmall";

    /**
     * 令牌前缀
     */
    private static final String TOKEN_PREFIX = "shopmall";

    /**
     * subject
     */
    private static final String SUBJECT = "shopmall";

    /**
     * 根据用户信息，生成令牌
     *
     * @param user user
     * @return String
     */
    public static String geneJsonWebToken(LoginUser user){
        if(user == null){
            throw new NullPointerException("LoginUser 对象为空");
        }
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("head_img", user.getHeadImg())
                .claim("id", user.getId())
                .claim("name", user.getName())
                .claim("mail", user.getMail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.ES256, SECRET).compact();
        return TOKEN_PREFIX + token;
    }

    /**
     * 校验token的方法
     *
     * @param token token
     * @return Claims
     */
    public static Claims checkJWT(String token){
        try{
            return Jwts.parser().setSigningKey(SECRET).parseClaimsJwt(token.replace(TOKEN_PREFIX, "")).getBody();
        }catch (Exception e){
            log.info("jwt token解密失败");
            return null;
        }
    }
}
