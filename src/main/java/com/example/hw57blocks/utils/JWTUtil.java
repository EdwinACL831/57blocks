package com.example.hw57blocks.utils;

import com.netflix.graphql.dgs.exceptions.DgsBadRequestException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

public class JWTUtil {
    public final static String JWT_EMAIL_KEY = "email";
    private static final SecretKey SIGN_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final static String TOKEN_EXP_MSG = "The session has expired, please login again";

    public static String generateToken(Map<String, Object> claims, long lifeTimeMillis) {
        long currentTime = System.currentTimeMillis();
        long expTime = currentTime + lifeTimeMillis;

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expTime))
                .setSubject("login token for authentication")
                .setIssuer("graphql API")
                .signWith(SIGN_KEY)
                .compact();
    }

    public static void validateBearerToken(String bearerToken) {
        Claims claims = JWTUtil.getTokenClaims(bearerToken);
        long expTime = claims.getExpiration().getTime();
        if (System.currentTimeMillis() > expTime) {
            throw new DgsBadRequestException(TOKEN_EXP_MSG);
        }
    }

    public static Claims getTokenClaims(String bearerToken) {
        String jwt = bearerToken.substring(7);

        return Jwts.parserBuilder()
                .setSigningKey(SIGN_KEY)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }
}
