package com.ohigraffers.securitytest.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtils {

    private static final Logger log = LoggerFactory.getLogger(TokenUtils.class);
    private static String jwtSecretKey;
    private static Long accessTokenExpiration;

    private static final String BEARER = "Bearer ";

    public static String getUsername(String accessToken) {
        return Jwts.parserBuilder()
                   .setSigningKey(createSignature())
                   .build()
                   .parseClaimsJws(accessToken)
                   .getBody()
                   .get("username").toString();
    }

    @Value("${jwt.secret}")
    public void setJwtSecretKey(String jwtSecretKey) {
        TokenUtils.jwtSecretKey = jwtSecretKey;
    }

    @Value("${jwt.access.expiration}")
    public void setAccessTokenExpiration(Long accessTokenExpiration) {
        TokenUtils.accessTokenExpiration = accessTokenExpiration;
    }

    public static String createAccessToken(Map<String, Object> memberInfo) {

        Claims claims = Jwts.claims().setSubject("AccessToken");
        claims.putAll(memberInfo);

        return Jwts.builder()
                   .setHeader(createHeader())
                   .setClaims(claims)
                   .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                   .signWith(createSignature(), SignatureAlgorithm.HS512)
                   .compact();
    }

    private static Key createSignature() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("type", "jwt");
        header.put("date", System.currentTimeMillis());
        return header;
    }

    public static String getToken(String token) {
        if (token != null && token.startsWith(BEARER)) {
            String temp = token.replace(BEARER, "");
            log.info("가공된 토큰 : {}",temp);
            return temp;
        }
        return null;
    }

    public static boolean isValidToken(String token) {
        try {
            log.info("요청된 토큰 : {}", token);
            Jwts.parserBuilder().setSigningKey(createSignature()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
