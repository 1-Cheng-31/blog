package com.cheng.blog.util;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cheng
 * @date 2022/5/16 - 14:33
 */
public class JWTUtils {

    private static final String jwtToken = "123456cheng!@$$";

    public static String createToken(Long userId){
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId",userId);
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtToken)// 设置签发算法，密钥为jwtToken
                .setClaims(claims) // body 数据，要唯一，自行设置
                .setIssuedAt(new Date()) // 设置签发时间，保证唯一
                .setExpiration(new Date(System.currentTimeMillis() * 24 * 60 * 60 * 60 * 1000)); // 一天的有效时间
        String token = jwtBuilder.compact();
        return token;
    }

    public static Map<String,Object> checkToken(String token){
        // 这样子，token过期会无法登录
        /*try {
            Jwt parse = Jwts.parser().setSigningKey(jwtToken).parse(token);
            return (Map<String, Object>) parse;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;*/
        Claims parse;
        try {
             parse = Jwts.parser().setSigningKey(jwtToken).parseClaimsJws(token).getBody();

        } catch (ExpiredJwtException e) {
            parse = e.getClaims();
        }
        return (Map<String, Object>) parse;
    }

    public static void main(String[] args) {
        String token = JWTUtils.createToken(100L);
        System.out.println(token);
        Map<String, Object> map = JWTUtils.checkToken(token);
        System.out.println(map);
    }
}
